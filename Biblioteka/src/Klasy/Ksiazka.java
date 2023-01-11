package Klasy;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Ksiazka extends Baza {
    String tytul,imie,nazwisko;
    int idT=0,idA=0,idK=0;

    public Ksiazka(Connection connection, String tytul, String imie, String nazwisko) {
        super(connection);
        this.tytul = tytul;
        this.imie = imie;
        this.nazwisko = nazwisko;
        try {
            s=c.createStatement();
            resultSet=s.executeQuery("select id from biblioteka.autor where imie='"+imie+"' and nazwisko='"+nazwisko+"'");
            if(resultSet.next()) idA=resultSet.getInt("id");
            resultSet.close();

            resultSet=s.executeQuery("select id from biblioteka.tytuł where Tytuł='"+tytul+"' and idAutor="+idA+"");
            if(resultSet.next()) idT=resultSet.getInt("id");
            resultSet.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public Ksiazka(Connection connection, int idK) {
        super(connection);
        this.idK = idK;
    }

    public Ksiazka(Connection connection, String tytul) {
        super(connection);
        this.tytul = tytul;
        try {
            s=c.createStatement();
            resultSet=s.executeQuery("select id from biblioteka.tytuł where Tytuł='"+tytul+"'");
            if(resultSet.next()) idT=resultSet.getInt("id");
            resultSet.close();

            resultSet=s.executeQuery("select autor.id,Imie,Nazwisko from biblioteka.autor left join biblioteka.tytuł on biblioteka.autor.id=idAutor where Tytuł='"+tytul+"'");
            if(resultSet.next()){
                idA=resultSet.getInt("id");
                imie=resultSet.getString("Imie");
                nazwisko=resultSet.getString("Nazwisko");
            }


        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public int ileDostepnych(){
        try {
            resultSet=s.executeQuery("SELECT Tytuł,count(czyWyporzyczona) as ilosc FROM tytuł left join książki on tytuł.id=książki.idTytuł where czyWyporzyczona=0 and idTytuł="+getIdT()+" and idAutor="+getIdA()+" group by Tytuł");
            if(resultSet.next()) return resultSet.getInt("ilosc");
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    public boolean czyDostepna(){
        try {
            resultSet=s.executeQuery("SELECT Tytuł,count(czyWyporzyczona) FROM tytuł left join książki on tytuł.id=książki.idTytuł where czyWyporzyczona=0 and idTytuł="+getIdT()+" and idAutor="+getIdA()+" group by Tytuł");
            if(resultSet.next()) return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public String getTytul() {
        return tytul;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public int getIdT() {
        return idT;
    }

    public int getIdA() {
        return idA;
    }

    public int getIdK() {
        return idK;
    }

    @Override
    public String toString() {
        return "Ksiazka{" +
                "tytul='" + tytul + '\'' +
                ", imie='" + imie + '\'' +
                ", nazwisko='" + nazwisko + '\'' +
                ", idT=" + idT +
                ", idA=" + idA +
                ", idK=" + idK +
                '}';
    }
}
