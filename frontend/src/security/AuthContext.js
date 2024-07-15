import { createContext, useContext, useEffect, useState } from "react";
import { authenticate } from "../service/BackendApi";
import { jwtDecode } from "jwt-decode";

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




    const login = (username,password)=>{
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


    const state = {isAuthenticated,JWTToken,decoded,login,logout}

    return (
        <AuthContext.Provider value={state}>
            {children}
        </AuthContext.Provider>
    );
}