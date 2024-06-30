'use client';

import { useEffect, useState } from 'react';

import { zodResolver } from '@hookform/resolvers/zod';
import { useForm } from 'react-hook-form';
import { z } from 'zod';

import { updateOrderStatusSchema } from '@/lib/schema';

import { Button } from '@/components/ui/button';
import { Dialog, DialogContent, DialogDescription, DialogHeader, DialogTitle } from '@/components/ui/dialog';
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form';
import { Input } from '@/components/ui/input';
import { Pencil } from 'lucide-react';

const EditOrderStatus = ({
  status,
  orderID,
  refetch
}: {
  status: string,
  orderID: number,
  refetch: () => void
}) => {
  const [orderUpdate, setUpdateOrder] = useState(false);
  const [error, setError] = useState<string | null>();

  useEffect(() => {
    setError(null);
  }, [orderUpdate]);

  const form = useForm<z.infer<typeof updateOrderStatusSchema>>({
    resolver: zodResolver(updateOrderStatusSchema),
    defaultValues: {
      status
    }
  });

  const updateOrderSubmit = async (data: z.infer<typeof updateOrderStatusSchema>) => {
    try {
      const response = await fetch(process.env.NEXT_PUBLIC_API_GATEWAY_URL + '/order/' + orderID, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
        credentials: 'include',
      });

      if (response.ok) {
        refetch();
        setUpdateOrder(false);
      } else {
        const error = await response.json();

        setError(error.message);
      }
    } catch (error) {
      console.error(error);
      setError("Something went wrong!");
    }
  };

  return (
    <>
      <div className='flex items-center gap-2'>
        <p>{status}</p>
        <Pencil size={15} onClick={() => setUpdateOrder(true)} className='cursor-pointer' />
      </div>

      <Dialog open={orderUpdate} onOpenChange={setUpdateOrder}>
        <DialogContent>
          <DialogHeader>
            <DialogTitle>
              Update Product
            </DialogTitle>
            <DialogDescription>
            </DialogDescription>
          </DialogHeader>

          {error &&
            <div className='bg-red-500 rounded-md text-white p-4 flex flex-col gap-2 text-sm'>
              Could not update order
              <p>{error}</p>
            </div>
          }

          <Form {...form}>
            <form
              onSubmit={form.handleSubmit(updateOrderSubmit)}
              className='flex flex-col gap-4'
            >
              <FormField
                control={form.control}
                name='status'
                render={({ field, formState: { isSubmitting } }) => (
                  <FormItem>
                    <FormLabel>New Status</FormLabel>
                    <FormControl>
                      <Input {...field} disabled={isSubmitting} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <Button type='submit'>Update</Button>
            </form>
          </Form>
        </DialogContent>
      </Dialog>
    </>
  );
};

export default EditOrderStatus;