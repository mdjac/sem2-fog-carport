package web.commands;

import business.entities.OrderLine;
import business.entities.User;
import business.exceptions.UserException;
import business.services.OrderLineFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.TreeMap;

public class ShowOrderLineCommand extends CommandProtectedPage{
    public ShowOrderLineCommand(String pageToShow, String role) {
        super(pageToShow, role);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws UserException {
        OrderLineFacade orderLineFacade = new OrderLineFacade(database);

        //Get required attributes
        int orderId = Integer.parseInt(request.getParameter("orderid"));

        //Create map for displaying && fetch from DB
        TreeMap<Integer, OrderLine> BOM = orderLineFacade.getOrderLinesByOrderId(orderId);

        //Set in request scope
        request.setAttribute("BOM",BOM);
        return pageToShow;
    }
}
