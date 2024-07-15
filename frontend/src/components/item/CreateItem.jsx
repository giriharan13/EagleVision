import { Field, Form, Formik } from 'formik'
import React from 'react'
import { createItem } from '../../service/BackendApi';
import { useNavigate, useParams } from 'react-router-dom';
import toast from 'react-hot-toast';

function CreateItem() {

    const {shopId} = useParams();

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

    const initialValues = {
        itemName:"",
        itemDescription:"",
        itemPrice:""
    }

    async function handleCreateItem(values){
        return createItem(shopId,values).then((response)=>{
            toast.success("Item created successfully");
            navigate(`/shops/${shopId}`);
        }).catch((error)=>{
            toast.error("An error occurred");
            navigate(`/shops/${shopId}`);
        })
    }
    return (
        <div>
            <Formik initialValues={initialValues} enableReinitialize onSubmit={handleCreateItem} >
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
                    <button className="btn btn-success" type="submit">Create</button>
                </div>

                </Form>    
            </Formik>        
        </div>
    )
}

export default CreateItem
