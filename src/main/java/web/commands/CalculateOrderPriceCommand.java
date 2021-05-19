package web.commands;

import business.entities.Order;
import business.entities.OrderLine;
import business.exceptions.UserException;
import business.services.OrderFacade;
import business.services.OrderLineFacade;
import com.sun.org.apache.xpath.internal.operations.Or;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.TreeMap;

public class CalculateOrderPriceCommand extends CommandProtectedPage {
    public CalculateOrderPriceCommand(String pageToShow, String role) {
        super(pageToShow, role);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws UserException {
        OrderFacade orderFacade = new OrderFacade(database);
        OrderLineFacade orderLineFacade = new OrderLineFacade(database);


        //Get required attributes
        int orderId = Integer.parseInt(request.getParameter("orderid"));
        Order order = orderFacade.getOrderByOrderId(orderId);
        //Create map for displaying && fetch from DB
        TreeMap<Integer, OrderLine> BOM = orderLineFacade.getOrderLinesByOrderId(orderId);
        //Set BOM at order object
        order.setBOM(BOM);
        //Calculate values for display
        order.calculateCostPrice();


        if (request.getParameter("totalPrice") != null && request.getParameter("avance") != null) {
            request.setAttribute("error", "Du kan ikke både udfylde avance og totalpris da disse beregninger er afhængige af hinanden - Vi har prioriteret beregningen udfra avancen");
        }
        if (request.getParameter("avance") != null) {
            double avance = Double.parseDouble(request.getParameter("avance"));
            order.calculateTotalPrice(avance);
        } else if (request.getParameter("totalPrice") != null) {
            double totalPrice = Double.parseDouble(request.getParameter("totalPrice"));
            order.setTotalPrice(totalPrice);
        }

        //Write to DB
        orderFacade.updateOrderTotalPrice(orderId,order.getTotalPrice());

        return new ShowOrderLineCommand(pageToShow, role).execute(request, response);
    }
}
