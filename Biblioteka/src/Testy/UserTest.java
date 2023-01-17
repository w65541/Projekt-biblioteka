package Testy;

import Klasy.User;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {


    @Test
    public void testCzyDostepna() {
        User test=new User("user","user");
        Assert.assertTrue(test.czyDostepna("Potop"));
        Assert.assertTrue(test.czyDostepna("Potop","","Sienkiewicz"));
        Assert.assertFalse(test.czyDostepna("afdaefaef","easgfsefesf","efsefesgfgseg"));
        Assert.assertFalse(test.czyDostepna("a'; select * from czytelnik; select * from autor where imie='a"));
    }

    @Test
    public void wyporzyczoneKsiazki() {
        User test=new User("user","user");
        test.wyporzyczoneKsiazki(1);
        test.historiaWyporzyczen();
    }
}