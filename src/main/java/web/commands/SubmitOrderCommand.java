package web.commands;

import business.entities.Carport;
import business.entities.Order;
import business.entities.Status;
import business.entities.User;
import business.exceptions.UserException;
import business.services.OrderFacade;
import web.FrontController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
        //Til custom carport
        else{

        }

        //Opret ordre
        Order order = new Order(Status.Request,20.0, user.getId(), carport);


        //Skriv ordre til DB
        OrderFacade orderFacade = new OrderFacade(database);
        try {
            orderFacade.insertOrder(order,carport);
        } catch (UserException e) {
            //Something went wrong when inserting to DB!
            request.setAttribute("error", e.getMessage());
            return pageToShow;
        }

        //TODO:Beregn stykliste

        //TODO: Skriv til order_line

        //TODO: Giv kvittering med ordrenummer

        return pageToShow;
    }
}
