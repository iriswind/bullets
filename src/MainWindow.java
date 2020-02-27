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

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MainWindow extends JFrame{
    private JButton button1;
    private JPanel panel1;
    private JPanel pn1;
    private JLabel label1;
    private JTextField Cdate;
    private JTextField Csecr;
    private JTextField Cjudge;
    private JLabel Lname;
    private JLabel Ldate;
    private JLabel Lsecr;
    private JLabel Ljudge;
    private JButton gen;
    private JLabel res;
    public JComboBox Cname;
    private JButton sel_discp;
    private JButton red_comp;
    public static String keep = "";
    public List<Competition> m_competitions;
    public List<Competition> competitions;


    public MainWindow() {


        this.getContentPane().add(panel1);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Cname.setVisible(true);
        try {
            db dbH = db.getInstance();
            this.competitions = dbH.getAllCompetitions();
            DefaultComboBoxModel clist=new DefaultComboBoxModel();
            Cname.setModel(clist);
            for (Competition competition : competitions) {
                clist.addElement(competition.full_name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
                        try {
                            db dbH = db.getInstance();
                            List<Person> persons = dbH.getAllPersons();
                            for (Person person : persons) {
                                System.out.println(person.toString());
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
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
        red_comp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Comptl window = new Comptl();
                window.pack();
                window.setVisible(true);
            }
        });
        Cname.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                try {
                    db dbH = db.getInstance();
                    competitions = dbH.getAllCompetitions();
                    DefaultComboBoxModel clist=new DefaultComboBoxModel();
                    Cname.setModel(clist);
                    for (Competition competition : competitions) {
                        clist.addElement(competition.full_name);
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        sel_discp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Discp window = new Discp();
                window.pack();
                window.setVisible(true);
            }
        });
    }

}
