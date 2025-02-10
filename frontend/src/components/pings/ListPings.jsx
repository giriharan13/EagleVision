import { useEffect, useState } from "react"
import { createVendorPing, getItemById, getOwnerId, getPingsById, replyVendorPing } from "../../service/BackendApi";
import { useParams } from "react-router-dom";
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

    const [position,setPosition] = useState(authContext?.position)


    const initialValues = {
        quantity:"0",
    }


    useEffect(()=>{
        
        getPingsById(shopId,itemId,position).then((response)=>{
            refreshPings(response.data.content)
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

    useEffect(()=>{
        setPosition(authContext?.position)
        setRefresh(!refresh)
    },[authContext?.position])

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


    return <div className="d-flex flex-column align-items-center justify-content-center">
        <h1 className="text-light my-2">Pings</h1>
    <div className="d-flex flex-column justify-content-center align-items-center">
        {(pings.length===0)?<h4>No pings on this item yet!</h4>:pings.map((ping)=>{
            return (<div className="card border-warning my-4"  key={ping.pingId}>
                <div className="card-header text-warning px-5">Buyer ping</div>
                <div className="card-body">
                    <h5 className="title px-5"> By {ping.userName}</h5>

                    {(ping.vendorResponsePing === null) ? <div className="px-5">No response yet.</div> : 
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
}