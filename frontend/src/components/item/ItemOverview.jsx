import React from 'react'
import ShopImage from "./../../images/shop.jpg";
import { Link, useLocation, useNavigate } from 'react-router-dom';
import "./Item.scss"
import { GrLinkNext } from "react-icons/gr";

function ItemOverview({item}) {

  const navigate = useNavigate()

  const location =  useLocation();

  return (
    <div className="item-card bg-white" key={item.id}>
            <div className='item-section'>
                <img alt={item.name} src={`data:${item.imageType};base64,${item.itemImageDataB64}`}  className="item-image"></img> {/* adding a static image as of now*/}
                {location.pathname==="/items" && <img alt={item.name} src={`data:${item.shopImageType};base64,${item.shopImageDataB64}`}  className="shop-image"></img>}
            </div>
            <div className="item-section">
                <div className="item-section-col-start">
                    <h5 className="item-name">{item.itemName}</h5>
                    <p className="item-price">Price : {item.itemPrice}</p>
                </div>
                <div className='item-section-col-end'>
                   {location.pathname==='/items' && <button className="button-primary" onClick={
                      ()=>{
                        navigate(`/shops/${item.shopId}`)
                      }
                    } >  Go to Shop <GrLinkNext/></button>
                    }
                    {
                      location.pathname!=='/items' && <Link className='button-primary' to={`./items/${item.itemId}`}><GrLinkNext/></Link>  
                    }
                </div> 
            </div>
    </div>
  )
}

export default ItemOverview
