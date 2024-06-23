package com.shivam.ecommerce.order_management_service.service;

import com.shivam.ecommerce.order_management_service.model.Orders;
import com.shivam.ecommerce.order_management_service.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Orders getOrderDetails(Integer id) {
        Optional<Orders> order = orderRepository.findById(id);

        return order.orElse(null);
    }

    public Orders createOrder(Orders orderRequest) {
        return orderRepository.save(orderRequest);
    }

    public Orders updateOrder(Integer id, String newStatus) {
        Optional<Orders> order = orderRepository.findById(id);

        if (order.isPresent()) {
            Orders newOrder = order.get();

            newOrder.setStatus(newStatus);

            return orderRepository.save(newOrder);
        }

        return null;
    }

    public boolean deleteOrder(Integer id) {
        Optional<Orders> order = orderRepository.findById(id);

        if (order.isPresent()) {
            orderRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
