package web.commands;

import business.entities.Order;
import business.entities.OrderLine;
import business.entities.Status;
import business.exceptions.UserException;
import business.services.ConstructSVG;
import business.services.OrderFacade;
import business.services.OrderLineFacade;
import business.utilities.Calculator;
import web.FrontController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.TreeMap;

public class ShowOrderPageCommand extends CommandProtectedPage{
    public ShowOrderPageCommand(String pageToShow, String role) {
        super(pageToShow, role);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws UserException {
        OrderLineFacade orderLineFacade = new OrderLineFacade(database);
        OrderFacade orderFacade = new OrderFacade(database);

        //Get required attributes
        int orderId = Integer.parseInt(request.getParameter("orderid"));

        Order order = orderFacade.getOrderByOrderId(orderId);

        if (order.getStatus().equals(Status.PickedUp)) {
            //Create map for displaying && fetch from DB
            TreeMap<Integer, OrderLine> BOM = orderLineFacade.getOrderLinesByOrderId(orderId);
            //Set BOM at order object
            order.setBOM(BOM);
        }
        if (!FrontController.svgValuesTreeMap.containsKey(order.getCarport().getId())){
            Calculator.calculateBOM(order.getCarport(),order);
        }
        String svgTopView = ConstructSVG.constructTopView(order.getCarport().getId());
        String svgSideView = ConstructSVG.constructSideView(order.getCarport().getId());
        request.setAttribute("svgdrawing",svgTopView);
        request.setAttribute("svgdrawingside",svgSideView);
        request.setAttribute("order", order);
        return pageToShow;
    }
}
