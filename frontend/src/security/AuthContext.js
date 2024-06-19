import { createContext, useContext, useEffect, useState } from "react";
import { authenticate } from "../service/BackendApi";

const AuthContext = createContext()

export const useAuth = () => useContext(AuthContext);

export default function AuthProvider({children}){

    const [isAuthenticated,setIsAuthenticated] = useState(()=>{
        return (localStorage.getItem("JWTToken"))?true:false
    })

    const [JWTToken,setJWTToken] = useState(()=>{
        return localStorage.getItem("JWTToken") ||  "";
    })

    useEffect(()=>{
        if(JWTToken){
            localStorage.setItem("JWTToken",JWTToken);
        }
        else{
            setIsAuthenticated(false)
            localStorage.removeItem("JWTToken")
        }
    },[JWTToken])




    const login = (username,password)=>{
        return authenticate(username,password)
        .then((response)=>{
            setIsAuthenticated(true);
            setJWTToken(response.data.token);
        })
        .catch((error)=>{
            setIsAuthenticated(false);
            setJWTToken("")
        })
    }

    function logout(){
        setIsAuthenticated(false);
        localStorage.removeItem("JWTToken");
        setJWTToken("");
    }

    const state = {isAuthenticated,JWTToken,login,logout}

    return (
        <AuthContext.Provider value={state}>
            {children}
        </AuthContext.Provider>
    );
}