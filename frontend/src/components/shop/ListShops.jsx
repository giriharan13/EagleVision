import { useState } from "react";
import { useEffect } from "react";
// import "../../../node_modules/bootstrap/dist/css/bootstrap.min.css"
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../../security/AuthContext";
import { shopCategories } from "../../config/ShopCategories";
import toast from "react-hot-toast";

export default function ListShops(props){
    const [shops,setShops] = useState([]);
    const urlParams = new URLSearchParams(window.location.search);

    const authContext = useAuth();

    const navigate = useNavigate();

    const [currentQuery,setCurrentQuery] = useState(urlParams.get("query") || "")
    const [query,setQuery] = useState("")
    const [selectedCategory,setSelectedCategory] = useState("ALL")
    const [currentSelectedCategory,setCurrentSelectedCategory]= useState(urlParams.get("category") || "ALL")
    const [pageSize,setPageSize] = useState(5)
    const [pageNumber,setPageNumber] = useState(0)
    const [totalPages,setTotalPages] = useState(1)
    const [position,setPosition] = useState(authContext?.position)
    const [refresh,setRefresh] = useState(false)

    const [pages,setPages] = useState([])

    const isLoading = authContext?.isLoading;

    useEffect(()=>{
       // console.log(totalPages)
        setPages([...Array(totalPages)])
    },[totalPages])

    useEffect(()=>{
        props.getShops(currentQuery,currentSelectedCategory,position[0],position[1],pageNumber,pageSize,shops,authContext?.position).then(
            (response)=>{
                setPageNumber(response.data.number)
                setTotalPages(response.data.totalPages)
                refreshShops(response.data.content)
            }
        ).catch(
            (error)=>{console.log(error)}
        );

        function refreshShops(shops){
            setShops(shops)
        }
    },[refresh,props,position])

    useEffect(()=>{
        setPosition(authContext?.position)
    },[authContext?.position])

    const handleSearch = ()=>{
        if(query===""){
            toast.error("Enter a valid query!")
            return
        }
        navigate(`/shops?query=${query}&category=${selectedCategory}`)
        setCurrentQuery(query)
        setCurrentSelectedCategory(selectedCategory)
        setRefresh(!refresh)
    }


    return <div className="shops text-light">
         <div className="row">
            <div className="text-center my-4">
                <div>
                    <div className="form-inline d-flex align-items-center justify-content-center m-3">
                        <div className="form-group m-2">
                            <input type="text" className="form-control w-4" placeholder="Search" onChange={(event)=>{
                                setQuery(event.target.value);
                            }
    
                            } value={query}></input>
                        </div>

                        
                        <div className="form-group m-2">
                                <select className="form-select" name="type" onChange={(event)=>{
                                    setSelectedCategory(event.target.value);
                                }}>
                                    <option selected={selectedCategory==="ALL"} value="ALL">All</option>
                                {
                                    shopCategories.map((category,idx)=>{
                                        return (<option value={category.type} key={idx} selected={category.type===selectedCategory}>{category.display}</option>)
                                    })
                                }
                                </select>
                            </div>
                            <div className="form-group m-2">
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
                        
                        
                        <div className="form-group m-2">
                            <button className="btn btn-success" onClick={()=>{
                                handleSearch();
                            }}>Search</button>
                        </div>
                    </div>
                </div>
                {currentSelectedCategory!=="ALL" && <h1 className="fw-bold">{currentSelectedCategory}</h1>}
                {currentQuery.length>0 && <h4 className="fw-bold">Showing results for '{currentQuery}'</h4>}
            </div>
        </div>
        <div className="d-flex flex-wrap justify-content-center align-self-stretch">
        {
            shops.length===0 && <div className=" d-flex justify-content-center align-items-center">
                <p>No shops found!</p>
                </div>
        }
        { shops.map((shop)=>{
           return (<div className="d-flex flex-column bg-white text-primary m-2 p-2 rounded-4 justify-content-between" style={{width:"16rem"}}  key={shop.shopId}>
                      <div className="d-flex justify-content-center align-items-center">
                            <img style={{width:"14rem",height:"14rem"}} className="rounded-4" src={`data:${shop.imageType};base64,${shop.shopImageDataB64}`} alt="shop"/> {/* adding a static image as of now*/}
                      </div>
                      <div className="d-flex flex-column justify-items-start align-items-start">
                            <h4 className="fw-bold">{shop.shopName}</h4>
                            { shop.description.length<100 ? <p className="text-break text-wrap">{shop.description}</p> : <p className="text-break text-wrap">{shop.description.substring(0,100)}...</p> }
                      </div>
                      <div className="d-flex flex-column">
                         <div className="d-flex align-items-end justify-content-end">
                            <Link className="btn btn-primary w-25" to={`/shops/${shop.shopId}`}>View</Link>
                        </div>
                      </div>
                  </div>);
        }) }
        </div>
        <div className="row">
            <div className="d-flex flex-column align-items-center fluid-container d-flex flex-wrap">
                <div className="d-flex align-items-center justify-content-center">
                {
                    shops.length?pages.map((val,index)=>{
                        return <div key={index} className="m-2">
                            <button className="btn btn-light text-primary" onClick={
                                (e)=>{
                                    setPageNumber(index)  
                                    setRefresh(!refresh)
                                }
                            }>{index}</button>
                        </div>
                    }):""
                }

                </div>
            </div>
        </div>
    </div>
}