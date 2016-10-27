package dao;


import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DaoFactory {
    private static volatile DaoFactory instance;
    private static volatile DataSource dataSource;
    private static Logger logger = Logger.getLogger(DaoFactory.class);

    private DaoFactory() {
    }

    public static DaoFactory getInstance() {
        if (instance == null) {
            synchronized (DaoFactory.class) {
                instance = new DaoFactory();
                try {
                    Context ctx = new InitialContext();
                    dataSource = (javax.sql.DataSource) ctx.lookup("java:comp/env/jdbc/postgres");
                } catch (NamingException e) {
                    logger.error(e.getMessage());
                }
            }
        }
        return instance;
    }

    public AuthDao getAuthDao() {
        return new AuthDao(dataSource);
    }

    public PersonsDao getPersonsDao() {
        return new PersonsDao(dataSource);
    }

    public DataSource getDataSource(){
        return dataSource;
    }
}
