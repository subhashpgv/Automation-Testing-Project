package o2technologies_utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;



import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager implements ITestListener {
    
    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public ExtentTest test;
    private WebDriver driver; 


    public void onStart(ITestContext context) {
        String timestamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
        String reportPath = "C:/Users/svegi/eclipse-workspace/O2technologiesTestCases/ExtendReports/myreport_" + timestamp + ".html";

        sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setDocumentTitle("Automation Report");
        sparkReporter.config().setReportName("Global Profile Test");
        sparkReporter.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        extent.setSystemInfo("Computer Name", System.getProperty("user.name"));
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Tester Name", "SubhashPgv");
    }

    public void onTestSuccess(ITestResult result) {
    
        String testClassName = result.getTestClass().getName();
        String testCaseID = result.getMethod().getDescription(); // Assuming test case ID is in description
        String testName = result.getName();

        test = extent.createTest(testClassName + " : " + testCaseID + " : " + testName);
        test.log(Status.PASS, "Test case passed is: " + testName);
    }

    public void onTestFailure(ITestResult result) {
        String testClassName = result.getTestClass().getName();
        String testCaseID = result.getMethod().getDescription(); // Assuming test case ID is in description
        String testName = result.getName();

        test = extent.createTest(testClassName + " : " + testCaseID + " : " + testName);
        test.log(Status.FAIL, "Test case failed is: " + testName);
        test.log(Status.FAIL, "Test case failed cause is: " + result.getThrowable());
        
        initializeDriver(result);
        
        if (driver != null) {
            try {
                String screenshotPath = captureScreenshot(testName);
                test.addScreenCaptureFromPath(screenshotPath);
            } catch (IOException e) {
                test.log(Status.FAIL, "Failed to capture screenshot: " + e.getMessage());
            }
        } else {
            test.log(Status.FAIL, "WebDriver is not initialized, screenshot not captured.");
        }
    }

    public void onTestSkipped(ITestResult result) {
        String testClassName = result.getTestClass().getName();
        String testCaseID = result.getMethod().getDescription(); // Assuming test case ID is in description
        String testName = result.getName();

        test = extent.createTest(testClassName + " : " + testCaseID + " : " + testName);
        test.log(Status.SKIP, "Test case skipped is: " + testName);
    }

    public void onFinish(ITestContext context) {
        extent.flush();
    }
 
    public String captureScreenshot(String screenshotName) throws IOException {
        String timestamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
        String filePath = "C:/Users/svegi/eclipse-workspace/O2technologiesTestCases/ExtendReports/screenshots/" + screenshotName + "_" + timestamp + ".png";
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileHandler.copy(screenshot, new File(filePath));
        return filePath;
    }

    private void initializeDriver(ITestResult result) {
        try {
            driver = (WebDriver) result.getTestClass().getRealClass().getDeclaredField("driver").get(result.getInstance());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
