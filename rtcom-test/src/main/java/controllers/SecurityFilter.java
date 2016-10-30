package controllers;

import dao.AuthDao;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter({"/", "/index.jsp"})
public class SecurityFilter implements Filter {
    private final static Logger logger = Logger.getLogger(SecurityFilter.class);

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession httpSession = ((HttpServletRequest) servletRequest).getSession();
        String userName = servletRequest.getParameter("username");
        String password = servletRequest.getParameter("password");

        if (httpSession.getAttribute("sessionUser") != null) {
            logger.info("Auth READY for user = " + httpSession.getAttribute("sessionUser"));
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            if (userName == null || password == null) {
                httpSession.setAttribute("msg", "");
                logger.info("User or Password is empty...");
                ((HttpServletResponse) servletResponse).sendRedirect("login.jsp");
            } else {
                if (AuthDao.getInstance().checkAuth(userName, password)) {
                    logger.info("Auth OK for pair user/password - " + userName + " / " + password);
                    httpSession.setAttribute("sessionUser", servletRequest.getParameter("username"));
                    filterChain.doFilter(servletRequest, servletResponse);
                } else {
                    logger.info("Auth FAIL for pair user/pass - " + userName + " / " + password);
                    httpSession.setAttribute("msg", "Login error! Wrong user/password.");
                    ((HttpServletResponse) servletResponse).sendRedirect("login.jsp");
                }
            }
        }
    }

    public void destroy() {
    }
}
