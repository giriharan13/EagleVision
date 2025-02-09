import React, { useState } from 'react'
import Review from './Review'
import { Field, Form, Formik } from 'formik'
import StarField from '../starField/StarField'

function Reviews({handleCreateReview,handleUpdateReview,handleDeleteReview,reviews,isOwner,element,toggleLike,toggleDislike}) {

    const initialValues = {
        "comment":"",
        "stars":"1",

    }

    const [stars,setStars] = useState(1);



    return (
        <div className="shop-reviews text-light ">
            <div className="heading">
                <h2>Reviews</h2>
            </div>

            {!isOwner && 
                            <Formik initialValues={initialValues} enableReinitialize onSubmit={(values,{resetForm,setFieldValue})=>{
                                handleCreateReview(values).then(()=>{
                                    resetForm();
                                    setStars(1);
                                })
                                }}>
                                {({setFieldValue})=><Form className="review-form bg-white text-primary">
                                    <h3>Post your review</h3>
                                    <Field className="bg-light" as="textarea" name="comment"  placeholder="Your comment here.."></Field>
                                    <StarField setFieldValue={setFieldValue} stars={stars} setStars={setStars}/>
                                    <button type="submit" className="btn btn-primary">Post</button>
                                </Form>}
                            </Formik>
                        }

            <div className="reviews bg-transparent text-primary">
                {reviews?.length===0 && <h3>No {element} reviews yet.</h3>}
                {reviews?.map((review,index)=>{
                    return <Review key={index} review={review} handleUpdateReview={handleUpdateReview} handleDeleteReview={handleDeleteReview} toggleLike={toggleLike} toggleDislike={toggleDislike}/>
                })}
            </div>
        </div>
    )
}

export default Reviews
