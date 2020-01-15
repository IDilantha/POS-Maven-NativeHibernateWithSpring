package io.github.idilantha.pos.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer implements SuperEntity{

    @Id
    private String customerId;
    private String name;
    private String address;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

    public Customer() {
    }

    public Customer(String customerId, String name, String address) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Order> getOrders() {
        return orders;
    }

    //Handler Method
    public void addOrders(Order orders) {
        orders.setCustomer(this);
        this.orders.add(orders);
    }

    public void removeOrder(Order order){
        if (order.getCustomer()!= this){
            throw new RuntimeException("Invalid Order");
        }else {
            order.setCustomer(null);
            this.getOrders().remove(order);
        }
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address+
                '}';
    }
}
