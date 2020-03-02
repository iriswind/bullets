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
    private List<Discipline> disciplines;
    private DefaultListModel listModel;

    public Discp(List<Discipline> disciplines,DefaultListModel listModel) {
        this.getContentPane().add(panel1);
        this.disciplines=disciplines;
        this.listModel=listModel;
        idl.setVisible(false);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        discplist.setModel(listModel);
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        discplist.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Integer idx=discplist.getSelectedIndex();
                if (disciplines.get(idx).discp_group.toLowerCase().equals("ката"))
                {
                    gdc.setSelectedIndex(0);
                    wdc.setText("0");
                }
                else {
                    gdc.setSelectedIndex(1);
                    wdc.setText(disciplines.get(idx).weight.toString());
                }
                agef.setText(disciplines.get(idx).age_low.toString());
                ageto.setText(disciplines.get(idx).age_high.toString());
            }
        });
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer idx=discplist.getSelectedIndex();
                String full_name=disciplines.get(idx).discp_group;
                if ((agef.getText().trim().length() == 0) || (ageto.getText().trim().length() == 0) || (Integer.parseInt(agef.getText().trim())>Integer.parseInt(ageto.getText().trim())) ||
                        (Integer.parseInt(agef.getText().trim())<1) || (Integer.parseInt(ageto.getText().trim())<5) )
                    {
                        reslb.setText("Укажите рамки возрастной группы");
                    }
                else
                    {
                        if  ( (gdc.getSelectedIndex() == 1) & ((wdc.getText().trim().length() == 0) | (Integer.parseInt(wdc.getText().trim())<1)) )
                            {
                            reslb.setText("Укажите весовую категорию");
                            }
                        else
                            {
                                if (Integer.parseInt(agef.getText().trim()) == Integer.parseInt(ageto.getText().trim()))
                                    {
                                        full_name = full_name + " " + agef.getText().trim() + Utils.getstrage(Integer.parseInt(agef.getText().trim()));
                                    }
                                else
                                    {
                                        full_name = full_name + " " + agef.getText().trim() + "-" + ageto.getText().trim() + Utils.getstrage(Integer.parseInt(ageto.getText().trim()));
                                    }
                                if (gdc.getSelectedIndex() == 1)
                                    {
                                        full_name = full_name + " " + wdc.getText().trim() + "кг";
                                    }
                                Discipline discp = new Discipline(disciplines.size()+1,gdc.getSelectedItem().toString().trim(), Integer.parseInt(agef.getText().trim()), Integer.parseInt(ageto.getText().trim()), Integer.parseInt(wdc.getText().trim()),full_name);
                                try {
                                    db dbH = db.getInstance();
                                    dbH.addDiscipline(discp);
                                    reslb.setText("Запись добавлена");
                                    disciplines.add(discp);
                                    listModel.addElement(full_name);
                                } catch (SQLException e1) {
                                    e1.printStackTrace();
                                }
                            }
                    }
            }
        });
        upd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer idx=discplist.getSelectedIndex();
                String full_name=disciplines.get(idx).discp_group;
                if ((agef.getText().trim().length() == 0) || (ageto.getText().trim().length() == 0) || (Integer.parseInt(agef.getText().trim())>Integer.parseInt(ageto.getText().trim())) ||
                        (Integer.parseInt(agef.getText().trim())<1) || (Integer.parseInt(ageto.getText().trim())<5) )
                {
                    reslb.setText("Укажите рамки возрастной группы");
                }
                else
                {
                    if  ( (gdc.getSelectedIndex() == 1) & ((wdc.getText().trim().length() == 0) | (Integer.parseInt(wdc.getText().trim())<1)) )
                    {
                        reslb.setText("Укажите весовую категорию");
                    }
                    else
                    {
                        if (Integer.parseInt(agef.getText().trim()) == Integer.parseInt(ageto.getText().trim()))
                        {
                            full_name = full_name + " " + agef.getText().trim() + Utils.getstrage(Integer.parseInt(agef.getText().trim()));
                        }
                        else
                        {
                            full_name = full_name + " " + agef.getText().trim() + "-" + ageto.getText().trim() + Utils.getstrage(Integer.parseInt(ageto.getText().trim()));
                        }
                        if (gdc.getSelectedIndex() == 1)
                        {
                            full_name = full_name + " " + wdc.getText().trim() + "кг";
                        }
                        disciplines.get(idx).discp_group=gdc.getSelectedItem().toString().trim();
                        disciplines.get(idx).age_low=Integer.parseInt(agef.getText().trim());
                        disciplines.get(idx).age_high=Integer.parseInt(ageto.getText().trim());
                        disciplines.get(idx).weight=Integer.parseInt(wdc.getText().trim());
                        disciplines.get(idx).full_name=full_name;
                        try {
                            db dbH = db.getInstance();
                            dbH.updDiscipline(disciplines.get(idx));
                            reslb.setText("Запись обновлено");
                            listModel.setElementAt(full_name,idx);
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}
