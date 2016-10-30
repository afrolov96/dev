package util;

import models.City;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CityFactory {
    private static volatile CityFactory instance;

    private CityFactory() {

    }

    public static CityFactory getInstance() {
        if (instance == null) {
            synchronized (CityFactory.class) {
                if (instance == null) {
                    instance = new CityFactory();
                }
            }
        }
        return instance;
    }

    public City getCity(ResultSet resultSet) throws SQLException{
        return new City(resultSet.getInt("city_id"), resultSet.getString("city_name"));
    }

}
