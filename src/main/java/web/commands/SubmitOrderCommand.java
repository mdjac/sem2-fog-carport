package web.commands;

import business.entities.*;
import business.exceptions.UserException;
import business.services.OrderFacade;
import business.services.OrderLineFacade;
import business.utilities.Calculator;
import web.FrontController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;

public class SubmitOrderCommand extends CommandProtectedPage{
    public SubmitOrderCommand(String pageToShow, String role) {
        super(pageToShow, role);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws UserException {
        HttpSession session = request.getSession();
        //Opret user
        User user = (User) session.getAttribute("user");

        //Opret carport objekt
        Carport carport = null;
        //til standard carport
        if (request.getParameter("standardCarportId") != null){
         carport =  FrontController.standardCarports.get(Integer.parseInt(request.getParameter("standardCarportId")));
        }
        //In case the previous page was loginpage, standardCarportId is located in getAttribute instead of getParameter
        else if (request.getAttribute("standardCarportId") != null){
            carport =  FrontController.standardCarports.get((int)request.getAttribute("standardCarportId"));
        }
        //For custom carport
        else{
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
            if(carport.acceptableMeasurements() == false){
                request.setAttribute("status", "One or more entered measurements is not accepted!");
                return "customizedcarportorderpage";
            }
        }

        //Opret ordre
        Order order = new Order(Status.Request,20.0, user.getId(), carport);


        //Skriv ordre til DB
        OrderFacade orderFacade = new OrderFacade(database);
        try {
            //Tilføj ordre så vi kan presentere ordrenummer for brugeren.
            order = orderFacade.insertOrder(order,carport);
            request.setAttribute("order",order);
        } catch (UserException e) {
            //Something went wrong when inserting to DB!
            request.setAttribute("error", e.getMessage());
            return pageToShow;
        }

        //TODO:Beregn stykliste
        OrderLineFacade orderLineFacade = new OrderLineFacade(database);
        ArrayList<OrderLine> bom = Calculator.calculateBOM(carport,order);
        bom.forEach(x -> {
            try {
                //TODO: Skriv til order_line
                orderLineFacade.insertOrderLine(x);
            } catch (UserException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        return pageToShow;
    }
}
