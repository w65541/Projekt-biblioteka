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

import Klasy.Admin;
import Klasy.Ksiazka;
import Klasy.User;
import Klasy.Wyporzyczenia;
import org.apache.commons.csv.*;

//user wyszukiwarka
//admin przerabianie wyników wyszukiwania na csv, dodawanie użytkownika
public class Main {
    public static void main(String[] args) {
        try{
        Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteka","root","root");
        Statement statement=connection.createStatement();
            Admin admin=new Admin("root","root");/*
        ResultSet resultSet=statement.executeQuery("select * from biblioteka.autor");
        while (resultSet.next()){
            System.out.println(resultSet.getString("imie"));
        }
        Path directory=Paths.get("C:\\Users\\HP\\Documents\\Projekt biblioteka\\Projekt-biblioteka\\Biblioteka");
        Path file=directory.resolve("test.csv");
            Path file2=directory.resolve("test2.csv");
        //dodajZCSV(connection,file);
            User user=new User("user","user");
    user.czyDostepna("potop");
    user.wyporzyczoneKsiazki(14);
    System.out.println("".isBlank());
    Ksiazka ks=new Ksiazka(connection,3);
            System.out.println(ks.toString());
//zapiszDoCSV( user.wyszukaj("","","sienkiew"), file2);
            zapiszDoCSV( admin.inwentarz(), file2);
            Ksiazka k=new Ksiazka(connection,"Potop","Henryk","Sienkiewicz");
        System.out.println(file);
        System.out.println(k.ileDostepnych());

        //odsetki(connection,5,1);
            //zmianaStanuKsiazki(connection,3);
           // System.out.println(czyNowyAutor(connection, "testimiie","testnazwisko"));

            Wyporzyczenia wyp=new Wyporzyczenia(connection,3);
            System.out.println(wyp.toString());*/
         wyswietl(admin.inwentarz());
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