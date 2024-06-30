'use client';

import React, { useEffect, useState } from 'react';

import useQuery from '@/hooks/useQuery';
import { zodResolver } from '@hookform/resolvers/zod';
import { useForm } from 'react-hook-form';
import { z } from 'zod';

import { addProductSchema } from '@/lib/schema';
import { ProductT } from '@/lib/types';

import ErrorComponent from '@/components/ErrorComponent';
import Spinner from '@/components/spinner';
import { Button } from '@/components/ui/button';
import { Dialog, DialogContent, DialogDescription, DialogHeader, DialogTitle } from '@/components/ui/dialog';
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form';
import { Input } from '@/components/ui/input';

import DeleteProduct from './DeleteProduct';
import EditProduct from './EditProduct';
import OrderProduct from './OrderProduct';

const ProductsComponent = () => {
  const [addProduct, setAddProduct] = useState(false);
  const [error, setError] = useState<string | null>();

  useEffect(() => {
    setError(null);
  }, [addProduct]);

  const { data, errorMsg, isLoading, refetch } = useQuery<ProductT[], null>({
    errMsg: 'Error fetching products',
    options: {
      method: 'GET',
    },
    queryFn: process.env.NEXT_PUBLIC_API_GATEWAY_URL + '/product',
  });

  const form = useForm<z.infer<typeof addProductSchema>>({
    resolver: zodResolver(addProductSchema),
    defaultValues: {
      name: '',
      price: 0
    }
  });

  const addProductSubmit = async (data: z.infer<typeof addProductSchema>) => {
    try {
      const response = await fetch(process.env.NEXT_PUBLIC_API_GATEWAY_URL + '/product', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
        credentials: 'include',
      });

      if (response.ok) {
        refetch();
        setAddProduct(false);
      } else {
        const error = await response.json();

        setError(error.message);
      }
    } catch (error) {
      console.error(error);
      setError("Something went wrong!");
    }
  };

  if (isLoading || errorMsg) {
    return (
      <>
        {isLoading ? <Spinner /> : errorMsg && <ErrorComponent error={errorMsg} />}
      </>
    );
  }

  return (
    <>
      <Dialog open={addProduct} onOpenChange={setAddProduct}>
        <DialogContent>
          <DialogHeader>
            <DialogTitle>
              Add Product
            </DialogTitle>
            <DialogDescription>
            </DialogDescription>
          </DialogHeader>

          {error &&
            <div className='bg-red-500 rounded-md text-white p-4 flex flex-col gap-2 text-sm'>
              Could not add product
              <p>{error}</p>
            </div>
          }

          <Form {...form}>
            <form
              onSubmit={form.handleSubmit(addProductSubmit)}
              className='flex flex-col gap-4'
            >
              <FormField
                control={form.control}
                name='name'
                render={({ field, formState: { isSubmitting } }) => (
                  <FormItem>
                    <FormLabel>Name of the product</FormLabel>
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
                    <FormLabel>Price of the product</FormLabel>
                    <FormControl>
                      <Input {...field} disabled={isSubmitting} min={0} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <Button type='submit'>Submit</Button>
            </form>
          </Form>
        </DialogContent>
      </Dialog>

      {data.data?.length === 0 ?
        <div className='flex gap-2 items-center'>
          <p>No products found!</p>
          <Button variant='outline' onClick={() => setAddProduct(true)}>Add Product</Button>
        </div>
        :
        <>
          <div className='flex flex-wrap gap-2'>
            {data.data?.map(({ id, name, price }, index) => (
              <div key={index} className='p-3 border rounded-md flex flex-col gap-2'>
                <div className='flex justify-between'>
                  <div>
                    <p>{name}</p>
                    <p>₹{price}</p>
                  </div>
                  <div className='flex flex-col gap-2'>
                    <EditProduct
                      productID={id}
                      refetch={refetch}
                      name={name}
                      price={price}
                    />
                    <DeleteProduct
                      productID={id}
                      refetch={refetch}
                    />
                  </div>
                </div>

                <OrderProduct
                  productID={id}
                />
              </div>
            ))}
          </div>

          <div>
            <Button variant='outline' onClick={() => setAddProduct(true)}>Add Product</Button>
          </div>
        </>
      }
    </>
  );
};

export default ProductsComponent;