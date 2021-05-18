package business.entities;

public class MinMax {
    private Double min;
    private Double max;
    private Double value;

    public MinMax(Double min, Double max) {
        this.min = min;
        this.max = max;
        if(min.equals(max)){
            setValue(max);
        }
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public boolean between(int i) {
        if (i >= this.min && i <= this.max)
            return true;
        else
            return false;
    }

    @Override
    public String toString() {
        return "(Min: " + min.intValue() +" - Max: " + max.intValue()+" Tilladt)";
    }
}
