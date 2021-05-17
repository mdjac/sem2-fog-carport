package web.commands;

import business.entities.OrderLine;
import business.exceptions.UserException;
import business.services.OrderLineFacade;

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
        int orderId = Integer.parseInt(request.getParameter("orderid"));
        request.setAttribute("orderid",orderId);
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
            String[] ids = request.getParameterValues("materialVariantId[]");
            String[] quantityArray = request.getParameterValues("quantity[]");
            String[] descriptionArray = request.getParameterValues("description[]");
            String[] orderlineidArray = request.getParameterValues("orderlineid[]");

        if (ids != null) {
            System.out.println("line 38: " + ids.length);
        }
        if (quantityArray != null) {
            System.out.println("line 41: " + quantityArray.length);
        }
        if (descriptionArray != null) {
            System.out.println("line 44: " + descriptionArray.length);
        }
        if (orderlineidArray != null) {
            System.out.println("line 44: " + orderlineidArray.length);
        }
        //Edit part


        return new ShowOrderLineCommand("showorderlinepage","employee").execute(request,response);
    }
}
