package business.entities;

import web.FrontController;

public class OrderLine {
    private int id;
    private String materialName;
    private int quantity;
    private int ordersID;
    private String unit;
    private int materialsVariantId;
    private String description;

    public OrderLine(int quantity, int ordersID, String unit, int materialsVariantId, String description) {
        this.quantity = quantity;
        this.ordersID = ordersID;
        this.unit = unit;
        this.materialsVariantId = materialsVariantId;
        this.description = description;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialName() {
        return materialName;
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

    public int getMaterialsVariantId() {
        return materialsVariantId;
    }

    public String getDescription() {
        return description;
    }

    //todo get material name from database or treemap
    @Override
    public String toString() {
        return "name: " + materialName +"description: " + description +", quantity=" + quantity + ", unit='" + unit + '\'';
    }
}
