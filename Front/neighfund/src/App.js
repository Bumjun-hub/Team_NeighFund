import './App.css';
import { Route, Routes } from 'react-router-dom';
import Layout from './components/Layout';
import MainPage from './pages/mainpage/MainPage';
import SuggestionPage from './pages/suggestionspage/SuggestionPage';
import SuggestionWritePage from './pages/suggestionspage/SuggestionWritePage';

function App() {
  return (
    <div className="App">
      <Routes>
        <Route element={<Layout />}>
          <Route path="/" element={<MainPage />} />
          <Route path="/suggestion" element={<SuggestionPage />} />
          <Route path="/suggestion/write" element={<SuggestionWritePage />} />
        </Route>
      </Routes>
    </div>
  );
}

export default App;
