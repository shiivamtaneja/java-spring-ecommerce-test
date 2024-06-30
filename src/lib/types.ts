export type ProductT = {
  id: number,
  name: string,
  price: number
};

export type OrderT = {
  id: number,
  status: string,
  orderFor: string,
  amount: number,
  orderDate: string
}