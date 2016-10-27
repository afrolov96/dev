package controllers;

import dao.DaoFactory;
import dao.PersonsDao;
import models.Car;
import models.City;
import models.Person;
import org.apache.log4j.Logger;
import util.ParamsConverter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/ActionServlet")
public class ActionServlet extends HttpServlet {
    static Logger logger = Logger.getLogger(ActionServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Statement statement = DaoFactory.getInstance().getDataSource().getConnection().createStatement();
            ResultSet rs = statement.executeQuery("select name from cities");
            resp.setContentType("text/plain; charset=UTF-8");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("<select size=1>");
            resp.getWriter().write("<option value='xxx'></option>");
            while(rs.next()){
                resp.getWriter().write("<option value='" + rs.getString("name") + "'>" + rs.getString("name") + "</option>");
            }
            resp.getWriter().write("</select>");
        } catch (SQLException e) {
            logger.equals(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        String params = req.getParameter("params");

        logger.info("Invoke getData request...");

        ParamsConverter converter = new ParamsConverter(params);

        PersonsDao personsDao = DaoFactory.getInstance().getPersonsDao();
        HashMap<String, City> result = personsDao.getData(converter);
        if (result.size() != 0) {
            resp.getWriter().write("</br>" + printResult(result));
        } else {
            resp.getWriter().write("</br>Data not found!");
        }
    }

    private String printResult(HashMap<String, City> result) {
        StringBuilder sb = new StringBuilder();
        sb.append("<table border=1>");
        sb.append("<tr><td>City</td><td>Person</td><td>Car</td></tr>");
        for (Map.Entry<String, City> entryCity : result.entrySet()) {
            sb.append("<tr><td colspan=3>" + entryCity.getValue().getName() + "</td></tr>");
            for (Map.Entry<String, Person> entryPerson : entryCity.getValue().getPersonsMap().entrySet()) {
                //sb.append("<tr><td></td><td>" + entryPerson.getValue().getSurname() + " " + entryPerson.getValue().getName() + " " + entryPerson.getValue().getSurname() + "</td><td></td></tr>");
                sb.append("<tr><td></td><td colspan=2>" + entryPerson.getValue().getSurname() + " " + entryPerson.getValue().getName() + " " + entryPerson.getValue().getSurname() + "</td></tr>");
                for (Map.Entry<String, Car> entryCar : entryPerson.getValue().getCarsMap().entrySet()) {
                    sb.append("<tr><td colspan=2></td><td>" + entryCar.getValue().getName() + " " + entryCar.getValue().getColor() + " " + entryCar.getValue().getNumber() + "</td></tr>");
                }
            }
        }
        sb.append("</table>");
        return sb.toString();
    }
}
