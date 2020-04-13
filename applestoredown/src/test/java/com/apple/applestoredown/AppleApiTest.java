package com.apple.applestoredown;

import org.testng.annotations.Test;



import static io.restassured.RestAssured.given;

import org.json.JSONArray;
import org.json.JSONObject;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import io.restassured.response.Response;

public class AppleApiTest {

	@BeforeTest
    public static void setupURL()
    {
        // here we setup the default URL and API base path to use throughout the tests
        RestAssured.baseURI = "https://istheapplestoredown.com";
   
    }
	
	@Test
	public void VerifyWebsiteStatusPerCountry(){
	
			Response response = given().
					header("Content-Type","application/json").
			when().
				get("/api/v1/status/worldwide").
			then().
				//log().all().
				assertThat().	
				statusCode(200). //Assert for 200 OK response
				contentType(ContentType.JSON).
				extract().response();	// extract the JSON response
			
			//parse the JSON response 
			String jsonAsString = response.asString();
			
			JSONObject jsonObj = new JSONObject(jsonAsString);
			
		    JSONArray keys = jsonObj.names();
		    
		    for(int i=0; i < keys.length(); i++)
		    {
		    	String key=keys.get(i).toString();
		    	JSONObject child = jsonObj.getJSONObject(key);		
				
		    	//If any country key has status : "y" then extract the country name
		    	if("y".equals(child.get("status"))) {	 
					Assert.fail("Failed country name is = " + child.get("name"));
				}
		    }
		    
		    Assert.assertTrue(true);
	}
		
}