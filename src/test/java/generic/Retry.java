package generic;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer
{
	public int count=0;
	public boolean retry(ITestResult result) {
		BaseTest baseTest = (BaseTest)result.getInstance();
		String methodName = result.getName();
		baseTest.extentTest.info("We are inside retry method");
		baseTest.extentTest.info("The failed method is: "+methodName);
		if(count<1)//how many times to rerun failed
		{
			baseTest.extentTest.skip("Skipping this test and reruning it");
			//baseTest.report.removeTest(methodName);
			count++;
			return true;//true- rerun again 
		}
		else
		{
			baseTest.extentTest.warning("Already re-executed, hence stoping it");
			return false;//false - dont rerun
		}
	}

}
