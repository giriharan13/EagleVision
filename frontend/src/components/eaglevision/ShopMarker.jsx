import { Icon } from "leaflet";
import { Marker, Popup } from "react-leaflet";
import { Link } from "react-router-dom";


export default function ShopMarker({shop,icon}){


    return (<Marker position={[shop.shopLocation.lattitude,shop.shopLocation.longitude]} icon={(new Icon({iconUrl:((shop.markerImageDataB64!==null)?`data:png;base64,${shop.markerImageDataB64}`:"https://cdn-icons-png.freepik.com/256/869/869636.png?semt=ais_hybrid"), iconSize: [50,50], iconAnchor: [12, 41]}))}>
            <Popup>
               <Link className="link-opacity-100" to={`/shops/${shop.shopId}`}>{shop.shopName}</Link> <br /> {shop.description}
            </Popup>
            </Marker>);
}