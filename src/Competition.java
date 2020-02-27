import java.text.SimpleDateFormat;

public class Competition {
    public Integer id;
    public String title;
    public String location;
    public String c_date;
    public String full_name;
    public Competition(int id, String title, String location, String c_date, String full_name)

    {
        this.id = id;
        this.title = title;
        this.location = location;
        this.c_date = c_date;
        this.full_name=full_name;
    }
}