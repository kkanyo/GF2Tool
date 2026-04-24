import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { Navigation } from './components/layout/Navigation';
import './i18n';
import { DollListPage, DollRegisterPage } from './pages';

function App() {
  return (
    <BrowserRouter>
      <div className="min-h-dvh bg-zinc-950 text-zinc-100">
        <Navigation />
        <main>
          <Routes>
            <Route path="/dolls/list" element={<DollListPage />} />
            <Route path="/dolls/register" element={<DollRegisterPage />} />
          </Routes>
        </main>
      </div>
    </BrowserRouter>
  );
}
export default App;
