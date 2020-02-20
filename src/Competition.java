import java.sql.Date;

public class Competition {
    // Поля класса

    public Integer id;

    public String title;

    public String location;

    public Date c_date;

    //public Date birth_date;

    // Конструктор
    public Competition(int id, String title, String location, Date c_date)

    {
        this.id = id;
        this.title = title;
        this.location = location;
        this.c_date = c_date;
    }

    // Выводим информацию по продукту
    @Override
    public String toString() {
        return String.format("Мероприятие: %s | Место проведения: %s | Дата: %s",
                this.title, this.location, this.c_date);
    }
}