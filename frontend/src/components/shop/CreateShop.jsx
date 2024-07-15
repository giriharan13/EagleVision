import { Field, Form, Formik } from 'formik'
import React from 'react'
import { shopValidationSchema } from '../../validation/ShopValidation'
import { createShop } from '../../service/BackendApi'
import toast from 'react-hot-toast'
import { useNavigate } from 'react-router-dom'
import { useAuth } from '../../security/AuthContext'

function CreateShop() {

    const navigate = useNavigate();
    

    const authContext = useAuth();

    const createShopFields = [
        {
            displayName:"Shop Name",
            name:"shopName",
            type:"text",
            sub:0

        },
        {
            displayName:"Description",
            name:"description",
            type:"text",
            sub:0
        },
        {
            displayName:"Contact Number",
            name:"contactNumber",
            type:"text",
            sub:0
        },
        {
            displayName:"Address",
            sub:6,
            fields:[
                {
                    displayName:"Line 1",
                    name:"line1",
                    type:"text"
                },
                {
                    displayName:"Line 2",
                    name:"line2",
                    type:"text"
                },
                {
                    displayName:"Country",
                    name:"country",
                    type:"text"
                },
                {
                    displayName:"State",
                    name:"state",
                    type:"text"
                },
                {
                    displayName:"City",
                    name:"city",
                    type:"text"
                },
                {
                    displayName:"Pincode",
                    name:"pincode",
                    type:"text"
                },

            ]
        },
        {
            displayName:"Shop Hours",
            sub:2,
            fields:[
                {
                    displayName:"Opening time",
                    name:"openingTime",
                    type:"time"
                },
                {
                    displayName:"Closing time",
                    name:"closingTime",
                    type:"time"
                }
            ]
        }
    ]

    const initialValues = {
        shopName:"",
        description:"",
        contactNumber:"",
        line1:"",
        line2:"",
        country:"",
        state:"",
        city:"",
        openingTime:"",
        closingTime:"",

    }

    const handleCreateShop = async (values)=>{
        return createShop({
            shopName:values.shopName,
            description:values.description,
            contactNumber:values.contactNumber,
            address:{
                line1:values.line1,
                line2:values.line2,
                country:values.country,
                state:values.state,
                city:values.city
            },
            hours:{
                openingTime:values.openingTime,
                closingTime:values.closingTime
            },
            userName:authContext.decoded.sub,
        }).then((response)=>{
            toast.success("Shop created successfully!");
            navigate("/shops");
        })
    }

    return (
        <div className=''>
            <Formik validationSchema={shopValidationSchema} initialValues={initialValues} enableReinitialize
            onSubmit={(values)=>{handleCreateShop(values)}}>
                {
                    ({errors,touched})=>{
                        return (
                            <Form>
                                {
                                    createShopFields.map((field,index)=>(
                                        <div key={index}>
                                            <label>{field.displayName}</label>
                                            {!field?.sub &&  (<Field type={field.type} className="form-control" name={field.name} ></Field>)}
                                            {field?.sub>0 && (<div>
                                                            {
                                                                field.fields.map((subField,subIndex)=>(
                                                                    <div key={subIndex}>
                                                                        <label>{subField.displayName}</label>
                                                                        <Field type={subField.type} className="form-control" name={subField.name}></Field>
                                                                        {touched[subField.name] && errors[subField.name] && <div className="alert alert-warning">{errors[subField.name]}</div> }
                                                                    </div>
                                                                ))
                                                            }
                                                        </div>)}
                                            {touched[field.name] && errors[field.name] && <div className="alert alert-warning">{errors[field.name]}</div> }
                                        </div>
                                    ))
                                }
                                <div>
                                    <button className="btn btn-success" type="submit">Create</button>
                                </div>
                            </Form>
                        )
                    }
                }
            </Formik>
        </div>
  )
}

export default CreateShop
