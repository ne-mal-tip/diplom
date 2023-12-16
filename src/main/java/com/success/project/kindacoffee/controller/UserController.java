package com.success.project.kindacoffee.controller;

import com.success.project.kindacoffee.entities.people.Employee;
import com.success.project.kindacoffee.services.people.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/persons")
public class UserController {

    private final EmployeeService employeeService;

    public UserController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping()
    public String getPersonsMain() {
        return "redirect:/persons/all";
    }

    @GetMapping("/all")
    public String showEmployeeList(Model model) {
        List<Employee> employees = employeeService.findAll();
        model.addAttribute("employees", employees);
        return "persons";
    }

}
