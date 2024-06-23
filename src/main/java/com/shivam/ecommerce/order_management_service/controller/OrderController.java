package com.shivam.ecommerce.order_management_service.controller;

import com.shivam.ecommerce.order_management_service.model.ApiResponse;
import com.shivam.ecommerce.order_management_service.model.Orders;
import com.shivam.ecommerce.order_management_service.service.OrderService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Orders>> getOrderDetails(@PathVariable Integer id) {
        Orders order = orderService.getOrderDetails(id);
        if (order != null) {
            ApiResponse<Orders> response = new ApiResponse<>(HttpStatus.OK.value(), "Order found", order);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<Orders> response = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Order not found", null);

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    record ProductId(Integer product_id) {
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Orders>> createOrder(@RequestBody ProductId orderRequest) {
        Orders order = orderService.createOrder(orderRequest.product_id);

        ApiResponse<Orders> response = new ApiResponse<>(HttpStatus.CREATED.value(), "Order created", order);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    record UpdatedStatus(String status) {
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Orders>> updateOrderDetails(@PathVariable Integer id, @RequestBody UpdatedStatus updatedStatus) {
        Orders order = orderService.updateOrder(id, updatedStatus.status());
        if (order != null) {
            ApiResponse<Orders> response = new ApiResponse<>(HttpStatus.OK.value(), "Order updated successfully", order);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<Orders> response = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Order not found", null);

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOrder(@PathVariable Integer id) {
        boolean isOrderDeleted = orderService.deleteOrder(id);

        if (isOrderDeleted) {
            ApiResponse<Void> response = new ApiResponse<>(HttpStatus.NO_CONTENT.value(), "Order deleted successfully", null);

            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            ApiResponse<Void> response = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Order not found", null);

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
