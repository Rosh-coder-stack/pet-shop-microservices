package com.petshop.order.event;
import com.petshop.order.model.Order;
public class OrderStatusChangedEvent {
    private final Order order;

    public OrderStatusChangedEvent(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}
