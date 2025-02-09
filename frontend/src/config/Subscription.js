import { features } from "./Features"

export const BuyerSubscriptions = [
    {
        name:"FREE",
        cost:0,
        features:[
            "Limited Radius",
            "2 Eagle Eyes",
            "No Chat Functionality",
            "Advertisements"
        ]
    },
    {
        name:"ACE",
        cost:100,
        features:[
            "Higher Radius",
            "10 Eagle Eyes",
            "Chat functionality",
            "Advertisements"
        ]
    },
    {
        name:"GOD",
        cost:500,
        features:[
            "Infinite Radius",
            "Unlimited Eagle Eyes",
            "Chat functionality",
            "No Advertisements"
        ]
    },
]


export const VendorSubscriptions = [
    {
        name:"FREE",
        cost:0,
        features:[
            "Shops are not featured",
            "No Telegram Notifications",
            "Default Marker",
            "No Statistics By EagleVision Bot"
        ]
    },
    {
        name:"VENDOR+",
        cost:500,
        features:[
            "Features Shops in Explore Page",
            "Telegram Notifcations",
            "Custom Shop Marker",
            "Statistics By EagleVision Bot"
        ]
    }
]

export const order = {
    "FREE":0,
    "ACE":1,
    "GOD":2,
    "VENDOR+":2
}