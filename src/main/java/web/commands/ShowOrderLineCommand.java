package web.commands;

import business.entities.Order;
import business.entities.OrderLine;
import business.exceptions.UserException;
import business.services.OrderFacade;
import business.services.OrderLineFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.TreeMap;

public class ShowOrderLineCommand extends CommandProtectedPage{
    public ShowOrderLineCommand(String pageToShow, String role) {
        super(pageToShow, role);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws UserException {
        OrderLineFacade orderLineFacade = new OrderLineFacade(database);
        OrderFacade orderFacade = new OrderFacade(database);

        //Get required attributes
        int orderId = Integer.parseInt(request.getParameter("orderid"));
        Order order;
        //Used to check if the request is redirected from CalculateOrderPriceCommand, if not, collects from DB.
        if(request.getAttribute("orderFromCalculate") != null){
            order = (Order) request.getAttribute("orderFromCalculate");
        }else{
            order = orderFacade.getOrderByOrderId(orderId);
        }


        //Create map for displaying && fetch from DB
        TreeMap<Integer, OrderLine> BOM = orderLineFacade.getOrderLinesByOrderId(orderId);


        //Set BOM at order object
        order.setBOM(BOM);

        //Set in request scope
        //Calculate values for display
        order.calculateCostPriceByTreeMap(BOM);
        //order.calculateAvance();
        order.calculateTotalPrice(order.getAvance());
        request.setAttribute("order",order);
        return pageToShow;
    }
}
