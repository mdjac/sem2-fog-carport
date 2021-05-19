package business.entities;

import web.FrontController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.TreeMap;

public class Order {
    private int id;
    private Status status;
    private double totalPrice;
    private double costPrice;
    private Timestamp time;
    private int userId;
    private Carport carport;
    private TreeMap<Integer, OrderLine> BOM;
    private double avance;

    public Order(Status status, int userId, Carport carport) {
        this.status = status;
        this.userId = userId;
        this.carport = carport;
        this.BOM = new TreeMap<>();
        this.avance = FrontController.priceCalculatorValues.get("ordreAvance").getValue();
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

    public TreeMap<Integer, OrderLine> getBOM() {
        return BOM;
    }

    public void setBOM(TreeMap<Integer, OrderLine> BOM) {
        this.BOM = BOM;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void calculateCostPriceByArrayList(ArrayList<OrderLine> orderLines){
        for (OrderLine tmp: orderLines){
            costPrice = costPrice + tmp.getAccumulatedPrice();
        }
        calculateTotalPrice(avance);
    }

    public void calculateCostPriceByTreeMap(TreeMap<Integer, OrderLine> orderLines){
        for (OrderLine tmp: orderLines.values()){
            costPrice = costPrice + tmp.getAccumulatedPrice();
        }
        calculateTotalPrice(avance);
    }


    public void calculateTotalPrice(double ordreAvance){
        this.avance = ordreAvance;
        this.totalPrice = costPrice * (100+avance)/100;
    }

    public void calculateAvance(){
        //(Salgspris - indkøbspris) / indkøbspris * 100
        this.avance = (totalPrice-costPrice)/costPrice*100;
    }

    public double getAvance() {
        return Math.round(avance * 100.0) / 100.0;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
        calculateAvance();
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

