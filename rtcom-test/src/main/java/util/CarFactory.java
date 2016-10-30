package util;

import models.Car;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarFactory {
    private static volatile CarFactory instance;

    private CarFactory() {

    }

    public static CarFactory getInstance() {
        if (instance == null) {
            synchronized (CarFactory.class) {
                if (instance == null) {
                    instance = new CarFactory();
                }
            }
        }
        return instance;
    }

    public Car getCar(ResultSet resultSet) throws SQLException{
        return new Car(resultSet.getInt("car_id"), resultSet.getString("car_name"),
                resultSet.getString("num"), resultSet.getString("color"), resultSet.getString("size"));
    }
}
