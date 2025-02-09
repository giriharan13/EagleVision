

import {Field, Form, Formik } from "formik";
import { useEffect, useState } from "react";
import { signup } from "../../service/BackendApi";
import {useNavigate } from "react-router-dom";
import { useAuth } from "../../security/AuthContext";
import { phoneNumberValidation, signUpSchema, userNameValidation } from "../../validation/SignUpValidation";
import toast from "react-hot-toast";





export default function Signup(){

    const authContext = useAuth();

    const isAuthenticated = authContext.isAuthenticated;

    const navigate = useNavigate();

    const [showPassword,setShowPassword] = useState(false);

    let initialValues = {
        username:"",
        phoneNumber:"",
        dateOfBirth:"",
        type:"",
        password:"",
        confirmPassword:"",
        role:"-"
    }

    const signUpFields = [
        {name:"username", type:"text",validate:userNameValidation},
        {name:"phoneNumber", type:"text",validate:phoneNumberValidation},
        {name:"dateOfBirth", type:"date"},
        {name:"password",type:!showPassword?"password":"text"},
        {name:"confirmPassword",type:!showPassword?"password":"text"}
    ]

    const handleSignUp = async  (values)=>{
            return signup(values.username,values.phoneNumber,values.dateOfBirth,values.password,values.role).then(
                (response)=>{
                    toast.success("Signed up successfully!");
                    navigate('/login')       
                }
            ).catch(
                (err)=>{
                    toast.error(err);
                }
            )
    }

    useEffect(()=>{
        if(isAuthenticated){
            navigate('/home')
        }
    },[isAuthenticated,navigate])


    return <div className="text-light">
        <Formik initialValues={initialValues}
        enableReinitialize={true} validationSchema={signUpSchema} validateOnChange={false}
        onSubmit={(values)=>handleSignUp(values)}>
                {({errors,touched})=>{
                    return <div>
                    <Form>
                       { signUpFields.map((field)=>(<div key={field.name}>
                            <label>{field.name}</label>
                            <Field type={field.type} className="form-control" name={field.name} validate={(field?.validate)}>

                            </Field>
                            {touched[field.name] && errors[field.name] && <div className="alert alert-warning">{errors[field.name]}</div>}
                        </div>))}
                        <div className="d-flex flex-grow justify-content-end">
                            <label className="p-2">Show password</label>
                            <Field type="checkbox" className="checkbox p-2" name="check" checked={showPassword} onChange={()=>setShowPassword((prev)=>!prev)}></Field>
                        </div>
                        <div>
                            Which best describes you?
                            <Field as="select" name="role" className="form-select">
                                <option selected>-</option>
                                <option>BUYER</option>
                                <option>VENDOR</option>
                            </Field>
                            {touched["role"] && errors["role"] && <div className="alert alert-warning">{errors["role"]}</div>}
                        </div>
                        <div>
                            <button className="btn btn-success" type="submit">Signup</button>
                         </div>
                    </Form>
                    </div>
                }}
        </Formik>
    </div>
}