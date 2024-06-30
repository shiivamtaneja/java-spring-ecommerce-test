'use client';

import { useEffect, useState } from 'react';

import { zodResolver } from '@hookform/resolvers/zod';
import { useForm } from 'react-hook-form';
import { z } from 'zod';

import { addProductSchema } from '@/lib/schema';

import { Button } from '@/components/ui/button';
import { Dialog, DialogContent, DialogDescription, DialogHeader, DialogTitle } from '@/components/ui/dialog';
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form';
import { Input } from '@/components/ui/input';
import { Pencil } from 'lucide-react';

const EditProduct = ({
  productID,
  refetch,
  name,
  price
}: {
  productID: number,
  refetch: () => void,
  name: string,
  price: number
}) => {
  const [updateProduct, setUpdateProduct] = useState(false);
  const [error, setError] = useState<string | null>();

  useEffect(() => {
    setError(null);
  }, [updateProduct]);

  const form = useForm<z.infer<typeof addProductSchema>>({
    resolver: zodResolver(addProductSchema),
    defaultValues: {
      name,
      price
    }
  });

  const updateProductSubmit = async (data: z.infer<typeof addProductSchema>) => {
    try {
      const response = await fetch(process.env.NEXT_PUBLIC_API_GATEWAY_URL + '/product/' + productID, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
        credentials: 'include',
      });

      if (response.ok) {
        refetch();
        setUpdateProduct(false);
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
      <div className='cursor-pointer' onClick={() => setUpdateProduct(true)}>
        <Pencil size={20} />
      </div>

      <Dialog open={updateProduct} onOpenChange={setUpdateProduct}>
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
              Could not update product
              <p>{error}</p>
            </div>
          }

          <Form {...form}>
            <form
              onSubmit={form.handleSubmit(updateProductSubmit)}
              className='flex flex-col gap-4'
            >
              <FormField
                control={form.control}
                name='name'
                render={({ field, formState: { isSubmitting } }) => (
                  <FormItem>
                    <FormLabel>New name of the product</FormLabel>
                    <FormControl>
                      <Input {...field} disabled={isSubmitting} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <FormField
                control={form.control}
                name='price'
                render={({ field, formState: { isSubmitting } }) => (
                  <FormItem>
                    <FormLabel>New price of the product</FormLabel>
                    <FormControl>
                      <Input {...field} disabled={isSubmitting} min={0} />
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

export default EditProduct;