package util;

import models.Car;
import models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PersonFactory {
    private static volatile PersonFactory instance;

    private PersonFactory() {

    }

    public static PersonFactory getInstance() {
        if (instance == null) {
            synchronized (PersonFactory.class) {
                if (instance == null) {
                    instance = new PersonFactory();
                }
            }
        }
        return instance;
    }

    public Person getPerson(ResultSet resultSet) throws SQLException {
        return new Person(resultSet.getInt("person_id"), resultSet.getString("person_name"),
                resultSet.getString("patronymic"), resultSet.getString("surname"), CityFactory.getInstance().getCity(resultSet), new ArrayList<Car>());
    }
}
