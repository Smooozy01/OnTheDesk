import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ClientService, CarService } from '../../api'; // Import CarService

const ClientForm = () => {
    const [client, setClient] = useState({ name: '', active: false, carId: '' });
    const [cars, setCars] = useState([]); // Store list of cars for the dropdown
    const { id } = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        // 1. Fetch available cars for the dropdown
        CarService.getAll().then(res => setCars(res.data));

        // 2. If editing, fetch the client data
        if (id) {
            ClientService.getById(id).then(res => setClient(res.data));
        }
    }, [id]);

    const handleChange = (e) => {
        const value = e.target.type === 'checkbox' ? e.target.checked : e.target.value;
        setClient({ ...client, [e.target.name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            if (id) {
                await ClientService.update(id, client);
            } else {
                await ClientService.create(client);
            }
            navigate('/clients');
        } catch (error) {
            alert('Error saving client');
        }
    };

    return (
        <div>
            <h2>{id ? 'Edit Client' : 'Create Client'}</h2>
            <form onSubmit={handleSubmit}>
                <div style={{ marginBottom: '10px' }}>
                    <label>Name: </label>
                    <input
                        type="text"
                        name="name"
                        value={client.name || ''}
                        onChange={handleChange}
                        required
                    />
                </div>

                <div style={{ marginBottom: '10px' }}>
                    <label>Car: </label>
                    {/* Refactored to a Dropdown Select */}
                    <select
                        name="carId"
                        value={client.carId || ''}
                        onChange={handleChange}
                    >
                        <option value="">-- Select a Car --</option>
                        {cars.map(car => (
                            <option key={car.id} value={car.id}>
                                {car.name} (ID: {car.id})
                            </option>
                        ))}
                    </select>
                </div>

                <div style={{ marginBottom: '10px' }}>
                    <label>Active: </label>
                    <input
                        type="checkbox"
                        name="active"
                        checked={client.active || false}
                        onChange={handleChange}
                    />
                </div>
                <button type="submit">Save</button>
            </form>
        </div>
    );
};

export default ClientForm;