import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/*import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;*/
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainWindow extends JFrame{
    private JButton button1;
    private JPanel panel1;
    private JLabel label1;
    private JTextField Cname;
    private JTextField Cdate;
    private JTextField Csecr;
    private JTextField Cjudge;
    private JLabel Lname;
    private JLabel Ldate;
    private JLabel Lsecr;
    private JLabel Ljudge;
    private JButton gen;
    private JLabel res;
    public static String keep = "";

    public MainWindow() {

        this.getContentPane().add(panel1);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Cname.setVisible(true);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileopen = new JFileChooser();
                int ret = fileopen.showDialog(null, "Открыть файл");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    label1.setText(""+fileopen.getSelectedFile());
                    keep = ""+fileopen.getSelectedFile();
                }
            }
        });
        gen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                File excel = new File(keep);
                try {
                    FileInputStream fis = new FileInputStream(excel);
                    try {
                        XSSFWorkbook wb = new XSSFWorkbook(fis);
                        XSSFSheet sheet = wb.getSheetAt(0);
                        XSSFRow row = sheet.getRow(1);
                        XSSFCell cell = row.getCell(1);
                        System.out.println(cell.getStringCellValue());
                    }
                    catch(IOException e) {
                        e.printStackTrace();
                    }
                }
                catch(FileNotFoundException fnfe) {
                    System.out.println(fnfe.getMessage());
                }


            }
        });
    }

}
