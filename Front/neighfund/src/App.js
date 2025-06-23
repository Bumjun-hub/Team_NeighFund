import './App.css';
import { Route, Routes } from 'react-router-dom';
import Layout from './components/Layout';
import MainPage from './pages/mainpage/MainPage';

function App() {
  return (
    <div className="App">
      <Routes>
        <Route element={<Layout />}>
          <Route path="/" element={<MainPage/>} />
        </Route>
      </Routes>
    </div>
  );
}

export default App;
