import React from 'react'
import ShopImage from "./../../images/shop.jpg";
import { Link } from 'react-router-dom';
import "./Item.scss"
import { GrLinkNext } from "react-icons/gr";

function ItemOverview({item}) {
  return (
    <div className="item-card" key={item.id}>
            <div className='item-section'>
                <img alt={item.name} src={ShopImage}  className="item-image"></img> {/* adding a static image as of now*/}
            </div>
            <div className="item-section">
                <div className="item-section-col-start">
                    <h5 className="item-name">{item.itemName}</h5>
                    <p className="item-price">Price : {item.itemPrice}</p>
                </div>
                <div className='item-section-col-end'>
                    <Link className="button-primary"  to={`items/${item.itemId}` }><GrLinkNext/></Link>
                </div> 
            </div>
    </div>
  )
}

export default ItemOverview
