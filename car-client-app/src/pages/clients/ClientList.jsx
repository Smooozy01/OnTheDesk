import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { ClientService } from '../../api';

const ClientList = () => {
    const [clients, setClients] = useState([]);

    const fetchClients = async () => {
        try {
            const response = await ClientService.getAll();
            setClients(response.data);
        } catch (error) {
            console.error("Error fetching clients", error);
        }
    };

    const deleteClient = async (id) => {
        if (window.confirm('Delete client?')) {
            await ClientService.delete(id);
            fetchClients();
        }
    };

    useEffect(() => { fetchClients(); }, []);

    return (
        <div>
            <h2>Clients</h2>
            <Link to="/clients/new"><button>Create New Client</button></Link>
            <table border="1" cellPadding="10" style={{ marginTop: '10px', width: '100%' }}>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Active</th>
                    <th>Car Name</th> {/* Changed Header */}
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {clients.map(client => (
                    <tr key={client.id}>
                        <td>{client.id}</td>
                        <td>{client.name}</td>
                        <td>{client.active ? 'Yes' : 'No'}</td>
                        {/* Display carName. Fallback to 'None' if null */}
                        <td>{client.carName || 'None'}</td>
                        <td>
                            <Link to={`/clients/${client.id}`}>View</Link> |
                            <Link to={`/clients/${client.id}/edit`}> Edit</Link> |
                            <button onClick={() => deleteClient(client.id)}>Delete</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default ClientList;