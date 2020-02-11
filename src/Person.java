public class Person {

    // Поля класса

    public Integer id_man;

    public String pname;

    public String club;

    //public Date birth_date;

    public Integer kyu_level;

    // Конструктор
    public Person(int id_man, String pname, String club, Integer kyu_level) {
        this.id_man = id_man;
        this.pname = pname;
        this.club = club;
        this.kyu_level = kyu_level;
    }

    // Выводим информацию по продукту
    @Override
    public String toString() {
        return String.format("ID: %s | Товар: %s | Цена: %s | Категория: %s",
                this.id_man, this.pname, this.club, this.kyu_level);
    }
}