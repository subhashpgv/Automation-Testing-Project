package o2technologies_pages;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class GlobalProfilePage {

	private WebDriver driver;
	private WebDriverWait wait;
	private JavascriptExecutor js;
	String UpdatedValue;
	String DeleteProfileName;
//	SoftAssert sa ;
     
	@FindBy(xpath = "//h4[normalize-space()='Global Profiles']")
	private WebElement Headding;

	@FindBy(xpath = "//input[@placeholder='Search Profile']")
	private WebElement Searchfield;

	@FindBy(xpath = "//tbody/tr/td[8]")
	private WebElement Status;

	@FindBy(xpath = "//span[normalize-space()='Settings']")
	private WebElement Settings;

	@FindBy(xpath = "//span[contains(text(),'Global Profiles')]")
	private WebElement GlobalProfiles;

	@FindBy(xpath = "//button//span[contains(text(),'Create Profile')]")
	private WebElement CreateProfile;

	@FindBy(xpath = "//input[@placeholder='Profile Name']")
	private WebElement ProfileName;

	@FindBy(xpath = "//input[@placeholder='Name']")
	private WebElement Name;

	@FindBy(xpath = "//input[@placeholder='Company Name']")
	private WebElement CompanyName;

	@FindBy(xpath = "//input[@placeholder='Company URL']")
	private WebElement CompanyURL;

	@FindBy(xpath = "//mat-label[contains(text(),'Mail Type')]")
	private WebElement Type;

	@FindBy(xpath = "//span[normalize-space()='Phone number']")
	private WebElement PhoneNumber;

	@FindBy(xpath = "//span[normalize-space()='Google']")
	private WebElement Google;

	@FindBy(xpath = "//input[@placeholder='Phone Number']")
	private WebElement PhoneNumberfield;

	@FindBy(xpath = "//input[@placeholder='Email']")
	private WebElement EmailID;

	@FindBy(xpath = "//input[@placeholder='Password']")
	private WebElement Passwordfeild;

	@FindBy(xpath = "//span[normalize-space()='Save']")
	private WebElement Save;

	@FindBy(xpath = "//div[@class='mat-mdc-snack-bar-label mdc-snackbar__label']")
	private WebElement sneakbar;

	@FindBy(xpath = "//span[normalize-space()='close']")
	private WebElement close;
	
	@FindBy(xpath = "//tbody/tr/td[8]")
	List<WebElement> statusElements;
	
	@FindBy(xpath = "//tbody//tr")
	List<WebElement> tableRows;

	@FindBy(xpath = "//div[contains(text(),'Updated successfully')]")
	private WebElement Updated_sneakbar;


	public GlobalProfilePage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		this.js = (JavascriptExecutor) driver;
		PageFactory.initElements(driver, this);
		
	}

	public void scrollToElement(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}

	/*----------- Verification of UI elements --------------*/

	public void verifyUIElements() {
		SoftAssert sa = new SoftAssert();
		
		if(wait.until(ExpectedConditions.elementToBeClickable(GlobalProfiles))==null) {
		wait.until(ExpectedConditions.elementToBeClickable(Settings)).click();
		}
		wait.until(ExpectedConditions.elementToBeClickable(GlobalProfiles)).click();
		// Heading verification
		if (wait.until(ExpectedConditions.visibilityOf(Headding)).isDisplayed()) {
			System.out.println("Global profile Headding is present ");
			sa.assertTrue(true);
		} else {
			sa.assertTrue(false);
		}

		// All columns heading verification
		int headerCount = driver.findElements(By.xpath("//thead//th")).size();

		List<String> HeaderTexts = new ArrayList<>();
		for (int i = 1; i <= headerCount; i++) {
			String HeaderText = driver.findElement(By.xpath("//thead//th[" + i + "]")).getText();
			HeaderTexts.add(HeaderText);
		}

		//// Define the expected list of header texts////
		List<String> expectedHeaders = Arrays.asList("Profile Name", "Type", "Email", "Mobile Number", "Company",
				"Created On", "Company Url", "Status", "Actions");

		boolean headersMatch = HeaderTexts.equals(expectedHeaders);

		if (headersMatch) {
			System.out.println("All headers are matching with expected Heading Names .");
			sa.assertTrue(true);
		} else {
			sa.assertTrue(false);
		}
		
		sa.assertAll();
	}
	
	/*-------------- verification of Search functionality---------------*/
	public void searchVerification() {
		SoftAssert sa = new SoftAssert();

		// search field verification

		if(wait.until(ExpectedConditions.elementToBeClickable(GlobalProfiles))==null) {
		wait.until(ExpectedConditions.elementToBeClickable(Settings)).click();
		}
		wait.until(ExpectedConditions.elementToBeClickable(GlobalProfiles)).click();
		wait.until(ExpectedConditions.elementToBeClickable(Searchfield)).click();

		String firstrecord = driver.findElement(By.xpath("//tbody/tr[1]/td[1]")).getText();

		wait.until(ExpectedConditions.elementToBeClickable(Searchfield)).sendKeys(firstrecord);

		int rowdata = driver.findElements(By.xpath("//tbody//tr")).size();

		List<String> values = new ArrayList<>();

		for (int i = 1; i <= rowdata; i++) {
			String value = driver.findElement(By.xpath("//tbody/tr[1]/td[" + i + "]")).getText();
			values.add(value);
		}

		for (String text : values) {
			if (text.contains(firstrecord)) {
				System.out.println("Search fuctionality is working fine , giving proper results");
				sa.assertTrue(true);
			} else {
				System.out.println("Search functionality is not working proprly");
				sa.assertTrue(false);
			}
			break;
		}
		sa.assertAll();

	}

	/*-------------- verification of Company URLS---------------*/

	public void company_URLS() {
		SoftAssert sa = new SoftAssert();

		if(wait.until(ExpectedConditions.elementToBeClickable(GlobalProfiles))==null) {
		wait.until(ExpectedConditions.elementToBeClickable(Settings)).click();
		}
		wait.until(ExpectedConditions.elementToBeClickable(GlobalProfiles)).click();
		
		List<WebElement> Links = driver.findElements(By.xpath("//tbody//tr//td[7]//a"));
		System.out.println("Total number of links: " + Links.size());

		String urlRegex = "^(http|https)://[^\\s/$.?#].[^\\s]*$";
		Pattern pattern = Pattern.compile(urlRegex);

		for (WebElement linkElement : Links) {
			String hrefValue = linkElement.getAttribute("href");

			Matcher matcher = pattern.matcher(hrefValue);
			boolean isValidUrl = matcher.matches();

			if (isValidUrl) {
				sa.assertTrue(true);
			} else {
				System.out.println("Invalid URL: " + hrefValue);
				sa.assertTrue(false);
			}
		}
		sa.assertAll();
	}

	// --------------verification of updatefuntionality-----------------//
	public void Update() {
		SoftAssert sa = new SoftAssert();
		if(wait.until(ExpectedConditions.elementToBeClickable(GlobalProfiles))==null) {
		wait.until(ExpectedConditions.elementToBeClickable(Settings)).click();
		}
		wait.until(ExpectedConditions.elementToBeClickable(GlobalProfiles)).click();
		WebElement profilename = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tbody/tr[1]/td[1]")));
		String currentvalue = profilename.getText();
		WebElement e = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tbody/tr[1]/td[9]//i-tabler[1]")));
		e.click();

		//System.out.println(currentvalue);
		ProfileName.sendKeys("A");

		driver.findElement(By.xpath("//span[normalize-space()='Update']")).click();
		
		
		Boolean isDisplyed = wait.until(ExpectedConditions.visibilityOf(Updated_sneakbar)).isDisplayed();

		if (isDisplyed) {
			System.out.println("Profile updated successfully from the UI");
			sa.assertTrue(true);
		} else {
			sa.assertTrue(false);
			
			
		}

		WebElement UpdatedValuetext = driver.findElement(By.xpath("//tbody/tr[1]/td[1]"));
		wait.until(ExpectedConditions.visibilityOf(UpdatedValuetext));
		this.UpdatedValue = UpdatedValuetext.getText();
		//System.out.println(UpdatedValue);

		if (UpdatedValue.equals(currentvalue + "a")) {
			System.out.println("Update functionality is verified from UI & working fine ");
			sa.assertTrue(true);
		} else {
			System.out.println("Update function is not working fine");
			sa.assertTrue(false);
		}
		wait.until(ExpectedConditions.elementToBeClickable(close)).click();
		
		sa.assertAll();
		}

	public String getUpdatedValue() {
		return UpdatedValue;
	}

	/* -------Verification of Delete funtion / it will delete first entery in list  ------ */
	public void Delete() {
		SoftAssert sa = new SoftAssert();
		if(wait.until(ExpectedConditions.elementToBeClickable(GlobalProfiles))==null) {
		wait.until(ExpectedConditions.elementToBeClickable(Settings)).click();
		}
		wait.until(ExpectedConditions.elementToBeClickable(GlobalProfiles)).click();
		this.DeleteProfileName = driver.findElement(By.xpath("//tbody/tr[1]/td[1]")).getText();
		
        WebElement deleteIcon=driver.findElement(By.xpath("//tbody/tr[1]/td[9]//i-tabler[2]"));
        
		wait.until(ExpectedConditions.visibilityOf(deleteIcon)).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(" //span[normalize-space()='Cancel']")))
				.click();
		wait.until(ExpectedConditions.visibilityOf(deleteIcon)).click();
		try {
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[normalize-space()='Delete']"))).click();
		}catch(Exception e) {
			e.printStackTrace();
		}
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(),'Deleted successfully')]")));
		Boolean isDisplyed = driver.findElement(By.xpath("//div[contains(text(),'Deleted successfully')]"))
				.isDisplayed();

		if (isDisplyed) {
			System.out.println("Profile Deleted successfully from UI ");
			sa.assertTrue(true);
		}else {
			System.out.println("profile not deleted from the UI");
			sa.assertTrue(false);
		}
		
		wait.until(ExpectedConditions.elementToBeClickable(close)).click();
		sa.assertAll();
	}

	// verification of items per page

	public String getDeleteProfileName() {
		return DeleteProfileName;
	}
	
	
	WebElement globalProfilesElement;
	public void pageNation() {
	    SoftAssert sa = new SoftAssert();
	    
 
	    // Check if GlobalProfiles element is clickable
	    boolean isGlobalProfilesClickable = false;
	    try {
	       WebElement globalProfilesElement = wait.until(ExpectedConditions.elementToBeClickable(GlobalProfiles));
	        isGlobalProfilesClickable = true;
	    } catch (Exception e) {
	        System.out.println("Global profile sub menu is not clickable. Error: " + e.getMessage());
	    }

	    // Click GlobalProfiles or Settings based on the above check
	   
	        if (!isGlobalProfilesClickable) {
	            WebElement settingsElement = wait.until(ExpectedConditions.elementToBeClickable(Settings));	           
	            settingsElement.click();
	            // Click on GlobalProfiles after navigating via Settings
	            WebElement globalProfilesElementAfterSettings = wait.until(ExpectedConditions.elementToBeClickable(GlobalProfiles));
	            globalProfilesElementAfterSettings.click();
	        }

	        // Scroll to the bottom of the page
	        js.executeScript("window.scrollBy(0, document.body.scrollHeight);");

	        // Find and verify paginator elements
	        WebElement disply = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='mat-mdc-paginator-container']")));
	        WebElement range_actions = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='mat-mdc-paginator-range-actions']")));

	        Boolean disply1 = disply.isDisplayed();
	        Boolean range_actions1 = range_actions.isDisplayed();

	        if (disply1 && range_actions1) {
	            System.out.println("Page paginator and navigation are present");
	            sa.assertTrue(true);
	        } else {
	            System.out.println("Paginator or navigation is not present");
	            sa.assertTrue(false);
	        }

	        // Select an item from the dropdown
	        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-select[@role='combobox']")));
	        dropdown.click();
	        WebElement items_selected = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-option[@role='option'][1]")));
	        items_selected.click();
	        String item_selected = items_selected.getText();
	        int item_selected_int = Integer.parseInt(item_selected);

	        // Verify item counts
	        WebElement Element1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='mat-mdc-paginator-range-label']")));
	        String s = Element1.getText();

	        int startIndex = s.indexOf("\u2013") + 1;
	        int endIndex = s.indexOf("of") - 1;
	        int firsttext = s.indexOf("\u2013") - 1;
	        int totalcountfinal = s.indexOf("of") + 2;

	        String extractedString = s.substring(totalcountfinal).trim();
	        int displayedItems = Integer.parseInt(extractedString);
	        System.out.println("Total items displayed on page: " + displayedItems);

	        String firsttext_string = s.substring(0, firsttext).trim();
	        int firsttext_int = Integer.parseInt(firsttext_string);

	        String secondtext_string = s.substring(startIndex, endIndex).trim();
	        int secondtext_int = Integer.parseInt(secondtext_string);

	        int rows = driver.findElements(By.xpath("//tbody//tr")).size();

	        int math = secondtext_int - firsttext_int;

	        // Verification of items per page
	        boolean condition_check = ((math == item_selected_int || math < item_selected_int)
	                && (rows == item_selected_int || rows < item_selected_int));
	        if (condition_check) {
	            System.out.println("Selected items per page is displaying proper results");
	            sa.assertTrue(true);
	        } else {
	            System.out.println("Items per page is not displaying selected items correctly");
	            sa.assertTrue(false);
	        }

	        // Assert all 
	        sa.assertAll();
	   
	}



	public void navigation() {

		if(wait.until(ExpectedConditions.visibilityOf(GlobalProfiles))==null) {
			wait.until(ExpectedConditions.elementToBeClickable(Settings)).click();
			}

	}

	public void fillFormWithPhoneNumber(String ProfileNameInput, String NameInput, String CompanyNameInput,
			String CompanyURLInput, String PhoneNumberInput, String condition) {
		
		SoftAssert sa = new SoftAssert();
	    navigation();
		wait.until(ExpectedConditions.elementToBeClickable(GlobalProfiles)).click();
		wait.until(ExpectedConditions.elementToBeClickable(CreateProfile)).click();
		wait.until(ExpectedConditions.elementToBeClickable(ProfileName)).sendKeys(ProfileNameInput);
		wait.until(ExpectedConditions.elementToBeClickable(Name)).sendKeys(NameInput);
		wait.until(ExpectedConditions.elementToBeClickable(CompanyName)).sendKeys(CompanyNameInput);
		wait.until(ExpectedConditions.elementToBeClickable(CompanyURL)).sendKeys(CompanyURLInput);
		wait.until(ExpectedConditions.elementToBeClickable(Type)).click();
		wait.until(ExpectedConditions.elementToBeClickable(PhoneNumber)).click();
		wait.until(ExpectedConditions.elementToBeClickable(PhoneNumberfield)).sendKeys(PhoneNumberInput);
		System.out.print(condition);
		try {
			wait.until(ExpectedConditions.elementToBeClickable(Save)).click();

			String Sneakbartext = wait.until(ExpectedConditions.visibilityOf(sneakbar)).getText();
			System.out.println("-  'SNEAK BAR MEESSAGE' : " + Sneakbartext);
			if(Sneakbartext=="Sneakbartext") {
				System.out.println("Profile added successfully with PhoneNumber ");
				sa.assertTrue(true);
				}
		} catch (Exception e) {
			e.printStackTrace();
			sa.assertTrue(false);
		}
		wait.until(ExpectedConditions.elementToBeClickable(close)).click();
		sa.assertAll();
	}

	public void fillFormWithGmail(String ProfileNameInput, String NameInput, String CompanyNameInput,
			String CompanyURLInput, String EmailIDInput, String PasswordfeildInput, String condition) {
		
		SoftAssert sa = new SoftAssert();
		wait.until(ExpectedConditions.elementToBeClickable(Settings)).click();
		wait.until(ExpectedConditions.elementToBeClickable(GlobalProfiles)).click();
		wait.until(ExpectedConditions.elementToBeClickable(CreateProfile)).click();
		wait.until(ExpectedConditions.elementToBeClickable(ProfileName)).sendKeys(ProfileNameInput);
		wait.until(ExpectedConditions.elementToBeClickable(Name)).sendKeys(NameInput);
		wait.until(ExpectedConditions.elementToBeClickable(CompanyName)).sendKeys(CompanyNameInput);
		wait.until(ExpectedConditions.elementToBeClickable(CompanyURL)).sendKeys(CompanyURLInput);
		wait.until(ExpectedConditions.elementToBeClickable(Type)).click();
		wait.until(ExpectedConditions.elementToBeClickable(Google)).click();
		wait.until(ExpectedConditions.elementToBeClickable(EmailID)).sendKeys(EmailIDInput);
		wait.until(ExpectedConditions.elementToBeClickable(Passwordfeild)).sendKeys(PasswordfeildInput);
		System.out.print(condition);
		try {
			wait.until(ExpectedConditions.elementToBeClickable(Save)).click();
			String Sneakbartext = wait.until(ExpectedConditions.visibilityOf(sneakbar)).getText();
			System.out.println("-  'SNEAK BAR MEESSAGE' : " + Sneakbartext);
			if("Saved ssuccessfully".equals(Sneakbartext)) {
			System.out.println("Profile added successfully with Email ");
			sa.assertTrue(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			sa.assertTrue(false);
		}
		wait.until(ExpectedConditions.elementToBeClickable(close)).click();
		sa.assertAll();
	}
	
	// ----------verification of data created /status-------------------//
	public void dateCreated(String ProfileNameInput, String NameInput, String CompanyNameInput,
			String CompanyURLInput, String EmailIDInput, String PasswordfeildInput, String condition) {
		
		SoftAssert sa = new SoftAssert();
		
		if(wait.until(ExpectedConditions.elementToBeClickable(GlobalProfiles))==null) {
			wait.until(ExpectedConditions.elementToBeClickable(Settings)).click();
			}
			wait.until(ExpectedConditions.elementToBeClickable(GlobalProfiles)).click();

		try {
			wait.until(ExpectedConditions.elementToBeClickable(CreateProfile)).click();
			sa.assertTrue(true);

			System.out.println("Click action on \"CreateProfile\" button is successful");
		} catch (Exception e) {
			System.out.println("Error clicking on 'CreateProfile' button: " + e.getMessage());
			sa.assertTrue(false);
		}

		Boolean disply2 = driver.findElement(By.xpath("//h4[normalize-space()='Create Profile']")).isDisplayed();
		if (disply2) {
			System.out.println("\"Create profile\" heading is present");
			sa.assertTrue(true);
		}

		wait.until(ExpectedConditions.elementToBeClickable(ProfileName)).sendKeys(ProfileNameInput);
		wait.until(ExpectedConditions.elementToBeClickable(Name)).sendKeys(NameInput);
		wait.until(ExpectedConditions.elementToBeClickable(CompanyName)).sendKeys(CompanyNameInput);
		wait.until(ExpectedConditions.elementToBeClickable(CompanyURL)).sendKeys(CompanyURLInput);
		wait.until(ExpectedConditions.elementToBeClickable(Type)).click();
		wait.until(ExpectedConditions.elementToBeClickable(Google)).click();
		wait.until(ExpectedConditions.elementToBeClickable(EmailID)).sendKeys(EmailIDInput);

		wait.until(ExpectedConditions.elementToBeClickable(Passwordfeild)).sendKeys(PasswordfeildInput);
		System.out.print(condition);
		try {
			wait.until(ExpectedConditions.elementToBeClickable(Save)).click();
			String Sneakbartext = wait.until(ExpectedConditions.visibilityOf(sneakbar)).getText();
			System.out.println("-  'SNEAK BAR MEESSAGE' : " + Sneakbartext);
		} catch (Exception e) {
			e.printStackTrace();
		}
		wait.until(ExpectedConditions.elementToBeClickable(close)).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(GlobalProfiles)).click();
		
		// verification of data created with +/- 5 min 
				
		WebElement date = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//tbody/tr[1]/td[6]")));
				String dateString= date.getText();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
				LocalDateTime webpageDateTime = LocalDateTime.parse(dateString, formatter);

				// Get current date and time 
				LocalDateTime currentDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

				 LocalDateTime currentDateTimeMinus1 = currentDateTime.minusMinutes(1);
			     LocalDateTime currentDateTimePlus1 = currentDateTime.plusMinutes(1);
				// Compare dates
			     if (webpageDateTime.isEqual(currentDateTime) || // exact match
			             (webpageDateTime.isAfter(currentDateTimeMinus1) && webpageDateTime.isBefore(currentDateTimePlus1))) {
					System.out.println("Date Created present and adding properly on creating profile ");
					sa.assertTrue(true);
				} 
			     
			     
	  // verification of status
					int rowCount  = tableRows.size();
					//System.out.println(rowCount );
					List<String> data = new ArrayList<>();
					for (WebElement element : statusElements) {
					    String t = element.getText();
					    data.add(t);
					}

					for (String text : data) {
						if (text.contains("Active")) {
							System.out.println("All Newly added profiles are \"Active\" status");
							sa.assertTrue(true);
						} else {
							System.out.println("error in Status ");
							sa.assertTrue(false);
						}
						break;
					}
					
					sa.assertAll();
	}
	
	public void navigationFix() {
	  boolean isGlobalProfilesClickable = false;

	    try {
	        WebElement globalProfilesElement = wait.until(ExpectedConditions.elementToBeClickable(GlobalProfiles));
	        isGlobalProfilesClickable = true;
	    } catch (Exception e) {
	        System.out.println("Global profile sub menu is not clickable");
	    }

	    if (isGlobalProfilesClickable) {
	        wait.until(ExpectedConditions.elementToBeClickable(GlobalProfiles)).click();
	    } else {
	        WebElement settingsElement = wait.until(ExpectedConditions.elementToBeClickable(Settings));
	        settingsElement.click();
	        wait.until(ExpectedConditions.elementToBeClickable(GlobalProfiles)).click();
	    }
	}
}