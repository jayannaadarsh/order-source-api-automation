package utilities;

import io.restassured.response.Response;
import org.apache.logging.log4j.*;
import org.testng.annotations.BeforeClass;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {


    public String acessToken = "";
    public String refreshToken = "";
    String loginuri;
    public PropertyFileReader property = new PropertyFileReader() ;
    public RequestHandler request = new RequestHandler() ;
    //Logger log = LogManager.getLogger(BaseTest.class.getName());
    public static Response response;


    @BeforeClass

    public void LoginToGetToken(){
        Map<String, String> jsonBody = new HashMap<>();
        //Map headers = new HashMap();

        try {
            //Get login uri
            String url=property.readFile("BaseURL");
            String path=property.readFile("Login");

            loginuri = url+path;

            //Add json body into map
            jsonBody.put("email", property.readFile("email"));
            jsonBody.put("password",property.readFile("password"));

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


    }


}
