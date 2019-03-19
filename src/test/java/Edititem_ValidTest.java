import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.BaseTest;
import utilities.Dbutils;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Edititem_ValidTest extends BaseTest {
    private String edititemurl;
    private String additemurl ;
    private String itemid;
    private String quantity;

    @Test
    public void editItem(){

        String auth = "Bearer "+ acessToken;
        Map<String, String> additem_jsonbody = new HashMap<>();
        Map<String, String> edititem_jsonbody = new HashMap<>();
        Dbutils db = new Dbutils();
        try {
             additemurl = property.readFile("BaseURL")+property.readFile("additem");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //add item JSON Body
        additem_jsonbody.put("container", "bottle");
        additem_jsonbody.put("cost", "25.15");
        additem_jsonbody.put( "quantity", "1");
        additem_jsonbody.put("total_cost", "25.15");
        additem_jsonbody.put( "retailer_id", "96745");
        additem_jsonbody.put("display_name", "Heineken 2/12 12 oz Bottle DSP");
        additem_jsonbody.put( "retailer_store_id", "1709320");
        additem_jsonbody.put("product_name", "Heineken 2/12 12 oz Bottle DSP ");
        additem_jsonbody.put( "external_retailer_store_id", "000001");
        additem_jsonbody.put("product_number", "10832");
        additem_jsonbody.put( "product_class", "beer");
        additem_jsonbody.put("catalog_id", "4694");
        additem_jsonbody.put("units_per_pack", "12");
        additem_jsonbody.put("packs_per_case", "2");
        additem_jsonbody.put("recent_record", "2018-11-20:264767834-1390138260");
        additem_jsonbody.put("vendor_id", "18173");
        additem_jsonbody.put("unit_of_measure", "Bottle");
        additem_jsonbody.put("product_description", "heineken 12z 12pk lnnr");
        additem_jsonbody.put("quantity_added","2");
        additem_jsonbody.put("unit_of_measure_selected","Bottle");

        request.setauthorization(auth);
        Response additemresponse = request.postRequest( additemurl , additem_jsonbody);

        //assert status code for add item
        Assert.assertEquals(200, additemresponse.getStatusCode());

        //query for item id
        String itemidquery = "select id from testing.item\n" +
                "where order_id = (select id from testing.order where status = \"PENDING\" and user_id = 7 )";

        ResultSet itemidresult = db.connectdb(itemidquery);

        try {
            while (itemidresult.next()) {
                itemid = String.valueOf(itemidresult.getInt("id"));
                edititemurl = property.readFile("BaseURL")+property.readFile("edititem")+itemid;
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        //edit item body
        edititem_jsonbody.put("quantityEdited","3");
        edititem_jsonbody.put("unit_of_measure","Bottle");

        request.setauthorization(auth);
        Response edititemresponse = request.putRequest( edititemurl , edititem_jsonbody);

        Assert.assertEquals(200,edititemresponse.getStatusCode() );
        //System.out.println(edititemresponse.body().asString());

        String quantityquery = "SELECT quantity FROM testing.item\n" +
                "where id ="+itemid+" ;";

       // System.out.println(quantityquery);
        ResultSet quantityresult = db.connectdb(quantityquery);

        try {
            while (quantityresult.next()) {
                quantity = String.valueOf(quantityresult.getInt("quantity"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        //assert quantity after updating
        Assert.assertEquals(quantity, "3");
    }
}
