import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { TransactionService } from '../../api';

const TransactionDetail = () => {
    const { id } = useParams();
    const [transaction, setTransaction] = useState(null);

    useEffect(() => {
        TransactionService.getById(id).then(res => setTransaction(res.data));
    }, [id]);

    if (!transaction) return <div>Loading...</div>;

    return (
        <div>
            <h2>Transaction Details</h2>
            <div style={{ background: '#f9f9f9', padding: '20px', borderRadius: '8px', maxWidth: '400px' }}>
                <p><strong>ID:</strong> {transaction.id}</p>
                <p><strong>Client:</strong> {transaction.clientName} (ID: {transaction.clientId})</p>
                <p><strong>Amount:</strong> {transaction.amount}</p>
                <p><strong>Date:</strong> {new Date(transaction.date).toLocaleString()}</p>
                <p><strong>Comment:</strong> {transaction.comment || 'N/A'}</p>
            </div>
            <br />
            <Link to="/transactions">Back to List</Link>
        </div>
    );
};

export default TransactionDetail;