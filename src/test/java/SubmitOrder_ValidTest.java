import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.BaseTest;
import utilities.Dbutils;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SubmitOrder_ValidTest extends BaseTest {
    private Dbutils db = new Dbutils();
    private String submitorderurl,additemurl;
    private Map<String, String> submitorder_jsonbody = new HashMap<>();
    private Map<String, String> jsonbody = new HashMap<>();


    @Test
    public void submitOrderTest() {
        String auth = "Bearer "+ acessToken;
        int orderid;
        String status="";


        orderid = checkpendingorder(auth);

        if(orderid>0) {

            try {
                submitorderurl = property.readFile("BaseURL") + property.readFile("submitorder") + orderid;
               // System.out.println(submitorderurl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            request.setauthorization(auth);
            request.putRequest(submitorderurl, submitorder_jsonbody);
            //System.out.println(submitorderresponse.getBody().asString());

            String statusquery = "SELECT status  FROM testing.`order`\n" +
                    "where id =" + orderid + ";";
            //System.out.println(statusquery);

            ResultSet statusresults = db.connectdb(statusquery);
            try {
                while (statusresults.next()) {
                    status = statusresults.getString("status");
                    //System.out.println("status" +status);

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            Assert.assertEquals(status, "SUBMITTED");

        }

    }



    //Add item code
    private int setAdditem(String auth){

        int orderid=0;
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
        request.postRequest(additemurl, jsonbody);
       // System.out.println("item added"+additemresponse.body().asString());
        String orderquery = "SELECT*  FROM testing.`order`\n" +
                "where user_id =7 and retailer_location_id = 9 and  status = \"PENDING\";";

        ResultSet orderresults = db.connectdb(orderquery);
        try {
            while (orderresults.next()) {
                orderid = orderresults.getInt("id");
               // System.out.println("count" + orderid);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  orderid;

    }


    private int checkpendingorder(String auth) {
        int orderid = 0;

        String orderquery = "SELECT*  FROM testing.`order`\n" +
                "where user_id =7 and retailer_location_id = 9 and  status = \"PENDING\";";

        ResultSet orderresults = db.connectdb(orderquery);
        try {
            while (orderresults.next()) {
                orderid = orderresults.getInt("id");
                //System.out.println("count" + orderid);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (orderid == 0) {
            orderid= setAdditem(auth);

        }

        return orderid;

    }


}
