package controllers;

import model.PersonsDao;
import model.Person;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/ActionServlet")
public class ActionServlet extends HttpServlet {

    static Logger logger = Logger.getLogger(ActionServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        String person_params = req.getParameter("person_params");
        String city_params = req.getParameter("city_params");
        String car_params = req.getParameter("car_params");

        logger.info("Invoke getPersonsInfo request...");

        ArrayList<Person> persons = (ArrayList<Person>) PersonsDao.getInstance().getPersonsInfo(person_params, city_params, car_params);
        if (persons.size() != 0) {
            for (Person person : persons) {
                resp.getWriter().write("</br>" + person.toString());
            }
        } else {
            resp.getWriter().write("</br>Data not found!");
        }
    }
}
