import { Field, Form, Formik } from 'formik'
import React, { useEffect, useState } from 'react'
import { getItemById, updateItem } from '../../service/BackendApi';
import { useNavigate, useParams } from 'react-router-dom';
import toast from 'react-hot-toast';
import { useAuth } from '../../security/AuthContext';

function UpdateItem() {

    const {shopId,itemId} = useParams();

    const navigate = useNavigate();

    const authContext = useAuth();

    const [position,setPosition] = useState(authContext?.position);

    const [item,setItem] = useState(null);

    const [isLoading,setIsLoading] = useState(true)


    useEffect(()=>{
        setPosition(authContext?.position)
    },[authContext?.position])

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
        getItemById(shopId,itemId,position).then((response)=>{
            setInitialValues(response.data);
            setItem(response.data)
            setIsLoading(false)
        }).catch((error)=>{
            toast.error("An error occurred.");
            navigate(`/shops/${shopId}`);
            console.log(error);
        })
    },[])

    async function handleUpdateItem(values){
        let formData = new FormData()

        let item = {
            itemName:values.itemName,
            itemDescription:values.itemDescription,
            itemPrice:values.itemPrice
        }


        formData.append("item",new Blob([JSON.stringify(item)],{type:"application/json"}))


        if(values.itemImageFile) formData.append("itemImageFile",values.itemImageFile)
        else formData.append("itemImageFile",null)


        return updateItem(shopId,itemId,formData).then((response)=>{
            toast.success("Item updated successfully");
            navigate(`/shops/${shopId}`);
        }).catch((error)=>{
            toast.error("An error occurred");
            navigate(`/shops/${shopId}`);
        })
    }
    return <div className='container h-200'>
        {!isLoading && <div className='row d-flex justify-content-center align-items-center h-200'> 
        <div className='col-lg-12 col-xl-12'>
        <div className='card text-black' style={{borderRadius: "25px"}}>
        <div className='card-body md-5'>
        <div className='row justify-content-center'>
        <div className='col-12 col-lg-12 col-xl-12'>
            <p className='text-center fw-bold h2'>Create Item</p>
            <Formik initialValues={initialValues} enableReinitialize onSubmit={handleUpdateItem} >
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
                                                <label className='form-label fw-bold' for="itemImageFile1">Item Image</label>
                                                <img alt="test" src={`data:${item.imageType};base64,${item.itemImageDataB64}`}  className="img-thumbnail"></img>
                                                <label className='form-label fw-bold' for="itemImageFile">Update Item Image</label>
                                                    <input type='file' className='form-control' accept="image/jpeg" name="itemImageFile" id="itemImageFile" onChange={(event)=>{                            
                                                                    setFieldValue("itemImageFile",event.target.files[0])
                                                                }}></input>
                                            </div>
                
                
                
                                        <div>
                                            <button className="btn btn-success" type="submit">Update</button>
                                        </div>
                
                                        </Form>)   }}   
            </Formik>        
            </div>
                </div>
                </div>
                </div>
                </div>
                </div>}
        </div>
}

export default UpdateItem
