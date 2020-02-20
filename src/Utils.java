import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    static boolean isValid(String value, String datePattern)
    {
        if (value == null || datePattern == null || datePattern.length() <= 0) {
            return false;}
        SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
        formatter.setLenient(false);

        try {
            formatter.parse(value);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
