import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.BaseTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RefreshToken_ValidTest extends BaseTest
{
    String refreshtokenuri;
    Map jsonbody;
    @Test
    public void refreshTokenTest(){

        // payload
       refreshToken = response.jsonPath().get("refreshToken");
        jsonbody = new HashMap();
        System.out.println(refreshToken+"******************************1");
        jsonbody.put("refreshToken", refreshToken);
        System.out.println(refreshToken+"******************************2");

        try {
            //getting refreshtoken uri
            String path = propperty.readFile("refreshtoken");
            String baseurl = propperty.readFile("BaseURL");
            refreshtokenuri = baseurl+path;

        } catch (IOException e) {
            e.printStackTrace();
        }

        //calling post request
        Response response = request.postRequest(refreshtokenuri, jsonbody);

        //response JSON validation
        Assert.assertEquals(200,response.getStatusCode());
        Assert.assertEquals("application/json;charset=UTF-8",response.getContentType());
        //System.out.println(response.getContentType());

        System.out.println("end refresh token invalid test");


    }
}
