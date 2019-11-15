package com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.FileHelper;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.HDBClusterHomePage;
import pages.NikuHomePage;

public class ClarityPPMHealthCheck {
	Properties prop;
	// WebDriver driver;
	RemoteWebDriver driver;
	NikuHomePage nikuhomepage;

	/*
	 * @BeforeSuite public void BeforeSuite() throws IOException,
	 * InterruptedException{ Docker.StartDockerAndGrid(); }
	 * 
	 * @org.testng.annotations.AfterSuite public void AfterSuite() throws
	 * IOException, InterruptedException{ Docker.StopDockerInstance();
	 * 
	 * }
	 */

	@BeforeTest
	public void setup(ITestContext context) throws IOException, InterruptedException {

		String propertyfilepath = System.getProperty("user.dir");
		File file = new File(propertyfilepath + "/src/main/java/resources/env.properties");
		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		prop = new Properties();
		try {
			prop.load(fileInput);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/* *********Docker Execution ********/
		// URL remoteAddress = new URL("http://localhost:4444/wd/hub");
		// ChromeOptions capabilities = new ChromeOptions();
		// capabilities.addArguments("--headless");
		// driver = new RemoteWebDriver(remoteAddress, capabilities);
		// context.setAttribute("driver", driver);

		/* *****************************************************************/

		/*
		 * ***************for Headless
		 * execution;***********************************************************
		 * ******* ChromeOptions options= new ChromeOptions();
		 * options.addArguments("--no-sandbox");
		 * options.addArguments("--headless");
		 * options.addArguments("disable-infobars"); // disabling infobars
		 * options.addArguments("--disable-extensions"); // disabling extensions
		 * options.addArguments("--disable-gpu"); // applicable to windows os
		 * only options.addArguments("--disable-dev-shm-usage"); // overcome
		 * limited resource problems //
		 * options.setBinary("/opt/google/chrome/google-chrome"); driver = new
		 * ChromeDriver(options); context.setAttribute("driver", driver);
		 ***********************************************************************************************************/

		/*
		 * *************************Html Unit
		 * Driver********************************** driver = new
		 * HtmlUnitDriver(); context.setAttribute("driver", driver);
		 *********************************************************************************/

		/*
		 * ******************REGULAR CHROME
		 *******************************************************************/
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		context.setAttribute("driver", driver);
		/* *********************************************************************************************************/

	}

	@Test(priority = 1)
	public void ValidateNikuLogin() {
		nikuhomepage = new NikuHomePage(driver, prop);
		nikuhomepage.nikuLogin();
	}

	@Test(dependsOnMethods = { "ValidateNikuLogin" }, priority = 2)
	public void ValidateLearnLink() throws InterruptedException {
		nikuhomepage.clickLearnLink();
	}

	@Test(dependsOnMethods = { "ValidateNikuLogin" }, priority = 3)
	public void ValidateAboutDialog() throws InterruptedException {
		nikuhomepage.clickAboutDialog();
	}

	@Test(dependsOnMethods = { "ValidateAboutDialog" }, priority = 4)
	public void ValidateProductName() throws InterruptedException {
		nikuhomepage.checkProductName();
	}

	@Test(dependsOnMethods = { "ValidateAboutDialog" }, priority = 5)
	public void ValidateBuildVersion() throws InterruptedException {
		nikuhomepage.checkBuildVersion();
	}

	@Test(dependsOnMethods = { "ValidateAboutDialog" }, priority = 6)
	public void ValidateJasperSoftBuildVersion() throws InterruptedException {
		nikuhomepage.checkJaspersoftVersion();
	}

	@Test(dependsOnMethods = { "ValidateJasperSoftBuildVersion" }, priority = 7)
	public void ValidateLastRunTimeSlices() throws InterruptedException, ParseException {
		nikuhomepage.checkLastRunTimeSlices();
	}

	@Test(dependsOnMethods = { "ValidateNikuLogin" }, priority = 8)
	public void ValidateProcessEnginesColors() throws InterruptedException {
		nikuhomepage.checkProcessEngineColors();
	}

	@Test(dependsOnMethods = { "ValidateNikuLogin" }, priority = 9)
	public void ValidateContentAddinsStatus() throws InterruptedException {
		nikuhomepage.checkContentAddinStatus();
	}

	@Test(dependsOnMethods = { "ValidateNikuLogin" }, priority = 10)
	public void ValidateResources() throws InterruptedException {

		nikuhomepage.checkResources();
	}

	@Test(dependsOnMethods = { "ValidateNikuLogin" }, priority = 11)
	public void ValidateAdvanceReporting() throws InterruptedException {
		nikuhomepage.checkAdvanceReporting();
	}

	@Test(dependsOnMethods = { "ValidateAdvanceReporting" }, priority = 11)
	public void ValidateJasperSoftReport() throws InterruptedException {

		nikuhomepage.checkJasperSoftReport();
	}

	@Test(dependsOnMethods = { "ValidateNikuLogin" }, priority = 12)
	public void ValidateHDBCluster() throws InterruptedException {

		driver.navigate().to(prop.getProperty("hdburl"));
		// driver.manage().window().maximize();
		TimeUnit.SECONDS.sleep(10);
		HDBClusterHomePage hdbClusterHomePage = PageFactory.initElements(driver, HDBClusterHomePage.class);
		hdbClusterHomePage.getUserName().sendKeys(prop.getProperty("hdbuserid"));
		hdbClusterHomePage.getPassword().sendKeys(prop.getProperty("hdbpassword"));
		hdbClusterHomePage.getLoginbtn().click();
		TimeUnit.SECONDS.sleep(5);
		hdbClusterHomePage.getAdmintab().click();
		TimeUnit.SECONDS.sleep(3);
		hdbClusterHomePage.getDatawarehouseodataservice().click();
		TimeUnit.SECONDS.sleep(10);
		String v2EndPointUrl = hdbClusterHomePage.getDatawarehourservices().get(0).getText();
		String v4EndPointUrl = hdbClusterHomePage.getDatawarehourservices().get(1).getText();
		String oDataAuthenticator = hdbClusterHomePage.getDatawarehourservices().get(1).getText() + "|Admin";
		driver.navigate().to(v2EndPointUrl);
		driver.switchTo().alert().sendKeys(prop.getProperty("hdbuserid") + Keys.TAB + prop.getProperty("hdbpassword"));
		TimeUnit.SECONDS.sleep(3);
		driver.switchTo().alert().accept();
	}

	@AfterTest
	public void teardown() {
		if (driver != null) {
			nikuhomepage.LogOut();
			driver.close();
			driver.quit();
		}
	}
}
