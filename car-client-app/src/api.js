import axios from 'axios';

const API_URL = 'http://localhost:8080';

const api = axios.create({
    baseURL: API_URL,
});

export const CarService = {
    getAll: (name) => api.get('/cars', { params: { name } }),
    getById: (id) => api.get(`/cars/${id}`),
    create: (car) => api.post('/cars', car),
    update: (id, car) => api.put(`/cars/${id}`, car),
    delete: (id) => api.delete(`/cars/${id}`)
};

export const ClientService = {
    getAll: (clientName, carName) => api.get('/clients', { params: { clientName, carName } }),
    getById: (id) => api.get(`/clients/${id}`),
    create: (client) => api.post('/clients', client),
    update: (id, client) => api.put(`/clients/${id}`, client),
    delete: (id) => api.delete(`/clients/${id}`)
};