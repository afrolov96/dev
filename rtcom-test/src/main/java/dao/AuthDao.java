package dao;

import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthDao {
    private DataSource dataSource;
    private static Logger logger = Logger.getLogger(AuthDao.class);

    public AuthDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean checkAuth(String user, String password) {
        boolean result = false;
        try(Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement("select * from users where user_name=? and password=?")) {
            ps.setString(1, user);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
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
