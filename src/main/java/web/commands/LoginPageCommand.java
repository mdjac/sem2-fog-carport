package web.commands;

import business.entities.Carport;
import business.entities.RoofType;
import business.exceptions.UserException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginPageCommand extends CommandUnprotectedPage{
    public LoginPageCommand(String pageToShow) {
        super(pageToShow);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws UserException {
        if(request.getParameter("standardCarportId") != null){
            int standardCarportId = Integer.parseInt(request.getParameter("standardCarportId"));
            request.getSession().setAttribute("standardCarportId",standardCarportId);
        } /*else if (request.getParameter(carportmaterial)){

            //TODO this i connected to custom carport
            //Fetch required variables
            RoofType roofType = RoofType.fromString(request.getParameter("rooftype"));
            request.getSession().setAttribute("rooftype", roofType);
            int roofMaterialId = Integer.parseInt(request.getParameter("roofmaterial"));
            request.getSession().setAttribute("roofmaterial", roofMaterialId);
            int carportMaterialId = Integer.parseInt(request.getParameter("carportmaterial"));
            request.getSession().setAttribute("carportmaterial", carportMaterialId);
            int carportLength = Integer.parseInt(request.getParameter("carportlength"));
            request.getSession().setAttribute("carportlength", carportLength);
            int carportWidth = Integer.parseInt(request.getParameter("carportwidth"));
            request.getSession().setAttribute("carportwidth", carportWidth);
            int carportHeight = Integer.parseInt(request.getParameter("carportheight"));
            request.getSession().setAttribute("carportheight", carportHeight);
            //Only tries to getParameter if the value isn't empty as not all carports have tilt
            Integer roofTilt = null;
            if (!request.getParameter("rooftilt").isEmpty()) {
                roofTilt = Integer.parseInt(request.getParameter("rooftilt"));
                request.getSession().setAttribute("rooftilt", roofTilt);
            }
            //Only tries to getParameters if shed is choosen
            Integer shedMaterialId = null;
            Integer shedLength = null;
            Integer shedWidth = null;
            if (request.getParameter("chooseshed").equals("ja")) {
                shedMaterialId = Integer.parseInt(request.getParameter("shedmaterial"));
                request.getSession().setAttribute("shedmaterial", shedMaterialId);
                shedLength = Integer.parseInt(request.getParameter("shedlength"));
                request.getSession().setAttribute("shedlength", shedLength);
                shedWidth = Integer.parseInt(request.getParameter("shedwidth"));
                request.getSession().setAttribute("shedwidth", shedWidth);
            }
        }*/
        return pageToShow;
    }
}
