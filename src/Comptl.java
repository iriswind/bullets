import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.awt.Dimension;


public class Comptl extends JFrame{
    private JList complist;
    private JLabel lb1;
    private JLabel lb2;
    private JLabel lb3;
    private JButton add;
    private JButton upd;
    private JTextField title;
    private JTextField location;
    private JTextField c_date;
    private JPanel panel1;
    private JLabel addlb;
    private JLabel updlb;
    private JLabel idlb;
    private JButton close;
    private DefaultListModel listModel;
    private List<Competition> competitions;
    private DefaultComboBoxModel clist;


    public Comptl(List<Competition> competitions,DefaultListModel listModel,DefaultComboBoxModel clist) {
        this.competitions=competitions;
        this.listModel=listModel;
        this.clist=clist;
        this.getContentPane().add(panel1);
        panel1.setPreferredSize(new Dimension(600, 300));
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        complist.setModel(listModel);
        complist.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Integer idx=complist.getSelectedIndex();
                title.setText(competitions.get(idx).title);
                location.setText(competitions.get(idx).location);
                c_date.setText(competitions.get(idx).c_date);
                upd.setEnabled(true);
            }
        });
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Integer idx=complist.getSelectedIndex();
                if (title.getText().trim().length() == 0)
                    {
                        addlb.setText("Укажите название мероприятия");
                    }
                else
                    {
                        if (location.getText().trim().length() == 0)
                            {
                                addlb.setText("Укажите место проведения мероприятия");
                            }
                        else
                            {
                                if (c_date.getText().trim().length() == 0)
                                    {
                                        addlb.setText("Укажите дату проведения мероприятия");
                                    }
                                else {
                                    if (Utils.isValid(c_date.getText().trim()))
                                        {
                                        String full_name = c_date.getText().trim() + " " + title.getText().trim() + ", " + location.getText().trim();
                                        competitions.get(idx).title = title.getText().trim();
                                        competitions.get(idx).location = location.getText().trim();
                                        competitions.get(idx).c_date = c_date.getText().trim();
                                        competitions.get(idx).full_name = full_name;
                                        Competition cmp = new Competition(competitions.size() + 1, title.getText().trim(), location.getText().trim(), c_date.getText().trim(), full_name);
                                        try
                                            {
                                            db dbH = db.getInstance();
                                            dbH.addCompetitions(cmp);
                                            addlb.setText("Запись добавлена");
                                            competitions.add(cmp);
                                            listModel.addElement(full_name);
                                            clist.addElement(full_name);
                                            }
                                        catch (SQLException e)
                                            {
                                                e.printStackTrace();
                                            }
                                        }
                                    else
                                        {
                                        updlb.setText("Неверная дата мероприятия - дд.мм.гггг");
                                        }
                                    }
                                              }
                                          }
                                      }
                                  }
        );
        upd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Integer idx=complist.getSelectedIndex();
                if (title.getText().trim().length() == 0)
                    {
                        updlb.setText("Укажите название мероприятия");
                    }
                else
                    {
                        if (location.getText().trim().length() == 0)
                            {
                                updlb.setText("Укажите место проведения мероприятия");
                            }
                        else
                            {
                                if (c_date.getText().trim().length() == 0)
                                    {
                                        updlb.setText("Укажите дату проведения мероприятия");
                                    }
                                else
                                    {
                                        if (Utils.isValid(c_date.getText().trim()))
                                            {
                                                String full_name=c_date.getText().trim() + " " + title.getText().trim() +", " + location.getText().trim();
                                                competitions.get(idx).title=title.getText().trim();
                                                competitions.get(idx).location=location.getText().trim();
                                                competitions.get(idx).c_date=c_date.getText().trim();
                                                competitions.get(idx).full_name=full_name;
                                                try {
                                                        db dbH = db.getInstance();
                                                        dbH.updCompetitions(competitions.get(idx));
                                                        updlb.setText("Запись обновлена");
                                                        listModel.setElementAt(full_name,idx);
                                                        clist.removeElementAt(idx);
                                                        clist.insertElementAt(full_name,idx);
                                                        }
                                                    catch (SQLException e) {e.printStackTrace();}
                                                    }
                                        else
                                            {
                                                updlb.setText("Неверная дата мероприятия - дд.мм.гггг");
                                            }
                                                    }
                                          }
                                      }
                                  }
                              }
        );
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
