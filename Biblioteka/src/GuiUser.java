import Klasy.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class GuiUser extends JFrame{
    private JPanel jpanel;
    private JTable table1;
    private JTextField stringTytul;
    private JTextField stringImie;
    private JTextField stringNazwisko;
    private JButton wyszukajButton;
    private JButton aktywneWyporzyczeniaButton;
    private JButton historiaButton;
    User user;
    DefaultTableModel data;
    public GuiUser(String u,String p) {
        user=new User(u,p);
        this.setContentPane(this.jpanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000,500);
        wyszukajButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wyswietl(user.wyszukaj(stringTytul.getText(),stringImie.getText(),stringNazwisko.getText()));
            }
        });
        aktywneWyporzyczeniaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wyswietl(user.wyporzyczoneKsiazki());
            }
        });
        historiaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wyswietl(user.historiaWyporzyczen());
            }
        });
    }
    public void wyswietl(ResultSet r){
        try {
            ResultSetMetaData rsmd = r.getMetaData();
            String[] row=new String[rsmd.getColumnCount()];
            for (int i=1;i<rsmd.getColumnCount()+1;i++){
                row[i-1]=rsmd.getColumnName(i);
            }
            String[][] column=new String[0][0];
            data=new DefaultTableModel(column,row);
            String[] roww=new String[rsmd.getColumnCount()];
            while (r.next()){
                for (int i=1;i<rsmd.getColumnCount()+1;i++){
                    roww[i-1]=r.getString(i);
                }
                data.addRow(roww);
            }
            table1.setModel(data);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
