package business.utilities;

import business.entities.*;
import web.FrontController;
import web.StaticValues;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.TreeMap;

public abstract class Calculator {
    private static SvgValues svgValues;
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
    //Vi antager at carport height er det indre mål på højden og ikke det udvendige



    public static ArrayList<OrderLine> calculateBOM(Carport carport, Order order){
        svgValues = new SvgValues();

        svgValues.setCarportId(order.getCarport().getId());
        svgValues.setCarportHeight(carport.getCarportHeight());
        svgValues.setCarportWidth(carport.getCarportWidth());
        svgValues.setCarportLenght(carport.getCarportLength());




        //The string in the treemap has to be category fx. Carport bygge materialer, Tag materialer
        ArrayList<OrderLine> bomItems = new ArrayList<>();
        OptimalMaterialResult optimalMaterialResult;

        //calculate carport

            //calculate spær
            int spærAntal = calculateSpær(carport, false);
            optimalMaterialResult = getOptimalMaterial(9, carport.getCarportWidth(), getRequiredWidthByCategory("spær"), 5,false);
            bomItems.add(new OrderLine(spærAntal,order.getId(), "stk",optimalMaterialResult.getMaterial(),"Spær, monteres på rem"));

            //Calculate stolper inklusiv redskabsskur
            int stolpeAntal = calculateStolper(carport);
            Double stolpeNedgravning = (Double) getPostDistancsByCategory("stolpeNedgravning");
            optimalMaterialResult = getOptimalMaterial(10, (carport.getCarportHeight()+stolpeNedgravning.intValue()), getRequiredWidthByCategory("stolper"), 5, false);
            bomItems.add(new OrderLine(stolpeAntal, order.getId(), "stk", optimalMaterialResult.getMaterial(), "Stolper nedgraves 90 cm. i jord"));

            //Calculate remme
            optimalMaterialResult = getOptimalMaterial(9,carport.getCarportLength(), getRequiredWidthByCategory("remme"), 5, false);
            bomItems.add(new OrderLine(2, order.getId(), "stk", optimalMaterialResult.getMaterial(), "Remme i sider, sadles ned i stolper"));
            svgValues.setRemMaterialeBredde(getRequiredWidthByCategory("remme"));
            svgValues.setRemMaterialeHøjde(getRequiredWidthByCategory("remmeHøjde"));
            System.out.println("test "+svgValues.getRemMaterialeHøjde());


        //Calculate redskabsrum
            //Redskabsskur beklædning
            if (carport.getShedLength() != null) {
                //front og bagbeklædning
                optimalMaterialResult = getOptimalMaterial(carport.getShedMaterial().getMaterialsId(), carport.getShedWidth(), getRequiredWidthByCategory("understernsbrædder"),5, true);
                int itemsNeededVertical = (int) Math.ceil(carport.getCarportHeight()/getRequiredWidthByCategory("understernsbrædder"));
                int quantity = (int) Math.ceil(((optimalMaterialResult.getQuantity()*itemsNeededVertical)*2)/optimalMaterialResult.getAmountCovered());
                bomItems.add(new OrderLine(quantity, order.getId(), "stk", optimalMaterialResult.getMaterial(), "Beklædning til skur front og bag"));
                //Side beklædning
                optimalMaterialResult = getOptimalMaterial(carport.getShedMaterial().getMaterialsId(), carport.getShedLength(), getRequiredWidthByCategory("understernsbrædder"),5, true);
                int itemsNeededVerticalSide = (int) Math.ceil(carport.getCarportHeight()/getRequiredWidthByCategory("understernsbrædder"));
                quantity = (int) Math.ceil(((optimalMaterialResult.getQuantity()*itemsNeededVerticalSide)*2)/optimalMaterialResult.getAmountCovered());
                bomItems.add(new OrderLine(quantity, order.getId(), "stk", optimalMaterialResult.getMaterial(), "Beklædning til skur sider"));
            }


        //Calculate Tag
            if (carport.getRoofType().equals(RoofType.Tag_Med_Rejsning.toString())) {
                //Tværgående spær
                //calculate spær
                int tværgåendeSpærAntal = calculateSpær(carport, true);
                optimalMaterialResult = getOptimalMaterial(9, carport.getCarportLength(), getRequiredWidthByCategory("spær"), 5,false);
                bomItems.add(new OrderLine(tværgåendeSpærAntal,order.getId(), "stk",optimalMaterialResult.getMaterial(),"Tværgående spær, monteres på spær"));

                optimalMaterialResult = getOptimalMaterial(carport.getCarportMaterial().getMaterialsId(),calculateRoofSideMaterial(carport),getRequiredWidthByCategory("understernsbrædder"),5,true );
                bomItems.add(new OrderLine(optimalMaterialResult.getQuantity(),order.getId(), "stk",optimalMaterialResult.getMaterial(),"Beklædnings brædder til gavler - Tag med rejsning"));
            }
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
            StaticValues.svgValuesTreeMap.put(carport.getId(),svgValues);
        return bomItems;
    }

