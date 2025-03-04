import React, { useEffect, useState } from 'react'
import { FaRegStar,FaStar } from "react-icons/fa";
import './Review.scss';
import { AiFillEdit,AiOutlineLike,AiOutlineDislike,AiFillLike,AiFillDislike } from "react-icons/ai";
import { RiDeleteBinFill } from "react-icons/ri";
import { useAuth } from '../../security/AuthContext';
import { Field, Form, Formik } from 'formik';
import StarField from '../starField/StarField';
import toast from 'react-hot-toast';


function Review({review,key,handleUpdateReview,handleDeleteReview,toggleLike,toggleDislike}) {
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
        <div key={key} className='review'>
            
          { !editMode && 
            <div className='review-info'>
              <div className='author'>
                <a href={`/profile/${review.authorUserId}`}>{review.authorUserName}</a>
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
                         toggleLike(setLiked,setDisliked,review,liked,disliked)
                     }}>
                     {liked?(<AiFillLike/>):(<AiOutlineLike/>) }
                     </button>
                     <div className='mx-2'>
                     {review.likesCount}
                     </div>
                  </div>
                  <div className='d-flex m-2 justify-content-center align-items-center'>
                    <button className='btn btn-primary' onClick={()=>{
                      toggleDislike(setLiked,setDisliked,review,liked,disliked)
                    }}>
                    {disliked?(<AiFillDislike/>):(<AiOutlineDislike/>)}
                    </button>
                    <div className='mx-2'>
                    {review.dislikesCount}
                    </div>
                  </div>
                </div>

              </div>

              {review.authorUserId === authContext.decoded.userId && <div className='d-flex'>
                  <button className='btn btn-primary mx-2' onClick={()=>{setEditMode(true)}}><AiFillEdit/></button>
                  <button className='btn btn-primary mx-2' onClick={()=>handleDeleteReview(review.reviewId)}><RiDeleteBinFill/></button>
                </div>}
            </div>
          }
          { editMode && review.authorUserId === authContext.decoded.userId &&  <div className='edit-review'>
            <Formik initialValues={initialValues} enableReinitialize onSubmit={(values)=>{
              handleUpdateReview(values,review.reviewId)
              setEditMode(false)}}>
              {({setFieldValue,values})=>(<Form className='review-form'>
                <Field as="textarea" name="comment"/>
                <StarField setFieldValue={setFieldValue} stars={stars} setStars={setStars}/>
                <div className='m-2'>
                   <button className='btn btn-primary mx-2' onClick={()=>{setEditMode(false)}}>cancel</button>
                   <button className='btn btn-primary mx-2' type='submit' >save</button>
                </div>
              </Form>)}
            </Formik>
            </div>

          }
        </div>
  )
}

export default Review
