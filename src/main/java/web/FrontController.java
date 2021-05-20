package web;

import business.entities.*;
import business.exceptions.UserException;
import business.persistence.Database;
import business.services.MaterialFacade;
import business.services.StandardCalcValuesFacade;
import business.services.StandardCarportFacade;
import web.commands.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "FrontController", urlPatterns = {"/fc/*"})
public class FrontController extends HttpServlet {
    private final static String USER = "dev";
    private final static String PASSWORD = "DevUser21!";
    private final static String URL = "jdbc:mysql://167.172.176.18:3306/carport?serverTimezone=CET";


    public static Database database;
    public static TreeMap<Integer, Carport> standardCarports = new TreeMap<>();
    public static TreeMap<Integer, TreeMap<Integer, Material>> materialMap = new TreeMap<>();
    public static TreeMap<Integer, TreeMap<Integer, Material>> categoryFormOptions = new TreeMap<>();
    public static TreeMap<String, MinMax> allowedMeasurements = new TreeMap<>();
    public static TreeMap<Integer, TreeMap<Integer, Material>> materialVariantMap = new TreeMap<>();
    public static TreeMap<String,MinMax> calculatorRequiredMaterialWidth = new TreeMap<>();
    public static TreeMap<String, MinMax> raftersDistance = new TreeMap<>();
    public static TreeMap<String,MinMax> postDistances = new TreeMap<>();
    public static TreeMap<String,MinMax> priceCalculatorValues = new TreeMap<>();

    StandardCalcValuesFacade standardCalcValuesFacade;
    MaterialFacade materialFacade;
    StandardCarportFacade standardCarportFacade;


    public void init() throws ServletException {
        // Initialize database connection
        if (database == null) {
            try {
                database = new Database(USER, PASSWORD, URL);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger("web").log(Level.SEVERE, ex.getMessage(), ex);
            }
        }

        // Initialize whatever global datastructures needed here:
        materialFacade = new MaterialFacade(database);
        standardCalcValuesFacade = new StandardCalcValuesFacade(database);
        standardCarportFacade = new StandardCarportFacade(database);

        //Used to collect all material from the database
        try {
            materialMap = materialFacade.getAllMaterials();
        } catch (UserException e) {
            e.printStackTrace();
        }

        //Used to populate the dropdown options on the form based on what FOG decides to offer in database
        categoryFormOptions = getCategoryFormOptions(materialMap);
        getServletContext().setAttribute("categoryFormOptions", categoryFormOptions);

        //Used to set the standard carports from DB for display and ordering on website
        try {
            standardCarports = standardCarportFacade.getStandardCarports();
        } catch (UserException e) {
            e.printStackTrace();
        }
        getServletContext().setAttribute("standardCarports", standardCarports);

        //Used to populate which roof types can be selected at form
        getServletContext().setAttribute("roofTypes", getRoofTypes());

        //Used to make it possible for fog employee to modify BOM with other variants from the same materialID
        materialVariantMap = getMaterialVariantMap(materialMap);
        getServletContext().setAttribute("materialVariantMap",materialVariantMap);

        //Used to set which dimensions we allow for creation of new carports. eg. max length of the carport.
        try {
            allowedMeasurements = standardCalcValuesFacade.getAllowedMeasurements();
        } catch (UserException e) {
            e.printStackTrace();
        }
        getServletContext().setAttribute("allowedMeasurements",allowedMeasurements);

        //Used to set which width we require for specific materials (used in calculations of BOM) eg. we want a post to be a specific width as it cant be to slim
        try {
            calculatorRequiredMaterialWidth = standardCalcValuesFacade.getCalculatorRequiredMaterialWidth();
        } catch (UserException e) {
            e.printStackTrace();
        }

        //Used to set which min and max distance we allow for rafters (different for flat roof and roof with tilt).
        try {
            raftersDistance = standardCalcValuesFacade.getRaftersDistance();
        } catch (UserException e) {
            e.printStackTrace();
        }

        //Used to set post distances needed in calculations, eg. max distance between posts
        try {
            postDistances = standardCalcValuesFacade.getPostDistances();
        } catch (UserException e) {
            e.printStackTrace();
        }

        //Used for order price calculation
        try {
            priceCalculatorValues = standardCalcValuesFacade.getPriceCalculatorValues();
        } catch (UserException e) {
            e.printStackTrace();
        }

        //Used to check status
        getServletContext().setAttribute("status",getStatusValues());

    }

    protected void processRequest(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            Command action = Command.fromPath(request, database);

            if (action instanceof CommandUnknown) {
                response.sendError(404);
                return;
            }

            String view = action.execute(request, response);

            if (view.startsWith(Command.REDIRECT_INDICATOR)) {
                String page = view.substring(Command.REDIRECT_INDICATOR.length());
                response.sendRedirect(page);
                return;
            }

            request.getRequestDispatcher("/WEB-INF/" + view + ".jsp").forward(request, response);
        } catch (UnsupportedEncodingException | UserException ex) {
            request.setAttribute("problem", ex.getMessage());
            Logger.getLogger("web").log(Level.SEVERE, ex.getMessage(), ex);
            request.getRequestDispatcher("/errorpage.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "FrontController for application";
    }

    public TreeMap<Integer, TreeMap<Integer, Material>> getCategoryFormOptions(TreeMap<Integer, TreeMap<Integer, Material>> inputTreeMap){
        TreeMap<Integer, TreeMap<Integer, Material>> categoryFormOptions = new TreeMap<>();
        TreeMap<Integer, Material> formOption;
        for (Map.Entry<Integer, TreeMap<Integer, Material>> tmp : inputTreeMap.entrySet()) {
            for (Material tmp1 : tmp.getValue().values()) {
                if (!categoryFormOptions.containsKey(tmp.getKey())) {
                    formOption = new TreeMap<>();
                    formOption.put(tmp1.getMaterialsId(), tmp1);
                    categoryFormOptions.put(tmp1.getMaterialsCategoryId(), formOption);
                } else {
                    //If categori id exist then we add the material to the treeMap under the existing categoriID
                    formOption = categoryFormOptions.get(tmp.getKey());
                    //Check om materialeID eksistere i formoption listen, hvis ikke tilføj

                    if (!formOption.containsKey(tmp1.getMaterialsId())) {
                        formOption.put(tmp1.getMaterialsId(), tmp1);
                    }
                }
            }
        }
        return categoryFormOptions;
    }

    public ArrayList<RoofType> getRoofTypes(){
        //Roof types
        ArrayList<RoofType> roofTypes = new ArrayList<>();
        for (RoofType type : RoofType.values()) {
            roofTypes.add(type);
        }
        return roofTypes;
    }

    public TreeMap<Integer, TreeMap<Integer, Material>> getMaterialVariantMap(TreeMap<Integer, TreeMap<Integer, Material>> inputMap){
        TreeMap<Integer, TreeMap<Integer, Material>> materialVariantMap = new TreeMap<>();
        for (Map.Entry<Integer,Material> tmp: inputMap.get(5).entrySet()) {
            int materialId = tmp.getValue().getMaterialsId();
            if(!materialVariantMap.containsKey(materialId)){
                materialVariantMap.put(materialId,Material.getMaterialVariantsFromMaterialId(materialId));
            }
        }
        return materialVariantMap;
    }

    public ArrayList<Status> getStatusValues(){
        ArrayList<Status> statuses = new ArrayList<>();
        for (Status status:Status.values()) {
            statuses.add(status);
        }
        return statuses;
    }
}


