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
    public String execute(HttpServletRequest request, HttpServletResponse response) {
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
        //Til custom carport
        else{
            //TODO:
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
