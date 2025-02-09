import * as Yup from "yup"

export const changePasswordValidationSchema = Yup.object().shape({
    oldPassword: Yup.string().required("Old password is required!"),
    newPassword: Yup.string().required("New Password is required!").matches(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$/,"Create a strong password"),
    confirmNewPassword: Yup.string().oneOf([Yup.ref("newPassword"),null], "Passwords must match"),
})