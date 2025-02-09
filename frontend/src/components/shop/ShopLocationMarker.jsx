import { Icon } from "leaflet";
import React, { useEffect, useState } from "react";
import { Marker, Popup, useMap, useMapEvents } from "react-leaflet";


export default function ShopLocationMarker({setFieldValue,position,setPosition}){

    
    const mapEvents = useMapEvents({
        click(e){
            setPosition(e.latlng)
            setFieldValue("lattitude",e.latlng.lat)
            setFieldValue("longitude",e.latlng.lng)
        }
    })

    const map = useMap()

    React.useEffect(() => {
        if (position) {
          map.setView(position, 13);
        }
    }, [map, position]);


    return <div>
        {position && (
            <Marker position={position} draggable={true} icon={new Icon({iconUrl: "https://cdn-icons-png.freepik.com/256/869/869636.png?semt=ais_hybrid", iconSize: [41, 41], iconAnchor: [12, 41]})}>
                    <Popup>
                        Your shop is here.
                    </Popup>
            </Marker>
        )}
</div>
}