import { useNavigate } from "react-router-dom"
import "./Home.css"

export default function Home(){

    let navigate = useNavigate();

    function navigateTo(path){
        navigate(path);
    }

    return <div>
        <h1 className="welcome">Welcome to Eagle Vision🦅</h1>
        <h4 className="welcome">Menu</h4>
        <div className="menu">
            <button className="btn btn-primary m-2" onClick={()=>{navigateTo("/shops")}}>Shops</button>
            <button className="btn btn-primary m-2" onClick={()=>{navigateTo("/items")}}>Items</button> {/* Add a controller in backend,that uses a ML model to get the best selling items,and best shops*/}
        </div>
        
    </div>
}