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
                System.out.println("Order Lines delete: "+rowsAffected);
            }
            String[] ids = request.getParameterValues("materialVariantId[]");
        System.out.println("line 36: "+ ids.length);
        //Edit part


        return new ShowOrderLineCommand("showorderlinepage","employee").execute(request,response);
    }
}
