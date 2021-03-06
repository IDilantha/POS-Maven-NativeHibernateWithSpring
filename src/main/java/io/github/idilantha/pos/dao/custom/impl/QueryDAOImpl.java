package io.github.idilantha.pos.dao.custom.impl;


import io.github.idilantha.pos.dao.custom.QueryDAO;
import io.github.idilantha.pos.entity.CustomEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QueryDAOImpl implements QueryDAO {

    @Autowired
    protected SessionFactory sessionFactory;

    @Override
    public CustomEntity getOrderInfo(int orderId) throws Exception {
        return  getSession().createQuery("SELECT NEW entity.CustomEntity(O.id,C.customerId,C.name,O.date) FROM Customer C " +
                "INNER JOIN C.orders O WHERE O.id=?1",CustomEntity.class)
                .setParameter(1, orderId)
                .uniqueResult();
    }

    @Override
    public CustomEntity getOrderInfo2(int orderId) throws Exception {
        return (CustomEntity) getSession().createQuery("SELECT NEW entity.CustomEntity(O.id, C.customerId, C.name, O.date, SUM(OD.qty * OD.unitPrice)) " +
                " FROM Customer C " +
                "INNER JOIN C.orders O INNER JOIN O.orderDetails OD WHERE O.id=?1 GROUP BY O.id",CustomEntity.class).setParameter(1,orderId);
    }

    @Override
    public List<CustomEntity> getOrdersInfo() throws Exception {
        NativeQuery nativeQuery = getSession().createNativeQuery("SELECT O.id as orderId, C.customerId as customerId, C.name as customerName, O.date as orderDate, SUM(OD.qty * OD.unitPrice) AS orderTotal  FROM Customer C INNER JOIN `Order` O ON C.customerId=O.customerId INNER JOIN  OrderDetail OD on O.id = OD.orderId GROUP BY O.id ");
        Query<CustomEntity> query = nativeQuery.setResultTransformer(Transformers.aliasToBean(CustomEntity.class));
        List<CustomEntity> list = query.list();
        return list;
    }

    @Override
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

}
