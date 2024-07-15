import { useState } from "react";
import { useEffect } from "react";
// import "../../../node_modules/bootstrap/dist/css/bootstrap.min.css"
import ShopImage from "./../../images/shop.jpg"
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../../security/AuthContext";
import {  getShopsByVendor } from "../../service/BackendApi";
import ShopOverview from "./ShopOverview";
import "./Shop.scss";
import { RiDeleteBinFill } from "react-icons/ri";

export default function ListMyShops(){
    const [shops,setShops] = useState([]);

    const authContext = useAuth();

    const [refresh,setRefresh] = useState(false);

    const navigate = useNavigate();

    function refreshShops(shops){
        setShops(shops)
    }

    useEffect(()=>{
        getShopsByVendor(authContext.decoded.userId).then(
            (response)=>{refreshShops(response.data)}
        ).catch(
            (error)=>{console.log(error)}
        );
    },[authContext.decoded.userId,refresh])


    return <div className="list-shops">
            {shops.length>0 && shops.map((shop)=>{
            return (<ShopOverview shop={shop} setRefresh={setRefresh} refresh={refresh}/>);
            }) }
            {
                shops.length===0 && <div className="col d-flex flex-wrap mx-5">You have no shops.</div>
            }
            <div className="others">
                <button className="button-primary" onClick={()=>navigate("/create/shop")}>
                        Create
                </button>
            </div>
        </div>
}