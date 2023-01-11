package Klasy;
import java.sql.Connection;
import java.time.LocalDate;
public class Wyporzyczenia extends Baza {
    int id,idK,idC,kara;
    LocalDate dataW,dataO=null;

    public Wyporzyczenia(Connection connection, int id) {
        super(connection);
        try {
            resultSet=s.executeQuery("select * from wyporzyczenia where id="+id);
            if(resultSet.next()){
                this.id = id;
                this.idK = resultSet.getInt(3);
                this.idC = resultSet.getInt(2);
                this.kara = resultSet.getInt(6);
                this.dataW = LocalDate.parse(resultSet.getString(4).substring(0,10));
                if(resultSet.getString(5)!=null) this.dataO = LocalDate.parse(resultSet.getString(5).substring(0,10));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Wyporzyczenia{" +
                "id=" + id +
                ", idK=" + idK +
                ", idC=" + idC +
                ", kara=" + kara +
                ", dataW=" + dataW +
                ", dataO=" + dataO +
                '}';
    }
}
