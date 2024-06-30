import { z } from "zod";

export const authSchema = z.object({
  email: z.string().min(1, { message: 'This filed has to be filled' }).email("Please enter a valid email."),
  password: z.string().min(1, { message: 'This filed has to be filled' })
});

export const addProductSchema = z.object({
  name: z.string().min(1, { message: 'This field has to be filled' }),
  price: z
    .string()
    .min(1, { message: 'This field has to be filled' })
    .regex(/^\d+(\.\d+)?$/, { message: 'Price must be a valid number' })
    .transform((val) => parseFloat(val)),
});

export const updateOrderStatusSchema = z.object({
  status: z.string().min(1, { message: 'This field has to be filled' }),
});