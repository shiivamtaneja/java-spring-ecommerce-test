'use client';

import React from 'react';

import useQuery from '@/hooks/useQuery';

import { OrderT } from '@/lib/types';
import { convertTimestampToDateString } from '@/lib/utils';

import ErrorComponent from '@/components/ErrorComponent';
import Spinner from '@/components/spinner';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';
import DeleteOrder from './DeleteOrder';
import EditOrderStatus from './EditOrderStatus';

const OrdersComponent = () => {
  const { data, errorMsg, isLoading, refetch } = useQuery<OrderT[], null>({
    errMsg: 'Error fetching orders',
    options: {
      method: 'GET',
    },
    queryFn: process.env.NEXT_PUBLIC_API_GATEWAY_URL + '/order',
  });

  if (isLoading || errorMsg) {
    return (
      <>
        {isLoading ? <Spinner /> : errorMsg && <ErrorComponent error={errorMsg} />}
      </>
    );
  }

  return (
    <>
      {data.data?.length === 0 ?
        <>
          <p>No orders found!</p>
        </>
        :
        <div className='border shadow-sm rounded-lg'>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Order #</TableHead>
                <TableHead>Date</TableHead>
                <TableHead>Order For</TableHead>
                <TableHead>Status</TableHead>
                <TableHead>Total</TableHead>
              </TableRow>
            </TableHeader>

            <TableBody>
              {data.data?.map(({ id, amount, orderDate, orderFor, status }, index) => (
                <TableRow key={index}>
                  <TableCell>#{id}</TableCell>
                  <TableCell>{convertTimestampToDateString(orderDate)}</TableCell>
                  <TableCell>{orderFor}</TableCell>
                  <TableCell>
                    <EditOrderStatus
                      status={status}
                      orderID={id}
                      refetch={refetch}
                    />
                  </TableCell>
                  <TableCell>₹ {amount}</TableCell>
                  <TableCell>
                    <DeleteOrder
                      orderID={id}
                      refetch={refetch}
                    />
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </div>
      }
    </>
  );
};

export default OrdersComponent;