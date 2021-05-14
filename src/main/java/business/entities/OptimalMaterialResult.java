package business.entities;

public class OptimalMaterialResult {
    private int quantity;
    private Material material;

    public OptimalMaterialResult(int quantity, Material material) {
        this.quantity = quantity;
        this.material = material;
    }

    public int getQuantity() {
        return quantity;
    }

    public Material getMaterial() {
        return material;
    }
}
