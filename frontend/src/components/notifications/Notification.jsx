import toast from "react-hot-toast"
import { markNotificationAsRead } from "../../service/BackendApi"


export default function Notification({message,refresh,setRefresh}){


  const date = new Date(message.creationDate)




  function markAsRead(id){
    markNotificationAsRead(id).then((response)=>{
      toast.success("Marked notification as read!")
      setRefresh(!refresh)
    }).catch((error)=>{
      toast.error("Error marking notification as read!")
    })
  }

    return <div href="#" className="bg-white w-50 m-2 p-2 rounded-4 flex-column align-items-start text-primary">
    <div className="d-flex w-100 justify-content-between">
      <h5 className="fw-bold">{message.title}</h5>
      <small>{message.creationDate!==null?`${date.getDate()}/${date.getMonth()}/${date.getFullYear()}`:""}</small>
    </div>
    <div className="d-flex justify-content-between align-items-end">
      <p className="text-break">{message.content}</p>
      {!message.isRead && <button onClick={()=>{
        markAsRead(message.notificationId)
      }} className="btn btn-primary">Mark as Read</button>}
    </div>
  </div>
}