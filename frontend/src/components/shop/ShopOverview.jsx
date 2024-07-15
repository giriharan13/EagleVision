import React, { useEffect, useState } from 'react'
import { GrNext } from "react-icons/gr";
import { RiDeleteBinFill } from "react-icons/ri";
import { AiFillEdit } from "react-icons/ai";
import ShopImage from "./../../images/shop.jpg"
import { Link, useNavigate } from "react-router-dom";
import { deleteShop } from '../../service/BackendApi';
import toast from 'react-hot-toast';

function ShopOverview({shop,refresh,setRefresh}) {

  const navigate = useNavigate();


  return (
      <div className="card m-2" style={{width:"18rem"}}  key={shop.shopId}>
            <img src={ShopImage} alt="shop"/> {/* adding a static image as of now*/}
            <div className="card-body">
                <h4 className="card-title">{shop.shopName}</h4>
                { shop.description.length<100 ? <p className="card-text">{shop.description}</p> : <p className="card-text">{shop.description.substring(0,100)}...</p> }
                <div>
                  <Link className="button-primary" to={`/shops/${shop.shopId}`}><GrNext/></Link>
                    <Link className="button-primary" to={`/update/shops/${shop.shopId}`}>
                    <AiFillEdit/>
                    </Link>
                    <button className='button-primary' onClick={()=>{
                                    deleteShop(shop.shopId).then((response)=>{
                                      setRefresh(!refresh)
                                      toast.success("Shop deleted successfully.")
                                    }).catch((error)=>{
                                      console.log(error)
                                    })
                                    }}>
                      <RiDeleteBinFill/>
                    </button>
                </div>
            </div>
      </div>
  )
}

export default ShopOverview
