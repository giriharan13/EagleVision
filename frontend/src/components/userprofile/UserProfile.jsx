import { useNavigate, useParams } from "react-router-dom"
import VendorProfile from "./VendorProfile"
import BuyerProfile from "./BuyerProfile"
import { useAuth } from "../../security/AuthContext"
import { useEffect, useRef, useState } from "react"
import { checkPhoneNumber, checkUsername, getUser, setProfilePicture, updateUserProfile } from "../../service/BackendApi"
import toast from "react-hot-toast"
import DefaultPfp from "../../images/defaultpfp.jpg"
import { AiFillEdit } from "react-icons/ai";


export default function UserProfile(){


    const authContext = useAuth()

    const {userId} = useParams()

    const [user,setUser] = useState({})

    const inputFileRef = useRef( null );

    const navigate = useNavigate()

    const [edit,setEdit] = useState(false)

    const [userName,setUserName] = useState(user?.userName)

    const [phoneNumber,setPhoneNumber] = useState(user?.phoneNumber)

    const [refreshUser,setRefreshUser] = useState(false)

    const [isValidUserName,setIsValidUserName] = useState(false)

    const [isValidPhoneNumber,setIsValidPhoneNumber] = useState(false)

    const onFileChangeCapture = async(e)=>{
        let formData = new FormData();
        formData.append("profilePicture",e.target.files[0])
        return setProfilePicture(formData).then(
            (response)=>{
                setRefreshUser(!refreshUser)
                toast.success("Updated Profile Picture successfully!")
            }
        )
    }

    function handleEditProfile(){
        if(!isValidUserName){
            toast.error("Not a valid Username!")
            return 
        }
        if(!isValidPhoneNumber){
            toast.error("Not a valid Phone Number!")
            return
        }

        setEdit(!edit)
        updateUserProfile({userName,phoneNumber}).then(
            ()=>{
                setRefreshUser(!refreshUser)
            }
        );
    }

    function goToChangePassword(){
        navigate("/account/changePassword")
    }

    useEffect(()=>{
        setRefreshUser(!refreshUser)
    },[userId])

    useEffect(()=>{
        getUser(userId).then(
            (response)=>{
                setUser(response.data)
                setUserName(response.data.userName)
                setPhoneNumber(response.data.phoneNumber)
            }
        ).catch(
            (error)=>{
                // toast.error(error.response)
                console.log(error)
            }
        )
    },[refreshUser])



    return <div className="bg-light bg-opacity-25 rounded-3 p-2 m-4">
        <div className="d-flex align-items-center justify-content-center my-3">
            <h1 className="text-light">Profile</h1>
        </div>
        <div>
            <div className="container rounded-5" style={{width:"15%",position:"relative"}}>
                <input onChangeCapture={onFileChangeCapture} ref={inputFileRef} type="file" style={{display:"none"}}/>
                <img style={{width:"100%"}} className="rounded-circle" src={user?.profilePictureImageData!=null?`data:${user?.profilePictureImageType};base64,${user?.profilePictureImageData}`:DefaultPfp}/>
                {authContext.decoded.userId===user.userId && <button className="btn btn-light rounded-circle" style={{position:"absolute", top:"70%",right:"10%",zIndex:1}} onClick={()=>{
                    inputFileRef.current.click()
                }}> <AiFillEdit/> </button>}
            </div>
        </div>
        <div className="d-flex flex-column bg-white rounded-4">
            <div className="d-flex text-primary m-4">
                {
                    !edit && <div className="d-flex flex-column align-items-start justify-content-center m-2">
                            <div className="d-flex gap-2">
                                <h5 className="fw-bold">Username </h5> :<h5>{user.userName}</h5>
                            </div>
                            <div className="d-flex gap-2">
                                <h5 className="fw-bold"> Phone Number </h5>: <h5>{user.phoneNumber} </h5>
                            </div>
                        </div>
                }
                {
                    edit && <div>
                        <div className="d-flex flex-column">
                            <label>Username:</label>
                            <input type="text" value={userName} onChange={(e)=>{
                                setUserName(e.target.value)
                                checkUsername(e.target.value).then(
                                    (response)=>{
                                        setIsValidUserName(true)
                                    }
                                ).catch(
                                    (error)=>{
                                        if(e.target.value!==user.userName){
                                            toast.error("Username already exists!")
                                            setIsValidUserName(false)
                                        }
                                    }
                                )
                                }}></input>
                        </div>
                        <div className="d-flex flex-column">
                        <label>Phone Number:</label>
                            <input type="text" value={phoneNumber} onChange={(e)=>{
                                setPhoneNumber(e.target.value)
                                checkPhoneNumber(e.target.value).then(
                                    (response)=>{
                                        setIsValidPhoneNumber(true)
                                    }
                                ).catch(
                                    (error)=>{
                                        if(e.target.value!==user.phoneNumber){
                                            toast.error("Phone Number already in use!")
                                            setIsValidPhoneNumber(false)
                                        }
                                    }
                                )
                            }}></input>
                        </div>
                        </div>
                }
            </div>
            <div className="d-flex align-items-end justify-content-end" >
                { authContext.decoded.userId===user.userId && 
                <div className="d-flex m-2 gap-2"> 
                    <button onClick={()=>{
                    setUserName(user?.userName)
                    setPhoneNumber(user?.phoneNumber)
                    setEdit(!edit)
                    }} className="btn btn-primary">{edit?"cancel":"Edit"}</button>
                    {edit && <button className="btn btn-light" onClick={handleEditProfile}>Save</button>}
                    {!edit && <button className="btn btn-primary" onClick={()=>goToChangePassword()}> Change Password</button> }
                </div>}


            </div>
        </div>
        {
            (user?.isVendor) &&  <VendorProfile/>
        }
        {
            !(user?.isVendor) &&  <BuyerProfile/>
        }
    </div>

}