import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import { createShopReview, deleteShopReview, getItemsByShopId, getOwnerId, getReviewsByShopId, getShopById, toggleShopReviewDislike, toggleShopReviewLike, updateShopReview } from "../../service/BackendApi";
import "./Shop.scss";
import { BsFillTelephoneFill } from "react-icons/bs";
import { FaMapLocationDot } from "react-icons/fa6";
import { FaClock } from "react-icons/fa";
import ItemOverview from "../item/ItemOverview";
import { useAuth } from "../../security/AuthContext";
import toast from "react-hot-toast";
import Reviews from "../reviews/Reviews";


export default function Shop(){
    const {shopId} = useParams();
    const [shop,setShop] = useState({});
    const [items,setItems] = useState([]);
    const [reviews,setReviews] = useState([]);
    const [isOwner,setIsOwner] = useState(false);

    const [show,setShow] = useState("Items")

    const [refresh,setRefresh] = useState(false);

    const [refreshItems,setRefreshItems] = useState(false);

    const [query,setQuery] = useState("")

    const [refreshReviews,setRefreshReviews] = useState(false);

    const authContext = useAuth();

    const [position,setPosition] = useState(authContext?.position)

    const isLoading = authContext?.isLoading;

    const navigate = useNavigate();
    
 
    useEffect(()=>{
        if(!isLoading){
            getOwnerId(shopId).then((response)=>{
                setIsOwner(authContext.decoded.userId === response.data);
            }).catch((err)=>{
                console.log(err);
                setIsOwner(false);
            })
            
            getShopById(shopId,position)
            .then((response)=>{
                refreshShop(response.data)
            })
            .catch(
                (err)=>{
                    console.log(err)
                    toast.error("No such shop exists!");
                    navigate("/home")
                });

            function refreshShop(shop){
                setShop(shop);
            }
        }
    },[shopId,refresh,position])

    useEffect(()=>{
        setPosition(authContext?.position)
    },[authContext?.position])

    useEffect(()=>{
        getReviewsByShopId(shopId,position).then(
            (response)=>{
                setReviews(response.data.content)
            }
        ).catch(
            (err)=>{
                toast.error("An error occured while fetching shop reviews.")
                navigate("/home")
            }
        )
    },[refreshReviews,position,shopId])


    useEffect(()=>{
        getItemsByShopId(shopId,position,0,0,query).then(
            (response)=>{
                setItems(response.data.content)
            }
        ).catch(
            (err)=>{
                toast.error("An error occured while fetching items.");
                navigate("/home")
            }
        )

    },[refreshItems,position,shopId,query])


    async function handleCreateShopReview(values){
        return createShopReview(shopId,{userId:authContext.decoded.userId,...values},position).then(
            (response)=>{
                
                setRefreshReviews(!refreshReviews)
                toast.success("Review posted successfully!");
            }
        ).catch(
            (error)=>{
                toast.error("An error occurred.");
            }
        );
    }

    async function  handleUpdateShopReview(values,reviewId) {
        return updateShopReview(shopId,reviewId,values).then((response)=>{
          toast.success("Review edited successfully");
          setRefreshReviews(!refreshReviews)
        }).catch((error)=>{
          toast.error("An error occurred");
          console.log(error);
        });  
      }
    
      async function handleDeleteShopReview(reviewId){
        return deleteShopReview(shopId,reviewId).then((response)=>{
          toast.success("Review deleted successfully!");
          setRefreshReviews(!refreshReviews)
        }).catch((error)=>{
          toast.error("An error occurred");
          console.log(error);
        }); 
      }


      async function toggleLike(setLiked,setDisliked,review,liked,disliked){
          return toggleShopReviewLike(shopId,review.reviewId,position).then((response)=>{
            setLiked(!liked)
            setDisliked(false)
            setRefreshReviews(!refreshReviews)
          }).catch((error)=>{
            toast.error("Error liking review.")
          })
        }
      
        async function toggleDislike(setLiked,setDisliked,review,liked,disliked){
          return toggleShopReviewDislike(shopId,review.reviewId,position).then((response)=>{
            setDisliked(!disliked)
            setLiked(false)
            setRefreshReviews(!refreshReviews)
          }).catch((error)=>{
            toast.error("Error disliking review.")
          })
        }

    return <div className="">
    { isLoading? (<div class="vh-100 d-flex justify-content-center align-items-center">
    <div class="spinner-border" role="status">
        <span class="visually-hidden">Loading...</span>
    </div>
    </div>):
    (<div>
        <div className="shop-hero bg-white">
            <div className="shop-info text-primary">
                <h1 className="shop-name">{shop.shopName}</h1>
                <p className="shop-description text-break">{shop.description}</p>
                <div className="shop-contact">
                    <div className="icon"> <BsFillTelephoneFill color="white" size={25} /> </div>
                    <div className="content">{shop.contactNumber}</div>
                </div>
                <div className="shop-contact">
                    <div className="icon"> <FaMapLocationDot color="white"  size={25} /> </div>
                    <div className="content">{shop.address?.city},{shop.address?.state},{shop.address?.country}</div>
                </div>
                <div className="shop-contact">
                    <div className="icon"> <FaClock color="white"  size={25} /> </div>
                    <div className="content">{shop.hours?.openingTime}-{shop.hours?.closingTime}</div>
                </div>
                <div>
                    <h4>By <Link to={`/profile/${shop.vendorId}`}>{shop.vendorName}</Link></h4>
                </div>
            </div>
            <div>
                <img className="shop-image" src={`data:${shop.imageType};base64,${shop.shopImageData}`} alt="shop"/> 
            </div>
        </div>
        <div className="d-flex justify-content-center align-items-center">
            <button className={`btn btn${(show==='Items')?"-light":"-primary"} border-light m-2`} onClick={()=>{
                setShow("Items")
            }}>Items</button>
            <button className={`btn btn${(show==='Reviews')?"-light":"-primary"} border-light m-2`} onClick={()=>{
                setShow("Reviews")
            }}>Reviews</button>
        </div>
        <div className="bg-white rounded-4 bg-opacity-25 p-4 m-4">
                { show==="Items" &&  <div className="shop-items">
                    <div className="heading">
                        <h2 className="text-light">Our Items</h2>
                        {isOwner && <Link to="./create/item" className="btn btn-primary"> Create </Link>}
                    </div>
                    <div className="d-flex m-4 justify-content-center align-items-center">
                        <div className="col-4">
                            <input className="form-control bg-light" type="text" value={query} placeholder="Search for a Item" onChange={
                                (event)=>{
                                    setQuery(event.target.value)
                                }
                            }></input>
                        </div>
                        </div>
                    <div className="d-flex flex-wrap align-items-center justify-content-start">
                    
                        {items?.length===0 && <h5 className="text-white">No items available.</h5>}
                        {items?.map((item)=>{
                        return <ItemOverview item={item}/>
                    })}
                    </div>
                </div>
                }   
                {show==="Reviews" && 
                <Reviews handleCreateReview={handleCreateShopReview} 
                        handleUpdateReview={handleUpdateShopReview} 
                        handleDeleteReview={handleDeleteShopReview}  
                        toggleLike={toggleLike}
                        toggleDislike={toggleDislike}
                        reviews={reviews} 
                        isOwner={isOwner} 
                        element={"Shop"}/>
                }
            </div>
            </div>)
            }
    </div>
}