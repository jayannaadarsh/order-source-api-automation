import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.BaseTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ForgotPassword_invalidTest extends BaseTest {

    private String forgotpassworduri;

    @Test
    public void forgotPasswordInvalidTest(){

        //JSON payload
        Map<String,String> jsonbody = new HashMap<>();
        try {
            String invalidemail = property.readFile("email")+".com";
            String baseurl = property.readFile("baseurl");

            jsonbody.put("email", invalidemail);
            jsonbody.put("base_url",baseurl);

            //Getting URI
            forgotpassworduri=property.readFile("BaseURL")+property.readFile("forgotpassword");

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Hit url and get response
        Response response = request.postRequest(forgotpassworduri, jsonbody);
        //response assertion
        Assert.assertEquals(417,response.getStatusCode());
        Assert.assertEquals("application/json;charset=UTF-8",response.getContentType());

        try {
            //validate sucess message
            Assert.assertEquals(property.readForAssertion("forgotpassword_failure_message"),response.jsonPath().getString("data"));

            //validate sucess=true
            Assert.assertEquals(property.readForAssertion("success_invalid"),response.jsonPath().getString("success"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println("end forgetpassword invalid test");
    }




}
