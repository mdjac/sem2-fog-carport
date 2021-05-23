package business.utilities;

import business.entities.*;
import business.persistence.Database;
import business.persistence.StandardCarportMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import web.StaticValues;

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
        orderWithOutShed = new Order(Status.Request,1,carportWithOutShed);


        //Used for carport with shed calculations
        carportWithShed = new Carport(carportMaterial,300,250,500, RoofType.Fladt_Tag,flatRoofMaterial);
        carportWithShed.setShedMaterial(shedMaterial);
        carportWithShed.setShedWidth(150);
        carportWithShed.setShedLength(150);
        orderWithShed = new Order(Status.Request,1,carportWithShed);

        //Used for carport with rooftilt calculations
        carportWithRoofTilt = new Carport(carportMaterial,300,250,500,RoofType.Tag_Med_Rejsning,roofTiltMaterial);
        carportWithRoofTilt.setRoofTilt(5);
    }


    @Test
    void calculateBOM() {
    }

    @Test
    void calculateRoofSideMaterial() {
    }

    @Test
    void getOptimalMaterialWithOutSplitAllowed() {
        //Check that the algorithm finds the correct post
        OptimalMaterialResult optimalMaterialResult =
                Calculator.getOptimalMaterial(10,290,StaticValues.calculatorRequiredMaterialWidth.get("stolper").getValue(),5,false);
        //Check that we only receive 1 post back as we didnt allow for split of materials
        assertEquals(1,optimalMaterialResult.getQuantity());
        //Check that we receive the post in length 300 as it's closest to 290 that is longer
        assertEquals(300,optimalMaterialResult.getMaterial().getLength());
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
    void getOptimalRoofUnits() {
    }

    @Test
    void calculateOptimalDistance() {
        double minDist = 50;
        double maxDist = 60;
        double materialWidth = 10;
        double totalDist = 300;
        double interval = 0.1;

        double result = Calculator.calculateOptimalDistance(minDist,maxDist,materialWidth,totalDist,interval);

    }

    @Test
    void calculateSpær() {
    }

    @Test
    void calculateStolper() {
    }

    @Test
    void getMaterialByMaterialVariantId() {
    }

    @Test
    void getRequiredWidthByCategory() {
    }

    @Test
    void getRaftersDistanceByRoofType() {
    }

    @Test
    void getPostDistancsByCategory() {
    }
}