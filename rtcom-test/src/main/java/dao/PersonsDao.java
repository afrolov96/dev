package dao;

import models.Person;
import org.apache.log4j.Logger;
import util.CarFactory;
import util.DataSourceFactory;
import util.PersonFactory;
import util.QueryRequestMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PersonsDao {

    private static volatile PersonsDao instance;
    private static final Logger logger = Logger.getLogger(PersonsDao.class);

    private PersonsDao() {
    }

    public static PersonsDao getInstance() {
        if (instance == null) {
            synchronized (PersonsDao.class) {
                if (instance == null) {
                    instance = new PersonsDao();
                }
            }
        }
        return instance;
    }

    public Iterable<Person> getData(QueryRequestMapper queryRequestMapper) {
        Map<Integer, Person> queryResult = new HashMap<>();

        StringBuilder sql = new StringBuilder();
        sql.append(" select persons.id person_id, persons.surname, persons.name person_name, persons.patronymic, ");
        sql.append(" cities.id city_id, cities.name city_name, ");
        sql.append(" cars.id car_id, cars.name car_name, cars.num, cars.color, cars.size ");
        sql.append(" from cities, persons, cars where persons.city_id = cities.id and cars.owner_id = persons.id ");
        sql.append(queryRequestMapper.getSurName() != null ? " and persons.surname like ? " : "");
        sql.append(queryRequestMapper.getName() != null ? " and persons.name like ? " : "");
        sql.append(queryRequestMapper.getPatronymic() != null ? " and persons.patronymic like ? " : "");
        sql.append(queryRequestMapper.getCityId() != 0 ? " and persons.city_id = ? " : "");
        sql.append(queryRequestMapper.getCarName() != null ? " and cars.name = ? " : "");
        sql.append(queryRequestMapper.getCarNumber() != null ? " and cars.num = ? " : "");
        sql.append(queryRequestMapper.getCarColor() != null ? " and cars.color = ? " : "");
        sql.append(queryRequestMapper.getCarSize() != null ? " and cars.size = ? " : "");

        try (Connection connection = DataSourceFactory.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.toString())) {

            int i = 1;
            if (queryRequestMapper.getSurName() != null) {
                statement.setString(i++, "%" + queryRequestMapper.getSurName() + "%");
            }
            if (queryRequestMapper.getName() != null) {
                statement.setString(i++, "%" + queryRequestMapper.getName() + "%");
            }
            if (queryRequestMapper.getPatronymic() != null) {
                statement.setString(i++, "%" + queryRequestMapper.getPatronymic() + "%");
            }
            if (queryRequestMapper.getCityId() != 0) {
                statement.setInt(i++, queryRequestMapper.getCityId());
            }
            if (queryRequestMapper.getCarName() != null) {
                statement.setString(i++, queryRequestMapper.getCarName());
            }
            if (queryRequestMapper.getCarNumber() != null) {
                statement.setString(i++, queryRequestMapper.getCarNumber());
            }
            if (queryRequestMapper.getCarColor() != null) {
                statement.setString(i++, queryRequestMapper.getCarColor());
            }
            if (queryRequestMapper.getCarSize() != null) {
                statement.setString(i++, queryRequestMapper.getCarSize());
            }
            logger.error("Statement = " + statement);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                logger.error(e);
            }

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Person person = queryResult.computeIfAbsent(resultSet.getInt("person_id"), personId -> {
                    try {
                        return PersonFactory.getInstance().getPerson(resultSet);
                    } catch (SQLException e) {
                        logger.error(e);
                        return null;
                    }
                });

                if (person != null) {
                    person.getCars().add(CarFactory.getInstance().getCar(resultSet));
                }
            }
        } catch (SQLException e) {
            logger.error("getData() : ", e);
        }

        return queryResult.values();
    }
}
