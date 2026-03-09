import { BrowserRouter, Route, Routes } from "react-router-dom";
import { DollRegisterPage } from "./pages";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="dolls/register" element={<DollRegisterPage />} />
      </Routes>
    </BrowserRouter>
  );
}
export default App;
