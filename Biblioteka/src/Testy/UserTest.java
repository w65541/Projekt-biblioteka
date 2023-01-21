package Testy;

import Klasy.User;
import org.junit.Assert;
import org.junit.Test;


public class UserTest {


    @Test
    public void testCzyDostepna() {
        try {
            User test=new User("user","user");
            Assert.assertTrue(test.czyDostepna("Potop"));
            Assert.assertTrue(test.czyDostepna("Potop","","Sienkiewicz"));
            Assert.assertFalse(test.czyDostepna("afdaefaef","easgfsefesf","efsefesgfgseg"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    @Test
    public void wyporzyczoneKsiazki() {
        try {
            User test=new User("user","user");
            Assert.assertNotNull(test.wyporzyczoneKsiazki());
            Assert.assertNotNull(test.historiaWyporzyczen());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    }