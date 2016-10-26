package controllers;

import dao.DaoFactory;
import models.City;
import org.apache.log4j.Logger;
import util.ParamsConverter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/ActionServlet")
public class ActionServlet extends HttpServlet {

    static Logger logger = Logger.getLogger(ActionServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        String params = req.getParameter("params");

        logger.info("Invoke getData request...");

        ParamsConverter converter = new ParamsConverter(params);

        HashMap<String, City> result = DaoFactory.getInstance().getPersonsDao().getData(converter);
        if (result.size() != 0) {
            resp.getWriter().write("</br>" + result.toString());
        } else {
            resp.getWriter().write("</br>Data not found!");
        }
    }
}
