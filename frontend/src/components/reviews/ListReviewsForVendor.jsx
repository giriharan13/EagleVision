import { useEffect, useState } from "react"
import Review from "./Review"
import { getItemReviewsForVendor, getShopReviewsForVendor, toggleItemReviewDislike, toggleItemReviewLike, toggleShopReviewDislike, toggleShopReviewLike } from "../../service/BackendApi"
import Reviews from "./Reviews"
import toast from "react-hot-toast"
import { useAuth } from "../../security/AuthContext"


export default function ListReviewsForVendor(){

    const [type,setType] = useState("shop")

    const [shopReviews,setShopReviews] = useState([])

    const [refreshShopReviews,setRefreshShopReviews] = useState(true)

    const [itemReviews,setItemReviews] = useState([])

    const [refreshItemReviews,setRefreshItemReviews] = useState(true)

    const [isLoading,setIsLoading] = useState(true)

    const authContext = useAuth()

    const [position,setPosition] = useState(authContext?.position)


    useEffect(()=>{
        setPosition(authContext?.position)
        setRefreshShopReviews(!refreshShopReviews)
        setRefreshItemReviews(!refreshItemReviews)
    },[authContext?.position])


    useEffect(()=>{
        setIsLoading(true)
        if(type==="shop"){
            getShopReviewsForVendor().then(
                (response)=>{
                    setShopReviews(response.data.content);
                    setIsLoading(false);
                }
            )
        } 
        
        if(type==="item"){
            getItemReviewsForVendor().then(
                (response)=>{
                    setItemReviews(response.data.content);
                    setIsLoading(false);
                }
            )
        }
    },[type,refreshShopReviews,refreshItemReviews])

    async function toggleShopLike(setLiked,setDisliked,review,liked,disliked){
        return toggleShopReviewLike(review.shopId,review.reviewId,position).then((response)=>{
          setLiked(!liked)
          setDisliked(false)
          setRefreshShopReviews(!refreshShopReviews)
        }).catch((error)=>{
          toast.error("Error liking review.")
        })
      }
    
      async function toggleShopDislike(setLiked,setDisliked,review,liked,disliked){
        return toggleShopReviewDislike(review.shopId,review.reviewId,position).then((response)=>{
          setDisliked(!disliked)
          setLiked(false)
          setRefreshShopReviews(!refreshShopReviews)
        }).catch((error)=>{
          toast.error("Error disliking review.")
        })
      }


      async function toggleItemLike(setLiked,setDisliked,review,liked,disliked){
        return toggleItemReviewLike(review.shopId,review.itemId,review.reviewId,position).then((response)=>{
          setLiked(!liked)
          setDisliked(false)
          setRefreshItemReviews(!refreshItemReviews)
        }).catch((error)=>{
          toast.error("Error liking review.")
        })
      }
    
      async function toggleItemDislike(setLiked,setDisliked,review,liked,disliked){
        return toggleItemReviewDislike(review.shopId,review.itemId,review.reviewId,position).then((response)=>{
          setDisliked(!disliked)
          setLiked(false)
          setRefreshItemReviews(!refreshItemReviews)
        }).catch((error)=>{
          toast.error("Error disliking review.")
        })
      }





    return <div>
        <button className={`btn btn-${(type==="shop")?"light":"primary"} m-2 border-light`} onClick={
            ()=>{
                setType("shop")
            }
        }>Shop Reviews</button>
        <button className={`btn btn-${(type==="item")?"light":"primary"} m-2 border-light`} onClick={
            ()=>{
                setType("item")
            }
        }>Item Reviews</button>
        <div>
            <div>
                {
                    type==="shop" && !isLoading && <Reviews isOwner={true} element={"SHOP"} reviews={shopReviews}  toggleLike={toggleShopLike} toggleDislike={toggleShopDislike}/>
                }
            </div>
            <div>
            {
                // type==="item" && !isLoading && <div>
                // {itemReviews.map(
                //     (review,id)=>{
                //         return <Review review={review} key={id}/>
                //     }
                // )}
                // </div>

            }
            {
                type==="item" && !isLoading && <Reviews isOwner={true} element={"ITEM"} reviews={itemReviews} toggleLike={toggleItemLike} toggleDislike={toggleItemDislike}/>
            }
        </div>
    </div>
    </div>
}