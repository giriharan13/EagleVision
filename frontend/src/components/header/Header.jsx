import { Link, useLocation, useNavigate } from "react-router-dom"
// import "../../../node_modules/bootstrap/dist/css/bootstrap.min.css"
import { useAuth } from "../../security/AuthContext"
import toast from "react-hot-toast";
import { Notifications } from "../notifications/Notifications";
import { useEffect, useRef, useState } from "react";
import Logo from "../../images/eaglevisionlogo.png"
import LogoWhite from "../../images/eaglevisionlogowhite.png"
import { MdAccountCircle } from "react-icons/md";
import { GiUpgrade } from "react-icons/gi";
import { getAllUnreadNotifications } from "../../service/BackendApi";

export default function Header({bellNotifications,setBellNotifications,refreshBellNotifications,setRefreshBellNotifications,featuresRef,pricingRef,contactRef}){

    const authContext = useAuth();

    const navigate = useNavigate();

    const location =  useLocation();

    const isAuthenticated = authContext.isAuthenticated;

    const logout = async ()=>{
        await authContext.logout();
        setBellNotifications([])
        toast.success("Logged out successfully!")
        navigate("/login")
    }

    const goToAccount = async ()=>{
        navigate(`/profile/${authContext.decoded.userId}`,{replace:true})
    }

    function goTo(elementRef){
        window.scrollTo({
            top:elementRef.current.offsetTop,
            behavior:"smooth"
        })
    }

    useEffect(
        ()=> {
           if(isAuthenticated) 
            {
                getAllUnreadNotifications().then((response)=>{
                       setBellNotifications(response.data)
                          }).catch(
                        (error)=>{
               toast.error("Error fetching notifications!")
             }
           )
           }
         },[isAuthenticated,refreshBellNotifications]
       )

    return <header className={`navbar navbar-expand-lg navbar-dark  ${authContext.isAuthenticated?"bg-light bg-opacity-25":"bg-transparent"}`}>
        <div className="container-fluid">
            <Link to="/" className="navbar-brand"> 
                {/* <p className="fs-4 text-white fw-bold text-center">🦅EagleVision</p> */}
                <img src={(location.pathname==="/" || location.pathname==="/vendors")?LogoWhite:Logo} className="navbar-brand" style={{width:150}}/>
            </Link>
            {/* <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button> */}
            <div className="d-flex collapse navbar-collapse" id="navbarSupportedContent">
                <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                    { (location.pathname==="/" || location.pathname==="/vendors") && <div className="d-flex me-auto mb-2 mb-lg-0 mt-2">
                        <li class="nav-item">
                            <Link class="nav-link active text-white" aria-current="page" onClick={()=>{
                                goTo(featuresRef)
                            }}>Features</Link>
                        </li>
                        <li class="nav-item">
                            <Link class="nav-link active text-white" aria-current="page" onClick={()=>{
                                goTo(pricingRef)
                            }}>Pricing</Link>
                        </li>
                        <li class="nav-item" onClick={()=>{
                                goTo(contactRef)
                            }}>
                            <Link class="nav-link text-white" to="#contact">Contact us</Link>
                        </li>
                    </div>}
                    {
                        isAuthenticated && location.pathname!=='/home' && <li className="nav-link">
                            <Link class="nav-link active text-white" to="/home">Home</Link>
                        </li>
                    }
                </ul> 

            </div>
            
            <ul class="d-flex nav navbar-nav navbar-right gap-2">
            {isAuthenticated && <Notifications initialNotifications={bellNotifications}/>}
                    {isAuthenticated && <li className="nav-item">
                        <button className="btn btn-light" onClick={()=>goToAccount()}><MdAccountCircle className="text-primary" size={22}/></button>
                        </li>}
                        {isAuthenticated && <li className="nav-item">
                        <button className="btn btn-light" onClick={()=>navigate("/subscription")}><GiUpgrade className="text-primary" size={22}/></button>
                        </li>}
                        {isAuthenticated && <li className="nav-item">
                        <Link className="btn btn-light" onClick={logout}>Logout</Link>
                        </li>}
                    {!isAuthenticated && location.pathname!=='/login' && location.pathname!=="/" && location.pathname!=="/vendors" &&
                        <li class="nav-item">
                            <Link className="nav-link" to="/login"> Login</Link>
                            </li>
                            }
                        { !isAuthenticated &&  location.pathname!=='/signup' && location.pathname!=="/" && location.pathname!=="/vendors" &&
                          <li class="nav-item">
                            <Link className="nav-link" to="/signup"> Signup</Link>
                            </li>
                    }
                </ul>
        </div>
    </header>
}