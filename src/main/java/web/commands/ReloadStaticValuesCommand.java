package web.commands;

import business.exceptions.UserException;
import web.FrontController;
import web.StaticValues;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReloadStaticValuesCommand extends CommandProtectedPage{
    public ReloadStaticValuesCommand(String pageToShow, String role) {
        super(pageToShow, role);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws UserException {
        try{
            new StaticValues().setGlobalValues(database);
            //Used to populate the dropdown options on the form based on what FOG decides to offer in database
            request.getServletContext().setAttribute("categoryFormOptions", StaticValues.categoryFormOptions);
            //Used to populate the dropdown options on the form based on what FOG decides to offer in database
            request.getServletContext().setAttribute("standardCarports", StaticValues.standardCarports);
            //Used to make it possible for fog employee to modify BOM with other variants from the same materialID
            request.getServletContext().setAttribute("materialVariantMap",StaticValues.materialVariantMap);
            //Used to set which dimensions we allow for creation of new carports. eg. max length of the carport.
            request.getServletContext().setAttribute("allowedMeasurements",StaticValues.allowedMeasurements);
            request.setAttribute("status","Værdier og materialer er nu opdateret!");
        } catch (Exception e){
            request.setAttribute("error",e.getMessage());
        }
        return pageToShow;
    }
}
