package business.utilities;

import business.entities.*;
import business.persistence.Database;
import business.persistence.StandardCarportMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import web.StaticValues;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    private final static String DATABASE = "carport";  // Change this to your own database
    private final static String TESTDATABASE = DATABASE + "_test";
    private final static String USER = "dev";
    private final static String PASSWORD = "DevUser21!";
    private final static String URL = "jdbc:mysql://64.227.113.104:3306/" + TESTDATABASE + "?serverTimezone=CET&useSSL=false";

    private static Database database;

    public static Carport carportWithOutShed;
    public static Order orderWithOutShed;

    public static Carport carportWithShed;
    public static Order orderWithShed;

    public static Carport carportWithRoofTilt;
    public static Order orderWithRoofTilt;

    public static Material carportMaterial;
    public static Material shedMaterial;
    public static Material flatRoofMaterial;
    public static Material roofTiltMaterial;

    @BeforeAll
    public static void setUpClass() {
        try {
            database = new Database(USER, PASSWORD, URL);
            new StaticValues().setGlobalValues(database);
        } catch (ClassNotFoundException e) {   // kan ikke finde driveren i database klassen
            fail("Database connection failed. Missing jdbc driver");
        }

        //Used to mock a materials
            //TrykImp brædt for carport material
            carportMaterial = StaticValues.categoryFormOptions.get(1).get(1);
            //TrykImp brædt for shed material
            shedMaterial = StaticValues.categoryFormOptions.get(3).get(1);
            //Plastmo for flat roof calculator
            flatRoofMaterial = StaticValues.categoryFormOptions.get(2).get(3);
            //Sort Tegl for roof tilt calculator
            roofTiltMaterial = StaticValues.categoryFormOptions.get(4).get(4);

        //Used for carport without shed calculations and flat roof
        carportWithOutShed = new Carport(carportMaterial,300,250,500, RoofType.Fladt_Tag,flatRoofMaterial);
        carportWithOutShed.setId(1);
        orderWithOutShed = new Order(Status.Request,1,carportWithOutShed);


        //Used for carport with shed calculations
        carportWithShed = new Carport(carportMaterial,300,250,500, RoofType.Fladt_Tag,flatRoofMaterial);
        carportWithShed.setId(2);
        carportWithShed.setShedMaterial(shedMaterial);
        carportWithShed.setShedWidth(150);
        carportWithShed.setShedLength(150);
        orderWithShed = new Order(Status.Request,1,carportWithShed);

        //Used for carport with rooftilt calculations
        carportWithRoofTilt = new Carport(carportMaterial,300,250,500,RoofType.Tag_Med_Rejsning,roofTiltMaterial);
        carportWithRoofTilt.setId(3);
        carportWithRoofTilt.setRoofTilt(10);
        orderWithRoofTilt = new Order(Status.Request,1,carportWithRoofTilt);
    }


    @Test
    void calculateBOM() {
        ArrayList<OrderLine> bomItems = Calculator.calculateBOM(carportWithShed,orderWithShed);
        assertNotNull(bomItems);
        assertNotEquals(0,bomItems.size());
    }

    @Test
    void calculateRoofSideMaterial() {
        //Is only called to make sure SVG values is not null to allow our tests to run
        Calculator.calculateBOM(carportWithRoofTilt,orderWithRoofTilt);
        int expected = 1076;
        int actual = Calculator.calculateRoofSideMaterial(carportWithRoofTilt);
        assertEquals(expected,actual);

        carportWithRoofTilt.setRoofTilt(5);
        expected = 848;
        actual = Calculator.calculateRoofSideMaterial(carportWithRoofTilt);
        assertEquals(expected,actual);
        carportWithRoofTilt.setRoofTilt(10);
    }

    @Test
    void getOptimalMaterialWithOutSplitAllowed() {
        //Check that the algorithm finds the correct post
        OptimalMaterialResult optimalMaterialResult =
                Calculator.getOptimalMaterial(10,290,StaticValues.calculatorRequiredMaterialWidth.get("stolper").getValue(),5,false);
        //Check that we only receive 1 post back as we didnt allow for split of materials
        assertEquals(1,optimalMaterialResult.getQuantity());
        //Check that we receive the post in length 300 as it's closest to 290 that is longer
        assertEquals(310,optimalMaterialResult.getMaterial().getLength());
    }

    @Test
    void getOptimalMaterialWithSplitAllowed() {
        //Mock a post in 810 length to see that the algorithm rather wants to use 2 or more smaller posts as split is allowed.
        //Should use 2 posts in 400 rather than one in 810 as waste is lower
        double postWidth = StaticValues.calculatorRequiredMaterialWidth.get("stolper").getValue();
        Material mockMaterial = new Material("Bygge materialer","Trykimp stolpe",10,5,100,1,20000);
        mockMaterial.setHeight(9.7);
        mockMaterial.setWidth(postWidth);
        mockMaterial.setLength(810.0);
        StaticValues.materialMap.get(5).put(mockMaterial.getVariantId(),mockMaterial);

        //Check that the algorithm finds the correct post with materialSplitAllowed
        OptimalMaterialResult optimalMaterialResult =
                Calculator.getOptimalMaterial(10,800,postWidth,5,true);
        //Check that we receive 2 post back as we allowed materialSplit
        assertEquals(2,optimalMaterialResult.getQuantity());
        //Check that we receive the posts in 400 as waste is lower than 1 post in 810
        assertEquals(400.0,optimalMaterialResult.getMaterial().getLength());
    }

    @Test
    void getOptimalRoofUnitsFlatRoof() {
        //carportWithOutShed; width 300, length 500;
        Material material = Calculator.getMaterialByMaterialVariantId(34);
        OptimalMaterialResult expected = new OptimalMaterialResult(5,material,1);
        OptimalMaterialResult actual = Calculator.getOptimalRoofUnits(carportWithOutShed.getRoofMaterial().getMaterialsId(),carportWithOutShed.getCarportLength(),carportWithOutShed.getCarportWidth(),RoofType.fromString(carportWithOutShed.getRoofType()));
        assertEquals(expected,actual);

        carportWithOutShed.setCarportWidth(470);
        material = Calculator.getMaterialByMaterialVariantId(32);
        expected = new OptimalMaterialResult(5,material,1);
        actual = Calculator.getOptimalRoofUnits(carportWithOutShed.getRoofMaterial().getMaterialsId(),carportWithOutShed.getCarportLength(),carportWithOutShed.getCarportWidth(),RoofType.fromString(carportWithOutShed.getRoofType()));
        assertEquals(expected,actual);
        carportWithOutShed.setCarportWidth(300);
    }

    @Test
    void getOptimalRoofUnitsRoofWithTilt() {
        //carportWithRoofTilt (carportMaterial,300,250,500,RoofType.Tag_Med_Rejsning,roofTiltMaterial);
        Material material = Calculator.getMaterialByMaterialVariantId(6);
        OptimalMaterialResult expected = new OptimalMaterialResult(168,material,1);
        OptimalMaterialResult actual = Calculator.getOptimalRoofUnits(carportWithRoofTilt.getRoofMaterial().getMaterialsId(),carportWithRoofTilt.getCarportLength(),carportWithRoofTilt.getCarportWidth(),RoofType.fromString(carportWithRoofTilt.getRoofType()));
        assertEquals(expected,actual);
    }

    @Test
    void calculateOptimalDistance() {
        double minDist = 50;
        double maxDist = 60;
        double materialWidth = 10;
        double totalDist = 420;
        double interval = 0.1;

        double result = Calculator.calculateOptimalDistance(minDist,maxDist,materialWidth,totalDist,interval);
        assertEquals(50.0,result);
        materialWidth = 5;
        result = Calculator.calculateOptimalDistance(minDist,maxDist,materialWidth,totalDist,interval);
        assertNotEquals(50.0,result);
        minDist = 56;
        materialWidth = 0;
        totalDist = 600;
        result = Calculator.calculateOptimalDistance(minDist,maxDist,materialWidth,totalDist,interval);
        assertEquals(60.0,result);
    }

    @Test
    void calculateSpærWithFlatRoof() {
        //Is only called to make sure SVG values is not null to allow our tests to run
        Calculator.calculateBOM(carportWithOutShed,orderWithOutShed);
        //carportWithOutShed is 500 length. 500-2xSpærBredde(9 total) = 491 to make sure there is 1 spær at each end, then calculates rest of spærs inbetween
        //54.5 is the calculated distance between Spær as materialWidth is 4.5 for spær, so 491/54.5 is = 9.009 which means 9 distances between spær, which is 10 spær
        boolean tværGåendeSpær = false;
        int actualSpærAntal = Calculator.calculateSpær(carportWithOutShed,tværGåendeSpær);
        assertEquals(10,actualSpærAntal);
        carportWithOutShed.setCarportLength(600);
        actualSpærAntal = Calculator.calculateSpær(carportWithOutShed,tværGåendeSpær);
        assertEquals(12,actualSpærAntal);
    }

    @Test
    void calculateSpærWithRoofTilt() {
        //Is only called to make sure SVG values is not null to allow our tests to run
        Calculator.calculateBOM(carportWithRoofTilt,orderWithRoofTilt);
        //carportWithRoofTilt - CarportLength is 500
        boolean tværGåendeSpær = false;
        int normalSpær = Calculator.calculateSpær(carportWithRoofTilt,tværGåendeSpær);
        assertEquals(7,normalSpær);
        carportWithRoofTilt.setCarportLength(709);
        normalSpær = Calculator.calculateSpær(carportWithRoofTilt,tværGåendeSpær);
        assertEquals(8,normalSpær);
        carportWithRoofTilt.setCarportLength(500);

        //TværgåendeSpær beregning
        //carportWidth is 300
        tværGåendeSpær = true;
        int tværgående = Calculator.calculateSpær(carportWithRoofTilt,tværGåendeSpær);
        assertEquals(7,tværgående);
        carportWithRoofTilt.setCarportWidth(500);
        tværgående = Calculator.calculateSpær(carportWithRoofTilt,tværGåendeSpær);
        assertEquals(10,tværgående);
    }

    @Test
    void calculateStolperWithoutShed() {
        //Is only called to make sure SVG values is not null to allow our tests to run
        Calculator.calculateBOM(carportWithOutShed,orderWithOutShed);
        //carportWithOutShed - length = 500;
        int expected = 6;
        int actual = Calculator.calculateStolper(carportWithOutShed);
        assertEquals(expected,actual);

        carportWithOutShed.setCarportLength(780);
        expected = 8;
        actual = Calculator.calculateStolper(carportWithOutShed);
        assertEquals(expected,actual);
        //Revert carport length to std as they are initialized in beforeAll
        carportWithOutShed.setCarportLength(500);
    }

    @Test
    void calculateStolperWithShed() {
        //Is only called to make sure SVG values is not null to allow our tests to run
        Calculator.calculateBOM(carportWithShed,orderWithShed);
        //carportWithShed - length = 500, carportWidth = 300, shedWidth = 150;
        int expected = 8;
        int actual = Calculator.calculateStolper(carportWithShed);
        assertEquals(expected,actual);

        //Changed so shed is same width as carport, so we can utilize posts in side instead of having extras for shed in center.
        carportWithShed.setShedWidth(carportWithShed.getCarportWidth());
        expected = 6;
        actual = Calculator.calculateStolper(carportWithShed);
        assertEquals(expected,actual);

        //Revert the changed width
        carportWithShed.setShedWidth(150);
    }

    @Test
    void getMaterialByMaterialVariantId() {
        Material material = Calculator.getMaterialByMaterialVariantId(22);
        assertNotNull(material);
        assertEquals("Bræddebolt",material.getMaterialName());
    }

    @Test
    void getRequiredWidthByCategory() {
        double expected = StaticValues.calculatorRequiredMaterialWidth.get("spær").getValue();
        double actual = Calculator.getRequiredWidthByCategory("spær");
        assertEquals(expected,actual);
    }

    @Test
    void getRaftersDistanceByRoofType() {
        MinMax expected = StaticValues.raftersDistance.get("fladtTag");
        MinMax actual = Calculator.getRaftersDistanceByRoofType("fladtTag");
        assertEquals(expected,actual);
    }

    @Test
    void getPostDistancsByCategory() {
        Object expected = StaticValues.postDistances.get("afstandMellemStolper");
        Object actual = Calculator.getPostDistancsByCategory("afstandMellemStolper");
        assertEquals(expected,actual);

        expected = StaticValues.postDistances.get("stolpeNedgravning").getValue();
        actual = Calculator.getPostDistancsByCategory("stolpeNedgravning");
        assertEquals(expected,actual);
    }
}