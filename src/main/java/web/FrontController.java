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
    public static TreeMap<String,Double> calculatorRequiredMaterialWidth = new TreeMap<>();
    public static TreeMap<String, MinMax> raftersDistance = new TreeMap<>();

    StandardCalcValuesFacade standardCalcValuesFacade;


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
        //Used to collect all material from the database
        MaterialFacade materialFacade = new MaterialFacade(database);
        try {
            materialMap = materialFacade.getAllMaterials();
        } catch (UserException e) {
            e.printStackTrace();
        }


        TreeMap<Integer, Material> formOption = new TreeMap<>();
        for (Map.Entry<Integer, TreeMap<Integer, Material>> tmp : materialMap.entrySet()) {
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
        getServletContext().setAttribute("categoryFormOptions", categoryFormOptions);


        //Get standard carports
        StandardCarportFacade standardCarportFacade = new StandardCarportFacade(database);
        try {
            standardCarports = standardCarportFacade.getStandardCarports();
        } catch (UserException e) {
            e.printStackTrace();
        }
        //Add standard carports to app scope
        getServletContext().setAttribute("standardCarports", standardCarports);


        //Sets options to appscopes
        //Roof types
        ArrayList<RoofType> roofTypes = new ArrayList<>();
        for (RoofType type : RoofType.values()) {
            roofTypes.add(type);
        }
        getServletContext().setAttribute("roofTypes", roofTypes);
        //Rest of form options
        getServletContext().setAttribute("categoryFormOptions", categoryFormOptions);

        standardCalcValuesFacade = new StandardCalcValuesFacade(database);
        setAllowedMeasurements();
        setCalculatorRequiredMaterialWidth();
        setRaftersDistance();


        for (Map.Entry<Integer,Material> tmp: materialMap.get(5).entrySet()) {
            int materialId = tmp.getValue().getMaterialsId();
            if(!materialVariantMap.containsKey(materialId)){
                materialVariantMap.put(materialId,Material.getMaterialVariantsFromMaterialId(materialId));
            }
        }
        getServletContext().setAttribute("materialVariantMap",materialVariantMap);
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

    public void setAllowedMeasurements(){
        try {
            allowedMeasurements = standardCalcValuesFacade.getAllowedMeasurements();
        } catch (UserException e) {
            e.printStackTrace();
        }
        getServletContext().setAttribute("allowedMeasurements",allowedMeasurements);
    }

    public void setCalculatorRequiredMaterialWidth(){
        try {
            calculatorRequiredMaterialWidth = standardCalcValuesFacade.getCalculatorRequiredMaterialWidth();
        } catch (UserException e) {
            e.printStackTrace();
        }
    }

    public void setRaftersDistance(){
        try {
            raftersDistance = standardCalcValuesFacade.getRaftersDistance();
        } catch (UserException e) {
            e.printStackTrace();
        }
    }

}


