package generic;

import java.lang.reflect.Method;
import java.net.URL;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.support.events.EventFiringDecorator;


@Listeners(TestListener.class)
public class BaseTest {
	public static final String DEFAULT_URL="https://demo.actitime.com";
	public static final String DEFAULT_GRID="no";
	public static final String DEFAULT_PPTFILE="qa.properties";
	public static final String DEFAULT_HTMLPATH="report/Spark.html";
	public static String XL_PATH;
	public static ExtentReports report;
	public ExtentTest extentTest;
	
	public WebDriver original_driver;
	public WebDriver driver;
	public WebDriverWait wait;

	@Parameters({"htmlpath"})
	@BeforeSuite
	public void intReport(@Optional(DEFAULT_HTMLPATH)  String htmlPath)
	{
		report=new ExtentReports();	
		ExtentSparkReporter spark = new ExtentSparkReporter(htmlPath);
		report.attachReporter(spark);
	}
	
	@AfterSuite
	public void generateReport()
	{
		report.flush();
	}
	
	@Parameters({"appurl","grid","pptfile"})
	@BeforeMethod
	public void preCondition(@Optional(DEFAULT_URL) String appURL,@Optional(DEFAULT_GRID) String grid,@Optional(DEFAULT_PPTFILE) String pptFile,Method method) throws Exception {
		String testName=method.getName();
		extentTest = report.createTest(testName);
		
		String browser=Util.getProperty(pptFile, "browser");
		extentTest.info("Browser is:"+browser);
		
		String sITO=Util.getProperty(pptFile, "ITO");
		long lITO=Long.parseLong(sITO);
		extentTest.info("ITO is:"+sITO);
		
		String sETO=Util.getProperty(pptFile, "ETO");
		long lETO=Long.parseLong(sETO);
		extentTest.info("ETO is:"+sETO);
		
		XL_PATH=Util.getProperty(pptFile, "XLPATH");
		extentTest.info("XL PATH is:"+XL_PATH);

		if(grid.equalsIgnoreCase("no"))
		{
			extentTest.info("Execution in local system");
			if(browser.equals("chrome"))
			{
				extentTest.info("Browser is: chrome");
				original_driver=new ChromeDriver();
			}
			else
			{
				extentTest.info("Browser is: Firefox");
				original_driver=new FirefoxDriver();
			}
		}
		else
		{
			extentTest.info("Execution in remote system");
			if(browser.equals("chrome"))
			{
				extentTest.info("Browser is: chrome");
				original_driver=new RemoteWebDriver(new URL(grid), new ChromeOptions());
			}
			else
			{
				extentTest.info("Browser is: Firefox");
				original_driver=new RemoteWebDriver(new URL(grid), new FirefoxOptions());
			}
			
		}
		extentTest.info("Enter the URL:"+appURL);
		original_driver.get(appURL);
		extentTest.info("maximize the browser");
		original_driver.manage().window().maximize();
		extentTest.info("Set ITO to :"+lITO);
		original_driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(lITO));
		extentTest.info("Set ETO to "+lETO);
		wait=new WebDriverWait(original_driver, Duration.ofSeconds(lETO));
		EventFiringDecorator<WebDriver> decorator=new EventFiringDecorator<WebDriver>(new SeleniumListener(extentTest));
		driver = decorator.decorate(original_driver);
	}
	
	@AfterMethod
	public void postCondition(ITestResult result) {
		
		List<String> output = Reporter.getOutput(result);
		for(String msg:output)
		{
			extentTest.info(msg);
		}
		
		String testName = result.getName();
		int status=result.getStatus();
		if(status==2)
		{
			String timeStamp = Util.getTimeStamp();
			String imgPath="./images/"+testName+timeStamp+".png";
			ScreenShot.takePageScreenShot(driver,imgPath);
			String msg=result.getThrowable().getMessage();
			imgPath="../images/"+testName+timeStamp+".png";
			extentTest.fail(msg,MediaEntityBuilder.createScreenCaptureFromPath(imgPath).build());
		}
		extentTest.info("Closing the browser");
		driver.quit();
	}
}
