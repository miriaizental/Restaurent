package restaurantsystem.service;

import restaurantsystem.model.Order;

import java.util.ArrayDeque;
import java.util.Deque;

public class OrdersQueueService {
    private static Deque<Order> ordersQueue;
    private static OrdersQueueService INSTANCE;

    public OrdersQueueService() {
        this.ordersQueue = new ArrayDeque<>();
    }

    public static OrdersQueueService getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new OrdersQueueService();
        }

        return INSTANCE;
    }

    public Order getOrder(){
        return ordersQueue.pollFirst();
    }

    public void addOrder(Order order){
        if(order != null )
            ordersQueue.addLast(order);
    }

    public void returnOrder(Order order){
        if(order != null )
            ordersQueue.addFirst(order);
    }

}
