import {ApiClient,SecureApiClient} from "./ApiClient";

export const getShops = (query,category,lat,lon,pageNumber,pageSize) => SecureApiClient.get("/shops",{params:{
    query,category,lat,lon,pageNumber,pageSize
}});

export const getShops2 = (query,category,lat,lon) => SecureApiClient.get("/shops",{
    params:{query,category,lat,lon}
})

export const getShopsByVendor = (vendorId,pageNumber,pageSize,position,sortBy,query) => SecureApiClient.get(`/users/vendors/${vendorId}/ownedshops`,{params:{
    pageNumber:pageNumber,
    pageSize:pageSize,
    sortBy:sortBy,
    query:query,
    lat:position[0],
    lon:position[1]
}})

export const getShopById = (id,position) => SecureApiClient.get(`/shops/${id}`,{params:{
    lat:position[0],
    lon:position[1]
}})

export const getShopsByCategory = (category,position) => SecureApiClient.get(`/shops`,{params:{
    category:category,
    lat:position[0],
    lon:position[1]
}})

export const getItemById = (shopId,id,position) => SecureApiClient.get(`/shops/${shopId}/items/${id}`,{params:{
    lat:position[0],
    lon:position[1]
}})

export const getItemsByShopId = (shopId,position,pageNumber=0,pageSize=0,query) => SecureApiClient.get(`/shops/${shopId}/items`,{params:{
    lat:position[0],
    lon:position[1],
    pageNumber:pageNumber,
    pageSize:pageSize,
    query:query
}})

export const getReviewsByShopId = (shopId,position,pageNumber=0,pageSize=0) => SecureApiClient.get(`/shops/${shopId}/reviews`,{
    params:{
        lat:position[0],
        lon:position[1],
        pageNumber:pageNumber,
        pageSize:pageSize
    }
})

export const getItemReviewsByItemId = (shopId,itemId,position,pageNumber=0,pageSize=0) => SecureApiClient.get(`/shops/${shopId}/items/${itemId}/reviews`,{
    params:{
        lat:position[0],
        lon:position[1],
        pageNumber:pageNumber,
        pageSize:pageSize
    }
})

export const getPingsById = (shopId,id,position) => SecureApiClient.get(`/shops/${shopId}/items/${id}/pings`,{
    params:{
        lat:position[0],
        lon:position[1]
    }
})

export const getPingsOnVendor = (vendorId,position) => SecureApiClient.get(`/pings/vendors/${vendorId}`,{
    params:{
        lat:position[0],
        lon:position[1]
    }
})

export const getPingsByBuyer = (buyerId,position) => SecureApiClient.get(`/pings/buyers/${buyerId}`,{
    params:{
        lat:position[0],
        lon:position[1]
    }
})

export const getShopReviewsForVendor = () => SecureApiClient.get("/reviews/shop")

export const getItemReviewsForVendor = () => SecureApiClient.get("/reviews/item")

export const getOwnerId = (shopId) => SecureApiClient.get(`/shops/${shopId}/owner`)

export const getRespondedPingsByVendor = (vendorId,position,pageNumber=0,pageSize=0)=> SecureApiClient.get(`/pings/vendors/response/${vendorId}`,{params:{
    pageNumber:pageNumber,
    pageSize:pageSize,
    lat:position[0],
    lon:position[1]
}})

export const getItems = (position,pageNumber=0,pageSize=0,query="") => SecureApiClient.get(`/searchitems`,{
    params:{
        pageNumber:pageNumber,
        pageSize:pageSize,
        query:query,
        lat:position[0],
        lon:position[1]
    }
})

export const toggleShopReviewLike = (shopId,reviewId,position) => SecureApiClient.put(`/shops/${shopId}/reviews/${reviewId}/like`,{},{
    params:{
        lat:position[0],
        lon:position[1]       
    }
})

export const toggleShopReviewDislike = (shopId,reviewId,position) => SecureApiClient.put(`/shops/${shopId}/reviews/${reviewId}/dislike`,{},{
    params:{
        lat:position[0],
        lon:position[1]       
    }
})

export const toggleItemReviewLike = (shopId,itemId,reviewId,position) => SecureApiClient.put(`/shops/${shopId}/items/${itemId}/reviews/${reviewId}/like`,{},{
    params:{
        lat:position[0],
        lon:position[1]       
    }
})

export const toggleItemReviewDislike = (shopId,itemId,reviewId,position) => SecureApiClient.put(`/shops/${shopId}/items/${itemId}/reviews/${reviewId}/dislike`,{},{
    params:{
        lat:position[0],
        lon:position[1]       
    }
})

export const setEagleEye = (shopId,itemId,position) =>SecureApiClient.put(`/shops/${shopId}/items/${itemId}/setEagleEye`,{},{
    params:{
        lat:position[0],
        lon:position[1]
    }
})

