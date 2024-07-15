import { BrowserRouter, Navigate, Route, Routes} from 'react-router-dom';
import './App.css';
import Header from './components/header/Header';
import Footer from './components/footer/Footer';
import ListShops from './components/shop/ListShops';
import Shop from './components/shop/Shop';
// import "../node_modules/bootstrap/dist/css/bootstrap.min.css"
import ListPings from './components/pings/ListPings';
import Home from './components/home/Home';
import AuthProvider, { useAuth } from './security/AuthContext';
import Login from './components/login/Login';
import Signup from './components/signup/Signup';
import CreateShop from './components/shop/CreateShop';
import {Toaster} from 'react-hot-toast';
import ListMyShops from './components/shop/ListMyShops';
import UpdateShop from './components/shop/UpdateShop';
import CreateItem from './components/item/CreateItem';
import Item from './components/item/Item';
import UpdateItem from './components/item/UpdateItem';

function AuthenticatedRoute({children}){
  const authContext = useAuth();
  if(authContext.isAuthenticated){
    console.log(authContext.decoded);
    return children;
  }
  return <Navigate to="/login"/>
}

function AuthorizedRoute({children,roles}){
  const authContext = useAuth();
  if(roles.includes(authContext.decoded.scope)){
    return children;
  }
  return <Navigate to="/home"/>
}


function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Header></Header>
        <div className="fluid-container p-3 bg-white" style={{minHeight:"90vh"}}>
          <Routes>
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
                  <ListShops/>
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
