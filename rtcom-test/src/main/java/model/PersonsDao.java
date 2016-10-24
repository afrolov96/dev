package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PersonsDao {

    private static PersonsDao instance;
    private static DataSource dataSource;
    private static Connection connection;
    private static final int KEY_WHERE = 1;
    private static final int KEY_AND = 2;

    private static Logger logger = Logger.getLogger(PersonsDao.class);

    private PersonsDao(){}

    public  static synchronized PersonsDao getInstance(){
        if(instance == null) {
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

    public static synchronized void destroyInstance(){
        instance = null;
        logger.info("PersonsDao is null...");
    }

    public String constructClause(String mainQuery, String params, int clauseKey){
        StringBuilder query = new StringBuilder();
        query.append(mainQuery);

        Map<String, String>[] map = new Gson().fromJson(params, new TypeToken<Map<String, String>[]>() {}.getType());
        int k = map.length;
        if(k > 0) {
            switch (clauseKey){
                case KEY_WHERE:
                    query.append(" where ");
                    break;
                case KEY_AND:
                    query.append(" and ");
                    break;
            }

            for (int i = 0; i < map.length; i++) {
                for (Map.Entry<String, String> entry : map[i].entrySet()) {
                    query.append(entry.getKey().replace("_", ".") + "='" + entry.getValue() + "'");
                    k--;
                    if (k > 0) {
                        query.append(" and ");
                    }
                }
            }
        }
        return query.toString();
    }

    public List<Person> getPersonsInfo(String person_params, String city_params, String car_params){
        ArrayList<Person> persons = new ArrayList<Person>();

        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select persons.id as id, persons.surname as surname, persons.name as name, persons.patronymic as patronymic, ");
            sb.append("cities.name as city, cars.model as car, cars.num as car_num, cars.color as car_color, cars.class as car_class from persons ");
            sb.append(constructClause("inner join cities on cities.id=persons.city_id ", city_params, KEY_AND));
            sb.append(constructClause("inner join cars on cars.id=persons.car_id ", car_params, KEY_AND));
            sb.append(constructClause("", person_params, KEY_WHERE));
            logger.info("Request constructed: " + sb.toString());
            PreparedStatement preparedStatement = connection.prepareStatement(sb.toString());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Person person = new Person();
                person.setId(rs.getInt("id"));
                person.setName(rs.getString("name"));
                person.setPatronymic(rs.getString("patronymic"));
                person.setSurname(rs.getString("surname"));
                person.setCity(rs.getString("city"));
                person.setCar(rs.getString("car"));
                person.setCar_num(rs.getString("car_num"));
                person.setCar_color(rs.getString("car_color"));
                person.setCar_class(rs.getString("car_class"));
                persons.add(person);
            }
            rs.close();
            preparedStatement.close();

        } catch (SQLException e) {
            logger.error("getPersonsInfo() error: " + e.getMessage());
        }

        return persons;
    }
}
