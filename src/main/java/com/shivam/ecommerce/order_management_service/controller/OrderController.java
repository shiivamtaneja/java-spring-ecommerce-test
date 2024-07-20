package com.shivam.ecommerce.order_management_service.controller;

import com.shivam.ecommerce.order_management_service.exception.NotFoundException;
import com.shivam.ecommerce.order_management_service.model.ApiResponse;
import com.shivam.ecommerce.order_management_service.model.Orders;
import com.shivam.ecommerce.order_management_service.service.OrderService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {
    private final OrderService orderService; // Service layer for handling order operations

    // Constructor injection for OrderService
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Handles GET requests to retrieve all order details.
     *
     * @return A ResponseEntity containing an ApiResponse with a list of orders and HTTP status 200 (OK) if products are found,
     * or HTTP status 404 (Not Found) if no orders are available.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Orders>>> getAllOrderDetails() {
        List<Orders> orders = orderService.getAllOrderDetails();

        if (orders.isEmpty()) {
            throw new NotFoundException("No orders found");
        }

        ApiResponse<List<Orders>> response = new ApiResponse<>(HttpStatus.OK.value(), "Orders found", orders);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Handles GET requests to retrieve details of a specific order by its ID.
     *
     * @param id The ID of the order to retrieve.
     * @return A ResponseEntity containing an ApiResponse with the order details and HTTP status 200 (OK) if found,
     * or HTTP status 404 (Not Found) if the order does not exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Orders>> getOrderDetails(@PathVariable Integer id) {
        Orders order = orderService.getOrderDetails(id);

        ApiResponse<Orders> response = new ApiResponse<>(HttpStatus.OK.value(), "Order found", order);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Represents the request body for creating an order, containing the product ID.
     */
    public record ProductId(Integer product_id) {
    }

    /**
     * Handles POST requests to create a new order.
     *
     * @param orderRequest The request body containing the product ID to create an order.
     * @return A ResponseEntity containing an ApiResponse with the created order and HTTP status 201 (Created).
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Orders>> createOrder(@RequestBody ProductId orderRequest) {
        Orders order = orderService.createOrder(orderRequest.product_id);

        ApiResponse<Orders> response = new ApiResponse<>(HttpStatus.CREATED.value(), "Order created", order);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Represents the request body for updating an order, containing the new status.
     */
    public record UpdatedStatus(String status) {
    }

    /**
     * Handles PUT requests to update the details of an existing order.
     *
     * @param id            The ID of the order to update.
     * @param updatedStatus The request body containing the new status for the order.
     * @return A ResponseEntity containing an ApiResponse with the updated order and HTTP status 200 (OK) if updated,
     * or HTTP status 404 (Not Found) if the order does not exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Orders>> updateOrderDetails(@PathVariable Integer id, @RequestBody UpdatedStatus updatedStatus) {
        Orders order = orderService.updateOrder(id, updatedStatus.status());

        ApiResponse<Orders> response = new ApiResponse<>(HttpStatus.OK.value(), "Order updated successfully", order);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Handles DELETE requests to remove an order by its ID.
     *
     * @param id The ID of the order to delete.
     * @return A ResponseEntity containing an ApiResponse with HTTP status 204 (No Content) if the order was deleted,
     * or HTTP status 404 (Not Found) if the order does not exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id);

        ApiResponse<Void> response = new ApiResponse<>(HttpStatus.NO_CONTENT.value(), "Order deleted successfully", null);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
