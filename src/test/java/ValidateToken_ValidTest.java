import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.BaseTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ValidateToken_ValidTest extends BaseTest {
    String validatetokenURI;
    Map jsonbody;

    @Test
    public void validateToken(){
        jsonbody = new HashMap();
        //Passing token to RequestHandler class
        String auth = "Bearer "+acessToken;
        request.setauthorization(auth);

        try {
            validatetokenURI = propperty.readFile("BaseURL")+propperty.readFile("validatetoken");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Response validatetokenresponse = request.postRequest(validatetokenURI, jsonbody);

        //assert status code
        Assert.assertEquals(200 , validatetokenresponse.getStatusCode());


       Assert.assertEquals("Valid Token" , validatetokenresponse.jsonPath().getString("data"));

       Assert.assertEquals("true" , validatetokenresponse.jsonPath().getString("success"));

       //System.out.println(validatetokenresponse.getBody().asString());

    }
}
