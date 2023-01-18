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
    //Sprawdzenie czy dany tytuł jest dostępny, bardziej i mniej szczegółowa wersja
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
    //Zwraca ResultSet wszystkich wyporzyczeń urzytkownika
    public ResultSet historiaWyporzyczen(){
        try{
        return getUser().historiaWyporzyczenia();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //Zwraca ResultSet obecnie wyporzyczonych książek
    public ResultSet wyporzyczoneKsiazki(){
        try{
            LocalDate date;
            return getUser().aktwyneWyporzyczenia();

        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
