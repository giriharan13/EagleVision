import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { getShopById } from "../../service/BackendApi";
import ShopImage from "./../../images/shop.jpg"

export default function Shop(){
    const {id} = useParams();
    const [shop,setShop] = useState({});
 
    useEffect(()=>{
        
        getShopById(id)
        .then((response)=>{refreshShop(response.data)})
        .catch((err)=>{console.log(err)});

        function refreshShop(shop){
            setShop(shop);
        }
    },[id])

    return <div>
        <h1>{shop.shopName}</h1>
        <p>{shop.description}</p>
        {shop.items?.map((item)=>{
            return <div className="card mb-3" style={{"max-width" : "540px"}} key={item.id}>
                    <div className="row g-0">
                        <div className="col-md-4" >
                            <img alt="test" src={ShopImage}  className="img-fluid rounded-start"></img> {/* adding a static image as of now*/}
                        </div>
                        <div className="col-md-8">
                            <div className="card-body">
                                <h5 className="card-title">{item.itemName}</h5>
                                <p className="card-text">Price : {item.itemPrice}</p>
                                <div>
                                    <Link className="btn btn-primary m-1"  to={`items/${item.itemId}/pings` }>View Pings</Link>
                                    <Link className="btn btn-primary m-1"  to={`items/${item.itemId}/reviews` }>View Reviews</Link>
                                </div> 
                            </div>
                        </div>
                    </div>
                </div>
        })}
    </div>
}