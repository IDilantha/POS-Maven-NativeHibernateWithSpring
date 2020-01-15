package io.github.idilantha.pos.dao.custom;


import io.github.idilantha.pos.dao.CrudDAO;
import io.github.idilantha.pos.entity.Order;

public interface OrderDAO extends CrudDAO<Order, Integer> {

    int getLastOrderId() throws Exception;

    boolean existsByCustomerId(String customerId) throws Exception;

}
