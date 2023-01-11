package Klasy;

import java.sql.Connection;
import java.sql.ResultSet;

public class Autor extends Baza{
    String imie,nazwisko;
    int id;

    public Autor(Connection connection, String imie, String nazwisko) {
        super(connection);
        this.imie = imie;
        this.nazwisko = nazwisko;
        try{
        s=c.createStatement();
        resultSet=s.executeQuery("select id from biblioteka.autor where imie='"+imie+"' and nazwisko='"+nazwisko+"'");
        if(resultSet.next()) id=resultSet.getInt("id");
        resultSet.close();
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

    public int getId() {
        return id;
    }

    public ResultSet tytulyAutora(){
        try{

            resultSet=s.executeQuery("select id,tytuł from biblioteka.tytuł where idAutor="+getId());
            return  resultSet;

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet ksiazkiAutora(){
        try{

            resultSet=s.executeQuery("SELECT książki.id,Tytuł,czyWyporzyczona FROM książki left join tytuł \n" +
                    "on tytuł.id=książki.idTytuł  left join autor on autor.id=idAutor where  idAutor="+getId());
            return  resultSet;

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "imie='" + imie + '\'' +
                ", nazwisko='" + nazwisko + '\'' +
                ", id=" + id +
                '}';
    }
}
