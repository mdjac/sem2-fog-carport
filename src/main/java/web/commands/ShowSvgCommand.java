package web.commands;
import business.entities.User;
import business.exceptions.UserException;
import business.services.OrderFacade;
import business.services.SVG;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ShowSvgCommand extends CommandUnprotectedPage {

    public ShowSvgCommand(String pageToShow) {
        super(pageToShow);
    }
    boolean hasShed = false;
    boolean hasRoof = false;
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws UserException
    {
        // ????
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        OrderFacade orderFacade = new OrderFacade(database);

        SVG svg = new SVG(0, 0, "0 0 600 600", 100, 150 );


        // optegning af ramme
            svg.addLine(75,300,500,300);
            svg.addLine(75,0,75,300);
            svg.addText(30,150,300); // <-- hent width fra db
            svg.addText(250,320,300); // <-- hent carport length fra db
        //
        //remme
        svg.addRect(100,25,7,305);
        svg.addRect(100,243,7,305);


        //stolper
        svg.addRect(146,24,9,12); // øverst forrest
        svg.addRect(256,24,9,12); // øverst midterst
        svg.addRect(380,24,9,12); // øverst bagerst
        svg.addRect(146,242,9,12); // nederst forrest
        svg.addRect(256,242,9,12); // nederst midterst
        svg.addRect(380,242,9,12); // nederst forrest
        //stærbrædder
        svg.addLine(100,14,405,14);
        svg.addLine(100,260,405,260);


        if(hasShed = false){
            //hvis der er et skur
            svg.addRect(310,136,9,12); // midterst forrest
            svg.addRect(380,136,9,12); // midterst bagerst
            svg.addRect(310,24,9,12); // midterst bagerst
            svg.addRect(310,242,9,12); // midterst bagerst
            svg.addShedLine(310,34,310,242);
            svg.addShedLine(392,34,392,242);
            svg.addShedLine(310,34,392,34);
            svg.addShedLine(310,242,392,242);
            //hulbånd hvis der er skur
            svg.addDottedLine(104,31,300,243);
            svg.addDottedLine(104,244,300,31);

        }else{
            //hulbånd uden skur
            svg.addDottedLine(104,31,400,243);
            svg.addDottedLine(104,244,400,31);
        }

        if(!hasRoof){
            svg.addRect(146,133,9,226); // rejsning midterst
            svg.addLine(100,15,146,136);    // øverst forrest rejsning
            svg.addLine(100,260,146,140);   // nederst forrest rejsning
            svg.addLine(100,15,100,260);
            svg.addLine(405,15,373,135); // øverst bagende rejsning
            svg.addLine(405,260,373,140);// nederst bagende rejsning
            svg.addLine(405,15,405,260);

        }else {
            //spær
            for (int x = 0; x < 7; x++)
            {
                svg.addRect(100 + 50 * x, 15, 245, 4.5);
            }
            for (int j = 0; j < 6; j++){
                svg.addText(130+50*j,12,55);
            }
        }


        request.setAttribute("svgdrawing", svg.toString());
        return pageToShow;
    }
}
