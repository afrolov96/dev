package dao;

import org.apache.log4j.Logger;
import util.DataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthDao {

    private volatile static AuthDao instance;
    private DataSource dataSource;
    private static Logger logger = Logger.getLogger(AuthDao.class);

    private AuthDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static AuthDao getInstance() {
        if (instance == null) {
            synchronized (AuthDao.class) {
                if (instance == null) {
                    instance = new AuthDao(DataSourceFactory.getDataSource());
                }
            }
        }
        return instance;
    }

    public boolean checkAuth(String user, String password) {
        boolean result = false;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("select * from users where user_name=? and password=?")) {
            statement.setString(1, user);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            result = resultSet.next();
        } catch (SQLException e) {
            logger.error("checkAuth(): ", e);
        }
        return result;
    }
}
