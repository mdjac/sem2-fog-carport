package business.utilities;

import business.entities.Carport;
import business.entities.Order;
import business.entities.OrderLine;

import java.util.ArrayList;

public abstract class Calculator {


    //Assumptions:
    //Alle mål er i centimeter
    //Remme kan laves i uendelige længder
    //Max afstand mellem stolper er 310cm
    //Max carport bredde 750cm
    //Max carport længde 780cm
    //Afstanden mellem spær skal måles fra midten af spærrets bredde
    //Afstanden mellem stolpe skal måles fra midten af stolpens bredde
    //Afstand fra Front til første stolpe er 100cm
    //Afstand fra bagerste stolpe fra bag er 20cm


    public static ArrayList<OrderLine> calculateBOM(Carport carport, Order order){
        //The string in the treemap has to be category fx. Carport bygge materialer, Tag materialer
        ArrayList<OrderLine> bomItems = new ArrayList<>();

        //calculate carport


        //TODO hen materialer ind og find den længde som er tættest på den ønskede længde
        //fx længde på 600 lager har vi 580 så prøv at tjekke på 600/2 og hvis vi har noget der matcher og er mindre end 580 så vælg det.
        //calculate spær
        int spærAntal = calculateSpær(carport);
        bomItems.add(new OrderLine(spærAntal,order.getId(), "stk",19,"Spær, monteres på rem"));

        //Calculate stolper
        int stolpeAntal = calculateStolper(carport);
        bomItems.add(new OrderLine(stolpeAntal, order.getId(), "stk", 20, "Stolper nedgraves 90 cm. i jord"));

        //Calculate remme
        bomItems.add(new OrderLine(2, order.getId(), "stk", 19, "Remme i sider, sadles ned i stolper"));
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

        if (carport.getRoofType() == "Fladt tag"){
            fladtTag = true;
        } else {
            fladtTag = false;
        }

        double carportLength = carport.getCarportLængde();
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

    public static int calculateStolper(Carport carport) {
        int stolpeAntal = 0;

        int forresteStolpeAfstandFraFront = 100;
        int bagersteStolpeAfstandFraBag = 20;
        int maxAfstandMellemStolper = 310;
        double stolpeBredde = 10;

        int carportLængde = carport.getCarportLængde();

        int afstand = carportLængde-forresteStolpeAfstandFraFront-bagersteStolpeAfstandFraBag;

        double result = calculateOptimalDistance(100, maxAfstandMellemStolper, stolpeBredde, afstand);
        System.out.println("Linje 102 " + result);
        stolpeAntal = (int) ((afstand - (stolpeBredde*2))/result);
        //+1 fordi vi har regnet antal afstande ud og derfor lige skal plus 1 for at få antal stolper
        stolpeAntal += 1;
        //Vi ganger med 2 for at få total antal stolper fra begge sider
        stolpeAntal = stolpeAntal*2;
        System.out.println("stolpe antal: "+stolpeAntal);
        return  stolpeAntal;
    }

}
