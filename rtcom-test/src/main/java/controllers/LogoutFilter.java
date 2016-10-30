package controllers;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/Logout")
public class LogoutFilter implements Filter {
    static Logger logger = Logger.getLogger(LogoutFilter.class);

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession httpSession = ((HttpServletRequest) servletRequest).getSession(false);

        if (httpSession != null) {
            httpSession.invalidate();
        }
        logger.info("User was logout...");
        ((HttpServletResponse) servletResponse).sendRedirect("login.jsp");
    }

    public void destroy() {
    }
}
