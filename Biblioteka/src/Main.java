//import java.beans.Statement;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.nio.file.Path;
import java.nio.file.Paths;

import Klasy.Admin;
import Klasy.Ksiazka;
import Klasy.User;
import Klasy.Wyporzyczenia;
import org.apache.commons.csv.*;

//user wyszukiwarka
//admin przerabianie wyników wyszukiwania na csv, dodawanie użytkownika
public class Main {
    public static void main(String[] args) {
        try{
        Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteka","root","root");
       /* Statement statement=connection.createStatement();
            Admin admin=new Admin("root","root");
        ResultSet resultSet=statement.executeQuery("select * from biblioteka.autor");
        while (resultSet.next()){
            System.out.println(resultSet.getString("imie"));
        }
        Path directory=Paths.get("C:\\Users\\HP\\Documents\\Projekt biblioteka\\Projekt-biblioteka\\Biblioteka");
        Path file=directory.resolve("test.csv");
            Path file2=directory.resolve("test2.csv");
        //dodajZCSV(connection,file);
            User user=new User("user","user");
    user.czyDostepna("potop");
    user.wyporzyczoneKsiazki(14);
    System.out.println("".isBlank());
    Ksiazka ks=new Ksiazka(connection,3);
            System.out.println(ks.toString());
//zapiszDoCSV( user.wyszukaj("","","sienkiew"), file2);
            zapiszDoCSV( admin.inwentarz(), file2);
            Ksiazka k=new Ksiazka(connection,"Potop","Henryk","Sienkiewicz");
        System.out.println(file);
        System.out.println(k.ileDostepnych());

        //odsetki(connection,5,1);
            //zmianaStanuKsiazki(connection,3);
           // System.out.println(czyNowyAutor(connection, "testimiie","testnazwisko"));

            Wyporzyczenia wyp=new Wyporzyczenia(connection,3);
            System.out.println(wyp.toString());*/
            Ksiazka k=new Ksiazka(connection,3);
            System.out.println(k.czyDostepna());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//SELECT Tytuł,Imie,Nazwisko FROM biblioteka.tytuł left join biblioteka.autor on biblioteka.autor.id=idAutor;
    //SELECT Tytuł,imie,nazwisko,count(książki.id) as 'ilość' FROM książki left join tytuł on tytuł.id=idTytuł left join autor on autor.id=idAutor group by tytuł.id
    //SELECT Tytuł,count(czyWyporzyczona) FROM tytuł left join książki on tytuł.id=książki.idTytuł where czyWyporzyczona=0 and idTytuł=7 and idAutor=5 group by Tytuł
//metoda naliczająca odsetki za przetrzymywanie książki
public static void odsetki(Connection c,int kara,int limit){
        try {
            Statement get=c.createStatement();
            Statement upd=c.createStatement();
            ResultSet resultSet=get.executeQuery("select * from biblioteka.wyporzyczenia where dataOddania is null;");
            LocalDate today=LocalDate.now();
            LocalDate date;
            while (resultSet.next()){
                date= LocalDate.parse(resultSet.getString("dataWyporzyczenia").substring(0,10));
                if(ChronoUnit.DAYS.between(date,today)>limit){
                    upd.executeUpdate("UPDATE `biblioteka`.`wyporzyczenia`\n" +
                            "SET\n" +
                            "`kara` =" +(resultSet.getString("kara")+"+"+kara)+"\n" +
                            "WHERE `idWyporzyczenia` ="+ resultSet.getString("idWyporzyczenia")+";");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
}
public static void zmianaStanuKsiazki(Connection c,int id){
        try {
            Statement get=c.createStatement();
            Statement upd=c.createStatement();
            ResultSet resultSet=get.executeQuery("select czyWyporzyczona from biblioteka.książki where id="+id+";");
            if (resultSet.next()){
            if(resultSet.getString(1).equals("0")){upd.executeUpdate("UPDATE `biblioteka`.`książki`\n" +
                    "SET\n" +
                    "`czyWyporzyczona` = 1\n" +
                    "WHERE `id` ="+ id+";");}else{upd.executeUpdate("UPDATE `biblioteka`.`książki`\n" +
                    "SET\n" +
                    "`czyWyporzyczona` = 0\n" +
                    "WHERE `id` ="+ id+";");}}
        }catch (Exception e){
            e.printStackTrace();
        }

}
public static void dodajZCSV(Connection c,Path p){
        try {
            int idA,idT;
            CSVParser plik=CSVParser.parse(p, Charset.defaultCharset(),CSVFormat.DEFAULT.withFirstRecordAsHeader());

            System.out.println(plik.getHeaderNames().toString());
            for (CSVRecord record : plik) {
                String field_1 = record.get("Tytuł");
                String field_2 = record.get("Imie");
                String field_3 = record.get("Nazwisko");
                String field_4 = record.get("Ilość");
                    idA=dodajAutor(c,field_2,field_3);
                    idT=dodajTytul(c,field_1,idA);
                    for (int i=0;i<Integer.parseInt(field_4);i++){
                        dodajKsiazke(c,idT);
                    }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
}
public static void zapiszDoCSV(ResultSet r,Path p){
    try{
        BufferedWriter writer = Files.newBufferedWriter(p, StandardCharsets.UTF_8 ) ;
        CSVFormat csvFileFormat = CSVFormat.DEFAULT;
        CSVPrinter plik=new CSVPrinter(writer,csvFileFormat);
        ResultSetMetaData rsmd = r.getMetaData();
        for (int i=1;i<rsmd.getColumnCount()+1;i++){
            plik.print(rsmd.getColumnName(i));
        }
        plik.println();
        while (r.next()){
            for (int i=1;i<rsmd.getColumnCount()+1;i++){
                plik.print(r.getString(i));
            }
            plik.println();
        }
        plik.close();
        writer.close();
    }catch (Exception e){
        e.printStackTrace();
    };
}
public static boolean czyNowyAutor(Connection c,String i,String n){
        try {
            Statement get=c.createStatement();
            ResultSet resultSet=get.executeQuery("select * from biblioteka.autor where imie='"+i+"' and nazwisko='"+n+"'");
            if(resultSet.next()) return true;
        }catch (Exception e){
            e.printStackTrace();
        }
    return false;
}
public static boolean czyNowyTytul(Connection c,String t,int idA){
        try {
            Statement get=c.createStatement();
            ResultSet resultSet=get.executeQuery("select * from biblioteka.tytuł where tytuł='"+t+"' and idAutor='"+idA+"'");
            if(resultSet.next()) return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
public static int dodajAutor(Connection c,String i,String n){
        try {
            if(!czyNowyAutor(c,i,n)){
                System.out.println("debug");
            Statement ins=c.createStatement();
            ins.executeUpdate("INSERT INTO `biblioteka`.`autor`\n" +
                    "(`Imie`,\n" +
                    "`Nazwisko`)\n" +
                    "VALUES\n" +
                    "('"+i+"',\n" +
                    "'"+n+"');\n");
            }
            Statement get=c.createStatement();
            ResultSet getid=get.executeQuery("select id from biblioteka.autor where imie='"+i+"' and nazwisko='"+n+"'");
            getid.next();
            return getid.getInt("id");
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }


}
public static int dodajTytul(Connection c,String t,int idA){
        try {
            if(!czyNowyTytul(c,t,idA)){
                Statement ins=c.createStatement();
                ins.executeUpdate("INSERT INTO `biblioteka`.`tytuł`\n" +
                        "(`Tytuł`,\n" +
                        "`idAutor`)\n" +
                        "VALUES\n" +
                        "('"+t+"',\n" +
                        ""+idA+");\n");
            }
            Statement get=c.createStatement();
            ResultSet getid=get.executeQuery("select id from biblioteka.tytuł where Tytuł='"+t+"' and idAutor="+idA+"");
            getid.next();
            return getid.getInt("id");
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
public static int dodajKsiazke(Connection c,int idT){
        try {
                Statement ins=c.createStatement();
                ins.executeUpdate("INSERT INTO `biblioteka`.`książki`\n" +
                        "(`idTytuł`)\n" +
                        "VALUES\n" +
                        "("+idT+");\n");
            Statement get=c.createStatement();
            ResultSet getid=get.executeQuery("select id from biblioteka.książki order by id desc LIMIT 1");
            getid.next();
            return getid.getInt("id");
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }


}