export const removeEagleEye = (shopId,itemId,position) =>SecureApiClient.put(`/shops/${shopId}/items/${itemId}/removeEagleEye`,{},{
    params:{
        lat:position[0],
        lon:position[1]
    }
})

export const subscribeToPlan = (subscriptionName)=> SecureApiClient.put(`/subscribe/${subscriptionName}`)

export const getAllSubscriptions = (pageNumber=0,pageSize=0)=>SecureApiClient.get("/subscriptions",{
    params:{
        pageNumber:0,
        pageSize:0
    }
})

export const getActiveSubscription = ()=>SecureApiClient.get(`/activeSubscription`)

export const getAllNotifications = () => SecureApiClient.get("/notifications")

export const getAllUnreadNotifications = () => SecureApiClient.get("/notifications/unread")

export const getShopMarker = (shopId) => SecureApiClient.get(`shops/${shopId}/shopMarker`)

export const getUser = (userId) => SecureApiClient.get(`/users/${userId}`)

export const getShopReviewsByBuyer = (buyerId,position) => SecureApiClient.get(`/users/buyers/${buyerId}/reviews/shopreviews`,{
    params:{
        lat:position[0],
        lon:position[1]
    }
})

export const getItemReviewsByBuyer = (buyerId,position) => SecureApiClient.get(`/users/buyers/${buyerId}/reviews/itemreviews`,{
    params:{
        lat:position[0],
        lon:position[1]
    }
})

export const createShop = (formData)=> SecureApiClient.post(`/shops`,formData, {
    headers: {
        'Content-Type': 'multipart/form-data'
    },
})

export const createItem = (shopId,formData)=> SecureApiClient.post(`/shops/${shopId}/items`,formData,{
    headers: {
        'Content-Type': 'multipart/form-data'
    },
})

export const createShopReview = (shopId,review,position)=> SecureApiClient.post(`/shops/${shopId}/reviews`,review,{
    params:{
        lat:position[0],
        lon:position[1]
    }
})

export const createItemReview = (shopId,itemId,review,position) => SecureApiClient.post(`/shops/${shopId}/items/${itemId}/reviews`,review,{
    params:{
        lat:position[0],
        lon:position[1]
    }
})

export const createBuyerPing = (shopId,itemId,values,position)=> SecureApiClient.post(`/shops/${shopId}/items/${itemId}/pings`,values,{
    params:{
        lat:position[0],
        lon:position[1]
    }
})

export const createVendorPing = (shopId,itemId,pingId,values)=> SecureApiClient.post(`/shops/${shopId}/items/${itemId}/pings/${pingId}`,values)

export const replyVendorPing = (pingId,values) => SecureApiClient.post(`/pings/reply/${pingId}`,values)

export const setProfilePicture = (formData) => SecureApiClient.put(`/users/setProfilePicture`,formData,{
    headers: {
        'Content-Type': 'multipart/form-data'
    },
})

export const updateShop = (shopId,values)=> SecureApiClient.put(`/shops/${shopId}`,values,
    {headers: {
        'Content-Type': 'multipart/form-data'
    },}
)

export const updateItem = (shopId,itemId,values)=> SecureApiClient.put(`/shops/${shopId}/items/${itemId}`,values,{headers: {
    'Content-Type': 'multipart/form-data'
},})

export const updateShopReview = (shopId,reviewId,values)=> SecureApiClient.put(`/shops/${shopId}/reviews/${reviewId}`,values)

export const updateItemReview = (shopId,itemId,reviewId,values)=> SecureApiClient.put(`/shops/${shopId}/items/${itemId}/reviews/${reviewId}`,values)

export const updateMarkerImage = (shopId,formData) => SecureApiClient.put(`shops/${shopId}/setShopMarker`,formData,{headers: {
    'Content-Type': 'multipart/form-data'
},})

export const markNotificationAsRead = (notificationId) => SecureApiClient.put(`/notifications/${notificationId}/read`)

export const markAllNotificationsAsRead = () => SecureApiClient.put(`/notifications/read`)

export const updateUserProfile = (userDetails) => SecureApiClient.put(`/users/updateProfile`,userDetails)

export const changePassword = (changePassword) => SecureApiClient.put("/users/changePassword",changePassword);

export const deleteShop = (shopId)=> SecureApiClient.delete(`/shops/${shopId}`)

export const deleteItem = (shopId,itemId)=> SecureApiClient.delete(`/shops/${shopId}/items/${itemId}`)

export const deleteShopReview = (shopId,reviewId) => SecureApiClient.delete(`/shops/${shopId}/reviews/${reviewId}`)

export const deleteItemReview = (shopId,itemId,reviewId) => SecureApiClient.delete(`/shops/${shopId}/items/${itemId}/reviews/${reviewId}`) 

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

