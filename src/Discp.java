import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.List;

public class Discp extends JFrame{
    private JList discplist;
    private JComboBox gdc;
    private JPanel panel1;
    private JLabel glb;
    private JLabel idl;
    private JTextField ageto;
    private JTextField agef;
    private JTextField wdc;
    private JButton upd;
    private JButton add;
    private JButton close;
    private JLabel reslb;

    public Discp() {
        this.getContentPane().add(panel1);
        idl.setVisible(false);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        DefaultListModel listModel = new DefaultListModel ();
        discplist.setModel(listModel);
        try {
            db dbH = db.getInstance();
            List<Discipline> disciplines = dbH.getAllDisciplines();
            for (Discipline discipline : disciplines) {
                listModel.addElement(discipline.toString());
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }


        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        discplist.addListSelectionListener(new ListSelectionListener() {
            String[] subStr;
            String[] subS;
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Object element = discplist.getSelectedValue();
                subStr=element.toString().replace("лет","").replace("кг","").split("\\s\\|\\s");
                idl.setText(subStr[0]);
                if (subStr[2].contains("-"))
                    {
                        subS=subStr[2].split("-");
                        agef.setText(subS[0]);
                        ageto.setText(subS[1]);
                    }
                else
                    {
                        agef.setText(subStr[2]);
                        ageto.setText(subStr[2]);
                    }
                if (subStr[1].trim().toLowerCase().equals("ката"))
                    {
                        gdc.setSelectedIndex(0);
                        wdc.setText("0");
                    }
                else {
                    gdc.setSelectedIndex(1);
                    wdc.setText(subStr[3]);
                    }
            }
        });
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String st = "0 | " + gdc.getSelectedItem().toString();
                if ((agef.getText().trim().length() == 0) || (ageto.getText().trim().length() == 0) || (Integer.parseInt(agef.getText().trim())>Integer.parseInt(ageto.getText().trim())) ||
                        (Integer.parseInt(agef.getText().trim())<1) || (Integer.parseInt(ageto.getText().trim())<5) ) {
                    reslb.setText("Укажите рамки возрастной группы");
                    }
                else {
                    if (Integer.parseInt(agef.getText().trim())<Integer.parseInt(ageto.getText().trim()))
                        {
                            st=st+" | " + agef.getText().trim() + "-" + ageto.getText().trim() + "лет";
                        }
                    else
                        {
                            st=st+" | " + agef.getText().trim() + "лет";
                        }
                    if  ( (gdc.getSelectedIndex() == 1) & ((wdc.getText().trim().length() == 0) | (Integer.parseInt(wdc.getText().trim())<1)) ) {
                         reslb.setText("Укажите весовую категорию");
                        }
                    else {
                        Integer w=0;
                        if (gdc.getSelectedItem().toString().toLowerCase().equals("ката")) {
                            w = 0;
                            }
                        else {
                            w = Integer.parseInt(wdc.getText().trim());
                            st=st+" | " + wdc.getText().trim() + "кг";
                            }
                        System.out.println(st);
                        Discipline discp = new Discipline(0,gdc.getSelectedItem().toString().trim(), Integer.parseInt(agef.getText().trim()), Integer.parseInt(ageto.getText().trim()), w);
                            try {
                                db dbH = db.getInstance();
                                dbH.addDiscipline(discp);
                                reslb.setText("Запись добавлена");
                                listModel.addElement(st);
                                } catch (SQLException e1) {
                                    e1.printStackTrace();
                                }

                        }

                }
            }
        });
    }
}
