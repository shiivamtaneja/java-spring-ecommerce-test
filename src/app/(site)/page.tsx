import OrdersComponent from "./_components/Orders";
import ProductsComponent from "./_components/Products";

export default function Home() {
  return (
    <div className="h-full max-w-screen-xl mx-auto w-full">
      <div className="flex flex-col gap-4 mt-12">
        <h1 className="text-2xl font-bold">Product Catalog</h1>

        <ProductsComponent />
      </div>

      <div className="flex flex-col gap-4 mt-12">
        <h1 className="text-2xl font-bold">Orders</h1>

        <OrdersComponent />
      </div>
    </div>
  );
}
