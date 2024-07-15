import { useEffect, useState } from "react"
import { useAuth } from "../../security/AuthContext";
import { useNavigate } from "react-router-dom";
import {Field, Form, Formik} from 'formik';
import toast from "react-hot-toast";


export default function Login(){
    const [showPassword,setShowPassword] = useState(false);
    
    const authContext = useAuth();

    const navigate = useNavigate();

    const handleLogin = async (values)=>{
        try{
            authContext.login(values.username,values.password).then((response)=>{
                toast.success(`Logged in successfully as ${values.username}`)
                navigate('/home')
            }).catch((err)=>{
                toast.error("Invalid credentials!");
            });
        }
        catch(e){
            console.log(e);
        }
    }

    useEffect(()=>{
        const isAuthenticated = authContext.isAuthenticated;
        if(isAuthenticated){
            navigate('/home')
        }
    },[navigate])

    return (<div className="fluid-container">
        <Formik initialValues={
            {
                username:"",
                password:"",
            }
        } enableReinitialize={true} 
            onSubmit={(values)=>handleLogin(values)} >
            {(props)=>( <Form>
                    <div data-mdb-input-init className="form-outline mb-2">
                        <label>Username</label>
                        <Field type="text" className="form-control" name="username" />
                    </div>
                    <div className="form-outline mb-2">
                        <label>Password</label>
                        <Field type={showPassword?"text":"password"} className="form-control" name="password" />
                    </div>
                    <div className="form-outline mb-2">
                        <div className="form-outline mb-2">
                            <label>Show Password</label>
                            <Field type="checkbox" checked={showPassword} className="form-check-label mx-2 p-2" name="showPassword" onChange={()=>{setShowPassword(!showPassword)}}/>
                        </div>
                    </div>
                    <div>
                        <button className="btn btn-success" type="submit">Login</button>
                    </div>
                </Form>)
            }
        </Formik>
    </div>);
}