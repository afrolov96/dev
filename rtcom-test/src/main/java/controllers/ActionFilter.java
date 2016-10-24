package controllers;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/ActionFilter")
public class ActionFilter implements Filter {
    static Logger logger = Logger.getLogger(ActionFilter.class);

    public void init(FilterConfig filterConfig) throws ServletException {    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession httpSession = ((HttpServletRequest) servletRequest).getSession(false);
        String username = servletRequest.getParameter("username");
        String password = servletRequest.getParameter("password");

            if(httpSession != null & httpSession.getAttribute("sessionUser") != null){
                logger.info("Auth READY for pair user/pass - " + username + " / " + password);
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                logger.info("Not auth access...");
                servletResponse.getWriter().write("</br>Session not found. Please, login again.");
            }
    }

    public void destroy() {    }
}
