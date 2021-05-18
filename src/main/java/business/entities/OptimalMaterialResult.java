package business.entities;

public class OptimalMaterialResult {
    private int quantity;
    private Material material;
    private int amountCovered;

    public OptimalMaterialResult(int quantity, Material material, int amountCovered) {
        this.quantity = quantity;
        this.material = material;
        this.amountCovered = amountCovered;
    }

    public int getQuantity() {
        return quantity;
    }

    public Material getMaterial() {
        return material;
    }

    public int getAmountCovered() {
        return amountCovered;
    }
}
