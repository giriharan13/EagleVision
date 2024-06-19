import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';
import './App.css';
import Header from './components/header/Header';
import Footer from './components/footer/Footer';
import ListShops from './components/listshops/ListShops';
import Shop from './components/shop/Shop';
import "../node_modules/bootstrap/dist/css/bootstrap.min.css"
import ListPings from './components/pings/ListPings';
import Home from './components/home/Home';
import AuthProvider, { useAuth } from './security/AuthContext';
import Login from './components/login/Login';

function AuthenticatedRoute({children}){
  const authContext = useAuth();
  console.log(authContext.isAuthenticated);
  if(authContext.isAuthenticated){
    return children;
  }
  return <Navigate to="/login"/>
}

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Header></Header>
        <div className="container p-5" style={{height:"90vh"}}>
          <Routes>
            <Route path="/login" element={
              <Login/>
            }/>
            <Route path="/home" element={
              <AuthenticatedRoute>
                <Home/>
              </AuthenticatedRoute>
            }/>
            <Route path="/shops" element={
              <AuthenticatedRoute>
                <ListShops/>
              </AuthenticatedRoute>
            }/>
            <Route path="/shops/:id" element={
              <AuthenticatedRoute>
                <Shop/>
              </AuthenticatedRoute>
            }/>
            <Route path="/shops/:shopId/items/:id/pings" element={
              <AuthenticatedRoute>
                <ListPings/>
              </AuthenticatedRoute>
              }/>
          </Routes>
        </div>
        <Footer></Footer>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
