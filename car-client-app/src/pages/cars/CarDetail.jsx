import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { CarService } from '../../api';

const CarDetail = () => {
    const { id } = useParams();
    const [car, setCar] = useState(null);

    useEffect(() => {
        CarService.getById(id).then(res => setCar(res.data));
    }, [id]);

    if (!car) return <div>Loading...</div>;

    return (
        <div>
            <h2>Car Details</h2>
            <p><strong>ID:</strong> {car.id}</p>
            <p><strong>Name:</strong> {car.name}</p>
            <Link to="/cars">Back to List</Link>
        </div>
    );
};

export default CarDetail;