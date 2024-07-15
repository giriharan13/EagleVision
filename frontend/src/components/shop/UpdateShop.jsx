import { Field, Form, Formik } from 'formik'
import React, { useEffect, useState } from 'react'
import { shopValidationSchema } from '../../validation/ShopValidation'
import { getShopById, updateShop } from '../../service/BackendApi'
import toast from 'react-hot-toast'
import { useNavigate, useParams } from 'react-router-dom'
import { useAuth } from '../../security/AuthContext'

export default function UpdateShop(props) {

    const navigate = useNavigate();
    

    const authContext = useAuth();

    const {id} = useParams();

    const [isLoading,setIsLoading] = useState(true);

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

    const [initialValues,setInitialValues] = useState({
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

    });


    useEffect(()=>{
        getShopById(id).then((response)=>{
            if(response.data.vendorId !== authContext.decoded.userId && !isLoading){
                setIsLoading(true);
                toast.error("You are not allowed to access this resource");
                navigate('/home');
            }
            setInitialValues({
                shopName:response.data.shopName,
                description:response.data.description,
                contactNumber:response.data.contactNumber,
                line1:response.data.address.line1,
                line2:response.data.address.line2,
                country:response.data.address.country,
                state:response.data.address.state,
                city:response.data.address.city,
                openingTime:response.data.hours.openingTime,
                closingTime:response.data.hours.closingTime,
            })
            setIsLoading(false)
        }).catch((error)=>{
            toast.error("You are not allowed to access this resource");
            navigate("/home");
        })
    },[id,navigate,authContext.decoded.userId,isLoading])




    const handleCreateShop = async (values)=>{
        return updateShop(id,{
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
            toast.success("Shop updated successfully!");
            navigate("/shops");
        })
    }

    return (
        <div className=''>
            {!isLoading && <Formik validationSchema={shopValidationSchema} initialValues={initialValues} enableReinitialize
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
                                    <button className="btn btn-success" type="submit">Update</button>
                                </div>
                            </Form>
                        )
                    }
                }
            </Formik> }
            {isLoading && <div>Loading..</div>}
        </div>
  )
}

