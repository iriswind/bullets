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

public class Reqst extends JFrame{
    private JLabel complb;
    private JList rlist;
    private JTextField r_fio;
    private JTextField r_date;
    private JTextField r_club;
    private JTextField r_trainer;
    private JTextField r_level;
    private JTextField r_weight;
    private JCheckBox r_kata;
    private JCheckBox r_kumite;
    private JButton add;
    private JButton upd;
    private JButton del;
    private JButton close;
    private JPanel panel1;
    private JLabel addlb;
    private JLabel updlb;
    private JLabel dellb;
    private Competition comp;
    private DefaultListModel listModel;
    private List<Request> requests;

    public Reqst(Competition comp){
        this.comp=comp;
        this.getContentPane().add(panel1);
        panel1.setPreferredSize(new Dimension(600, 300));
        complb.setText(comp.full_name);
        try
        {
            db dbH = db.getInstance();
            this.requests=dbH.getRequest(comp.id);
            complb.setText(comp.full_name);
            listModel=new DefaultListModel();
            rlist.setModel(listModel);
            for (Request request : requests)
                {
                    listModel.addElement(request.fio);
                }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        rlist.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Integer idx=rlist.getSelectedIndex();
                Request req = requests.get(idx);
                r_fio.setText(req.fio);
                r_date.setText(req.r_date);
                r_club.setText(req.club);
                r_trainer.setText(req.trainer);
                r_level.setText(req.level);
                r_weight.setText(req.weight.toString());
                if (req.kata.toLowerCase().equals("true"))
                    {
                        r_kata.setSelected(true);
                    }
                if (req.kumite.toLowerCase().equals("true"))
                    {
                        r_kumite.setSelected(true);
                    }

            }
        });
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (r_fio.getText().trim().length() == 0)
                    {
                        addlb.setText("Укажите ФИО спортсмена");
                    }
                else
                    {
                        String fio=r_fio.getText().trim();
                        if ((r_date.getText().trim().length() == 0) || !(Utils.isValid(r_date.getText().trim())))
                            {
                                addlb.setText("Укажите дату рождения спортсмена");
                            }
                        else
                            {
                                String rdate=r_date.getText().trim();
                                if (r_club.getText().trim().length() == 0)
                                    {
                                        addlb.setText("Укажите клуб спортсмена");
                                    }
                                else
                                    {
                                        String club=r_club.getText().trim();
                                        if (r_trainer.getText().trim().length() == 0)
                                            {
                                                addlb.setText("Укажите тренера спортсмена");
                                            }
                                        else
                                            {
                                                String trainer=r_trainer.getText().trim();
                                                if (r_level.getText().trim().length() == 0)
                                                    {
                                                        addlb.setText("Укажите кю/дан спортсмена");
                                                    }
                                                else
                                                    {
                                                        String lvl=r_level.getText().trim();
                                                        if (r_weight.getText().trim().length() == 0)
                                                            {
                                                                addlb.setText("Укажите вес спортсмена");
                                                            }
                                                        else
                                                            {
                                                                Integer wght=Integer.parseInt(r_weight.getText().trim());
                                                                String kata="false";
                                                                String kumite="false";
                                                                if (!(r_kata.isSelected() && !(r_kumite.isSelected())))
                                                                    {
                                                                        addlb.setText("Отметьте группу дисциплин");
                                                                    }
                                                                else
                                                                    {
                                                                        if (r_kata.isSelected())
                                                                            {
                                                                                kata="true";
                                                                            }
                                                                        if (r_kumite.isSelected())
                                                                        {
                                                                            kumite="true";
                                                                        }
                                                                        Request req = new Request(comp.id,requests.size()+1,fio,club,rdate,lvl,wght,kata,kumite,trainer);
                                                                        try {
                                                                            db dbH = db.getInstance();
                                                                            Integer idm=dbH.addRequest(req);
                                                                            addlb.setText("Запись добавлена");
                                                                            req.id=idm;
                                                                            requests.add(req);
                                                                            listModel.addElement(fio);
                                                                        } catch (SQLException e1) {
                                                                            e1.printStackTrace();
                                                                        }
                                                                    }
                                                            }
                                                    }
                                            }
                                    }
                            }
                    }


            }
        });
        upd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (r_fio.getText().trim().length() == 0)
                {
                    addlb.setText("Укажите ФИО спортсмена");
                }
                else
                {
                    String fio=r_fio.getText().trim();
                    if ((r_date.getText().trim().length() == 0) || !(Utils.isValid(r_date.getText().trim())))
                    {
                        addlb.setText("Укажите дату рождения спортсмена");
                    }
                    else
                    {
                        String rdate=r_date.getText().trim();
                        if (r_club.getText().trim().length() == 0)
                        {
                            addlb.setText("Укажите клуб спортсмена");
                        }
                        else
                        {
                            String club=r_club.getText().trim();
                            if (r_trainer.getText().trim().length() == 0)
                            {
                                addlb.setText("Укажите тренера спортсмена");
                            }
                            else
                            {
                                String trainer=r_trainer.getText().trim();
                                if (r_level.getText().trim().length() == 0)
                                {
                                    addlb.setText("Укажите кю/дан спортсмена");
                                }
                                else
                                {
                                    String lvl=r_level.getText().trim();
                                    if (r_weight.getText().trim().length() == 0)
                                    {
                                        addlb.setText("Укажите вес спортсмена");
                                    }
                                    else
                                    {
                                        Integer wght=Integer.parseInt(r_weight.getText().trim());
                                        String kata="false";
                                        String kumite="false";
                                        if (!(r_kata.isSelected() && !(r_kumite.isSelected())))
                                        {
                                            addlb.setText("Отметьте группу дисциплин");
                                        }
                                        else
                                        {
                                            if (r_kata.isSelected())
                                            {
                                                kata="true";
                                            }
                                            if (r_kumite.isSelected())
                                            {
                                                kumite="true";
                                            }
                                            Request req = new Request(comp.id,requests.size()+1,fio,club,rdate,lvl,wght,kata,kumite,trainer);
                                            try {
                                                db dbH = db.getInstance();
                                                Integer idm=dbH.addRequest(req);
                                                addlb.setText("Запись добавлена");
                                                req.id=idm;
                                                requests.add(req);
                                                listModel.addElement(fio);
                                            } catch (SQLException e1) {
                                                e1.printStackTrace();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }


            }
        });
    }
}
