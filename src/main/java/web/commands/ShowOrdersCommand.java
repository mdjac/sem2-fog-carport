package web.commands;

import business.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ShowOrdersCommand extends CommandUnprotectedPage {

    public ShowOrdersCommand(String pageToShow) {
        super(pageToShow);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");

        if (user.getRole().equals("employee")) {
            //TODO make logic to get all orders

            //
            return "adminallorderspage";
        }
        else{
            //TODO make logic to get all orders by user ID
            return pageToShow;
        }
    }
}
