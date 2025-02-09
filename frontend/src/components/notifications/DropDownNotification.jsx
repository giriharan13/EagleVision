

export default function DropDownNotification({message}){


    return <li>
        <a class="dropdown-item" href="#">
            <div className="fw-bold">{message.title}</div>
            <div>{message.content}</div>
        </a>
    </li>
}