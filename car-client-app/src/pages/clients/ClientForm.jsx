import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ClientService, CarService } from '../../api';

const ClientForm = () => {
    // Initialize balance as empty string to control the input
    const [client, setClient] = useState({ name: '', active: false, carId: '', balance: '' });
    const [cars, setCars] = useState([]);
    const { id } = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        CarService.getAll().then(res => setCars(res.data));

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

                {/* Added Balance Input */}
                <div style={{ marginBottom: '10px' }}>
                    <label>Balance: </label>
                    <input
                        type="number"
                        step="0.01" // Allows decimals
                        name="balance"
                        value={client.balance || ''}
                        onChange={handleChange}
                    />
                </div>

                <div style={{ marginBottom: '10px' }}>
                    <label>Car: </label>
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