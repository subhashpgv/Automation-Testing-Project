package TestSuitRunner;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// ---- Use this test runer for itarate the test suite for multiple times-----//

public class TestRunner {
	
    public static void main(String[] args) {
        TestNG testNG = new TestNG();
        List<XmlSuite> suites = new ArrayList<>();
        XmlSuite suite = new XmlSuite();
        suite.setName("MyTestSuite");

        suite.setSuiteFiles(Collections.singletonList("O2techTestSuite.xml"));

        suites.add(suite);
        testNG.setXmlSuites(suites);

        // It will Run the suite for 30 times.
        for (int i = 1; i <= 30; i++) {
            System.out.println("Running suite iteration: " + i);
            testNG.run();
        }
    }
}
