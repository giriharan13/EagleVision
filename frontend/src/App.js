import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import Header from './components/header/Header';
import Footer from './components/footer/Footer';
import ListShops from './components/listshops/ListShops';
import Shop from './components/shop/Shop';
import "../node_modules/bootstrap/dist/css/bootstrap.min.css"
import ListPings from './components/pings/ListPings';

function App() {
  return (
    <BrowserRouter>
      <Header></Header>
      <div className="container" style={{minHeight:"80vh"}}>
        <Routes> 
          <Route path="/shops" element={<ListShops/>}/>
          <Route path="/shops/:id" element={<Shop/>}/>
          <Route path="/shops/:shopId/items/:id/pings" element={<ListPings/>}/>
        </Routes>
      </div>
      <Footer></Footer>
    </BrowserRouter>
  );
}

export default App;
