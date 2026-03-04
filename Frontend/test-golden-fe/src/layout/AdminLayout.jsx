import { Link, useLocation } from "react-router-dom";

function AdminLayout({ children }) {
    const location = useLocation();

    const menu = [
        { name: "Tra Cứu Điểm Thi", path: "/" },
        { name: "Report", path: "/report" },
        { name: "Top 10 student", path: "/top" },
    ];

    return (
        <div className="flex min-h-screen bg-gray-100">
            {/* Sidebar */}
            <div className="w-64 bg-white shadow-lg p-6">
                <h1 className="text-2xl font-bold mb-8 text-blue-600">
                    Tra cứu điểm thi THPT 2024
                </h1>

                <ul className="space-y-3">
                    {menu.map((item) => (
                        <li key={item.path}>
                            <Link
                                to={item.path}
                                className={`block px-4 py-2 rounded-lg transition ${location.pathname === item.path
                                        ? "bg-blue-500 text-white"
                                        : "hover:bg-gray-200"
                                    }`}
                            >
                                {item.name}
                            </Link>
                        </li>
                    ))}
                </ul>
            </div>

            {/* Main content */}
            <div className="flex-1 p-10">{children}</div>
        </div>
    );
}

export default AdminLayout;