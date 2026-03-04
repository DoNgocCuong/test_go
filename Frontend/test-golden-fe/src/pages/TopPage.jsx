import TopGroupA from "../components/TopGroupA";

function TopPage() {
  return (
    <div className="px-4 sm:px-6 md:px-10 py-6 md:py-10">
      <div className="max-w-6xl mx-auto">
        <h1 className="text-xl sm:text-2xl md:text-3xl font-bold mb-4 md:mb-6">
          Top 10 học sinh khối A cao nhất
        </h1>

        <TopGroupA />
      </div>
    </div>
  );
}

export default TopPage;