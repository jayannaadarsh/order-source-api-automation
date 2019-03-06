import utilities.Dbutils;
import utilities.PropertyFileReader;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class test1
{
    public static ResultSet result;

    public static  void  main(String args[]) throws IOException {
        /*PropertyFileReader prop = new PropertyFileReader();
        String val=prop.readFile("");
        System.out.println(val);*/
        String query = "select * from testing.order \n" +
                "where order.id = (select count(*) from testing.order)";
        Dbutils db = new Dbutils();
         result = db.connectdb(query);
     try {
         while (result.next()) {
             System.out.println(result.getInt("id")+1);
         }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
