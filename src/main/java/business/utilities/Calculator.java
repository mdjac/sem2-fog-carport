package business.utilities;

import business.entities.Carport;
import business.entities.OrderLine;

import java.util.ArrayList;
import java.util.TreeMap;

public abstract class Calculator {


    //Assumptions:

    public void calculateBOM(Carport carport){
        //The string in the treemap has to be category fx. Carport bygge materialer, Tag materialer
        TreeMap<String, ArrayList<OrderLine>> bom = new TreeMap<>();
        OrderLine orderLine;
        //calculate carport
        

        //Calculate redskabsrum


        //Calculate Tag
    }
}
