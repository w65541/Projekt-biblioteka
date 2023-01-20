package Klasy;
import java.sql.*;
import java.time.LocalDate;

import java.time.temporal.ChronoUnit;

//liczba książek, czas do oddania kara
public class User extends Login {
    int id;
    Czytelnik user;
    public User(String username,String password) throws SQLException {
        super(username,password);
        try{

            if(walidacjaSql(username)){
             r= s.executeQuery("SELECT id from czytelnik where username='"+username+"'");
            if(r.next()) {id=r.getInt("id");
            user=new Czytelnik(c,id);}
            }

        }catch (Exception e){
            e.printStackTrace();
            throw new SQLException();
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
        if(walidacjaSql(tytul) || walidacjaSql(imie) || walidacjaSql(nazwisko)){
        Tytul k=new Tytul(c,tytul,imie,nazwisko);
        if (k.czyDostepna()) return true;
        }
        return false;
    }
    public boolean czyDostepna(String tytul){
        if(walidacjaSql(tytul)){
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
