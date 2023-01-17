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

    public boolean czyDostepna(String tytul, String imie, String nazwisko){
        if(!tytul.contains("czytelnik") || !imie.contains("czytelnik") || !nazwisko.contains("czytelnik")){
        Tytul k=new Tytul(c,tytul,imie,nazwisko);
        if (k.czyDostepna()) return true;
        }
        return false;
    }
    public boolean czyDostepna(String tytul){
        if(!tytul.contains("czytelnik")){
        Tytul k=new Tytul(c,tytul);
        if(k.czyDostepna())return true;
        }
        return false;
    }
    public ResultSet historiaWyporzyczen(){
        return getUser().historiaWyporzyczenia();
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
