package io.github.idilantha.pos.dao.custom.impl;

import io.github.idilantha.pos.dao.CrudDAOImpl;
import io.github.idilantha.pos.dao.custom.OrderDAO;
import io.github.idilantha.pos.entity.Order;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Component;

@Component
public class OrderDAOImpl extends CrudDAOImpl<Order,Integer> implements OrderDAO {

    @Override
    public int getLastOrderId() throws Exception {
        Object o = getSession().createNativeQuery("SELECT id FROM `Order` ORDER BY id DESC LIMIT 1").uniqueResult();
        return ( o == null ) ? 0 : (int)o;
    }

    @Override
    public boolean existsByCustomerId(String customerId) throws Exception {
        NativeQuery nativeQuery = getSession().createNativeQuery("SELECT * FROM `Order` WHERE customerId=?");
        nativeQuery.setParameter(1,customerId);
        return nativeQuery.uniqueResult() != null;
    }

}
