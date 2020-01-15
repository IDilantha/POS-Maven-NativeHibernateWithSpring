package io.github.idilantha.pos.business.custom.impl;

import io.github.idilantha.pos.business.custom.CustomerBO;
import io.github.idilantha.pos.dao.custom.CustomerDAO;
import io.github.idilantha.pos.dao.custom.OrderDAO;
import io.github.idilantha.pos.db.HibernateUtil;
import io.github.idilantha.pos.dto.CustomerDTO;
import io.github.idilantha.pos.entity.Customer;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerBOImpl implements CustomerBO {

    @Autowired
    private CustomerDAO customerDAO;
    @Autowired
    private OrderDAO orderDAO;

    @Override
    public void saveCustomer(CustomerDTO customer) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            customerDAO.setSession(session);
            session.beginTransaction();
            customerDAO.save(new Customer(customer.getId(), customer.getName(), customer.getAddress()));
            session.getTransaction().commit();
        }

    }

    @Override
    public void updateCustomer(CustomerDTO customer) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            customerDAO.setSession(session);
            session.beginTransaction();
            customerDAO.update(new Customer(customer.getId(), customer.getName(), customer.getAddress()));
            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteCustomer(String customerId) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            customerDAO.setSession(session);
            orderDAO.setSession(session);
            session.beginTransaction();

            if (orderDAO.existsByCustomerId(customerId)) {
                new Alert(Alert.AlertType.ERROR,"Customer Already exist in an order , Unable to Delete");
                return;
            }
            customerDAO.delete(customerId);
            session.getTransaction().commit();
        }

    }

    @Override
    public List<CustomerDTO> findAllCustomers() throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            customerDAO.setSession(session);
            session.beginTransaction();

            List<Customer> alCustomers = customerDAO.findAll();
            List<CustomerDTO> dtos = new ArrayList<>();
            for (Customer customer : alCustomers) {
                dtos.add(new CustomerDTO(customer.getCustomerId(), customer.getName(), customer.getAddress()));
            }
            session.getTransaction().commit();
            return dtos;
        }
    }

    @Override
    public String getLastCustomerId() throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            customerDAO.setSession(session);
            session.beginTransaction();

            String lastCustomerId = customerDAO.getLastCustomerId();
            session.getTransaction().commit();
            return lastCustomerId;
        }
    }

    @Override
    public CustomerDTO findCustomer(String customerId) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            customerDAO.setSession(session);
            session.beginTransaction();

            Customer customer = customerDAO.find(customerId);
            session.getTransaction().commit();
            return new CustomerDTO(customer.getCustomerId(), customer.getName(), customer.getAddress());
        }
    }

    @Override
    public List<String> getAllCustomerIDs() throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            customerDAO.setSession(session);
            session.beginTransaction();

            List<Customer> customers = customerDAO.findAll();
            List<String> ids = new ArrayList<>();
            for (Customer customer : customers) {
                ids.add(customer.getCustomerId());
            }
            session.getTransaction().commit();
            return ids;
        }
    }
}
