package o2technologies_utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listener implements ITestListener {
	
	 public void onStart(ITestContext context) {
	   System.out.println("before all test excicution start  ");
	  }
	
	 public void onTestStart(ITestResult result) {
		  System.out.println("before each test ");
		  }
	 
	 public void onTestSuccess(ITestResult result) {
		    System.out.println("after test success ");
		  }
	 
	 public void onTestFailure(ITestResult result) {
		 System.out.println("after test failed ");
		  }
	 public void onTestSkipped(ITestResult result) {
		 System.out.println("after test skipped ");
		  }
	 public void onFinish(ITestContext context) {
		 System.out.println("after all testes exciution finished ");
		  }

}
