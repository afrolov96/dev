package dao;

import models.City;
import org.apache.log4j.Logger;
import util.CityFactory;
import util.DataSourceFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CityDao {

    private static volatile CityDao instance;
    private static final Logger logger = Logger.getLogger(CityDao.class);

    private CityDao() {
    }

    public static CityDao getInstance() {
        if (instance == null) {
            synchronized (CityDao.class) {
                if (instance == null) {
                    instance = new CityDao();
                }
            }
        }
        return instance;
    }

    public Iterable<City> getAll() {
        ArrayList<City> cities = new ArrayList<>();

        try (Connection connection = DataSourceFactory.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select id city_id, name city_name from cities");

            while (resultSet.next()) {
                cities.add(CityFactory.getInstance().getCity(resultSet));
            }
        } catch (SQLException e) {
            logger.error("getAll(): ", e);
        }

        return cities;
    }
}
