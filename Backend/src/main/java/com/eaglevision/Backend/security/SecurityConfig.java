package com.eaglevision.Backend.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.eaglevision.Backend.security.filters.BotAuthenticationFilter;
import com.eaglevision.Backend.security.filters.WebSocketAuthenticationFilter;
import com.eaglevision.Backend.service.AuthorityUtilityService;
import com.eaglevision.Backend.service.UserService;
import com.eaglevision.Backend.service.VendorService;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${bot.token}")
    private String botToken;

  
    @Autowired
    @Lazy
    private VendorService vendorService;

    @Autowired
    @Lazy
    private WebSocketAuthenticationFilter webSocketAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable());
        http.authorizeHttpRequests((auth) -> {
            auth.requestMatchers("/h2-console/**").permitAll();
            auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
            auth.requestMatchers("/auth/**").permitAll();
            auth.requestMatchers("/api/secure/**").authenticated();
            auth.requestMatchers("/api/bot/**").permitAll();
            auth.requestMatchers("/ws/**").permitAll();
            auth.anyRequest().authenticated(); 
        });

        http.addFilterBefore(new BotAuthenticationFilter(botToken, vendorService),
                UsernamePasswordAuthenticationFilter.class);
        
        http.addFilterBefore(webSocketAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class);

        http.httpBasic(Customizer.withDefaults());
        http.cors(Customizer.withDefaults());
        http.headers((header) -> header.frameOptions((frame) -> frame.sameOrigin()));
        http.oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public KeyPair keyPair() {
        try {
            var keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);

            return keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Bean
    public RSAKey rsaKey(KeyPair keyPair) {
        return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
                .privateKey(keyPair.getPrivate()).keyID(UUID.randomUUID().toString()).build();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource(RSAKey rsaKey) {
        var jwkSet = new JWKSet(rsaKey);

        return (JWKSelector jwkSelector, SecurityContext securityContext) -> jwkSelector.select(jwkSet);
    }

    @Bean
    public JwtDecoder jwtDecoder(RSAKey rsaKey) throws Exception {
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
    }

    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
