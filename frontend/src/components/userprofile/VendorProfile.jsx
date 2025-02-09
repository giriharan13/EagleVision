import { useEffect, useState } from "react"
import { getRespondedPingsByVendor, getShopsByVendor } from "../../service/BackendApi"
import { useParams } from "react-router-dom"
import toast from "react-hot-toast"
import { useAuth } from "../../security/AuthContext"
import ShopOverview from "../shop/ShopOverview"
import Ping from "../pings/Ping"


export default function VendorProfile(){


    const [shops,setShops] = useState([])

    const [refreshShops,setRefreshShops] = useState(false)

    const [pings,setPings] = useState([])

    const [refreshPings,setRefreshPings] = useState(false)

    const {userId} = useParams()

    const authContext = useAuth()

    const [position,setPosition] = useState(authContext?.position)

    const [type,setType] = useState("SHOPS")

    useEffect(()=>{
        setPosition(authContext?.position)
        setRefreshShops(!refreshShops)
        setRefreshPings(!refreshPings)
    },[authContext?.position])


    useEffect(()=>{
        getShopsByVendor(userId,0,5,position,"shopName","").then((response)=>{
            setShops(response.data.content)
        }).catch((error)=>{
            toast.error("Error fetching shops!")
            console.log(error)
        })
    },[refreshShops,position])

    useEffect(
        ()=>{
            getRespondedPingsByVendor(userId,position).then((response)=>{
                setPings(response.data.content)
            }).catch((error)=>{
                toast.error("Error fetching response pings by vendor.")
                console.log(error)
            })
        },[refreshPings,position]
    )







    return <div>
        <button className={`btn ${type==="SHOPS"?"btn-light":"btn-primary"} m-2`} onClick={
            ()=>{
                setRefreshShops(!refreshShops)
                setType("SHOPS")
            }
        }>Shops</button>
        <button className={`btn ${type==="PINGS"?"btn-light":"btn-primary"} m-2`} onClick={
            ()=>{
                setRefreshPings(!refreshPings)
                setType("PINGS")
            }
        }>Response Pings</button>

        <div>
            {
                type==="SHOPS" && <div className="d-flex gap-2">
                    {
                        shops.map((shop,index)=>{
                            return <ShopOverview shop={shop} key={index}/>
                        })
                    }
                    {shops.length===0 && <div>
                        <h3 className="text-light">No Shops created!</h3>
                        </div>
                    }
                </div>
            }
            {
                type==="PINGS" && <div>
                    {
                        pings.map((ping,id)=>{
                            return <Ping ping={ping} key={id}/>
                        })
                    }
                    {pings.length===0 && <div>
                        <h3 className="text-light">No Response Pings created!</h3>
                        </div>
                    }
                </div>
            }
        </div>
         


    </div>
}