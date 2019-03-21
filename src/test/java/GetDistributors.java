import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.BaseTest;

import java.io.IOException;

public class GetDistributors extends BaseTest {

    private String getdistributorURI;

    @Test
    public void getdistributors(){

        String auth = "Bearer "+ acessToken;

        try {
            getdistributorURI = property.readFile("BaseURL") + property.readFile("getdistributors");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Response getdistributorsresponse = request.getRequest(getdistributorURI, auth);
        //Assert status code
        Assert.assertEquals(200, getdistributorsresponse.getStatusCode());
        //Assert content type
        Assert.assertEquals("application/json;charset=UTF-8",getdistributorsresponse.getContentType());

        //System.out.println(getdistributorsresponse.getBody().asString());

    }
}
