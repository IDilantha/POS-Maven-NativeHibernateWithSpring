package io.github.idilantha.pos.business.custom.impl;

import io.github.idilantha.pos.business.custom.CustomerBO;
import io.github.idilantha.pos.dao.custom.CustomerDAO;
import io.github.idilantha.pos.dao.custom.OrderDAO;
import io.github.idilantha.pos.dto.CustomerDTO;
import io.github.idilantha.pos.entity.Customer;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Component
public class CustomerBOImpl implements CustomerBO {

    @Autowired
    private CustomerDAO customerDAO;
    @Autowired
    private OrderDAO orderDAO;

    @Override
    public void saveCustomer(CustomerDTO customer) throws Exception {
        customerDAO.save(new Customer(customer.getId(), customer.getName(), customer.getAddress()));

    }

    @Override
    public void updateCustomer(CustomerDTO customer) throws Exception {
        customerDAO.update(new Customer(customer.getId(), customer.getName(), customer.getAddress()));
    }

    @Override
    public void deleteCustomer(String customerId) throws Exception {
        if (orderDAO.existsByCustomerId(customerId)) {
            new Alert(Alert.AlertType.ERROR, "Customer Already exist in an order , Unable to Delete");
            return;
        }
        customerDAO.delete(customerId);
    }

    @Override
    public List<CustomerDTO> findAllCustomers() throws Exception {
        List<Customer> alCustomers = customerDAO.findAll();
        List<CustomerDTO> dtos = new ArrayList<>();
        for (Customer customer : alCustomers) {
            dtos.add(new CustomerDTO(customer.getCustomerId(), customer.getName(), customer.getAddress()));
        }
        return dtos;
    }

    @Override
    public String getLastCustomerId() throws Exception {
        String lastCustomerId = customerDAO.getLastCustomerId();
        return lastCustomerId;
    }

    @Override
    public CustomerDTO findCustomer(String customerId) throws Exception {
        Customer customer = customerDAO.find(customerId);
        return new CustomerDTO(customer.getCustomerId(), customer.getName(), customer.getAddress());

    }

    @Override
    public List<String> getAllCustomerIDs() throws Exception {
        List<Customer> customers = customerDAO.findAll();
        List<String> ids = new ArrayList<>();
        for (Customer customer : customers) {
            ids.add(customer.getCustomerId());
        }

        return ids;

    }
}
