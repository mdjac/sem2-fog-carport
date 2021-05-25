package business.entities;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OptimalMaterialResult that = (OptimalMaterialResult) o;
        return quantity == that.quantity && amountCovered == that.amountCovered && material.equals(that.material);
    }


    @Override
    public String toString() {
        return "OptimalMaterialResult{" +
                "quantity=" + quantity +
                ", material=" + material +
                ", amountCovered=" + amountCovered +
                '}';
    }
}
