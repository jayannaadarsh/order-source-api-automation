package utilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class RequestHandler {

    public Response postRequest(String uri, Map reqbody){

        RequestSpecification request = RestAssured.given();
        JSONObject requestpara = new JSONObject();

        //Traversing map
        Set set=reqbody.entrySet();
        Iterator itr = set.iterator();
        while(itr.hasNext())
        {
            Map.Entry entry=(Map.Entry)itr.next();
            requestpara.put(entry.getKey().toString(),entry.getValue().toString());

        }

        request.header("Content-Type", "application/json");
        request.body(requestpara.toString());
        Response response = request.post(uri);

        return response;

    }

    public Response getRequest(String uri , String auth){
        RequestSpecification request = RestAssured.given();

        //seting headers
        request.header("Content-Type", "application/json");
        request.header("Authorization",auth);

        //Hit url
        Response response = request.get(uri);

        //return response
        return response;
    }

}
