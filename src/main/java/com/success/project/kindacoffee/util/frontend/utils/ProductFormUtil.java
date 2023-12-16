package com.success.project.kindacoffee.util.frontend.utils;

import com.success.project.kindacoffee.entities.manufacturing.Product;
import com.success.project.kindacoffee.services.people.EmployeeService;
import com.success.project.kindacoffee.util.frontend.forms.ManufacturingProcessForm;
import com.success.project.kindacoffee.util.frontend.wrappers.ProductForm;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductFormUtil implements AbstractUnwrapUtil<Product, ProductForm> {

    private final EmployeeService employeeService;

    public ProductFormUtil(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public static ProductForm wrap(Product product, String username) {
        List<ManufacturingProcessForm> manufacturingProcessForms = ManufacturingProcessFormUtil.wrap(product.getManufacturingProcessList());
        return new ProductForm(product.getId(), product.getName(), username, manufacturingProcessForms);
    }

    public static ProductForm wrap(Product product) {
        List<ManufacturingProcessForm> manufacturingProcessForms = ManufacturingProcessFormUtil.wrap(product.getManufacturingProcessList());
        return new ProductForm(product.getId(), product.getName(), product.getEmployee()
                .getUser()
                .getUsername(), manufacturingProcessForms);
    }

    @Override
    public Product unwrapSingle(ProductForm form) {
        Product product = new Product();
        product.setId(form.getProductId());
        product.setName(form.getProductName());
        product.setEmployee(employeeService.findByUsername(form.getUsername())
                .get());
        return product;
    }

    @Override
    public List<Product> unwrap(List<ProductForm> forms) {
        return null;
    }


}
