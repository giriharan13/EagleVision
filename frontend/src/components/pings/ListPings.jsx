import { useEffect, useState } from "react"
import { createVendorPing, getItemById, getOwnerId, getPingsById } from "../../service/BackendApi";
import { useParams } from "react-router-dom";
import ShopImage from "./../../images/shop.jpg"
import { useAuth } from "../../security/AuthContext";
import { Field, Form, Formik } from "formik";
import toast from "react-hot-toast";


export default function ListPings(){

    const [pings,setPings] = useState([]);

    const [item,setItem] = useState([]) ;

    const {shopId,itemId} = useParams();

    const [isOwner,setIsOwner] = useState(false)

    const [refresh,setRefresh] = useState(false)

    const authContext = useAuth();


    const initialValues = {
        quantity:"0",
    }


    useEffect(()=>{
        
        getPingsById(shopId,itemId).then((response)=>{
            refreshPings(response.data)
        }).catch((err)=>{
            console.log(err);
        })

        getItemById(shopId,itemId).then((response)=>{
            refreshItem(response.data);
        }).catch((err)=>{
            console.log(err);
        })

        getOwnerId(shopId).then((response)=>{
            if(authContext.decoded.userId=== response.data) setIsOwner(true)
        }).catch((error)=>{
            console.log(error)
        })


        function  refreshPings(pings){
            setPings(pings);
        }

        function refreshItem(item){
            setItem(item);
        }
    },[itemId,shopId,refresh])

    async function handleReplyPing(pingId,values){
        return createVendorPing(shopId,itemId,pingId,{
            quantity:values.quantity,
            userId:authContext.decoded.userId
        }).then((response)=>{
            setRefresh(!refresh)
            toast.success("Response ping created successfully.");
        }).catch((error)=>{
            toast.error("An error has occurred");
            console.log(error);
        })
    }


    return <div className="container">
    <div className="row">
        <div className="col-6">
        {(pings.length===0)?<h4>No pings on this item yet!</h4>:pings.map((ping)=>{
            return (<div className="card border-warning mb-3" style={{maxWidth:"40rem"}} key={ping.pingId}>
                <div className="card-header text-warning">Buyer ping</div>
                <div className="card-body">
                    <h5 className="title"> By {ping.userName}</h5>

                    {(ping.vendorResponsePing === null) ? <div>No response yet.</div> : 
                    <div className="card border-success mb-3" style={{maxWidth:"40rem"}} >
                        <div className="card-header">Vendor ping</div>
                        <div className="card-body">
                            <h5>Quantity : {ping.vendorResponsePing.quantity}</h5>
                        </div>
                    </div> }
                    
                </div>
                {isOwner && ping.vendorResponsePing === null && <Formik initialValues={initialValues} enableReinitialize onSubmit={(values)=>handleReplyPing(ping.pingId,values)}>
                                <Form>
                                    <Field type="text" name="quantity"/>
                                    <button className="button-primary" type="submit">reply</button>
                                </Form>
                            </Formik>}
            </div>);
        }).reverse()}
        </div>
    </div>
    </div>
}