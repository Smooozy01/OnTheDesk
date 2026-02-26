import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { ClientService } from '../../api';

const ClientDetail = () => {
    const { id } = useParams();
    const [clientData, setClientData] = useState(null);

    useEffect(() => {
        ClientService.getById(id)
            .then(res => setClientData(res.data))
            .catch(err => console.error("Error fetching client data:", err));
    }, [id]);

    if (!clientData) return <div>Loading...</div>;

    const { client, balance } = clientData;

    return (
        <div>
            <h2>Client Details</h2>
            <p><strong>ID:</strong> {client.id}</p>
            <p><strong>Name:</strong> {client.name}</p>
            <p><strong>Balance:</strong> {balance.balance}</p>
            <p><strong>Active:</strong> {client.active ? 'Yes' : 'No'}</p>
            <p><strong>Car:</strong> {client.carName || 'No Car Assigned'}</p>
            <Link to="/clients">Back to List</Link>
        </div>
    );
};

export default ClientDetail;