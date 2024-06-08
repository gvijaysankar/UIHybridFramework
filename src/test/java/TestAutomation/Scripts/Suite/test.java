package TestAutomation.Scripts.Suite;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;


public class test {
  
 @Test
  public void test1()
  { 
		  String crnt_name="vijay";
		  Assert.assertEquals(crnt_name,"sankar");
  }		  



}
