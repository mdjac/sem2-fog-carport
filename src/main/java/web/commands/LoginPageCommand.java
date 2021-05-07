package web.commands;

import business.exceptions.UserException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginPageCommand extends CommandUnprotectedPage{
    public LoginPageCommand(String pageToShow) {
        super(pageToShow);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws UserException {
        if(request.getParameter("standardCarportId") != null){
            int standardCarportId = Integer.parseInt(request.getParameter("standardCarportId"));
            request.getSession().setAttribute("standardCarportId",standardCarportId);
        }
        return pageToShow;
    }
}
