import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.BaseTest;
import utilities.Dbutils;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetPendingOrders_ValidTest extends BaseTest {

    String getpendingorderURI ;
    Dbutils db = new Dbutils();
    int pendingcount;
    int expectedstatuscode , expectedordernumber ;

    @Test
    public  void getpendingorders(){
        String auth = "Bearer "+ acessToken;



        try {
            getpendingorderURI = propperty.readFile("BaseURL") + propperty.readFile("getpendingorder");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Response getpendingorderresponse = request.getRequest(getpendingorderURI, auth);

        //get count of pwnding order for looged in user
        String query = "select count(*) from testing.order\n" +
                "where user_id = 7 and status = \"PENDING\"";

        String ordernumberquery = "select order_number from testing.order\n" +
                "where user_id = 7 and status = \"PENDING\"";

        ResultSet dbresult = db.connectdb(query);
        try {
            while (dbresult.next()) {
                pendingcount = dbresult.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(pendingcount>0){
            expectedstatuscode = 200;
        }
        else {
            expectedstatuscode = 400;
        }

        //Assert status code
        Assert.assertEquals(expectedstatuscode, getpendingorderresponse.getStatusCode());
        //Assert content type
        Assert.assertEquals("application/json;charset=UTF-8",getpendingorderresponse.getContentType());

        //query for order number
        ResultSet dbresult1 = db.connectdb(ordernumberquery);
        try {
            while (dbresult1.next()) {
                expectedordernumber = dbresult1.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int actualordernumber = Integer.parseInt(getpendingorderresponse.jsonPath().getString("orderNumber"));
        //assert ordernumber
        //System.out.println("actual order number" + actualordernumber +"expected ordernumber"+ expectedordernumber);
        Assert.assertEquals(expectedordernumber, actualordernumber);

    }
}
