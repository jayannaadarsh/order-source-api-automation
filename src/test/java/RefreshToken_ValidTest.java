import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.BaseTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RefreshToken_ValidTest extends BaseTest
{
    private String refreshtokenuri;

    @Test
    public void refreshTokenTest(){

        // payload
       refreshToken = response.jsonPath().get("refreshToken");
        Map<String, String> jsonbody = new HashMap<>();
        jsonbody.put("refreshToken", refreshToken);

        try {
            //getting refreshtoken uri
            String path = property.readFile("refreshtoken");
            String baseurl = property.readFile("BaseURL");
            refreshtokenuri = baseurl+path;

        } catch (IOException e) {
            e.printStackTrace();
        }

        //calling post request
        Response response = request.postRequest(refreshtokenuri, jsonbody);

        //response JSON validation
        Assert.assertEquals(200,response.getStatusCode());
        Assert.assertEquals("application/json;charset=UTF-8",response.getContentType());



    }
}
