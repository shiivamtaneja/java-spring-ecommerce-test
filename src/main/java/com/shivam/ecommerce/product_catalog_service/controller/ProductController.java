package com.shivam.ecommerce.product_catalog_service.controller;

import com.shivam.ecommerce.product_catalog_service.model.ApiResponse;
import com.shivam.ecommerce.product_catalog_service.model.Product;
import com.shivam.ecommerce.product_catalog_service.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        ApiResponse<List<Product>> response = new ApiResponse<>(HttpStatus.OK.value(), "Products Found", products);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable Integer id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            ApiResponse<Product> response = new ApiResponse<>(HttpStatus.OK.value(), "Product found", product);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<Product> response = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Product not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Product>> addProduct(@RequestBody Product product) {
        Product saveProduct = productService.addProduct(product);

        ApiResponse<Product> response = new ApiResponse<>(HttpStatus.CREATED.value(), "Product created successfully", saveProduct);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> updateProduct(@PathVariable Integer id, @RequestBody Product productDetails) {
        Product updatedProduct = productService.updateProduct(id, productDetails);
        if (updatedProduct != null) {
            ApiResponse<Product> response = new ApiResponse<>(HttpStatus.OK.value(), "Product updated successfully", updatedProduct);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<Product> response = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Product not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Integer id) {
        boolean isDeleted = productService.deleteProduct(id);
        if (isDeleted) {
            ApiResponse<Void> response = new ApiResponse<>(HttpStatus.NO_CONTENT.value(), "Product deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            ApiResponse<Void> response = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Product not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}