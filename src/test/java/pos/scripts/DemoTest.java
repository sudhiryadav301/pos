package pos.scripts;

import org.testng.annotations.Test;

import generic.BaseTest;
import generic.Excel;
import generic.Retry;

public class DemoTest extends BaseTest {

	@Test(priority = 1, retryAnalyzer = Retry.class)
	public void testPosValidLogin() throws InterruptedException {
		String un = Excel.getCellData(XL_PATH, "ValidLogin", 1, 0);
		String pw = Excel.getCellData(XL_PATH, "ValidLogin", 1, 1);

		System.out.println("done");

	}
}
