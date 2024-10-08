package o2technologies_pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class SearchNewCandidatesPage {
    private static final boolean PerformSearchNewCandidate = false;
	private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
   
   
    
    @FindBy(xpath = "//span[normalize-space()='Dice']")
    private WebElement DiceMenu;

    @FindBy(xpath = "//span[contains(text(),'Search New Candidates')]")
    private WebElement SearchNewCandidateOption;

    @FindBy(xpath = "//mat-label[contains(.,'Key word search')]")
    private WebElement KeyWordSearch;
   
    @FindBy(xpath = "//input[@placeholder='Key Word Search or Boolean Search']")
    private WebElement KeyWordSearchInput;
    
    @FindBy(xpath = "//i-tabler[@name='map-pin']//*[name()='svg']")
    private WebElement Locationclick;
    
    @FindBy(xpath = "//input[@formcontrolname='locationFilter']")
    private WebElement LocationInput;
    
    @FindBy(id = "distance")
    private WebElement distanceclick;
    
    @FindBy(id = "distance")
    private WebElement distanceinput;
    
    @FindBy(xpath = "//mat-label[contains(.,'UOM')]")
    private WebElement UOM;
    
    @FindBy(xpath = "//mat-option[@value='km']")
    private WebElement Km;
    
    @FindBy(xpath = "//div[@formgroupname='initialLocation']//i-tabler[@name='square-plus']")
    private WebElement plusIcon;
    
    @FindBy(xpath = "//label[normalize-space()='Willing To Relocate']")
    private WebElement willingToRelocate;
    
    @FindBy(xpath = "//span[normalize-space()='Save Filters']")
    private WebElement SaveFiltersButton;
    
    
    @FindBy(xpath = "//input[@name='myInput']")
    private WebElement FilterName;
    
    @FindBy(xpath = "(//span[@class='mdc-button__label'][normalize-space()='Save Filters'])[2]")
    private WebElement savefilterButtonIn;
    
    @FindBy(xpath = "//span[contains(text(),'Skills Keyword')]")
    private WebElement SkillKeyWordFilter;
    
    @FindBy(xpath = "//mat-accordion[@class='mat-accordion mat-accordion-multi ng-star-inserted']//mat-label[text()='Skills Keyword']")
    private WebElement SkillKeyWordFilterClick;
    
    @FindBy(id = "mat-input-11")
    private WebElement SkillKeyWordInput;

    @FindBy(xpath = "//button[@type='button']//span[contains(text(),'Apply Filter')]")
    private WebElement Applyfilter;

    

    public SearchNewCandidatesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }
    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void fillForm(String searchCandidateInput, String location, String distance, String filterName,String SkillKeyWord ) {
    	
   
    	
        wait.until(ExpectedConditions.elementToBeClickable(KeyWordSearch)).click();
        wait.until(ExpectedConditions.elementToBeClickable(KeyWordSearchInput)).sendKeys(searchCandidateInput);
        wait.until(ExpectedConditions.elementToBeClickable(Locationclick)).click();
        wait.until(ExpectedConditions.elementToBeClickable(LocationInput)).sendKeys(location);
        wait.until(ExpectedConditions.elementToBeClickable(distanceinput)).sendKeys(distance);;
        wait.until(ExpectedConditions.elementToBeClickable(UOM)).click();
        wait.until(ExpectedConditions.elementToBeClickable(Km)).click();
        wait.until(ExpectedConditions.elementToBeClickable(plusIcon)).click();
        wait.until(ExpectedConditions.elementToBeClickable(willingToRelocate)).click();
        wait.until(ExpectedConditions.elementToBeClickable(SaveFiltersButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(FilterName)).sendKeys(filterName);;
        wait.until(ExpectedConditions.elementToBeClickable(savefilterButtonIn)).click();
      
        scrollToElement(SkillKeyWordFilter);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", SkillKeyWordFilter);
        
        wait.until(ExpectedConditions.visibilityOf(SkillKeyWordFilterClick));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", SkillKeyWordFilterClick);
        
        wait.until(ExpectedConditions.elementToBeClickable(SkillKeyWordInput)).sendKeys(SkillKeyWord);
        
        scrollToElement(Applyfilter);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", Applyfilter);
       
    }
	public void navigateToSearchNewCandidate() {
		  wait.until(ExpectedConditions.elementToBeClickable(DiceMenu)).click();
	        wait.until(ExpectedConditions.elementToBeClickable(SearchNewCandidateOption)).click();
		
	}
	
}
