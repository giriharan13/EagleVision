import React, { useState } from 'react'
import { FaRegStar,FaStar } from "react-icons/fa";
import './Review.scss';
import { AiFillEdit } from "react-icons/ai";
import { RiDeleteBinFill } from "react-icons/ri";
import { useAuth } from '../../security/AuthContext';
import { Field, Form, Formik } from 'formik';
import StarField from '../starField/StarField';
import { useNavigate, useParams } from 'react-router-dom';
import toast from 'react-hot-toast';


function Review({review,key,setRefresh,refresh,handleUpdateReview,handleDeleteReview}) {
  const [editMode,setEditMode] = useState(false);

  const authContext = useAuth();

  const navigate = useNavigate();

  const {shopId} = useParams();

  const stars = [1,2,3,4,5];

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
                {review.authorUserName}
              </div>
              <div className='content'>
                <div className='comment'>
                  {review.comment}
                </div>
                <div className='stars'>
                  {
                    stars.map((value)=> (review.stars >= value)? <FaStar/> : <FaRegStar/> )

                  }

                </div>
              </div>

              {review.authorUserId === authContext.decoded.userId && <div>
                  <button className='button-primary' onClick={()=>{setEditMode(true)}}><AiFillEdit/></button>
                  <button className='button-primary' onClick={()=>handleDeleteReview(review.reviewId)}><RiDeleteBinFill/></button>
                </div>}
            </div>
          }
          { editMode && review.authorUserId === authContext.decoded.userId &&  <div className='edit-review'>
            <Formik initialValues={initialValues} enableReinitialize onSubmit={(values)=>{
              handleUpdateReview(values,review.reviewId)
              setEditMode(false)}}>
              {({setFieldValue,values})=>(<Form className='review-form'>
                <Field as="textarea" name="comment"/>
                <StarField setFieldValue={setFieldValue} stars={review.stars}/>
                <div>
                   <button className='button-primary' onClick={()=>{setEditMode(false)}}>cancel</button>
                   <button className='button-primary' type='submit' >save</button>
                </div>
              </Form>)}
            </Formik>
            </div>

          }
        </div>
  )
}

export default Review
