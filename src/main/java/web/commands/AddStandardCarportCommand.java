package web.commands;

import business.entities.*;
import business.exceptions.UserException;
import business.persistence.StandardCarportMapper;
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
    public String execute(HttpServletRequest request, HttpServletResponse response) throws UserException {
        StandardCarportFacade standardCarportFacade = new StandardCarportFacade(database);
        Carport carport;
        //Fetch required variables
        RoofType roofType = RoofType.valueOf(request.getParameter("rooftype"));
        int roofMaterialId = Integer.parseInt(request.getParameter("roofmaterial"));
        int carportMaterialId = Integer.parseInt(request.getParameter("carportmaterial"));
        int carportLength = Integer.parseInt(request.getParameter("carportlength"));
        int carportWidth = Integer.parseInt(request.getParameter("carportwidth"));
        int carportHeight = Integer.parseInt(request.getParameter("carportheight"));
        carport = new Carport(Carport.findCarportMaterialFromId(carportMaterialId),carportWidth,carportHeight,carportLength,roofType,Carport.findRoofMaterialFromId(roofMaterialId,roofType));

        //Only tries to getParameter if the value isn't empty as not all carports have tilt
        Integer roofTilt = null;
        if(!request.getParameter("rooftilt").isEmpty()){
            roofTilt = Integer.parseInt(request.getParameter("rooftilt"));
            carport.setRoofTilt(roofTilt);
        }
        //Only tries to getParameters if shed is choosen
        Integer shedMaterialId = null;
        Integer shedLength = null;
        Integer shedWidth = null;
        if(request.getParameter("chooseshed").equals("ja")){
            shedMaterialId = Integer.parseInt(request.getParameter("shedmaterial"));
            shedLength = Integer.parseInt(request.getParameter("shedlength"));
            shedWidth = Integer.parseInt(request.getParameter("shedwidth"));
            carport.setShedMaterial(Carport.findShedMaterialFromId(shedMaterialId));
            carport.setShedLength(shedLength);
            carport.setShedWidth(shedWidth);
        }

        //Check if the carports dimensions is okay
        boolean acceptableMeasurements = false;
        if(FrontController.allowedMeasurements.get("carportLength").between(carportLength) && FrontController.allowedMeasurements.get("carportWidth").between(carportWidth) && FrontController.allowedMeasurements.get("carportHeight").between(carportHeight)){
            //Carport dimensions is okay
            acceptableMeasurements = true;
            //Check for tilted roof, to see if tilt is acceptable
            if(roofTilt != null){
                acceptableMeasurements = FrontController.allowedMeasurements.get("roofTilt").between(roofTilt);
            }
            //Check if shed is choosen and that boolean isnt false already from the rooftilt check
            if(acceptableMeasurements == true && request.getParameter("chooseshed").equals("ja")){
                //Check if the shed dimensions is okay
                if(FrontController.allowedMeasurements.get("shedLength").between(shedLength) && FrontController.allowedMeasurements.get("shedWidth").between(shedWidth)){
                    acceptableMeasurements = true;
                }
                else{
                    acceptableMeasurements = false;
                }
            }
        }
        if(acceptableMeasurements == false){
            request.setAttribute("status", "One or more entered measurements is not accepted!");
            return "addstandardcarportpage";
        }
        try {
            standardCarportFacade.insertStandardCarport(carport);
            request.setAttribute("status", "Standard carport added!");
        } catch (UserException e) {
            e.printStackTrace();
        }

        //Add to std carport list
        try {
            FrontController.standardCarports = standardCarportFacade.getStandardCarports();
        } catch (UserException e) {
            e.printStackTrace();
        }

        //Update app scope
        request.getServletContext().setAttribute("standardCarports", FrontController.standardCarports);

        return pageToShow;
    }
}
