import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { CarService } from '../../api';

const CarList = () => {
    const [cars, setCars] = useState([]);

    const fetchCars = async () => {
        try {
            const response = await CarService.getAll();
            setCars(response.data);
        } catch (error) {
            console.error("Error fetching cars", error);
        }
    };

    const deleteCar = async (id) => {
        if (window.confirm('Delete this car?')) {
            await CarService.delete(id);
            fetchCars();
        }
    };

    useEffect(() => { fetchCars(); }, []);

    return (
        <div>
            <h2>Cars</h2>
            <Link to="/cars/new"><button>Create New Car</button></Link>
            <table border="1" cellPadding="10" style={{ marginTop: '10px', width: '100%' }}>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {cars.map(car => (
                    <tr key={car.id}>
                        <td>{car.id}</td>
                        <td>{car.name}</td>
                        <td>
                            <Link to={`/cars/${car.id}`}>View</Link> |
                            <Link to={`/cars/${car.id}/edit`}> Edit</Link> |
                            <button onClick={() => deleteCar(car.id)}>Delete</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default CarList;