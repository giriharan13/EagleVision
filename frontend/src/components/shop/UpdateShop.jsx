import { Field, Form, Formik } from 'formik'
import React, { useEffect, useRef, useState } from 'react'
import { shopValidationSchema } from '../../validation/ShopValidation'
import { getShopById, updateShop } from '../../service/BackendApi'
import toast from 'react-hot-toast'
import { useNavigate, useParams } from 'react-router-dom'
import { useAuth } from '../../security/AuthContext'
import { Country,State,City } from 'country-state-city'
import { shopCategories } from '../../config/ShopCategories'
import { MapContainer,TileLayer, useMap} from 'react-leaflet'
import ShopLocationMarker from './ShopLocationMarker'

export default function UpdateShop(props) {

    const navigate = useNavigate();
    

    const authContext = useAuth();

    const mapRef = useRef();

    const {id} = useParams();

    const [isLoading,setIsLoading] = useState(true);

    const [countries,setCountries] =  useState(Country.getAllCountries())

    const [states,setStates] = useState([])

    const [cities,setCities] = useState([])

    const [selectedCountry,setSelectedCountry] = useState("")
    
    const [selectedState,setSelectedState] = useState("")

    const [position,setPosition] = useState([0,0])

    const [shopImageData,setShopImageData] = useState(null)

    const [imageType,setImageType] = useState(null)
    

    const handleCountryChange = (country)=>{
            console.log(country)
            setSelectedCountry(country)
            setStates(State.getStatesOfCountry(country.isoCode))
            setCities([])
            setSelectedState(null)
    }
    
    
    const handleStateChange = (state)=>{
        console.log(state)
        setSelectedState(state)
        setCities(City.getCitiesOfState(selectedCountry.isoCode,state.isoCode))
    }


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
                displayName:"Category",
                name:"shopCategory",
                sub:-1,
                categories:shopCategories
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
                        type:"select"
                    },
                    {
                        displayName:"State",
                        name:"state",
                        type:"select"
                    },
                    {
                        displayName:"City",
                        name:"city",
                        type:"select"
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
            },
        ]
    
        const [initialValues,setInitialValues] = useState({
            shopName:"",
            description:"",
            shopImage:"",
            shopCategory:"CONVENIENCE",
            contactNumber:"",
            line1:"",
            line2:"",
            country:"",
            state:"",
            city:"",
            pincode:"",
            openingTime:"",
            closingTime:"",
            lattitude:"",
            longitude:""
    
        })


    useEffect(()=>{
        getShopById(id,position).then((response)=>{
            if(response.data.vendorId !== authContext.decoded.userId && !isLoading){
                setIsLoading(true);
                toast.error("You are not allowed to access this resource");
                navigate('/home');
            }
            setStates(State.getStatesOfCountry(response.data.address.country))
            setCities(City.getCitiesOfState(response.data.address.country,response.data.address.state))
            let state = states.find((s)=>s.isoCode===response.data.address.state);
            let country = countries.find((c)=>c.isoCode===response.data.address.country);
            setSelectedCountry(country)
            setSelectedState(state)
            setPosition([response.data.shopLocation.lattitude,response.data.shopLocation.longitude])
            setShopImageData(response.data.shopImageData)
            setImageType(response.data.imageType)
            setInitialValues({
                shopName:response.data.shopName,
                description:response.data.description,
                contactNumber:response.data.contactNumber,
                shopCategory:response.data.shopCategory,
                line1:response.data.address.line1,
                line2:response.data.address.line2,
                country:country?.isoCode,
                state:state?.isoCode,
                city:response.data.address.city,
                pincode:response.data.address.pincode,
                openingTime:response.data.hours.openingTime,
                closingTime:response.data.hours.closingTime,
                lattitude:response.data.shopLocation.lattitude,
                longitude:response.data.shopLocation.longitude
            })
            setIsLoading(false)
        }).catch((error)=>{
            console.log(error)
            toast.error("You are not allowed to access this resource");
            navigate("/home");
        })
    },[id,navigate,authContext.decoded.userId,isLoading])




    const handleUpdateShop = async (values)=>{
        let formData = new FormData()

        let updateShopRequest ={
            shopName:values.shopName,
            description:values.description,
            shopCategory:values.shopCategory,
            contactNumber:values.contactNumber,
            address:{
                line1:values.line1,
                line2:values.line2,
                country:values.country,
                state:values.state,
                city:values.city,
                pincode:values.pincode,
            },
            hours:{
                openingTime:values.openingTime,
                closingTime:values.closingTime
            },
            shopLocation:{
                lattitude:values.lattitude,
                longitude:values.longitude
            },
            userName:authContext.decoded.sub,
        }

        formData.append("updatedShopDTO",new Blob([JSON.stringify(updateShopRequest)],{type:"application/json"}))

        if(values.shopImageFile) formData.append("shopImageFile",values.shopImageFile)
        else formData.append("shopImageFile",null)

        return updateShop(id,formData).then((response)=>{
            toast.success("Shop updated successfully!");
            navigate("/shops");
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
                    <p className='text-center fw-bold h2'>Update Shop</p>
                    <Formik validationSchema={shopValidationSchema} initialValues={initialValues} enableReinitialize
                    onSubmit={(values)=>{handleUpdateShop(values)}}>
                        {
                            ({errors,touched,setFieldValue,handleChange,getFieldProps})=>{
                                return (
                                    <Form>
                                        <div className='container-fluid'>
                                        <div className='row justify-content-center '>
                                        <div className='col-12 col-lg-6 order-1 align-items-center justify-content-center mt-3'>
                                        {
                                            createShopFields.map((field,index)=>(
                                                <div key={index} className='row'>
                                                    <label className='form-label fw-bold' for={field.name}>{field.displayName}</label>
                                                    <div className="col-10 align-items-center justify-content-center " >
                                                    {field?.sub===0 &&  (<Field type={field.type} className="form-control" name={field.name} id={field.name}></Field>)}
                                                    {field?.sub===-1 && (<Field as="select" className="form-select" name={field.name} id={field.name}>
                                
                                                                            {field.categories.map((category)=>{
                                                                                return <option value={category.type}>{category.display}</option>
                                                                            })}
                                                                        </Field>)}
                                            
                                                    {field?.sub>0 && (<div>
                                                                    {
                                                                        field.fields.map((subField,subIndex)=>(
                                                                            <div key={subIndex}>
                                                                                <label className='form-label' for={subField.name}>{subField.displayName}</label>
                                                                                {(subField.type==="text" || subField.type==="time") && <Field type={subField.type} className="form-control" name={subField.name} id={subField.name}>
                                                                                </Field>}
                                                                                {subField.type==="select" && subField.name==="country"  && <Field as={subField.type} className="form-select" name={subField.name} id={subField.name} 
                                                                                onChange={
                                                                                        (e)=>{
                                                                                            handleChange(e)
                                                                                            handleCountryChange(countries.find((c)=> e.target.value === c.isoCode))
                                                                                        }
                                                                                    
                                                                                }>
                                                                                    <option value="Select country"> Select Country</option>
                                                                                {
                                                                                        countries.map(
                                                                                            (country)=>{
                                                                                                return<option key={country.isoCode} value={country.isoCode}>
                                                                                                    {country.name}
                                                                                                </option>
                                                                                            }
                                                                                        )
                                                                                }
                                                                                </Field>}
                                                                                {subField.type==="select" && subField.name==="state"  && <Field as={subField.type} className="form-select" name={subField.name} id={subField.name} 
                                                                                disabled={selectedCountry===null}
                                                                                onChange={
                                                                                        (e)=>{
                                                                                            handleChange(e)
                                                                                            handleStateChange(states.find((s)=>s.isoCode===e.target.value))
                                                                                        }
                                                                                    
                                                                                }>
                                                                                    <option value="Select state">Select state</option>
                                                                                {
                                                                                    
                                                                                        states.map(
                                                                                            (state,index)=>{
                                                                                                return<option key={index} value={state.isoCode}>
                                                                                                    {state.name}
                                                                                                </option>
                                                                                            }
                                                                                        )
                                                                                }
                                                                                </Field>}
                                                                                {subField.type==="select" && subField.name==="city"  && <Field as={subField.type} className="form-select" name={subField.name} id={subField.name} 
                                                                                disabled={selectedState===null}
                                                                                onChange={
                                                                                        (e)=>{
                                                                                           handleChange(e)
                                                                                        }
                                                                                    
                                                                                }>
                                                                                    <option value="Select city">Select city</option>
                                                                                {
                                                                                        cities.map(
                                                                                            (city,index)=>{
                                                                                                return<option key={index} value={city.isoCode}>
                                                                                                    {city.name}
                                                                                                </option>
                                                                                            }
                                                                                        )
                                                                                }
                                                                                </Field>}
                                                                                {touched[subField.name] && errors[subField.name] && <div className="alert alert-warning">{errors[subField.name]}</div> }
                                                                            </div>
                                                                        ))
                                                                    }
                                                                </div>)}
                                                    {touched[field.name] && errors[field.name] && <div className="alert alert-warning">{errors[field.name]}</div> }
                                                    </div>
                                                    </div>
                                            ))
                                        }
                                        </div>
                                        <div className='col-12 col-lg-6 order-2 justify-content-center mt-3'>
                                        <div>
                                            <img className='img-thumbnail' src={`data:${imageType};base64,${shopImageData}`} alt="shopImage"/>
                                            <label className='form-label fw-bold' for="shopImageFile">Shop Image</label>
                                             <input type='file' className='form-control' accept="image/jpeg" name="shopImageFile" id="shopImageFile" onChange={(event)=>{                            
                                                                setFieldValue("shopImageFile",event.target.files[0])
                                                            }}></input>
                                        </div>
                                        <label className='form-label fw-bold'>Shop Location 🦅 </label>
                                        <MapContainer center={position} zoom={13} scrollWheelZoom={false} style={{
                                                                border: "1px solid black",
                                                                borderRadius: 8,
                                                                height:400, 
                                                                width:"90%"  
                                                              }  } ref={mapRef}>
                                                                <label> Shop Location</label>
                                                                <TileLayer attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                                                                url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" ></TileLayer>
                                                                <ShopLocationMarker setFieldValue={setFieldValue}   position={position} setPosition={setPosition}/>
                                        </MapContainer>
        
                                        </div>
                                        
                                        </div>
                                        <div className='d-flex justify-content-center align-items-center'>
                                                <button className="btn btn-success" type="submit">Update</button>
                                        </div>
                                    </div>
                                    </Form>
                                )
                            }
                        }
                    </Formik>
                    {isLoading && <div>Loading..</div>}
                </div>
                </div>
                </div>
                </div>
                </div>
                </div>
                </div>
  )
}

