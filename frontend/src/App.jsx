import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

// Imported pages
import Home from './pages/home';
import Animals from './pages/animals';
import CareTypes from './pages/care-types';
import Cares from './pages/care';

// Configure the react rounter and define all the application's routes
function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home/>} />
        <Route path="/animals" element={<Animals/>} />
        <Route path="/care-types" element={<CareTypes/>} />
        <Route path="/cares" element={<Cares />} />
      </Routes>
    </Router>
  );
}

export default App