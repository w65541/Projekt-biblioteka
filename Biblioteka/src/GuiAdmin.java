import Klasy.Admin;
import Klasy.Autor;
import Klasy.Czytelnik;
import Klasy.Tytul;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class GuiAdmin extends JFrame{
    private JPanel jpanel;
    private JTable table1;
    private JTextField idTytul;
    private JTextField stringTytul;
    private JTextField idAutor;
    private JTextField stringImie;
    private JTextField stringNazwisko;
    private JButton dodajTytulButton;
    private JButton dodajAutoraButton;
    private JButton dodajKsiazkeButton;
    private JTextField idBook;
    private JButton wyszukajButton;
    private JButton Inwentarz;
    private JButton aktualneWyporzyczeniaButton;
    private JButton dodajWyporzyczenieButton;
    private JButton zakonczWyporzyczenieButton;
    private JTextField idCzytelnik;
    private JTextField nowyPlik;
    private JButton doCsvButton;
    private JTabbedPane tabbedPane1;
    private JButton dodajZCsvButton;
    private JTextField zPlik;
    private JComboBox tabele;
    private JTextField usunId;
    private JButton usunButton;
    private JTextField warunek;
    private JButton button1;
    private JTextArea sqlCustom;
    private JButton wykonajButton;
    private JComboBox wyburTabeli;
    private JButton odsetkiButton;
    private JTextField czytId;
    private JTextField czytImie;
    private JTextField czytNaz;
    private JTextField czytEmail;
    private JTextField czytTel;
    private JTextField czytAdres;
    private JTextField czytUser;
    private JButton updateButton;
    private JButton zmodyfikujTytulButton;
    private JButton zmodyfikujAutoraButton;
    private JButton zmodyfikujKsiążkęButton;
    private JTextField czytPwd;
    private JButton dodajButton;
    private JButton aktywneButton;
    private JButton historiaButton;
    DefaultTableModel data;
    Admin admin;
    int limit=14,kara=10;
    public GuiAdmin(String u,String p) {
        admin=new Admin(u,p);
        this.setContentPane(this.jpanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000,500);
        //table1.setModel(data);
        final ResultSet[] r = new ResultSet[1];
        Path path= Paths.get("C:\\Users\\HP\\Documents\\Projekt biblioteka\\Projekt-biblioteka\\Biblioteka\\src\\CSV");
        dodajTytulButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!stringTytul.getText().isBlank()){
                if(!idAutor.getText().isBlank()){
                admin.dodajTytul(stringTytul.getText(),Integer.parseInt(idAutor.getText()));
            }else if(!stringImie.getText().isBlank() || !stringNazwisko.getText().isBlank()){
            admin.dodajTytul(stringTytul.getText(),new Autor(admin.getC(),stringImie.getText(),stringNazwisko.getText()).getId());
            }}
            }
        });
        Inwentarz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                r[0] =admin.inwentarz();
                wyswietl(r[0]);
                r[0] =admin.inwentarz();
            }
        });
        wyszukajButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                r[0]=admin.wyszukaj(stringTytul.getText(),stringImie.getText(),stringNazwisko.getText());
                wyswietl(r[0]);
                r[0]=admin.wyszukaj(stringTytul.getText(),stringImie.getText(),stringNazwisko.getText());
            }
        });
        aktualneWyporzyczeniaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                r[0]=admin.aktualneWyporzyczenia();
                wyswietl(r[0]);
                r[0]=admin.aktualneWyporzyczenia();
            }
        });
        dodajWyporzyczenieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               if(!idBook.getText().isBlank() || !idCzytelnik.getText().isBlank()) admin.dodajWyporzyczenie(Integer.parseInt(idBook.getText()),Integer.parseInt(idCzytelnik.getText()));
            }
        });
        zakonczWyporzyczenieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!idBook.getText().isBlank() || !idCzytelnik.getText().isBlank()) admin.zakonczWyporzyczenie(Integer.parseInt(idBook.getText()),Integer.parseInt(idCzytelnik.getText()));
            }
        });
        dodajKsiazkeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!idTytul.getText().isBlank()){
                    admin.dodajKsiazke(Integer.parseInt(idTytul.getText()));
                } else if (!stringTytul.getText().isBlank()) {
                    admin.dodajKsiazke(new Tytul(admin.getC(),stringTytul.getText()).getId());
                }
            }
        });
        dodajAutoraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!stringImie.getText().isBlank() || !stringNazwisko.getText().isBlank()) admin.dodajAutor(stringImie.getText(),stringNazwisko.getText());
            }
        });
        doCsvButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(r[0]!=null && !nowyPlik.getText().isBlank()) admin.zapiszDoCSV(r[0],path.resolve(nowyPlik.getText()+".csv"));
            }
        });
        dodajZCsvButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!zPlik.getText().isBlank()) admin.dodajZCSV(path.resolve(zPlik.getText()+".csv"));
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                r[0]=admin.wyszukajtabela((String) wyburTabeli.getSelectedItem(),warunek.getText());
                wyswietl(r[0]);
                r[0]=admin.wyszukajtabela((String) wyburTabeli.getSelectedItem(),warunek.getText());
            }
        });
        usunButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!usunId.getText().isBlank())admin.usun((String) tabele.getSelectedItem(),Integer.parseInt(usunId.getText()));
            }
        });
        wykonajButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               if(!sqlCustom.getText().isBlank()){ r[0]=admin.wykonajSql(sqlCustom.getText());
                if(r[0]!=null) {wyswietl(r[0]);
                    r[0]=admin.wykonajSql(sqlCustom.getText());
                }}
            }
        });
        odsetkiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin.odsetki(kara,limit);
            }
        });
        zmodyfikujTytulButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!idTytul.getText().isBlank()){
                    Tytul tytul=new Tytul(admin.getC(),Integer.parseInt(idTytul.getText()));
                    if(!idAutor.getText().isBlank())tytul.setIdA(Integer.parseInt(idAutor.getText()));
                    if(!stringTytul.getText().isBlank())tytul.setTytul(stringTytul.getText());
                }
            }
        });
        dodajButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!czytUser.getText().isBlank() || !czytPwd.getText().isBlank()){
                    admin.dodajCzytelnika(czytImie.getText(),czytNaz.getText(),czytEmail.getText(),czytTel.getText(),czytAdres.getText(),czytUser.getText(),czytPwd.getText());
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!czytId.getText().isBlank()){
                    Czytelnik czytelnik=new Czytelnik(admin.getC(),Integer.parseInt(czytId.getText()));
                    if(!czytImie.getText().isBlank()) czytelnik.setImie(czytImie.getText());
                    if(!czytNaz.getText().isBlank()) czytelnik.setNazwisko(czytNaz.getText());
                    if(!czytEmail.getText().isBlank()) czytelnik.setEmail(czytEmail.getText());
                    if(!czytTel.getText().isBlank()) czytelnik.setTelefon(czytTel.getText());
                    if(!czytAdres.getText().isBlank()) czytelnik.setAdres(czytAdres.getText());
                    if(!czytUser.getText().isBlank()) czytelnik.setUsername(czytUser.getText());
                    if(!czytPwd.getText().isBlank()) czytelnik.setPassword(czytPwd.getText());
                }
            }
        });
        aktywneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!czytId.getText().isBlank()){
                    Czytelnik czytelnik=new Czytelnik(admin.getC(),Integer.parseInt(czytId.getText()));
                    r[0]=czytelnik.aktwyneWyporzyczenia();
                    wyswietl(r[0]);
                    r[0]=czytelnik.aktwyneWyporzyczenia();
                }
            }
        });
        historiaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!czytId.getText().isBlank()){
                    Czytelnik czytelnik=new Czytelnik(admin.getC(),Integer.parseInt(czytId.getText()));
                    r[0]=czytelnik.historiaWyporzyczenia();
                    wyswietl(r[0]);
                    r[0]=czytelnik.historiaWyporzyczenia();
                }
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
