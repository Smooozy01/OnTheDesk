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
    getAll: (clientName, carName, page, size) =>
        api.get('/clients', { params: { clientName, carName, page, size } }),
    getById: (id) => api.get(`/clients/${id}`),
    create: (client) => api.post('/clients', client),
    update: (id, client) => api.put(`/clients/${id}`, client),
    delete: (id) => api.delete(`/clients/${id}`)
};

export const TransactionService = {
    getAll: () => api.get('/transactions'),
    getById: (id) => api.get(`/transactions/${id}`),
    create: (transaction) => api.post('/transactions', transaction),
    update: (id, transaction) => api.put(`/transactions/${id}`, transaction),
    delete: (id) => api.delete(`/transactions/${id}`)
};