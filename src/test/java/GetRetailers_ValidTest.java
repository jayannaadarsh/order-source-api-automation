import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.BaseTest;

import java.io.IOException;

public class GetRetailers_ValidTest extends BaseTest {
    private String getretailersuri ;

    @Test
    public void getRetailers(){

        String auth = "Bearer "+ acessToken;

        try {
            getretailersuri = property.readFile("BaseURL")+ property.readFile("getretailers");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Response getRetailersresponse = request.getRequest(getretailersuri, auth);
        //System.out.println(response.getBody().asString());

        //response assertion
        Assert.assertEquals(200,getRetailersresponse.getStatusCode());
        // Assert.assertEquals(200 , getRetailersresponse.getStatusCode());
        Assert.assertEquals("application/json;charset=UTF-8",getRetailersresponse.getContentType());


    }

}
