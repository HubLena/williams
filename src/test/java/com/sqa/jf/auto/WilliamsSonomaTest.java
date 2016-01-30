package com.sqa.jf.auto;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class WilliamsSonomaTest
{

  private boolean acceptNextAlert = true;

  private String baseUrl;

  private WebDriver driver;

  private StringBuffer verificationErrors = new StringBuffer();

  @DataProvider
  public Object[][] keywordData()
  {
    return new Object[][] { new Object[] { "tea kettle", 1 }, new Object[] { "cookware", 1 },
        new Object[] { "pan", 1 } };
  }

  @BeforeClass(groups = "chrome")
  public void setUpChrome() throws Exception
  {
    System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
    this.driver = new ChromeDriver();
    this.baseUrl = "http://williams-sonoma.com";
    // driver.manage().window()
    this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  //
  // @BeforeClass(groups = "firefox")
  // public void setUpFirefox() throws Exception {
  // this.driver = new FirefoxDriver();
  // this.baseUrl = "http://williams-sonoma.com";
  // this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  // }
  //
  // @BeforeClass(groups = "ie")
  // public void setUpIE() throws Exception {
  // System.setProperty("webdriver.ie.driver", "drivers/IEDriverServer.exe");
  // this.driver = new InternetExplorerDriver();
  // this.baseUrl = "http://williams-sonoma.com";
  // this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  // }
  //
  // @BeforeClass(groups = "safari")
  // public void setUpSafari() throws Exception {
  // this.driver = new SafariDriver();
  // this.baseUrl = "http://williams-sonoma.com";
  // this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  // }
  @AfterClass(alwaysRun = true, groups = "craigslist-search")
  public void tearDown() throws Exception
  {
    this.driver.quit();

  }

  @Test(enabled = false, dataProvider = "keywordData", groups = "craigslist-search")
  public void testCraigslist(String keywords) throws Exception
  {
    this.driver.get(this.baseUrl + "/");
    this.driver.findElement(By.cssSelector("a.jjj > span.txt")).click();
    this.driver.findElement(By.id("query")).clear();
    this.driver.findElement(By.id("query")).sendKeys(keywords);
    this.driver.findElement(By.xpath("//button[@type='submit']")).click();
    List<WebElement> jobs = this.driver.findElements(By.cssSelector("p.row > a.i"));
    System.out.println("Keywords:" + keywords);
    for (WebElement job : jobs)
    {
      System.out.println(job.getAttribute("href"));
    }
  }

  @Test(groups = "williams-sonoma")
  public void testWilliamsSonoma() throws Exception
  {
    // Go to Williams Sonoma website
    this.driver.get(this.baseUrl);
    // Collect the popup close buton location
    WebElement popupX = this.driver.findElement(By.cssSelector("a.overlayCloseButton.overlayCloseX"));
    // Wait for Popup to load - 3 Seconds
    Thread.sleep(3000);
    // Click the X on the popup window
    popupX.click();
    // Wait for 5 seconds
    Thread.sleep(5000);
    // Create an Actions object
    Actions builder = new Actions(this.driver);
    // Find the cookware Button and hold it inside a WebElement object
    WebElement cookwareButton = this.driver.findElement(By.cssSelector(".topnav-cookware"));
    // Designate the Actions command to move to cookware button and perform
    // this action
    builder.moveToElement(cookwareButton).perform();
    // Wait for 5 seconds
    Thread.sleep(5000);
    // Locate the Tea Kettle button now that the submenu is visible
    WebElement teaKettles = this.driver.findElement(By.linkText("Tea Kettles"));
    // Click on Tea Kettles
    builder.click(teaKettles).perform();
    // Assert that the title of the page is what it is suppose to be
    Assert.assertEquals(this.driver.getTitle(), "Stainless Steel Tea Kettles & Copper Tea Kettles | Williams-Sonoma");
  }

  private String closeAlertAndGetItsText()
  {
    try
    {
      Alert alert = this.driver.switchTo().alert();
      String alertText = alert.getText();
      if (this.acceptNextAlert)
      {
        alert.accept();
      } else
      {
        alert.dismiss();
      }
      return alertText;
    } finally
    {
      this.acceptNextAlert = true;
    }
  }

  private boolean isAlertPresent()
  {
    try
    {
      this.driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e)
    {
      return false;
    }
  }

  private boolean isElementPresent(By by)
  {
    try
    {
      this.driver.findElement(by);
      return true;
    } catch (NoSuchElementException e)
    {
      return false;
    }
  }
}
