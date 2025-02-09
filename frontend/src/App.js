import { BrowserRouter, Navigate, Route, Routes} from 'react-router-dom';
import './App.css';
import Header from './components/header/Header';
import Footer from './components/footer/Footer';
import ListShops from './components/shop/ListShops';
import Shop from './components/shop/Shop';
import "../node_modules/bootstrap/dist/css/bootstrap.min.css"
import '../node_modules/bootstrap/dist/js/bootstrap.bundle.min.js'
import ListPings from './components/pings/ListPings';
import Home from './components/home/Home';
import AuthProvider, { useAuth } from './security/AuthContext';
import Login from './components/login/Login';
import Signup from './components/signup/Signup';
import CreateShop from './components/shop/CreateShop';
import toast, {Toaster} from 'react-hot-toast';
import ListMyShops from './components/shop/ListMyShops';
import UpdateShop from './components/shop/UpdateShop';
import CreateItem from './components/item/CreateItem';
import Item from './components/item/Item';
import UpdateItem from './components/item/UpdateItem';
import EagleVision from './components/eaglevision/EagleVision';
import { getAllUnreadNotifications, getShops } from './service/BackendApi';
import AllNotifications from './components/notifications/AllNotifications.jsx';
import { useEffect, useRef, useState } from 'react';
import ListReviewsForVendor from './components/reviews/ListReviewsForVendor.jsx';
import ListPingsForVendor from './components/pings/ListPingsForVendor.jsx';
import Subscription from './components/subscription/Subscription.jsx';
import SubscriptionHistory from './components/subscription/SubscriptionHistory.jsx';
import SetMarker from './components/shop/SetMarker.jsx';
import UserProfile from './components/userprofile/UserProfile.jsx';
import Items from './components/item/Items.jsx';
import LandingPage from './components/landing/LandingPage.jsx';
import ChangePassword from './components/account/ChangePassword.jsx';
import LandingPageForVendors from './components/landing/LandingPageForVendors.jsx';

function AuthenticatedRoute({children}){
  const authContext = useAuth();
  if(authContext.isAuthenticated){
    console.log(authContext.decoded);
    return children;
  }
  return <Navigate to="/"/>
}

function AuthorizedRoute({children,roles}){
  const authContext = useAuth();
  if(roles.some((role)=>authContext.decoded.scope.includes(role))){
    return children;
  }
  return <Navigate to="/home"/>
}


