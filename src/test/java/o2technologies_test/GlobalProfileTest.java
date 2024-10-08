package o2technologies_test;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import o2technologies_pages.GlobalProfilePage;
import o2technologies_pages.LoginPage;
import o2technologies_utils.InputValues;

public class GlobalProfileTest { 
	private WebDriver driver;
	private LoginPage login;
	private GlobalProfilePage globalProfile;
	private InputValues inputValuesForPhone;
	private InputValues inputValuesForGoogle;
	private InputValues inputValuesLogin;

	@Parameters({"Browser","headless"} )
	@BeforeTest
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
				driver =new ChromeDriver(chromeOptions);
				
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
				driver =new EdgeDriver(edgeOptions);
				break;
			default : System.out.println("INVALID BROWSER");
			return;
		}
		
		driver.manage().window().maximize();
		login = new LoginPage(driver);
		globalProfile = new GlobalProfilePage(driver);
		inputValuesForPhone = new InputValues("Input_values.json");
		inputValuesForGoogle = new InputValues("input_values_google.json");
		inputValuesLogin = new InputValues("LoginCredentials.json");

	}
	
	

	@AfterTest
	public void tearDown() {
		// driver.quit();
	}

	@Test(priority = 1)
	public void login() {
		// Login using the first set of credentials from inputValuesLogin
		Map<String, String> LoginCredentials = inputValuesLogin.getInputValuesList().get(0);
		login.login(LoginCredentials.get("username"), LoginCredentials.get("password"));
	}

	@Test(priority=2)
	public void addProfileWithPhonenumber() throws InterruptedException {

		globalProfile.navigation();

		// Fill profile form using data from inputValues
		List<Map<String, String>> profileInputData = inputValuesForPhone.getInputValuesList();
		for (Map<String, String> profileData : profileInputData) {

			globalProfile.fillFormWithPhoneNumber(profileData.get("ProfileNameInput"), profileData.get("NameInput"),
					profileData.get("CompanyNameInput"), profileData.get("CompanyURLInput"),
					profileData.get("PhoneNumberInput"), profileData.get("conditionInput"));
		}
	
	}

	// @Test(priority=3)
	public void addProfileWithGoogle() throws InterruptedException

	{

		List<Map<String, String>> profileInputData2 = inputValuesForGoogle.getInputValuesList();
		for (Map<String, String> profileData2 : profileInputData2) {

			globalProfile.fillFormWithGmail(profileData2.get("ProfileNameInput"), profileData2.get("NameInput"),
					profileData2.get("CompanyNameInput"), profileData2.get("CompanyURLInput"),
					profileData2.get("EmailIDInput"), profileData2.get("PasswordInput"),
					profileData2.get("conditionInput"));
		}
		
	}

	//@Test(priority = 4)
	public void UITest() {

		//globalProfile.navigation();
		globalProfile.verifyUIElements();
		globalProfile.pageNation();
		globalProfile.company_URLS();
		globalProfile.Update();
        globalProfile.Delete();
	}
}
