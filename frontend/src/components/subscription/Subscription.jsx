import { useAuth } from "../../security/AuthContext"
import "./Subscription.css"
import { BuyerSubscriptions,order,VendorSubscriptions } from "../../config/Subscription"
import toast from "react-hot-toast"
import { subscribeToPlan } from "../../service/BackendApi"
import { useNavigate } from "react-router-dom"


export default function Subscription(){


    const authContext = useAuth()

    const navigate = useNavigate()


    async function subscribe(subscriptionName){
        subscribeToPlan(subscriptionName).then((response)=>{
        toast.success(`Succesfully subscribed to ${subscriptionName}`)
    }).catch((error)=>{
        toast.error("An error occurred.")
    })
    }



    return <div className="bg-white bg-opacity-25 p-4 m-4 rounded-4">
    <div className="d-flex text-light justify-content-between m-4">
            <div>
			    <h2 className="fw-bold">Subscription Plans</h2>
            </div>
            <button className="btn btn-primary" onClick={()=>{
                navigate("/subscription/history")
            }}>My Subscriptions</button>
		</div>		
    {
        authContext?.decoded.scope.includes("BUYER") && <div>
            <div className="container">							
		    <div className="row text-center">									
                { BuyerSubscriptions.map((subscription,id)=>{
                    return <div className="col-lg-4 col-sm-6 col-xs-12">
                        
                            <div className="bg-light text-primary p-5 rounded-5" key={id}>
                            <div className="">
                                <div className="">		
                                    <h2>{subscription.name} Tier</h2>
                                    <h1>{subscription.cost}₹</h1>
                                    <span>/Monthly</span>
                                </div>
                                <ul className="list-unstyled row p-4 align-items-center justify-content-center">
                                    {subscription.features.map(
                                        (feature,id)=>{
                                            return  <li key={id}>{feature}</li>
                                        }
                                    )}
                                </ul>
                                <div className="">
                                </div>
                                <button className="btn btn-primary" disabled={order[authContext?.activeSubscription.subscriptionName]>=order[subscription.name]} 
                                    onClick={()=>{
                                        subscribe(subscription.name)
                                    }}
                                    >{order[authContext?.activeSubscription.subscriptionName]<order[subscription.name]?"Upgrade":order[authContext?.activeSubscription.subscriptionName]===order[subscription.name]?"Current":"Not Upgradable"}</button>
                            </div>
                        </div>
                </div>})
        }
    </div>
    </div>
    </div>
}
    {
        authContext?.decoded.scope.includes("VENDOR") && <div>
            <div className="container">							
		    <div className="row text-center">									
                { VendorSubscriptions.map((subscription,id)=>{
                    return <div className="col-lg-4 col-sm-6 col-xs-12" >
                        
                            <div className="bg-light text-primary p-5 rounded-5" key={id}>
                            <div className="">
                                <div className="">		
                                    <h2>{subscription.name} Tier</h2>
                                    <h1>{subscription.cost}₹</h1>
                                    <span>/Monthly</span>
                                </div>
                                <ul className="list-unstyled row p-4 align-items-center justify-content-center">
                                    {subscription.features.map(
                                        (feature,id)=>{
                                            return  <li key={id}>{feature}</li>
                                        }
                                    )}
                                </ul>
                                <button className="btn btn-primary" disabled={order[authContext?.activeSubscription.subscriptionName]>=order[subscription.name]} 
                                    onClick={()=>{
                                        subscribe(subscription.name)
                                    }}
                                    >{order[authContext?.activeSubscription.subscriptionName]<order[subscription.name]?"Upgrade":order[authContext?.activeSubscription.subscriptionName]===order[subscription.name]?"Current":"Not Available"}</button>
                            </div>
                        </div>
                </div>})
        }
    </div>
    </div>
        </div>
    }
    </div>
}