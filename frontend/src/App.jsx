import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './pages/home';
import Animals from './pages/animals';
import CareTypes from './pages/care-types';
import Cares from './pages/care';

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