//import java.beans.Statement;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import Klasy.Admin;
import Klasy.Ksiazka;
import Klasy.User;
import Klasy.Wyporzyczenia;
import org.apache.commons.csv.*;

import javax.swing.*;

//user wyszukiwarka
//admin przerabianie wyników wyszukiwania na csv, dodawanie użytkownika
public class Main {
    public static void main(String[] args) {
        try{
        int m1,m2;
        Admin admin;
        User user;
            //JFrame a = new GuiUser("user","user");
            //JFrame a = new GuiAdmin("root","root");
           // a.setVisible(true);

        String username,password;
            Scanner in = new Scanner(System.in);

             System.out.println("0 - Logowanie do admina\n1 - Logowanie do usera\n9 - wyjście");
             m1=in.nextInt();
             switch (m1){
                 case 0:
                     System.out.print("Username: ");
                     username=in.next();
                     System.out.print("Password: ");
                     password=in.next();
                     try {
                         admin=new Admin(username,password);
                         JFrame a = new GuiAdmin(admin);
                         a.setVisible(true);
                     }catch (SQLException e){
                         System.out.println("Niepoprawny login lub hasło");
                         return;
                     }

                     break;

                 case 1:
                        System.out.print("Username: ");
                     username=in.next();
                     System.out.print("Password: ");
                     password=in.next();
                     try {
                         user=new User(username,password);
                         JFrame b = new GuiUser(user);
                         b.setVisible(true);
                     }catch (SQLException e){
                         System.out.println("Niepoprawny login lub hasło");
                         return;
                     }

                     break;
                 case 9: return;
                 default:

             }


        }catch (Exception e){
            e.printStackTrace();
        }
    }
//SELECT Tytuł,Imie,Nazwisko FROM biblioteka.tytuł left join biblioteka.autor on biblioteka.autor.id=idAutor;
    //SELECT Tytuł,imie,nazwisko,count(książki.id) as 'ilość' FROM książki left join tytuł on tytuł.id=idTytuł left join autor on autor.id=idAutor group by tytuł.id
    //SELECT Tytuł,count(czyWyporzyczona) FROM tytuł left join książki on tytuł.id=książki.idTytuł where czyWyporzyczona=0 and idTytuł=7 and idAutor=5 group by Tytuł
//metoda naliczająca odsetki za przetrzymywanie książki
public static void wyswietl(ResultSet r){
        try {
            ResultSetMetaData rsmd = r.getMetaData();
            for (int i=1;i<rsmd.getColumnCount()+1;i++){
                System.out.print("|"+rsmd.getColumnName(i));
            }
            System.out.println("|");
            while (r.next()){
                for (int i=1;i<rsmd.getColumnCount()+1;i++){
                    System.out.print("|"+r.getString(i));
                }
                System.out.println("|");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

}

}