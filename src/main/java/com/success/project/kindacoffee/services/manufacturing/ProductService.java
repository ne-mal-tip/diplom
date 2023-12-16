package com.success.project.kindacoffee.services.manufacturing;

import com.success.project.kindacoffee.entities.manufacturing.Product;
import com.success.project.kindacoffee.entities.people.Employee;
import com.success.project.kindacoffee.services.CrudAbstractService;
import com.success.project.kindacoffee.util.frontend.wrappers.ProductForm;

import java.util.Optional;

public interface ProductService extends CrudAbstractService<Product, Integer> {

    Optional<Product> findByName(String name);

    Product saveOrUpdateProduct(ProductForm form, Employee employee);

    Product createNew(String name, Employee employee);
}
