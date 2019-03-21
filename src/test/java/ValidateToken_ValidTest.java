import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.BaseTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ValidateToken_ValidTest extends BaseTest {
    private String validatetokenURI;
   //Logger log = LogManager.getLogger(ValidateToken_ValidTest.class.getName());

    @Test
    public void validateToken(){
        Map jsonbody = new HashMap();
        //Passing token to RequestHandler class
        String auth = "Bearer "+acessToken;
        request.setauthorization(auth);

        try {
            validatetokenURI = property.readFile("BaseURL")+property.readFile("validatetoken");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Response validatetokenresponse = request.postRequest(validatetokenURI, jsonbody);

        //assert status code
        Assert.assertEquals(200 , validatetokenresponse.getStatusCode());


       Assert.assertEquals("Valid Token" , validatetokenresponse.jsonPath().getString("data"));

       Assert.assertEquals("true" , validatetokenresponse.jsonPath().getString("success"));

    }
}
