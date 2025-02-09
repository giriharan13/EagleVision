import { useEffect, useRef, useState } from "react";
import { useAuth } from "../../security/AuthContext";
import { over } from "stompjs";
import SockJS from "sockjs-client";
import { FaBell } from "react-icons/fa";
import Notification from "./DropDownNotification";
import { Link } from "react-router-dom";
import DropDownNotification from "./DropDownNotification";

export function Notifications({initialNotifications}){

    const [notifications,setNotifications] = useState(initialNotifications)

    const token = localStorage.getItem('JWTToken'); 

    const authContext = useAuth();

    const stompClientRef = useRef(null)

    useEffect(()=>{
            let Sock = new SockJS(`http://localhost:8080/ws?token=${token}`)

            stompClientRef.current = over(Sock)

            stompClientRef.current.onStompError = (frame) => {
                console.error("STOMP error: ", frame);
            };

            stompClientRef.current.connect({
                
            },()=>{
                console.log("connected");
                stompClientRef.current.subscribe(`/user/queue/notifications`, (message) => {
                    console.log("Received: " + message.body);
                    setNotifications(notifications=>[JSON.parse(message.body),...notifications])
                });
            },(error)=>{
                console.log("error"+error)
        })},[])

        useEffect(()=>{
            setNotifications(initialNotifications)
        },[initialNotifications])

    

    // const client = new Client(
    //     {
    //         brokerURL:"ws://localhost:8080/",
    //         connectHeaders: {
    //         Authorization: `Bearer ${token}`, // Include Bearer token in headers
    //         },
    //         onConnect: () => {
    //         console.log('Connected to WebSocket');
    //         },
    //         onStompError: (frame) => {
    //         console.error('Broker reported error: ', frame.headers['message']);
    //         },
    //         onDisconnect: () => {
    //         console.log('Disconnected from WebSocket');
    //         },
    // }
    // )



    return <div className="btn-group dropstart">
        <button class="btn btn-primary dropdown-toggle d-flex align-items-center" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
            <div className="d-flex">
            <FaBell/> 
            {
                notifications.length>0 && <div className="d-flex bg-danger px-1 rounded-circle">{notifications.length}</div>
            }
            </div>
        </button>
        <ul className="dropdown-menu" aria-labelledby="dropdownMenuLink">
            {notifications.length===0 && <li><a class="dropdown-item text-muted" href="#">No new notifications</a></li>}
            {
                notifications.slice(0,8).map((notification,id)=>{
                    return <DropDownNotification key={id} message={notification}/>
                })
            }
            <li><Link className="dropdown-item link-primary" to="/notifications">view all</Link></li>
        </ul>
    </div>

}