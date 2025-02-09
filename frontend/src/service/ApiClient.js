import axios from "axios";


export const ApiClient = axios.create({
    "baseURL":"http://localhost:8080"
})


export const SecureApiClient = axios.create({
    "baseURL":"http://localhost:8080/api/secure"
})



SecureApiClient.interceptors.request.use((config) => {
    const token = localStorage.getItem('JWTToken'); 
    if (token) {
        config.headers['Authorization'] = 'Bearer ' + token;  
    }
    return config;
}, (error) => {
    return Promise.reject(error);
});
