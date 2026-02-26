import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { ClientService, CarService } from '../../api';

const ClientList = () => {
    // 1. Data State
    const [clients, setClients] = useState([]);
    const [cars, setCars] = useState([]);

    // 2. Search State
    const [nameQuery, setNameQuery] = useState('');
    const [carQuery, setCarQuery] = useState('');

    // 3. Pagination State
    const [currentPage, setCurrentPage] = useState(0); // Spring pages are 0-indexed
    const [totalPages, setTotalPages] = useState(0);
    const pageSize = 10; // You can adjust this or make it a selectable dropdown

    const fetchData = async (searchClientName, searchCarName, page = 0) => {
        try {
            const finalClientName = searchClientName || undefined;
            const finalCarName = searchCarName || undefined;

            const [clientRes, carRes] = await Promise.all([
                // Passing page and size to the API call
                ClientService.getAll(finalClientName, finalCarName, page, pageSize),
                CarService.getAll()
            ]);

            // Extracting data from Spring's Page object structure
            setClients(clientRes.data.content);
            setTotalPages(clientRes.data.totalPages);
            setCurrentPage(clientRes.data.number);

            setCars(carRes.data);
        } catch (error) {
            console.error("Error fetching data", error);
        }
    };

    // Initial load
    useEffect(() => {
        fetchData(nameQuery, carQuery, currentPage);
    }, [currentPage]); // Re-fetch whenever currentPage changes

    const handleSearch = (e) => {
        e.preventDefault();
        setCurrentPage(0); // Reset to first page on new search
        fetchData(nameQuery, carQuery, 0);
    };

    const handleClear = () => {
        setNameQuery('');
        setCarQuery('');
        setCurrentPage(0); // Reset to first page
        fetchData(undefined, undefined, 0);
    };

    const deleteClient = async (id) => {
        if (window.confirm('Delete client?')) {
            await ClientService.delete(id);
            // Refresh current page after deletion
            fetchData(nameQuery, carQuery, currentPage);
        }
    };

    const getCarName = (client) => {
        if (client.carName) return client.carName;
        const foundCar = cars.find(c => c.id === client.carId);
        return foundCar ? foundCar.name : 'None';
    };

    // Pagination Handlers
    const goToPreviousPage = () => {
        if (currentPage > 0) setCurrentPage(currentPage - 1);
    };

    const goToNextPage = () => {
        if (currentPage < totalPages - 1) setCurrentPage(currentPage + 1);
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
                    <th>Balance</th>
                    <th>Active</th>
                    <th>Car Name</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {clients.length > 0 ? (
                    // Destructuring the ClientBalanceDTO into client and balance
                    clients.map(({ client, balance }) => (
                        <tr key={client.id}>
                            <td>{client.id}</td>
                            <td>{client.name}</td>
                            {/* Accessing balance from the nested BalanceDTO */}
                            <td>{balance.balance}</td>
                            <td>{client.active ? 'Yes' : 'No'}</td>
                            {/* Passing the inner client object to getCarName */}
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
                        <td colSpan="6" style={{ textAlign: 'center' }}>No clients found matching criteria.</td>
                    </tr>
                )}
                </tbody>
            </table>

            {/* Pagination Controls */}
            {totalPages > 1 && (
                <div style={{ marginTop: '20px', display: 'flex', justifyContent: 'center', gap: '15px', alignItems: 'center' }}>
                    <button
                        onClick={goToPreviousPage}
                        disabled={currentPage === 0}
                        style={{ cursor: currentPage === 0 ? 'not-allowed' : 'pointer' }}
                    >
                        Previous
                    </button>

                    <span>Page {currentPage + 1} of {totalPages}</span>

                    <button
                        onClick={goToNextPage}
                        disabled={currentPage >= totalPages - 1}
                        style={{ cursor: currentPage >= totalPages - 1 ? 'not-allowed' : 'pointer' }}
                    >
                        Next
                    </button>
                </div>
            )}
        </div>
    );
};

export default ClientList;