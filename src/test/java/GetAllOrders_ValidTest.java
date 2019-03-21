import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.BaseTest;
import utilities.Dbutils;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

public class GetAllOrders_ValidTest extends BaseTest {

    private String getallordersURL;
    private Collection<String> actualorders = new LinkedList<>();
    private Collection<String> expectedorders = new LinkedList<>();
    private Dbutils db = new Dbutils();

    @Test
    public void getAllOrders(){
        String auth = "Bearer "+ acessToken;
        try {
            getallordersURL = property.readFile("BaseURL")+property.readFile("getallorders");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Response getallordersresponse = request.getRequest(getallordersURL, auth);
        String getallordersbody = getallordersresponse.body().asString();
        //System.out.println(getallordersbody);

        JSONArray values = new JSONArray(getallordersbody);
        for(int i=0; i<values.length(); i++){
            JSONObject obj = values.getJSONObject(i) ;
            String ordernumber = String.valueOf(obj.getInt("orderNumber"));            //String name = obj.getString("name");
            //System.out.println(ordernumber);
            actualorders.add(ordernumber);

        }

        // Get orders from the Data base
        String query  = "SELECT order_number  FROM testing.`order`;\t";

        ResultSet orders = db.connectdb(query);
        String ordernumber;
        try {
            while (orders.next()) {
                ordernumber = String.valueOf(orders.getInt(1));
               // System.out.println(ordernumber);
                expectedorders.add(ordernumber);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Assert Order numbers got from DB and API
        for (int i=0; i<actualorders.size(); i++){

            boolean contains = expectedorders.contains(((LinkedList<String>) actualorders).get(i));
            //System.out.println(contains +"        " + ((LinkedList<String>) actualorders).get(i));
            Assert.assertTrue(contains);




        }





    }
}
