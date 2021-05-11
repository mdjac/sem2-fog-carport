package business.utilities;

import business.entities.Carport;
import business.entities.Order;
import business.entities.OrderLine;

import java.util.ArrayList;
import java.util.TreeMap;

public abstract class Calculator {


    //Assumptions:
    //Alle mål er i centimeter
    //Remme kan laves i uendelige længder
    //Max afstand mellem stolper er 310cm
    //Max carport bredde 750cm
    //Max carport længde 780cm
    //Afstanden mellem spær skal måles fra midten af spærrets bredde


    public static ArrayList<OrderLine> calculateBOM(Carport carport, Order order){
        //The string in the treemap has to be category fx. Carport bygge materialer, Tag materialer
        ArrayList<OrderLine> bomItems = new ArrayList<>();
        OrderLine orderLine;

        //calculate carport

        //calculate spær
        int spærAntal = calculateSpær(carport);
        bomItems.add(new OrderLine(spærAntal,order.getId(), "stk",19,"Spær, monteres på rem"));

        //Calculate redskabsrum


        //Calculate Tag

        return bomItems;
    }

    public static double calculateOptimalDistance(double minDist, double maxDist, double materialWidth, double totalDist){
        double result = 0;
        double bestRemainder = 1000;
        for (double i = minDist; i < maxDist; i += 0.1) {
            double remainder = (totalDist-(materialWidth*2))%i;
            if (remainder < bestRemainder){
                bestRemainder = remainder;
                result = i;
            }
        }
        System.out.println("Result : " +result);
        System.out.println("bestRemainder : " +bestRemainder);
        return result;
    }

    public static int calculateSpær(Carport carport){
        //Alle mål er i centimeter
        double spærMaxAfstandFladtTag = 60;
        double spærMinAfstandFladtTag = 50;
        double spærMaxAfstandTagMedRejsning = 100;
        double spærMinAfstandTagMedRejsning = 70;
        //Todo Hent spærbredde fra database
        double spærBredde = 4.5;

        boolean fladtTag;

        if (carport.getTagType() == "Fladt tag"){
            fladtTag = true;
        } else {
            fladtTag = false;
        }

        double carportLength = Double.parseDouble(carport.getCarportLængde());
        double spærMellemrum;

        if (fladtTag == true) {
            spærMellemrum = calculateOptimalDistance(spærMinAfstandFladtTag, spærMaxAfstandFladtTag, spærBredde, carportLength);

        } else {
            spærMellemrum = calculateOptimalDistance(spærMinAfstandTagMedRejsning, spærMaxAfstandTagMedRejsning, spærBredde, carportLength);
        }
        int spærAntal = (int) (carportLength/spærMellemrum);
        System.out.println("linje 76 "+ spærAntal);
        System.out.println(("bregning" + carportLength/spærMellemrum ));
        return spærAntal;
    }


}
