package com.shivam.ecommerce.order_management_service.service;

import com.shivam.ecommerce.order_management_service.feign.ProductInterface;
import com.shivam.ecommerce.order_management_service.model.ApiResponse;
import com.shivam.ecommerce.order_management_service.exception.NotFoundException;
import com.shivam.ecommerce.order_management_service.model.Orders;
import com.shivam.ecommerce.order_management_service.model.Product;
import com.shivam.ecommerce.order_management_service.repository.OrderRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;

    private final ProductInterface productInterface;

    // Constructor injection of OrderRepository and ProductInterface
    public OrderService(OrderRepository orderRepository, ProductInterface productInterface) {
        this.orderRepository = orderRepository;
        this.productInterface = productInterface;
    }

    /**
     * Retrieves all order details from the repository, sorted by ID in ascending order.
     *
     * @return A list of all orders.
     */
    public List<Orders> getAllOrderDetails() {
        return orderRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param id The ID of the order to retrieve.
     * @return The order with the given ID.
     * @throws NotFoundException If the order with the given ID is not found.
     */
    public Orders getOrderDetails(Integer id) {
        return orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found with id: " + id));
    }

    /**
     * Creates a new order based on the product ID.
     *
     * @param id The ID of the product to create an order for.
     * @return The created order, or null if the product does not exist.
     */
    public Orders createOrder(Integer id) {
        try {
            ResponseEntity<ApiResponse<Product>> productDetailsResponse = productInterface.getProductById(id);

            // Check if the response status code is OK
            if (productDetailsResponse.getStatusCode().is2xxSuccessful()) {
                ApiResponse<Product> productDetails = productDetailsResponse.getBody();

                if (productDetails != null && productDetails.getData() != null) {
                    Product product = productDetails.getData();

                    // Create a new order based on the product details
                    Orders orderReq = new Orders();
                    orderReq.setOrderFor(product.getName());
                    orderReq.setAmount(product.getPrice());
                    orderReq.setProductId(product.getId());
                    orderReq.setStatus("created");

                    // Save and return the created order
                    return orderRepository.save(orderReq);
                } else {
                    throw new NotFoundException("Product data is missing for product id: " + id);
                }
            }

        } catch (FeignException e) {
            if (e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new NotFoundException("Product not found with id: " + id);
            }

            throw e;
        }

        return null;
    }

    /**
     * Updates the status of an existing order.
     *
     * @param id        The ID of the order to update.
     * @param newStatus The new status to set for the order.
     * @return The updated order.
     * @throws NotFoundException If the order with the given ID is not found.
     */
    public Orders updateOrder(Integer id, String newStatus) {
        Orders order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found with id: " + id));

        order.setStatus(newStatus);
        return orderRepository.save(order);
    }

    /**
     * Deletes an order by its ID.
     *
     * @param id The ID of the order to delete.
     * @throws NotFoundException If the order with the given ID is not found.
     */
    public void deleteOrder(Integer id) {
        Orders order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found with id: " + id));

        orderRepository.deleteById(id);
    }
}
