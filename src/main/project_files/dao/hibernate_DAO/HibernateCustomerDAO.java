package main.project_files.dao.hibernate_DAO;

import main.project_files.dao.CustomerDAO;
import main.project_files.models.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class HibernateCustomerDAO implements CustomerDAO {

    private SessionFactory sessionFactory;


    public HibernateCustomerDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Long save(Customer customer) {
        Long id = null;
        try (Session session = sessionFactory.openSession()) {
            id = (Long) session.save(customer);
        } catch (Exception e) {
            System.out.println("Exception while trying to save your customer " + customer);
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public Customer findByID(Long id) {
        Customer customer = new Customer(id, "");
        try (Session session = sessionFactory.openSession()) {
            Customer customerDB = session.get(Customer.class, id);
            if (customerDB != null) {
                customer = customerDB;
            }
        } catch (Exception e) {
            System.out.println("Exception while trying to find your customer with id " + id);
            e.printStackTrace();
        }
        return customer;
    }

    @Override
    public void update(Customer customer) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Customer customerDB = session.get(Customer.class, customer.getId());
            if (customerDB == null) {
                return;
            }
            customerDB.setTitle(customer.getTitle());
            session.update(customerDB);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Exception while trying to update your customer " + customer);
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void delete(Customer customer) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Customer customerDB = session.get(Customer.class, customer.getId());
            if (customerDB == null) {
                return;
            }
            session.delete(customerDB);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Exception while trying to delete customer " + customer);
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @SuppressWarnings("Unchecked")
    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            customers = session.createQuery("from Customers").list();
        } catch (Exception e) {
            System.out.println("Exception while trying to find all your customers");
            e.printStackTrace();
        }
        return customers;
    }

    @SuppressWarnings("Unchecked")
    @Override
    public Customer findByName(String title) {
        Customer customer = new Customer(0, title);
        try (Session session = sessionFactory.openSession()) {
            List<Customer> companies = session.createQuery("select c from Customer c where c.title like :=title")
                    .setParameter("title", title).list();
            if (companies.size() != 0) {
                customer = companies.get(0);
            }
        } catch (Exception e) {
            System.out.println("Exception while trying to find your customer with title " + title);
        }
        return customer;
    }
}

