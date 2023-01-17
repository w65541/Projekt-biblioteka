package Klasy;

import java.sql.Connection;

public class Tytul extends Baza{

    String tytul;
    int id,idA;

    public Tytul(Connection connection, int id) {
        super(connection);

        try {
            resultSet=s.executeQuery("SELECT Tytuł,idAutora FROM tytuł where id="+id);
            if(resultSet.next()) {
                idA=resultSet.getInt("idAutor");
                tytul=resultSet.getString("tytuł");
            }
            resultSet.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        this.id = id;
    }
    public Tytul(Connection connection, String tytul) {
        super(connection);

        try {
            resultSet=s.executeQuery("SELECT id,idAutor FROM tytuł where Tytuł='"+tytul+"'");
            if(resultSet.next()) {
                idA=resultSet.getInt("idAutor");
                id=resultSet.getInt("id");
            }
            resultSet.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        this.tytul = tytul;
    }
    public Tytul(Connection connection, String tytul,String imie,String nazwisko) {
        super(connection);

        try {
            if(imie.isBlank()) imie=".*";
            resultSet=s.executeQuery("SELECT tytuł.id,idAutor FROM tytuł left join autor on idAutor=autor.id where Tytuł='"+tytul+"' and imie regexp '"+imie+"' and nazwisko='"+nazwisko+"'");
            if(resultSet.next()) {
                idA=resultSet.getInt("idAutor");
                id=resultSet.getInt("id");
            }
            resultSet.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        this.tytul = tytul;
    }
    public boolean czyDostepna(){
        try {
            resultSet=s.executeQuery("SELECT Tytuł,count(czyWyporzyczona) FROM tytuł left join książki on tytuł.id=książki.idTytuł where czyWyporzyczona=0 and idTytuł="+getId()+" and idAutor="+getIdA()+" group by Tytuł");
            if(resultSet.next()) return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public int ileDostepnych(){
        try {
            resultSet=s.executeQuery("SELECT Tytuł,count(czyWyporzyczona) as ilosc FROM tytuł left join książki on tytuł.id=książki.idTytuł where czyWyporzyczona=0 and idTytuł="+getId()+" and idAutor="+getIdA()+" group by Tytuł");
            if(resultSet.next()) return resultSet.getInt("ilosc");
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    public int getIdA() {
        return idA;
    }

    public void setIdA(int idA) {
        try {
            s.execute("UPDATE `biblioteka`.`tytuł` SET `idAutor` = "+idA+" WHERE `id` ="+getId());
            this.idA=idA;

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public void setId(int id) {
        try {
            s.execute("UPDATE `biblioteka`.`tytuł` SET `id` = "+id+" WHERE `id` ="+getId());
            this.id=id;

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public void setTytul(String tytul) {
        try {
            s.execute("UPDATE `biblioteka`.`tytuł` SET `Tytuł` = '"+tytul+"' WHERE `id` ="+id);
            this.tytul=tytul;

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public String getTytul() {
        return tytul;
    }

    public int getId() {
        return id;
    }

}
