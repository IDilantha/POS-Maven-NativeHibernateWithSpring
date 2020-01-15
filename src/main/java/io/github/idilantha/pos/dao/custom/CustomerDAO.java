package io.github.idilantha.pos.dao.custom;


import io.github.idilantha.pos.dao.CrudDAO;
import io.github.idilantha.pos.entity.Customer;

public interface CustomerDAO extends CrudDAO<Customer, String> {

    String getLastCustomerId() throws Exception;

}
