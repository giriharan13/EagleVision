import { useNavigate } from "react-router-dom"
import "./Home.css"
import { useAuth } from "../../security/AuthContext";
import { roles } from "../../config/Roles";
import ShopImage from "./../../images/manageshops.jpg"
import PingImage from "./../../images/pings.jpg"
import ReviewsImage from "./../../images/reviews.jpg"
import { shopCategories } from "../../config/ShopCategories";
import { useState } from "react";
import toast from "react-hot-toast";


export default function Home(){

    let navigate = useNavigate();

    const authContext = useAuth();

    const [query,setQuery] =  useState("");
    const [searchType,setSearchType] = useState("shop");
    const [category,setCategory] = useState("ALL");

    function navigateTo(path){
        navigate(path);
    }

    const handleSearch = ()=>{
        if(query===""){
            toast.error("Enter a valid query!")
            return
        }
        if(searchType==="shop"){
            navigate(`/shops?query=${query}&category=${category}`)
        }
        else if(searchType==="item"){
            navigate(`/items?query=${query}`)
        }
    }

    return <div className="bg-primary text-light p-4">
        <h2 className="welcome" style={{fontFamily:"Kanit"}}>Welcome {authContext.decoded?.scope?authContext.decoded.sub:""}!</h2>
        <div className="menu">
            { authContext.decoded?.scope && authContext.decoded.scope.includes(roles.BUYER) && <>
            {
                    <div className="">
                        <div className="d-flex justify-content-center align-items-center">
                           
                                <div className="form-inline d-flex">
                                    <div className="form-group m-2">
                                        <input type="text" className="form-control w-4" placeholder="Search" onChange={(event)=>{
                                            setQuery(event.target.value);
                                        }
                
                                        } value={query}></input>
                                    </div>
                                    <div className="form-group m-2">
                                        <select className="form-select" name="type" onChange={(event)=>{
                                            setSearchType(event.target.value);
                                        }}>
                                            <option value="shop">shop</option>
                                            <option value="item">item</option>
                                        </select>
                                    </div>
                                    { searchType==="shop" && (
                                         <div className="form-group m-2">
                                         <select className="form-select" name="type" onChange={(event)=>{
                                             setCategory(event.target.value);
                                         }}>
                                             <option value="ALL">All</option>
                                            {
                                                shopCategories.map((category,idx)=>{
                                                    return (<option value={category.type} key={idx}>{category.display}</option>)
                                                })
                                            }
                                         </select>
                                     </div>
                                    )
                                    }
                                    <div className="form-group m-2">
                                        <button className="btn btn-success" onClick={()=>{
                                            handleSearch();
                                        }}>Search</button>
                                    </div>
                                </div>
                            
                            <div className="mx-4">
                                <button onClick={()=>navigateTo("/eaglevision")} className="btn btn-outline-light"><img src={require("./../../images/eagle.png")} style={{width:"1.5rem"}} alt="eaglevision"/></button>
                            </div>
                        </div>
                        <div className="d-flex justify-content-center align-items-center">
                            <h3 style={{fontFamily:"Kanit"}}>Explore</h3>
                            </div>
                        <div className="d-flex flex-wrap justify-content-center align-items-center">
                    { shopCategories.map((category,idx)=>(
                            <div className="d-flex m-2 justify-content-center align-items-center flex-column bg-light rounded-4" key={idx} onClick={()=>{
                                 navigateTo(`/shops?category=${category.type}`)
                                }}>
                                <div className="card category rounded-4">
                                    <img src={require(`./../../images/categories/${category.image}`)} alt="category" className="rounded-4"/>
                                    <div className="card-body text-center">
                                        <h5 className="card-title">{category.display}</h5>
                                    </div>
                                    
                                </div>
                            </div>
                    ))
                }
                    </div>
                </div>
            }
            </> } {/* Add a controller in backend,that uses a ML model to get the best selling items,and best shops*/}
            {
                authContext.decoded?.scope && authContext.decoded.scope.includes(roles.VENDOR) &&
                <div className="container">
                    <div className="row align-items-center justify-content-center">
                        <div className="col">
                            <div className="card text-center" style={{width:"25rem"}}>
                                <img alt="test" src={ShopImage}  className="card-img-top"></img>
                                <div className="card-body align-items-center justify-content-center ">
                                    <button className="btn btn-primary m-2 " onClick={()=>{navigateTo("/myshops")}}>Manage Shops</button>
                                </div>
                            </div>
                        </div>
                        <div className="col">
                            <div className="card text-center" style={{width:"25rem"}}>
                                <img alt="test" src={PingImage}  className="card-img-top"></img>
                                <div className="card-body">
                                    <button className="btn btn-primary m-2 " onClick={()=>{navigateTo("/pings")}}>Pings</button>
                                </div>
                            </div>
                        </div>
                        <div className="col">
                            <div className="card text-center" style={{width:"25rem"}}>
                                <img alt="test" src={ReviewsImage}  className="card-img-top"></img>
                                <div className="card-body">
                                    <button className="btn btn-primary m-2 " onClick={()=>{navigateTo("/reviews")}}>Reviews</button>
                                </div>
                            </div>
                        </div>
                        
                    </div>
                </div> 
            }
        </div>
        
    </div>
}