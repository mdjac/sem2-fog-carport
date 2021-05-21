package business.services;

import business.entities.SvgValues;
import web.FrontController;

public class ConstructSVG {

    public static String constructTopView(int carportId){
        SvgValues svgValue = FrontController.svgValuesTreeMap.get(carportId);

        int carportWidth = svgValue.getCarportHeight();
        int carportLenght = svgValue.getCarportLenght();
        int margin = 30;
        int ref = 75;

        double spærBredde = svgValue.getSpærMaterialeBredde();
        int spærAntal = svgValue.getSpærAntal(); //Alle Stolper inklusiv skur
        double spærMellemrum = svgValue.getSpærMellemrum();
        int remAfstandFraSider = 10;
        Double remBredde = svgValue.getRemMaterialeBredde();
        Double stolpeAfstandFront = svgValue.getStolpeAfstandFront();
        Double stolpeAfstandBag = svgValue.getStolpeAfstandBag();
        Double stolpeAfstand = svgValue.getStolpeAfstand();
        Double stolpeBredde = svgValue.getStolpeMaterialeBredde();
        int stolpeAntal = svgValue.getStolpeAntal();

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
        if (svgValue.getSkurBredde() != null){
            int stolperSkur = (svgValue.getSkurStolpeAntalSider()*2) + (svgValue.getSkurStolpeAntalFrontBag()*2) - 4;
            int resterendeStolper = stolpeAntal - stolperSkur;
            if (resterendeStolper > 2){
                int q = (resterendeStolper-2)/2;
                for (int i = 0; i < q; i++) {
                    if (i>0) {
                        svg.addRect(ref + stolpeAfstandFront + (stolpeAfstand * i), margin + remAfstandFraSider - (remBredde / 2), stolpeBredde, stolpeBredde); // øverst til højre
                        svg.addRect(ref + stolpeAfstandFront + (stolpeAfstand * i), margin + carportWidth - remAfstandFraSider - remBredde - (remBredde / 2), stolpeBredde, stolpeBredde); // nederst til højre
                    }
                    int xRef = (int) (ref+stolpeAfstandFront+(stolpeBredde/2) + (stolpeAfstand * i));
                    svg.addLine(xRef, margin+carportWidth+måleStregAfstand, (int) (xRef+stolpeAfstand), margin+carportWidth+måleStregAfstand);
                    svg.addLine(xRef, margin+carportWidth+måleStregAfstand+3, xRef, margin+carportWidth+måleStregAfstand-3);
                    svg.addLine((int) (xRef+stolpeAfstand), margin+carportWidth+måleStregAfstand-3, (int) (xRef+stolpeAfstand), margin+carportWidth+måleStregAfstand+3);
                    //tekst
                    svg.addText((int) (xRef+(stolpeAfstand/2)+stolpeBredde),margin + carportWidth + måleStregAfstand+13, stolpeAfstand);
                }
            }

        } else {
            int a = (stolpeAntal-2)/2;
            for (int i = 0; i < a; i++) {
                if (i>0) {
                    svg.addRect(ref + stolpeAfstandFront + (stolpeAfstand * i), margin + remAfstandFraSider - (remBredde / 2), stolpeBredde, stolpeBredde); // øverst til højre
                    svg.addRect(ref + stolpeAfstandFront + (stolpeAfstand * i), margin + carportWidth - remAfstandFraSider - remBredde - (remBredde / 2), stolpeBredde, stolpeBredde); // nederst til højre
                }

                int xRef = (int) (ref+stolpeAfstandFront+(stolpeBredde/2) + (stolpeAfstand * i));
                svg.addLine(xRef, margin+carportWidth+måleStregAfstand, (int) (xRef+stolpeAfstand), margin+carportWidth+måleStregAfstand);
                svg.addLine(xRef, margin+carportWidth+måleStregAfstand+3, xRef, margin+carportWidth+måleStregAfstand-3);
                svg.addLine((int) (xRef+stolpeAfstand), margin+carportWidth+måleStregAfstand-3, (int) (xRef+stolpeAfstand), margin+carportWidth+måleStregAfstand+3);
            }

        }

        //Mål venstre side
        int m = måleStregAfstand*2;
        svg.addLine(ref-m, margin, ref-m, margin+carportWidth);
        svg.addLine(ref-m-3, margin, ref-m+3, margin);
        svg.addLine(ref-m-3, margin+carportWidth, ref-m+3, margin+carportWidth);
        svg.addText(ref/2,(carportWidth/2)+margin, carportWidth);

        m = måleStregAfstand;
        svg.addLine(ref-m, margin+remAfstandFraSider, ref-m, margin+carportWidth-remAfstandFraSider);
        svg.addLine(ref-m-3, margin+remAfstandFraSider, ref-m+3, margin+remAfstandFraSider);
        svg.addLine(ref-m-3, margin+carportWidth-remAfstandFraSider, ref-m+3, margin+carportWidth-remAfstandFraSider);
        //TODO tekst skal drejes
        svg.addText(ref/2,(carportWidth/2)+margin+20, (carportWidth-remAfstandFraSider-remAfstandFraSider));

        //Mål i bunden
        int textMargin = margin*2;
        svg.addLine(ref, textMargin+carportWidth+måleStregAfstand, ref+carportLenght, textMargin+carportWidth+måleStregAfstand);
        svg.addLine(ref, textMargin+carportWidth+måleStregAfstand-3, ref, textMargin+carportWidth+måleStregAfstand+3);
        svg.addLine(ref+carportLenght, textMargin+carportWidth+måleStregAfstand-3, ref+carportLenght, textMargin+carportWidth+måleStregAfstand+3);
        svg.addText(ref+(carportLenght/2),textMargin+carportWidth+måleStregAfstand+15, carportLenght);

        //Any shed?
        if (svgValue.getSkurBredde() != null){
            Double stolpeAfstandSkurLængde = svgValue.getStolpeAfstandSkurSider();
            Double stolpeAfstandSkurBredde = svgValue.getStolpeAfstandSkurforOgBag();
            //inklusiv de 2 hjørne stolper
            int skurStolpeAntalLængde = svgValue.getSkurStolpeAntalSider();
            int skurStolpeAntalBredde = svgValue.getSkurStolpeAntalFrontBag();

            int skurLængde = svgValue.getSkurLængde();
            int skurBredde = svgValue.getSkurBredde();

            for (int i = 0; i < skurStolpeAntalLængde; i++) {
                //Tilføjer horizontalt
                //top
                //TODO add again
                if (i >=skurStolpeAntalLængde-1  && skurBredde == carportWidth){
                    svg.addRect(ref + carportLenght - stolpeAfstandBag - (stolpeAfstandSkurLængde * i)+stolpeBredde, margin + remAfstandFraSider - (remBredde / 2), stolpeBredde, stolpeBredde);
                    svg.addRect(ref + carportLenght - stolpeAfstandBag - (stolpeAfstandSkurLængde * i)+stolpeBredde, margin + skurBredde - remAfstandFraSider -(remBredde / 2)-remBredde , stolpeBredde, stolpeBredde);
                }else if (i >=skurStolpeAntalLængde-1){
                    svg.addRect(ref + carportLenght - stolpeAfstandBag - (stolpeAfstandSkurLængde * i)+stolpeBredde, margin + remAfstandFraSider - (remBredde / 2), stolpeBredde, stolpeBredde);
                    svg.addRect(ref + carportLenght - stolpeAfstandBag - (stolpeAfstandSkurLængde * i)+stolpeBredde, margin + skurBredde - stolpeBredde, stolpeBredde, stolpeBredde);
                    svg.addRect(ref + carportLenght - stolpeAfstandBag - (stolpeAfstandSkurLængde * i)+stolpeBredde, margin + carportWidth - remAfstandFraSider -(remBredde / 2)-remBredde , stolpeBredde, stolpeBredde);

                }else {
                    svg.addRect(ref + carportLenght - stolpeAfstandBag - (stolpeAfstandSkurLængde * i), margin + remAfstandFraSider - (remBredde / 2), stolpeBredde, stolpeBredde);
                    //TODO check denne under
                    if (skurBredde != carportWidth){
                        svg.addRect(ref + carportLenght - stolpeAfstandBag - (stolpeAfstandSkurLængde * i), margin + skurBredde - stolpeBredde, stolpeBredde, stolpeBredde);
                    }
                    svg.addRect(ref + carportLenght - stolpeAfstandBag - (stolpeAfstandSkurLængde * i), margin + carportWidth - remAfstandFraSider -(remBredde / 2)-remBredde , stolpeBredde, stolpeBredde);

                }

                //Målelinjer
                if (i < skurStolpeAntalLængde-2) {
                    int xRef = (int) Math.round(ref + carportLenght - stolpeAfstandBag - (stolpeAfstandSkurLængde * i)+(stolpeBredde/2));
                    svg.addLine((int) Math.round(xRef - stolpeAfstandSkurLængde), margin + carportWidth + måleStregAfstand, xRef, margin  + carportWidth + måleStregAfstand);
                    svg.addLine(xRef, margin + carportWidth + måleStregAfstand + 3, xRef, margin + carportWidth + måleStregAfstand - 3);
                    svg.addLine((int) Math.round(xRef - stolpeAfstandSkurLængde), margin + carportWidth + måleStregAfstand - 3, (int) Math.round(xRef - stolpeAfstandSkurLængde), margin + carportWidth + måleStregAfstand + 3);
                    //tekst
                    svg.addText((int) (xRef-(stolpeAfstandSkurLængde/2)+stolpeBredde),margin + carportWidth + måleStregAfstand+13, stolpeAfstandSkurLængde);
                } else if (i < skurStolpeAntalLængde-1) {
                    int xRef = (int) Math.round(ref + carportLenght - stolpeAfstandBag - (stolpeAfstandSkurLængde * i)+(stolpeBredde/2));
                    svg.addLine((int) Math.round(xRef - stolpeAfstandSkurLængde+stolpeBredde), margin + carportWidth + måleStregAfstand, xRef, margin + carportWidth + måleStregAfstand);
                    svg.addLine(xRef, margin + carportWidth + måleStregAfstand + 3, xRef, margin + carportWidth + måleStregAfstand - 3);
                    svg.addLine((int) Math.round(xRef - stolpeAfstandSkurLængde+stolpeBredde), margin + carportWidth + måleStregAfstand - 3, (int) Math.round(xRef - stolpeAfstandSkurLængde+stolpeBredde), margin + carportWidth + måleStregAfstand + 3);
                    //tekst
                    svg.addText((int) (xRef-(stolpeAfstandSkurLængde/2)+stolpeBredde),margin + carportWidth + måleStregAfstand+13, stolpeAfstandSkurLængde);
                }



            }

            for (int j = 1; j < skurStolpeAntalBredde; j++) {
                //Tilføjer stolper verticalt
                if (j >= skurStolpeAntalBredde-1 && skurBredde == carportWidth){
                    //Højre
                    svg.addRect(ref+carportLenght-stolpeAfstandBag,margin+(stolpeAfstandSkurBredde*j)- remAfstandFraSider -(remBredde / 2)-remBredde,stolpeBredde,stolpeBredde);
                    //venstre
                    svg.addRect(ref+carportLenght-stolpeAfstandBag-(skurLængde-stolpeBredde),margin+carportWidth-remAfstandFraSider-remBredde-(remBredde/2),stolpeBredde,stolpeBredde);

                } else if (j >= skurStolpeAntalBredde-1){
                    //Højre
                    svg.addRect(ref+carportLenght-stolpeAfstandBag,margin+(stolpeAfstandSkurBredde*j)-stolpeBredde,stolpeBredde,stolpeBredde);
                    //venstre
                    svg.addRect(ref+carportLenght-stolpeAfstandBag-(skurLængde-stolpeBredde),margin+(stolpeAfstandSkurBredde*j)-stolpeBredde,stolpeBredde,stolpeBredde);
                }else {

                    svg.addRect(ref+carportLenght-stolpeAfstandBag,margin+(stolpeAfstandSkurBredde*j),stolpeBredde,stolpeBredde);
                    svg.addRect(ref+carportLenght-stolpeAfstandBag-(skurLængde-stolpeBredde),margin+(stolpeAfstandSkurBredde*j),stolpeBredde,stolpeBredde);
                }
            }

            int temp = (int) Math.ceil((skurLængde+stolpeAfstandBag)/spærMellemrum);
            //Hulbånd
            svg.addDottedLine((int) (ref+spærMellemrum),margin+remAfstandFraSider, (int) (ref+carportLenght-(temp*spærMellemrum)),margin+carportWidth-remAfstandFraSider);
            svg.addDottedLine((int) (ref+spærMellemrum),margin+carportWidth-remAfstandFraSider, (int) (ref+carportLenght-(temp*spærMellemrum)),margin+remAfstandFraSider);

            svg.addRect(ref+carportLenght-stolpeAfstandBag-skurLængde+stolpeBredde,margin,2,skurLængde);
            svg.addRect(ref+carportLenght-stolpeAfstandBag-skurLængde+stolpeBredde,margin,skurBredde,2);
            svg.addRect(ref+carportLenght-stolpeAfstandBag+stolpeBredde,margin,skurBredde,2);
            svg.addRect(ref+carportLenght-stolpeAfstandBag-skurLængde+stolpeBredde,margin+skurBredde-2,2,skurLængde);
        } else {
            svg.addDottedLine((int) (ref+spærMellemrum),margin+remAfstandFraSider, (int) (ref+carportLenght-spærMellemrum),margin+carportWidth-remAfstandFraSider);
            svg.addDottedLine((int) (ref+spærMellemrum),margin+carportWidth-remAfstandFraSider, (int) (ref+carportLenght-spærMellemrum),margin+remAfstandFraSider);
        }


        //remme
        svg.addRect(ref,margin+remAfstandFraSider,remBredde,carportLenght); //remmewidth
        svg.addRect(ref,margin+carportWidth-remAfstandFraSider-remBredde,remBredde,carportLenght); //remmewidth

        //spær
        for (int x = 0; x < spærAntal; x++)
        {
            int xRef = (int) (ref + (x * spærMellemrum));

            //Måle Linjer
            if (x < spærAntal) {
                svg.addLine(xRef, margin - måleStregAfstand, (int) (xRef + spærMellemrum), margin - måleStregAfstand);
                svg.addLine(xRef, margin - måleStregAfstand+3, xRef, margin - måleStregAfstand -3);
                svg.addLine((int) (xRef + spærMellemrum), margin - måleStregAfstand-3, (int) (xRef + spærMellemrum), margin - måleStregAfstand+3);
                svg.addText((int) (xRef+(spærMellemrum/2)),10, spærMellemrum);
            }
            if (x <spærAntal-1) {
                svg.addRect(xRef, margin, carportWidth, spærBredde); // x == spaerDistanceInbetween, height = carportBredde
            }
        }
        svg.addRect((int) (ref+carportLenght-spærBredde), margin, carportWidth, spærBredde);





        SVG svg1 = new SVG(0, 0, "0 0 1500 1000", 100, 100 );
        svg1.addSvg(svg);

        return svg1.toString();
    }


