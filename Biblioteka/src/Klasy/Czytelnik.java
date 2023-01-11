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
