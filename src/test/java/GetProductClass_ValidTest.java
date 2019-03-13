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

public class GetProductClass_ValidTest extends BaseTest {

    @Test
    public void productClassTest(){
        Dbutils db = new Dbutils();
        Collection <String> expectedproductclass = new LinkedList<String>();
        Collection <String> actualproductclass = new LinkedList<String>();
        String productclassname;
        String query = "select name FROM testing.product_class;";
        ResultSet productclass = db.connectdb(query);
        try {
            while (productclass.next()) {
                productclassname = productclass.getString(1);
                expectedproductclass.add(productclassname);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String auth = "Bearer "+ acessToken;
        String productclassURI = null;
        try {
            productclassURI = property.readFile("BaseURL")+property.readFile("productclass");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Response getproductclssresponse = request.getRequest(productclassURI, auth);
        String jsonbody = getproductclssresponse.body().asString();

        JSONArray jsonastring = new JSONArray(jsonbody);
        for(int i=0; i<jsonastring.length(); i++){
            JSONObject obj = jsonastring.getJSONObject(i) ;

            //int id = obj.getInt("id");
            String name = obj.getString("name");

            actualproductclass.add(name);
        }

        for(int i=0; i<actualproductclass.size(); i++)
        {
            boolean contains = expectedproductclass.contains(((LinkedList<String>) actualproductclass).get(i));
            //System.out.println("Expected:" +((LinkedList<String>) expectedproductclass).get(i) + "Actual:" + ((LinkedList<String>) actualproductclass).get(i));
            //Assert productclass name
            Assert.assertEquals(true, contains);
        }

    }
}
