package com.shivam.ecommerce.order_management_service.repository;

import com.shivam.ecommerce.order_management_service.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Integer> {
}
