import { useEffect, useState } from "react"
import { getAllNotifications, markAllNotificationsAsRead } from "../../service/BackendApi"
import Notification from "./Notification"
import toast from "react-hot-toast"


export default function AllNotifications({setBellNotifications,setRefreshBellNotifications,refreshBellNotifications}){

    const [notifications,setNotifications] = useState([])

    const [refresh,setRefresh] = useState(false)

    const [isLoading,setIsLoading] = useState(true)

    useEffect(()=>{
        getAllNotifications().then(
            (response)=>{
                setNotifications(response.data.reverse())
                setIsLoading(false)
                setRefreshBellNotifications(!refreshBellNotifications)
            }
        ).catch((error)=>{
            console.log(error)
        })
    },[refresh])


    function markAllAsRead(){
        markAllNotificationsAsRead().then(
            ()=>{
                toast.success("Marked all notifications as read!")
                setRefresh(!refresh)
            }
        ).catch(
            ()=>{
                toast.error("Error marking all notifications as read!")
            }
        )
    }

    return <div className="bg-white bg-opacity-25 p-2 rounded-4 m-4">
        <div className="text-center h1 my-4">
            <h2 className="text-light fw-bold">All Notifications </h2>
            </div>
        {isLoading && <div>Loading...</div>}
        <div className="d-flex flex-column jusity-content-center align-items-center">
        <div className="d-flex">
            <button className="btn btn-primary" onClick={()=>{markAllAsRead()}}>Mark all as Read</button>
        </div>
        {notifications.map((notification,id)=>{
            return <Notification key={id} message={notification} refresh={refresh} setRefresh={setRefresh}/>
        }
        )}
        </div> 
    </div>

}