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
    public Autor(Connection connection, int id) {
        super(connection);
        this.id=id;
        try{
            s=c.createStatement();
            resultSet=s.executeQuery("select imie,nazwisko from biblioteka.autor where id="+id);
            if(resultSet.next()) {
                imie=resultSet.getString("imie");
                nazwisko=resultSet.getString("nazwisko");
            }
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
    //Zwraca ResultSet tytułów danego autora
    public ResultSet tytulyAutora(){
        try{

            resultSet=s.executeQuery("select id,tytuł from biblioteka.tytuł where idAutor="+getId());
            return  resultSet;

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    //Zwraca ResultSet książek danego autora
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
    public void setId(int id) {
        try {
            s.execute("UPDATE `biblioteka`.`autor` SET `id` = "+id+" WHERE `id` ="+getId());
            this.id=id;

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public void setImie(String imie) {
        try {
            s.execute("UPDATE `biblioteka`.`autor` SET `imie` = '"+imie+"' WHERE `id` ="+getId());
            this.imie=imie;

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public void setNazwisko(String nazwisko) {
        try {
            s.execute("UPDATE `biblioteka`.`autor` SET `nazwisko` = '"+nazwisko+"' WHERE `id` ="+getId());
            this.nazwisko=nazwisko;

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public void setIdK(String nazwisko) {
        try {
            s.execute("UPDATE `biblioteka`.`autor` SET `nazwisko` = '"+nazwisko+"' WHERE `id` ="+getId());
            this.nazwisko=nazwisko;

        }catch(Exception e){

            e.printStackTrace();
        }
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
