import { useState } from "react";
import { useEffect } from "react";
// import "../../../node_modules/bootstrap/dist/css/bootstrap.min.css"
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../../security/AuthContext";
import {  getShopsByVendor } from "../../service/BackendApi";
import ShopOverview from "./ShopOverview";
import "./Shop.scss";
import { RiDeleteBinFill } from "react-icons/ri";

export default function ListMyShops(){
    const [shops,setShops] = useState([]);

    const authContext = useAuth();

    const [refresh,setRefresh] = useState(false);

    const [pageNumber,setPageNumber] = useState(0)

    const [pageSize,setPageSize] = useState(5)

    const [totalPages,setTotalPages] = useState(1)

    const [sortBy,setSortBy] = useState("shopName")

    const [query,setQuery] = useState("")

    const [pages,setPages] = useState([])

    const isLoading = authContext?.isLoading;

    useEffect(()=>{
       // console.log(totalPages)
        setPages([...Array(totalPages)])
    },[totalPages])

    const navigate = useNavigate();

    function refreshShops(shops){
        setShops(shops)
    }

    useEffect(()=>{
        if(!isLoading){
            getShopsByVendor(authContext.decoded.userId,pageNumber,pageSize,authContext.position,sortBy,query).then(
                (response)=>{
                    setPageNumber(response.data.number)
                    setTotalPages(response.data.totalPages)
                    refreshShops(response.data.content)
                }
            ).catch(
                (error)=>{console.log(error)}
            );
        }

        //setPageCount(getShopCountByVendor())
    },[authContext.decoded.userId,refresh,isLoading])

    function handleSearch(e){
        e.preventDefault()
        setRefresh(!refresh)
    }


    return <div className="list-shops d-flex flex-column justify-content-center align-items-center">
        <div className="d-flex my-4">
                <h2 className="fw-bold text-light">My Shops</h2>
        </div>
        <div className="d-flex flex-column align-items-center justify-content-center align-self-stretch gap-4  bg-white rounded-4 p-4 bg-opacity-25">
                    <div className="d-flex justify-content-center align-items-center">
                        <form onSubmit={
                            handleSearch
                        }>
                            <div className="d-flex gap-2 justify-content-center align-items-center">
                                <div className="d-flex">
                                    <input type="text" className="form-control" placeholder="Search" value={query} onChange={(e)=>setQuery(e.target.value)}></input>
                                </div>
                                <div className="d-flex">
                                    <select className="form-select" onChange={
                                        (e)=>{
                                            setPageSize(Number(e.target.value))
                                            setRefresh(!refresh)
                                        }
                                    }>
                                        <option value={5} selected>5</option>
                                        <option value={10}>10</option>
                                        <option value={20}>20</option>
                                    </select>
                                </div>
                                <div className="d-flex">
                                    <button type="sumbit" className="btn btn-primary">Search</button>
                                </div>
                            </div>
                        </form>
            </div>
            <div className="d-flex align-items-around justify-content-around">
                <div className="container-fluid d-flex flex-wrap">
                    {shops.length>0 && shops.map((shop)=>{
                    return (<ShopOverview shop={shop} setRefresh={setRefresh} refresh={refresh}/>);
                    }) }
                    {
                        shops.length===0 && <div className="col-3">You have no shops.</div>
                    }

                </div>
                <div className="d-flex">
                    <div className="d-flex justify-content-start align-items-start">
                        <div className="d-flex justify-content-center m-4 ">
                            <button className="btn btn-primary" onClick={()=>navigate("/create/shop")}>
                                    Create
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <div className="d-flex">
                <div className="d-flex flex-column align-items-center fluid-container d-flex flex-wrap">
                    <div className="row">
                    {
                        pages.map((val,index)=>{
                            return <div key={index} className="col-2 mx-2">
                                <button className="btn btn-primary" onClick={
                                    (e)=>{
                                        setPageNumber(index)  
                                        setRefresh(!refresh)
                                    }
                                }>{index}</button>
                            </div>
                        })
                    }
                    </div>
                </div>
            </div>
            </div>
        </div>
}