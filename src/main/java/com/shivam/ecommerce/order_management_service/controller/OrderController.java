package com.shivam.ecommerce.order_management_service.controller;

import com.shivam.ecommerce.order_management_service.model.ApiResponse;
import com.shivam.ecommerce.order_management_service.model.OrderModel;
import com.shivam.ecommerce.order_management_service.service.OrderService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderModel>> getOrderDetails(@PathVariable Integer id) {
        OrderModel order = orderService.getOrderDetails(id);
        if (order != null) {
            ApiResponse<OrderModel> response = new ApiResponse<>(HttpStatus.OK.value(), "Order found", order);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<OrderModel> response = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Order not found", null);

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<OrderModel>> createOrder(@RequestBody OrderModel orderRequest) {
        OrderModel order = orderService.createOrder(orderRequest);

        ApiResponse<OrderModel> response = new ApiResponse<>(HttpStatus.CREATED.value(), "Order created", order);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    static record UpdatedStatus(String status){}

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderModel>> updateOrderDetails(@PathVariable Integer id, @RequestBody UpdatedStatus updatedStatus) {
        OrderModel order = orderService.updateOrder(id, updatedStatus.status());
        if (order != null) {
            ApiResponse<OrderModel> response = new ApiResponse<>(HttpStatus.OK.value(), "Order updated successfully", order);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<OrderModel> response = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Order not found", null);

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
