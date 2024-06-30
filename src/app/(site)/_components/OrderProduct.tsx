import React from 'react';

import { Button } from '@/components/ui/button';
import { useRouter } from 'next/navigation';

const OrderProduct = ({
  productID
}: {
  productID: number
}) => {
  const router = useRouter();
  
  const orderProduct = async () => {
    try {
      const response = await fetch(process.env.NEXT_PUBLIC_API_GATEWAY_URL + '/order', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          "product_id": productID
        }),
        credentials: 'include',
      });

      if (response.ok) {
        router.push('/order/created');
      }
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <>
      <Button onClick={orderProduct}>Order Now</Button>
    </>
  );
};

export default OrderProduct;