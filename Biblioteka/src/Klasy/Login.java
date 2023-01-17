package Klasy;
import java.nio.charset.Charset;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.nio.file.Path;
import java.nio.file.Paths;
public class Login {
    Connection c;
    Statement s;
    ResultSet r;
    public Login(String username,String password) {
        try{
            this.c = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteka",username,password);
            s=c.createStatement();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //Zwraca ResulSet wyszukiwarki, można wstawić puste "" a i tak będzie działać
    public ResultSet wyszukaj(String tytul,String imie,String nazwisko){
        if(!tytul.contains("czytelnik") || !imie.contains("czytelnik") || !nazwisko.contains("czytelnik")){
        try{
            String sql="SELECT Tytuł,Imie,Nazwisko,sum(czyWyporzyczona=0) as 'Dostępne' FROM książki left join tytuł \n" +
                    "on tytuł.id=książki.idTytuł  left join autor on autor.id=idAutor";

            if(!tytul.isBlank() || !imie.isBlank() || !nazwisko.isBlank()){
                sql+=" where";
                if(!tytul.isBlank()){
                    sql+=" Tytuł regexp '"+tytul+"' and";
                }else {sql+=" Tytuł regexp '.*' and";}
                if(!imie.isBlank()){
                    sql+=" Imie regexp '"+imie+"' and";
                }else {sql+=" Imie regexp '.*' and";}
                if(!nazwisko.isBlank()){
                    sql+=" Nazwisko regexp '"+nazwisko+"' ";
                }else {sql+=" Nazwisko regexp '.*' ";}
            }
            sql+=" group by tytuł.id";
            r=s.executeQuery(sql);
            return r;
        }catch (Exception e){
            e.printStackTrace();
        }}
        return null;
    }

}
