package o2technologies_test;

import o2technologies_utils.DatabaseUtils;
import o2technologies_utils.ExtentReportManager;
import o2technologies_utils.InputValues;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import o2technologies_pages.GlobalProfilePage;
import o2technologies_pages.LoginPage;
import org.testng.annotations.Parameters;


/// --- "GlobalProfile_UI_DB_Test" Test case performs UI tests and database verifications. --- ///

/*--- Uncomment the following line to add listeners if the test cases are not running with the suite file. --- */
//@Listeners(o2technologies_utils.Listener.class)
public class GlobalProfile_UI_DB_Test {

	public WebDriver driver;
	private LoginPage login;
	private GlobalProfilePage globalProfile;
	private InputValues inputValuesForGoogle;
	private InputValues inputValuesForMobile;
	private InputValues inputValuesLogin;
	private InputValues inputValuesForUI;
	
	
	//private SoftAssert sa  ;

	
	
//    @BeforeTest 
//	  public void setUp() {
//	  ChromeOptions chromeOptions = new ChromeOptions();
//	  chromeOptions.setAcceptInsecureCerts(true); // This will ConfigureChromeOptions to accept insecure certificates. 
//	  driver = new ChromeDriver(chromeOptions);
//	  driver.manage().window().maximize(); 
//	  login = new LoginPage(driver); 
//	   globalProfile = new GlobalProfilePage(driver);
//	  inputValuesForGoogle = new InputValues("input_values_google.json");
//	  inputValuesForMobile = new InputValues("input_values_Mobile.json");
//	  inputValuesLogin = new InputValues("LoginCredentials.json"); 
//	  inputValuesForUI= new InputValues("input_values_UITest.json"); 
//	  
//	
//	  }
	 
	
	  @Parameters({"Browser","headless"} )
	    @BeforeClass
	    public void setUp(String br ,String headless)  throws InterruptedException 
	    {
	        boolean isHeadless = Boolean.parseBoolean(headless);

	        switch(br)
	        {
	            case "chrome": 
	                ChromeOptions chromeOptions = new ChromeOptions();
	                chromeOptions.setAcceptInsecureCerts(true);
	                if (isHeadless) 
	                {
	                    chromeOptions.addArguments("--headless");
	                    chromeOptions.addArguments("--disable-gpu");
	                    chromeOptions.addArguments("--window-size=1920,1080"); // Applicable to
	                }
	                driver = new ChromeDriver(chromeOptions);
	                break;
	            case "edge":
	                EdgeOptions edgeOptions = new EdgeOptions();
	                edgeOptions.setAcceptInsecureCerts(true);
	                if (isHeadless) 
	                {
	                    edgeOptions.addArguments("--headless");
	                    edgeOptions.addArguments("--disable-gpu");
	                    edgeOptions.addArguments("--window-size=1920,1080"); // Applicable to
	                }
	                driver = new EdgeDriver(edgeOptions);
	                break;
	            default : 
	                System.out.println("INVALID BROWSER");
	                return;
	        }

	        driver.manage().window().maximize();
	        login = new LoginPage(driver);
	        globalProfile = new GlobalProfilePage(driver);
	        inputValuesForGoogle = new InputValues("input_values_google.json");
	        inputValuesForMobile = new InputValues("input_values_Mobile.json");
	        inputValuesLogin = new InputValues("LoginCredentials.json");
	        inputValuesForUI = new InputValues("input_values_UITest.json");

	       
	    }
	 
	@AfterTest
	    public void tearDown() {
	     //   driver.quit();
	    }
	 
	@Test(description = "TC001 ",priority = 1)
	public void login() {
		Map<String, String> loginCredentials = inputValuesLogin.getInputValuesList().get(0);
	
		login.login(loginCredentials.get("username"), loginCredentials.get("password"));
		
		System.out.println("---------------------------------------------------------");
	}

	@Test(description = "TC002 ",priority = 2)
	public void addProfileWithGoogle() throws InterruptedException {
		
		List<Map<String, String>> profileInputData = inputValuesForGoogle.getInputValuesList();

		if (!profileInputData.isEmpty()) {
			Map<String, String> profileData = profileInputData.get(0);
			globalProfile.fillFormWithGmail(profileData.get("ProfileNameInput"), profileData.get("NameInput"),
					profileData.get("CompanyNameInput"), profileData.get("CompanyURLInput"),
					profileData.get("EmailIDInput"), profileData.get("PasswordInput"),
					profileData.get("conditionInput"));
			
			
			
		} else {
			System.out.println("Profile input data list is empty.");
		}
	
	}

