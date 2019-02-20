import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.BaseTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ForgotPassword_ValidTest extends BaseTest {

    String forgotpassworduri;
    Map jsonbody;

    @Test
    public void forgotPasswordTest(){
        //JSON payload
        jsonbody = new HashMap();
        try {
            jsonbody.put("email", propperty.readFile("email"));

            //Getting URI
            forgotpassworduri=propperty.readFile("BaseURL")+propperty.readFile("forgotpassword");

        } catch (IOException e) {
            e.printStackTrace();
        }
        //Hit url and get response
        Response response = request.postRequest(forgotpassworduri, jsonbody);

        //response assertion
        Assert.assertEquals(200,response.getStatusCode());
        Assert.assertEquals("application/json;charset=UTF-8",response.getContentType());
        try {
            //validate sucess message
            Assert.assertEquals(propperty.readForAssertion("forgotpassword_sucess_message"),response.jsonPath().getString("data"));

            //validate sucess=true
            Assert.assertEquals(propperty.readForAssertion("success_valid"),response.jsonPath().getString("success"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("end forgetpassword valid test");



    }
}
