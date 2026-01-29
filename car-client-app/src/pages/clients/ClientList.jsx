import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { ClientService, CarService } from '../../api';

const ClientList = () => {
    const [clients, setClients] = useState([]);
    const [cars, setCars] = useState([]);

    const [nameQuery, setNameQuery] = useState('');
    const [carQuery, setCarQuery] = useState('');

    const fetchData = async (searchClientName, searchCarName) => {
        try {
            // FIX: Convert empty strings ('') to undefined.
            // Axios will completely skip keys that are undefined,
            // resulting in a URL like /clients?clientName=SomeName
            const finalClientName = searchClientName ? searchClientName : undefined;
            const finalCarName = searchCarName ? searchCarName : undefined;

            const [clientRes, carRes] = await Promise.all([
                ClientService.getAll(finalClientName, finalCarName),
                CarService.getAll()
            ]);
            setClients(clientRes.data);
            setCars(carRes.data);
        } catch (error) {
            console.error("Error fetching data", error);
        }
    };

    useEffect(() => { fetchData(); }, []);

    const handleSearch = (e) => {
        e.preventDefault();
        fetchData(nameQuery, carQuery);
    };

    const handleClear = () => {
        setNameQuery('');
        setCarQuery('');
        // Pass undefined explicitly to clear the search
        fetchData(undefined, undefined);
    };

    const deleteClient = async (id) => {
        if (window.confirm('Delete client?')) {
            await ClientService.delete(id);
            fetchData(nameQuery, carQuery);
        }
    };

    const getCarName = (client) => {
        if (client.carName) return client.carName;
        const foundCar = cars.find(c => c.id === client.carId);
        return foundCar ? foundCar.name : 'None';
    };

    return (
        <div>
            <h2>Clients</h2>

            <div style={{ background: '#f4f4f4', padding: '15px', marginBottom: '20px', borderRadius: '5px' }}>
                <form onSubmit={handleSearch} style={{ display: 'flex', gap: '10px', alignItems: 'center' }}>
                    <strong>Search:</strong>
                    <input
                        type="text"
                        placeholder="Client Name..."
                        value={nameQuery}
                        onChange={(e) => setNameQuery(e.target.value)}
                        style={{ padding: '5px' }}
                    />
                    <input
                        type="text"
                        placeholder="Car Name..."
                        value={carQuery}
                        onChange={(e) => setCarQuery(e.target.value)}
                        style={{ padding: '5px' }}
                    />
                    <button type="submit" style={{ cursor: 'pointer' }}>Search</button>
                    <button type="button" onClick={handleClear} style={{ cursor: 'pointer', background: '#ccc' }}>Clear</button>
                </form>
            </div>

            <Link to="/clients/new"><button>Create New Client</button></Link>

            <table border="1" cellPadding="10" style={{ marginTop: '10px', width: '100%' }}>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Active</th>
                    <th>Car Name</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {clients.length > 0 ? (
                    clients.map(client => (
                        <tr key={client.id}>
                            <td>{client.id}</td>
                            <td>{client.name}</td>
                            <td>{client.active ? 'Yes' : 'No'}</td>
                            <td>{getCarName(client)}</td>
                            <td>
                                <Link to={`/clients/${client.id}`}>View</Link> |
                                <Link to={`/clients/${client.id}/edit`}> Edit</Link> |
                                <button onClick={() => deleteClient(client.id)}>Delete</button>
                            </td>
                        </tr>
                    ))
                ) : (
                    <tr>
                        <td colSpan="5" style={{ textAlign: 'center' }}>No clients found matching criteria.</td>
                    </tr>
                )}
                </tbody>
            </table>
        </div>
    );
};

export default ClientList;