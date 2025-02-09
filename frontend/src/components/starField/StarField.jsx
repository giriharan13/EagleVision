import React, { useEffect, useState } from 'react'
import { FaRegStar,FaStar } from "react-icons/fa";
import "./StarField.scss"

function StarField({stars,setStars,setFieldValue}) {


    const starFieldValues = [1,2,3,4,5]
    const starSize = 30;

    useEffect(()=>{
        setFieldValue("stars",stars)
    },[stars])

    return (
        <div className='stars'>
            {
                starFieldValues.map((value,index)=>{
                    return <div key={index} className='star' onClick={()=>setStars(value)}>
                         {(stars>=value)? <FaStar size={starSize}/> : <FaRegStar size={starSize}/>}
                    </div>
                })
            }
        </div>
    )
}

export default StarField
