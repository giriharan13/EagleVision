import React, { useEffect, useState } from 'react'
import { Link, useNavigate, useParams } from 'react-router-dom'
import { createBuyerPing, createItemReview, deleteItem, deleteItemReview, getItemById, getItemReviewsByItemId, getOwnerId, removeEagleEye, setEagleEye, toggleItemReviewDislike, toggleItemReviewLike, updateItemReview } from '../../service/BackendApi';
import toast from 'react-hot-toast';
import ShopImage from "./../../images/shop.jpg"

import { AiFillEdit } from "react-icons/ai";
import { RiDeleteBinFill,RiPushpinFill } from "react-icons/ri";
import { FaQuestion } from "react-icons/fa";
import { useAuth } from '../../security/AuthContext';
import Reviews from '../reviews/Reviews';
import PingIcon from "../../images/pingvf.png"
import EagleEyeIcon from "../../images/eagleeyesvf.png"

function Item() {

    const {shopId,itemId} = useParams();

    const navigate = useNavigate();

    const [item,setItem] = useState({});

    const [itemReviews,setItemReviews] = useState([])

    const [refresh,setRefresh] = useState(false)

    const [refreshReviews,setRefreshReviews] = useState(false)

    const [isOwner,setIsOwner] = useState(false)

    const authContext = useAuth();

    const [position,setPosition] = useState(authContext?.position)

    const [isLoading,setIsLoading] = useState(authContext?.isLoading);

    useEffect(()=>{

        getOwnerId(shopId).then((response)=>{
            setIsOwner(authContext.decoded.userId === response.data);
        }).catch((err)=>{
            console.log(err);
            setIsOwner(false);
        })

        if(!isLoading){
            getItemById(shopId,itemId,position).then((response)=>{
                setItem(response.data)
            }).catch((error)=>{
                toast.error("An error occurred")
                console.log(error)
            })
        }


    },[refresh,authContext.decoded.userId,isLoading])

    useEffect(()=>{
        setPosition(authContext?.position)
        setIsLoading(authContext?.isLoading)
    },[authContext?.isLoading,authContext?.position])

    useEffect(()=>{
        if(!isLoading){
            getItemReviewsByItemId(shopId,itemId,position).then(
                (response)=>{
                    setItemReviews(response.data.content)
                }
            ).catch((error)=>{
                toast.error("Error occured")
                console.log(error)
            })
        }
    },[isLoading,refreshReviews])


    async function handleDeleteItem(){
        return deleteItem(shopId,itemId).then((response)=>{
            toast.success("Item deleted successfully.");
            navigate(`/shops/${shopId}`);
        }).catch((error)=>{
            toast.error("An error occurred.");
            console.log(error)
        })
    }
    
    async function handlePing(){
        return createBuyerPing(shopId,itemId,{
            type:0,
            userId:authContext.decoded.userId
        },position).then((response)=>{
            toast.success("Item pinged successfully.")
        }).catch((error)=>{
            toast.error("An error has occurred.")
            console.log(error)
        })
    }

    async function handleCreateReview(values){
        return createItemReview(shopId,itemId,{userId:authContext.decoded.userId,...values},position).then((response)=>{
            toast.success("Item review posted successfully!")
            setRefreshReviews(!refreshReviews)
        }).catch((error)=>{
            toast.error("An error occurred.")
            console.log(error)
        })
    }

    async function handleUpdateReview(values,reviewId){
        return updateItemReview(shopId,itemId,reviewId,values).then((response)=>{
            toast.success("Item review updated successfully!");
            setRefreshReviews(!refreshReviews)
        }).catch((error)=>{
            toast.error("An error occurred.")
            console.log(error)
        })
    }

    async function handleDeleteReview(reviewId){
        return deleteItemReview(shopId,itemId,reviewId).then((response)=>{
            toast.success("Item review deleted successfully!");
            setRefreshReviews(!refreshReviews)
        }).catch((error)=>{
            toast.error("An error occurred.")
            console.log(error)
        })
    }

    async function toggleLike(setLiked,setDisliked,review,liked,disliked){
        return toggleItemReviewLike(shopId,itemId,review.reviewId,position).then((response)=>{
          setLiked(!liked)
          setDisliked(false)
          setRefreshReviews(!refreshReviews)
        }).catch((error)=>{
          toast.error("Error liking review.")
        })
      }
    
      async function toggleDislike(setLiked,setDisliked,review,liked,disliked){
        return toggleItemReviewDislike(shopId,itemId,review.reviewId,position).then((response)=>{
          setDisliked(!disliked)
          setLiked(false)
          setRefreshReviews(!refreshReviews)
        }).catch((error)=>{
          toast.error("Error disliking review.")
        })
      }

      async function setEagleEyeOnItem(){
        return setEagleEye(shopId,itemId,position).then((response)=>{
            setRefresh(!refresh)
        }).catch((error)=>{
            console.log(error)
            toast.error(error.response.data)
        })
      }

      async function removeEagleEyeOnItem(){
        return removeEagleEye(shopId,itemId,position).then((response)=>{
            setRefresh(!refresh)
        }).catch((error)=>{
            toast.error("An error occurred")
        })
      }



    return (
        <div className="bg-white bg-opacity-25 m-4 p-2">
            <div className="d-flex flex-lg-row flex-column">
                <div className="d-flex col-12 col-lg-6 g-0">
                    <div className="d-flex align-items-center justify-content-center ">
                        <div className="card border-primary mb-3 col-10">
                            <div className="d-flex card-header align-items-center justify-content-center" >
                                <img alt="test" src={`data:${item.imageType};base64,${item.itemImageDataB64}`}  className="img-fluid rounded-start"></img> {/* adding a static image as of now*/}
                            </div>
                            <div className="card-body">
                                <h3 className="card-title">{item.itemName}</h3>
                                <p className='card-text'>{item.itemDescription}</p>
                                <div>
                                    <h5 className="card-text">Price : {item.itemPrice}</h5>
                                   {isOwner && <div>
                                        <button className='btn btn-primary' onClick={handleDeleteItem}><RiDeleteBinFill/></button>
                                        <Link className='btn btn-primary' to={`/update/shops/${shopId}/items/${itemId}`} ><AiFillEdit/></Link>
                                    </div> }
                                    <div className='d-flex gap-2'>
                                    {
                                        authContext.decoded.scope.includes("BUYER") && <div>
                                        <button className='btn btn-primary' onClick={handlePing}>Ping<img className='bg-white rounded-5 p-1 mx-1' style={{width:"2rem"}} src={PingIcon}></img></button>
                                            </div>
                                    }
                                    <Link className='btn btn-primary' to={`./pings`}>view pings<RiPushpinFill size={"2rem"} className='text-primary bg-white rounded-5 p-1 mx-1'/></Link>
                                    { authContext.decoded.scope.includes("BUYER") &&
                                         !item?.currentUserSetEagleEye && <button className='btn btn-primary' onClick={
                                            ()=>{
                                                setEagleEyeOnItem()
                                            }
                                        }>Set Eagle Eye <img className='bg-white rounded-5 p-1 mx-1' style={{width:"2rem"}} src={EagleEyeIcon}></img></button>
                                    }
                                    { authContext.decoded.scope.includes("BUYER") &&
                                         item?.currentUserSetEagleEye && <button className='btn btn-primary' onClick={
                                            ()=>{
                                                removeEagleEyeOnItem()
                                            }
                                        }>Remove Eagle Eye</button>
                                    }
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="col-12 col-lg-6">
                    <Reviews handleCreateReview={handleCreateReview} handleUpdateReview={handleUpdateReview} handleDeleteReview={handleDeleteReview} reviews={itemReviews} toggleLike={toggleLike} toggleDislike={toggleDislike} isOwner={isOwner} element={"Item"} />
                </div>
            </div>
        </div>
    )
}

export default Item
