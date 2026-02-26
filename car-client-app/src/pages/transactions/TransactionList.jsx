import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { TransactionService } from '../../api';

const TransactionList = () => {
    const [transactions, setTransactions] = useState([]);

    const fetchTransactions = async () => {
        try {
            const response = await TransactionService.getAll();
            setTransactions(response.data);
        } catch (error) {
            console.error("Error fetching transactions", error);
        }
    };

    const deleteTransaction = async (id) => {
        if (window.confirm('Delete this transaction?')) {
            await TransactionService.delete(id);
            fetchTransactions();
        }
    };

    useEffect(() => { fetchTransactions(); }, []);

    // Helper to format LocalDateTime string nicely
    const formatDate = (dateString) => {
        if (!dateString) return '';
        return new Date(dateString).toLocaleString();
    };

    return (
        <div>
            <h2>Transactions</h2>
            <Link to="/transactions/new"><button>Create New Transaction</button></Link>
            <table border="1" cellPadding="10" style={{ marginTop: '10px', width: '100%' }}>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Client</th>
                    <th>Amount</th>
                    <th>Date</th>
                    <th>Comment</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {transactions.map(t => (
                    <tr key={t.id}>
                        <td>{t.id}</td>
                        <td>{t.clientName || `ID: ${t.clientId}`}</td>
                        <td>{t.amount}</td>
                        <td>{formatDate(t.date)}</td>
                        <td>{t.comment}</td>
                        <td>
                            <Link to={`/transactions/${t.id}`}>View</Link> |
                            <Link to={`/transactions/${t.id}/edit`}> Edit</Link> |
                            <button onClick={() => deleteTransaction(t.id)}>Delete</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default TransactionList;