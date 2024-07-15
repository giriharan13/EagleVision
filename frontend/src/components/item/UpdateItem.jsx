import { Field, Form, Formik } from 'formik'
import React, { useEffect, useState } from 'react'
import { getItemById, updateItem } from '../../service/BackendApi';
import { useNavigate, useParams } from 'react-router-dom';
import toast from 'react-hot-toast';

function UpdateItem() {

    const {shopId,itemId} = useParams();

    const navigate = useNavigate();

    const createItemFields = [
        {
            displayName:"Name",
            name:"itemName",
            type:"text"
        },
        {
            displayName:"Description",
            name:"itemDescription",
            type:"text"
        },
        {
            displayName:"Price",
            name:"itemPrice",
            type:"text"
        },
    ]

    const [initialValues,setInitialValues] = useState({
        itemName:"",
        itemDescription:"",
        itemPrice:""
    })

    useEffect(()=>{
        getItemById(shopId,itemId).then((response)=>{
            setInitialValues(response.data);
        }).catch((error)=>{
            toast.error("An error occurred.");
            navigate(`/shops/${shopId}`);
            console.log(error);
        })
    },[])

    function handleUpdateItem(values){
        return updateItem(shopId,itemId,values).then((response)=>{
            toast.success("Item updated successfully");
            navigate(`/shops/${shopId}`);
        }).catch((error)=>{
            toast.error("An error occurred");
            navigate(`/shops/${shopId}`);
        })
    }
    return (
        <div>
            <Formik initialValues={initialValues} enableReinitialize onSubmit={handleUpdateItem} >
                <Form>
                    {
                        createItemFields.map((field,index)=>(
                            <div key={index}>
                                <label>{field.displayName}</label>
                                <Field type={field.type} name = {field.name}></Field>
                            </div>
                        ))
                    }

                <div>
                    <button className="btn btn-success" type="submit">Update</button>
                </div>

                </Form>    
            </Formik>        
        </div>
    )
}

export default UpdateItem
