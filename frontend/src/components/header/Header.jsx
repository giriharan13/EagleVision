import { Link } from "react-router-dom"
import "../../../node_modules/bootstrap/dist/css/bootstrap.min.css"

export default function Header(){
    return <header className="d-flex flex-wrap justify-content-center py-3 mb-4 border-bottom">
       <Link to="/" className="d-flex align-items-center m-2 mb-md-0 me-md-auto text-dark text-decoration-none"> 
        <span className="fs-4">EagleVision</span>
       </Link>
    </header>
}