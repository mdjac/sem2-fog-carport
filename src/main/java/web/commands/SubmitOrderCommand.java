package web.commands;

import business.entities.Carport;
import web.FrontController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SubmitOrderCommand extends CommandProtectedPage{
    public SubmitOrderCommand(String pageToShow, String role) {
        super(pageToShow, role);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        //Opret carport objekt
        //til standard carport
        if (request.getParameter("standardCarportId") != null){
        Carport standardCarport =  FrontController.standardCarports.get(Integer.parseInt(request.getParameter("standardCarportId")));
            System.out.println(standardCarport.getCarportBeklædning());
            System.out.println(standardCarport.getCarportHøjde());
        }
        //Til custom carport
        else{

        }

        //Opret ordre




        //Skriv ordre til DB


        //Giv kvittering med ordrenummer

        return pageToShow;
    }
}
