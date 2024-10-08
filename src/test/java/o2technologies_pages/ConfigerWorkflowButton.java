package o2technologies_pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ConfigerWorkflowButton {
	private WebDriverWait wait;
	private WebDriver driver;
	private JavascriptExecutor js;

	@FindBy(xpath = "//span[normalize-space()='Dice']")
	private WebElement DiceMenu;

	@FindBy(xpath = "//span[contains(text(),'Search New Candidates')]")
	private WebElement SearchNewCandidateOption;

	@FindBy(xpath = " //span[normalize-space()='Configure Workflows']")
	private WebElement ConfigureWorkflows;

	@FindBy(xpath = "//input[@placeholder='Provide Work Flow Name']")
	private WebElement NameInput;

	@FindBy(xpath = "//input[@formcontrolname='totalCount']")
	private WebElement totalCount;

	@FindBy(xpath = "(//div[@class='mdc-radio'])[2]")
	private WebElement OneTimeRadioButton;

	@FindBy(xpath = "//label[normalize-space()='University List']//parent::div//input")
	private WebElement UniversityListEnable;

	@FindBy(xpath = "//b[normalize-space()='Email']//parent::div//following-sibling::mat-form-field//child::mat-label[text()='Select Profile Name']")
	private WebElement EmailSelectProfileName;

	@FindBy(xpath = "//b[normalize-space()='Email']//parent::div//following-sibling::mat-form-field//child::mat-label[text()='Select Templates']")
	private WebElement EmailSelectTemplates;

	@FindBy(xpath = "//div[@class='col-md-1 ng-star-inserted']//i-tabler[@name='square-plus']//*[name()='svg']")
	private WebElement EmailPlusIcon;

	// b[normalize-space()='SMS']//parent::div//following-sibling::mat-form-field//child::mat-label[text()='Select
	// Profile Name']
	@FindBy(xpath = "//b[normalize-space()='SMS']//parent::div//following-sibling::mat-form-field//child::mat-label[text()='Select Profile Name']")
	private WebElement SMSSelectProfileName;

	@FindBy(xpath = "//b[normalize-space()='SMS']//parent::div//following-sibling::mat-form-field//child::mat-label[text()='Select Templates']")
	private WebElement SMSSelectTemplates;

	@FindBy(xpath = "//div[@class='col-md-1 f-s-12 ng-star-inserted']//i-tabler[@name='square-plus']//*[name()='svg']")
	private WebElement SMSPlusIcon;

	@FindBy(xpath = "//div[@role='listbox']")
	public WebElement DropDownList;

	@FindBy(xpath = "//div[@role='listbox']//mat-option")
	private List<WebElement> DropDownOptions;

	@FindBy(xpath = "//span[normalize-space()='Save']")
	public WebElement save;



	public ConfigerWorkflowButton(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		this.js = (JavascriptExecutor) driver;
		PageFactory.initElements(driver, this);
	}

	public boolean PerformSearchNewCandidate(String NameInputValue, String totalCountValue) {

		try {
			wait.until(ExpectedConditions.elementToBeClickable(DiceMenu)).click();
			wait.until(ExpectedConditions.elementToBeClickable(SearchNewCandidateOption)).click();

			wait.until(ExpectedConditions.elementToBeClickable(ConfigureWorkflows));
			ConfigureWorkflows.click();

			wait.until(ExpectedConditions.elementToBeClickable(NameInput));
			NameInput.sendKeys(NameInputValue);

			wait.until(ExpectedConditions.elementToBeClickable(totalCount));
			totalCount.sendKeys(totalCountValue);

			wait.until(ExpectedConditions.elementToBeClickable(OneTimeRadioButton));
			OneTimeRadioButton.click();

			wait.until(ExpectedConditions.elementToBeClickable(EmailSelectProfileName));
			EmailSelectProfileName.click();

			wait.until(ExpectedConditions.visibilityOf(DropDownList));
			DropDownOptions.get(0).click();

			wait.until(ExpectedConditions.elementToBeClickable(EmailSelectTemplates));
			EmailSelectTemplates.click();

			wait.until(ExpectedConditions.visibilityOf(DropDownList));
			DropDownOptions.get(1).click();

			wait.until(ExpectedConditions.visibilityOf(EmailPlusIcon));
			EmailPlusIcon.click();

			wait.until(ExpectedConditions.elementToBeClickable(SMSSelectProfileName));
			SMSSelectProfileName.click();

			wait.until(ExpectedConditions.visibilityOf(DropDownList));
			DropDownOptions.get(0).click();

			wait.until(ExpectedConditions.elementToBeClickable(SMSSelectTemplates));
			SMSSelectTemplates.click();

			wait.until(ExpectedConditions.visibilityOf(DropDownList));
			DropDownOptions.stream().findFirst().ifPresent(WebElement::click);

			wait.until(ExpectedConditions.visibilityOf(SMSPlusIcon));
			SMSPlusIcon.click();

			wait.until(ExpectedConditions.elementToBeClickable(save)).click();
			System.out.println("Workflow Configerd successfully ");
			return true; // Indicate success

		} catch (Exception e) {
			e.printStackTrace();
			return false; // Indicate failure
		}

	}

	

}
