package Klasy;
import java.sql.*;
import java.nio.charset.Charset;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.csv.*;
public class Baza {
    Statement s;
    ResultSet resultSet;
    Connection c;
    public Baza(Connection connection) {
        try {
            c=connection;
            s=connection.createStatement();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
