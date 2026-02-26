import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { TransactionService, ClientService } from '../../api';

const TransactionForm = () => {
    const [transaction, setTransaction] = useState({
        clientId: '',
        amount: '',
        date: '',
        comment: ''
    });
    const [clients, setClients] = useState([]); // For the dropdown
    const { id } = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        // 1. Fetch Clients for dropdown
        ClientService.getAll().then(res => setClients(res.data.content));

        // 2. If editing, fetch transaction data
        if (id) {
            TransactionService.getById(id).then(res => {
                // We need to format the date slightly for the HTML input (yyyy-MM-ddThh:mm)
                // Assuming backend sends standard ISO format
                const data = res.data;
                setTransaction(data);
            });
        }
    }, [id]);

    const handleChange = (e) => {
        setTransaction({ ...transaction, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            if (id) {
                await TransactionService.update(id, transaction);
            } else {
                await TransactionService.create(transaction);
            }
            navigate('/transactions');
        } catch (error) {
            alert('Error saving transaction. Check all required fields.');
            console.error(error);
        }
    };

    return (
        <div>
            <h2>{id ? 'Edit Transaction' : 'Create Transaction'}</h2>
            <form onSubmit={handleSubmit} style={{ maxWidth: '400px' }}>

                {/* Client Dropdown */}
                <div style={{ marginBottom: '10px' }}>
                    <label style={{ display: 'block' }}>Client: </label>
                    <select
                        name="clientId"
                        value={transaction.clientId || ''}
                        onChange={handleChange}
                        required
                        style={{ width: '100%', padding: '5px' }}
                    >
                        <option value="">-- Select Client --</option>
                        {clients && clients.map(({ client }) => (
                            <option key={client.id} value={client.id}>
                                {client.name} (ID: {client.id})
                            </option>
                        ))}
                    </select>
                </div>

                {/* Amount Input */}
                <div style={{ marginBottom: '10px' }}>
                    <label style={{ display: 'block' }}>Amount: </label>
                    <input
                        type="number"
                        step="0.01"
                        name="amount"
                        value={transaction.amount || ''}
                        onChange={handleChange}
                        required
                        style={{ width: '100%', padding: '5px' }}
                    />
                </div>

                {/* Date Input (DateTime-Local) */}
                <div style={{ marginBottom: '10px' }}>
                    <label style={{ display: 'block' }}>Date: </label>
                    <input
                        type="datetime-local"
                        name="date"
                        value={transaction.date || ''}
                        onChange={handleChange}
                        required
                        style={{ width: '100%', padding: '5px' }}
                    />
                </div>

                {/* Comment Input */}
                <div style={{ marginBottom: '10px' }}>
                    <label style={{ display: 'block' }}>Comment: </label>
                    <textarea
                        name="comment"
                        value={transaction.comment || ''}
                        onChange={handleChange}
                        style={{ width: '100%', padding: '5px' }}
                    />
                </div>

                <button type="submit" style={{ padding: '10px 20px' }}>Save Transaction</button>
            </form>
        </div>
    );
};

export default TransactionForm;