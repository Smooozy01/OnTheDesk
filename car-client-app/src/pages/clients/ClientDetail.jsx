import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { ClientService } from '../../api';

const ClientDetail = () => {
    const { id } = useParams();
    const [client, setClient] = useState(null);

    useEffect(() => {
        ClientService.getById(id).then(res => setClient(res.data));
    }, [id]);

    if (!client) return <div>Loading...</div>;

    return (
        <div>
            <h2>Client Details</h2>
            <p><strong>ID:</strong> {client.id}</p>
            <p><strong>Name:</strong> {client.name}</p>
            <p><strong>Active:</strong> {client.active ? 'Yes' : 'No'}</p>
            {/* Show Car Name */}
            <p><strong>Car:</strong> {client.carName || 'No Car Assigned'}</p>
            <Link to="/clients">Back to List</Link>
        </div>
    );
};

export default ClientDetail;