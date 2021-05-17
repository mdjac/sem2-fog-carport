package web.commands;

import business.entities.Carport;
import business.exceptions.UserException;
import business.services.SVG;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ShowSvgCommand extends CommandUnprotectedPage {
    Carport carport;
    public ShowSvgCommand(String pageToShow) {
        super(pageToShow);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws UserException
    {
        SVG svg = new SVG(0, 0, "0 0 800 600", 100, 100 );

      /*  for (int x = 0; x < carport.; x++)
        {
            svg.addRect(100 + 50 * x, 0, 600.0, 4.5);
        }*/
        
        svg.addRect(100,100,carport.getCarportHeight(),carport.getCarportWidth());

        request.setAttribute("svgdrawing", svg.toString());
        return pageToShow;
    }
}
