import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.BaseTest;

import java.io.IOException;

public class GetProducts_ValidTest extends BaseTest {
    private String getproductsURI;

    @Test
    public void getProductsTest(){

        String auth = "Bearer "+ acessToken;

        try {
            getproductsURI = property.readFile("getproducts") + property.readFile("retailerid")+ property.readFile("retailerstoreid");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Response getproductsresponse = request.getRequest(getproductsURI, auth);
        //System.out.println(getproductsresponse.getBody().asString());

        //response assertion
        Assert.assertEquals(200,getproductsresponse.getStatusCode());
        // Assert.assertEquals(200 , getRetailersresponse.getStatusCode());
        Assert.assertEquals("application/json;charset=UTF-8",getproductsresponse.getContentType());


    }
}
