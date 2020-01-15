package io.github.idilantha.pos.dao.custom;

import io.github.idilantha.pos.dao.CrudDAO;
import io.github.idilantha.pos.entity.OrderDetail;
import io.github.idilantha.pos.entity.OrderDetailPK;

public interface OrderDetailDAO extends CrudDAO<OrderDetail, OrderDetailPK> {

    boolean existsByItemCode(String itemCode) throws Exception;

}
