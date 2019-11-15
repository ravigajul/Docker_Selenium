package pages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class NikuHomePage {

	public WebDriver driver;
	public WebDriverWait wait;
	public Properties prop;

	@FindBy(how = How.ID, using = "ppm_login_username")
	private WebElement userName;

	@FindBy(how = How.ID, using = "ppm_header_logout")
	private WebElement logout;
	
	@FindBy(how = How.ID, using = "ppm_login_password")
	private WebElement password;

	@FindBy(how = How.ID, using = "jasperframe")
	private WebElement jasperframe;

	@FindBy(how = How.ID, using = "ppm_app")
	private WebElement ppmapp;

	@FindBy(how = How.ID, using = "ppm_login_button")
	private WebElement loginbtn;

	@FindBy(how = How.ID, using = "ppm_header_about")
	private WebElement aboutlink;

	@FindBy(how = How.ID, using = "ppm_page_contents")
	private WebElement aboutdialog;

	@FindBy(how = How.ID, using = "ppm_header_help")
	private WebElement helplink;

	@FindBy(how = How.ID, using = "ppm_header_learn")
	private WebElement learnlink;

	@FindBy(how = How.ID, using = "ppm_nav_app")
	private WebElement hometab;

	@FindBy(how = How.ID, using = "ppm_refresh")
	private WebElement ppmrefreshbtn;

	@FindBy(how = How.CSS, using = "li#main_library.leaf")
	private WebElement librarytab;

	@FindBy(how = How.ID, using = "ppm_nav_admin")
	private WebElement admintab;

	@FindBy(how = How.ID, using = "apply")
	private WebElement applybtn;

	@FindBy(linkText = "Time Slices")
	private WebElement timeslices;

	@FindBy(linkText = "Process Engines")
	private WebElement processengines;

	@FindBy(linkText = "Content Add-Ins")
	private WebElement contentaddins;

	@FindBy(linkText = "Resources")
	private WebElement resources;

	@FindBy(linkText = "Advanced Reporting")
	private WebElement advancedreporting;

	@FindBy(linkText = "Resources")
	private WebElement rosources;

	@FindAll(@FindBy(how = How.CSS, using = "span.ppm_read_only_value"))
	private List<WebElement> aboutinformation; // 0 ppm 1 build version 7
												// jaspersoft version

	@FindBy(how = How.CSS, using = "button.ppm_dialog_close")
	private WebElement aboutclosebtn;

	@FindBy(how = How.CSS, using = "ul#resultsListHeader")
	private WebElement resultsListHeader;

	@FindBy(how = How.CSS, using = "tbody.ppm_grid_content")
	private WebElement tableBody;

	@FindBy(how = How.CSS, using = "div.ppm_gridcontent")
	private WebElement contentTableBody;

	@FindBy(how = How.CSS, using = "div#results")
	private WebElement libraryResults;

	@FindAll(@FindBy(how = How.CSS, using = "div.column.two p"))
	private List<WebElement> descriptions;

	/*
	 * @FindAll(@FindBy(how = How.CSS, using = "p#null.resourceDescription"))
	 * private List<WebElement> descriptions;
	 */
	@FindAll(@FindBy(how = How.CSS, using = "tbody.ppm_grid_content tr"))
	private List<WebElement> tablerows;

	@FindAll(@FindBy(how = How.CSS, using = "p.resourceName a"))
	private List<WebElement> libraryNames;

	@FindAll(@FindBy(how = How.CSS, using = "div.jr-jViewPortChunk ul li div a"))
	private List<WebElement> libraryOptions;

	@FindAll(@FindBy(how = How.CSS, using = "#reportContainer table.jrPage tbody tr"))
	private List<WebElement> datawarehouseschemarows;

	public NikuHomePage(WebDriver driver, Properties prop) {
		this.driver = driver;
		this.prop = prop;
		this.wait = new WebDriverWait(driver, 30);
		PageFactory.initElements(driver, this);
	}

	public void nikuLogin() {
		driver.get(prop.getProperty("nikuurl"));
		driver.manage().window().maximize();
		this.wait.until(ExpectedConditions.visibilityOf(this.userName));
		System.out.println(driver.getTitle());
		this.userName.sendKeys(prop.getProperty("userid"));
		this.password.sendKeys(prop.getProperty("password"));
		this.loginbtn.click();
		this.wait.until(ExpectedConditions.visibilityOf(this.ppmapp));
		Assert.assertTrue(this.ppmapp.isDisplayed(), "Login failed");
	}

	public void clickLearnLink() throws InterruptedException {

		this.wait.until(ExpectedConditions.visibilityOf(this.learnlink));
		this.learnlink.click();
		TimeUnit.SECONDS.sleep(10);
		String windHandleBefore = driver.getWindowHandle();
		for (String windowhandle : driver.getWindowHandles()) {
			driver.switchTo().window(windowhandle);
		}

		Assert.assertTrue(driver.getTitle().contains("trainer"), "title displayed is " + driver.getTitle());
		driver.close();
		driver.switchTo().window(windHandleBefore);
	}

	public void clickAboutDialog() {
		this.aboutlink.click();
		this.wait.until(ExpectedConditions.visibilityOf(this.aboutdialog));
		Assert.assertTrue(this.aboutdialog.isDisplayed(), "The about dialog box is not displayed");

	}

	public void checkProductName() throws InterruptedException {
		TimeUnit.SECONDS.sleep(5);
		Assert.assertTrue(this.aboutinformation.get(0).getText().equalsIgnoreCase(prop.getProperty("productname")),
				"Product name displayed is " + prop.getProperty("productname"));
	}

	public void checkBuildVersion() {
		Assert.assertTrue(this.aboutinformation.get(1).getText().equalsIgnoreCase(prop.getProperty("build_version")),
				"build_version displayed is " + prop.getProperty("build_version"));
	}

	public void checkJaspersoftVersion() {
		Assert.assertTrue(
				this.aboutinformation.get(7).getText().equalsIgnoreCase(prop.getProperty("jaspersoft_version")),
				"jaspersoft_version displayed is " + prop.getProperty("jaspersoft_version"));
	}

	public void checkLastRunTimeSlices() throws ParseException {
		String sLastRun;
		Date dLastRun;
		this.aboutclosebtn.click();
		this.wait.until(ExpectedConditions.visibilityOf(this.admintab));
		this.admintab.click();
		this.wait.until(ExpectedConditions.visibilityOf(this.timeslices));
		this.timeslices.click();
		this.wait.until(ExpectedConditions.visibilityOf(this.tablerows.get(0)));
		sLastRun = this.tablerows.get(1).findElements(By.tagName("td")).get(10).getText();
		dLastRun = new SimpleDateFormat("MM/dd/yy hh:mm a").parse(sLastRun);
		this.wait.until(ExpectedConditions.visibilityOf(this.ppmrefreshbtn));
		this.ppmrefreshbtn.click();
		this.wait.until(ExpectedConditions.visibilityOf(this.tablerows.get(0)));
		String sLatestRun = this.tablerows.get(1).findElements(By.tagName("td")).get(10).getText();
		Date dLatestRun = new SimpleDateFormat("MM/dd/yy hh:mm a").parse(sLastRun);
		Assert.assertTrue(dLatestRun.compareTo(dLastRun) > 0,
				"Latest Run is " + sLatestRun + " Last Run is " + sLastRun);
	}

	public void checkProcessEngineColors() throws InterruptedException {
		List<WebElement> processenginerows;
		String processenginestatuscolor;
		if (this.aboutclosebtn.isDisplayed()) {
			this.aboutclosebtn.click();
		}
		this.admintab.click();
		this.wait.until(ExpectedConditions.visibilityOf(this.processengines));
		this.processengines.click();
		this.wait.until(ExpectedConditions.visibilityOf(this.tableBody));
		// TimeUnit.SECONDS.sleep(10);
		processenginerows = this.tablerows;
		System.out.println(processenginerows.size());
		for (WebElement processenginerow : processenginerows) {
			processenginestatuscolor = processenginerow.findElements(By.tagName("td")).get(7)
					.findElement(By.tagName("img")).getAttribute("name");
			Assert.assertTrue(
					processenginestatuscolor.equalsIgnoreCase("DiamondYellow")
							| processenginestatuscolor.equalsIgnoreCase("DiamondRed")
							| processenginestatuscolor.equalsIgnoreCase("DiamondGrey"),
					"The status colore displayed is " + processenginestatuscolor);
		}
	}

	public void checkContentAddinStatus() throws InterruptedException {
		List<WebElement> contentaddinsrows;
		String contentaddinstatus;
		this.admintab.click();
		this.wait.until(ExpectedConditions.visibilityOf(this.contentaddins));
		this.contentaddins.click();
		this.wait.until(ExpectedConditions.visibilityOf(this.tableBody));
		TimeUnit.SECONDS.sleep(3);
		contentaddinsrows = this.tablerows;
		for (WebElement contentaddinsrow : contentaddinsrows) {

			contentaddinstatus = contentaddinsrow.findElements(By.tagName("td")).get(5).getText();
			Assert.assertTrue(
					contentaddinstatus.equalsIgnoreCase("Installed")
							| contentaddinstatus.equalsIgnoreCase("Upgrade Ready")
							| contentaddinstatus.equalsIgnoreCase("Upgrade Pending"),
					"The status displayed is " + contentaddinstatus);
		}
	}

	public void checkJasperSoftReport() throws InterruptedException {
		this.libraryNames.get(5).click();
		TimeUnit.SECONDS.sleep(25);
		this.libraryOptions.get(0).click();
		this.wait.until(ExpectedConditions.visibilityOf(this.applybtn));
		this.applybtn.click();
		this.wait.until(ExpectedConditions.visibilityOfAllElements(this.datawarehouseschemarows));

		Assert.assertTrue(this.datawarehouseschemarows.get(12).getText().trim().equalsIgnoreCase("CMN_DB_HISTORY"),
				"Text displayed is " + this.datawarehouseschemarows.get(12).getText().trim());
	}

	public void checkResources() {
		List<WebElement> resourcerows;
		String status;
		String type;
		this.admintab.click();
		this.wait.until(ExpectedConditions.visibilityOf(this.resources));
		this.resources.click();
		this.wait.until(ExpectedConditions.visibilityOf(this.tableBody));
		resourcerows = this.tablerows;
		for (WebElement resourcerow : resourcerows) {

			if (resourcerow.findElements(By.tagName("td")).get(3).getText().equalsIgnoreCase("od_admin@dummy.com")
					| resourcerow.findElements(By.tagName("td")).get(3).getText()
							.equalsIgnoreCase("ca_portal@dummy.com")) {

				status = resourcerow.findElements(By.tagName("td")).get(7).getText();
				type = resourcerow.findElements(By.tagName("td")).get(6).getText();
				Assert.assertTrue(status.equalsIgnoreCase("Active"), "The status displayed is " + status);
				Assert.assertTrue(type.equalsIgnoreCase("Internal"), "The type displayed is " + type);

			}

		}

	}

	public void checkAdvanceReporting() throws InterruptedException {
		int i;
		int rows;
		this.wait.until(ExpectedConditions.visibilityOf(this.hometab));
		this.hometab.click();
		this.wait.until(ExpectedConditions.visibilityOf(this.advancedreporting));
		this.advancedreporting.click();
		this.wait.until(ExpectedConditions.visibilityOf(this.jasperframe));
		driver.switchTo().frame(this.jasperframe);
		TimeUnit.SECONDS.sleep(5);
		this.wait.until(ExpectedConditions.visibilityOf((this.librarytab)));
		this.librarytab.click();
		this.wait.until(ExpectedConditions.visibilityOfAllElements(this.resultsListHeader));
		TimeUnit.SECONDS.sleep(10);
		rows = this.descriptions.size();
		System.out.println(rows);
		List<WebElement> descriptions = this.descriptions;
		for (i = 2; i < (rows / 2); i = i + 2) {
			System.out.println("****" + i + descriptions.get(i).getText());
			Assert.assertTrue(descriptions.get(i).getText().contains("PMO Accelerator"),
					"The Description had " + descriptions.get(i).getText());
		}
	}

	public void LogOut(){
		driver.switchTo().defaultContent();
		this.logout.click();
		this.wait.until(ExpectedConditions.visibilityOf(this.userName));
	}
	public WebElement getUserName() {
		return userName;
	}

	public void setUserName(WebElement userName) {
		this.userName = userName;
	}

	public WebElement getPassword() {
		return password;
	}

	public void setPassword(WebElement password) {
		this.password = password;
	}

	public WebElement getLoginbtn() {
		return loginbtn;
	}

	public void setLoginbtn(WebElement loginbtn) {
		this.loginbtn = loginbtn;
	}

	public WebElement getAboutlink() {
		return aboutlink;
	}

	public void setAboutlink(WebElement aboutlink) {
		this.aboutlink = aboutlink;
	}

	public WebElement getAboutdialog() {
		return aboutdialog;
	}

	public void setAboutdialog(WebElement aboutdialog) {
		this.aboutdialog = aboutdialog;
	}

	public WebElement getHelplink() {
		return helplink;
	}

	public void setHelplink(WebElement helplink) {
		this.helplink = helplink;
	}

	public WebElement getLearnlink() {
		return learnlink;
	}

	public void setLearnlink(WebElement learnlink) {
		this.learnlink = learnlink;
	}

	public WebElement getHometab() {
		return hometab;
	}

	public void setHometab(WebElement hometab) {
		this.hometab = hometab;
	}

	public WebElement getAdmintab() {
		return admintab;
	}

	public void setAdmintab(WebElement admintab) {
		this.admintab = admintab;
	}

	public WebElement getTimeslices() {
		return timeslices;
	}

	public void setTimeslices(WebElement timeslices) {
		this.timeslices = timeslices;
	}

	public WebElement getProcessengines() {
		return processengines;
	}

	public void setProcessengines(WebElement processengines) {
		this.processengines = processengines;
	}

	public WebElement getContentaddins() {
		return contentaddins;
	}

	public void setContentaddins(WebElement contentaddins) {
		this.contentaddins = contentaddins;
	}

	public WebElement getResources() {
		return resources;
	}

	public void setResources(WebElement resources) {
		this.resources = resources;
	}

	public WebElement getAdvancedreporting() {
		return advancedreporting;
	}

	public void setAdvancedreporting(WebElement advancedreporting) {
		this.advancedreporting = advancedreporting;
	}

	public List<WebElement> getAboutinformation() {
		return aboutinformation;
	}

	public void setAboutinformation(List<WebElement> aboutinformation) {
		this.aboutinformation = aboutinformation;
	}

	public WebElement getAboutclosebtn() {
		return aboutclosebtn;
	}

	public void setAboutclosebtn(WebElement aboutclosebtn) {
		this.aboutclosebtn = aboutclosebtn;
	}

	public List<WebElement> getTablerows() {
		return tablerows;
	}

	public void setTablerows(List<WebElement> tablerows) {
		this.tablerows = tablerows;
	}

	public WebElement getPpmrefreshbtn() {
		return ppmrefreshbtn;
	}

	public void setPpmrefreshbtn(WebElement ppmrefreshbtn) {
		this.ppmrefreshbtn = ppmrefreshbtn;
	}

	public WebElement getPpmapp() {
		return ppmapp;
	}

	public void setPpmapp(WebElement ppmapp) {
		this.ppmapp = ppmapp;
	}

	public WebElement getRosources() {
		return rosources;
	}

	public void setRosources(WebElement rosources) {
		this.rosources = rosources;
	}

	public List<WebElement> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(List<WebElement> descriptions) {
		this.descriptions = descriptions;
	}

	public List<WebElement> getLibraryNames() {
		return libraryNames;
	}

	public void setLibraryNames(List<WebElement> libraryNames) {
		this.libraryNames = libraryNames;
	}

	public List<WebElement> getLibraryOptions() {
		return libraryOptions;
	}

	public void setLibraryOptions(List<WebElement> libraryOptions) {
		this.libraryOptions = libraryOptions;
	}

	public WebElement getApplybtn() {
		return applybtn;
	}

	public void setApplybtn(WebElement applybtn) {
		this.applybtn = applybtn;
	}

	public List<WebElement> getDatawarehouseschemarows() {
		return datawarehouseschemarows;
	}

	public void setDatawarehouseschemarows(List<WebElement> datawarehouseschemarows) {
		this.datawarehouseschemarows = datawarehouseschemarows;
	}
}
