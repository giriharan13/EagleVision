import ApiClient from "./ApiClient";

export const getShops = () => ApiClient.get("/shops");

export const getShopById = (id) => ApiClient.get(`/shops/${id}`)

export const getItemById = (shopId,id) => ApiClient.get(`/shops/${shopId}/items/${id}`)

export const getPingsById = (shopId,id) => ApiClient.get(`/shops/${shopId}/items/${id}/pings`)

export const authenticate = (username,password) => ApiClient.post(`/auth/login`,{},{headers:{
    'Authorization': 'Basic '+ btoa(username + ':' + password)
}})

