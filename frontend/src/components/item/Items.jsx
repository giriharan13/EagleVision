import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getItems } from "../../service/BackendApi";
import toast from "react-hot-toast";
import ItemOverview from "./ItemOverview";
import { useAuth } from "../../security/AuthContext";


export default function Items(){

    const urlParams = new URLSearchParams(window.location.search);

    const [currentQuery,setCurrentQuery] = useState(urlParams.get("query") || "")

    const [query,setQuery] = useState("")

    

    const [items,setItems] = useState([])

    const [refreshItems,setRefreshItems] = useState(false)

    const [pageNumber,setPageNumber] = useState(0)

    const [pageSize,setPageSize] = useState(5)

    const [totalPages,setTotalPages] = useState(1)

    const [pages,setPages] = useState([])

    const authContext = useAuth()

    const [position,setPosition] = useState(authContext?.position)

    useEffect(
        ()=>{
            setPosition(authContext?.position)
            setRefreshItems(!refreshItems)
        },[authContext?.position]
    )

    useEffect(()=>{
         setPages([...Array(totalPages)])
     },[totalPages])

    useEffect(()=>{
        getItems(position,pageNumber,pageSize,currentQuery).then(
            (response)=>{
                setItems(response.data.content)
                setTotalPages(response.data.totalPages)
            }
        ).catch((error)=>{
            toast.error("Error fetching items.")
            console.log(error)
        })
    },[refreshItems])

    function handleSearch(){
        if(query===""){
            toast.error("Enter a valid query!")
            return
        }
        setCurrentQuery(query)
        setRefreshItems(!refreshItems)
    }


    return <div>
        <div className="d-flex align-items-center justify-content-center mt-4">
            <div className="form-group m-2">
                <input type="text" className="form-control w-4" placeholder="Search" onChange={(event)=>{
                    setQuery(event.target.value);
                }

                } value={query}></input>
            </div>
            <div>
                <select className="form-select" onChange={
                    (e)=>{
                        setPageSize(Number(e.target.value))
                        setRefreshItems(!refreshItems)
                    }
                }>
                    <option value={5} selected>5</option>
                    <option value={10}>10</option>
                    <option value={20}>20</option>
                </select>
            </div>

            
            <div className="form-group m-2">
                <button className="btn btn-success" onClick={()=>{
                    handleSearch();
                }}>Search</button>
            </div>
        </div>
        <div className="text-center text-light my-5">
            {currentQuery!=='' && <h4>Showing results for "{currentQuery}"
            </h4>}
        </div>
        <div className="d-flex flex-wrap align-items-center justify-content-center">
        {
        items.map((item,id)=>{
            return <ItemOverview item={item} key={id}/>
        })
        }
        {
            items.length===0 && <div><h6 className="text-light">No items found!</h6></div>
        }
         </div>
         <div className="d-flex flex-wrap justify-content-center align-items-center">
         {
         items.length?pages.map((val,index)=>{
            return <div key={index} className="mx-2">
                <button className="btn btn-light text-primary" onClick={
                    (e)=>{
                        setPageNumber(index)  
                        setRefreshItems(!refreshItems)
                    }
                }>{index}</button>
            </div>
        }):""

    }
         </div>
    </div>
    
}