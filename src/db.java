import org.sqlite.JDBC;

import java.sql.*;
import java.util.*;

public class db {

    private static final String CON_STR = "jdbc:sqlite:bullets.db";
    private static db instance = null;
    public static synchronized db getInstance() throws SQLException {
        if (instance == null)
            instance = new db();
        return instance;
    }

    private Connection connection;

    private db() throws SQLException {
            // Регистрируем драйвер, с которым будем работать
            // в нашем случае Sqlite
            DriverManager.registerDriver(new JDBC());
            // Выполняем подключение к базе данных
            this.connection = DriverManager.getConnection(CON_STR);
        }

    public List<Person> getAllPersons() {
            try (Statement statement = this.connection.createStatement()) {
                List<Person> persons = new ArrayList<Person>();
            ResultSet resultSet = statement.executeQuery("SELECT id_man, name, club, kyu_level FROM competition");
            while (resultSet.next()) {
                persons.add(new Person(resultSet.getInt("id_man"),
                            resultSet.getString("name"),
                            resultSet.getString("club"),
                            resultSet.getInt("kyu_level")));
                }
            return persons;
            } catch (SQLException e) {
                e.printStackTrace();
                // Если произошла ошибка - возвращаем пустую коллекцию
                return Collections.emptyList();
            }
        }
    public List<Competition> getAllCompetitions() {
        try (Statement statement = this.connection.createStatement()) {
            List<Competition> competitions = new ArrayList<Competition>();
            ResultSet resultSet = statement.executeQuery("SELECT id, title, location, c_date FROM COMPETITION");
            while (resultSet.next()) {
                competitions.add(new Competition(resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("location"),
                        resultSet.getDate("c_date")));
            }
            return competitions;
        } catch (SQLException e) {
            e.printStackTrace();
            // Если произошла ошибка - возвращаем пустую коллекцию
            return Collections.emptyList();
        }
        }
    }