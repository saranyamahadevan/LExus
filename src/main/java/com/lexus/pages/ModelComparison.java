package com.lexus.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;





/**Model comparison page object which has page elements and its operation
 * @author saranya.mahadevan
 *
 */
public class ModelComparison {

    int maxElementWait=30;
	String elementIdentifier;
	String elementObject;
	private boolean isPageLoaded;

	private String appURL;
	HashMap<String, String> getPageObjects;
	public static List<Object> pageFactoryKey = new ArrayList<>();
	public static List<String> pageFactoryValue = new ArrayList<>();


	private WebDriver driver;

	@FindBy(how = How.XPATH, using = "//input[@id='input_first_name']")
	private WebElement inpfirtName;



	@FindBy(how = How.XPATH, using = "//input[@id='input_last_name']")
	private WebElement inpLastName;


	@FindBy(how = How.XPATH, using = "//input[@id='input_email_address']")
	private WebElement inpEmail;


	@FindBy(how = How.XPATH, using = "//input[@id='input_phone_number']")
	private WebElement inpPhoenNo;

	@FindBy(how = How.XPATH, using = "//input[@id='checkbox_privacy_policy']")
	private WebElement chkPrivacyPolicy;


	@FindBy(how = How.XPATH, using = "//input[@id='checkbox_marketing']")
	private WebElement chkMarketing;



	@FindBy(how = How.XPATH, using = "//a[@id='consent_prompt_submit']")
	private WebElement acceptCookies;



	@FindBy(how = How.XPATH, using = "//select[@name='select_preferred_sales_consultant']")
	private WebElement selSalesConsultant;


	@FindBy(how = How.XPATH, using = "//select[@name='select_pax']")
	private WebElement selPax;


	@FindBy(how = How.XPATH, using = "//select[@name='select_preferred_time']")
	private WebElement selTime;

	@FindBy(how = How.XPATH, using = "//select[@name='select_preferred_models']")
	private WebElement selModel;



	@FindBy(how = How.XPATH, using = "//input[@id='datepicker_preferred_date']")
	private WebElement inpDatePicker;


	@FindBy(how = How.XPATH, using = "//button/span[text()='SUBMIT']")
	private WebElement btnSubmit;

	/**
	 * Construcor
	 */
	public ModelComparison() {
		ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver, 10);
		PageFactory.initElements(finder, this);
	}

	public ModelComparison(WebDriver driver, String url) {
		appURL = url;
		this.driver = driver;
		ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver, maxElementWait);
		PageFactory.initElements(finder, this);
	}

	public ModelComparison(WebDriver driver) {
		this.driver = driver;
		ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver, maxElementWait);
		PageFactory.initElements(finder, this);



	}


	/**Enter TestDrive Booking detials
	 * @param input
	 */
	public void enter_BookTestDriverValues(HashMap<String,String> input)
	{
		acceptCookies.click();
		inpfirtName.sendKeys(input.get("FirstName"));
		inpLastName.sendKeys(input.get("LastName"));
		inpEmail.sendKeys(input.get("EmailAddress"));
		inpPhoenNo.sendKeys(input.get("PhoneNumber"));
		chkPrivacyPolicy.click();
		chkMarketing.click();

		 ((JavascriptExecutor) driver).executeScript(
	                "arguments[0].removeAttribute('readonly','readonly')",inpDatePicker);
		 inpDatePicker.sendKeys(input.get("Date"));

		selectFromDropDownText("select_pax",input.get("NumberofPax"));
		selectFromDropDownText("select_preferred_time",input.get("PreferredTime"));
		selectFromDropDownText("select_test_drive_option",input.get("TestdriveOption"));
		verifyPreferedModel(input.get("Model"));
	    selectCheckBox();
		verifySubmitEnabled();
	}


	/**Verify the model value provided is selected by default
	 * @param valueOpt
	 */
	public void verifyPreferedModel(String valueOpt)
	{
		 Select selectBox = new Select(selModel);
		 WebElement option = selectBox.getOptions().get(0);
		 String value =option.getAttribute("value");
		 Assert.assertEquals(value, valueOpt,"value selected is UX300e by default");
		 if(value.equals(valueOpt))
		 {
			 System.out.println("PASS" +" value selected is UX300e by default");
		 }
		 else
		 {
			 System.out.println("FAIL" +" value selected is NOT UX300e by default");
		 }
	}

	/**
	 * check submit is enabled or disabled
	 */
	public void verifySubmitEnabled()
	{

		String classes = btnSubmit.getAttribute("class");
		boolean isDisabled = classes.contains("cta__dialog_form is_disabled");

		Assert.assertEquals(isDisabled, false,"PASS Submit button Enabled after entering valid inputs");
		if (!isDisabled)
		{

			 System.out.println("PASS" +" Submit button Enabled after entering valid inputs");
		}
		else
		 {
			 System.out.println("FAIL" +" Submit button NOT Enabled after entering valid inputs");
		 }
	}


	/**
	 * To select all the checkboxed in the page
	 */
	public void selectCheckBox()
	{

		List<WebElement> eleList= driver.findElements(By.xpath("//input[@type='checkbox']"));
		for(WebElement ele:eleList)
		{
			if(! ele.isSelected())
			{
				ele.click();
			}
		}

	}

	/**Select values from the dropdown elemet with the given option
	 * @param elementID
	 * @param optionVal
	 */
	public void selectFromDropDownText(String elementID,String optionVal)
	{
		try {
			String selDropDownXpath="//select[@id='"+elementID+"']/../div[@class='choices__list choices__list--single']";
			driver.findElement(By.xpath(selDropDownXpath)).click();

			String valueXpath="//div[contains(@id,'"+elementID+"') and text()='"+optionVal+"']";
			driver.findElement(By.xpath(valueXpath)).click();


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}



}
