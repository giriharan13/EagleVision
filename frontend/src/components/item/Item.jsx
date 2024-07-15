import React, { useEffect, useState } from 'react'
import { Link, useNavigate, useParams } from 'react-router-dom'
import { createBuyerPing, createItemReview, deleteItem, deleteItemReview, getItemById, getOwnerId, updateItemReview } from '../../service/BackendApi';
import toast from 'react-hot-toast';
import ShopImage from "./../../images/shop.jpg"

import { AiFillEdit } from "react-icons/ai";
import { RiDeleteBinFill,RiPushpinFill } from "react-icons/ri";
import { FaQuestion } from "react-icons/fa";
import { useAuth } from '../../security/AuthContext';
import Reviews from '../reviews/Reviews';

function Item() {

    const {shopId,itemId} = useParams();

    const navigate = useNavigate();

    const [item,setItem] = useState({});

    const [refresh,setRefresh] = useState(false)

    const [isOwner,setIsOwner] = useState(false)

    const authContext = useAuth();

    useEffect(()=>{

        getOwnerId(shopId).then((response)=>{
            setIsOwner(authContext.decoded.userId === response.data);
        }).catch((err)=>{
            console.log(err);
            setIsOwner(false);
        })

        getItemById(shopId,itemId).then((response)=>{
            setItem(response.data)
        }).catch((error)=>{
            toast.error("An error occurred")
            console.log(error)
        })


    },[refresh])

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
        }).then((response)=>{
            toast.success("Item pinged successfully.")
        }).catch((error)=>{
            toast.error("An error has occurred.")
            console.log(error)
        })
    }

    async function handleCreateReview(values){
        return createItemReview(shopId,itemId,{userId:authContext.decoded.userId,...values}).then((response)=>{
            toast.success("Item review posted successfully!")
            setRefresh(!refresh)
        }).catch((error)=>{
            toast.error("An error occurred.")
            console.log(error)
        })
    }

    async function handleUpdateReview(values,reviewId){
        return updateItemReview(shopId,itemId,reviewId,values).then((response)=>{
            toast.success("Item review updated successfully!");
            setRefresh(!refresh)
        }).catch((error)=>{
            toast.error("An error occurred.")
            console.log(error)
        })
    }

    async function handleDeleteReview(reviewId){
        return deleteItemReview(shopId,itemId,reviewId).then((response)=>{
            toast.success("Item review deleted successfully!");
            setRefresh(!refresh)
        }).catch((error)=>{
            toast.error("An error occurred.")
            console.log(error)
        })
    }



    return (
        <div className="container">
            <div className="row">
                <div className="col-6 g-0">
                    <div className="container">
                        <div className="card border-primary mb3">
                            <div className="card-header" >
                                <img alt="test" src={ShopImage}  className="img-fluid rounded-start"></img> {/* adding a static image as of now*/}
                            </div>
                            <div className="card-body">
                                <h3 className="card-title">{item.itemName}</h3>
                                <p className='card-text'>{item.itemDescription}</p>
                                <div>
                                    <h5 className="card-text">Price : {item.itemPrice}</h5>
                                    {authContext.decoded.userId===item.vendorId && <div>
                                        <button className='button-primary' onClick={handleDeleteItem}><RiDeleteBinFill/></button>
                                        <Link className='button-primary' to={`/update/shops/${shopId}/items/${itemId}`} ><AiFillEdit/></Link>
                                    </div>}

                                    {
                                        authContext.decoded.scope.includes("BUYER") && <div>
                                        <button className='button-primary' onClick={handlePing}>ping<FaQuestion/></button>
                                            </div>
                                    }
                                    <Link className='button-primary' to={`./pings`}>view pings<RiPushpinFill/></Link>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="col-6">
                    <Reviews handleCreateReview={handleCreateReview} handleUpdateReview={handleUpdateReview} handleDeleteReview={handleDeleteReview} reviews={item.itemReviews} refresh={refresh} setRefresh={setRefresh} isOwner={isOwner} element={"Item"}/>
                </div>
            </div>
        </div>
    )
}

export default Item
