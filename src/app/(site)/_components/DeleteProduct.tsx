import { Delete } from 'lucide-react';

const DeleteProduct = ({
  productID,
  refetch
}: {
  productID: number,
  refetch: () => void,
}) => {
  const deleteProduct = async () => {
    const window = confirm("Are you sure you want to delete this product?");

    if (!window) {
      return;
    }

    try {
      const response = await fetch(process.env.NEXT_PUBLIC_API_GATEWAY_URL + '/product/' + productID, {
        method: 'DELETE',
        credentials: 'include',
      });

      if (response.ok) {
        refetch();
      }
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div className='cursor-pointer' onClick={deleteProduct}>
      <Delete size={20} />
    </div>
  );
};

export default DeleteProduct;