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
        new StaticValues().setGlobalValues(database);

        //Used to populate the dropdown options on the form based on what FOG decides to offer in database
        getServletContext().setAttribute("categoryFormOptions", StaticValues.categoryFormOptions);

        //Used to populate the dropdown options on the form based on what FOG decides to offer in database
        getServletContext().setAttribute("standardCarports", StaticValues.standardCarports);

        //Used to populate which roof types can be selected at form
        getServletContext().setAttribute("roofTypes", getRoofTypes());

        //Used to make it possible for fog employee to modify BOM with other variants from the same materialID
        getServletContext().setAttribute("materialVariantMap",StaticValues.materialVariantMap);

        //Used to set which dimensions we allow for creation of new carports. eg. max length of the carport.
        getServletContext().setAttribute("allowedMeasurements",StaticValues.allowedMeasurements);

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

    public ArrayList<Status> getStatusValues(){
        ArrayList<Status> statuses = new ArrayList<>();
        for (Status status:Status.values()) {
            statuses.add(status);
        }
        return statuses;
    }

    public ArrayList<RoofType> getRoofTypes(){
        //Roof types
        ArrayList<RoofType> roofTypes = new ArrayList<>();
        for (RoofType type : RoofType.values()) {
            roofTypes.add(type);
        }
        return roofTypes;
    }
}


