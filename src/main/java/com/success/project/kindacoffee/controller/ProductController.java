package com.success.project.kindacoffee.controller;

import com.success.project.kindacoffee.entities.manufacturing.Action;
import com.success.project.kindacoffee.entities.manufacturing.ManufacturingProcess;
import com.success.project.kindacoffee.entities.manufacturing.Product;
import com.success.project.kindacoffee.entities.manufacturing.Robot;
import com.success.project.kindacoffee.entities.people.Employee;
import com.success.project.kindacoffee.services.manufacturing.ActionService;
import com.success.project.kindacoffee.services.manufacturing.ManufacturingProcessService;
import com.success.project.kindacoffee.services.manufacturing.ProductService;
import com.success.project.kindacoffee.services.manufacturing.RobotService;
import com.success.project.kindacoffee.services.people.EmployeeService;
import com.success.project.kindacoffee.util.frontend.utils.ManufacturingProcessFormUtil;
import com.success.project.kindacoffee.util.frontend.utils.ProductFormUtil;
import com.success.project.kindacoffee.util.frontend.wrappers.ProductForm;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ActionService actionService;
    private final RobotService robotService;
    private final EmployeeService employeeService;
    private final ManufacturingProcessService manufacturingProcessService;
    private final ManufacturingProcessFormUtil manufacturingProcessFormUtil;

    public ProductController(ProductService productService, ActionService actionService, RobotService robotService, EmployeeService employeeService, ManufacturingProcessService manufacturingProcessService, ManufacturingProcessFormUtil manufacturingProcessFormUtil) {
        this.productService = productService;
        this.actionService = actionService;
        this.robotService = robotService;
        this.employeeService = employeeService;
        this.manufacturingProcessService = manufacturingProcessService;
        this.manufacturingProcessFormUtil = manufacturingProcessFormUtil;
    }

    @GetMapping(value = "/")
    public String getProductsHome() {
        return "redirect:/products/all";
    }

    @GetMapping(value = {"/all"})
    public String getAllProducts(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/createForm")
    public String createProduct(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Product product = new Product();
        product.setManufacturingProcessList(List.of(new ManufacturingProcess()));
        product.setEmployee(employeeService.findByUsername(userDetails.getUsername())
                .get());
        ProductForm productForm = ProductFormUtil.wrap(product);
        populateModelWithThymeleafObjects(model, productForm);
        return "editProduct";
    }

    @GetMapping("/editForm")
    public String editProduct(Model model, @RequestParam Integer id) {
        Optional<Product> productOptional = productService.find(id);
        if (productOptional.isEmpty()) {
            return "redirect:/products/createForm";
        } else {
            Product foundedProduct = productOptional.get();
            if (foundedProduct.getManufacturingProcessList()
                    .isEmpty()) {
                foundedProduct.setManufacturingProcessList(List.of(new ManufacturingProcess()));
            }
            ProductForm wrapper = ProductFormUtil.wrap(productOptional.get());
            populateModelWithThymeleafObjects(model, wrapper);
            return "editProduct";
        }
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("productForm") @Valid ProductForm productForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            populateModelWithThymeleafObjects(model, productForm);
            return "editProduct";
        }
        try {
            Employee employee = employeeService.findByUsername(productForm.getUsername())
                    .orElseThrow(IllegalArgumentException::new);
            Product editedProduct = productService.saveOrUpdateProduct(productForm, employee);
            saveManufacturingProcesses(productForm, editedProduct);
        } catch (IllegalArgumentException exception) {
            return "redirect:/products/createForm";
        }
        return "redirect:/";
    }

    private void saveManufacturingProcesses(ProductForm productForm, Product editedProduct) {
        List<ManufacturingProcess> manufacturingProcessList = manufacturingProcessFormUtil.unwrap(productForm.getManufacturingProcessFormList());
        manufacturingProcessList = manufacturingProcessList.stream()
                .peek(manufacturingProcess -> manufacturingProcess.setProduct(editedProduct))
                .filter(manufacturingProcess -> manufacturingProcess.getProduct() != null && manufacturingProcess.getRobot() != null && manufacturingProcess.getAction() != null)
                .collect(Collectors.toList());
        manufacturingProcessService.saveAll(manufacturingProcessList);
    }

    private void populateModelWithThymeleafObjects(Model model, ProductForm productForm) {
        List<Action> allActions = actionService.findAll();
        List<Robot> allRobots = robotService.findAll();
        List<Employee> allEmployees = employeeService.findAll();
        model.addAttribute("actions", allActions);
        model.addAttribute("robots", allRobots);
        model.addAttribute("employees", allEmployees);
        model.addAttribute("productForm", productForm);
    }
}
