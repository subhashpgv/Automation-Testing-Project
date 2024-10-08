package apiIntegrationTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import o2technologies_utils.*;

public class ProfileAPITestSQL {

	private static final String AUTH_TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoic3ViaGFzaCIsImh0dHA6Ly9zY2hlbWFzLm1pY3Jvc29mdC5jb20vd3MvMjAwOC8wNi9pZGVudGl0eS9jbGFpbXMvcm9sZSI6IkFkbWluIiwiaHR0cDovL3NjaGVtYXMueG1sc29hcC5vcmcvd3MvMjAwNS8wNS9pZGVudGl0eS9jbGFpbXMvbmFtZWlkZW50aWZpZXIiOiIxIiwiaHR0cDovL3NjaGVtYXMueG1sc29hcC5vcmcvd3MvMjAwNS8wNS9pZGVudGl0eS9jbGFpbXMvZW1haWxhZGRyZXNzIjoiU3ViaGFzaHRlc3RAc3ZlZ2lsZXRlY2guY29tIiwiZXhwIjoxNzIxODE3ODQ3LCJpc3MiOiJOZXhnaWxlLmNvbSIsImF1ZCI6Ik5leGdpbGUuY29tIn0.XIRRSPvMxzhF8mZkTUu3UlcXJnhtCRT1AcSjku0kLXQ";
	String FILE_PATH = "C:\\Users\\svegi\\eclipse-workspace\\IntegrationTesting\\src\\test\\resources\\AddGlobalProfile.xlsx";

    @Test(priority = 1)
	public void testAddProfile() throws SQLException, IOException {
    	
    	  // Set RestAssured to ignore SSL certificate validation
 	   RestAssured.useRelaxedHTTPSValidation();

		Map<String, String> profileData = readexceldata.readExcelData(FILE_PATH);

		given()
			.header("Authorization", AUTH_TOKEN)
			.header("userID", "1")
			.contentType(ContentType.JSON)
			.body(profileData)
			//.log().all()
		.when()
			.post("https://host.o2inc.net:8008/Api/Profile/Register")
		.then()
			.statusCode(200).body("status", equalTo("Success"))
			.body("message", equalTo("Profile Added Successfully"));

	}
     
    
    

	@Test(priority = 2)
	public void verifyDataInDatabase() throws SQLException, ClassNotFoundException, IOException {
		  // Set RestAssured to ignore SSL certificate validation
 	   RestAssured.useRelaxedHTTPSValidation();

		Map<String, String> profileData = readexceldata.readExcelData(FILE_PATH);

		String nameValue = profileData.get("name");
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

		String connectionURL = "jdbc:sqlserver://10.0.2.34:1433;Database=MailScan_Dev;User=mailscan;Password=MailScan@343260;encrypt=true;trustServerCertificate=true";

		Connection connection = DriverManager.getConnection(connectionURL);
		Statement statement = connection.createStatement();

		String query = "SELECT * FROM profile WHERE Name = '" + nameValue + "' ";

		System.out.println(query);
		ResultSet resultSet = statement.executeQuery(query);

		System.out.println(resultSet.toString());

		if (resultSet.next()) {
			String actualName = resultSet.getString("Name");

			if (nameValue.equals(actualName)) {
				System.out.println("Profile added to database successfully.");
			} else {
				System.out.println("Profile not added to database.");
			}
		} else {
			System.out.println("No records found in the database for Name: " + nameValue);
		}
		resultSet.close();
		statement.close();
		connection.close();
	}
}
