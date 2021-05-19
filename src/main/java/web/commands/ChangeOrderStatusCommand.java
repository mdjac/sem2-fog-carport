package web.commands;

import business.entities.Status;
import business.exceptions.UserException;
import business.services.OrderFacade;
import com.sun.org.apache.xpath.internal.operations.Or;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeOrderStatusCommand extends CommandUnprotectedPage{
    public ChangeOrderStatusCommand(String pageToShow) {
        super(pageToShow);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws UserException {
        int orderid = Integer.parseInt(request.getParameter("orderid"));
        Status status = Status.fromString(request.getParameter("status"));

        //Opdater DB
        OrderFacade orderFacade = new OrderFacade(database);
        orderFacade.updateOrderStatus(orderid,status);

        return new ShowOrdersCommand(pageToShow).execute(request,response);
    }
}
