import { useState } from "react";
import { Link, useLocation } from "react-router-dom";
import { Menu, X } from "lucide-react";

function Navbar() {
    const location = useLocation();
    const [open, setOpen] = useState(false);

    const activeStyle = "bg-blue-600 text-white";
    const normalStyle = "text-gray-600 hover:bg-gray-100";

    const navItem = (path, label) => (
        <Link
            to={path}
            onClick={() => setOpen(false)}
            className={`block px-4 py-2 rounded-lg transition ${location.pathname === path ? activeStyle : normalStyle}`}
        >
            {label}
        </Link>
    );

    return (
        <nav className="bg-white shadow-md relative z-50">
            <div className="max-w-6xl mx-auto px-4 sm:px-6 md:px-10 py-4 flex items-center justify-between">

                {/* Logo */}
                <h1 className="text-lg sm:text-xl font-bold text-blue-600">
                    G-Scores
                </h1>

                {/* Desktop Menu */}
                <div className="hidden md:flex gap-4">
                    {navItem("/", "Dashboard")}
                    {navItem("/report", "Report")}
                    {navItem("/top", "Top Group A")}
                </div>

                {/* Mobile Button */}
                <button
                    className="md:hidden p-1"
                    onClick={() => setOpen(!open)}
                    aria-label={open ? "Đóng menu" : "Mở menu"}
                >
                    {open ? <X size={24} /> : <Menu size={24} />}
                </button>
            </div>

            {/* Mobile Menu */}
            <div
                className={`md:hidden absolute top-full left-0 w-full bg-white shadow-md transition-all duration-300 overflow-hidden z-50 ${open ? "max-h-60 opacity-100 py-4" : "max-h-0 opacity-0 py-0"
                    }`}
            >
                <div className="px-4 space-y-2">
                    {navItem("/", "Dashboard")}
                    {navItem("/report", "Report")}
                    {navItem("/top", "Top Group A")}
                </div>
            </div>
        </nav>
    );
}

export default Navbar;