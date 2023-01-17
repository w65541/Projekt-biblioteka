package Testy;

import Klasy.Admin;
import Klasy.Autor;
import org.junit.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.Assert.*;

public class AdminTest {

    @org.junit.Test
    public void odsetki() {
        new Admin("root","root").odsetki(1,1);
    }

    @org.junit.Test
    public void zmianaStanuKsiazki() {
        Admin test=new Admin("root","root");
        Statement s1,s2;
        ResultSet r1,r2;
        try {
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
    public void CSV() throws IOException {
        Admin test=new Admin("root","root");
        Path p=Paths.get("C:\\Users\\HP\\Documents\\Projekt biblioteka\\Projekt-biblioteka\\Biblioteka\\src\\Testy");
        test.zapiszDoCSV(test.inwentarz(),p.resolve("testDoCSV1.csv"));
        test.dodajZCSV(p.resolve("testZCSV.csv"));
        test.zapiszDoCSV(test.inwentarz(),p.resolve("testDoCSV2.csv"));
        Assert.assertNotEquals(Files.mismatch(p.resolve("testDoCSV1.csv"),p.resolve("testDoCSV2.csv")),-1);
    }

    @org.junit.Test
    public void czyNowyAutor() {
        Admin test=new Admin("root","root");
        Assert.assertTrue(test.czyNowyAutor("drgdrahh","sigsyuigisegfb"));
        Assert.assertFalse(test.czyNowyAutor("Henryk","Sienkiewicz"));
    }

    @org.junit.Test
    public void czyNowyTytul() {
        Admin test=new Admin("root","root");
        Assert.assertTrue(test.czyNowyTytul("drgdrahh",0));
        Assert.assertFalse(test.czyNowyTytul("Potop",5));
    }

    @org.junit.Test
    public void dodajAutor() {
        Admin test=new Admin("root","root");
        Assert.assertNotEquals(test.dodajAutor("Feliks","Koneczny"),0);
    }

    @org.junit.Test
    public void dodajTytul() {
        Admin test=new Admin("root","root");
        Assert.assertNotEquals(test.dodajTytul("Polskie logos a ethos",8),0);
    }

    @org.junit.Test
    public void dodajKsiazke() {
        Admin test=new Admin("root","root");
        Assert.assertNotEquals(test.dodajKsiazke(11),0);
    }

    @org.junit.Test
    public void dodajCzytelnika() {
        Admin test=new Admin("root","root");
    }

    @org.junit.Test
    public void dodajIzakonczWyporzyczenie() {
        Admin test=new Admin("root","root");
        Assert.assertNotEquals(test.dodajWyporzyczenie(3,1),0);
        test.zakonczWyporzyczenie(3,1);
    }

    @org.junit.Test
    public void usun() {
        Admin test=new Admin("root","root");
    }

    @org.junit.Test
    public void wyniki() {
        Admin test=new Admin("root","root");

        try {
            Connection c= DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteka","root","root");
            Autor test2=new Autor(c,"Henryk","Sienkiewicz");
            test.inwentarz();
            test.wyszukaj("","","");
            test.aktualneWyporzyczenia();
            test2.ksiazkiAutora();
            test2.tytulyAutora();
        }catch(Exception e){
            e.printStackTrace();
            Assert.fail();
        }
    }
}