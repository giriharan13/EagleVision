import EagleVision from "../images/eaglevisionvf.png"
import EagleEyes from "../images/eagleeyesvf.png"
import Ping from "../images/pingvf.png"

import { FaShop } from "react-icons/fa6";
import { FaTelegram } from "react-icons/fa";
import { FaRobot } from "react-icons/fa";

export const features = [
    {
        name:"Eagle Vision",
        description:"Utilize the vision of your eagle to view nearby shops!",
        image:EagleVision
    },{
        name:"Ping",
        description:"Ping the vendor to know the availability of an item!",
        image:Ping
    },{
        name:"Eagle Eyes",
        description:"Place the eagle eyes on a item, to get updates for the response ping by vendors!",
        image:EagleEyes
    }
]


export const vendorFeatures = [
    {
        name: "Shop Creation",
        description: "Set up your digital storefront and showcase your products to local buyers!",
        image: <FaShop style={{scale:1.5}}/>
    },
    {
        name: "Instant Notifications",
        description: "Get real-time updates when buyers ping your items through the app or Telegram!",
        image: <FaTelegram style={{scale:1.5}}/>,
        inDevelopement:true
    },
    {
        name: "Eagle Vision Bot",
        description: "Track statistics, analyze sales trends, and grow your business effortlessly!",
        image:<FaRobot style={{scale:1.5}}/>,
        inDevelopement:true
    }
];