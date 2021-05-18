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

       
        if (descriptionArray != null) {
            for (int i = 0; i < descriptionArray.length; i++) {
                System.out.println("index orderline id: "+i + " Value: "+orderlineidArray[i]);
                System.out.println("index materialvariant: "+i + " Value: "+ids[i]);
                System.out.println("index quantity: "+i + " Value: "+quantityArray[i]);
                System.out.println("index describtion: "+i + " Value: "+descriptionArray[i]);
            }
        }

        //Edit part


        return new ShowOrderLineCommand("showorderlinepage","employee").execute(request,response);
    }
}
