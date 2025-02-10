import Logo from "../../images/eaglevisionlogo.png"
import { features } from "../../config/Features"
import { Link, useNavigate } from "react-router-dom"
import { motion } from "motion/react"
import { BuyerSubscriptions } from "../../config/Subscription"
import { duration } from "moment/moment"
import { animate, delay } from "motion"

import VendorsLogo from "../../images/eaglevisionforvendors.png"
import { useEffect } from "react"

export default function LandingPage({featuresRef,pricingRef,contactRef}){

    const text = "Discover fresh, affordable, and unique products from local street vendors. Support small businesses and enjoy a seamless shopping experience!"

    const navigateTo = useNavigate()

    return <div className="container-fluid mx-0  p-0 align-items-center justify-content-center">
        <div className="container-fluid mx-0 p-0">
            <section className="container-fluid p-0 mx-0" id="hero">
                <div className="m-5">
                    <div className="d-flex mx-0 pt-2 bg-transparent rounded align-items-center justify-content-center mh-100 mb-5">
                        <img src={Logo} style={{width:400}}></img>
                    </div>
                    <div className="d-flex text-white flex-column">
                        <div className="d-flex align-items-center justify-content-center">
                            <h5 className="text-center" style={{fontFamily:"Kanit",fontWeight:700}}>Find Hidden Street Gems Near You!</h5>
                        </div>
                        <div className="d-flex align-items-center justify-content-center">
                            <h6 className="text-center" style={{fontFamily:"Kanit",fontWeight:400}} >
                            {text.split("").map((letter,index)=>{
                                return <motion.span key={index} 
                                initial={{ opacity: 0 }}
                                animate={ { opacity: 1 }}
                                transition={{ duration: 1.4, delay:  index*0.005 }}> 
                                    {letter}
                                </motion.span>
                            })}
                            </h6>
                            
                        </div>
                        {/* <h4 className="col-12 text-center">Explore unique products, and</h4>
                        <h4 className="col-12 text-center"> Support small businesses—all in one place!</h4> */}
                        <div className="d-flex align-items-center justify-content-center">
                                <motion.button className="btn btn-light rounded-5 m-2 fw-bold text-primary" 
                                whileHover={
                                    {scale:1.1}
                                }
                                transition={
                                    {ease:"easeInOut",duration:0.2}
                                }
                                
                                onClick={()=>{
                                    navigateTo("/login")
                                }} style={{fontFamily:"Kanit"}}> Start Eagling!</motion.button>
                        </div>
                        <div className="d-flex flex-column align-items-end justify-content-end">
                            <div className="d-flex flex-column align-items-center justify-content-center bg-light p-4 m-2  rounded-4 " >
                                <h5 className="text-center text-primary fw-bold" style={{fontFamily:"Kanit"}}>Also check out EagleVision for vendors!</h5>
                                <Link to="/vendors" className="navbar-brand"> 
                                    <img src={VendorsLogo} className="navbar-brand" style={{width:200}}/>
                                </Link>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <section ref={featuresRef} className="container-fluid p-0 mx-0" id="features" >
                <div className="bg-light m-0 p-0" style={{width:"100%"/*minHeight:"100vh"*/}}>
    
                    <div className="col p-2 align-items-center justify-content-center">
                        <div className="row mt-4">
                            <h3 className="text-primary text-center" style={{fontFamily:"Kanit"}}>Features</h3>
                        </div>
                        <ul className="d-flex  p-4 align-items-center justify-content-center list-unstyled">
                            {
                                features.map((feature)=>{
                                    return <li className="d-flex flex-column align-items-center align-self-stretch justify-content-center bg-primary rounded-4 p-4 m-4 flex-grow-1 w-25">
                                    <div className="d-flex align-items-center justify-content-center">
                                        <motion.div className="bg-white rounded-circle p-2 m-2" style={{width:"fit-content"}} 
                                        initial={{ opacity: 0, scale: 0 }}
                                        animate={{ opacity: 1, scale: 1 }}
                                        transition={{
                                            duration: 0.15,
                                            ease:"easeInOut"
                                        }}
                                        whileHover={{
                                            scale: 1.2 
                                        }}>
                                           <img src={feature.image} style={{width:25}}/>
                                        </motion.div>
                                    </div>
                                    <div className="d-flex flex-column text-primary text-center text-white">
                                        <h4 className="fw-bold" style={{fontFamily:"Kanit"}}>{feature.name}</h4>
                                        <p className="text-break" style={{fontFamily:"Kanit"}}>{feature.description}</p>
                                    </div>
                                    {
                                        feature?.inDevelopement && <div className="d-flex align-items-center justify-content-center p-2 rounded-4 bg-light">
                                            <h5 className="text-primary fw-bold">Coming soon..</h5>
                                        </div>
                                    }
                                </li>
                                })
                            }
                        </ul>
                    </div>
                </div>
            </section>
            <section ref={pricingRef} className="container-fluid p-0 mx-0" id="pricing">
                <div className="container-fluid bg-white bg-opacity-25 p-2">
                         <div className="row mt-3">
                            <h3 className="text-light text-center" style={{fontFamily:"Kanit"}}>Pricing</h3>
                        </div>
                    <div className="container">							
                    <div className="row text-center p-5">									
                        { BuyerSubscriptions.map((subscription,id)=>{
                            return <motion.div className="col-lg-4 mb-3 m-lg-0 col-sm-6 col-xs-12" initial={{scale:0}} animate={{scale:1.0,type:"easeOut"}} transition={{delay:0}} whileHover={{scale:1.1,type:"easeIn",transition:{duration:0.2,delay:0,ease:"easeIn"}}}>
                                
                                    <div className="bg-light text-primary p-5 rounded-5" key={id}>
                                    <div className="single-pricing">
                                        <div className="price-head">		
                                            <h2>{subscription.name} Tier</h2>
                                            <h1>{subscription.cost}₹</h1>
                                            <span>/Monthly</span>
                                        </div>
                                        <ul style={{listStyle:"none"}}>
                                            {subscription.features.map(
                                                (feature,id)=>{
                                                    return  <li key={id}>{feature}</li>
                                                }
                                            )}
                                        </ul>
                                        </div>
                                </div>
                        </motion.div>})
                     }
                    </div>
                </div>
                </div>
            </section>
            <section ref={contactRef} id="contactus">
                <div className="bg-primary p-2">
                    <div className="text-center  text-light mt-3">
                        <h3 className="" style={{fontFamily:"Kanit"}}>Contact Us</h3>
                    </div>
                    <div className="d-flex align-items-center justify-content-center">
                        <h6 className="text-light">Send your queries at 
                             <a href="mailto:eaglevision@testing.com" className="link-light"> eaglevision@testing.com</a></h6>
                    </div>
                </div>
            </section>
        </div>
    </div>
}