package org.techArk.tests.CRUD;

import com.github.javafaker.Faker;

import com.tekarch.base.APIHelper;
import com.tekarch.base.BaseTest;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.techArk.requestPOJO.AddDataRequest;
import org.techArk.requestPOJO.DeleteDataRequest;
import org.techArk.responsePOJO.AddDataResponse;
import org.techArk.responsePOJO.GetDataResponse;
import org.techArk.responsePOJO.LoginResponse;
import org.techArk.responsePOJO.StatusResponse;
import org.techArk.utils.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import java.util.List;

public class ValidateAddAndDeleteData_Functionality extends BaseTest {
    APIHelper apiHelper;
    String userId, accountNo, departmentNo, salary, pincode;
    private Faker faker;
    String dataId = "";

    @BeforeClass
    public void beforeClass() {
        faker = new Faker();
        apiHelper = new APIHelper();
//        Response login = apiHelper.login(EnvironmentDetails.getProperty("username"), EnvironmentDetails.getProperty("password"));
//        userId = login.getBody().as(new TypeRef<List<LoginResponse>>() {}).get(0).getUserid();
    }

//    @Test(priority = 0, description = "validate add data functionality")
//    public void validateAddDataFunctionality() {
//        accountNo = "TA-" + faker.number().numberBetween(10000, 20000);
//        departmentNo = "5";
//        salary = faker.number().numberBetween(15000, 85000) + "";
//        pincode = faker.address().zipCode();
//        AddDataRequest addDataRequest = AddDataRequest.builder().accountNo(accountNo).departmentNo(departmentNo).salary(salary).pinCode(pincode).build();
//        Response response = apiHelper.addData(addDataRequest);
//        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_CREATED, "Add data functionality is not working as expected.");
//        Assert.assertEquals(response.as(AddDataResponse.class).getStatus(), TestDataUtils.getProperty("successStatusMessage"), "The value of status key is not as expected in response ");
//        JsonSchemaValidate.validateSchema(response.asPrettyString(), "StatusResponseSchema.json");
//
//    }

//    @Test(priority = 1, description = "validate added data in the get data object", dependsOnMethods = "validateAddDataFunctionality")
//    public void validateAddedDataInGetData() {
//        Response data = apiHelper.getData();
//        List<GetDataResponse> getDataResponseList = data.getBody().as(new TypeRef<List<GetDataResponse>>() {
//        });
//        Assert.assertEquals(data.getStatusCode(), HttpStatus.SC_OK, "Response code is not matching for get data.");
//        GetDataResponse getDataResponse = null;
//        try {
//            getDataResponse = returnTheMatchingGetDataResponse(accountNo, userId, getDataResponseList);
//        } catch (NullPointerException e) {
//            Assert.fail("Added data is not available in the get data response");
//        }
//        dataId = getDataResponse.getId();
//        Assert.assertEquals(getDataResponse.getDepartmentNo(), departmentNo, "Add data functionality is not working as expected, Department number is not matching");
//        Assert.assertEquals(getDataResponse.getSalary(), salary, "Add data functionality is not working as expected, Salary is not matching");
//        Assert.assertEquals(getDataResponse.getPinCode(), pincode, "Add data functionality is not working as expected, Pincode is not matching");
//    }

    @Test(priority = 2, description = "delete repo functionality")//, dependsOnMethods = "validateAddDataFunctionality")
    public void validateDeleteData() {
    	 //        DeleteDataRequest deleteDataRequest = DeleteDataRequest.builder().userId(userId).id(dataId).build();
        Response data = apiHelper.deleteData(EnvironmentDetails.getProperty("token"), EnvironmentDetails.getProperty("deleteEndpoint"));
        Assert.assertEquals(data.getStatusCode(), HttpStatus.SC_NO_CONTENT, "Delete data functionality is not working as expected.");
//        Assert.assertEquals(data.as(StatusResponse.class).getStatus(), TestDataUtils.getProperty("successStatusMessage"), "The value of status key is not as expected in response ");
//        String actualResponse = data.jsonPath().prettyPrint();
//        JsonSchemaValidate.validateSchema(actualResponse, "StatusResponseSchema.json");
    }

