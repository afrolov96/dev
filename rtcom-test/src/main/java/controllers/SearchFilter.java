package controllers;

import dao.DaoFactory;
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

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession httpSession = ((HttpServletRequest) servletRequest).getSession(false);
        String userName = servletRequest.getParameter("username");
        String password = servletRequest.getParameter("password");

        if (httpSession != null & httpSession.getAttribute("sessionUser") != null) {
            logger.info("Auth READY for pair user/pass - " + userName + " / " + password + ", sessionUser = " + httpSession.getAttribute("sessionUser"));
            filterChain.doFilter(servletRequest, servletResponse);
        } else {

            if (userName == null || password == null) {
                servletRequest.setAttribute("msg", "Login error.");
                logger.info("User or Password is empty...");
                ((HttpServletResponse) servletResponse).sendRedirect("login.jsp");
            } else {

                if (DaoFactory.getInstance().getAuthDao().checkAuth(userName, password)) {
                    logger.info("Auth OK for pair user/password - " + userName + " / " + password);
                    httpSession.setAttribute("sessionUser", servletRequest.getParameter("username"));
                    filterChain.doFilter(servletRequest, servletResponse);
                } else {
                    logger.info("Auth FAIL for pair user/pass - " + userName + " / " + password);
                    servletRequest.setAttribute("msg", "Login error.");
                    ((HttpServletResponse) servletResponse).sendRedirect("login.jsp");
                }
            }
        }
    }

    public void destroy() {
    }
}
