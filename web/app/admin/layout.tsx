import AppContent from "@/components/AppContent";
import AppNavBar from "@/components/AppNavBar";


export const metadata = {
  title: "Magic Emerge Admin",
  description: "Admin ManageMent",
};

export default function AdminLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <section className="flex flex-col mx-auto min-h-screen">
      <div className="grid grid-cols-18 gap-4 w-full h-full">
        <div className="col-span-2 mt-6 ml-8 h-full bg-white rounded shadow-lg">
          <div className="flex flex-col items-center h-full">
            <AppNavBar />
          </div>
        </div>
        <div className="col-span-16 ml-4 mt-6 h-full bg-white mr-8 rounded shadow-lg">
          <AppContent>{children}</AppContent>
        </div>
      </div>
    </section>
  );
}