    @Test(priority = 3, description = "validate deleted repo", dependsOnMethods = "validateDeleteData")
    public void validateDeletedDataInGetData() throws InterruptedException {
    	 
//       Response data = apiHelper.getData();
//        List<GetDataResponse> getDataResponseList = data.getBody().as(new TypeRef<List<GetDataResponse>>() {
//        });
    	Thread.sleep(100);
    	 Response data = apiHelper.getData(EnvironmentDetails.getProperty("token"), EnvironmentDetails.getProperty("deleteEndpoint"));
            	 Assert.assertEquals(data.getStatusCode() , HttpStatus.SC_NOT_FOUND , "Repo is deleted from github");
    }
//        if (returnTheMatchingGetDataResponse(accountNo, userId, getDataResponseList) != null) {
//            Assert.fail("Deleted data is still available in the get data response");
//        }
            	 
            	 
            	 @Test(priority = 4, description = "Add repo functionality", dependsOnMethods = "validateDeletedDataInGetData")
            	 public void addrepo() throws InterruptedException {

            		 //            	       Response data = apiHelper.getData();
            		 //            	        List<GetDataResponse> getDataResponseList = data.getBody().as(new TypeRef<List<GetDataResponse>>() {
            		 //  	});
            		 Thread.sleep(100);
            		 Response data1 = apiHelper.addData(EnvironmentDetails.getProperty("token"), EnvironmentDetails.getProperty("newRepoName"));
            		 Assert.assertEquals(data1.getStatusCode() , HttpStatus.SC_CREATED , "Repo is created in github");
            		 //            	        if (returnTheMatchingGetDataResponse(accountNo, userId, getDataResponseList) != null) {
            		 //            	            Assert.fail("Deleted data is still available in the get data response");
            		 //            	        }
        
        
    }
            	 
            	 @Test(priority = 5, description = "Validate repo", dependsOnMethods = "add repo")
            	 public void validaterepo() throws InterruptedException {

            		 //            	       Response data = apiHelper.getData();
            		 //            	        List<GetDataResponse> getDataResponseList = data.getBody().as(new TypeRef<List<GetDataResponse>>() {
            		 //  	});
            		 Thread.sleep(100);
            		 Response data = apiHelper.addData(EnvironmentDetails.getProperty("token"), EnvironmentDetails.getProperty("newRepoName"));
            		 Assert.assertEquals(data1.getStatusCode() , HttpStatus.SC_UNPROCESSABLE_ENTITY , "Repo already exists github, please use a different name");
            		 //            	        if (returnTheMatchingGetDataResponse(accountNo, userId, getDataResponseList) != null) {
            		 //            	            Assert.fail("Deleted data is still available in the get data response");
            		 //            	        }
        
        
    }
            	 
            	 @Test(priority = 6, description = "Update repo", dependsOnMethods = "validate repo")
            	 public void updaterepo() throws InterruptedException {

            		 //            	       Response data = apiHelper.getData();
            		 //            	        List<GetDataResponse> getDataResponseList = data.getBody().as(new TypeRef<List<GetDataResponse>>() {
            		 //  	});
            		 Thread.sleep(100);
            		 Response data = apiHelper.addData(EnvironmentDetails.getProperty("token"), EnvironmentDetails.getProperty("newRepoName"));
            		 Assert.assertEquals(data.getStatusCode() , HttpStatus.SC_OK , "Repo has been updated");
            		 //            	        if (returnTheMatchingGetDataResponse(accountNo, userId, getDataResponseList) != null) {
            		 //            	            Assert.fail("Deleted data is still available in the get data response");
            		 //            	        }
        
        
    }
 
            	 @Test(priority = 7, description = "Get all repo", dependsOnMethods = "update repo")
            	 public void getallrepo() throws InterruptedException {

            		 //            	       Response data = apiHelper.getData();
            		 //            	        List<GetDataResponse> getDataResponseList = data.getBody().as(new TypeRef<List<GetDataResponse>>() {
            		 //  	});
            		 Thread.sleep(100);
            		 Response data = apiHelper.getallData(EnvironmentDetails.getProperty("token"), EnvironmentDetails.getProperty("baseRepo"));
            		 System.out.println(data.getBody());
            		 Assert.assertEquals(data.getStatusCode() , HttpStatus.SC_OK , "All Repos have been listed");
            		 //            	        if (returnTheMatchingGetDataResponse(accountNo, userId, getDataResponseList) != null) {
            		 //            	            Assert.fail("Deleted data is still available in the get data response");
            		 //            	        }
        
        
    }
            	 
            	 
//    public GetDataResponse returnTheMatchingGetDataResponse(String accountNo, String userId, List<GetDataResponse> getDataResponseList) {
//        for (GetDataResponse dataResponse : getDataResponseList) {
//            if (dataResponse.getAccountNo().equals(accountNo) && dataResponse.getUserId().equals(userId))
//                return dataResponse;
//        }
//        return null;
//    }

}
