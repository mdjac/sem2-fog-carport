package web;

import business.entities.Carport;
import business.entities.Material;
import business.exceptions.UserException;
import business.persistence.Database;
import business.services.MaterialFacade;
import web.commands.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "FrontController", urlPatterns = {"/fc/*"})
public class FrontController extends HttpServlet
{
    private final static String USER = "dev";
    private final static String PASSWORD = "DevUser21!";
    private final static String URL = "jdbc:mysql://167.172.176.18:3306/carport?serverTimezone=CET";


    public static Database database;
    public static TreeMap<Integer, Carport> standardCarports = new TreeMap<>();
    public static List<Material> materialList = new ArrayList<>();

    public void init() throws ServletException
    {
        // Initialize database connection
        if (database == null)
        {
            try
            {
                database = new Database(USER, PASSWORD, URL);
            }
            catch (ClassNotFoundException ex)
            {
                Logger.getLogger("web").log(Level.SEVERE, ex.getMessage(), ex);
            }
        }

        // Initialize whatever global datastructures needed here:


        //Create standard carports
        Carport carport = new Carport(
                "Sort Træ",
                "300 cm",
                "320 cm",
                "400 cm",
                "brunt træ",
                "100 cm",
                "100 cm",
                "20 grader",
                "Plastik",
                "Fladt tag");
        carport.setId(1);
        standardCarports.put(carport.getId(), carport);

        Carport carport2 = new Carport(
                "Sort Træ",
                "300 cm",
                "320 cm",
                "400 cm",
                "brunt træ",
                "100 cm",
                "100 cm",
                "20 grader",
                "Tegl",
                "Fladt tag");
        carport2.setId(2);
        standardCarports.put(carport2.getId(), carport2);
        //Add standard carports to app scope
        getServletContext().setAttribute("standardCarports",standardCarports);

        //TODO: TEST TO SEE IF MATERIALS ARE ADDED
        MaterialFacade materialFacade = new MaterialFacade(database);
        try {
            materialList = materialFacade.getAllMaterials();
        } catch (UserException e) {
            e.printStackTrace();
        }
        for (Material tmp:materialList ) {
            System.out.println(tmp.toString());
        }
    }

    protected void processRequest(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
    {
        try
        {
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
        }
        catch (UnsupportedEncodingException | UserException ex)
        {
            request.setAttribute("problem", ex.getMessage());
            Logger.getLogger("web").log(Level.SEVERE, ex.getMessage(), ex);
            request.getRequestDispatcher("/errorpage.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo()
    {
        return "FrontController for application";
    }

}
