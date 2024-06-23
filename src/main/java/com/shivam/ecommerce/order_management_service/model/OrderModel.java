package com.shivam.ecommerce.order_management_service.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class OrderModel {
    @Id
    @SequenceGenerator(name = "order_id_sequence", sequenceName = "order_id_sequence", allocationSize = 1)
    @GeneratedValue(generator = "order_id_sequence", strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String orderFor;
    private Double amount;

    private LocalDateTime orderDate;
    private String status;

    public OrderModel(Integer id, String orderFor, Double amount, LocalDateTime orderDate, String status) {
        this.id = id;
        this.orderFor = orderFor;
        this.amount = amount;
        this.orderDate = orderDate;
        this.status = status;
    }

    public OrderModel() {
    }

    @PrePersist
    protected void onCreate() {
        this.orderDate = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderFor() {
        return orderFor;
    }

    public void setOrderFor(String orderFor) {
        this.orderFor = orderFor;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = LocalDateTime.now();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}