    public static int calculateRoofSideMaterial(Carport carport) {
        double roofTilt = carport.getRoofTilt();
        ArrayList<Double> heights = new ArrayList<>();

        Double height = 0.0;
        double width = carport.getCarportWidth()/2;

        //Material width - Later incremented with materialwidth to calculate height;
        double widthInterval = getRequiredWidthByCategory("understernsbrædder");

        //180 i en trekant
        double topAngle = 90.0-roofTilt;
        topAngle = Math.toRadians(topAngle);
        Double hypo;
        double widthRemaining = width;
        //hypotenuseberegning
        for (int i = 0; i < Math.ceil(width/widthInterval); i++) {
            hypo = (widthRemaining*Math.sin(Math.toRadians(90)))/Math.sin(topAngle);
            height = Math.cos(topAngle)*hypo;
            height = height + getRequiredWidthByCategory("spærHøjde");
            widthRemaining = widthRemaining - widthInterval;
            heights.add(height);
            if (i == 0){
                svgValues.setTagMedRejsningHøjde(height);
            }
        }
        double totalLength = 0;

        //Her lægger vi det hele sammen så køberen selv kan udskære det
        for (Double tmp:heights) {
            totalLength = totalLength + tmp;
        }
        //Vi har delt taget i 2 for at få en retvinklet trekant. Nu ganger vi med 2 for at inkludere den anden side
        totalLength = totalLength*2;
        //Nu ganger vi med 2 for at få bagsiden med også
        totalLength = totalLength*2;

        totalLength = Math.ceil(totalLength);
        System.out.println("Total length "+totalLength);
        return (int) totalLength;
    }

    public static OptimalMaterialResult getOptimalMaterial(int materialId, int requiredLength, double requiredMaterialWidth, int categoriId, boolean materialSplitAllowed) {
        Material material = null;
        TreeMap<Integer, Material> materials = StaticValues.materialMap.get(categoriId);
        int bestVariantId = 0;
        double waste = -1;
        int quantity = 1;
        int amountCovered = 1;
        Double bestVariantLength = null;

        for (Material materialTmp: materials.values()) {

            if (materialTmp.getMaterialsId() == materialId && materialTmp.getWidth() == requiredMaterialWidth) {
                int requiredLengthAddsup = (int) Math.floor(materialTmp.getLength()/requiredLength);


                //If required length can be more than one time on the material
                if (requiredLengthAddsup >=1 ) {
                    if ((((materialTmp.getLength()/requiredLengthAddsup) - requiredLength)*requiredLengthAddsup <= waste) || waste == -1) {
                        if (bestVariantLength != null && materialTmp.getLength() < bestVariantLength) {
                            waste = ((materialTmp.getLength() / requiredLengthAddsup) - requiredLength) * requiredLengthAddsup;
                            bestVariantId = materialTmp.getVariantId();
                            quantity = 1;
                            amountCovered = requiredLengthAddsup;
                            bestVariantLength = materialTmp.getLength();
                        } else {
                            waste = ((materialTmp.getLength() / requiredLengthAddsup) - requiredLength) * requiredLengthAddsup;
                            bestVariantId = materialTmp.getVariantId();
                            quantity = 1;
                            amountCovered = requiredLengthAddsup;
                            bestVariantLength = materialTmp.getLength();
                        }
                    }
                }
                if (materialTmp.getLength() >= requiredLength ) {
                    if ((materialTmp.getLength() - requiredLength <= waste) || waste == -1) {
                        waste = materialTmp.getLength() - requiredLength;
                        bestVariantId = materialTmp.getVariantId();
                        quantity = 1;
                        amountCovered = 1;
                        bestVariantLength = materialTmp.getLength();
                    }
                } else {
                    // check if other length can be used
                    int quantityTmp = (int) Math.ceil(requiredLength/materialTmp.getLength());
                    if ((materialTmp.getLength()*quantityTmp)-requiredLength < waste || waste == -1 && materialSplitAllowed == true) {
                        waste = (materialTmp.getLength()*quantityTmp)-requiredLength;
                        bestVariantId = materialTmp.getVariantId();
                        quantity = quantityTmp;
                        amountCovered = 1;
                        bestVariantLength = materialTmp.getLength();
                    }
                }

            }
        }
        material = materials.get(bestVariantId);
        return new OptimalMaterialResult(quantity, material, amountCovered);
    }

