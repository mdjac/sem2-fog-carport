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
        }else if (request.getParameter("carportlength") != null){
            //Fetch required variables
            RoofType roofType = RoofType.valueOf(request.getParameter("rooftype"));
            int roofMaterialId = Integer.parseInt(request.getParameter("roofmaterial"));
            int carportMaterialId = Integer.parseInt(request.getParameter("carportmaterial"));
            int carportLength = Integer.parseInt(request.getParameter("carportlength"));
            int carportWidth = Integer.parseInt(request.getParameter("carportwidth"));
            int carportHeight = Integer.parseInt(request.getParameter("carportheight"));
            Carport carport = new Carport(Carport.findCarportMaterialFromId(carportMaterialId),carportWidth,carportHeight,carportLength,roofType,Carport.findRoofMaterialFromId(roofMaterialId,roofType));

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
            if(carport.acceptableMeasurements() == false){
                request.setAttribute("error", "One or more entered measurements is not accepted!");
                return "customizedcarportorderpage";
            }
            System.out.println("carport linje 52: "+carport);
            request.getSession().setAttribute("customCarport",carport);
        }
        return pageToShow;
    }
}
