package pos.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import generic.BaseTest;
import generic.Excel;
import generic.Retry;

import pos.pages.HomePage;
import pos.pages.LoginPage;

public class ValidLogin extends BaseTest {

	@Test 
	public void testPosValidLogin() throws InterruptedException {
		 
		
		
		   LoginPage loginPage=new LoginPage(driver);
			loginPage.setUserName("admin");
	
			loginPage.setPassword("pointofsale");
	
			loginPage.clickGoButton();
	
			
			HomePage homePage=new HomePage(driver);
			
			boolean result =homePage.VerifyHomePageDisplayed(wait);
			Assert.assertTrue(result);
		}
	}

