import { createContext, useContext, useEffect, useState } from "react";
import { authenticate, getActiveSubscription } from "../service/BackendApi";
import { jwtDecode } from "jwt-decode";
import toast from "react-hot-toast";

const AuthContext = createContext()

export const useAuth = () => useContext(AuthContext);

export default function AuthProvider({children}){

    const [isAuthenticated,setIsAuthenticated] = useState(()=>{
        return (localStorage.getItem("JWTToken"))?true:false
    })

    const [JWTToken,setJWTToken] = useState(()=>{
        return localStorage.getItem("JWTToken") ||  "";
    })

    const [decoded,setDecoded] = useState(()=>{
        return JWTToken ? jwtDecode(JWTToken) : null;
    })

    const [position,setPosition] = useState([0,0])

    const [isLoading,setIsLoading] = useState(true)

    const [roles,setRoles] = useState("")

    const [activeSubscription,setActiveSubscription] = useState({})

    const [refreshSubscription,setRefreshSubscription] = useState(false)

    useEffect(()=>{
        if(JWTToken){
            localStorage.setItem("JWTToken",JWTToken);
            setDecoded(jwtDecode(JWTToken))
        }
        else{
            setIsAuthenticated(false)
            localStorage.removeItem("JWTToken")
            setDecoded(null)
        }
    },[JWTToken])

    useEffect(()=>{
            navigator.geolocation.getCurrentPosition(success,error);

            function success (position){
                
                setPosition([position.coords.latitude,position.coords.longitude]);
                setIsLoading(false)
            }

            function error (){
                console.log("Unable to access your location.")
            }
    },[])

    useEffect(()=>{
        if(isAuthenticated){
            getActiveSubscription().then((response)=>{
                setActiveSubscription(response.data)
            }).catch((error)=>{
                toast.error("Error occured while fetching active subscription")
            })
        }
    },[JWTToken,refreshSubscription])

    const login = async (username,password)=>{
        return authenticate(username,password)
        .then((response)=>{
            setIsAuthenticated(true);
            setJWTToken(response.data.token);
            return response;
        })
        .catch((error)=>{
            setIsAuthenticated(false);
            setJWTToken("");
            setDecoded(null);
            throw error;
        })
    }

    function logout(){
        setIsAuthenticated(false);
        localStorage.removeItem("JWTToken");
        setJWTToken("");
        setDecoded(null)
    }


    const state = {isAuthenticated,JWTToken,decoded,login,logout,position,isLoading,activeSubscription}

    return (
        <AuthContext.Provider value={state}>
            {children}
        </AuthContext.Provider>
    );
}