    public static OptimalMaterialResult getOptimalRoofUnits(int materialId, int carportLength, int carportWidth, RoofType roofType) {
        Material material = null;
        int quantity = 0;
        TreeMap<Integer, Material> materials = StaticValues.materialMap.get(5);
        int bestVariantId = 0;
        double waste = -1;
        int amountCovered = 1;
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
        return new OptimalMaterialResult(quantity,material, amountCovered);
    }

    public static double calculateOptimalDistance(double minDist, double maxDist, double materialWidth, double totalDist, double interval){
        DecimalFormat df = new DecimalFormat("0.0");
        double result = 0;
        double bestRemainder = 1000;
        for (double i = minDist; i <= maxDist; i += interval) {
            //Used to make sure i is only 1 decimal
            //i = Math.round(i*10)/10;
            String rounder = df.format(i);
            rounder = rounder.replace(",",".");
            i = Double.parseDouble(rounder);
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

    public static int calculateSpær(Carport carport, boolean tværgåendeSpær){
        //Alle mål er i centimeter
        double spærMinAfstandFladtTag = getRaftersDistanceByRoofType("fladtTag").getMin();
        double spærMaxAfstandFladtTag = getRaftersDistanceByRoofType("fladtTag").getMax();

        double tværgåendeSpærMinAfstandTagMedRejsning = getRaftersDistanceByRoofType("tagMedRejsningTværgående").getMin();
        double tværgåendeSpærMaxAfstandTagMedRejsning = getRaftersDistanceByRoofType("tagMedRejsningTværgående").getMax();

        double spærMinAfstandTagMedRejsning = getRaftersDistanceByRoofType("tagMedRejsning").getMin();
        double spærMaxAfstandTagMedRejsning = getRaftersDistanceByRoofType("tagMedRejsning").getMax();


        double spærBredde = getRequiredWidthByCategory("spær");
        double spærHøjde = getRequiredWidthByCategory("spærHøjde");
        svgValues.setSpærMaterialeBredde(spærBredde);
        svgValues.setSpærMaterialeHøjde(spærHøjde);
        //TODO sæt spær højde
        boolean fladtTag;

        if (carport.getRoofType() == "Fladt tag"){
            fladtTag = true;
        } else {
            fladtTag = false;
        }
        double carportLength = carport.getCarportLength();
        double carportwidth = carport.getCarportWidth();
        double spærMellemrum;
        int spærAntal;
        if (fladtTag == true) {
            spærMellemrum = calculateOptimalDistance(spærMinAfstandFladtTag, spærMaxAfstandFladtTag, spærBredde, carportLength, 0.1);
            spærAntal = (int) ((carportLength-(spærBredde*2))/spærMellemrum);
            spærAntal += 1;
            svgValues.setSpærAntal(spærAntal);
            svgValues.setSpærMellemrum(spærMellemrum);

        } else {
            if (tværgåendeSpær == false) {
                spærMellemrum = calculateOptimalDistance(spærMinAfstandTagMedRejsning, spærMaxAfstandTagMedRejsning, spærBredde, carportLength, 0.1);
                spærAntal = (int) ((carportLength-(spærBredde*2))/spærMellemrum);
                //+1 fordi vi har regnet antal afstande ud og derfor lige skal plus 1 for at få antal spær
                spærAntal += 1;
                svgValues.setSpærAntal(spærAntal);
                svgValues.setSpærMellemrum(spærMellemrum);
            } else {
                spærMellemrum = calculateOptimalDistance(tværgåendeSpærMinAfstandTagMedRejsning, tværgåendeSpærMaxAfstandTagMedRejsning, spærBredde, carportwidth, 0.1);
                spærAntal = (int) ((carportwidth-(spærBredde*2))/spærMellemrum);
                //+1 fordi vi har regnet antal afstande ud og derfor lige skal plus 1 for at få antal spær
                spærAntal += 1;
                svgValues.setSpærAntalTværgående(spærAntal);
                svgValues.setSpærMellemrumTværgående(spærMellemrum);
            }
        }
        return spærAntal;
    }

    public static int calculateStolper(Carport carport) {
        int stolpeAntal = 0;
        int redskabsskurAntal = 0;
        double forresteStolpeAfstandFraFront = (Double) getPostDistancsByCategory("forresteStolpeAfstandFraFront");
        double bagersteStolpeAfstandFraBag = (Double) getPostDistancsByCategory("bagersteStolpeAfstandFraBag");
        MinMax afstandMellemStolper = (MinMax) getPostDistancsByCategory("afstandMellemStolper");
        double stolpeBredde = getRequiredWidthByCategory("stolper");
        double redskabsskurLængde = 0;
        svgValues.setStolpeAfstandFront(forresteStolpeAfstandFraFront);
        svgValues.setStolpeAfstandBag(bagersteStolpeAfstandFraBag);
        svgValues.setStolpeMaterialeBredde(stolpeBredde);

        int carportLængde = carport.getCarportLength();
        int redskabsskurStolpeAntalSider = 0;
        if (carport.getShedLength() != null) {
            redskabsskurLængde = carport.getShedLength();
            int redskabsskurBredde = carport.getShedWidth();
            redskabsskurStolpeAntalSider = (int)Math.floor(redskabsskurLængde/afstandMellemStolper.getMax());
            double skurResultatDistanceSider = redskabsskurLængde/(redskabsskurStolpeAntalSider+1);
            int redskabsskurStolpeAntalFrontBag = (int)Math.floor(redskabsskurBredde/afstandMellemStolper.getMax());
            double skurResultatDistanceFrontBag = redskabsskurBredde/(redskabsskurStolpeAntalFrontBag+1);


            System.out.println("Redskabskur mellemrum sider"+skurResultatDistanceSider);
            //vi har indtil videre kun regnet afstande så vi plusser 1 for at få antal stolper
            redskabsskurStolpeAntalFrontBag += 2;
            redskabsskurStolpeAntalSider += 2;
            svgValues.setSkurStolpeAntalFrontBag(redskabsskurStolpeAntalFrontBag);
            svgValues.setSkurStolpeAntalSider(redskabsskurStolpeAntalSider);
            svgValues.setStolpeAfstandSkurSider(skurResultatDistanceSider);
            svgValues.setStolpeAfstandSkurforOgBag(skurResultatDistanceFrontBag);
            svgValues.setSkurBredde(carport.getShedWidth());
            svgValues.setSkurLængde(carport.getShedLength());


            redskabsskurAntal = (redskabsskurStolpeAntalFrontBag*2)+(redskabsskurStolpeAntalSider*2)-4; //-4 da vi har talt alle hjørner bed 2x

            //minus stolpebredde da vi tæller den med i vores optimaldistance
            redskabsskurLængde = redskabsskurLængde-stolpeBredde;
        }

        double beregningsAfstand = carportLængde-forresteStolpeAfstandFraFront-redskabsskurLængde-bagersteStolpeAfstandFraBag;

        //double afstand = calculateOptimalDistance(afstandMellemStolper.getMin(), afstandMellemStolper.getMax(), stolpeBredde, beregningsAfstand, 1);
        stolpeAntal = (int)Math.floor(beregningsAfstand/afstandMellemStolper.getMax());
        double afstand = beregningsAfstand/(stolpeAntal+1); //+1 for at gå fra antal stolper til afstande

        stolpeAntal = stolpeAntal*2;

        stolpeAntal += 4;

        System.out.println("beregnings beregningsAfstand " + beregningsAfstand);
        System.out.println("afstand " + afstand);
        svgValues.setStolpeAfstand(afstand);
        //stolpeAntal = (int) ((beregningsAfstand- (stolpeBredde*2))/afstand);
        //+1 fordi vi har regnet antal afstande ud og derfor lige skal plus 1 for at få antal stolper
        //stolpeAntal += 1;
        //Vi ganger med 2 for at få total antal stolper fra begge sider
        //stolpeAntal = stolpeAntal*2;

        //Hvis der er et skur, skal vi minus 2 stolper, da skuret allerede har de 2 stolper inkludere
        if (redskabsskurAntal > 0) {
            stolpeAntal = stolpeAntal - 2; //-2 da skur og og bagerste stolpe er den samme i begge sider
            if (carport.getShedWidth() < carport.getCarportWidth()) {
                stolpeAntal = stolpeAntal + redskabsskurStolpeAntalSider; //Hvis skuret ikke er ligeså bred som carporten så tilføjer vi den enes side med stolper af skuret
            }

        }

        System.out.println("stolpe antal: "+stolpeAntal);
        System.out.println("stolpe antal skur: "+redskabsskurAntal);

        svgValues.setStolpeAntal(stolpeAntal + redskabsskurAntal);
        stolpeAntal = stolpeAntal + redskabsskurAntal;
        return stolpeAntal;
    }

    public static Material getMaterialByMaterialVariantId(int variantId){
        return StaticValues.materialMap.get(5).get(variantId);
    }

    public static double getRequiredWidthByCategory(String category){
        return StaticValues.calculatorRequiredMaterialWidth.get(category).getValue();
    }

    public static MinMax getRaftersDistanceByRoofType(String category){
        return StaticValues.raftersDistance.get(category);
    }

    public static Object getPostDistancsByCategory(String category){
        MinMax minMax = StaticValues.postDistances.get(category);
        if(minMax.getValue() != null){
            return minMax.getValue();
        }
        else{
        return StaticValues.postDistances.get(category);
        }
    }
}
