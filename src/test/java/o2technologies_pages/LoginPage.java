package o2technologies_pages;
import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import o2technologies_utils.ExtentReportManager;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "mat-input-0")
    private WebElement usernameInput;

    @FindBy(css = ".mdc-button__label")
    private WebElement signInButton;

    @FindBy(id = "i0118")
    private WebElement passwordInput;

    @FindBy(id = "idSIButton9")
    private WebElement idButton;

    @FindBy(id = "idBtn_Back")
    private WebElement noButton;

    @FindBy(css = "button.mat-mdc-snack-bar-action.mdc-snackbar__action")
    private WebElement closeButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver; 
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20)); 
        PageFactory.initElements(driver, this);
    }

    public void login(String username, String password) {
    
        driver.get("https://host.o2inc.net:8009/o2technologies/login");

        wait.until(ExpectedConditions.visibilityOf(usernameInput));
        usernameInput.sendKeys(username);

        signInButton.click();
        try {
        wait.until(ExpectedConditions.visibilityOf(passwordInput));
     
        passwordInput.sendKeys(password);
        idButton.click();
       }catch(Exception e) {
           System.out.println("password no needed, already logged-In");
       }
 
        try {
            wait.until(ExpectedConditions.elementToBeClickable(noButton));
            noButton.click();
        } catch (Exception e) {
            System.out.println("No button not found or clickable. Continuing without clicking.");
        }
        
        wait.until(ExpectedConditions.elementToBeClickable(closeButton));
        closeButton.click();
        
        System.out.println("Successfully login");
    }
}

