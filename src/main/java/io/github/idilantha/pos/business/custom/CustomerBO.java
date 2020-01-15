package io.github.idilantha.pos.business.custom;

import io.github.idilantha.pos.business.SuperBO;
import io.github.idilantha.pos.dto.CustomerDTO;

import java.util.List;

public interface CustomerBO extends SuperBO {

    void saveCustomer(CustomerDTO customer)throws Exception;

    void updateCustomer(CustomerDTO customer)throws Exception;

    void deleteCustomer(String customerId) throws Exception;

    List<CustomerDTO> findAllCustomers() throws Exception;

    String getLastCustomerId()throws Exception;

    CustomerDTO findCustomer(String customerId) throws Exception;

    List<String> getAllCustomerIDs() throws Exception;

}
