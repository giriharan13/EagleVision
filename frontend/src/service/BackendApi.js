import ApiClient from "./ApiClient";

export const getShops = () => ApiClient.get("/shops");

export const getShopsByVendor = (vendorId) => ApiClient.get(`/users/vendors/${vendorId}/ownedshops`)

export const getShopById = (id) => ApiClient.get(`/shops/${id}`)

export const getItemById = (shopId,id) => ApiClient.get(`/shops/${shopId}/items/${id}`)

export const getPingsById = (shopId,id) => ApiClient.get(`/shops/${shopId}/items/${id}/pings`)

export const getOwnerId = (shopId) => ApiClient.get(`/shops/${shopId}/owner`)

export const createShop = (values)=>ApiClient.post(`/shops`,values)

export const createItem = (shopId,item)=>ApiClient.post(`/shops/${shopId}/items`,item)

export const createShopReview = (shopId,review)=>ApiClient.post(`/shops/${shopId}/reviews`,review)

export const createItemReview = (shopId,itemId,review) => ApiClient.post(`/shops/${shopId}/items/${itemId}/reviews`,review)

export const createBuyerPing = (shopId,itemId,values)=>ApiClient.post(`/shops/${shopId}/items/${itemId}/pings`,values)

export const createVendorPing = (shopId,itemId,pingId,values)=>ApiClient.post(`/shops/${shopId}/items/${itemId}/pings/${pingId}`,values)

export const updateShop = (shopId,values)=>ApiClient.put(`/shops/${shopId}`,values)

export const updateItem = (shopId,itemId,values)=>ApiClient.put(`/shops/${shopId}/items/${itemId}`,values)

export const updateShopReview = (shopId,reviewId,values)=>ApiClient.put(`/shops/${shopId}/reviews/${reviewId}`,values)

export const updateItemReview = (shopId,itemId,reviewId,values)=>ApiClient.put(`/shops/${shopId}/items/${itemId}/reviews/${reviewId}`,values)

export const deleteShop = (shopId)=>ApiClient.delete(`/shops/${shopId}`)

export const deleteItem = (shopId,itemId)=>ApiClient.delete(`/shops/${shopId}/items/${itemId}`)

export const deleteShopReview = (shopId,reviewId) => ApiClient.delete(`/shops/${shopId}/reviews/${reviewId}`)

export const deleteItemReview = (shopId,itemId,reviewId) => ApiClient.delete(`/shops/${shopId}/items/${itemId}/reviews/${reviewId}`) 

export const authenticate = (username,password) => ApiClient.post(`/auth/login`,{},{headers:{
    'Authorization': 'Basic '+ btoa(username + ':' + password)
}})

export const checkUsername = (username) => ApiClient.post(`/auth/username_validity_checks`,
    {field:username}
)

export const checkPhoneNumber = (phoneNumber) => ApiClient.post(`/auth/phoneNumber_validity_checks`,
    {field:phoneNumber}
)

export const signup = (userName,phoneNumber,dateOfBirth,password,role) => ApiClient.post(`/auth/register`,
    {
        userName,phoneNumber,dateOfBirth,password,role
    }
)

