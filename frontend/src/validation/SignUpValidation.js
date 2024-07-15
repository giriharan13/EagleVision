import * as Yup from "yup";
import { checkPhoneNumber, checkUsername } from "../service/BackendApi";

export const signUpSchema = Yup.object().shape({
    username:Yup.string().min(6,"username is too short!").max(30,"username is too large!").required("Username is required"),
    phoneNumber:Yup.string().min(10,"Invalid phone number!").max(10,"Invalid phone number!").matches(/^[0-9]+$/, "Phone number must contain only digits"),
    dateOfBirth: Yup.date().max(new Date(),"Invalid date of birth!"),
    password: Yup.string().required("Password is required!").matches(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$/,"Create a strong password"),
    confirmPassword: Yup.string().oneOf([Yup.ref("password"),null], "Passwords must match")
})

export async function userNameValidation(username){
    return checkUsername(username).then(
        (response)=>(undefined)
    ).catch((err)=>{
        return err.response.data
    })
}


export async function phoneNumberValidation(phoneNumber){
    return checkPhoneNumber(phoneNumber).then(
        (response)=>undefined
    ).catch((err)=>{
        return err.response.data
    })
}