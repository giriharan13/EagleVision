import React, { useEffect, useState } from 'react'
import { FaRegStar,FaStar } from "react-icons/fa";
import './Review.scss';
import { AiFillEdit,AiOutlineLike,AiOutlineDislike,AiFillLike,AiFillDislike } from "react-icons/ai";
import { RiDeleteBinFill } from "react-icons/ri";
import { useAuth } from '../../security/AuthContext';
import { Field, Form, Formik } from 'formik';
import StarField from '../starField/StarField';
import toast from 'react-hot-toast';


export default function ShopReview({review,key,handleUpdateReview,handleDeleteReview,toggleLike,toggleDislike,shopId}) {
  const [editMode,setEditMode] = useState(false);

  const authContext = useAuth();

  const starValues = [1,2,3,4,5];

  const [stars,setStars] = useState(review.stars);

  const [liked,setLiked] = useState(review.currentUserLiked)

  const [disliked,setDisliked] = useState(review.currentUserDisliked)


  const initialValues = {
    "comment":review.comment,
    "stars":review.stars,
    "isEdited":true
  }



  return (
        <div key={key} className='review text-primary'>
            
          { !editMode && 
            <div className='review-info'>
              <div className='author'>
                {review.authorUserName}
              </div>
              <div className='content'>
                <div className='comment'>
                  {review.comment} 
                </div>

                <div className='stars'>
                  {
                    starValues.map((value)=> (review.stars >= value)? <FaStar/> : <FaRegStar/> )

                  }

                </div>
                <div className='text-muted'>
                {review.isEdited && "(edited)"}
                </div>

                <div className='d-flex'>
                  <div className='d-flex m-2 justify-content-center align-items-center'>
                     <button className='btn btn-primary' onClick={()=>{
                         toggleLike(shopId,setLiked,setDisliked,review,liked,disliked)
                     }}>
                     {liked?(<AiFillLike/>):(<AiOutlineLike/>) }
                     </button>
                     <div className='mx-2'>
                     {review.likesCount}
                     </div>
                  </div>
                  <div className='d-flex m-2 justify-content-center align-items-center'>
                    <button className='btn btn-primary' onClick={()=>{
                      toggleDislike(shopId,setLiked,setDisliked,review,liked,disliked)
                    }}>
                    {disliked?(<AiFillDislike/>):(<AiOutlineDislike/>)}
                    </button>
                    <div className='mx-2'>
                    {review.dislikesCount}
                    </div>
                  </div>
                </div>

              </div>

              {review.authorUserId === authContext.decoded.userId && <div className='d-flex gap-2'>
                  <button className='btn btn-primary' onClick={()=>{setEditMode(true)}}><AiFillEdit/></button>
                  <button className='btn btn-primary' onClick={()=>handleDeleteReview(shopId,review.reviewId)}><RiDeleteBinFill/></button>
                </div>}
            </div>
          }
          { editMode && review.authorUserId === authContext.decoded.userId &&  <div className='edit-review'>
            <Formik initialValues={initialValues} enableReinitialize onSubmit={(values)=>{
              handleUpdateReview(shopId,review.reviewId,values)
              setEditMode(false)}}>
              {({setFieldValue,values})=>(<Form className='review-form'>
                <Field as="textarea" name="comment"/>
                <StarField setFieldValue={setFieldValue} stars={stars} setStars={setStars}/>
                <div className='d-flex gap-2 m-2'>
                   <button className='btn btn-primary' onClick={()=>{setEditMode(false)}}>cancel</button>
                   <button className='btn btn-primary' type='submit' >save</button>
                </div>
              </Form>)}
            </Formik>
            </div>

          }
        </div>
  )
}

