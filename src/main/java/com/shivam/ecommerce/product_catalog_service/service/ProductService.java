package com.shivam.ecommerce.product_catalog_service.service;

import com.shivam.ecommerce.product_catalog_service.model.Product;
import com.shivam.ecommerce.product_catalog_service.repository.ProductRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Product getProductById(Integer id) {
        Optional<Product> product = productRepository.findById(id);

        return product.orElse(null);
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Integer id, Product productDetails) {
        Optional<Product> existingProduct = productRepository.findById(id);

        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();

            product.setName(productDetails.getName());
            product.setPrice(productDetails.getPrice());

            return productRepository.save(product);
        }

        return null;
    }

    public boolean deleteProduct(Integer id) {
        Optional<Product> existingProduct = productRepository.findById(id);

        if (existingProduct.isPresent()) {
            productRepository.deleteById(id);

            return true;
        }

        return false;
    }
}
