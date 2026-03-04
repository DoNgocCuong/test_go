import { useState, useEffect } from "react";
import scoreApi from "../api/scoresApi";
import {
  PieChart,
  Pie,
  Tooltip,
  Cell,
  ResponsiveContainer,
  Legend,
} from "recharts";

const subjects = [
  { id: "Toán", string: "Toán" },
  { id: "Ngữ Văn", string: "Ngữ Văn" },
  { id: "Ngoại ngữ", string: "Ngoại ngữ" },
  { id: "Vật lý", string: "Vật lý" },
  { id: "Hóa học", string: "Hóa học" },
  { id: "Sinh học", string: "Sinh học" },
  { id: "Lịch sử", string: "Lịch sử" },
  { id: "Địa lý", string: "Địa lý" },
  { id: "GDCD", string: "GDCD" },
];

const COLORS = ["#4f46e5", "#06b6d4", "#10b981", "#f59e0b"];

function ChartLevel() {
  const [subject, setSubject] = useState("Toán");
  const [report, setReport] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchReport = async () => {
      try {
        setLoading(true);
        const res = await scoreApi.getReportBySubject(subject);
        const data = res.result || res.data?.result;
        setReport(data);
      } catch (err) {
        console.error("Lỗi khi lấy báo cáo:", err);
        setReport(null);
      } finally {
        setLoading(false);
      }
    };

    fetchReport();
  }, [subject]);

  const chartData = report
    ? [
      { name: ">= 8 điểm", value: report.levelA || 0 },
      { name: "6 - 8 điểm", value: report.levelB || 0 },
      { name: "4 - 6 điểm", value: report.levelC || 0 },
      { name: "<= 4 điểm", value: report.levelD || 0 },
    ]
    : [];

  return (
    <div className="w-full px-4 sm:px-6 md:px-10">
      <div className="shadow-lg rounded-2xl bg-white overflow-hidden">

        {/* Header */}
        <div className="p-4 sm:p-6 md:p-8 flex flex-col sm:flex-row sm:items-center sm:justify-between gap-3">
          <div>
            <h2 className="font-bold text-lg sm:text-xl md:text-2xl">
              Báo cáo theo môn
            </h2>
            <p className="text-gray-500 text-sm">
              Thống kê cho từng môn thi
            </p>
          </div>

          <select
            value={subject}
            onChange={(e) => setSubject(e.target.value)}
            className="px-4 py-2 border border-gray-300 rounded-lg w-full sm:w-48 md:w-56 focus:outline-none focus:ring-2 focus:ring-blue-500"
          >
            {subjects.map((s) => (
              <option key={s.id} value={s.id}>
                {s.string}
              </option>
            ))}
          </select>
        </div>

        {/* Chart */}
        <div className="px-4 sm:px-6 md:px-8 pb-6 md:pb-10">
          {loading ? (
            <p className="py-6 text-center text-gray-500">
              Đang tải dữ liệu...
            </p>
          ) : report && chartData.length > 0 ? (
            /* 
              Dọc (portrait): dùng aspect-square để chart luôn vuông, dễ nhìn
              Ngang (landscape): giới hạn chiều cao thấp hơn để không bị cắt
              sm:h-64 → landscape mobile
              md:h-80 → tablet
              lg:h-96 → desktop
            */
            <div className="w-full h-64 sm:h-72 md:h-80 lg:h-96">
              <ResponsiveContainer width="100%" height="100%">
                <PieChart>
                  <Pie
                    data={chartData}
                    cx="50%"
                    cy="50%"
                    outerRadius="70%"
                    dataKey="value"
                    label
                  >
                    {chartData.map((_, index) => (
                      <Cell
                        key={index}
                        fill={COLORS[index % COLORS.length]}
                      />
                    ))}
                  </Pie>
                  <Tooltip />
                  <Legend />
                </PieChart>
              </ResponsiveContainer>
            </div>
          ) : (
            <p className="py-6 text-center text-gray-500">
              Không có dữ liệu
            </p>
          )}
        </div>

      </div>
    </div>
  );
}

export default ChartLevel;