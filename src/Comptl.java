import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.sql.Date;
import java.text.SimpleDateFormat;


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

    public Comptl() {
        this.getContentPane().add(panel1);
        idlb.setVisible(false);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        try {
            db dbH = db.getInstance();
            List<Competition> competitions = dbH.getAllCompetitions();
            DefaultListModel listModel = new DefaultListModel ();
            complist.setModel(listModel);
            for (Competition competition : competitions) {
                listModel.addElement(competition.toString());
                }
            }
         catch (SQLException e) {
            e.printStackTrace();
        }
        complist.addListSelectionListener(new ListSelectionListener() {
            String[] subStr;
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Object element = complist.getSelectedValue();
                subStr=element.toString().replace("Мероприятие:","").replace("Место проведения:","").replace("Дата:","").split("\\s\\|\\s");
                idlb.setText(subStr[0].trim());
                title.setText(subStr[1].trim());
                location.setText(subStr[2].trim());
                c_date.setText(subStr[3].trim());
                upd.setEnabled(true);
            }
        });
        upd.addActionListener(new ActionListener() {
                                  @Override
                                  public void actionPerformed(ActionEvent actionEvent) {
                                      if (title.getText().trim().length() == 0) {
                                          updlb.setText("Укажите название мероприятия");
                                      } else {
                                          if (location.getText().trim().length() == 0) {
                                              updlb.setText("Укажите место проведения мероприятия");
                                          } else {
                                              if (c_date.getText().trim().length() == 0) {
                                                  updlb.setText("Укажите дату проведения мероприятия");
                                              }
                                              else {
                                                  if (Utils.isValid(c_date.getText().trim(),"dd.MM.yyyy")==false)
                                                        {updlb.setText("Неверная дата мероприятия - дд.мм.гггг");}
                                                  else {
                                                      Competition cmp=new Competition(Integer.parseInt(idlb.getText()), title.getText().trim(), location.getText().trim(),c_date.getText().trim());
                                                    try {
                                                        db dbH = db.getInstance();
                                                        dbH.updCompetitions(cmp);
                                                        updlb.setText("Запись обновлена");
                                                        }
                                                    catch (SQLException e) {e.printStackTrace();}
                                                    }
                                                    }
                                          }
                                      }
                                  }
                              }
        );
                add.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        if (title.getText().trim().length() == 0) {
                            addlb.setText("Укажите название мероприятия");
                        } else {
                            if (location.getText().trim().length() == 0) {
                                addlb.setText("Укажите место проведения мероприятия");
                            } else {
                                if (c_date.getText().trim().length() == 0) {
                                    addlb.setText("Укажите дату проведения мероприятия");
                                }
                                else {
                                    if (Utils.isValid(c_date.getText().trim(),"dd.MM.yyyy")==false)
                                    {addlb.setText("Неверная дата мероприятия - дд.мм.гггг");}
                                    else {
                                        Competition cmp=new Competition(0, title.getText().trim(), location.getText().trim(),c_date.getText().trim());
                                        try {
                                            db dbH = db.getInstance();
                                            dbH.addCompetitions(cmp);
                                            addlb.setText("Запись добавлена");
                                        }
                                        catch (SQLException e) {e.printStackTrace();}
                                    }
                                }
                            }
                        }
                    }
                }
         );
    }
}
