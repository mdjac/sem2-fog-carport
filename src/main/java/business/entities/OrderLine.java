package business.entities;

public class OrderLine {
    private int id;
    private int quantity;
    private int ordersID;
    private String unit;
    private Material material;
    private String description;
    private double accumulatedPrice;

    public OrderLine(int quantity, int ordersID, String unit, Material material, String description) {
        this.quantity = quantity;
        this.ordersID = ordersID;
        this.unit = unit;
        this.material = material;
        this.description = description;
        this.accumulatedPrice = calculateAccumulatedPrice();
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getOrdersID() {
        return ordersID;
    }

    public String getUnit() {
        return unit;
    }

    public Material getMaterial() {
        return material;
    }

    public String getDescription() {
        return description;
    }

    private double calculateAccumulatedPrice(){
        return material.getPrice()*getQuantity();
    }

    public double getAccumulatedPrice() {
        return accumulatedPrice;
    }

    @Override
    public String toString() {
        return "OrderLine{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", ordersID=" + ordersID +
                ", unit='" + unit + '\'' +
                ", material=" + material +
                ", description='" + description + '\'' +
                '}';
    }
}
