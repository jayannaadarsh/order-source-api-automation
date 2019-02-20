package utilities;

import io.restassured.response.Response;
import org.apache.logging.log4j.*;
import org.testng.annotations.BeforeTest;
import utilities.PropertyFileReader;
import utilities.RequestHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {


    public String acessToken;
    public String refreshToken;
    String loginuri;
    public PropertyFileReader propperty = new PropertyFileReader() ;
    public RequestHandler request = new RequestHandler() ;
    Logger log = LogManager.getLogger(BaseTest.class.getName());
    public static Response response;
    public int x = 0;


    @BeforeTest

    public void LoginToGetToken(){
        x++;
        System.out.println("running before test--------------------"+x);
        Map jsonBody = new HashMap();
        //Map headers = new HashMap();

        try {
            //Get login uri
            String url=propperty.readFile("BaseURL");
            String path=propperty.readFile("Login");

            loginuri = url+path;

            //Add json body into map
            jsonBody.put("email", propperty.readFile("email"));
            jsonBody.put("password",propperty.readFile("password"));


        }

        catch (IOException e) {
            e.printStackTrace();
        }

        //call post method
        response = request.postRequest(loginuri, jsonBody);

        //Extract access token
        acessToken = response.jsonPath().get("accessToken");

        //Extract refresh token
        refreshToken = response.jsonPath().get("refreshToken");

        //System.out.println(response.asString());

        //storing access token and refresh token in data

    }


}
