import ChartLevel from "../components/ChartLevel";

function ReportPage() {
    return (
        <div className="px-4 sm:px-6 md:px-10 py-6 md:py-10 bg-gray-100 min-h-screen">
            <div className="max-w-6xl mx-auto">
                <h1 className="text-xl sm:text-2xl md:text-3xl font-bold mb-6">
                    Báo cáo
                </h1>

                <div className="bg-white p-4 sm:p-6 md:p-8 rounded-2xl shadow">
                    <ChartLevel />
                </div>
            </div>
        </div>
    );
}

export default ReportPage;