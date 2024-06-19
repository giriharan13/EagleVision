import { useEffect, useState } from "react"
import { getItemById, getPingsById } from "../../service/BackendApi";
import { useParams } from "react-router-dom";
import ShopImage from "./../../images/shop.jpg"


export default function ListPings(){

    const [pings,setPings] = useState([]);

    const [item,setItem] = useState([]) ;

    const {shopId,id} = useParams();


    useEffect(()=>{
        
        getPingsById(shopId,id).then((response)=>{
            refreshPings(response.data)
        }).catch((err)=>{
            console.log(err);
        })

        getItemById(shopId,id).then((response)=>{
            refreshItem(response.data);
        }).catch((err)=>{
            console.log(err);
        })


        function  refreshPings(pings){
            setPings(pings);
        }

        function refreshItem(item){
            setItem(item);
        }
    },[id,shopId])


    return <div className="container">
    <div className="row">
        <div className="col-6">
        {(pings.length===0)?<h4>No pings on this item yet!</h4>:pings.map((ping)=>{
            return (<div className={(ping.type===0)?"card border-warning mb-3":"card text-bg-success mb-3"} style={{maxWidth:"40rem"}} key={ping.pingId}>
                <div className={"card-header"+((ping.type===0)?" text-warning":"")}>{(ping.type===0)?"Buyer ping":"Vendor Ping"}</div>
                <div className="card-body">
                    <h5 className="title"> By {ping.userName}</h5>
                    <p className="card-text">{(ping.type===1) && "Quantity Available:"+ping.quantity}</p>
                </div>
            </div>);
        }).reverse()}
        </div>
        <div className="col-6 g-0">
            <div className="container">
                <div className="card border-primary mb3">
                    <div className="card-header" >
                        <img alt="test" src={ShopImage}  className="img-fluid rounded-start"></img> {/* adding a static image as of now*/}
                    </div>
                    <div className="card-body">
                        <h5 className="card-title">{item.itemName}</h5>
                        <p className="card-text">Price : {item.itemPrice}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </div>
}