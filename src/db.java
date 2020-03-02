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
            ResultSet resultSet = statement.executeQuery("SELECT ID, TITLE, LOCATION, C_DATE FROM COMPETITION ORDER BY C_DATE");
            while (resultSet.next()) {
                Integer id=resultSet.getInt("id");
                String title=resultSet.getString("title");
                String location=resultSet.getString("location");
                String c_date=resultSet.getString("c_date");
                String full_name=c_date + " " + title +", " + location;
                competitions.add(new Competition(id,title,location,c_date,full_name));
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
                Integer id = resultSet.getInt("id");
                String discp_group=resultSet.getString("discp_group");
                Integer age_low=resultSet.getInt("age_low");
                Integer age_high=resultSet.getInt("age_high");
                Integer weight=resultSet.getInt("weight");
                String full_name=discp_group;
                if (age_low == age_high)
                    {
                        full_name = full_name + " " + age_low.toString() + Utils.getstrage(age_low);
                    }
                else
                    {
                        full_name = full_name + " " + age_low.toString() + "-" + age_high.toString() + Utils.getstrage(age_high);
                    }
                if (discp_group.toLowerCase().equals("кумитэ"))
                    {
                        full_name = full_name + " " + weight.toString() + "кг";
                    }
                discipline.add(new Discipline(id,discp_group,age_low,age_high,weight,full_name));
            }
            return discipline;
        } catch (SQLException e) {
            e.printStackTrace();
            // Если произошла ошибка - возвращаем пустую коллекцию
            return Collections.emptyList();
        }
    }
    public List<Orgperson> getAllOrgPersons() {
        try (Statement statement = this.connection.createStatement()) {
            List<Orgperson> orgpersons = new ArrayList<Orgperson>();
            ResultSet resultSet = statement.executeQuery("SELECT ID, FIO, LEVEL, CATEGORY FROM ORGPERSONS ORDER BY ID");
            while (resultSet.next()) {
                Integer id=resultSet.getInt("ID");
                String fio=resultSet.getString("FIO");
                String level=resultSet.getString("LEVEL");
                String category=resultSet.getString("CATEGORY");
                String full_name=fio;
                if (level.length()>0)
                    {
                        full_name = full_name +", "+level;
                    }
                if (category.length()>0)
                {
                    full_name = full_name +", "+category;
                }
                orgpersons.add(new Orgperson(id,fio,level,category,full_name));
            }
            return orgpersons;
        } catch (SQLException e) {
            e.printStackTrace();
            // Если произошла ошибка - возвращаем пустую коллекцию
            return Collections.emptyList();
        }
    }
    public List<Request> getRequest(Integer id) {
        try (Statement statement = this.connection.createStatement()) {
            List<Request> requests = new ArrayList<Request>();
            ResultSet resultSet = statement.executeQuery("SELECT ID_MAN, ID, FIO, CLUB, BIRTH_DATE, KYU_LEVEL, WEIGHT, KUMITE, KATA, TRAINER FROM REQUEST WHERE ID="+id.toString());
            while (resultSet.next()) {
                Integer idm=resultSet.getInt("ID_MAN");
                Integer id_comp=resultSet.getInt("ID");
                String fio=resultSet.getString("FIO");
                String club=resultSet.getString("CLUB");
                String rdate=resultSet.getString("BIRTH_DATE");
                String level=resultSet.getString("KYU_LEVEL");
                Integer weight=resultSet.getInt("WEIGHT");
                String kata=resultSet.getString("KATA");
                String kumite=resultSet.getString("KUMITE");
                String trainer=resultSet.getString("TRAINER");
                requests.add(new Request(idm,id_comp,fio,club,rdate,level,weight,kata,kumite,trainer));
            }
            return requests;
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
    public void updDiscipline(Discipline discipline) {
        // Создадим подготовленное выражение, чтобы избежать SQL-инъекций

        try (PreparedStatement statement = this.connection.prepareStatement(
                "UPDATE DISCIPLINES SET DISCP_GROUP=?, AGE_LOW=?, AGE_HIGH=?, WEIGHT=? WHERE ID=?")) {
            statement.setObject(1, discipline.discp_group);
            statement.setObject(2, discipline.age_low);
            statement.setObject(3, discipline.age_high);
            statement.setObject(4, discipline.weight);
            statement.setObject(5, discipline.id);
            // Выполняем запрос
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updOrgpersons(Orgperson orgperson) {
        // Создадим подготовленное выражение, чтобы избежать SQL-инъекций
        try (PreparedStatement statement = this.connection.prepareStatement(
                "UPDATE ORGPERSONS SET FIO=?,LEVEL=?,CATEGORY=? WHERE ID=?")) {
            statement.setObject(1, orgperson.fio);
            statement.setObject(2, orgperson.level);
            statement.setObject(3, orgperson.jcategory);
            statement.setObject(4, orgperson.id);
            // Выполняем запрос
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addOrgpersons(Orgperson orgperson) {
        // Создадим подготовленное выражение, чтобы избежать SQL-инъекций
        try (PreparedStatement statement = this.connection.prepareStatement(
                "INSERT INTO ORGPERSONS(FIO,LEVEL,CATEGORY) VALUES(?,?,?)")) {
            statement.setObject(1, orgperson.fio);
            statement.setObject(2, orgperson.level);
            statement.setObject(3, orgperson.jcategory);
            // Выполняем запрос
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Integer addRequest(Request request) {
        // Создадим подготовленное выражение, чтобы избежать SQL-инъекций

        try {
            PreparedStatement statement = this.connection.prepareStatement(
                "INSERT INTO REQUEST(ID, FIO, CLUB, BIRTH_DATE, KYU_LEVEL, WEIGHT, KUMITE, KATA, TRAINER) VALUES(?,?,?,?,?,?,?,?,?)");
                statement.setObject(1, request.id_comp);
                statement.setObject(2, request.fio);
                statement.setObject(3, request.club);
                statement.setObject(4, request.r_date);
                statement.setObject(5, request.level);
                statement.setObject(6, request.weight);
                statement.setObject(7, request.kumite);
                statement.setObject(8, request.kata);
                statement.setObject(9, request.trainer);
                // Выполняем запрос
                statement.execute();
                Statement statement1 = this.connection.createStatement();
                ResultSet resultSet = statement1.executeQuery("SELECT MAX(ID_MAN) AS ID_MAN  FROM REQUEST");
                resultSet.next();
                Integer idm = resultSet.getInt("ID_MAN");
                return idm;
            }
         catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void updRequest(Request request) {
        // Создадим подготовленное выражение, чтобы избежать SQL-инъекций

        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "UPDATE REQUEST  SET FIO=?, CLUB=?, BIRTH_DATE=?, KYU_LEVEL=?, WEIGHT=?, KUMITE=?, KATA=?, TRAINER=? WHERE ID_MAN=?");
            statement.setObject(1, request.fio);
            statement.setObject(2, request.club);
            statement.setObject(3, request.r_date);
            statement.setObject(4, request.level);
            statement.setObject(5, request.weight);
            statement.setObject(6, request.kumite);
            statement.setObject(7, request.kata);
            statement.setObject(8, request.trainer);
            statement.setObject(9, request.id);
            // Выполняем запрос
            statement.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*public void updDiscipline(Discipline discipline) {
        // Создадим подготовленное выражение, чтобы избежать SQL-инъекций

        try (PreparedStatement statement = this.connection.prepareStatement(
                "UPDATE DISCIPLINES SET DISCP_GROUP=?, AGE_LOW=?, AGE_HIGH=?, WEIGHT=? WHERE ID=?")) {
            statement.setObject(1, discipline.discp_group);
            statement.setObject(2, discipline.age_low);
            statement.setObject(3, discipline.age_high);
            statement.setObject(4, discipline.weight);
            statement.setObject(5, discipline.id);
            // Выполняем запрос
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
}