import { useEffect, useRef, useState } from "react";
import { Circle, MapContainer,TileLayer, useMap } from "react-leaflet"
import L, { Icon } from "leaflet"
import "leaflet/dist/leaflet.css";
import { getShops, getShops2 } from "../../service/BackendApi";
import ShopMarker from "./ShopMarker";
import { useAuth } from "../../security/AuthContext";
import { shopCategories } from "../../config/ShopCategories";
import toast from "react-hot-toast";


function SetViewOnChange({position}){
    const map = useMap();
    map.setView(position,map.getZoom())
    return null;
}


export default function EagleVision(){
    const mapRef = useRef(null);
    const authContext = useAuth();
    const [shops,setShops] = useState([])
    const [position,setPosition]  = useState([0,0])
    const [radius,setRadius] = useState(authContext?.activeSubscription.subscriptionName==="GOD"?2147483647:authContext?.activeSubscription.subscriptionName==="ACE"?140000:60000)

    const [query,setQuery] = useState("")
    const [category,setCategory] = useState("ALL");

    const [refresh,setRefresh] = useState(false);


    useEffect(
        ()=>{
            getShops2(query,category,position[0],position[1]).then((response)=>{
                setShops(response.data.content)
            }).catch(
                (error)=>{console.log(error)}
            );
        },[position,refresh]
    )

    useEffect(
        ()=>{
            if(authContext?.position){
                setPosition(authContext?.position)
            }
        },[authContext?.position]
    )

    return <div className="d-flex justify-content-center align-items-center flex-column">
    <div className="row justify-content-center align-items-center bg-transparent">
        <div className="d-flex justify-content-center align-items-center m-2">
            <h3 className="text-center fw-bold h2 text-light" style={{fontFamily:"Kanit"}}>Vision of the Eagle</h3>
        </div>
        <div className="d-flex justify-content-center align-items-center m-2">
            <div className="col-4 m-2">
                <input className="form-control" onChange={(event)=>{
                    setQuery(event.target.value);
                }} placeholder="Search"></input>
            </div>
            <div className="col-2 m-2">
                <select className="form-select" onChange={(event)=>{
                    setCategory(event.target.value);
                }}>
                    <option value="ALL">All</option>
                {
                    shopCategories.map((category,idx)=>{
                        return (<option value={category.type} key={idx}>{category.display}</option>)
                    })
                }
                </select>
            </div>
            <div className="m-2">
                <button className="btn btn-success" onClick={()=>{
                    setRefresh(!refresh)
                }}>Apply</button>
            </div>
        </div>
    </div>
    <div className="row align-items-center justify-content-center mb-5">
        <MapContainer  center={position} zoom={10} scrollWheelZoom={false} style={{height:600,width:1000,borderRadius:20} }ref={mapRef} removeOutsideVisibleBounds={false}>
        <TileLayer
        attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        />
        {
            shops.map((shop,index)=>{
                return <ShopMarker shop={shop} key={index}/>

            })
        }
        <Circle center={position} radius={radius} >
        </Circle>
        <SetViewOnChange position={position}/>
        </MapContainer>
    </div>
    </div>
}