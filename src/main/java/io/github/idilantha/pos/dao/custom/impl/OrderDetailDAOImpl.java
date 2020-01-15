package io.github.idilantha.pos.dao.custom.impl;


import io.github.idilantha.pos.dao.CrudDAOImpl;
import io.github.idilantha.pos.dao.custom.OrderDetailDAO;
import io.github.idilantha.pos.entity.OrderDetail;
import io.github.idilantha.pos.entity.OrderDetailPK;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Component;

@Component
public class OrderDetailDAOImpl extends CrudDAOImpl<OrderDetail,OrderDetailPK> implements OrderDetailDAO {

    @Override
    public boolean existsByItemCode(String itemCode) throws Exception {
        NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM OrderDetail WHERE itemCode=?");
        nativeQuery.setParameter(1,itemCode);
        return nativeQuery.uniqueResult() != null ;
    }
}
