package web.commands;
import business.entities.User;
import business.exceptions.UserException;
import business.services.OrderFacade;
import business.services.SVG;
import sun.reflect.generics.tree.Tree;
import web.FrontController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.TreeMap;


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

        int carportWidth = 300;
        int carportLenght = 480;
        int margin = 30;
        int ref = 75;

        double spærBredde = 3.0;
        int spærAntal = 9;
        double spærAfstand = carportLenght/spærAntal;
        int remAfstandFraSider = 10;
        int remBredde = 5;
        int stolpeAfstandFront = 100;
        int stolpeAfstandBag = 20;
        double stolpeAfstand = 100;
        int stolpeBredde = 9;

        int stolpeAntalSkur = 4;
        int stolpeAntal = 4;
        int måleStregAfstand = margin/3;

        SVG svg = new SVG(0, 0, "0 0 "+(carportLenght+ref)+" "+(carportWidth+(margin*2)), 100, 100 );

        //Carport sider
        svg.addRect(ref,margin,carportWidth,carportLenght);

        //Stolper
        svg.addRect(ref+stolpeAfstandFront,margin+remAfstandFraSider-(remBredde/2),stolpeBredde,stolpeBredde); // øverst til venstre
        svg.addRect(ref+stolpeAfstandFront,margin+carportWidth-remAfstandFraSider-remBredde-(remBredde/2),stolpeBredde,stolpeBredde); // nederst til venstre

        svg.addRect(ref+carportLenght-stolpeAfstandBag,margin+remAfstandFraSider-(remBredde/2),stolpeBredde,stolpeBredde); // øverst til højre
        svg.addRect(ref+carportLenght-stolpeAfstandBag,margin+carportWidth-remAfstandFraSider-remBredde-(remBredde/2),stolpeBredde,stolpeBredde); // nederst til højre

        //Vi tjekker om den er større end 2 fordi vi allerede har de 2 forreste stolper
        if (true && stolpeAntal > 2){
            for (int i = 1; i <=(stolpeAntal-2)/2 ; i++) {
                int refX = (int) (ref+stolpeAfstandFront+(stolpeAfstand*i));
                svg.addRect(refX,margin+remAfstandFraSider-(remBredde/2),stolpeBredde,stolpeBredde);
                svg.addRect(refX,margin+carportWidth-remAfstandFraSider-remBredde-(remBredde/2),stolpeBredde,stolpeBredde);
            }
        }

        //Mål venstre side
        svg.addLine(ref-måleStregAfstand, margin, ref-måleStregAfstand, margin+carportWidth);
        svg.addLine(ref-måleStregAfstand-3, margin, ref-måleStregAfstand+3, margin);
        svg.addLine(ref-måleStregAfstand-3, margin+carportWidth, ref-måleStregAfstand+3, margin+carportWidth);
        svg.addText(ref/2,(carportWidth/2)+margin, carportWidth);

        //Mål i bunden
        svg.addLine(ref, margin+carportWidth+måleStregAfstand, ref+carportLenght, margin+carportWidth+måleStregAfstand);
        svg.addLine(ref, margin+carportWidth+måleStregAfstand-3, ref, margin+carportWidth+måleStregAfstand+3);
        svg.addLine(ref+carportLenght, margin+carportWidth+måleStregAfstand-3, ref+carportLenght, margin+carportWidth+måleStregAfstand+3);
        svg.addText(ref+(carportLenght/2),margin+carportWidth+måleStregAfstand+15, carportLenght);

        //Any shed?
        if (true){
            int stolpeAfstandSkurLængde = 75;
            int stolpeAfstandSkurBredde = carportWidth/2-(remAfstandFraSider*1)-(stolpeBredde/2);
            //inklusiv de 2 hjørne stolper
            int skurStolpeAntalLængde = 3;
            int skurLængde = (skurStolpeAntalLængde-1)*stolpeAfstandSkurLængde;

            for (int i = 0; i < skurStolpeAntalLængde; i++) {
                //Tilføjer stolper sider
                //top
                svg.addRect(ref+carportLenght-stolpeAfstandBag-((stolpeAfstandSkurLængde-stolpeBredde)*i),margin+remAfstandFraSider-(remBredde/2),stolpeBredde,stolpeBredde);
                //bund
                svg.addRect(ref+carportLenght-stolpeAfstandBag-((stolpeAfstandSkurLængde-stolpeBredde)*i),margin+carportWidth-remAfstandFraSider-remBredde-(remBredde/2),stolpeBredde,stolpeBredde);

            }
            for (int j = 1; j <= 1; j++) {
                //Tilføjer stolper bredde
                //venstre
                svg.addRect(ref+carportLenght-stolpeAfstandBag-(skurLængde-stolpeBredde),margin+carportWidth-remAfstandFraSider-remBredde-(remBredde/2)-(stolpeAfstandSkurBredde*j),stolpeBredde,stolpeBredde);
                //højre
                svg.addRect(ref+carportLenght-stolpeAfstandBag,margin+carportWidth-remAfstandFraSider-remBredde-(remBredde/2)-(stolpeAfstandSkurBredde*j),stolpeBredde,stolpeBredde);

            }

            int temp = (int) Math.ceil((skurLængde+stolpeAfstandBag)/spærAfstand);
            //Hulbånd
            svg.addDottedLine((int) (ref+spærAfstand),margin+remAfstandFraSider, (int) (ref+carportLenght-(temp*spærAfstand)),margin+carportWidth-remAfstandFraSider);
            svg.addDottedLine((int) (ref+spærAfstand),margin+carportWidth-remAfstandFraSider, (int) (ref+carportLenght-(temp*spærAfstand)),margin+remAfstandFraSider);

            svg.addRect(ref+carportLenght-stolpeAfstandBag-skurLængde+stolpeBredde,margin+remAfstandFraSider,carportWidth-(remAfstandFraSider*2),2);
            svg.addRect(ref+carportLenght-stolpeAfstandBag+stolpeBredde,margin+remAfstandFraSider,carportWidth-(remAfstandFraSider*2),2);
        } else {
            svg.addDottedLine((int) (ref+spærAfstand),margin+remAfstandFraSider, (int) (ref+carportLenght-spærAfstand),margin+carportWidth-remAfstandFraSider);
            svg.addDottedLine((int) (ref+spærAfstand),margin+carportWidth-remAfstandFraSider, (int) (ref+carportLenght-spærAfstand),margin+remAfstandFraSider);
        }


        //remme
        svg.addRect(ref,margin+remAfstandFraSider,remBredde,carportLenght); //remmewidth
        svg.addRect(ref,margin+carportWidth-remAfstandFraSider-remBredde,remBredde,carportLenght); //remmewidth

        //spær
        for (int x = 0; x < spærAntal; x++)
        {
            int xRef = (int) (ref + (x * spærAfstand));

            //Måle Linjer
            if (x < spærAntal) {
                svg.addLine(xRef, margin - måleStregAfstand, (int) (xRef + spærAfstand), margin - måleStregAfstand);
                svg.addLine(xRef, margin - måleStregAfstand+3, xRef, margin - måleStregAfstand -3);
                svg.addLine((int) (xRef + spærAfstand), margin - måleStregAfstand-3, (int) (xRef + spærAfstand), margin - måleStregAfstand+3);
                svg.addText((int) (xRef+(spærAfstand/2)),10, spærAfstand);
            }
            svg.addRect(xRef, margin, carportWidth, spærBredde); // x == spaerDistanceInbetween, height = carportBredde
        }
        svg.addRect((int) (ref+carportLenght-spærBredde), margin, carportWidth, spærBredde);


        /*// optegning af ramme med text
            //Måle linje Vertical venstre
            svg.addLine(ref,margin,ref,carportWidth+margin);
            //Måle linje Vertical venstre nederst hak
            svg.addLine(ref,carportWidth+margin,ref+10,carportWidth+margin);
            //Måle linje Vertical venstre øverst hak
            svg.addLine(ref,margin,ref+10,margin);
            //Måle linje horizontal
            svg.addLine(ref+margin,carportWidth+margin,ref+margin+carportLenght,carportWidth+margin);
            //Text mål venstre
            svg.addText(margin*2,(carportWidth+margin)/2,carportWidth); // <-- hent width fra db
            //Text mål bund
            svg.addText(250,320,carportLenght); // <-- hent carport length fra db*/




       /* //remme
        svg.addRect(100,25,7,300); //remmewidth
        svg.addRect(100,243,7,300); //remmewidth


        //stolper
        svg.addRect(146,24,9,12); // øverst forrest
        svg.addRect(256,24,9,12); // øverst midterst
        svg.addRect(380,24,9,12); // øverst bagerst
        svg.addRect(146,242,9,12); // nederst forrest
        svg.addRect(256,242,9,12); // nederst midterst
        svg.addRect(380,242,9,12); // nederst forrest*/


