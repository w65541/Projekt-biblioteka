package Klasy;

import java.sql.Connection;

public class Tytul extends Baza{

    String tytul;
    int id,idA;

    public Tytul(Connection connection, int id) {
        super(connection);

        try {
            resultSet=s.executeQuery("SELECT Tytuł,idAutora FROM tytuł where id="+getId());
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
