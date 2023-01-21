
import java.sql.*;
import java.util.Scanner;
import Klasy.Admin;
import Klasy.User;
import javax.swing.*;

//user wyszukiwarka
//admin przerabianie wyników wyszukiwania na csv, dodawanie użytkownika
public class Main {
    public static void main(String[] args) {
        try{
        int m;
        Admin admin;
        User user;
        String username,password;
            Scanner in = new Scanner(System.in);
             System.out.println("0 - Logowanie do admina\n1 - Logowanie do usera\n9 - wyjście");
             m=in.nextInt();
             switch (m){
                 case 0:
                     System.out.print("Username: ");
                     username=in.next();
                     System.out.print("Password: ");
                     password=in.next();
                     try {
                         admin=new Admin(username,password);
                         JFrame a = new GuiAdmin(admin);
                         a.setVisible(true);
                     }catch (SQLException e){
                         System.out.println("Niepoprawny login lub hasło");
                         return;
                     }

                     break;

                 case 1:
                        System.out.print("Username: ");
                     username=in.next();
                     System.out.print("Password: ");
                     password=in.next();
                     try {
                         user=new User(username,password);
                         JFrame b = new GuiUser(user);
                         b.setVisible(true);
                     }catch (SQLException e){
                         System.out.println("Niepoprawny login lub hasło");
                         return;
                     }

                     break;
                 case 9: return;
                 default:

             }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

}