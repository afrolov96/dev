package dao;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthDao {
    private static AuthDao instance;
    private static DataSource dataSource;
    private static Connection connection;
    private static Logger logger = Logger.getLogger(AuthDao.class);

    private AuthDao() {
    }

    public static synchronized AuthDao getInstance() {
        if (instance == null) {
            try {
                instance = new AuthDao();
                Context ctx = new InitialContext();
                instance.dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/postgres_sec");
                connection = dataSource.getConnection();
            } catch (NamingException e) {
                logger.error(e.getMessage());
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
        return instance;
    }

    public static synchronized void destroyInstance() {
        instance = null;
        logger.info("AuthDao is null...");
    }

    public boolean checkAuth(String user, String password) {
        boolean result = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from users where user_name=? and password=?")) {
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, password);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                result = rs.next();
            } catch (Exception e) {
                logger.error("checkAuth() ResultSet error: " + e.getMessage());
            }

        } catch (SQLException e) {
            logger.error("checkAuth() PreparedStatement: " + e.getMessage());
        }
        return result;
    }
}
