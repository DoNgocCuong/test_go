import { Routes, Route } from "react-router-dom";
import AdminLayout from "./layout/AdminLayout";
import Dashboard from "./pages/Dashboard";
import ReportPage from "./pages/ReportPage";
import TopPage from "./pages/TopPage";

function App() {
  return (
    <AdminLayout>
      <Routes>
        <Route path="/" element={<Dashboard />} />
        <Route path="/report" element={<ReportPage />} />
        <Route path="/top" element={<TopPage />} />
      </Routes>
    </AdminLayout>
  );
}

export default App;