package controllers;

import com.google.gson.Gson;
import dao.PersonsDao;
import org.apache.log4j.Logger;
import util.QueryRequestMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ActionServlet")
public class ActionServlet extends HttpServlet {
    private final static Logger logger = Logger.getLogger(ActionServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        QueryRequestMapper findRequest = QueryRequestMapper.getPersonFindRequestParams(req.getParameter("params"));

        logger.info("Invoke getData request...");
        Gson gson = new Gson();
        resp.setContentType("application/json; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(gson.toJson(PersonsDao.getInstance().getData(findRequest)));
    }
}
