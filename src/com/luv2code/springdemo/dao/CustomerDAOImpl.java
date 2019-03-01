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
        // execute query, get result list and return it
        return theQuery.getResultList();
    }

    @Override
    public void saveCustomer(Customer theCustomer) {
        // get current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();
        // Save or Update the customer
        currentSession.saveOrUpdate(theCustomer);
    }

    @Override
    public Customer getCustomer(int theId) {
        // get current session
        Session currentSession = sessionFactory.getCurrentSession();
        // Retrieve the customer from database using id(Primary key)
        return currentSession.get(Customer.class,theId);
    }

    @Override
    public void deleteCustomer(int id) {
        // get current session
        Session currentSession = sessionFactory.getCurrentSession();
        // Delete customer from database using id( Primary Key )
        Query query =
                currentSession.createQuery("delete from Customer where id=:customerId");
        query.setParameter("customerId",id);
        query.executeUpdate();
    }

    @Override
    public List<Customer> searchCustomers(String theSearchName) {
        // get current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();
        Query query;
        // only search by name if theSearchName is not empty
        if(theSearchName!=null && theSearchName.trim().length()>0){
            //search for first name and last name and case insensitive
            query=currentSession.createQuery(
                    "from Customer where lower(firstName) like:theName or lower(lastName) like :theName",
                    Customer.class);
            query.setParameter("theName","%"+ theSearchName.toLowerCase()+"%");
        }else{
            // theSearchName is empty so we get all customers
            query=currentSession.createQuery("from Customer ",Customer.class);
        }
        return query.getResultList();
    }
}
