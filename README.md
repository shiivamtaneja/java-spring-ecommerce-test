# Project Setup

This README includes the instruction to clone only the `frontend` branch of the repository.

## Prerequisites
- Ensure you have Node.js, npm, and Yarn installed on your machine.

  - Use the following commands to check if they are installed:
    ```bash
      node -v
      npm -v
      yarn -v
    ```
  - If Node.js is not installed, download and install it from [Node.js official website](https://nodejs.org/en).
  - After installing Node.js, install Yarn globally using npm:
    ```bash
      npm install --global yarn
    ```

## Installation

1. Clone the frontend branch of this repository:
  ```bash
    git clone --branch frontend --single-branch https://github.com/shiivamtaneja/java-spring-ecommerce-test.git

cd java-spring-ecommerce-test
  ```
2. Install the dependencies:
  ```bash
    yarn install
  ```
3. Start the server:
  ```bash
    yarn dev
  ```
  - The server will start at http://localhost:3000.

## Configuration

Create an .env file in the root directory to set up the backend API endpoints. Use the `.env.example` file as a reference.

## API Endpoints

### Product Service
  - Base URL: `NEXT_PUBLIC_API_GATEWAY_URL + /product`
  - Endpoints:
    - GET `/`
      - Response: 
        ```ts
        {
          "statusCode": 200,
          "message": "Products Found",
          "data": {
            id: number,
            name: string,
            price: number
          }[]
        }
        ```

    - POST `/`
      - Request Body: 
        ```ts
        {
          price: number,
          name: string
        }
        ```

    - PUT `/:id`
      - Request Body: 
        ```ts
        {
          price: number,
          name: string
        }
        ```

    - DELETE `/:id`

### Order Service
  - Base URL: `NEXT_PUBLIC_API_GATEWAY_URL + /order`
  - Endpoints:
    - GET `/`
      - Response: 
        ```ts
        {
          "statusCode": 200,
          "message": "Products Found",
          "data": {
            id: number,
            orderFor: string,
            orderDate: string,
            status: string,
            amount: number
          }[]
        }
        ```

    - POST `/`
      - Request Body: 
        ```ts
        {
          product_id: number,
        }
        ```

    - PUT `/:id`
      - Request Body: 
        ```ts
        {
          status: string
        }
        ```

    - DELETE `/:id`

## Data Rendering

### Product Data
- For the Product component, the data array from the GET /product endpoint response is used to render the products.

### Order Data
- For the Order component, the data array from the GET /order endpoint response is used to render the orders.

## Type Definitions

```ts
  interface Product {
    id: number;
    name: string;
    price: number;
  }

  interface Order {
    id: number;
    orderFor: string;
    amount: number;
    orderDate: string;
    status: string;
  }
```