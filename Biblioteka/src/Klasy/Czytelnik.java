package Klasy;

import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Czytelnik extends Baza{
    String imie,nazwisko,telefon,email,adres,username;
    int id;

    public Czytelnik(Connection connection, int id) {
        super(connection);
        try {
            resultSet=s.executeQuery("select * from czytelnik where id="+id);
            if(resultSet.next()){
                this.id = id;
                this.imie = resultSet.getString(2);
                this.nazwisko = resultSet.getString(3);
                this.telefon = resultSet.getString(5);
                this.email = resultSet.getString(4);
                this.adres = resultSet.getString(6);
                this.username = resultSet.getString(7);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setImie(String imie) {
        try {
            s.execute("UPDATE `biblioteka`.`czytelnik` SET `imie` = '"+imie+"' WHERE `id` ="+getId());
            this.imie=imie;

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public void setNazwisko(String nazwisko) {
        try {
            s.execute("UPDATE `biblioteka`.`czytelnik` SET `nazwisko` = '"+nazwisko+"' WHERE `id` ="+getId());
            this.nazwisko=nazwisko;

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public void setTelefon(String telefon) {
        try {
            s.execute("UPDATE `biblioteka`.`czytelnik` SET `telefon` = '"+telefon+"' WHERE `id` ="+getId());
            this.telefon=telefon;

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public void setEmail(String email) {
        try {
            s.execute("UPDATE `biblioteka`.`czytelnik` SET `email` = '"+email+"' WHERE `id` ="+getId());
            this.email=email;

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public void setAdres(String adres) {
        try {
            s.execute("UPDATE `biblioteka`.`czytelnik` SET `adres` = '"+adres+"' WHERE `id` ="+getId());
            this.adres=adres;

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public void setUsername(String username) {
        try {
            s.execute("UPDATE `biblioteka`.`czytelnik` SET `username` = '"+username+"' WHERE `id` ="+getId());
            this.username=username;

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public void setId(int id) {
        try {
            s.execute("UPDATE `biblioteka`.`czytelnik` SET `id` = "+id+" WHERE `id` ="+getId());
            this.id=id;

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getEmail() {
        return email;
    }

    public String getAdres() {
        return adres;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public ResultSet aktwyneWyporzyczenia(){
        try{
            resultSet= s.executeQuery("SELECT Tytuł,Imie,Nazwisko,dataWyporzyczenia,kara FROM biblioteka.wyporzyczenia left join biblioteka.książki on biblioteka.książki.id=idKsiążki left join tytuł on tytuł.id=idTytuł left join autor on autor.id=idAutor where idCzytelnik="+id+" and dataOddania is null");
            return resultSet;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet historiaWyporzyczenia(){
        try{
            resultSet= s.executeQuery("SELECT Tytuł,Imie,Nazwisko,dataWyporzyczenia,dataOddania,kara FROM biblioteka.wyporzyczenia left join biblioteka.książki on biblioteka.książki.id=idKsiążki left join tytuł on tytuł.id=idTytuł left join autor on autor.id=idAutor where idCzytelnik="+id);
            return resultSet;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
