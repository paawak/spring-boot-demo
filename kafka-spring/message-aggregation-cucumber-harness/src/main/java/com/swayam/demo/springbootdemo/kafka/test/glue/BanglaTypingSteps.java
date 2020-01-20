package com.swayam.demo.springbootdemo.kafka.test.glue;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By.ById;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import cucumber.api.PreviousStepState;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class BanglaTypingSteps {

	private final WebDriver driver = new ChromeDriver();

	static {
		System.setProperty("webdriver.chrome.driver",
				"/kaaj/source/bhasha-js-test-harness/src/test/resources/driver/chromedriver_linux64_v2.29");
	}

	@Given("^I am on the Bhasha editor$")
	public String iAmOnBhashaEditor(PreviousStepState previousResponse) {
		System.out.println("BanglaTypingSteps.iAmOnBhashaEditor(), previousResponse: " + previousResponse.getResponseFromPreviousStep().orElse("Not Found")); 
		driver.get("file:///home/paawak/Downloads/bhasha-js-v1.0-goggle-closure/index.html");
		return "iAmOnBhashaEditor";
	}

	@When("^I type \"(.*)\"$")
	public String iType(String searchItem, PreviousStepState previousResponse) {
		System.out.println("*********BanglaTypingSteps.iType() previousResponse: " + previousResponse.getResponseFromPreviousStep().orElse("Not Found")); 
		WebElement indicTextEditor = driver.findElement(ById.id("indicTextEditor"));
		indicTextEditor.sendKeys(searchItem);
		return "iType";
	}
	
	@Then("^Bhasha editor should display \"(.*)\"$")
	public String theBhashaEditorShouldDisplay(String expectedString) {
		WebElement indicTextEditor = driver.findElement(ById.id("indicTextEditor"));
		assertEquals(expectedString, indicTextEditor.getAttribute("value"));
		return "theBhashaEditorShouldDisplay";
	}

	@After
	public String closeBrowser() {
		driver.close();
		return "closeBrowser";
	}

}
