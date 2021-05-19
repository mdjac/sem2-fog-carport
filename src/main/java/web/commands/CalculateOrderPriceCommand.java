package web.commands;

import business.entities.Order;
import business.exceptions.UserException;
import business.services.OrderFacade;
import com.sun.org.apache.xpath.internal.operations.Or;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CalculateOrderPriceCommand extends CommandProtectedPage {
    public CalculateOrderPriceCommand(String pageToShow, String role) {
        super(pageToShow, role);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws UserException {
        OrderFacade orderFacade = new OrderFacade(database);
        //Get required attributes
        int orderId = Integer.parseInt(request.getParameter("orderid"));
        Order order = orderFacade.getOrderByOrderId(orderId);
        if (request.getParameter("totalPrice") != null || request.getParameter("avance") != null) {
            if (request.getParameter("totalPrice") != null) {
                double totalPrice = Double.parseDouble(request.getParameter("totalPrice"));
                order.setTotalPrice(totalPrice);
                //order.calculateAvance();
            }
            if (request.getParameter("avance") != null) {
                double avance = Double.parseDouble(request.getParameter("avance"));
                order.calculateTotalPrice(avance);
            }
            //Write to DB
            orderFacade.updateOrderTotalPrice(orderId,order.getTotalPrice());
        }
        return new ShowOrderLineCommand(pageToShow, role).execute(request, response);
    }
}
