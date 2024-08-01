package com.framework.tests;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.common.utils.FileReader;
import com.lexus.pages.ModelComparison;

/**
 * Main Test Script with function to test the feature of the application in
 * TEST-NG format
 *
 * @author saranya
 *
 */

public class ModelTest {

	private static FileReader fileRead = FileReader.getInstance();
	FileReader environmentPropertiesReader;
	String webSite;
	public WebDriver driver;

	/**
	 * Function to initialise test script variable
	 *
	 * @param context
	 */
	@BeforeSuite
	public void init(ITestContext context) {

		webSite = context.getCurrentXmlTest().getParameter("webSite");
	}

	/**
	 * WraupAction to be done after Test suite executed
	 */
	@AfterSuite
	public void wrapUp() {

		try {
			if (driver != null) {

				// driver.quit();
				System.out.println("Logged out successfully from the application");
			}
		} catch (Exception e) {

			System.out.println(e.toString());
		}

		// driver.close();
		System.out.println();
	}

	/**
	 * testScript to verify bookingtestDrive details entered properly and its
	 * functional verification
	 */
	@Test(priority = 1)
	public void enterTestDriveBookingValuest() {
		try {
			HashMap<String, String> input = new HashMap<>();
			driver =new EdgeDriver();
			driver.navigate().to(webSite);
			driver.manage().window().maximize();
			// To accept the cookies
			Thread.sleep(100);

			ModelComparison pages = new ModelComparison(driver);
			input.put("FirstName", fileRead.getProperty("FirstName"));
			input.put("LastName", fileRead.getProperty("LastName"));
			input.put("EmailAddress", fileRead.getProperty("EmailAddress"));
			input.put("PhoneNumber", fileRead.getProperty("PhoneNumber"));
			input.put("PreferredTime", fileRead.getProperty("PreferredTime"));
			input.put("NumberofPax", fileRead.getProperty("NumberofPax"));
			input.put("Date", fileRead.getProperty("Date"));
			input.put("TestdriveOption", fileRead.getProperty("TestdriveOption"));
			input.put("Model", fileRead.getProperty("Model"));
			// Enter the details in the page
			pages.enter_BookTestDriverValues(input);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}