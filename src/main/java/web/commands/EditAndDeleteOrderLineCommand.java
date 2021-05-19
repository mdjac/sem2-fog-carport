package web.commands;

import business.entities.Order;
import business.entities.OrderLine;
import business.exceptions.UserException;
import business.services.OrderFacade;
import business.services.OrderLineFacade;
import business.utilities.Calculator;
import web.FrontController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class EditAndDeleteOrderLineCommand extends CommandProtectedPage{
    public EditAndDeleteOrderLineCommand(String pageToShow, String role) {
        super(pageToShow, role);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws UserException {
        OrderLineFacade orderLineFacade = new OrderLineFacade(database);
        OrderFacade orderFacade = new OrderFacade(database);
        int orderId = Integer.parseInt(request.getParameter("orderid"));
        //Deletion part
            //Gets all delete ID's as an array
            String[] tmpDeleteIds = request.getParameterValues("deleteIds[]");
            //Converts all delete ID's to integer array
            List<Integer> deleteIds = new ArrayList<>();
            if(tmpDeleteIds != null){
                for (String tmp: tmpDeleteIds) {
                    deleteIds.add(Integer.parseInt(tmp));
                }
                int rowsAffected = orderLineFacade.deleteOrderLine(deleteIds);
            }
            try {
                String[] materialVariantArray = request.getParameterValues("materialVariantId[]");
                String[] quantityArray = request.getParameterValues("quantity[]");
                String[] descriptionArray = request.getParameterValues("description[]");
                String[] orderlineidArray = request.getParameterValues("orderlineid[]");
                String[] unitArray = request.getParameterValues("unit[]");
                //request.setAttribute("orderid",orderId);
                if (descriptionArray != null) {
                    for (int i = 0; i < descriptionArray.length; i++) {
                        OrderLine orderLine = new OrderLine(
                                Integer.parseInt(quantityArray[i]),
                                orderId,
                                unitArray[i],
                                Calculator.getMaterialByMaterialVariantId(Integer.parseInt(materialVariantArray[i])),
                                descriptionArray[i]);
                        orderLine.setId(Integer.parseInt(orderlineidArray[i]));
                        orderLineFacade.updateOrderline(orderLine);
                    }
                }
            } catch (Exception e) {
                throw new UserException(e.getMessage());
            }

        //Update order with new totalPrice after change in BOM
        Order order = orderFacade.getOrderByOrderId(orderId);
        order.setBOM(orderLineFacade.getOrderLinesByOrderId(orderId));
        order.calculateCostPrice();
        order.calculateTotalPrice(FrontController.priceCalculatorValues.get("ordreAvance").getValue());
        //Write new calculation to DB
        orderFacade.updateOrderTotalPrice(order.getId(),order.getTotalPrice());

        return new ShowOrderLineCommand("showorderlinepage","employee").execute(request,response);
    }
}
