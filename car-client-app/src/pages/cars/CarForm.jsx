import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { CarService } from '../../api';

const CarForm = () => {
    const [car, setCar] = useState({ name: '' });
    const { id } = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        if (id) {
            CarService.getById(id).then(res => setCar(res.data));
        }
    }, [id]);

    const handleChange = (e) => {
        setCar({ ...car, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            if (id) {
                await CarService.update(id, car);
            } else {
                await CarService.create(car);
            }
            navigate('/cars');
        } catch (error) {
            alert('Error saving car. Check constraints (Name 5-8 chars).');
        }
    };

    return (
        <div>
            <h2>{id ? 'Edit Car' : 'Create Car'}</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Name (5-8 chars): </label>
                    <input
                        type="text"
                        name="name"
                        value={car.name || ''}
                        onChange={handleChange}
                    />
                </div>
                <button type="submit">Save</button>
            </form>
        </div>
    );
};

export default CarForm;