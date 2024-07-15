import React, { useEffect, useState } from 'react'
import { FaRegStar,FaStar } from "react-icons/fa";
import "./StarField.scss"

function StarField({stars,setFieldValue}) {

    const [editStars,setEditStars] = useState(stars);

    const starFieldValues = [1,2,3,4,5]
    const starSize = 30;

    useEffect(()=>{
        setFieldValue("stars",editStars)
    },[editStars])

    return (
        <div className='stars'>
            {
                starFieldValues.map((value,index)=>{
                    return <div key={index} className='star' onClick={()=>setEditStars(value)}>
                         {(editStars>=value)? <FaStar size={starSize}/> : <FaRegStar size={starSize}/>}
                    </div>
                })
            }
        </div>
    )
}

export default StarField
