package utilities;

import java.io.IOException;
import java.sql.*;

public class Dbutils {
    PropertyFileReader reader = new PropertyFileReader();
    String url;


     ResultSet result;

    {
        try {
            url = reader.readFile("Dburl");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Connection connection = null;

    public ResultSet connectdb(String query){
        try {
            //Connect to DB
            connection = DriverManager.getConnection(url);

            //Execute Query
            Statement statement = connection.createStatement();
            result = statement.executeQuery(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;

    }


}