	@Test(description = "TC003",priority = 3)
	public void addProfileWithMobile() throws InterruptedException {
		
		List<Map<String, String>> profileInputData = inputValuesForMobile.getInputValuesList();

		if (!profileInputData.isEmpty()) {
			Map<String, String> profileData = profileInputData.get(0);
			globalProfile.fillFormWithPhoneNumber(profileData.get("ProfileNameInput"), profileData.get("NameInput"),
					profileData.get("CompanyNameInput"), profileData.get("CompanyURLInput"),
					profileData.get("PhoneNumberInput"), profileData.get("condition"));
		} else {
			System.out.println("Profile input data list is empty.");
		}
		
	}

	@Test(description = "TC004",priority = 4)
	public void verifyProfileAdditionToDatabaseWithGoogle() {
		List<Map<String, String>> profileInputData = inputValuesForGoogle.getInputValuesList();
		String firstProfileName = profileInputData.get(0).get("NameInput");
		String query = "SELECT * FROM profile WHERE Name = '" + firstProfileName + "'";

		try (ResultSet resultSet = DatabaseUtils.executeQuery(query)) {
			if (resultSet.next()) {
				String actualName = resultSet.getString("Name");
				if (firstProfileName.equals(actualName)) {
					System.out.println("Verification of profile addition to the database was successful.");
				} else {
					System.out.println("Profile not added to database.");
				}
				System.out.println("---------------------------------------------------------");
			} else {
				System.out.println("No profile found in the database with Name: " + firstProfileName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test(description = "TC005",priority = 5)
	public void verifyProfileUpdateInDatabase() {
		globalProfile.Update();
		String value = globalProfile.getUpdatedValue();
		String query = "SELECT * FROM profile WHERE ProfileName = '" + value + "'";

		try (ResultSet resultSet = DatabaseUtils.executeQuery(query)) {
			if (resultSet.next()) {
				String actualName = resultSet.getString("ProfileName");
				if (value.trim().equalsIgnoreCase(actualName.trim())) {
					System.out.println("Profile Updated to Database successfully.");
				} else {
					System.out.println("Profile not updated in Database.");
				}
			} else {
				System.out.println("No profile found in the database with ProfileName: " + value);
			}
			System.out.println("---------------------------------------------------------");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test(description = "TC006",priority = 6)
	public void verifyProfileDeletionFromDatabase() {
		globalProfile.Delete();
		String value = globalProfile.getUpdatedValue();
		String query = "SELECT * FROM profile WHERE Name = '" + value + "'";

		try (ResultSet resultSet = DatabaseUtils.executeQuery(query)) {
			if (!resultSet.next()) {
				System.out.println("Profile deleted successfully from the database");
			} else {
				String actualName = resultSet.getString("ProfileName");
				if (actualName == null) {
					System.out.println("Profile not deleted from the database");
				} else {
					System.out.println(actualName + ": Profile not deleted from the database.");
				}
				System.out.println("---------------------------------------------------------");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test(description = "TC007",priority = 7)
	public void dateCreatedTest() {
		System.out.println("----------------UI TESTS-------------------------");
		List<Map<String, String>> profileInputData = inputValuesForUI.getInputValuesList();

		if (!profileInputData.isEmpty()) {
			Map<String, String> profileData = profileInputData.get(0);
			globalProfile.dateCreated(profileData.get("ProfileNameInput"), profileData.get("NameInput"),
					profileData.get("CompanyNameInput"), profileData.get("CompanyURLInput"),
					profileData.get("EmailIDInput"), profileData.get("PasswordInput"),
					profileData.get("conditionInput"));
			System.out.println("---------------------------------------------------------");
		} else {
			System.out.println("Profile input data list is empty.");
		}

	}

	@Test(description = "TC008",priority = 8)
	public void verifyUIElements() {
		globalProfile.verifyUIElements();
	
		System.out.println("---------------------------------------------------------");		
	}

	@Test(description = "TC009",priority = 9)
	public void searchVerification() {
		globalProfile.searchVerification();
	}

	@Test(description = "TC010",priority = 10)
	public void company_URLS() {
		globalProfile.company_URLS();
		
	}

	@Test(description = "TC011",priority =11)
	public void pageNation() {
		
		globalProfile.pageNation();
		
	}
}
