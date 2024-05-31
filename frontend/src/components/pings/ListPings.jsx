import { useEffect, useState } from "react"
import { getPingsById } from "../../service/BackendApi";
import { useParams } from "react-router-dom";


export default function ListPings(){

    const [pings,setPings] = useState([]);

    const {shopId,id} = useParams();

    useEffect(()=>{
        
        getPingsById(shopId,id).then((response)=>{
            refreshPings(response.data)
        }).catch((err)=>{
            console.log(err);
        })


        function  refreshPings(pings){
            setPings(pings);
        }
    },[id,shopId])


    return <div>
    {pings.map((ping)=>{
        return (<div key={ping.pingId}>
            <h1>{ping.pingId}</h1>
        </div>);
    })}
    </div>
}