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


public class Orgp  extends JFrame{
    private JPanel panel1;
    private JList jlist;
    private JTextField cfio;
    private JTextField clevel;
    private JTextField ccat;
    private JButton add;
    private JButton upd;
    private JButton close;
    private JLabel addlb;
    private JLabel updlb;
    private DefaultListModel listModel;
    private List<Orgperson> orgpersons;
    private DefaultComboBoxModel clist;


    public Orgp(List<Orgperson> orgpersons,DefaultListModel listModel,DefaultComboBoxModel clist) {
        this.orgpersons=orgpersons;
        this.listModel=listModel;
        this.clist=clist;
        this.getContentPane().add(panel1);
        panel1.setPreferredSize(new Dimension(600, 300));
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        jlist.setModel(listModel);
        jlist.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Integer idx=jlist.getSelectedIndex();
                cfio.setText(orgpersons.get(idx).fio);
                clevel.setText(orgpersons.get(idx).level);
                ccat.setText(orgpersons.get(idx).jcategory);
                upd.setEnabled(true);
            }
        });
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer idx=jlist.getSelectedIndex();
                if (cfio.getText().trim().length() == 0)
                    {
                        addlb.setText("Укажите ФИО");
                    }
                else
                    {
                        String full_name=cfio.getText().trim();
                        if (clevel.getText().trim().length()>0)
                            {
                                full_name = full_name + ", " + clevel.getText().trim();
                            }
                        if (ccat.getText().trim().length()>0)
                            {
                                full_name = full_name + ", " + ccat.getText().trim();
                            }
                        Orgperson op = new Orgperson(orgpersons.size() + 1, cfio.getText().trim(), clevel.getText().trim(), ccat.getText().trim(), full_name);
                        try
                        {
                            db dbH = db.getInstance();
                            dbH.addOrgpersons(op);
                            addlb.setText("Запись добавлена");
                            orgpersons.add(op);
                            listModel.addElement(full_name);
                            clist.addElement(full_name);
                        }
                        catch (SQLException e1)
                        {
                            e1.printStackTrace();
                        }

                    }
            }
        });
        upd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer idx=jlist.getSelectedIndex();
                if (cfio.getText().trim().length() == 0)
                {
                    updlb.setText("Укажите ФИО");
                }
                else
                {
                    String full_name=cfio.getText().trim();
                    if (clevel.getText().trim().length()>0)
                    {
                        full_name = full_name + ", " + clevel.getText().trim();
                    }
                    if (ccat.getText().trim().length()>0)
                    {
                        full_name = full_name + ", " + ccat.getText().trim();
                    }
                        orgpersons.get(idx).fio=cfio.getText().trim();
                        orgpersons.get(idx).level=clevel.getText().trim();
                        orgpersons.get(idx).jcategory=ccat.getText().trim();
                        orgpersons.get(idx).full_name=full_name;
                    try
                    {
                        db dbH = db.getInstance();
                        dbH.updOrgpersons(orgpersons.get(idx));
                        updlb.setText("Запись обновлена");
                        listModel.setElementAt(full_name,idx);
                        clist.removeElementAt(idx);
                        clist.insertElementAt(full_name,idx);
                    }
                    catch (SQLException e1)
                    {
                        e1.printStackTrace();
                    }

                }
            }
        });
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
