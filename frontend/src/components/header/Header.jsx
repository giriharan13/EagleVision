import { Link, useLocation, useNavigate } from "react-router-dom"
// import "../../../node_modules/bootstrap/dist/css/bootstrap.min.css"
import { useAuth } from "../../security/AuthContext"
import toast from "react-hot-toast";

export default function Header(){

    const authContext = useAuth();

    const navigate = useNavigate();

    const location =  useLocation();

    const isAuthenticated = authContext.isAuthenticated;

    const logout = async ()=>{
        await authContext.logout();
        toast.success("Logged out successfully!")
        navigate("/login")
    }

    return <header className="navbar navbar-expand-lg navbar-dark bg-secondary">
        <div class="container-fluid">
            <Link to="/home" className="navbar-brand"> 
                <p className="fs-4 text-white fw-bold">🦅EagleVision</p>
            </Link>
            {/* <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button> */}
            <div class="d-flex collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <Link class="nav-link active text-white" aria-current="page" to="/home">Home</Link>
                    </li>
                    <li class="nav-item">
                        <Link class="nav-link text-white" to="/about">About</Link>
                    </li>
                    <li class="nav-item">
                        <Link class="nav-link text-white" to="/contact">Contact us</Link>
                    </li>
                </ul> 
            </div>
            <ul class="d-flex nav navbar-nav navbar-right">
                    {isAuthenticated && <li className="nav-item">
                        <Link className="nav-link text-primary" onClick={logout}>Logout</Link>
                        </li>}
                    {!isAuthenticated && location.pathname!=='/login' &&
                        <li class="nav-item">
                            <Link className="nav-link" to="/login"> Login</Link>
                            </li>}
                        { !isAuthenticated &&  location.pathname!=='/signup' &&
                          <li class="nav-item">
                            <Link className="nav-link" to="/signup"> Signup</Link>
                            </li>
                    }
                </ul>
        </div>
    </header>
}