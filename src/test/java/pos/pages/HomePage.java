package pos.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import generic.WebUtil;

public class HomePage {
	@FindBy(xpath ="//a[text()='Logout']")
	private WebElement LogoutLink;
	
	public HomePage(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
	}
	
	public boolean VerifyHomePageDisplayed(WebDriverWait wait)
	{
	return WebUtil.verifyPageByElement(wait, LogoutLink,"Home Page");
	}

}
