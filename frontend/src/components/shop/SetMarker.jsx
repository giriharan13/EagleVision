import { useEffect, useState } from "react"
import toast from "react-hot-toast";
import { useNavigate, useParams } from "react-router-dom";
import { getShopMarker, updateMarkerImage } from "../../service/BackendApi";


export default function SetMarker(){

    const [marker,setMarker] = useState(null);

    const [updatedMarker,setUpdatedMarker] =  useState(null);

    const {shopId}= useParams()

    const navigate = useNavigate()


    useEffect(()=>{
        getShopMarker(shopId).then(
            (response)=>{
                setMarker(response.data)
            }
        ).catch(
            (error)=>{
                toast.error("Error fetching marker")
            }
        )
    },[])




    function updateMarker(){
        if(updatedMarker===null){
            toast.error("No Image choosen!");
        }
        else{
            let formData = new FormData()

            formData.append("markerImageFile",updatedMarker)

            updateMarkerImage(shopId,formData).then((response)=>{
                toast.success("Updated Marker successfully!")
                navigate("/myshops")
            }).catch(
                (error)=>{
                    console.log(error)
                    toast.error("An error occurred.")
                }
            )
        }
    }



    return <>
    <div>Set Shop Marker</div>
    <div className="d-flex">
        <div className="col-2">
        {
            marker && marker.shopMarkerImage && <img className='img-thumbnail' src={`data:${marker.shopMarkerImageType};base64,${marker.shopMarkerImage}`} alt="marker"/>
        }
        {
            (marker===null || marker.shopMarkerImage===null) &&  <div>
                No marker set.
            </div>
        }
        </div>
    </div>
        <input type='file' className='form-control' accept="image/jpeg" name="shopImageFile" id="shopImageFile" onChange={(event)=>{                            
                                                        setUpdatedMarker(event.target.files[0])
                                               }}></input>
        <button className="btn btn-secondary" onClick={
            ()=>{
                updateMarker()
            }
        } >Set Marker</button>
    </>
}