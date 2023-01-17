package Klasy;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Admin extends Login{

    public Admin(String username,String password) {
        super(username,password);
        try{

    }catch (Exception e){
            e.printStackTrace();
        }
    }

    //metoda naliczająca odsetki za przetrzymywanie książki
    public  void odsetki(int kara,int limit){
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
                            "WHERE `id` ="+ resultSet.getString("id")+";");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //zmiana statusu książki
    public  void zmianaStanuKsiazki(int id){
        try {
            new Ksiazka(c,id).zmianaStanuKsiazki();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //Dodawanie książek z pliku .csv, automatycznie dodaje nowych autorów i tytuły
    public  void dodajZCSV( Path p){
        try {
            int idA,idT;
            CSVParser plik=CSVParser.parse(p, Charset.defaultCharset(), CSVFormat.DEFAULT.withFirstRecordAsHeader());
            for (CSVRecord record : plik) {
                String field_1 = record.get("Tytuł");
                String field_2 = record.get("Imie");
                String field_3 = record.get("Nazwisko");
                String field_4 = record.get("Ilość");
                idA=dodajAutor(field_2,field_3);
                idT=dodajTytul(field_1,idA);
                for (int i=0;i<Integer.parseInt(field_4);i++){
                    dodajKsiazke(idT);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Tworzy plik .csv z ResultSet
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

    //Sprawdza czy dany autor istnieje w bazie danych
    public  boolean czyNowyAutor(String i,String n){
        try {
            Statement get=c.createStatement();
            ResultSet resultSet=get.executeQuery("select * from biblioteka.autor where imie='"+i+"' and nazwisko='"+n+"'");
            if(resultSet.next()) return false;
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }
    //Sprawdza czy dany tytuł istnieje w bazie danych
    public  boolean czyNowyTytul(String t,int idA){
        try {
            Statement get=c.createStatement();
            ResultSet resultSet=get.executeQuery("select * from biblioteka.tytuł where tytuł='"+t+"' and idAutor='"+idA+"'");
            if(resultSet.next()) return false;
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }
    //Dodaje nowego autora do bazy danych i wzraca id nowego lub już istniejącego
    public  int dodajAutor(String i,String n){
        try {
            if(czyNowyAutor(i,n)){
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
    //Dodaje nowy tytuł do bazy danych i wzraca id nowego lub już istniejącego
    public  int dodajTytul(String t,int idA){
        try {
            if(czyNowyTytul(t,idA)){
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
    //Dodaje nową książkę do bazy danych i zwraca jej id
    public  int dodajKsiazke(int idT){
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
    //Dodaje nowego czytelnika do bazy danych i tworzy też w niej urzytkownika nadając odpowiednie uprawnienia
    public int dodajCzytelnika(String imie,String nazwisko,String email,int telefon,String adres,String username,String haslo){
        try{
            String sql="CREATE USER '"+username+"'@'localhost' IDENTIFIED BY 'password'; GRANT select ON biblioteka.* TO '"+username+"'@'localhost';";

            s.execute("CREATE USER '"+username+"'@'localhost' IDENTIFIED BY 'password'");
            s.execute("GRANT SELECT ON biblioteka.* TO '"+username+"'@'localhost';");
            s.execute("FLUSH PRIVILEGES");
            s.execute("INSERT INTO `biblioteka`.`czytelnik`\n" +
                    "(`Imie`,\n" +
                    "`Nazwisko`,\n" +
                    "`e-mail`,\n" +
                    "`telefon`,\n" +
                    "`adres`,\n" +
                    "`username`)\n" +
                    "VALUES\n" +
                    "('"+imie+"',\n" +
                    "'"+nazwisko+"',\n" +
                    "'"+email+"',\n" +
                    "'"+telefon+"',\n" +
                    "'"+adres+"',\n" +
                    "'"+username+"');\n");
            r=s.executeQuery("select id from czytelnik where " +
                    "imie='"+imie+"' and nazwisko='"+nazwisko+"' and email='"+email+"' and telefon='"+telefon+"' and adres='"+adres+"' and username='"+username+"'");
            r.next();
            return r.getInt("id");
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    //Wyporzycza książkę
    public int dodajWyporzyczenie(int idK,int idC){
        try {
            if(!new Ksiazka(c,idK).isCzyWyp()) {
                s.execute("INSERT INTO `biblioteka`.`wyporzyczenia`\n" +
                        "(`idCzytelnik`,\n" +
                        "`idKsiążki`,\n" +
                        "`dataWyporzyczenia`)\n" +
                        "VALUES\n" +
                        "(" + idC + ",\n" +
                        idK + ",\n" +
                        "now())");
                zmianaStanuKsiazki(idK);
                r=s.executeQuery("select id from wyporzyczenia where idCzytelnik="+idC+" and idKsiążki="+idK+" and dataOddania is null");
                r.next();
                return r.getInt("id");
            }
        }catch (Exception e){
            e.printStackTrace();

        }return 0;
    }
    //Oddaje książkę
    public void zakonczWyporzyczenie(int idK,int idC){
        try {

                s.execute("UPDATE `biblioteka`.`wyporzyczenia`\n" +
                        "SET\n" +
                        "`dataOddania` = now()\n" +
                        "WHERE `idKsiążki` = "+idK+" and idCzytelnik="+idC+" and dataOddania is null");
                zmianaStanuKsiazki(idK);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //Usuwa rekord z danej tabeli o podanym id
    public void usun(String tabela,int id){
        try {
            s.execute("DELETE FROM `biblioteka`.`"+tabela+"` WHERE id="+id);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //Zwraca ResultSet posiadanych książek z informacją ile ich jest dostępnych
    public ResultSet inwentarz(){
        try{
            String sql="SELECT Tytuł,Imie,Nazwisko,sum(czyWyporzyczona=0) as 'Dostępne',  count(czyWyporzyczona) as 'Ilość' FROM książki left join tytuł \n" +
                    "on tytuł.id=książki.idTytuł  left join autor on autor.id=idAutor group by tytuł.id";
            r=s.executeQuery(sql);
            return r;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    //Zwraca ResultSet wszystkich niezakończonych wyporzyczeń
    public ResultSet aktualneWyporzyczenia(){
        try{
            String sql="SELECT * from wyporzyczenia where dataOddania is null";
            r=s.executeQuery(sql);
            return r;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
