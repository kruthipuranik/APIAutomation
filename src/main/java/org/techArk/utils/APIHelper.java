package org.techArk.utils;

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

    public Response login(String username, String password) {
        LoginRequest loginRequest = LoginRequest.builder().username(username).password(password).build();
        reqSpec.headers(getHeaders(true));
        Response response = null;
        try {
            reqSpec.body(new ObjectMapper().writeValueAsString(loginRequest)); //Serializing loginrequest class to byte stream
            response = reqSpec.post("/login");
            response.then().log().all();
            if (response.getStatusCode() == HttpStatus.SC_CREATED) {
                List<LoginResponse> loginResponse = response.getBody().as(new TypeRef<List<LoginResponse>>() {
                });
                this.token = loginResponse.get(0).getToken();
            }
        } catch (Exception e) {
            Assert.fail("Login functionality is failing due to :: " + e.getMessage());
        }
        return response;
    }

    public Response getData() {
        reqSpec = RestAssured.given();
        reqSpec.headers(getHeaders(false));
        Response response = null;
        try {
            response = reqSpec.get("/getdata");
            response.then().log().all();
        } catch (Exception e) {
            Assert.fail("Get data is failing due to :: " + e.getMessage());
        }
        return response;
    }

    public Response addData(AddDataRequest addDataRequest) {
        reqSpec = RestAssured.given();
        Response response = null;
        try {
            log.info("Adding below data :: " + new ObjectMapper().writeValueAsString(addDataRequest));
            reqSpec.headers(getHeaders(false));
            reqSpec.body(new ObjectMapper().writeValueAsString(addDataRequest)); //Serializing addData Request POJO classes to byte stream
            response = reqSpec.post("/addData");
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

    public Response deleteData(DeleteDataRequest deleteDataRequest) {
        reqSpec = RestAssured.given();
        reqSpec.headers(getHeaders(false));
        Response response = null;
        try {
            reqSpec.body(new ObjectMapper().writeValueAsString(deleteDataRequest)); //Serializing addData Request POJO classes to byte stream
            response = reqSpec.delete("/deleteData");
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
