import { useEffect, useState } from "react"
import { deleteItemReview, deleteShopReview, getItemReviewsByBuyer, getPingsByBuyer, getShopReviewsByBuyer, toggleItemReviewDislike, toggleItemReviewLike, toggleShopReviewDislike, toggleShopReviewLike, updateItemReview, updateShopReview } from "../../service/BackendApi"
import toast from "react-hot-toast"
import { useAuth } from "../../security/AuthContext"
import ShopReview from "../reviews/ShopReview"
import ItemReview from "../reviews/ItemReview"
import { useParams } from "react-router-dom"
import Ping from "../pings/Ping"


export default function BuyerProfile(){

    const [shopReviews,setShopReviews] = useState([])

    const [refreshShopReviews,setRefreshShopReviews] = useState(false)

    const [itemReviews,setItemReviews] = useState([])

    const [refreshItemReviews,setRefreshItemReviews] = useState(false)

    const [pings,setPings] = useState([])

    const [refreshPings,setRefreshPings] = useState([])

    const authContext = useAuth()
    
    const {userId} = useParams()

    const [position,setPosition] = useState(authContext?.position)

    const [type,setType]  = useState("SHOP REVIEWS")


    useEffect(()=>{
        setPosition(authContext?.position)
        setRefreshItemReviews(!refreshItemReviews)
        setRefreshShopReviews(!refreshShopReviews)
        setRefreshPings(!refreshPings)
    },[authContext?.position])

    


    useEffect(()=>{
        getShopReviewsByBuyer(userId,position).then(
            (response)=>{
                setShopReviews(response.data.content)
            }
        ).catch(
            (error)=>{
                console.log(error);
            }
        )
    },[refreshShopReviews])

    useEffect(()=>{
        getItemReviewsByBuyer(userId,position).then(
            (response)=>{
                setItemReviews(response.data.content)
            }
        ).catch(
            (error)=>{
                console.log(error);
            }
        )
    },[refreshItemReviews])


    useEffect(()=>{
        getPingsByBuyer(userId,position).then(
            (response)=>{
                setPings(response.data.content)
            }
        ).catch(
            (error)=>{
                console.log(error)
            }
        )
    },[refreshPings])



    async function  handleUpdateShopReview(shopId,reviewId,values) {
        return updateShopReview(shopId,reviewId,values).then((response)=>{
          toast.success("Review edited successfully");
          setRefreshShopReviews(!refreshShopReviews)
        }).catch((error)=>{
          toast.error("An error occurred");
          console.log(error);
        });  
      }
    
      async function handleDeleteShopReview(shopId,reviewId){
        return deleteShopReview(shopId,reviewId).then((response)=>{
          toast.success("Review deleted successfully!");
          setRefreshShopReviews(!refreshShopReviews)
        }).catch((error)=>{
          toast.error("An error occurred");
          console.log(error);
        }); 
      }

    async function handleUpdateItemReview(shopId,itemId,reviewId,values){
        return updateItemReview(shopId,itemId,reviewId,values).then((response)=>{
            toast.success("Item review updated successfully!");
            setRefreshItemReviews(!refreshItemReviews)
        }).catch((error)=>{
            toast.error("An error occurred.")
            console.log(error)
        })
    }

    async function handleDeleteItemReview(shopId,itemId,reviewId){
        return deleteItemReview(shopId,itemId,reviewId).then((response)=>{
            toast.success("Item review deleted successfully!");
            setRefreshItemReviews(!refreshItemReviews)
        }).catch((error)=>{
            toast.error("An error occurred.")
            console.log(error)
        })
    }

    async function toggleShopLike(shopId,setLiked,setDisliked,review,liked,disliked){
        return toggleShopReviewLike(shopId,review.reviewId,position).then((response)=>{
          setLiked(!liked)
          setDisliked(false)
          setRefreshShopReviews(!refreshShopReviews)
        }).catch((error)=>{
          toast.error("Error liking review.")
        })
      }
    
      async function toggleShopDislike(shopId,setLiked,setDisliked,review,liked,disliked){
        return toggleShopReviewDislike(shopId,review.reviewId,position).then((response)=>{
          setDisliked(!disliked)
          setLiked(false)
          setRefreshShopReviews(!refreshShopReviews)
        }).catch((error)=>{
          toast.error("Error disliking review.")
        })
      }

      async function toggleItemLike(shopId,itemId,setLiked,setDisliked,review,liked,disliked){
        return toggleItemReviewLike(shopId,itemId,review.reviewId,position).then((response)=>{
          setLiked(!liked)
          setDisliked(false)
          setRefreshItemReviews(!refreshItemReviews)
        }).catch((error)=>{
          toast.error("Error liking review.")
        })
      }
    
      async function toggleItemDislike(shopId,itemId,setLiked,setDisliked,review,liked,disliked){
        return toggleItemReviewDislike(shopId,itemId,review.reviewId,position).then((response)=>{
          setDisliked(!disliked)
          setLiked(false)
          setRefreshItemReviews(!refreshItemReviews)
        }).catch((error)=>{
          toast.error("Error disliking review.")
        })
      }




    return <>
        <div className="d-flex justify-content-center align-items-center mt-5">
        <button className={`btn ${type==="SHOP REVIEWS"?"btn-light":"btn-primary"} m-2`} onClick={
            ()=>{
                setRefreshShopReviews(!refreshShopReviews)
                setType("SHOP REVIEWS")
            }
        }>Shop Reviews</button>
        <button className={`btn ${type==="ITEM REVIEWS"?"btn-light":"btn-primary"} m-2`} onClick={
            ()=>{
                setRefreshItemReviews(!refreshItemReviews)
                setType("ITEM REVIEWS")
            }
        }>Item Reviews</button>
         <button className={`btn ${type==="PINGS"?"btn-light":"btn-primary"} m-2`} onClick={
            ()=>{
                setRefreshPings(!refreshPings)
                setType("PINGS")
            }
        }>Pings</button>
        </div>

        <div className="">
            {
                type==="SHOP REVIEWS" && <div>
                    {
                        shopReviews.map((review,id)=>{
                            return <div>
                                    <ShopReview review={review} key={id} handleUpdateReview={handleUpdateShopReview} handleDeleteReview={handleDeleteShopReview} toggleLike={toggleShopLike} toggleDislike={toggleShopDislike} shopId={review.shopId} />
                                </div>
                        })
                    }

                    {shopReviews.length===0 && <div>
                        <h3 className="text-light">No Shop Reviews created!</h3>
                        </div>
                    }
                    </div>
            }
            {
                type==="ITEM REVIEWS" && <div>
                    {
                        itemReviews.map((review,id)=>{
                            return <div>
                                    <ItemReview review={review} key={id} handleUpdateReview={handleUpdateItemReview} handleDeleteReview={handleDeleteItemReview} toggleLike={toggleItemLike} toggleDislike={toggleItemDislike} shopId={review.shopId} itemId={review.itemId}/>
                                </div>
                        })
                    }
                     {
                    itemReviews.length===0 && <div>
                        <h3 className="text-light">No Item Reviews created!</h3>
                        </div>
                    }
                    </div>
            }
            {
                type==="PINGS" && <div className="gap-2">
                {
                    pings.map((ping,id)=>{
                        return <Ping ping={ping} key={id}/>
                    })
                }
                {
                    pings.length===0 && <div>
                        <h3 className="text-light">No Pings created!</h3>
                        </div>
                }
                </div>
            }
        </div>
    </>
}