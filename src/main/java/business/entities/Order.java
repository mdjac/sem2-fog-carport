package business.entities;

import java.sql.Timestamp;
import java.util.TreeMap;

public class Order {
    private int id;
    private Status status;
    private double totalPrice;
    private Timestamp time;
    private int userId;
    private TreeMap<Integer,Integer> options;

}