/*

        if(hasShed = true){
            //hvis der er et skur
            */
/*svg.addRect(310,136,9,12); // midterst forrest
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
            svg.addShedLine(390,400,390,500); // right side*//*

            for (int i = 0; i < 18; i++) {
                //svg.addRect(305+5*i,400,100,1);

            }
        }else{
            //hulbånd uden skur
            svg.addDottedLine(104,31,400,243);
            svg.addDottedLine(104,244,400,31);
        }
*/
/*
        if(hasRoof){
            svg.addRect(146,133,9,226); // rejsning midterst
            svg.addLine(100,15,146,136);    // øverst forrest rejsning
            svg.addLine(100,260,146,140);   // nederst forrest rejsning
            svg.addLine(100,15,100,260);
            svg.addLine(405,15,373,135); // øverst bagende rejsning
            svg.addLine(405,260,373,140);// nederst bagende rejsning
            svg.addLine(405,15,405,260);

        }else {

            for (int j = 0; j < 6; j++){
                svg.addText(130+50*j,5,55);
                svg.addLine(110+50*j,8,147+50*j,8);//flad linje
                svg.addLine(110+50*j,0,110+50*j,12);//venstre linje
                svg.addLine(147+50*j,0,147+50*j,12); // højre linje


            }
        }*/
/*

        svg.addLine(75,380,75,500);
        svg.addLine(100,520,400,520);

        svg.addLine(100,510,138,510); // 1 meter inde
        svg.addLine(145,510,257,510); // afstand mellem første stolpe on 2.
        svg.addLine(266,510,300,510); // afstand mellem 2. stolpe og skur

        //TODO: 100 tilføjet til x2 skur længde pga compiler fejl ellers - Mikkel
        svg.addLine(308,510,100,510); // skur længde

        svg.addText(30,450,300); // <-- hent width fra db
        svg.addText(250,535,300); // <-- hent carport length fra db
        //sideview
        svg.addRect(100,380,20,300);
        svg.addRect(140,400,100,4.5);
        svg.addRect(260,400,100,4.5);
        svg.addRect(375,400,100,4.5);
*/

        SVG svg1 = new SVG(0, 0, "0 0 1500 1000", 100, 100 );
        svg1.addSvg(svg);
        System.out.println(svg.toString());
        request.setAttribute("svgdrawing", svg1.toString());
        return pageToShow;
    }



}
