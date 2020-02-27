import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utils {
    static boolean isValid(String userValue)
    {
        if (userValue.matches("\\d{2}.\\d{2}.\\d{4}"))
        {
            String[] spl = userValue.split("\\.");
            Integer d=Integer.parseInt(spl[0]);
            Integer m=Integer.parseInt(spl[1]);
            Integer y=Integer.parseInt(spl[2]);
            Integer daysInMonth = 31;

            if (m > 0 && m < 13 && y > 0 && y < 2200)
            {
                if (m == 2)
                {
                    daysInMonth = ((y % 400) == 0) ? 29 : ((y % 100) == 0) ? 28 : ((y % 4) == 0) ? 29 : 28;
                }
                else if (m == 4 || m == 6 || m == 9 || m == 11)
                {
                    daysInMonth = 30;
                }
                return (d <= daysInMonth);
            }
        }
        return false;
        /*if (value == null || datePattern == null || datePattern.length() <= 0) {
            return false;}
        SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
        formatter.setLenient(false);

        try {
            formatter.parse(value);
        } catch (ParseException e) {
            return false;
        }
        return true;*/
    }
    static String getstrage(Integer age)
        {
            Integer[] agearr1 = {21,31,41,51,61,71};
            Integer[] agearr2 = {22,23,24,32,33,34,42,43,44,52,53,54,62,63,64};
            if (Arrays.asList(agearr1).contains(age))
                {
                    return age.toString()+"год";
                }
            else
                {
                    if (Arrays.asList(agearr2).contains(age))
                        {
                            return "года";
                        }
                    else
                        {
                            return "лет";
                        }
                }
        }
}
