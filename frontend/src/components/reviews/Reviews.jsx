import React from 'react'
import Review from './Review'
import { Field, Form, Formik } from 'formik'
import StarField from '../starField/StarField'

function Reviews({handleCreateReview,handleUpdateReview,handleDeleteReview,reviews,refresh,setRefresh,isOwner,element}) {

    const initialValues = {
        "comment":"",
        "stars":"0",

    }

    return (
        <div className="shop-reviews">
            <div className="heading">
                <h2>Reviews</h2>
            </div>

            {!isOwner && 
                            <Formik initialValues={initialValues} enableReinitialize onSubmit={(values)=>{handleCreateReview(values)}}>
                                {({setFieldValue})=><Form className="review-form">
                                    <h3>Post your review</h3>
                                    <Field as="textarea" name="comment"  placeholder="Your comment here.."></Field>
                                    <StarField setFieldValue={setFieldValue} stars={1}/>
                                    <button type="submit" className="button-primary">Post</button>
                                </Form>}
                            </Formik>
                        }

            <div className="reviews">
                {reviews?.length===0 && <h3>No reviews on this {element} yet.</h3>}
                {reviews?.map((review,index)=>{
                    return <Review key={index} review={review} refresh={refresh} setRefresh={setRefresh} handleUpdateReview={handleUpdateReview} handleDeleteReview={handleDeleteReview}/>
                })}
            </div>
        </div>
    )
}

export default Reviews
