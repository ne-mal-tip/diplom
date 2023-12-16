package com.success.project.kindacoffee.services.manufacturing.impl;

import com.success.project.kindacoffee.entities.manufacturing.Product;
import com.success.project.kindacoffee.entities.people.Employee;
import com.success.project.kindacoffee.exceptions.EntityDoesntExistException;
import com.success.project.kindacoffee.repositories.manufacturing.ProductRepository;
import com.success.project.kindacoffee.services.manufacturing.ProductService;
import com.success.project.kindacoffee.util.frontend.utils.ProductFormUtil;
import com.success.project.kindacoffee.util.frontend.wrappers.ProductForm;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository repository;
    private final ProductFormUtil productFormUtil;


    public ProductServiceImpl(ProductRepository repository, ProductFormUtil productFormUtil) {
        this.repository = repository;
        this.productFormUtil = productFormUtil;
    }

    public Product save(Product product) {
        Product saved = repository.save(product);
        logProductInfo("saved", saved);
        return saved;
    }

    @Override
    public Product createNew(String name, Employee employee) {
        logger.info("Product with name {} will be created", name);
        Optional<Product> productOptional = findByName(name);
        return productOptional.orElseGet(() -> {
            Product newProduct = new Product();
            newProduct.setName(name);
            newProduct.setEmployee(employee);
            return save(newProduct);
        });
    }

    public Product saveOrUpdateProduct(ProductForm productForm, Employee employee) throws IllegalArgumentException {
        if (Objects.nonNull(productForm.getProductId())) return find(productForm.getProductId()).map(foundProduct -> {
                    Product unwrappedProduct = productFormUtil.unwrapSingle(productForm);
                    unwrappedProduct.setId(foundProduct.getId());
                    return save(unwrappedProduct);
                })
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        return createNew(productForm.getProductName(), employee);
    }

    public Optional<Product> find(Integer id) {
        logSearchInfo("id", id);
        if (Objects.isNull(id)) {
            return Optional.empty();
        }
        return repository.findById(id);
    }

    @Override
    public Optional<Product> findByName(String name) {
        logSearchInfo("name", name);
        return repository.findByName(name);
    }

    public List<Product> findAll() {
        logger.debug("Searching all Products");
        List<Product> list = repository.findAll();
        logger.info("Product list was found");
        return list;
    }

    public boolean delete(Integer id) {
        logSearchInfo("id", id);
        return find(id).map(product -> {
                    repository.delete(product);
                    logger.info("Product with id={} was deleted", id);
                    return true;
                })
                .orElseThrow(() -> new EntityDoesntExistException(id, Product.class, "Error deleting Product with id=" + id));
    }

    private void logSearchInfo(String key, Object value) {
        logger.debug("Searching Product with {} {}", key, value);
    }

    private void logProductInfo(String action, Product product) {
        logger.info("Product {} {} with id {}", action, product.getName(), product.getId());
    }
}
