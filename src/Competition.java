import java.text.SimpleDateFormat;

public class Competition {
    // Поля класса

    public Integer id;

    public String title;

    public String location;

    public String c_date;

    //public Date birth_date;

    // Конструктор
    public Competition(int id, String title, String location, String c_date)

    {
        this.id = id;
        this.title = title;
        this.location = location;
        this.c_date = c_date;
    }

    // Выводим информацию по продукту
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return
        this.id + " | Мероприятие: " + this.title + " | Место проведения: " + this.location + " | Дата: " + this.c_date;
    }
}