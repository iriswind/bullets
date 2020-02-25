import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.List;

public class Discp extends JFrame{
    private JList dicplist;
    private JComboBox gdc;
    private JPanel panel1;
    private JLabel glb;
    private JTextField ageto;
    private JTextField agef;
    private JTextField wdc;
    private JButton upd;
    private JButton add;
    private JButton close;
    private JLabel reslb;

    public Discp() {
        this.getContentPane().add(panel1);
        //idlb.setVisible(false);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        //DefaultListModel listModel = new DefaultListModel();
        DefaultListModel listModel = new DefaultListModel ();
        dicplist.setModel(listModel);
        try {
            db dbH = db.getInstance();
            List<Discipline> discipline = dbH.getAllDisciplines();
            for (Discipline discipline : discipline) {
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
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((agef.getText().trim().length() == 0) || (ageto.getText().trim().length() == 0) || (Integer.parseInt(agef.getText().trim())>Integer.parseInt(ageto.getText().trim())) ||
                        (Integer.parseInt(agef.getText().trim())<1) || (Integer.parseInt(ageto.getText().trim())<5) ) {
                    reslb.setText("Укажите рамки возрастной группы");
                    }
                else {
                    if  ( (gdc.getSelectedIndex() == 1) & ((wdc.getText().trim().length() == 0) | (Integer.parseInt(wdc.getText().trim())<1)) ) {
                         reslb.setText("Укажите весовую категорию");
                        }
                    else {
                        Integer w=0;
                        if (gdc.getSelectedItem().toString().toLowerCase() == "ката") {
                            w = 0;
                            }
                        else {
                            w = Integer.parseInt(wdc.getText().trim());
                            Discipline discp = new Discipline(gdc.getSelectedItem().toString().trim(), Integer.parseInt(agef.getText().trim()), Integer.parseInt(ageto.getText().trim()), w);
                            try {
                                db dbH = db.getInstance();
                                dbH.addDiscipline(discp);
                                reslb.setText("Запись добавлена");
                                //listModel.setElementAt(Integer.parseInt(idlb.getText()) + " | Мероприятие: " + title.getText().trim() +" | Место проведения: " + location.getText().trim() + " | Дата: " + c_date.getText().trim(),complist.getSelectedIndex());
                                } catch (SQLException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }

                }
            }
        });
    }
}