function App() {

  const [bellNotifications,setBellNotifications] = useState([])

  const [refreshBellNotifications,setRefreshBellNotifications] = useState(false)


  const featuresRef = useRef(null)

  const pricingRef = useRef(null)

  const contactRef = useRef(null)





  return (
    <AuthProvider>
      <BrowserRouter>
        <Header bellNotifications={bellNotifications} setBellNotifications={setBellNotifications} refreshBellNotifications={refreshBellNotifications} setRefreshBellNotifications={setRefreshBellNotifications} featuresRef={featuresRef} pricingRef={pricingRef} contactRef={contactRef} ></Header>
        <div className="container-fluid p-0 bg-transparent mx-0 h-100" style={{minHeight:"90vh"}}>
        <Routes>
            <Route path="/" element={
              <LandingPage featuresRef={featuresRef} pricingRef={pricingRef} contactRef={contactRef}/>
            }
              />
              <Route path="/vendors" element={
              <LandingPageForVendors featuresRef={featuresRef} pricingRef={pricingRef} contactRef={contactRef}/>
            }
              />
            <Route path="/login" element={
              <Login/>
            }/>
            <Route path="/signup" element={
              <Signup/>
            }/>
            <Route path="/home" element={
              <AuthenticatedRoute>
                <Home/>
              </AuthenticatedRoute>
            }/>
            <Route path="/shops" element={
              <AuthenticatedRoute>
                <AuthorizedRoute roles={["BUYER"]}>
                  <ListShops getShops={getShops}/>
                  </AuthorizedRoute>
              </AuthenticatedRoute>
            }/>
            <Route path="/shops" element={
              <AuthenticatedRoute>
                <AuthorizedRoute roles={["BUYER"]}>
                  <ListShops getShops={getShops}/>
                  </AuthorizedRoute>
              </AuthenticatedRoute>
            }/>
            <Route path="/shops/:shopId" element={
              <AuthenticatedRoute>
                <Shop/>
              </AuthenticatedRoute>
            }/>
            <Route path="/shops/:shopId/items/:itemId/pings" element={
              <AuthenticatedRoute>
                <ListPings/>
              </AuthenticatedRoute>
              }/>
              <Route path="/create/shop" element={
                <AuthenticatedRoute>
                  <AuthorizedRoute roles={["VENDOR"]}>
                    <CreateShop/>
                  </AuthorizedRoute>
                </AuthenticatedRoute>
              }/>
              <Route path="/shops/:shopId/create/item" element={
                <AuthenticatedRoute>
                  <AuthorizedRoute roles={["VENDOR"]}>
                    <CreateItem/>
                  </AuthorizedRoute>
                </AuthenticatedRoute>
              }/>
              <Route path="/myshops" element={
                <AuthenticatedRoute>
                  <AuthorizedRoute roles={["VENDOR"]}>
                    <ListMyShops/>
                  </AuthorizedRoute>
                </AuthenticatedRoute>
              }/>
              <Route path="/update/shops/:id" element={
                <AuthenticatedRoute>
                  <AuthorizedRoute roles={["VENDOR"]}>
                    <UpdateShop/>
                  </AuthorizedRoute>
                </AuthenticatedRoute>
              }/>
              <Route path="/update/shops/:shopId/items/:itemId" element={
                <AuthenticatedRoute>
                  <AuthorizedRoute roles={["VENDOR"]}>
                    <UpdateItem/>
                  </AuthorizedRoute>
                </AuthenticatedRoute>
              }/>
              <Route path="/shops/:shopId/items/:itemId" element={
                <AuthenticatedRoute>
                    <Item/>
                </AuthenticatedRoute>
              }/>
              <Route path="/eaglevision" element={
                <AuthenticatedRoute>
                <AuthorizedRoute roles={["BUYER"]}>
                  <EagleVision/>
                </AuthorizedRoute>
              </AuthenticatedRoute>
              }/>
              <Route path="/pings" element={
                <AuthenticatedRoute>
                <AuthorizedRoute roles={["VENDOR"]}>
                  <ListPingsForVendor/>
                </AuthorizedRoute>
              </AuthenticatedRoute>
              }/>
              <Route path="/reviews" element={
                <AuthenticatedRoute>
                <AuthorizedRoute roles={["VENDOR"]}>
                  <ListReviewsForVendor/>
                </AuthorizedRoute>
              </AuthenticatedRoute>
              }/>
              <Route path="/notifications" element={
                <AuthenticatedRoute>
                  <AllNotifications setBellNotifications={setBellNotifications} refreshBellNotifications={refreshBellNotifications} setRefreshBellNotifications={setRefreshBellNotifications}/>
                </AuthenticatedRoute>
              }
              />
              <Route path="/subscription" element={
                <AuthenticatedRoute>
                  <Subscription/>
                </AuthenticatedRoute>
              }
              />
              <Route path="/subscription/history" element={
                <AuthenticatedRoute>
                  <SubscriptionHistory/>
                </AuthenticatedRoute>
              }
              />
              <Route path="/myshops/update/shops/:shopId/marker" element={
                <AuthenticatedRoute>
                  <AuthorizedRoute roles={["VENDOR"]}>
                    <SetMarker/>
                  </AuthorizedRoute>
                </AuthenticatedRoute>  
              }
              />
              <Route path="/profile/:userId" element={
                <AuthenticatedRoute>
                  <UserProfile/>
                </AuthenticatedRoute>
              }/>
              <Route path="/items" element={
                <AuthenticatedRoute>
                  <AuthorizedRoute roles={["BUYER"]}>
                     <Items/>
                    </AuthorizedRoute>
                </AuthenticatedRoute>
              }/>
              <Route path="/account/changePassword" element={
                <AuthenticatedRoute>
                  <ChangePassword/>
                </AuthenticatedRoute>
              }/>
        </Routes>
        <Toaster  
          position="bottom-right" 
          reverseOrder={false}
          toastOptions={{
            error:{
              style:{
                border:"3px dashed #F25B5B"
              }
              }
          }}/>
        </div>
        <Footer></Footer>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
