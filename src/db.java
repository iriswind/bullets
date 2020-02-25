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
            ResultSet resultSet = statement.executeQuery("SELECT id_man, name, club, kyu_level FROM competition ORDER BY id_man");
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
            ResultSet resultSet = statement.executeQuery("SELECT id, title, location, c_date FROM COMPETITION ORDER BY ID");
            while (resultSet.next()) {
                competitions.add(new Competition(resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("location"),
                        resultSet.getString("c_date")));
            }
            return competitions;
        } catch (SQLException e) {
            e.printStackTrace();
            // Если произошла ошибка - возвращаем пустую коллекцию
            return Collections.emptyList();
        }
        }
    public List<Discipline> getAllDisciplines() {
        try (Statement statement = this.connection.createStatement()) {
            List<Discipline> discipline = new ArrayList<Discipline>();
            ResultSet resultSet = statement.executeQuery("SELECT ID,DISCP_GROUP,AGE_LOW,AGE_HIGH,WEIGHT FROM DISCIPLINES ORDER BY DISCP_GROUP,AGE_LOW,AGE_HIGH,WEIGHT");
            while (resultSet.next()) {
                discipline.add(new Discipline(
                        resultSet.getInt("id"),
                        resultSet.getString("discp_group"),
                        resultSet.getInt("age_low"),
                        resultSet.getInt("age_high"),
                        resultSet.getInt("weight")));
            }
            return discipline;
        } catch (SQLException e) {
            e.printStackTrace();
            // Если произошла ошибка - возвращаем пустую коллекцию
            return Collections.emptyList();
        }
    }
    public void updCompetitions(Competition competition) {
        // Создадим подготовленное выражение, чтобы избежать SQL-инъекций
        try (PreparedStatement statement = this.connection.prepareStatement(
                "UPDATE COMPETITION SET TITLE=?,LOCATION=?,C_DATE=? WHERE ID=?")) {
            statement.setObject(1, competition.title);
            statement.setObject(2, competition.location);
            statement.setObject(3, competition.c_date);
            statement.setObject(4, competition.id);
            // Выполняем запрос
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addCompetitions(Competition competition) {
        // Создадим подготовленное выражение, чтобы избежать SQL-инъекций
        try (PreparedStatement statement = this.connection.prepareStatement(
                "INSERT INTO COMPETITION(TITLE,LOCATION,C_DATE) VALUES(?,?,?)")) {
            statement.setObject(1, competition.title);
            statement.setObject(2, competition.location);
            statement.setObject(3, competition.c_date);
            // Выполняем запрос
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addDiscipline(Discipline discipline) {
        // Создадим подготовленное выражение, чтобы избежать SQL-инъекций

        try (PreparedStatement statement = this.connection.prepareStatement(
                "INSERT INTO DISCIPLINES(DISCP_GROUP,AGE_LOW,AGE_HIGH,WEIGHT) VALUES(?,?,?,?)")) {
            statement.setObject(1, discipline.discp_group);
            statement.setObject(2, discipline.age_low);
            statement.setObject(3, discipline.age_high);
            statement.setObject(4, discipline.weight);
            // Выполняем запрос
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}