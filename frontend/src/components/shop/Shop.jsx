import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import { createShopReview, deleteShopReview, getOwnerId, getShopById, updateShopReview } from "../../service/BackendApi";
import ShopImage from "./../../images/shop.jpg";
import "./Shop.scss";
import { BsFillTelephoneFill } from "react-icons/bs";
import { FaMapLocationDot } from "react-icons/fa6";
import { FaClock } from "react-icons/fa";
import ItemOverview from "../item/ItemOverview";
import { useAuth } from "../../security/AuthContext";
import { Field, Form, Formik } from "formik";
import toast from "react-hot-toast";
import Review from "../reviews/Review";
import StarField from "../starField/StarField";
import Reviews from "../reviews/Reviews";


export default function Shop(){
    const {shopId} = useParams();
    const [shop,setShop] = useState({});
    const [isOwner,setIsOwner] = useState(false);

    const [refresh,setRefresh] = useState(false);


    const authContext = useAuth();
    
 
    useEffect(()=>{

        getOwnerId(shopId).then((response)=>{
            setIsOwner(authContext.decoded.userId === response.data);
        }).catch((err)=>{
            console.log(err);
            setIsOwner(false);
        })
        
        getShopById(shopId)
        .then((response)=>{refreshShop(response.data)})
        .catch((err)=>{console.log(err)});


        function refreshShop(shop){
            setShop(shop);
        }
    },[shopId,refresh])


    async function handleCreateShopReview(values){
        return createShopReview(shopId,{userId:authContext.decoded.userId,...values}).then(
            (response)=>{
                setRefresh(!refresh)
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
          setRefresh(!refresh)
        }).catch((error)=>{
          toast.error("An error occurred");
          console.log(error);
        });  
      }
    
      async function handleDeleteShopReview(reviewId){
        return deleteShopReview(shopId,reviewId).then((response)=>{
          toast.success("Review deleted successfully!");
          setRefresh(!refresh)
        }).catch((error)=>{
          toast.error("An error occurred");
          console.log(error);
        }); 
      }

    return <div>
        <div className="shop-hero">
            <div className="shop-info">
                <h1 className="shop-name">{shop.shopName}</h1>
                <p className="shop-description">{shop.description}</p>
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
            </div>
            <div>
                <img className="shop-image" src={ShopImage} alt="shop"/> 
            </div>
        </div>
        <div className="shop-items">
        <div className="heading">
            <h2>Our Items</h2>
            {isOwner && <Link to="./create/item" className="button-primary"> Create </Link>}
        </div>
        <div className="items">
                {shop.items?.length===0 && <div>No items available.</div>}
                {shop.items?.map((item)=>{
                return <ItemOverview item={item}/>
            })}
            </div>
        </div>
        <Reviews handleCreateReview={handleCreateShopReview} handleUpdateReview={handleUpdateShopReview} handleDeleteReview={handleDeleteShopReview}  reviews={shop.shopReviews} refresh={refresh} setRefresh={setRefresh} isOwner={isOwner} element={"Shop"}/>
    </div>
}