package business.utilities;

import business.entities.Carport;
import business.entities.OrderLine;

import java.util.ArrayList;
import java.util.TreeMap;

public abstract class Calculator {


    //Assumptions:
    //Alle mål er i centimeter

    public void calculateBOM(Carport carport){
        //The string in the treemap has to be category fx. Carport bygge materialer, Tag materialer
        TreeMap<String, ArrayList<OrderLine>> bom = new TreeMap<>();
        OrderLine orderLine;

        //calculate carport


        //Calculate redskabsrum


        //Calculate Tag
    }

    public static double[] calculateOptimalDistance(double minDist, double maxDist, double materialWidth, double totalDist){
        double result = 0;
        double bestRemainder = 1000;
        for (double i = minDist; i < maxDist; i += 0.5) {
            double remainder = (totalDist-(materialWidth*2))%i;
            if (remainder < bestRemainder){
                bestRemainder = remainder;
                result = i;
            }
        }

        return new double[]{result,bestRemainder};
    }

    public static void calculateSpær(Carport carport){
        //Alle mål er i centimeter
        double spærMaxAfstandFladtTag = 60;
        double spærMinAfstandFladtTag = 50;
        double spærMaxAfstandTagMedRejsning = 100;
        double spærMinAfstandTagMedRejsning = 70;
        double spærBredde = 4.5;
        boolean fladtTag;

        if (carport.getTagType() == "Fladt tag"){
            fladtTag = true;
        } else {
            fladtTag = false;
        }

        double carportLength = Double.parseDouble(carport.getCarportLængde());
        double result[];
        double optimalDistanceBetween = 0;
        if (fladtTag == true) {
            result = calculateOptimalDistance(spærMinAfstandFladtTag, spærMaxAfstandFladtTag, spærBredde, carportLength);
            optimalDistanceBetween = result[0];
            //Check if remainder is within or limits
            if (result[1] > 2) {

            }
        } else {
            result = calculateOptimalDistance(spærMinAfstandTagMedRejsning, spærMaxAfstandTagMedRejsning, spærBredde, carportLength);
            optimalDistanceBetween = result[0];
            //Check if remainder is within or limits
            if (result[1] > 2) {

            }
        }
    }
}
