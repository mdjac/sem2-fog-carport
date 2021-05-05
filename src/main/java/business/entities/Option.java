package business.entities;

import java.util.TreeMap;

public class Option {
    private int id;
    private String name;
    static TreeMap<Integer,String> values;

    public Option(int id, String name) {
        this.id = id;
        this.name = name;
        this.values = new TreeMap<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public TreeMap<Integer, String> getValues() {
        return values;
    }

    public void addValue(int id, String value){
        values.put(id,value);
    }
    public void removeValue(int id){
        values.remove(id);
    }
}
