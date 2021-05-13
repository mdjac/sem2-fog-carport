package business.entities;

public class AllowedMinMax {
    private int min;
    private int max;


    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public AllowedMinMax(int min, int max) {
        this.min = min;
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
