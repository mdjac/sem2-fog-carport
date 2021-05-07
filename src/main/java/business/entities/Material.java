package business.entities;

public class Material {
    private String categoryName;
    private String materialName;
    private int materialsId;
    private int materialsCategoryId;
    private int variantId;
    private int quantity;
    private double depth;
    private double length;
    private double height;
    private double price;

    public Material(String categoryName, String materialName, int materialsId, int materialsCategoryId, int variantId, int quantity, double price) {
        this.categoryName = categoryName;
        this.materialName = materialName;
        this.materialsId = materialsId;
        this.materialsCategoryId = materialsCategoryId;
        this.variantId = variantId;
        this.quantity = quantity;
        this.price = price;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getMaterialName() {
        return materialName;
    }

    public int getMaterialsId() {
        return materialsId;
    }

    public int getMaterialsCategoryId() {
        return materialsCategoryId;
    }

    public int getVariantId() {
        return variantId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getDepth() {
        return depth;
    }

    public double getLength() {
        return length;
    }

    public double getHeight() {
        return height;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Material{" +
                "categoryName='" + categoryName + '\'' +
                ", materialName='" + materialName + '\'' +
                ", materialsId=" + materialsId +
                ", materialsCategoryId=" + materialsCategoryId +
                ", variantId=" + variantId +
                ", quantity=" + quantity +
                ", depth=" + depth +
                ", length=" + length +
                ", height=" + height +
                ", price=" + price +
                '}';
    }
}
