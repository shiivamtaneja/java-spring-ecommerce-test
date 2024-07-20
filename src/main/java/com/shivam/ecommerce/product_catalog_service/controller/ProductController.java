package com.shivam.ecommerce.product_catalog_service.controller;

import com.shivam.ecommerce.product_catalog_service.exception.NotFoundException;
import com.shivam.ecommerce.product_catalog_service.model.ApiResponse;
import com.shivam.ecommerce.product_catalog_service.model.Product;
import com.shivam.ecommerce.product_catalog_service.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/product") // Specifies the base URL for this controller's endpoints
public class ProductController {
    private final ProductService productService; // Service for handling product-related business logic

    // Constructor injection for ProductService
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Handles GET requests to retrieve all products.
     *
     * @return A ResponseEntity containing an ApiResponse with a list of products and HTTP status 200 (OK) if products are found,
     *         or HTTP status 404 (Not Found) if no products are available.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts() {
        List<Product> products = productService.getAllProducts();

        if (products.isEmpty()) {
            throw new NotFoundException("No products found");
        }

        ApiResponse<List<Product>> response = new ApiResponse<>(HttpStatus.OK.value(), "Products Found", products);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Handles GET requests to retrieve a specific product by its ID.
     *
     * @param id The ID of the product to retrieve.
     * @return A ResponseEntity containing an ApiResponse with the product details and HTTP status 200 (OK) if found,
     * or HTTP status 404 (Not Found) if the product does not exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable Integer id) {
        Product product = productService.getProductById(id);

        ApiResponse<Product> response = new ApiResponse<>(HttpStatus.OK.value(), "Product found", product);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Handles POST requests to add a new product.
     *
     * @param product The product details to add.
     * @return A ResponseEntity containing an ApiResponse with the created product and HTTP status 201 (Created).
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Product>> addProduct(@RequestBody Product product) {
        Product saveProduct = productService.addProduct(product);

        ApiResponse<Product> response = new ApiResponse<>(HttpStatus.CREATED.value(), "Product created successfully", saveProduct);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Handles PUT requests to update an existing product.
     *
     * @param id             The ID of the product to update.
     * @param productDetails The new details for the product.
     * @return A ResponseEntity containing an ApiResponse with the updated product and HTTP status 200 (OK) if successful,
     * or HTTP status 404 (Not Found) if the product does not exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> updateProduct(@PathVariable Integer id, @RequestBody Product productDetails) {
        Product updatedProduct = productService.updateProduct(id, productDetails);

        ApiResponse<Product> response = new ApiResponse<>(HttpStatus.OK.value(), "Product updated successfully", updatedProduct);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Handles DELETE requests to remove a product by its ID.
     *
     * @param id The ID of the product to delete.
     * @return A ResponseEntity containing an ApiResponse with HTTP status 204 (No Content) if the product was deleted,
     * or HTTP status 404 (Not Found) if the product does not exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);

        ApiResponse<Void> response = new ApiResponse<>(HttpStatus.NO_CONTENT.value(), "Product deleted successfully", null);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}