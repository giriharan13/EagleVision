import { Link } from "react-router-dom"
import "../../../node_modules/bootstrap/dist/css/bootstrap.min.css"

export default function Header(){
    return <header className="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <Link to="/" className="navbar-brand"> 
                <span className="fs-4">EagleVision</span>
            </Link>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="d-flex collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/about">About</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/contact">Contact us</a>
                    </li>
                </ul>
            </div>
        </div>
    </header>
}