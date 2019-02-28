package com.luv2code.springdemo.controller;

import com.luv2code.springdemo.dao.CustomerDAO;
import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.jws.WebParam;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    // need to inject the customer service
    @Autowired
    private CustomerService customerService;

    @GetMapping("/list")
    public String listCustomers(Model theModel){
        // get the customers from the CustomerService
        List<Customer> theCustomers = customerService.getCustomers();
        // add the customers to the model
        theModel.addAttribute("customers",theCustomers);
        return "list-customers";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel){
        Customer theCustomer = new Customer();
        theModel.addAttribute("customer",theCustomer);
        return "customer-form";
    }
    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(
            @RequestParam("customerId")int theId, Model theModel){
        // get the customer from the service
        Customer customer = customerService.getCustomer(theId);
        // set customer as a model attribut to pre-populate the form
        theModel.addAttribute("customer",customer);
        // send to our form
        return "customer-form";
    }

    @PostMapping("/saveCustomer")
    public String saveCustomer(
            @ModelAttribute("customer") Customer theCustomer){
        // Save the customer using our service
        customerService.saveCustomer(theCustomer);
        return "redirect:/customer/list";
    }
}
