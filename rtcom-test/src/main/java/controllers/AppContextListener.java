package controllers;

import model.AuthDao;
import model.PersonsDao;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;

/**
 * Created by andrey on 23.10.2016.
 */
@WebListener
public class AppContextListener implements ServletContextListener {
    static Logger logger = Logger.getLogger(AppContextListener.class);

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext ctx = servletContextEvent.getServletContext();
        String log4jConfig = ctx.getInitParameter("log4j-config");

        if(log4jConfig == null){
            BasicConfigurator.configure();
        } else {
            String webAppPath = ctx.getRealPath("/");
            String log4jProp = webAppPath + log4jConfig;
            File log4jConfigFile = new File(log4jProp);
            if (log4jConfigFile.exists()) {
                DOMConfigurator.configure(log4jProp);
            } else {
                BasicConfigurator.configure();
            }
        }

        AuthDao.getInstance();
        PersonsDao.getInstance();
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        AuthDao.destroyInstance();
        PersonsDao.destroyInstance();
    }
}
