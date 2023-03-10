package Testy;

import Klasy.Admin;
import Klasy.Autor;
import Klasy.Tytul;
import org.junit.Assert;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class AdminTest {

    @org.junit.Test
    public void odsetki() {
       try {
           new Admin("root","root").odsetki(1,1);
       }catch(Exception e){
           e.printStackTrace();
           Assert.fail();
       }
    }

    @org.junit.Test
    public void zmianaStanuKsiazki() {

        Statement s1,s2;
        ResultSet r1,r2;
        try {Admin test=new Admin("root","root");
            Connection c= DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteka","root","root");
            s1=c.createStatement();
            s2=c.createStatement();
            r1=s1.executeQuery("select czyWyporzyczona from książki where id=1");
            test.zmianaStanuKsiazki(1);
            r2=s2.executeQuery("select czyWyporzyczona from książki where id=1");
            r2.next();
            r1.next();
            Assert.assertNotEquals(r1.getInt("czyWyporzyczona"),r2.getInt("czyWyporzyczona"));
        }catch(Exception e){
            e.printStackTrace();
            Assert.fail();
        }
    }


    @org.junit.Test
    public void CSV() {
        try {


        Admin test=new Admin("root","root");
        Path p=Paths.get("C:\\Users\\HP\\Documents\\Projekt biblioteka\\Projekt-biblioteka\\Biblioteka\\src\\Testy");
        test.zapiszDoCSV(test.inwentarz(),p.resolve("testDoCSV1.csv"));
        test.dodajZCSV(p.resolve("testZCSV.csv"));
        test.zapiszDoCSV(test.inwentarz(),p.resolve("testDoCSV2.csv"));
        Assert.assertNotEquals(Files.mismatch(p.resolve("testDoCSV1.csv"),p.resolve("testDoCSV2.csv")),-1);
        }catch(Exception e){
            e.printStackTrace();
            Assert.fail();
        }
    }

    @org.junit.Test
    public void czyNowyAutor() {
        try {
            Admin test=new Admin("root","root");
            Assert.assertTrue(test.czyNowyAutor("drgdrahh","sigsyuigisegfb"));
            Assert.assertFalse(test.czyNowyAutor("Henryk","Sienkiewicz"));
        }catch(Exception e){
            e.printStackTrace();
            Assert.fail();
        }

    }

    @org.junit.Test
    public void czyNowyTytul() {
        try {
            Admin test=new Admin("root","root");
            Assert.assertTrue(test.czyNowyTytul("drgdrahh",1));
            Assert.assertFalse(test.czyNowyTytul("Potop",5));
        }catch(Exception e){
            e.printStackTrace();
            Assert.fail();
        }

    }

    @org.junit.Test
    public void dodajAutor() {
        try {
            Admin test=new Admin("root","root");
            Assert.assertNotEquals(test.dodajAutor("Feliks","Koneczny"),0);
        }catch(Exception e){
            e.printStackTrace();
            Assert.fail();
        }

    }

    @org.junit.Test
    public void dodajTytul() {
        try {
            Admin test=new Admin("root","root");
            Assert.assertNotEquals(test.dodajTytul("Polskie logos a ethos",8),0);
        }catch(Exception e){
            e.printStackTrace();
            Assert.fail();
        }

    }

    @org.junit.Test
    public void dodajKsiazke() {
        try {
            Admin test=new Admin("root","root");
            Assert.assertNotEquals(test.dodajKsiazke(11),0);
        }catch(Exception e){
            e.printStackTrace();
            Assert.fail();
        }

    }

    @org.junit.Test
    public void dodajCzytelnika() {
        try {
            Admin test=new Admin("root","root");
            test.usun("czytelnik",test.dodajCzytelnika("testI","testN","test@test.pl","123456789","ulica miasto kod pocztowy","test","test"));

        }catch(Exception e){
            e.printStackTrace();
            Assert.fail();
        }

    }

    @org.junit.Test
    public void dodajIzakonczWyporzyczenie() {
        try {
            Admin test=new Admin("root","root");
            Assert.assertNotEquals(test.dodajWyporzyczenie(3,1),0);
            test.zakonczWyporzyczenie(3,1);
        }catch(Exception e){
            e.printStackTrace();
            Assert.fail();
        }

    }

    @org.junit.Test
    public void usun() {
        try {
            Admin test=new Admin("root","root");
            test.usun("książki", test.dodajKsiazke(11));
        }catch(Exception e){
            e.printStackTrace();
            Assert.fail();
        }

    }

    @org.junit.Test
    public void wyniki() {


        try {Admin test=new Admin("root","root");
            Tytul tytul=new Tytul(test.getC(),"Potop");
            Connection c= DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteka","root","root");
            Autor test2=new Autor(c,"Henryk","Sienkiewicz");
            Assert.assertEquals(tytul.ileDostepnych(),2);
            Assert.assertNotNull(test.inwentarz());
            Assert.assertNotNull(test.wyszukaj("","",""));
            Assert.assertNotNull(test.aktualneWyporzyczenia());
            Assert.assertNotNull(test2.ksiazkiAutora());
            Assert.assertNotNull(test2.tytulyAutora());
            Assert.assertNotNull(test.wyszukajtabela("tytuł","id=1"));
            Assert.assertNull(test.wykonajSql("SELECT * FROM biblioteka.książki;SELECT * FROM biblioteka.książki;"));
        }catch(Exception e){
            e.printStackTrace();
            Assert.fail();
        }
    }

}