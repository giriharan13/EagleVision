import axios from "axios";


const host_url = process.env.REACT_APP_BACKEND_HOST_URL;

export const ApiClient = axios.create({
    "baseURL":host_url
})


export const SecureApiClient = axios.create({
    "baseURL":`${host_url}/api/secure`
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
