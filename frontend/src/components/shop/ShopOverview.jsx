import React, { useEffect, useState } from 'react'
import { GrNext } from "react-icons/gr";
import { RiDeleteBinFill } from "react-icons/ri";
import { AiFillEdit } from "react-icons/ai";
import { Link, useNavigate } from "react-router-dom";
import { deleteShop } from '../../service/BackendApi';
import toast from 'react-hot-toast';
import { useAuth } from '../../security/AuthContext';

function ShopOverview({shop,refresh,setRefresh}) {

  const navigate = useNavigate();

  const authContext = useAuth()


  return (
        <div className="d-flex flex-column bg-white rounded-4 p-2 m-2" style={{width:"17rem"}}   key={shop.shopId}>
              <div className='d-flex flex-column align-items-center justify-content-center'>
                <img className="rounded-4" src={`data:${shop.imageType};base64,${shop.shopImageData}`} alt="shop" style={{width:"15rem"}}/> {/* adding a static image as of now*/}
              </div>
              <div className="d-flex flex-column justify-content-start align-items-start px-3">
                  <h3 className="text-primary fw-bold">{shop.shopName}</h3>
                  <div className='d-flex flex-wrap'>
                  { shop.description.length<100 ? <p className="text-primary">{shop.description}</p> : <p className="text-primary text-wrap text-break">{shop.description.substring(0,100)}...</p> }
                  </div>
              </div>
              <div className='d-flex'>
                      <Link className="btn btn-primary m-1" to={`/shops/${shop.shopId}`}><GrNext/></Link>
                      { shop.vendorId === authContext.decoded.userId && <div>
                          <Link className="btn btn-primary m-1" to={`/update/shops/${shop.shopId}`}>
                          <AiFillEdit/>
                          </Link>
                          <button className='btn btn-primary m-1' onClick={()=>{
                                          deleteShop(shop.shopId).then((response)=>{
                                            setRefresh(!refresh)
                                            toast.success("Shop deleted successfully.")
                                          }).catch((error)=>{
                                            console.log(error)
                                          })
                                          }}>
                            <RiDeleteBinFill/>
                          </button>
                          <button className='btn btn-primary m-1' disabled={authContext.activeSubscription.subscriptionName==="FREE"} onClick={
                            ()=>{
                              navigate(`update/shops/${shop.shopId}/marker`)
                            }
                          }> M </button>
                          </div> }
                  </div>
        </div>
  )
}

export default ShopOverview
