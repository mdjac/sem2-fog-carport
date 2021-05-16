package web.commands;

import business.entities.Order;
import business.entities.User;
import business.exceptions.UserException;
import business.services.OrderFacade;
import com.sun.org.apache.xpath.internal.operations.Or;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.TreeMap;

public class ShowOrdersCommand extends CommandUnprotectedPage {

    public ShowOrdersCommand(String pageToShow) {
        super(pageToShow);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        OrderFacade orderFacade = new OrderFacade(database);
        TreeMap<Integer,Order> orders = new TreeMap<>();
        try {
            orders = orderFacade.getOrders(user);
        } catch (UserException e) {
            //TODO: Lav fejlhåndtering pænt
            e.printStackTrace();
        }
        request.setAttribute("orders", orders);
        return pageToShow;
    }
}
