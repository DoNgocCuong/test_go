import { useState } from "react";
import scoreApi from "../api/scoresApi";

function ListScores() {
  const [score, setScore] = useState(null);
  const [searchText, setSearchText] = useState("");
  const [loading, setLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  const getScoreColor = (value) => {
    if (value === null || value === undefined) return "";
    if (value >= 8) return "text-blue-600 font-semibold";
    if (value >= 6) return "text-green-600 font-semibold";
    if (value >= 4) return "text-yellow-600 font-semibold";
    return "text-red-600 font-semibold";
  };

  const handleSearch = async () => {
    if (!searchText.trim()) return;

    try {
      setLoading(true);
      setErrorMessage("");
      setScore(null);

      const res = await scoreApi.getScoresByRegistrationNumber(
        searchText.trim()
      );

      setScore(res.data.result);
    } catch (error) {
      console.error(error);

      if (error.response?.data?.message) {
        setErrorMessage(error.response.data.message);
      } else {
        setErrorMessage("Có lỗi xảy ra, vui lòng thử lại");
      }

      setScore(null);
    } finally {
      setLoading(false);
    }
  };

  const scoreFields = score
    ? [
      { label: "Toán", value: score.toan, colored: true },
      { label: "Ngữ Văn", value: score.nguVan, colored: true },
      { label: "Ngoại Ngữ", value: score.ngoaiNgu, colored: true },
      { label: "Vật Lý", value: score.vatLi, colored: true },
      { label: "Hóa Học", value: score.hoaHoc, colored: true },
      { label: "Sinh Học", value: score.sinhHoc, colored: true },
      { label: "Lịch Sử", value: score.lichSu, colored: false },
      { label: "Địa Lý", value: score.diaLi, colored: false },
      { label: "GDCD", value: score.gdcd, colored: false },
      { label: "Mã NN", value: score.maNgoaiNgu, colored: false },
    ]
    : [];

  return (
    <div className="grid grid-cols-12 gap-4 px-4 sm:px-6 md:px-10">
      <div className="col-span-12 lg:col-span-10 lg:col-start-2 my-6 sm:my-8 shadow-lg rounded-2xl bg-white">

        {/* HEADER */}
        <div className="p-4 sm:p-6 border-b">
          <h2 className="font-bold text-lg sm:text-xl mb-3">
            Tra cứu điểm
          </h2>

          <div className="flex flex-col sm:flex-row gap-3">
            <input
              type="text"
              placeholder="Nhập số báo danh..."
              value={searchText}
              onChange={(e) => setSearchText(e.target.value)}
              onKeyDown={(e) => e.key === "Enter" && handleSearch()}
              className="px-4 py-2 border border-gray-400 rounded w-full"
            />

            <button
              onClick={handleSearch}
              disabled={loading}
              className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 transition disabled:bg-gray-400 w-full sm:w-auto"
            >
              {loading ? "Đang tìm..." : "Tìm"}
            </button>
          </div>
        </div>

        {/* RESULT */}
        <div className="p-4 sm:p-6">

          {loading && (
            <p className="text-gray-500">Đang tải dữ liệu...</p>
          )}

          {!loading && errorMessage && (
            <div className="text-red-600 bg-red-50 p-3 rounded-lg mb-4">
              {errorMessage}
            </div>
          )}

          {!loading && score && (
            <>
              {/* SBD header */}
              <p className="text-sm text-gray-500 mb-3">
                Số báo danh:{" "}
                <span className="font-semibold text-gray-800">{score.sbd}</span>
              </p>

              {/* TABLE — chỉ hiện từ md trở lên */}
              <div className="hidden md:block overflow-x-auto">
                <table className="min-w-full text-left text-sm">
                  <thead className="text-gray-500">
                    <tr>
                      <th className="px-4 py-2">SBD</th>
                      <th className="px-4 py-2">Toán</th>
                      <th className="px-4 py-2">Ngữ Văn</th>
                      <th className="px-4 py-2">Ngoại Ngữ</th>
                      <th className="px-4 py-2">Vật Lý</th>
                      <th className="px-4 py-2">Hóa Học</th>
                      <th className="px-4 py-2">Sinh Học</th>
                      <th className="px-4 py-2">Lịch Sử</th>
                      <th className="px-4 py-2">Địa Lý</th>
                      <th className="px-4 py-2">GDCD</th>
                      <th className="px-4 py-2">Mã NN</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr className="border-t">
                      <td className="px-4 py-2 font-medium">{score.sbd}</td>
                      <td className={`px-4 py-2 ${getScoreColor(score.toan)}`}>{score.toan ?? "-"}</td>
                      <td className={`px-4 py-2 ${getScoreColor(score.nguVan)}`}>{score.nguVan ?? "-"}</td>
                      <td className={`px-4 py-2 ${getScoreColor(score.ngoaiNgu)}`}>{score.ngoaiNgu ?? "-"}</td>
                      <td className={`px-4 py-2 ${getScoreColor(score.vatLi)}`}>{score.vatLi ?? "-"}</td>
                      <td className={`px-4 py-2 ${getScoreColor(score.hoaHoc)}`}>{score.hoaHoc ?? "-"}</td>
                      <td className={`px-4 py-2 ${getScoreColor(score.sinhHoc)}`}>{score.sinhHoc ?? "-"}</td>
                      <td className="px-4 py-2">{score.lichSu ?? "-"}</td>
                      <td className="px-4 py-2">{score.diaLi ?? "-"}</td>
                      <td className="px-4 py-2">{score.gdcd ?? "-"}</td>
                      <td className="px-4 py-2">{score.maNgoaiNgu ?? "-"}</td>
                    </tr>
                  </tbody>
                </table>
              </div>

              {/* CARD GRID — chỉ hiện dưới md (mobile + landscape phone) */}
              <div className="grid grid-cols-2 sm:grid-cols-3 gap-3 md:hidden">
                {scoreFields.map((field) => (
                  <div
                    key={field.label}
                    className="bg-gray-50 rounded-xl px-4 py-3 flex flex-col gap-1"
                  >
                    <span className="text-xs text-gray-400 uppercase tracking-wide">
                      {field.label}
                    </span>
                    <span
                      className={`text-base ${field.colored
                          ? getScoreColor(field.value)
                          : "font-medium text-gray-700"
                        }`}
                    >
                      {field.value ?? "-"}
                    </span>
                  </div>
                ))}
              </div>
            </>
          )}

          {!loading && !score && !errorMessage && (
            <p className="text-gray-500">
              Nhập số báo danh để tra cứu
            </p>
          )}
        </div>
      </div>
    </div>
  );
}

export default ListScores;