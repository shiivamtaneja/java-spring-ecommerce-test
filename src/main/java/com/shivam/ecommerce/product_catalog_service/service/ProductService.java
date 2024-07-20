package com.shivam.ecommerce.product_catalog_service.service;

import com.shivam.ecommerce.product_catalog_service.exception.NotFoundException;
import com.shivam.ecommerce.product_catalog_service.model.Product;
import com.shivam.ecommerce.product_catalog_service.repository.ProductRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    // Constructor injection of the ProductRepository dependency
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Retrieves all products from the repository, sorted by ID in ascending order.
     *
     * @return A list of all products.
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id The ID of the product to retrieve.
     * @return The product with the given ID.
     * @throws NotFoundException If no product with the specified ID is found.
     */
    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found with id: " + id));
    }

    /**
     * Adds a new product to the repository.
     *
     * @param product The product to add.
     * @return The added product.
     */
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Updates an existing product with new details.
     *
     * @param id             The ID of the product to update.
     * @param productDetails The new details to update the product with.
     * @return The updated product.
     * @throws NotFoundException If no product with the specified ID is found.
     */
    public Product updateProduct(Integer id, Product productDetails) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found with id: " + id));

        existingProduct.setName(productDetails.getName());
        existingProduct.setPrice(productDetails.getPrice());

        return productRepository.save(existingProduct);
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id The ID of the product to delete.
     * @throws NotFoundException If no product with the specified ID is found.
     */
    public void deleteProduct(Integer id) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found with id: " + id));

        productRepository.deleteById(id);
    }
}
