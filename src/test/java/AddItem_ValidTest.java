import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.BaseTest;
import utilities.Dbutils;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class AddItem_ValidTest extends BaseTest {
    private String additemurl;
    private Dbutils db =  new Dbutils();
    private String expecteditemcount;
    private String expectedtotalorder;

    @Test
    public void addItem(){
        String auth = "Bearer "+ acessToken;
        Map<String, String> jsonbody = new HashMap<>();
        //JSON Body
        jsonbody.put("container", "bottle");
        jsonbody.put("cost", "25.15");
        jsonbody.put( "quantity", "1");
        jsonbody.put("total_cost", "25.15");
        jsonbody.put( "retailer_id", "96745");
        jsonbody.put("display_name", "Heineken 2/12 12 oz Bottle DSP");
        jsonbody.put( "retailer_store_id", "1709320");
        jsonbody.put("product_name", "Heineken 2/12 12 oz Bottle DSP ");
        jsonbody.put( "external_retailer_store_id", "000001");
        jsonbody.put("product_number", "10832");
        jsonbody.put( "product_class", "beer");
        jsonbody.put("catalog_id", "4694");
        jsonbody.put("units_per_pack", "12");
        jsonbody.put("packs_per_case", "2");
        jsonbody.put("recent_record", "2018-11-20:264767834-1390138260");
        jsonbody.put("vendor_id", "18173");
        jsonbody.put("unit_of_measure", "Bottle");
        jsonbody.put("product_description", "heineken 12z 12pk lnnr");
        jsonbody.put("quantity_added","2");
        jsonbody.put("unit_of_measure_selected","Bottle");


        try {
             additemurl = property.readFile("BaseURL")+ property.readFile("additem");
        } catch (IOException e) {
            e.printStackTrace();
        }

        request.setauthorization(auth);
        Response additemresponse = request.postRequest(additemurl, jsonbody);

        //extract item count
        String actualitemcount = additemresponse.jsonPath().getString("itemCount");

        //Extract dolar total
        String actualtotalorder = additemresponse.jsonPath().getString("dollarTotal");

        //Execure query and get item count and dolar totall
        String query = "Select item_count, dollar_total from testing.order\n" +
                "where user_id=7 and status = \"PENDING\"";

        ResultSet dbresults = db.connectdb(query);

        try {
                while (dbresults.next()) {
                    expecteditemcount = String.valueOf(dbresults.getInt("item_count"));
                    //formatting decimal value to 1 digit after the ddecimal point
                    BigDecimal x = dbresults.getBigDecimal("dollar_total");
                    DecimalFormat df = new DecimalFormat(".#");
                    expectedtotalorder = df.format(x);

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        //Assert data from the query and API
        Assert.assertEquals(actualitemcount, expecteditemcount);
        Assert.assertEquals(actualtotalorder,  expectedtotalorder);






    }
}
