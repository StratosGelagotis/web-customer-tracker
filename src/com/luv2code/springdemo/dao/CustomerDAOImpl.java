package com.luv2code.springdemo.dao;

import com.luv2code.springdemo.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CustomerDAOImpl implements CustomerDAO {
    // inject the session factory
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Customer> getCustomers() {
        // get the current hibernate session
        Session currentSession = this.sessionFactory.getCurrentSession();
        // create a query
        Query<Customer> theQuery =
                currentSession.createQuery("from Customer order by lastName",
                        Customer.class);
        // execute query and get result list
        List<Customer> customers = theQuery.getResultList();
        // return the results
        return customers;
    }

    @Override
    public void saveCustomer(Customer theCustomer) {
        // get current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();
        //save the customer
        currentSession.save(theCustomer);
    }

    @Override
    public Customer getCustomer(int theId) {
        // get current session
        Session currentSession = sessionFactory.getCurrentSession();
        // Retrieve the customer from database using id(Primary key)
        Customer customer = currentSession.get(Customer.class,theId);
        return customer;
    }
}
