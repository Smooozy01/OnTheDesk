import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import CarList from './pages/cars/CarList';
import CarForm from './pages/cars/CarForm';
import CarDetail from './pages/cars/CarDetail';
import ClientList from './pages/clients/ClientList';
import ClientForm from './pages/clients/ClientForm';
import ClientDetail from './pages/clients/ClientDetail';
import './App.css';

function App() {
  return (
      <Router>
        <div className="App">
          <nav style={{ padding: '1rem', background: '#eee', marginBottom: '20px' }}>
            <Link to="/cars" style={{ marginRight: '10px' }}>Cars</Link>
            <Link to="/clients">Clients</Link>
          </nav>

          <div style={{ padding: '0 2rem' }}>
            <Routes>
              {/* Car Routes */}
              <Route path="/cars" element={<CarList />} />
              <Route path="/cars/new" element={<CarForm />} />
              <Route path="/cars/:id" element={<CarDetail />} />
              <Route path="/cars/:id/edit" element={<CarForm />} />

              {/* Client Routes */}
              <Route path="/clients" element={<ClientList />} />
              <Route path="/clients/new" element={<ClientForm />} />
              <Route path="/clients/:id" element={<ClientDetail />} />
              <Route path="/clients/:id/edit" element={<ClientForm />} />
            </Routes>
          </div>
        </div>
      </Router>
  );
}

export default App;