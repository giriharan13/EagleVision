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
        itemPrice:"",
        itemImageFile:""
    }

    async function handleCreateItem(values){

        let formData = new FormData()

        let item = {
            itemName:values.itemName,
            itemDescription:values.itemDescription,
            itemPrice:values.itemPrice
        }


        formData.append("item",new Blob([JSON.stringify(item)],{type:"application/json"}))

        formData.append("itemImageFile",values.itemImageFile)



        return createItem(shopId,formData).then((response)=>{
            toast.success("Item created successfully");
            navigate(`/shops/${shopId}`);
        }).catch((error)=>{
            toast.error("An error occurred");
            navigate(`/shops/${shopId}`);
        })
    }
    return (
        <div className='container h-200'>
                <div className='row d-flex justify-content-center align-items-center h-200'> 
                <div className='col-lg-12 col-xl-12'>
                <div className='card text-black' style={{borderRadius: "25px"}}>
                <div className='card-body md-5'>
                <div className='row justify-content-center'>
                <div className='col-12 col-lg-12 col-xl-12'>
                    <p className='text-center fw-bold h2'>Create Item</p>
                    <Formik initialValues={initialValues} enableReinitialize onSubmit={handleCreateItem} >
                        {({errors,setFieldValue})=>{return (<Form>
                            {
                                createItemFields.map((field,index)=>(
                                    <div key={index}>
                                        <label>{field.displayName}</label>
                                        <Field type={field.type} name = {field.name} className="form-control"></Field>
                                    </div>
                                ))
                            }
                            <div>
                                <label className='form-label fw-bold' for="shopImageFile">Item Image</label>
                                    <input type='file' className='form-control' accept="image/jpeg" name="itemImageFile" id="itemImageFile" onChange={(event)=>{                            
                                                    setFieldValue("itemImageFile",event.target.files[0])
                                                }}></input>
                            </div>



                        <div>
                            <button className="btn btn-success" type="submit">Create</button>
                        </div>

                        </Form>)   }} 
                    </Formik>        
                </div>
                </div>
                </div>
                </div>
                </div>
                </div>
        </div>
    )
}

export default CreateItem
