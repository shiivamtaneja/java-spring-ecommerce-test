package com.shivam.ecommerce.order_management_service.service;

import com.shivam.ecommerce.order_management_service.feign.ProductInterface;
import com.shivam.ecommerce.order_management_service.model.ApiResponse;
import com.shivam.ecommerce.order_management_service.model.Orders;
import com.shivam.ecommerce.order_management_service.model.Product;
import com.shivam.ecommerce.order_management_service.repository.OrderRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    private final ProductInterface productInterface;

    public OrderService(OrderRepository orderRepository, ProductInterface productInterface) {
        this.orderRepository = orderRepository;
        this.productInterface = productInterface;
    }

    public List<Orders> getAllOrderDetails() {
        return orderRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Orders getOrderDetails(Integer id) {
        Optional<Orders> order = orderRepository.findById(id);

        return order.orElse(null);
    }

    public Orders createOrder(Integer id) {
        ResponseEntity<ApiResponse<Product>> productDetailsResponse = productInterface.getProductById(id);

        if (productDetailsResponse.getStatusCode() == HttpStatus.OK) {
            ApiResponse<Product> productDetails = productDetailsResponse.getBody();

            if (productDetails != null) {
                Product product = productDetails.getData();

                Orders orderReq = new Orders();
                orderReq.setOrderFor(product.getName());
                orderReq.setAmount(product.getPrice());
                orderReq.setProductId(product.getId());
                orderReq.setStatus("created");

                return orderRepository.save(orderReq);
            }
        } else {
            System.out.println("No product found!");
        }

        return null;
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
