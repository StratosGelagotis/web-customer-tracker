package com.luv2code.springdemo.service;

import com.luv2code.springdemo.dao.CustomerDAO;
import com.luv2code.springdemo.entity.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    // need to inject customer dao
    @Autowired
    private CustomerDAO customerDAO;

    @Override
    @Transactional
    public List<Customer> getCustomers() {
        return this.customerDAO.getCustomers();
    }

    @Override
    @Transactional
    public void deleteCustomer(int id) {
        this.customerDAO.deleteCustomer(id);
    }

    @Override
    @Transactional
    public void saveCustomer(Customer theCustomer) {
        this.customerDAO.saveCustomer(theCustomer);
    }

    @Override
    @Transactional
    public Customer getCustomer(int theId) {
        return customerDAO.getCustomer(theId);
    }

    @Override
    @Transactional
    public List<Customer> searchCustomers(String theSearchName) {
        // search customers from the customerDAO
        // return results to the controller
        return this.customerDAO.searchCustomers(theSearchName);
    }
}
