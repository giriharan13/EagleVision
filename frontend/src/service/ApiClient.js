import axios from "axios";


const ApiClient = axios.create({
    "baseURL":"http://localhost:8080"
})

export default ApiClient;