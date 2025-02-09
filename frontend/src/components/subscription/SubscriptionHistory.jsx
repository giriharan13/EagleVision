import { useEffect, useState } from "react"
import { getAllSubscriptions } from "../../service/BackendApi"
import toast from "react-hot-toast"



export default function SubscriptionHistory(){


    const [subscriptions,setSubscriptions]=useState([])

    useEffect(
        ()=>{
            getAllSubscriptions().then(
                (response)=>{
                    setSubscriptions(response.data.content)
                }
            ).catch((error)=>{
                toast.error("An error occurred.")
            })
        },[]
    )

    return <>
    <div className="container my-5">
    <div className="text-center mb-5">
      <h2 className="fw-bold text-light">Subscription History</h2>
    </div>
    {
      subscriptions.length===0 && <div className="d-flex align-items-center justify-content-center">
        <h5 className="text-light">You have no subscriptions!</h5>
      </div>
    }
    {subscriptions.map((subscription,id)=><div className={`card mb-3 bg-white ${new Date(subscription.subscriptionEndDateTime)<new Date()?"bg-opacity-25":"bg-opacity-100"}`} key={id}>
      <div className="card-body">
        <div className="d-flex flex-column flex-lg-row">
          <div className="row flex-fill">
            <div className="col-sm-5">
              <h4 className="h5">{subscription.subscriptionName}</h4>
              { new Date(subscription.subscriptionEndDateTime)>=new Date() && <span className="badge bg-secondary">Expires {subscription.subscriptionEndDateTime}</span>}
              { new Date(subscription.subscriptionEndDateTime)<new Date() && <span className="badge bg-danger">Expired {subscription.subscriptionEndDateTime}</span>}
            </div>


          </div>
        </div>
      </div>
    </div>)}
    
  </div>
    </>
}