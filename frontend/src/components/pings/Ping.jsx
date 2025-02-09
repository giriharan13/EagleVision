

export default function Ping({ping}){


    return <div className="d-flex mb-3 flex-column bg-white rounded-4 p-4" key={ping.pingId}>
                    <div className="text-warning px-5">Buyer ping</div>
                    <div className="d-flex flex-column">
                        <h5 className="fw-bold"> By {ping.userName}</h5>
    
                        {(ping.vendorResponsePing === null) ? <div>No response yet.</div> : 
                        <div className="d-flex flex-column border border-success mb-3 rounded-4 p-2">
                            <div className="text-success">Vendor ping</div>
                            <div className="card-body">
                                <h5>Quantity : {ping.vendorResponsePing.quantity}</h5>
                            </div>
                        </div> }
                        
                    </div>
                </div>
}