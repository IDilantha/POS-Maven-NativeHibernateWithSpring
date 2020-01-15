package io.github.idilantha.pos.business.custom.impl;

import io.github.idilantha.pos.business.custom.OrderBO;
import io.github.idilantha.pos.dao.custom.*;
import io.github.idilantha.pos.db.HibernateUtil;
import io.github.idilantha.pos.dto.OrderDTO;
import io.github.idilantha.pos.dto.OrderDTO2;
import io.github.idilantha.pos.dto.OrderDetailDTO;
import io.github.idilantha.pos.entity.CustomEntity;
import io.github.idilantha.pos.entity.Item;
import io.github.idilantha.pos.entity.Order;
import io.github.idilantha.pos.entity.OrderDetail;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class OrderBOImpl implements OrderBO {

    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private OrderDetailDAO orderDetailDAO;
    @Autowired
    private ItemDAO itemDAO ;
    @Autowired
    private QueryDAO queryDAO;
    @Autowired
    private CustomerDAO customerDAO;


    @Override
    public int getLastOrderId() throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            orderDAO.setSession(session);
            session.beginTransaction();

            int lastOrderId = orderDAO.getLastOrderId();
            session.getTransaction().commit();
            return lastOrderId;
        }
    }

    @Override
    public void placeOrder(OrderDTO order) throws Exception {
       try (Session session = HibernateUtil.getSessionFactory().openSession()){
           orderDAO.setSession(session);
           orderDetailDAO.setSession(session);
           customerDAO.setSession(session);
           itemDAO.setSession(session);
           session.beginTransaction();

           int oId = order.getId();
           orderDAO.save(new Order(oId, new java.sql.Date(new Date().getTime()), customerDAO.find(order.getCustomerId())));

           for (OrderDetailDTO orderDetail : order.getOrderDetails()) {
               orderDetailDAO.save(new OrderDetail(oId, orderDetail.getCode(), orderDetail.getQty(), orderDetail.getUnitPrice()));

               Item item = itemDAO.find(orderDetail.getCode());
               item.setQtyOnHand(item.getQtyOnHand() - orderDetail.getQty());
               itemDAO.update(item);
           }
           session.getTransaction().commit();
       }
    }

    @Override
    public List<OrderDTO2> getOrderInfo() throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            queryDAO.setSession(session);
            session.beginTransaction();

            List<CustomEntity> ordersInfo = queryDAO.getOrdersInfo();
            List<OrderDTO2> dtos = new ArrayList<>();
            for (CustomEntity info : ordersInfo) {
                dtos.add(new OrderDTO2(info.getOrderId(),info.getOrderDate(),info.getCustomerId(),info.getCustomerName(),info.getOrderTotal()));
            }
            session.getTransaction().commit();
            return dtos;
        }
    }
}
