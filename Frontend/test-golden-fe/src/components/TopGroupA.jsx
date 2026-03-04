import { useState, useEffect } from "react";
import scoreApi from "../api/scoresApi";

function TopGroupA() {
  const [list, setList] = useState([]);

  useEffect(() => {
    const fetchList = async () => {
      try {
        const res = await scoreApi.getTopGroupA();
        setList(res.data.result);
      } catch (err) {
        console.error(err);
        setList([]);
      }
    };
    fetchList();
  }, []);

  const getScoreColor = (score) => {
    if (score === null || score === undefined) return "";
    if (score >= 8) return "text-blue-600 font-semibold";
    if (score >= 6) return "text-green-600 font-semibold";
    if (score >= 4) return "text-yellow-600 font-semibold";
    return "text-red-600 font-semibold";
  };

  return (
    <div className="col-span-12 lg:col-start-6 lg:col-end-12 grid grid-cols-12 rounded-2xl">
      <div className="col-start-1 col-end-13 mb-10 shadow-lg rounded-2xl bg-white">
        <div className="p-6 md:p-10">
          <h2 className="font-bold text-xl md:text-2xl">Bảng xếp hạng</h2>
          <p className="text-gray-500 text-sm">
            Top 10 thí sinh khối A (Toán - Lý - Hóa)
          </p>
        </div>

        <section className="px-4 sm:px-6 md:px-10 pb-10">

          {/* TABLE — md trở lên */}
          <div className="hidden sm:block rounded-2xl overflow-x-auto">
            <table className="min-w-full text-left">
              <thead className="text-gray-400">
                <tr>
                  <th className="px-4 py-3 font-semibold">#</th>
                  <th className="px-4 py-3 font-semibold">SBD</th>
                  <th className="px-4 py-3 font-semibold text-center">Toán</th>
                  <th className="px-4 py-3 font-semibold text-center">Lý</th>
                  <th className="px-4 py-3 font-semibold text-center">Hóa</th>
                  <th className="px-4 py-3 font-semibold text-center">Tổng</th>
                </tr>
              </thead>
              <tbody>
                {list.length > 0 ? (
                  list.map((item, index) => (
                    <tr
                      key={index}
                      className="border-t border-gray-200 hover:bg-gray-50 transition"
                    >
                      <td className="px-4 py-3 text-gray-400 text-sm">{index + 1}</td>
                      <td className="px-4 py-3 font-medium">{item.sbd}</td>
                      <td className={`px-4 py-3 text-center ${getScoreColor(item.toan)}`}>{item.toan}</td>
                      <td className={`px-4 py-3 text-center ${getScoreColor(item.vatLy)}`}>{item.vatLy}</td>
                      <td className={`px-4 py-3 text-center ${getScoreColor(item.hoaHoc)}`}>{item.hoaHoc}</td>
                      <td className={`px-4 py-3 text-center ${getScoreColor(item.tongDiem)}`}>{item.tongDiem}</td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td className="py-6 text-center text-gray-500" colSpan="6">
                      Đang tải dữ liệu...
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>

          {/* CARD LIST — chỉ mobile (dưới sm) */}
          <div className="flex flex-col gap-3 sm:hidden">
            {list.length > 0 ? (
              list.map((item, index) => (
                <div
                  key={index}
                  className="bg-gray-50 rounded-xl px-4 py-3 flex items-center gap-4"
                >
                  {/* Rank */}
                  <span className="text-2xl font-bold text-gray-200 w-8 shrink-0">
                    {index + 1}
                  </span>

                  {/* SBD + scores */}
                  <div className="flex-1">
                    <p className="font-semibold text-gray-800 mb-2">{item.sbd}</p>
                    <div className="grid grid-cols-3 gap-2 text-sm">
                      <div className="flex flex-col items-center bg-white rounded-lg py-1">
                        <span className="text-xs text-gray-400">Toán</span>
                        <span className={getScoreColor(item.toan)}>{item.toan}</span>
                      </div>
                      <div className="flex flex-col items-center bg-white rounded-lg py-1">
                        <span className="text-xs text-gray-400">Lý</span>
                        <span className={getScoreColor(item.vatLy)}>{item.vatLy}</span>
                      </div>
                      <div className="flex flex-col items-center bg-white rounded-lg py-1">
                        <span className="text-xs text-gray-400">Hóa</span>
                        <span className={getScoreColor(item.hoaHoc)}>{item.hoaHoc}</span>
                      </div>
                    </div>
                  </div>

                  {/* Tổng */}
                  <div className="flex flex-col items-center shrink-0">
                    <span className="text-xs text-gray-400">Tổng</span>
                    <span className={`text-base ${getScoreColor(item.tongDiem)}`}>
                      {item.tongDiem}
                    </span>
                  </div>
                </div>
              ))
            ) : (
              <p className="py-6 text-center text-gray-500">Đang tải dữ liệu...</p>
            )}
          </div>

        </section>
      </div>
    </div>
  );
}

export default TopGroupA;