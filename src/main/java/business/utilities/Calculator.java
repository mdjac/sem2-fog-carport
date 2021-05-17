package business.utilities;

import business.entities.*;
import web.FrontController;

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
    //Afstanden mellem stolpe skal måles fra midten af stolpens bredde
    //Afstand fra Front til første stolpe er 100cm
    //Afstand fra bagerste stolpe fra bag er 20cm
    //lige mange overstern og undesterns brædder per carportside
    //oversterns brædder kun på forsiden
    //lige mange undesterns brædder per front og bagside
    //Vi antager at hvis man vælger beklædning til carporten, så er det ikke side beklædninge, men materialetype til over og understernsbrædder
    //Vi antager at skuret er det eneste på carporten som får beklædning på siderne.



    public static ArrayList<OrderLine> calculateBOM(Carport carport, Order order){
        //The string in the treemap has to be category fx. Carport bygge materialer, Tag materialer
        ArrayList<OrderLine> bomItems = new ArrayList<>();
        OptimalMaterialResult optimalMaterialResult;

        //calculate carport

            //calculate spær
            int spærAntal = calculateSpær(carport);
            optimalMaterialResult = getOptimalMaterial(9, carport.getCarportWidth(), getRequiredWidthByCategory("spær"), 5,false);
            bomItems.add(new OrderLine(spærAntal,order.getId(), "stk",optimalMaterialResult.getMaterial(),"Spær, monteres på rem"));

            //Calculate stolper inklusiv redskabsskur
            int stolpeAntal = calculateStolper(carport);
            optimalMaterialResult = getOptimalMaterial(10, (carport.getCarportHeight()+90), getRequiredWidthByCategory("stolper"), 5, false);
            bomItems.add(new OrderLine(stolpeAntal, order.getId(), "stk", optimalMaterialResult.getMaterial(), "Stolper nedgraves 90 cm. i jord"));

            //Calculate remme
            //TODO If time permits e can change materialSplit to be allowed. This has to be in sync with stolpe afstanden
            optimalMaterialResult = getOptimalMaterial(9,carport.getCarportLength(), getRequiredWidthByCategory("remme"), 5, false);
            bomItems.add(new OrderLine(2, order.getId(), "stk", optimalMaterialResult.getMaterial(), "Remme i sider, sadles ned i stolper"));


        //Calculate redskabsrum
            //Redskabsskur beklædning


        //Calculate Tag
            //Under Sternsbrædder side * 2 for vi skal have til begge sider
            optimalMaterialResult = getOptimalMaterial(carport.getCarportMaterial().getMaterialsId(), carport.getCarportLength(), getRequiredWidthByCategory("understernsbrædder"),5, true);
            bomItems.add(new OrderLine(optimalMaterialResult.getQuantity()*2, order.getId(), "stk", optimalMaterialResult.getMaterial(), "understernbrædder til siderne"));

            //Under Sternsbrædder for og bagende * 2 for vi skal have til begge sider
            optimalMaterialResult = getOptimalMaterial(carport.getCarportMaterial().getMaterialsId(), carport.getCarportWidth(),getRequiredWidthByCategory("understernsbrædder"), 5, true);
            bomItems.add(new OrderLine(optimalMaterialResult.getQuantity()*2, order.getId(), "stk", optimalMaterialResult.getMaterial(), "understernbrædder til for & bag ende"));

            //over Sternsbrædder side * 2 for vi skal have til begge sider
            optimalMaterialResult = getOptimalMaterial(carport.getCarportMaterial().getMaterialsId(), carport.getCarportLength(),getRequiredWidthByCategory("oversternsbrædder"), 5, true);
            bomItems.add(new OrderLine(optimalMaterialResult.getQuantity()*2, order.getId(), "stk", optimalMaterialResult.getMaterial(), "oversternbrædder til siderne"));

            //over Sternsbrædder forende
            optimalMaterialResult = getOptimalMaterial(carport.getCarportMaterial().getMaterialsId(), carport.getCarportWidth(),getRequiredWidthByCategory("oversternsbrædder"), 5, true);
            bomItems.add(new OrderLine(optimalMaterialResult.getQuantity(), order.getId(), "stk", optimalMaterialResult.getMaterial(), "oversternbrædder til forenden"));

            //vandbrædt på stern i forende
            optimalMaterialResult = getOptimalMaterial(carport.getCarportMaterial().getMaterialsId(), carport.getCarportWidth(),getRequiredWidthByCategory("vandbrædt"), 5, true);
            bomItems.add(new OrderLine(optimalMaterialResult.getQuantity(), order.getId(), "stk", optimalMaterialResult.getMaterial(), "vandbrædt på stern i forende"));

            //vandbrædt på stern i sider * 2 for vi skal have til begge sider
            optimalMaterialResult = getOptimalMaterial(carport.getCarportMaterial().getMaterialsId(), carport.getCarportLength(),getRequiredWidthByCategory("vandbrædt"), 5, true);
            bomItems.add(new OrderLine(optimalMaterialResult.getQuantity()*2, order.getId(), "stk", optimalMaterialResult.getMaterial(), "vandbrædt på stern i sider"));

            //Tagmateriale antal
            optimalMaterialResult = getOptimalRoofUnits(carport.getRoofMaterial().getMaterialsId(), carport.getCarportLength(),carport.getCarportWidth(),RoofType.fromString(carport.getRoofType()));
            bomItems.add(new OrderLine(optimalMaterialResult.getQuantity(), order.getId(), "stk", optimalMaterialResult.getMaterial(), "tagmateriale monteres på spær"));


        //SKRUER OG BESLAG
        Material material;
            //Hulbånd
            material = getMaterialByMaterialVariantId(8);
            bomItems.add(new OrderLine(2,order.getId(), "rulle(r)", material,"Til vindkryds på spær"));

            //Plastmo bundskruer
            if (carport.getRoofMaterial().getMaterialsId() == 3) {
                material = getMaterialByMaterialVariantId(7);
                int quantity = 1;
                //Vi ved at seneste optimalMaterialResult er tagmateriale og vi antager at der går én pakke skruer per 4 tagplader
                quantity =  (int) Math.ceil(optimalMaterialResult.getQuantity()/4);
                bomItems.add(new OrderLine(quantity,order.getId(), "pakke(r)", material,"Skruer til tagplader"));
            }

            //universal 190 mm højre
            material = getMaterialByMaterialVariantId(38);
            bomItems.add(new OrderLine(spærAntal,order.getId(), "stk", material,"Til montering af spær på rem"));
            //universal 190 mm venstre
            material = getMaterialByMaterialVariantId(39);
            bomItems.add(new OrderLine(spærAntal,order.getId(), "stk", material,"Til montering af spær på rem"));

            //4,5 x 60 mm. skruer 200 stk.
            material = getMaterialByMaterialVariantId(21);
            bomItems.add(new OrderLine(1,order.getId(), "pakke(r)", material,"Til montering af stern&vandbrædt"));
            //4,0 x 50 mm. beslagskruer 250 stk.
            material = getMaterialByMaterialVariantId(40);
            bomItems.add(new OrderLine(3,order.getId(), "pakke(r)", material,"Til montering af universalbeslag + hulbånd"));

            //bræddebolt 10 x 120 mm.
            material = getMaterialByMaterialVariantId(22);
            bomItems.add(new OrderLine(stolpeAntal*3,order.getId(), "stk", material,"Til montering af rem på stolper"));
            //firkantskiver 40x40x11mm
            material = getMaterialByMaterialVariantId(23);
            bomItems.add(new OrderLine(stolpeAntal*2,order.getId(), "stk", material,"Til montering af rem på stolper"));
            //4,5 x 70 mm. Skruer 400 stk.
            material = getMaterialByMaterialVariantId(41);
            bomItems.add(new OrderLine(2,order.getId(), "pakke(r)", material,"til montering af yderste beklædning"));
            //4,5 x 50 mm. Skruer 300 stk.
            material = getMaterialByMaterialVariantId(42);
            bomItems.add(new OrderLine(2,order.getId(), "pakke(r)", material,"til montering af inderste beklædning"));

            if (carport.getShedLength() != null && carport.getShedLength() > 1) {
                //stalddørsgreb 50x75
                material = getMaterialByMaterialVariantId(24);
                bomItems.add(new OrderLine(1, order.getId(), "sæt", material, "Til lås på dør i skur"));
                //t hængsel 390 mm
                material = getMaterialByMaterialVariantId(43);
                bomItems.add(new OrderLine(2, order.getId(), "stk", material, "Til skurdør"));
                //vinkelbeslag 35
                material = getMaterialByMaterialVariantId(25);
                bomItems.add(new OrderLine(32, order.getId(), "stk", material, "Til montering af løsholter i skur"));
            }
        return bomItems;

    }

    public static OptimalMaterialResult getOptimalMaterial(int materialId, int requiredLength, double requiredMaterialWidth, int categoriId, boolean materialSplitAllowed) {
        Material material = null;
        TreeMap<Integer, Material> materials = FrontController.materialMap.get(categoriId);
        int bestVariantId = 0;
        double waste = -1;
        int quantity = 1;
        for (Material tmp: materials.values()) {
            if (tmp.getMaterialsId() == materialId && tmp.getWidth() == requiredMaterialWidth) {
                if (tmp.getLength() >= requiredLength ) {
                    if((tmp.getLength()-requiredLength < waste) || waste == -1){
                        waste = tmp.getLength()-requiredLength;
                        bestVariantId = tmp.getVariantId();
                        quantity = 1;
                    }
                } else {
                    // check if other length can be used
                    int quantityTmp = (int) Math.ceil(requiredLength/tmp.getLength());
                    if ((tmp.getLength()*quantityTmp)-requiredLength < waste || waste == -1 && materialSplitAllowed == true) {
                        waste = (tmp.getLength()*quantityTmp)-requiredLength;
                        bestVariantId = tmp.getVariantId();
                        quantity = quantityTmp;
                    }
                }
            }
        }
        material = materials.get(bestVariantId);
        return new OptimalMaterialResult(quantity, material);
    }

    public static OptimalMaterialResult getOptimalRoofUnits(int materialId, int carportLength, int carportWidth, RoofType roofType) {
        Material material = null;
        int quantity = 0;
        TreeMap<Integer, Material> materials = FrontController.materialMap.get(5);
        int bestVariantId = 0;
        double waste = -1;
        for (Material tmp: materials.values()) {
            if (tmp.getMaterialsId() == materialId) {
                if (tmp.getLength() >= carportWidth ) {
                    if((tmp.getLength()-carportWidth < waste) || waste == -1){
                        waste = tmp.getLength()-carportWidth;
                        bestVariantId = tmp.getVariantId();
                        quantity = 1;
                    }
                } else {
                    // check if other length can be used
                    int quantityTmp = (int) Math.ceil(carportWidth/tmp.getLength());
                    if ((tmp.getLength()*quantityTmp)-carportWidth < waste || waste == -1) {
                        waste = (tmp.getLength()*quantityTmp)-carportWidth;
                        bestVariantId = tmp.getVariantId();
                        quantity = quantityTmp;
                    }
                }
            }
        }
        material = materials.get(bestVariantId);
        //Material width/height i 90 degree off as thats how they are placed on the roof
        //We have calculated how many units neede to cover the carport width, now we multiply with the required
        //items needed for the length
        quantity = quantity*((int) Math.ceil(carportLength/material.getWidth()));
        System.out.println("roof quantity:" +quantity);
        return new OptimalMaterialResult(quantity,material);
    }

    public static double calculateOptimalDistance(double minDist, double maxDist, double materialWidth, double totalDist, double interval){
        double result = 0;
        double bestRemainder = 1000;
        for (double i = minDist; i <= maxDist; i += interval) {
            double remainder = (totalDist-(materialWidth*2))%i;
            if (remainder <= bestRemainder){
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
        double spærMinAfstandFladtTag = getRaftersDistanceByRoofType("FladtTag").getMin();
        double spærMaxAfstandFladtTag = getRaftersDistanceByRoofType("FladtTag").getMax();

        double spærMinAfstandTagMedRejsning = getRaftersDistanceByRoofType("TagMedRejsning").getMin();
        double spærMaxAfstandTagMedRejsning = getRaftersDistanceByRoofType("TagMedRejsning").getMax();

        double spærBredde = getRequiredWidthByCategory("spær");

        boolean fladtTag;

        if (carport.getRoofType() == "Fladt tag"){
            fladtTag = true;
        } else {
            fladtTag = false;
        }

        double carportLength = carport.getCarportLength();
        double spærMellemrum;

        if (fladtTag == true) {
            spærMellemrum = calculateOptimalDistance(spærMinAfstandFladtTag, spærMaxAfstandFladtTag, spærBredde, carportLength, 0.1);

        } else {
            spærMellemrum = calculateOptimalDistance(spærMinAfstandTagMedRejsning, spærMaxAfstandTagMedRejsning, spærBredde, carportLength, 0.1);
        }
        int spærAntal = (int) ((carportLength-(spærBredde*2))/spærMellemrum);

        //+1 fordi vi har regnet antal afstande ud og derfor lige skal plus 1 for at få antal spær
        spærAntal += 1;
        System.out.println("linje 76 "+ spærAntal);
        System.out.println(("bregning" + carportLength/spærMellemrum ));
        return spærAntal;
    }

    public static int calculateStolper(Carport carport) {
        int stolpeAntal = 0;
        int redskabsskurAntal = 0;
        int forresteStolpeAfstandFraFront = 100;
        int bagersteStolpeAfstandFraBag = 20;
        int maxAfstandMellemStolper = 310;
        double stolpeBredde = 10;
        double redskabsskurLængde = 0;

        int carportLængde = carport.getCarportLength();
        if (carport.getShedLength() != null) {
            redskabsskurLængde = carport.getShedLength();
            int redskabsskurBredde = carport.getShedWidth();
            double skurResultatDistanceSider = calculateOptimalDistance(80, maxAfstandMellemStolper, stolpeBredde, redskabsskurLængde, 1);
            int redskabsskurStolpeAntalSider = (int) ((redskabsskurLængde - (stolpeBredde*2))/skurResultatDistanceSider);
            double skurResultatDistanceFrontBag = calculateOptimalDistance(80, maxAfstandMellemStolper, stolpeBredde, redskabsskurBredde,1);
            int redskabsskurStolpeAntalFrontBag = (int) ((redskabsskurBredde - (stolpeBredde*2))/skurResultatDistanceFrontBag);

            System.out.println("linje 231 " +redskabsskurStolpeAntalSider);
            System.out.println("linje 232 " +redskabsskurStolpeAntalFrontBag);

            //vi har indtil videre kun regnet afstande så vi plusser 1 for at få antal stolper
            redskabsskurStolpeAntalFrontBag += 1;
            redskabsskurStolpeAntalSider += 1;

            //Minus med 2 fordi der allerede er en stolpe i hvert hjørne og vi omlidt skal regne front og bag ud som benytter samme hjørnestolpe
            redskabsskurStolpeAntalSider = redskabsskurStolpeAntalSider-2;
            //Ganges med 2 for at få begge sider med
            redskabsskurStolpeAntalSider = redskabsskurStolpeAntalSider*2;
            //Gange med 2 for at få både for og bagside med
            redskabsskurStolpeAntalFrontBag = redskabsskurStolpeAntalFrontBag*2;

            redskabsskurAntal = redskabsskurStolpeAntalFrontBag+redskabsskurStolpeAntalSider;

            //minus stolpebredde da vi tæller den med i vores optimaldistance
            redskabsskurLængde = redskabsskurLængde-stolpeBredde;
        }
        //TODO CHECK OP PÅ AT HJØRNESTOLPER IKKE BLIVER TALT DOBBELT I LIGNINGEN EFTER REDSKABSKUR BEREGNINGEN
        double afstand = carportLængde-forresteStolpeAfstandFraFront-redskabsskurLængde-bagersteStolpeAfstandFraBag;

        double result = calculateOptimalDistance(100, maxAfstandMellemStolper, stolpeBredde, afstand, 1);
        System.out.println("afstand " + afstand);
        System.out.println("resultat " +result);
        stolpeAntal = (int) ((afstand- (stolpeBredde*2))/result);
        //+1 fordi vi har regnet antal afstande ud og derfor lige skal plus 1 for at få antal stolper
        stolpeAntal += 1;
        //Vi ganger med 2 for at få total antal stolper fra begge sider
        stolpeAntal = stolpeAntal*2;

        //Hvis der er et skur, skal vi minus 2 stolper, da skuret allerede har de 2 stolper inkludere
        if (redskabsskurAntal > 0) {
            stolpeAntal = stolpeAntal - 2;
        }

        System.out.println("stolpe antal: "+stolpeAntal);
        System.out.println("stolpe antal skur: "+redskabsskurAntal);

        return  stolpeAntal + redskabsskurAntal;
    }

    public static Material getMaterialByMaterialVariantId(int variantId){
        return FrontController.materialMap.get(5).get(variantId);
    }

    public static double getRequiredWidthByCategory(String category){
        return FrontController.calculatorRequiredMaterialWidth.get(category).doubleValue();
    }

    public static MinMax getRaftersDistanceByRoofType(String category){
        return FrontController.raftersDistance.get(category);
    }
}
