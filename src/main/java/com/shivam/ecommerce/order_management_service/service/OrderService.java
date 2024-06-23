package com.shivam.ecommerce.order_management_service.service;

import com.shivam.ecommerce.order_management_service.model.OrderModel;
import com.shivam.ecommerce.order_management_service.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderModel getOrderDetails(Integer id) {
        Optional<OrderModel> order = orderRepository.findById(id);

        return order.orElse(null);
    }

    public OrderModel createOrder(OrderModel orderRequest) {
        return orderRepository.save(orderRequest);
    }

    public OrderModel updateOrder(Integer id, String newStatus) {
        Optional<OrderModel> order = orderRepository.findById(id);

        if (order.isPresent()) {
            OrderModel newOrder = order.get();

            newOrder.setStatus(newStatus);

            return orderRepository.save(newOrder);
        }

        return null;
    }

    public boolean deleteOrder(Integer id) {
        Optional<OrderModel> order = orderRepository.findById(id);

        if (order.isPresent()) {
            orderRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
