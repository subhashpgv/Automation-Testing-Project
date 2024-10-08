package o2technologies_test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.*;

import o2technologies_pages.ConfigerWorkflowButton;
import o2technologies_pages.LoginPage;
import o2technologies_pages.SearchNewCandidatesPage;
import o2technologies_utils.*;

public class SearchCandidateTest {
    private LoginPage loginPage;
    private WebDriver driver;
    private static SearchNewCandidatesPage searchNewCandidatesPage;
    private static ConfigerWorkflowButton configerWorkflowButton;
    private static  Excel_InputValues Excel_InputValues;

   // private InputValues inputValues;

    @Parameters({"Browser","headless"} )
	@BeforeClass
	public void setUp(String br ,String headless)  throws InterruptedException, IOException 
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
        loginPage = new LoginPage(driver);
        searchNewCandidatesPage = new SearchNewCandidatesPage(driver);
        configerWorkflowButton = new ConfigerWorkflowButton(driver);
      
       // inputValues = new InputValues("input_values.json");
        Excel_InputValues = new Excel_InputValues("C:\\Users\\svegi\\eclipse-workspace\\IntegrationTesting\\src\\test\\resources\\testdata.xlsx","Sheet1");
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

    
    
    @Test
    public void searchCandidate() throws InterruptedException {
//        for (Map<String, String> inputValue : inputValues.getInputValuesList()) {
//            loginPage.login(inputValue.get("username"), inputValue.get("password"));
//            
//            boolean isSearchSuccessful = configerWorkflowButton.PerformSearchNewCandidate(inputValue.get("NameInputValue"), inputValue.get("totalCountValue"));
//            
//            if(!isSearchSuccessful) {
//            	searchNewCandidatesPage.navigateToSearchNewCandidate();
//            }
//            
//            searchNewCandidatesPage.fillForm(inputValue.get("searchCandidateInput"), inputValue.get("location"), inputValue.get("distance"), inputValue.get("filterName"), inputValue.get("SkillKeyWord")); 
//
//        }
        
        List<Map<String, String>> dataList = Excel_InputValues.getExcelData();
        for (Map<String, String> data : dataList) {
        	 loginPage.login(data.get("username"), data.get("password"));
             
             boolean isSearchSuccessful = configerWorkflowButton.PerformSearchNewCandidate(data.get("NameInputValue"), data.get("totalCountValue"));
             
             if(!isSearchSuccessful) {
             	searchNewCandidatesPage.navigateToSearchNewCandidate();
             }
             
             searchNewCandidatesPage.fillForm(data.get("searchCandidateInput"), data.get("location"), data.get("distance"), data.get("filterName"), data.get("SkillKeyWord")); 

        }
    }
}