    public static String constructSideView(int carportId){
        SvgValues svgValue = FrontController.svgValuesTreeMap.get(carportId);


        int carportLenght = svgValue.getCarportLenght();
        int carportHeight = svgValue.getCarportHeight();
        int margin = 30;
        int ref = 75;

        double spærBredde = svgValue.getSpærMaterialeBredde();
        int spærAntal = svgValue.getSpærAntal(); //Alle Stolper inklusiv skur
        double spærMellemrum = svgValue.getSpærMellemrum();
        int remAfstandFraSider = 10;
        Double remBredde = svgValue.getRemMaterialeBredde();
        Double stolpeAfstandFront = svgValue.getStolpeAfstandFront();
        Double stolpeAfstandBag = svgValue.getStolpeAfstandBag();
        Double stolpeAfstand = svgValue.getStolpeAfstand();
        Double stolpeBredde = svgValue.getStolpeMaterialeBredde();
        int stolpeAntal = svgValue.getStolpeAntal();

        int måleStregAfstand = margin/3;
        //Tilføj remme højde og spær højde
        SVG svg = new SVG(0, 0, "0 0 "+(carportLenght+ref)+" "+(carportHeight+20+(margin*2)), 100, 100 );


        //Carport Tag
        //TODO akkumuler spær højde og rem højde
        double tagHøjde = 40;
        svg.addRect(ref,margin,tagHøjde,carportLenght);

        //Stolper
        svg.addRect(ref+stolpeAfstandFront,margin+tagHøjde,carportHeight,stolpeBredde); // øverst til venstre
        svg.addRect(ref+carportLenght-stolpeAfstandBag,margin+tagHøjde,carportHeight,stolpeBredde); // nederst til højre

       //Vi tjekker om den er større end 2 fordi vi allerede har de 2 forreste stolper
        if (svgValue.getSkurBredde() != null){
            int stolperSkur = (svgValue.getSkurStolpeAntalSider()*2) + (svgValue.getSkurStolpeAntalFrontBag()*2) - 4;
            int resterendeStolper = stolpeAntal - stolperSkur;
            if (resterendeStolper > 2){
                int q = (resterendeStolper-2)/2;
                for (int i = 0; i < q; i++) {
                    if (i>0) {
                        svg.addRect(ref + stolpeAfstandFront + (stolpeAfstand * i), margin+tagHøjde, carportHeight, stolpeBredde); // øverst til højre
                    }
                    /*int xRef = (int) (ref+stolpeAfstandFront+(stolpeBredde/2) + (stolpeAfstand * i));
                    svg.addLine(xRef, margin+carportWidth+måleStregAfstand, (int) (xRef+stolpeAfstand), margin+carportWidth+måleStregAfstand);
                    svg.addLine(xRef, margin+carportWidth+måleStregAfstand+3, xRef, margin+carportWidth+måleStregAfstand-3);
                    svg.addLine((int) (xRef+stolpeAfstand), margin+carportWidth+måleStregAfstand-3, (int) (xRef+stolpeAfstand), margin+carportWidth+måleStregAfstand+3);
                    //tekst
                    svg.addText((int) (xRef+(stolpeAfstand/2)+stolpeBredde),margin + carportWidth + måleStregAfstand+13, stolpeAfstand);*/
                }
            }

        } else {
            int a = (stolpeAntal-2)/2;
            for (int i = 0; i < a; i++) {
                if (i>0) {
                    svg.addRect(ref + stolpeAfstandFront + (stolpeAfstand * i), margin+tagHøjde, carportHeight, stolpeBredde); // øverst til højre

                }

                /*int xRef = (int) (ref+stolpeAfstandFront+(stolpeBredde/2) + (stolpeAfstand * i));
                svg.addLine(xRef, margin+carportWidth+måleStregAfstand, (int) (xRef+stolpeAfstand), margin+carportWidth+måleStregAfstand);
                svg.addLine(xRef, margin+carportWidth+måleStregAfstand+3, xRef, margin+carportWidth+måleStregAfstand-3);
                svg.addLine((int) (xRef+stolpeAfstand), margin+carportWidth+måleStregAfstand-3, (int) (xRef+stolpeAfstand), margin+carportWidth+måleStregAfstand+3);*/
            }

        }

      /*  //Mål venstre side
        int m = måleStregAfstand*2;
        svg.addLine(ref-m, margin, ref-m, margin+carportWidth);
        svg.addLine(ref-m-3, margin, ref-m+3, margin);
        svg.addLine(ref-m-3, margin+carportWidth, ref-m+3, margin+carportWidth);
        svg.addText(ref/2,(carportWidth/2)+margin, carportWidth);

        m = måleStregAfstand;
        svg.addLine(ref-m, margin+remAfstandFraSider, ref-m, margin+carportWidth-remAfstandFraSider);
        svg.addLine(ref-m-3, margin+remAfstandFraSider, ref-m+3, margin+remAfstandFraSider);
        svg.addLine(ref-m-3, margin+carportWidth-remAfstandFraSider, ref-m+3, margin+carportWidth-remAfstandFraSider);
        //TODO tekst skal drejes
        svg.addText(ref/2,(carportWidth/2)+margin+20, (carportWidth-remAfstandFraSider-remAfstandFraSider));

        //Mål i bunden
        int textMargin = margin*2;
        svg.addLine(ref, textMargin+carportWidth+måleStregAfstand, ref+carportLenght, textMargin+carportWidth+måleStregAfstand);
        svg.addLine(ref, textMargin+carportWidth+måleStregAfstand-3, ref, textMargin+carportWidth+måleStregAfstand+3);
        svg.addLine(ref+carportLenght, textMargin+carportWidth+måleStregAfstand-3, ref+carportLenght, textMargin+carportWidth+måleStregAfstand+3);
        svg.addText(ref+(carportLenght/2),textMargin+carportWidth+måleStregAfstand+15, carportLenght);*/

        //Any shed?
        if (svgValue.getSkurBredde() != null){

            int skurBredde = svgValue.getSkurBredde();
            int beklædningBredde = 20;
            int antalBeklædning=skurBredde/beklædningBredde;

            for (int i = 0; i < antalBeklædning; i++) {




            }

            //int temp = (int) Math.ceil((skurLængde+stolpeAfstandBag)/spærMellemrum);

        }





        SVG svg1 = new SVG(0, 0, "0 0 1500 1000", 100, 100 );
        svg1.addSvg(svg);

        return svg1.toString();
    }
}
