package generic;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JSUtil {

	public static void sendKeys(WebDriver driver,WebElement element,String input)
	{
		JavascriptExecutor j=(JavascriptExecutor)driver;
		j.executeScript("arguments[0].value=arguments[1]",element,input);
	}
	
	public static void click(WebDriver driver,WebElement element)
	{
		JavascriptExecutor j=(JavascriptExecutor)driver;
		j.executeScript("arguments[0].click()",element);
	}
	
	public static void clear(WebDriver driver,WebElement element)
	{
		JavascriptExecutor j=(JavascriptExecutor)driver;
		j.executeScript("arguments[0].value=''",element);
	}
	
	public static String getValue(WebDriver driver,WebElement element)
	{
		JavascriptExecutor j=(JavascriptExecutor)driver;
		String v=(String)j.executeScript("return arguments[0].value",element);
		return v;
	}
	
	public static void scrollBy(WebDriver driver,int x,int y)
	{
		JavascriptExecutor j=(JavascriptExecutor)driver;
		j.executeScript("window.scrollBy("+x+","+y+")");
	}
	
	public static void scrollTo(WebDriver driver,int x,int y)
	{
		JavascriptExecutor j=(JavascriptExecutor)driver;
		j.executeScript("window.scrollTo(arguments[0],arguments[1])",x,y);
	}
	

	public static void scrollToElement(WebDriver driver,WebElement element)
	{
		JavascriptExecutor j=(JavascriptExecutor)driver;
		j.executeScript("arguments[0].scrollIntoView()", element);
	}
	
}
