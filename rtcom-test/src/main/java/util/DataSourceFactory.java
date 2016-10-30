package util;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataSourceFactory {

    private static final Logger logger = Logger.getLogger(DataSourceFactory.class);
    private static volatile DataSource dataSource;

    private DataSourceFactory() {
    }

    public static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (DataSourceFactory.class) {
                try {
                    Context ctx = new InitialContext();
                    dataSource = (javax.sql.DataSource) ctx.lookup("java:comp/env/jdbc/postgres");
                } catch (NamingException e) {
                    logger.error(e.getMessage());
                }
            }
        }
        return dataSource;
    }
}
