import { useEffect, useState } from "react"
import { createVendorPing, getItemById, getOwnerId, getPingsById, getPingsOnVendor, replyVendorPing } from "../../service/BackendApi";
import { useParams } from "react-router-dom";
import ShopImage from "./../../images/shop.jpg"
import { useAuth } from "../../security/AuthContext";
import { Field, Form, Formik } from "formik";
import toast from "react-hot-toast";


export default function ListPingsForVendor(){

    const [pings,setPings] = useState([]);


    const [isOwner,setIsOwner] = useState(false)

    const [refresh,setRefresh] = useState(false)

    const [filter,setFilter] = useState("all")

    const [item,setItem] = useState(false)

    const authContext = useAuth();

    const [position,setPosition] = useState(authContext?.position)


    useEffect(()=>{
        setPosition(authContext?.position)
    },[authContext?.position])


    const initialValues = {
        quantity:"0",
    }


    useEffect(()=>{
        
        getPingsOnVendor(authContext.decoded.userId,position).then((response)=>{
            refreshPings(response.data.content)
        }).catch((err)=>{
            console.log(err);
        })


        function  refreshPings(pings){
            setPings(pings);
        }

        function refreshItem(item){
            setItem(item);
        }
    },[refresh])

    async function handleReplyPing(shopId,itemId,pingId,values){
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
    <div className="row text-center">
        <h2 className="fw-bold text-light">Pings </h2>
    </div>
    <div className="row">
        <div className="d-flex">
            <button className={`btn btn-${(filter==="all")?"light":"primary"} m-2 border-light`} onClick={
                ()=>{
                    setFilter("all")
                }
            }>All</button>
            <button className={`btn btn-${(filter==="replied")?"light":"primary"} m-2 border-light`} onClick={
                ()=>{
                    setFilter("replied")
                }
            }>Replied</button>
            <button className={`btn btn-${(filter==="notreplied")?"light":"primary"} border-light m-2`} onClick={
                ()=>{
                    setFilter("notreplied")
                }
            }>Not Replied</button>
        </div>
    </div>
    <div className="row">
        <div className="d-flex flex-column">
        {(pings.length===0)?<h4>No pings!</h4>:pings.map((ping)=>{
            return ((filter==="all" || (filter==="replied" && ping.vendorResponsePing!==null) || (filter==="notreplied" && ping.vendorResponsePing===null)) && <div className="d-flex mb-3 flex-column bg-white rounded-4 p-4 align-items-start"  key={ping.pingId}>
                <div className="text-warning fw-bold">Buyer ping</div>
                <div className="d-flex flex-column">
                    <h6 className="title"> By {ping.userName}</h6>

                    {(ping.vendorResponsePing === null) ? <div>No response yet.</div> : 
                    <div className="d-flex flex-column border border-success mb-3 rounded-4 p-2">
                        <div className="text-success fw-bold">Vendor ping</div>
                        <div className="card-body">
                            <h6>Quantity : {ping.vendorResponsePing.quantity}</h6>
                        </div>
                    </div> }
                    
                </div>
                {ping.vendorResponsePing === null && <Formik initialValues={initialValues} enableReinitialize onSubmit={(values)=>handleReplyPing(ping.shopId,ping.itemId,ping.pingId,values)}>
                                <Form>
                                    <div className="d-flex gap-2">
                                        <Field type="text" name="quantity" className="form-control"/>
                                        <button className="btn btn-primary" type="submit">reply</button>
                                    </div>
                                </Form>
                            </Formik>}
            </div>) ;
        }).reverse()}
        </div>
    </div>
    </div>
}