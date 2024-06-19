import axios from "axios";


const ApiClient = axios.create({
    "baseURL":"http://localhost:8080"
})

ApiClient.interceptors.request.use((config) => {
    const token = localStorage.getItem('JWTToken'); 
    if (token) {
        config.headers['Authorization'] = 'Bearer ' + token;  
    }
    return config;
}, (error) => {
    return Promise.reject(error);
});


export default ApiClient;