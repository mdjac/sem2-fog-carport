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
    int remmeWidth;
    int garageHeight;
    int spaerDistanceInbetween;
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws UserException
    {
        // ????
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        OrderFacade orderFacade = new OrderFacade(database);
        SVG svg = new SVG(75, 40, "0 -30 600 600", 100, 100 );


        // optegning af ramme med text
            svg.addLine(75,15,75,260); // vertical < hent højde/bredde fra db
            svg.addLine(75,15,85,15); // set linje top
            svg.addLine(75,260,85,260); // set linje bund
            svg.addLine(100,280,405,280); // horizontal < hent højde/bredde fra db
            svg.addLine(100,270,100,280); // set linje venstre
            svg.addLine(405,270,405,280); // set linje højre

            svg.addText(30,150,300); // <-- hent width fra db
            svg.addText(250,320,300); // <-- hent carport length fra db

        //


        //remme
        svg.addRect(100,25,7,300); //remmewidth
        svg.addRect(100,243,7,300); //remmewidth


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


        if(hasShed = true){
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
            //sideview shed
            svg.addShedLine(305,400,305,500); // left side
            svg.addShedLine(305,500,390,500); //bottom
            svg.addShedLine(305,400,390,400); // top
            svg.addShedLine(390,400,390,500); // right side
            for (int i = 0; i < 18; i++) {
                svg.addRect(305+5*i,400,100,1);

            }
        }else{
            //hulbånd uden skur
            svg.addDottedLine(104,31,400,243);
            svg.addDottedLine(104,244,400,31);
        }

        if(hasRoof){
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
                svg.addRect(100 + 50 * x, 15, 245, 4.5); // x == spaerDistanceInbetween, height = carportBredde
            }
            for (int j = 0; j < 6; j++){
                svg.addText(130+50*j,5,55);
                svg.addLine(110+50*j,8,147+50*j,8);//flad linje
                svg.addLine(110+50*j,0,110+50*j,12);//venstre linje
                svg.addLine(147+50*j,0,147+50*j,12); // højre linje


            }
        }

        svg.addLine(75,380,75,500);
        svg.addLine(100,520,400,520);

        svg.addLine(100,510,138,510); // 1 meter inde
        svg.addLine(145,510,257,510); // afstand mellem første stolpe on 2.
        svg.addLine(266,510,300,510); // afstand mellem 2. stolpe og skur
        svg.addLine(308,510,,510); // skur længde
        svg.addText(30,450,300); // <-- hent width fra db
        svg.addText(250,535,300); // <-- hent carport length fra db
        //sideview
        svg.addRect(100,380,20,300);
        svg.addRect(140,400,100,4.5);
        svg.addRect(260,400,100,4.5);
        svg.addRect(375,400,100,4.5);



        request.setAttribute("svgdrawing", svg.toString());
        return pageToShow;
    }



}
