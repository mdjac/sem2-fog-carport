package business.entities;

public class AllowedMinMax {
    private Integer min;
    private Integer max;

    public AllowedMinMax(Integer min, Integer max) {
        this.min = min;
        this.max = max;
    }


    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public boolean between(int i) {
        if (i >= this.min && i <= this.max)
            return true;
        else
            return false;
    }

    @Override
    public String toString() {
        return "(Min: " + min +" - Max: " + max+" Tilladt)";
    }
}
