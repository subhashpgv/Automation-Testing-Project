package apiIntegrationTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;


///----"ProfileAPITestApi"  Registers a new global profile and verifies its successful addition by calling another API.----///
public class ProfileAPITestApi { 


    private static final String AUTH_TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoic3ViaGFzaCIsImh0dHA6Ly9zY2hlbWFzLm1pY3Jvc29mdC5jb20vd3MvMjAwOC8wNi9pZGVudGl0eS9jbGFpbXMvcm9sZSI6IkFkbWluIiwiaHR0cDovL3NjaGVtYXMueG1sc29hcC5vcmcvd3MvMjAwNS8wNS9pZGVudGl0eS9jbGFpbXMvbmFtZWlkZW50aWZpZXIiOiIxIiwiaHR0cDovL3NjaGVtYXMueG1sc29hcC5vcmcvd3MvMjAwNS8wNS9pZGVudGl0eS9jbGFpbXMvZW1haWxhZGRyZXNzIjoiU3ViaGFzaHRlc3RAc3ZlZ2lsZXRlY2guY29tIiwiZXhwIjoxNzIxODE3ODQ3LCJpc3MiOiJOZXhnaWxlLmNvbSIsImF1ZCI6Ik5leGdpbGUuY29tIn0.XIRRSPvMxzhF8mZkTUu3UlcXJnhtCRT1AcSjku0kLXQ";
    private static final String FILE_PATH = "C:\\Users\\svegi\\eclipse-workspace\\IntegrationTesting\\src\\test\\resources\\AddGlobalProfile.json";
    private String requestBody;
    private String Name;
    
    @BeforeClass
    public void setUp() throws IOException {
    	  // Set RestAssured to ignore SSL certificate validation
    	   RestAssured.useRelaxedHTTPSValidation();
    	  
             requestBody = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
             // 
           //  System.out.println("Request Body Loaded: " + requestBody);
             
             
             JSONObject jsonObject = new JSONObject(requestBody);
             Name = jsonObject.getString("name");
             System.out.println("Extracted Profile Name: " + Name);
        
    }
    
    // Perform POST request and validate response with another API endpoint
    
   @Test(priority=1)
    public void testAddProfile()  {
	
	   // Ensure requestBody is not null or empty
       if (requestBody == null || requestBody.isEmpty()) {
           throw new RuntimeException("Request body is null or empty");
       }
       
        given()
            .header("Authorization", AUTH_TOKEN)
            .header("userID", "1")
            .contentType(ContentType.JSON)
            .body(requestBody)
            .log().all()
        .when()
            .post("https://10.0.2.34:8008/Api/Profile/Register")  
        .then()
            .statusCode(200)
            .body("status", equalTo("Success"))
            .body("message", equalTo("Profile Added Successfully"));
    }

        @Test(priority=2)
        public void testAddProfileAndVerify() {
        	 
        // Verify the data via API endpoint
        List<Map<String, Object>> profiles = given()
					            .header("Authorization", AUTH_TOKEN)
					            .header("userID", "1")
					        .when()
					            .get("https://10.0.2.34:8008/Api/Profile/Profiles?isDeletedProfilesListed=false")  // Assuming this endpoint retrieves the list of profiles
					        .then()
					            .statusCode(200)
					            .extract().jsonPath().getList("");
    
        // System.out.println(profiles);
        boolean profileFound = false;
          for (Map<String, Object> profile : profiles) {
            if (Name.equals(profile.get("name"))) {
                profileFound = true;  
                break;
            }
        }

        assert profileFound : "Profile not found in the list.";
    }
}
