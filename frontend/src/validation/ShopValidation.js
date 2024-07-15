import * as Yup from "yup";

export const shopValidationSchema = Yup.object().shape({
    "shopName":Yup.string().min(5,"Shop name too short!").max(50,"Shop name too long!").required("Shop name is required!"),
    "description":Yup.string().max(500,"The description can have a maximum of 500 characters."),
    "contactNumber":Yup.string().max(10,"Invalid contact number!").min(10,"Invalid contact number!").matches(/^[0-9]+$/,"The contact number should contain only digits."),
    "line1":Yup.string().required("Line 1 is required"),
    "country":Yup.string().required("Country is required"),
    "state":Yup.string().required("State is required"),
    "city":Yup.string().required("City is required")
})