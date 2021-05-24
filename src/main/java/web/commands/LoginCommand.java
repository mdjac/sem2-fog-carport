package web.commands;

import business.entities.User;
import business.services.UserFacade;
import business.exceptions.UserException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginCommand extends CommandUnprotectedPage
{
    private UserFacade userFacade;

    public LoginCommand(String pageToShow)
    {
        super(pageToShow);
        userFacade = new UserFacade(database);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws UserException
    {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        try {
        User user = userFacade.login(email, password);

        HttpSession session = request.getSession();

        session.setAttribute("user", user);
        session.setAttribute("role", user.getRole());
        session.setAttribute("email", email);


            //Made  to ensure the flow continues from shoppingcart to paymentpage
            if (session.getAttribute("link") != null && session.getAttribute("link").equals("/fc/loginpage") && user.getRole().equals("customer") && session.getAttribute("standardCarportId") != null) {
                    int standardCarportId = (int)session.getAttribute("standardCarportId");
                    request.setAttribute("standardCarportId",standardCarportId);

                    //Redirects user to submitorderCommand as we need want to submit the order
                    SubmitOrderCommand submitOrderCommand = new SubmitOrderCommand("receiptpage","customer");
                    return submitOrderCommand.execute(request,response);
            }
        //String pageToShow =  user.getRole() + "page";
            String pageToShow = "index";
        return REDIRECT_INDICATOR + pageToShow;
        }
        catch (UserException ex)
        {
            request.setAttribute("error", "Wrong username or password!");
            return "loginpage";
        }
    }

}
