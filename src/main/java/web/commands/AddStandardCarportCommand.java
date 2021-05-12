package web.commands;

import business.entities.*;
import business.exceptions.UserException;
import business.services.OrderFacade;
import business.services.OrderLineFacade;
import business.services.StandardCarportFacade;
import business.utilities.Calculator;
import web.FrontController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddStandardCarportCommand extends CommandProtectedPage{
    public AddStandardCarportCommand(String pageToShow, String role) {
        super(pageToShow, role);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        StandardCarportFacade standardCarportFacade = new StandardCarportFacade(database);
        //Fetch variables

        //Create carport object
        //Carport carport = new Carport()
        //Maybe change so carport has materials rather than String or Int for beklædning?
        
        //TODO: Check if the carports dimensions is okay

        //Write to std carport db


        //Add to std carport list

        //Update app scope
        return pageToShow;
    }
}
