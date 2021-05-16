package business.entities;

public class Material {
    private String categoryName;
    private String materialName;
    private int materialsId;
    private int materialsCategoryId;
    private int variantId;
    private int quantity;
    private Double width;
    private Double length;
    private Double height;
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


    @Override
    public String toString() {
        StringBuilder toBOM = new StringBuilder("");
        if(width != null || height != null){
            toBOM.append(getHeight()+"X"+getWidth()+" cm. ");
        }
        toBOM.append(getMaterialName());
        if(quantity != 1){
            toBOM.append(" "+getQuantity()+" stk");
        }
        return toBOM.toString();
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

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public double getPrice() {
        return price;
    }



}
