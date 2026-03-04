import { useState } from "react";
import ListScrore from "../components/ListScores";
import ChartLevel from "../components/ChartLevel";
import TopGroupA from "../components/TopGroupA";
import {
  LayoutDashboard,
  BarChart3,
  Trophy,
  Settings,
  Menu,
  X,
} from "lucide-react";

function HomePage() {
  const [open, setOpen] = useState(false);

  return (
    <div className="flex min-h-screen bg-gray-100">

      {/* ===== Mobile Sidebar Overlay ===== */}
      {open && (
        <div className="fixed inset-0 bg-black/30 z-40 md:hidden"
             onClick={() => setOpen(false)}
        />
      )}

      {/* ===== Sidebar ===== */}
      <aside
        className={`
          fixed md:static z-50
          w-64 bg-white shadow-xl
          transform transition-transform duration-300
          ${open ? "translate-x-0" : "-translate-x-full"}
          md:translate-x-0
          hidden md:flex md:flex-col
        `}
      >
        <div className="flex flex-col justify-between h-full p-6">
          <div>
            <h2 className="text-2xl font-bold text-blue-600 mb-10">
              G-Scores
            </h2>

            <nav className="space-y-4">
              <SidebarItem icon={<LayoutDashboard size={18} />} label="Dashboard" active />
              <SidebarItem icon={<BarChart3 size={18} />} label="Reports" />
              <SidebarItem icon={<Trophy size={18} />} label="Top Students" />
              <SidebarItem icon={<Settings size={18} />} label="Settings" />
            </nav>
          </div>

          <p className="text-xs text-gray-400">© 2026 G-Scores</p>
        </div>
      </aside>

      {/* ===== Main Content ===== */}
      <main className="flex-1 w-full">

        {/* Header */}
        <header className="bg-white border-b border-gray-200 py-6 px-6 md:px-10 shadow-sm flex items-center justify-between">
          
          {/* Mobile Menu Button */}
          <button
            className="md:hidden"
            onClick={() => setOpen(true)}
          >
            <Menu size={24} />
          </button>

          <div>
            <h1 className="text-2xl md:text-3xl font-bold text-blue-600">
              Điểm thi THPT 2024
            </h1>
            <p className="text-gray-500 mt-1 text-sm md:text-base">
              Tổng quan về kết quả thi THPT 2024
            </p>
          </div>
        </header>

        {/* Content */}
        <div className="p-4 sm:p-6 md:p-10 space-y-8">
          <ListScrore />

          <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
            <ChartLevel />
            <TopGroupA />
          </div>
        </div>
      </main>
    </div>
  );
}

function SidebarItem({ icon, label, active }) {
  return (
    <div
      className={`flex items-center gap-3 p-3 rounded-xl cursor-pointer transition-all duration-200 ${
        active
          ? "bg-blue-100 text-blue-600 font-medium"
          : "hover:bg-gray-100 text-gray-600"
      }`}
    >
      {icon}
      <span>{label}</span>
    </div>
  );
}

export default HomePage;