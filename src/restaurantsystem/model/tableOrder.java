package restaurantsystem.model;

import java.util.ArrayList;

public class tableOrder {
    private int tableId;
    private ArrayList<Integer> orders;
    private String date;

    @Override
    public String toString() {
        return "tableOrder{" +
                "tableId=" + tableId +
                ", orders=" + orders +
                ", date='" + date + '\'' +
                '}';
    }

    public tableOrder() {
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public ArrayList<Integer> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Integer> orders) {
        this.orders = orders;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
