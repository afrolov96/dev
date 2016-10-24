package controllers;

import model.AuthDao;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/SearchFilter")
public class SearchFilter implements Filter {
    static Logger logger = Logger.getLogger(SearchFilter.class);

    public void init(FilterConfig filterConfig) throws ServletException {    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession httpSession = ((HttpServletRequest) servletRequest).getSession(false);
        String username = servletRequest.getParameter("username");
        String password = servletRequest.getParameter("password");

        if(httpSession != null & httpSession.getAttribute("sessionUser") != null){
            logger.info("Auth READY for pair user/pass - " + username + " / " + password + ", sessionUser = " + httpSession.getAttribute("sessionUser"));
            filterChain.doFilter(servletRequest, servletResponse);
        } else {

            if(username == null || password == null){
                servletRequest.setAttribute("msg", "Login error.");
                logger.info("User or Password is empty...");
                ((HttpServletResponse)servletResponse).sendRedirect("login.jsp");
            } else {

                if (AuthDao.getInstance().checkAuth(username, password)) {
                    logger.info("Auth OK for pair user/password - " + username + " / " + password);
                    httpSession.setAttribute("sessionUser", servletRequest.getParameter("username"));
                    filterChain.doFilter(servletRequest, servletResponse);
                } else {

                    logger.info("Auth FAIL for pair user/pass - " + username + " / " + password);
                    servletRequest.setAttribute("msg", "Login error.");
                    ((HttpServletResponse)servletResponse).sendRedirect("login.jsp");
                }
            }
        }
    }

    public void destroy() {    }
}
