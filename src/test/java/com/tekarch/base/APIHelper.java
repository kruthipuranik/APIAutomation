package com.tekarch.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.techArk.requestPOJO.AddDataRequest;
import org.techArk.requestPOJO.DeleteDataRequest;
import org.techArk.requestPOJO.LoginRequest;
import org.techArk.requestPOJO.UpdateDataRequest;
import org.techArk.responsePOJO.LoginResponse;
import org.techArk.utils.EnvironmentDetails;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;

@Slf4j
public class APIHelper {
    RequestSpecification reqSpec;
    String token = "";

    public APIHelper() {
        RestAssured.baseURI = EnvironmentDetails.getProperty("baseURL");
        reqSpec = RestAssured.given();
        
       
    }

    public Response login(String token, String endpoint) {
    	
        //LoginRequest loginRequest = LoginRequest.builder().username(username).password(password).build(); // payload 
     
        reqSpec.headers(getHeaders(true));
        Response response = null;
//        token = EnvironmentDetails.getProperty("token");
//        endpoint = EnvironmentDetails.getProperty("endpoint");
        String bURL = EnvironmentDetails.getProperty("baseURL");
        try {
            //reqSpec.body(loginRequest); //Serializing loginrequest class to byte stream
             //response = reqSpec.post("/login");
            response = reqSpec.baseUri(bURL).header("Authorization", "Bearer " + token).get(endpoint);
            if (response.getStatusCode() == HttpStatus.SC_OK) {
//                List<LoginResponse> loginResponse = response.getBody().as(new TypeRef<List<LoginResponse>>() {
//                });
//                this.token = loginResponse.get(0).getToken();
                System.out.println("Response is successfull for "+bURL+endpoint);
            }
        } catch (Exception e) {
            Assert.fail("Login functionality is failing due to :: " + e.getMessage());
        }
        return response;
    }

    public Response getData(String token ,String deleteEndpoint) {
        reqSpec = RestAssured.given();
        reqSpec.headers(getHeaders(false));
        Response response = null;
        String bURL = EnvironmentDetails.getProperty("baseURL");
        try {
//            response = reqSpec.get("/getdata");
        	response = reqSpec.baseUri(bURL).header("Authorization", "Bearer " + token).get(deleteEndpoint);
            response.then().log().all();
        } catch (Exception e) {
            Assert.fail("Get data is failing due to :: " + e.getMessage());
        }
        return response;
    }
    
    public Response getallData(String token ,String Endpoint) {
        reqSpec = RestAssured.given();
        reqSpec.headers(getHeaders(false));
        Response response = null;
        String bURL = EnvironmentDetails.getProperty("baseURL");
        try {
//            response = reqSpec.get("/getdata");
        	response = reqSpec.baseUri(bURL).header("Authorization", "Bearer " + token).get(Endpoint);
            response.then().log().all();
        } catch (Exception e) {
            Assert.fail("Get data is failing due to :: " + e.getMessage());
        }
        return response;
    }

    public Response addData(String token, String newRepoName) {
        reqSpec = RestAssured.given();
        Response response = null;
        String bURL = EnvironmentDetails.getProperty("baseURL");
        String repoUrl = bURL+"user/repos"; //"https://api.github.com/user/repos";
        String requestBody = "{\"name\":\"Hello-World2\",\"description\":\"This is your first repository\",\"homepage\":\"https://github.com\",\"private\":false,\"has_issues\":true,\"has_projects\":true,\"has_wiki\":true}";
        try {
            //log.info("Adding below data :: " + new ObjectMapper().writeValueAsString(addDataRequest));
            //reqSpec.headers(getHeaders(false));
            //reqSpec.body(new ObjectMapper().writeValueAsString(addDataRequest)); //Serializing addData Request POJO classes to byte stream
            //response = reqSpec.post("/addData");
             response = reqSpec
                    .header("Authorization", "Bearer " + token)
                    .body(requestBody)
                    .post(repoUrl);
            response.then().log().all();
        } catch (Exception e) {
            Assert.fail("Add data functionality is failing due to :: " + e.getMessage());
        }
        return response;
    }
    
    public Response patchData(String token, String RepoName) {
        reqSpec = RestAssured.given();
        Response response = null;
        String bURL = EnvironmentDetails.getProperty("baseURL");
        String repoUrl = bURL+"repos/kruthipuranik"+RepoName; //"https://api.github.com/user/repos";
        String requestBody = "{\"name\":\"Hello-World2\",\"description\":\"This is your first repository\",\"private\":false}";
        try {
            //log.info("Adding below data :: " + new ObjectMapper().writeValueAsString(addDataRequest));
            //reqSpec.headers(getHeaders(false));
            //reqSpec.body(new ObjectMapper().writeValueAsString(addDataRequest)); //Serializing addData Request POJO classes to byte stream
            //response = reqSpec.post("/addData");
             response = reqSpec
                    .header("Authorization", "Bearer " + token)
                    .body(requestBody)
                    .patch(repoUrl);
            response.then().log().all();
        } catch (Exception e) {
            Assert.fail("Add data functionality is failing due to :: " + e.getMessage());
        }
        return response;
    }

    public Response putData(UpdateDataRequest updateDataRequest) {
        reqSpec = RestAssured.given();
        reqSpec.headers(getHeaders(false));
        Response response = null;
        try {
            reqSpec.body(new ObjectMapper().writeValueAsString(updateDataRequest)); //Serializing addData Request POJO classes to byte stream
            response = reqSpec.put("/updateData");
            response.then().log().all();
        } catch (Exception e) {
            Assert.fail("Update data functionality is failing due to :: " + e.getMessage());
        }
        return response;
    }

    public Response deleteData(String token, String deleteEndpoint) {
        reqSpec = RestAssured.given();
        reqSpec.headers(getHeaders(false));
        String dep = EnvironmentDetails.getProperty("baseURL")+EnvironmentDetails.getProperty("deleteEndpoint");
        Response response = null;
        try {
//            reqSpec.body(new ObjectMapper().writeValueAsString(deleteDataRequest)); //Serializing addData Request POJO classes to byte stream
//            response = reqSpec.delete("/deleteData");
        	 response = reqSpec.header("Authorization", "Bearer " + token).delete(dep);
            response.then().log().all();
        } catch (Exception e) {
            Assert.fail("Delete data functionality is failing due to :: " + e.getMessage());
        }
        return response;
    }

    public HashMap<String, String> getHeaders(boolean forLogin) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        if (!forLogin) {
            headers.put("token", token);
        }
        return headers;
    }

}
