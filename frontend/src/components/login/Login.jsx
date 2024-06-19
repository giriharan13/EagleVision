import { useEffect, useState } from "react"
import { useAuth } from "../../security/AuthContext";
import { useNavigate } from "react-router-dom";
import {Field, Form, Formik} from 'formik';
import "./Login.css"


export default function Login(){
    const [showPassword,setShowPassword] = useState(false);
    
    const authContext = useAuth();

    const isAuthenticated = authContext.isAuthenticated;

    const navigate = useNavigate();

    const handleLogin = async (values)=>{
        try{
            await authContext.login(values.username,values.password);
            if(isAuthenticated){
                navigate('/home')
            }
        }
        catch(e){
            console.log(e);
        }
    }

    useEffect(()=>{
        if(isAuthenticated){
            navigate('/home')
        }
    },[isAuthenticated,navigate])

    return (<div className="login">
        <Formik initialValues={
            {
                username:"",
                password:""
            }
        } enableReinitialize={true} 
            onSubmit={(values)=>handleLogin(values)} >
            {(props)=>( <Form>
                    <div data-mdb-input-init className="form-outline mb-2">
                        <label>Username</label>
                        <Field type="text" className="form-control" name="username" />
                    </div>
                    <fieldset className="form-outline">
                        <label>Password</label>
                        <Field type={showPassword?"text":"password"} className="form-control" name="password" />
                    </fieldset>
                    <div>
                        <button className="btn btn-success" type="submit">Login</button>
                    </div>
                </Form>)
            }
        </Formik>
    </div>);
}