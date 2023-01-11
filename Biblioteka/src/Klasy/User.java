package Klasy;
import java.time.LocalDate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.temporal.ChronoUnit;

//liczba książek, czas do oddania kara
public class User extends Login{
    int id;
    Czytelnik user;
    public User(String username,String password) {
        super(username,password);
        try{


             r= s.executeQuery("SELECT id from czytelnik where username='"+username+"'");
            if(r.next()) {id=r.getInt("id");
            user=new Czytelnik(c,id);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public Czytelnik getUser() {
        return user;
    }

    public void czyDostepna(String tytul, String imie, String nazwisko){
        Ksiazka k=new Ksiazka(c,tytul,imie,nazwisko);
        System.out.println(k.czyDostepna());
    }
    public void czyDostepna(String tytul){
        Ksiazka k=new Ksiazka(c,tytul);
        System.out.println(k.toString());
        System.out.println(k.czyDostepna());
    }

    public void wyporzyczoneKsiazki(int limit){
        try{
            LocalDate date;
            r= getUser().aktwyneWyporzyczenia();
                while (r.next()){
                    date= LocalDate.parse(r.getString("dataWyporzyczenia").substring(0,10));
                    System.out.println(r.getString("Tytuł")+" "+r.getString("Imie")+" "+r.getString("Nazwisko")+" "+r.getString("dataWyporzyczenia")+" "+ (limit-ChronoUnit.DAYS.between(date,LocalDate.now()))+" "+r.getString("Kara"));
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
