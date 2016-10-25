package dao;

import models.Car;
import models.City;
import util.ParamsConverter;
import models.Person;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class PersonsDao {

    private static PersonsDao instance;
    private static DataSource dataSource;
    private static Connection connection; 

    private static Logger logger = Logger.getLogger(PersonsDao.class);

    private PersonsDao() {
    }

    public static synchronized PersonsDao getInstance() {
        if (instance == null) {
            try {
                instance = new PersonsDao();
                Context ctx = new InitialContext();
                instance.dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/postgres");
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
        logger.info("PersonsDao is null...");
    }

    public HashMap<String, City> getData(ParamsConverter converter) {
        HashMap<String, City> result = new HashMap<String, City>();
        StringBuilder sql = new StringBuilder();
        sql.append(" select cities.id city_id, cities.name city_name, ");
        sql.append(" persons.id person_id, persons.name person_name, persons.patronymic, persons.surname, ");
        sql.append(" cars.name car_name, cars.num, cars.color, cars.size ");
        sql.append(" from cities, persons, cars where cities.id = persons.city_id and persons.id = cars.owner_id ");

        if (converter.getKeys().length > 0) {
            for (int i = 0; i < converter.getKeys().length; i++) {
                sql.append(" and " + converter.getKeys()[i] + (converter.getKeys()[i].startsWith("person") ? " like ? " : " = ? "));
            }
        }

        logger.info("Request constructed: " + sql.toString());

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < converter.getValues().length; i++) {
                ps.setString(i + 1, (converter.getKeys()[i].startsWith("person") ? "%" + converter.getValues()[i].replace("!", "!!") : converter.getValues()[i]));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    City city = result.get(rs.getString("city_name"));
                    if (city == null) {
                        city = new City();
                        city.setId(rs.getInt("city_id"));
                        city.setName(rs.getString("city_name"));
                    }

                    HashMap<String, Person> personsMap = city.getPersonsMap();
                    Person person = personsMap.get(rs.getString("person_name"));
                    if (person == null) {
                        person = new Person();
                        person.setId(rs.getInt("person_id"));
                        person.setName(rs.getString("person_name"));
                        person.setPatronymic(rs.getString("patronymic"));
                        person.setSurname(rs.getString("surname"));
                    }

                    HashMap<String, Car> carsMap = person.getCarsMap();
                    Car car = carsMap.get(rs.getString("car_name"));
                    if (car == null) {
                        car = new Car();
                        car.setName(rs.getString("car_name"));
                        car.setNumber(rs.getString("num"));
                        car.setColor(rs.getString("color"));
                        car.setSize(rs.getString("size"));
                    }

                    person.getCarsMap().put(car.getName(), car);
                    city.getPersonsMap().put(person.getName(), person);
                    result.put(city.getName(), city);
                }
                logger.info("Query result : \n" + printResult(result));
            } catch (Exception e) {
                logger.error("getData() ResultSet error: " + e.getMessage());
            }

        } catch (SQLException e) {
            logger.error("getData() PreparedStatement error: " + e.getMessage());
        }
        return result;
    }

    private String printResult(HashMap<String, City> result) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, City> entryCity : result.entrySet()) {
            sb.append(entryCity.getValue().getName() + "\n");

            for (Map.Entry<String, Person> entryPerson : entryCity.getValue().getPersonsMap().entrySet()) {
                sb.append("\t" + entryPerson.getValue().getSurname() + " " + entryPerson.getValue().getName() + " " + entryPerson.getValue().getSurname() + "\n");

                for (Map.Entry<String, Car> entryCar : entryPerson.getValue().getCarsMap().entrySet()) {
                    sb.append("\t\t" + entryCar.getValue().getName() + " " + entryCar.getValue().getColor() + " " + entryCar.getValue().getNumber() + "\n\n");
                }
            }
        }
        return sb.toString();
    }
}
