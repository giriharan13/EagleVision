import { useState } from "react";
import { getShops } from "../../service/BackendApi";
import { useEffect } from "react";
import "../../../node_modules/bootstrap/dist/css/bootstrap.min.css"
import ShopImage from "./../../images/shop.jpg"
import { Link } from "react-router-dom";

export default function ListShops(){
    const [shops,setShops] = useState([]);

    useEffect(()=>{
        getShops().then(
            (response)=>{refreshShops(response.data)}
        ).catch(
            (error)=>{console.log(error)}
        );

        function refreshShops(shops){
            setShops(shops)
        }
    },[])


    return <div className="shops">
        <div className="d-flex flex-wrap">
        { shops.map((shop)=>{
           return (<div className="card m-2" style={{width:"18rem"}}  key={shop.shopId}>
                      <img src={ShopImage} alt="shop"/> {/* adding a static image as of now*/}
                      <div className="card-body">
                        <h4 className="card-title">{shop.shopName}</h4>
                         { shop.description.length<100 ? <p className="card-text">{shop.description}</p> : <p className="card-text">{shop.description.substring(0,100)}...</p> }
                        <Link className="btn btn-primary" to={`/shops/${shop.shopId}`}>View</Link>
                      </div>
                  </div>);
        }) }
        </div>
    </div>
}