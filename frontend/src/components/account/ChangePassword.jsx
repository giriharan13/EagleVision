import { Field, Form, Formik } from "formik";
import { changePasswordValidationSchema } from "../../validation/AccountValidation";
import { changePassword } from "../../service/BackendApi";
import toast from "react-hot-toast";
import { useNavigate } from "react-router-dom";
import { useState } from "react";


export default function ChangePassword(){

    const navigate = useNavigate()

    const [showOldPassword,setShowOldPassword] = useState(false)

    const [showPassword,setShowPassword] = useState(false)


    function handleChangePassword(values){
        changePassword({
            oldPassword:values.oldPassword,
            newPassword:values.newPassword,
            confirmNewPassword:values.confirmNewPassword
        }).then(
            (response)=>{
                toast.success("updated Password successfully!")
                navigate("/home")
            }
        ).catch(
            (error)=>{
                toast.error("Error updating password!")
            }
        )
    }



    return <div className="d-flex flex-column align-items-center justify-content-center m-4">
        <div className="d-flex align-items-center justify-content-center">
            <h2 className="fw-bold text-light">Change Password</h2>
        </div>

        <div className=" d-flex align-items-center justify-content-center text-primary p-4 m-4 bg-white rounded-4">
            <Formik initialValues={{
                oldPassword:"",
                newPassword:"",
                confirmNewPassword:""
            }} enableReinitialize={true} onSubmit={(values)=>handleChangePassword(values)} validationSchema={changePasswordValidationSchema}>
                { ({errors,touched})=>
                    <Form>
                            <div  className="form-outline mb-2">
                                <label>Old Password</label>
                                <Field type={showOldPassword?"text":"password"} className="form-control" name="oldPassword" />
                            </div>
                            <div className="form-outline mb-2">
                                <div className="form-outline mb-2">
                                    <label>Show Old Password</label>
                                    <Field type="checkbox" checked={showOldPassword} className="form-check-label mx-2 p-2" name="showPassword" onChange={()=>{setShowOldPassword(!showOldPassword)}}/>
                                </div>
                            </div>
                            {touched["oldPassword"] && errors["oldPassword"] && <div className="alert alert-warning">{errors["oldPassword"]}</div>}

                            <div  className="form-outline mb-2">
                                <label>New Password</label>
                                <Field type={showPassword?"text":"password"}  className="form-control" name="newPassword" />
                            </div>

                            <div  className="form-outline mb-2">
                                <label>Confirm New Password</label>
                                <Field type={showPassword?"text":"password"}  className="form-control" name="confirmNewPassword" />
                            </div>
                            <div className="form-outline mb-2">
                                <div className="form-outline mb-2">
                                    <label>Show New Password</label>
                                    <Field type="checkbox" checked={showPassword} className="form-check-label mx-2 p-2" name="showPassword" onChange={()=>{setShowPassword(!showPassword)}}/>
                                </div>
                            </div>
                            {touched["newPassword"] && errors["newPassword"] && <div className="alert alert-warning">{errors["newPassword"]}</div>}
                            {touched["confirmNewPassword"] && errors["confirmNewPassword"] && <div className="alert alert-warning">{errors["confirmNewPassword"]}</div>}
                            <div>
                                <button className="btn btn-success" type="submit">Change Password</button>
                            </div>


                    </Form>}
            </Formik>
        </div>

    </div>

}