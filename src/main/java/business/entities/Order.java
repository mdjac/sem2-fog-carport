package business.entities;

import java.sql.Timestamp;
import java.util.TreeMap;

public class Order {
    private int id;
    private Status status;
    private double totalPrice;
    private Timestamp time;
    private int userId;
    private Carport carport;

    public Order(Status status, double totalPrice, int userId, Carport carport) {
        this.status = status;
        this.totalPrice = totalPrice;
        this.userId = userId;
        this.carport = carport;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }


    public int getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Timestamp getTime() {
        return time;
    }

    public int getUserId() {
        return userId;
    }

    public Carport getCarport() {
        return carport;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", status=" + status +
                ", totalPrice=" + totalPrice +
                ", time=" + time +
                ", userId=" + userId +
                ", carport=" + carport +
                '}';
    }
}

