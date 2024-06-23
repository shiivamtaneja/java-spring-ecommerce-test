package com.shivam.ecommerce.order_management_service.feign;

import com.shivam.ecommerce.order_management_service.model.ApiResponse;
import com.shivam.ecommerce.order_management_service.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("PRODUCT-CATALOG-SERVICE")
public interface ProductInterface {
    @GetMapping("api/v1/product/{id}")
    ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable Integer id);
}
