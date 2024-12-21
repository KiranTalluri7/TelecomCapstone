package com.Telecom;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class TelecomAPIs {
	String authToken;
	String id;

	@Test(priority = 1)
	public void AddNewUser() {
		String dynamicEmail = "Davey_" + System.currentTimeMillis() + "@warner.com";

		Response res = given().header("application", "application/json").header("Content-Type", "application/json")

				.body("{\n" + "\"firstName\": \"warner\",\n" + "\"lastName\": \"Bull\",\n" + "\"email\": \""
						+ dynamicEmail + "\",\n" + "\"password\": \"myPassword\"\n" + "}")
				.when().post("https://thinking-tester-contact-list.herokuapp.com/users");
		res.then().log().body();

		int statusCode = res.getStatusCode();
		System.out.println("Status Code: " + statusCode);
		String statusLine = res.getStatusLine();
		System.out.println("Status Line: " + statusLine);

		// saving Token
		authToken = res.jsonPath().getString("token");
		System.out.println("Generated Token: " + authToken);

		// Assertions
		Assert.assertEquals(statusCode, 201, "Unexpected status code!");
		Assert.assertTrue(statusLine.contains("Created"), "Message does not contain 'Created'");

	}

	@Test(priority = 2, dependsOnMethods = "AddNewUser")
	public void GetUserProfile() {
		Response res = given().header("application", "application/json").header("Content-Type", "application/json")
				.header("Authorization", "Bearer " + authToken).when()
				.get("https://thinking-tester-contact-list.herokuapp.com/users/me");

		res.then().log().body();
		int statusCode = res.getStatusCode();
		System.out.println("Status Code: " + statusCode);
		String statusLine = res.getStatusLine();
		System.out.println("Status Line: " + statusLine);
// Assertions
		Assert.assertEquals(statusCode, 200, "Unexpected status code!");
		Assert.assertTrue(statusLine.contains("OK"), "Message does not contain 'OK'");

	}

	@Test(priority = 3, dependsOnMethods = "AddNewUser")
	public void UpdateUser() {
		String dynamicEmail = "Davey_" + System.currentTimeMillis() + "@warner.com";

		Response res = given().header("application", "application/json").header("Content-Type", "application/json")
				.header("Authorization", "Bearer " + authToken)

				.body("{\r\n" + "\"firstName\": \"Jishnu\",\r\n" + "\"lastName\": \"Kings\",\r\n" + "\"email\": \""
						+ dynamicEmail + "\",\r\n" + "\"password\": \"myNewPassword\"\r\n" + "}\r\n" + "")
				.when().patch("https://thinking-tester-contact-list.herokuapp.com/users/me");
		res.then().log().body();
		int statusCode = res.getStatusCode();
		System.out.println("Status Code: " + statusCode);
		String statusLine = res.getStatusLine();
		System.out.println("Status Line: " + statusLine);
// Assertions
		Assert.assertEquals(statusCode, 200, "Unexpected status code!");
		Assert.assertTrue(statusLine.contains("OK"), "Message does not contain 'OK'");

	}

	@Test(priority = 4)
	public void LoginUser() {
		Response res = given()
				.header("application", "application/json").header("Content-Type", "application/json").body("{\r\n"
						+ "\"email\": \"kkk@fake.com\",\r\n" + "\"password\": \"myNewPassword\"\r\n" + "}\r\n" + "")
				.when().post("https://thinking-tester-contact-list.herokuapp.com/users/login");
		res.then().log().body();
		int statusCode = res.getStatusCode();
		System.out.println("Status Code: " + statusCode);
		String statusLine = res.getStatusLine();
		System.out.println("Status Line: " + statusLine);
// Assertions
		Assert.assertEquals(statusCode, 200, "Unexpected status code!");
		Assert.assertTrue(statusLine.contains("OK"), "Message does not contain 'OK'");
	}

	@Test(priority = 5, dependsOnMethods = "AddNewUser")
	public void AddContact() {
		Response res = given().header("application", "application/json").header("Content-Type", "application/json")
				.header("Authorization", "Bearer " + authToken)
				.body("{ \r\n" + "\"firstName\": \"John\", \r\n" + "\"lastName\": \"Doe\", \r\n"
						+ "\"birthdate\": \"1970-01-01\", \r\n" + "\"email\": \"jdoe@fake.com\", \r\n"
						+ "\"phone\": \"8005555555\", \r\n" + "\"street1\": \"1 Main St.\", \r\n"
						+ "\"street2\": \"Apartment A\", \r\n" + "\"city\": \"Anytown\", \r\n"
						+ "\"stateProvince\": \"KS\", \r\n" + "\"postalCode\": \"12345\", \r\n"
						+ "\"country\": \"USA\" \r\n" + "} \r\n" + " ")
				.when().post("https://thinking-tester-contact-list.herokuapp.com/contacts");
		res.then().log().body();
		int statusCode = res.getStatusCode();
		System.out.println("Status Code: " + statusCode);
		String statusLine = res.getStatusLine();
		System.out.println("Status Line: " + statusLine);
		  id=res.jsonPath().getString("_id");

// Assertions
		Assert.assertEquals(statusCode, 201, "Unexpected status code!");
		Assert.assertTrue(statusLine.contains("Created"), "Message does not contain 'Created'");

	}

	@Test(priority = 6, dependsOnMethods = "AddNewUser")
	public void GetContactList() {
		Response res = given().header("application", "application/json").header("Content-Type", "application/json")
				.header("Authorization", "Bearer " + authToken)

				.when().get("https://thinking-tester-contact-list.herokuapp.com/contacts");
		res.then().log().body();
		int statusCode = res.getStatusCode();
		System.out.println("Status Code: " + statusCode);
		String statusLine = res.getStatusLine();
		System.out.println("Status Line: " + statusLine);
// Assertions
		Assert.assertEquals(statusCode, 200, "Unexpected status code!");
		Assert.assertTrue(statusLine.contains("OK"), "Message does not contain 'OK'");

	}

	@Test(priority = 7, dependsOnMethods = "AddNewUser")
	public void GetContact() {
		Response res = given()

				.header("application", "application/json").header("Content-Type", "application/json")
				.header("Authorization", "Bearer " + authToken).when()
				.get("https://thinking-tester-contact-list.herokuapp.com/contacts/");
		res.then().log().body();
		int statusCode = res.getStatusCode();
		System.out.println("Status Code: " + statusCode);
		String statusLine = res.getStatusLine();
		System.out.println("Status Line: " + statusLine);
// Assertions
		Assert.assertEquals(statusCode, 200, "Unexpected status code!");
		Assert.assertTrue(statusLine.contains("OK"), "Message does not contain 'OK'");

	}

	@Test(priority = 8, dependsOnMethods ={"AddNewUser", "AddContact"})
	public void UpdateContact() {
		Response res = given()

				.header("application", "application/json").header("Content-Type", "application/json")
				.header("Authorization", "Bearer " + authToken)
				.body("{ \r\n" + "\"firstName\": \"Amys\", \r\n" + "\"lastName\": \"Millesr\",\r\n"
						+ "\"birthdate\": \"1992-12-02\", \r\n" + "\"email\": \"test@fake.com\", \r\n"
						+ "\"phone\": \"8005554242\", \r\n" + "\"street1\": \"13 School St.\", \r\n"
						+ "\"street2\": \"Apt. 5\", \r\n" + "\"city\": \"Washington\", \r\n"
						+ "\"stateProvince\": \"QC\", \r\n" + "\"postalCode\": \"A1A1A1\", \r\n"
						+ "\"country\": \"Canada\" \r\n" + "}")
				  .when().put("https://thinking-tester-contact-list.herokuapp.com/contacts/"+id);
		res.then().log().body();
		int statusCode = res.getStatusCode();
		
		  System.out.println("Status Code: " + statusCode); String statusLine =
		  res.getStatusLine(); System.out.println("Status Line: " + statusLine);
		  //Assertions 
		  Assert.assertEquals(statusCode, 200, "Unexpected status code!");
		  Assert.assertTrue(statusLine.contains("OK"),
		  "Message does not contain 'OK'");
		  
		  String email = res.jsonPath().getString("email"); String expectedEmail =
		  "test@fake.com"; Assert.assertEquals(email, expectedEmail,
		  "Email does not match the expected value");
		 
		//System.out.println("User updated with code: " + res.statusCode());

	}

	@Test(priority = 9, dependsOnMethods ={"AddNewUser", "AddContact"})
	public void UpdateContact2() {
		Response res = given()

				.header("application", "application/json").header("Content-Type", "application/json")
				.header("Authorization", "Bearer " + authToken)
				.body("{ \n"
				  		+ "\"firstName\": \"Amy\", \n"
				  		+ "\"lastName\": \"Miller\"} ")
				  .when().put("https://thinking-tester-contact-list.herokuapp.com/contacts/"+id);
		res.then().log().body();
		
		 int statusCode = res.getStatusCode(); System.out.println("Status Code: " +statusCode);
		 String statusLine = res.getStatusLine();
		  System.out.println("Status Line: " + statusLine); 
		 // Assertions
		  Assert.assertEquals(statusCode, 200, "Unexpected status code!");
		 Assert.assertTrue(statusLine.contains("OK"),"Message does not contain 'OK'");
		  
		  
		 
		//System.out.println("User updated with code: " + res.statusCode());

	}

	@Test(priority = 10, dependsOnMethods = "AddNewUser")
	public void LogOut() {
		Response res = given()

				.header("application", "application/json").header("Content-Type", "application/json")
				.header("Authorization", "Bearer " + authToken).when()
				.post("https://thinking-tester-contact-list.herokuapp.com/users/logout");
		int statusCode = res.getStatusCode();
		System.out.println("Status Code: " + statusCode);
		String statusLine = res.getStatusLine();
		System.out.println("Status Line: " + statusLine);
// Assertions
		Assert.assertEquals(statusCode, 200, "Unexpected status code!");
		Assert.assertTrue(statusLine.contains("OK"), "Message does not contain 'OK'");
	}
}
