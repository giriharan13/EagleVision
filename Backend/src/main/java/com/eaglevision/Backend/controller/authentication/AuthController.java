package com.eaglevision.Backend.controller.authentication;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eaglevision.Backend.dto.RegisterUserDTO;
import com.eaglevision.Backend.dto.ValidityDTO;
import com.eaglevision.Backend.model.Buyer;
import com.eaglevision.Backend.model.Role;
import com.eaglevision.Backend.model.Vendor;
import com.eaglevision.Backend.service.BuyerService;
import com.eaglevision.Backend.service.RoleService;
import com.eaglevision.Backend.service.UserService;
import com.eaglevision.Backend.service.VendorService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private JwtEncoder jwtEncoder;
    private BCryptPasswordEncoder passwordEncoder;
    private UserService userService;
    private BuyerService buyerService;
    private VendorService vendorService;
    private RoleService roleService;

    @Autowired
    public AuthController(JwtEncoder jwtEncoder, BCryptPasswordEncoder passwordEncoder, UserService userService,
            BuyerService buyerService, VendorService vendorService, RoleService roleService) {
        this.jwtEncoder = jwtEncoder;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.buyerService = buyerService;
        this.vendorService = vendorService;
        this.roleService = roleService;
    }

    @PostMapping("login")
    public JwtResponse login(Authentication authentication) {
        return new JwtResponse(createToken(authentication));
    }

    @PostMapping("register")
    public ResponseEntity<String> registerVendor(@RequestBody RegisterUserDTO registerUserDTO) {
        if (userService.userExistsWithUserName(registerUserDTO.getUserName())) {
            return new ResponseEntity<>("Registration Failed: Username already exists!", HttpStatus.BAD_REQUEST);
        }

        if (registerUserDTO.getRole().equals("BUYER")) {
            Buyer buyer = new Buyer(registerUserDTO.getUserName(), registerUserDTO.getPhoneNumber(),
                    registerUserDTO.getDateOfBirth());
            buyer.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
            List<Role> roles = List.of(roleService.findRoleByName(registerUserDTO.getRole()));
            buyer.setRoles(roles);
            buyerService.createBuyer(buyer);
            return new ResponseEntity<>("Registration Success: Buyer created!", HttpStatus.OK);

        } else if (registerUserDTO.getRole().equals("VENDOR")) {
            Vendor vendor = new Vendor(registerUserDTO.getUserName(), registerUserDTO.getPhoneNumber(),
                    registerUserDTO.getDateOfBirth());
            vendor.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
            List<Role> roles = List.of(roleService.findRoleByName(registerUserDTO.getRole()));
            vendor.setRoles(roles);
            vendorService.createVendor(vendor);
            return new ResponseEntity<>("Registration Success: Vendor created!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Registration Failed: Invalid role!", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("username_validity_checks")
    public ResponseEntity<String> checkUserName(@RequestBody ValidityDTO<String> userNameValidityDTO) {
        if (userService.userExistsWithUserName(userNameValidityDTO.getField())) {
            return new ResponseEntity<>("Username already taken!", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("Valid username", HttpStatus.OK);
    }

    @PostMapping("phoneNumber_validity_checks")
    public ResponseEntity<String> checkPhoneNumber(@RequestBody ValidityDTO<String> phoneNumberValidityDTO) {
        if (userService.userExistsWithPhoneNumber(phoneNumberValidityDTO.getField())) {
            return new ResponseEntity<>("Phone number already taken!", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("Valid phone number", HttpStatus.OK);
    }

    @GetMapping("user_exists_by_phoneNumber/{phoneNumber}")
    public ResponseEntity<Boolean> checkUserExistsByPhoneNumber(@PathVariable String phoneNumber) {
        return new ResponseEntity<>(userService.userExistsWithPhoneNumber(phoneNumber), HttpStatus.OK);
    }

    @GetMapping("vendor_exists_by_telegram_user_id/{telegramUserId}")
    public ResponseEntity<Boolean> checkVendorExistsByTelegramUserId(@PathVariable String telegramUserId) {
        return new ResponseEntity<>(vendorService.existsByTelegramUserId(telegramUserId), HttpStatus.OK);
    }

    private String createToken(Authentication authentication) {
        var claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(60 * 60))
                .subject(authentication.getName())
                .claim("userId", userService.getUserIdByUserName(authentication.getName()))
                .claim("scope", createScope(authentication)).build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

    }
    

    private String createScope(Authentication authentication) {
        return authentication.getAuthorities().stream().map((auth) -> auth.toString()).collect(Collectors.joining(" "));
    }

}

record JwtResponse(String token) {
}
