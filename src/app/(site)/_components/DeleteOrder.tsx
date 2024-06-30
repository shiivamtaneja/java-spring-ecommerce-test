import { Delete } from 'lucide-react';

const DeleteOrder = ({
  orderID,
  refetch
}: {
  orderID: number,
  refetch: () => void,
}) => {
  const deleteOrder = async () => {
    const window = confirm("Are you sure you want to delete this order?");

    if (!window) {
      return;
    }

    try {
      const response = await fetch(process.env.NEXT_PUBLIC_API_GATEWAY_URL + '/order/' + orderID, {
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
    <div className='cursor-pointer' onClick={deleteOrder}>
      <Delete size={20} />
    </div>
  );
};

export default DeleteOrder;