/**
 * Created by luwe on 9/5/2017.
 */
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.chrome.ChromeDriver;

public class jd_comment {

    public static void main(String[] args){

        System.setProperty("webdriver.gecko.driver", "C:\\Selenium\\geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.get("https://club.jd.com/mycomments.aspx");
        driver.manage().window().maximize();
        driver.findElement(By.linkText("账户登录")).click();
        driver.findElement(By.id("loginname")).sendKeys("iverson.lu@gmail.com");
        driver.findElement(By.id("nloginpwd")).sendKeys("Su11281202");
        driver.findElement(By.id("loginsubmit")).click();
        new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfElementLocated(By.linkText("评价")));
        driver.findElement(By.linkText("评价")).click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        driver.findElement(By.xpath("//*[@id='activityVoucher']/div[2]/div[1]/div[1]/div[1]/span[2]/span[5]")).click();
        driver.findElement(By.xpath("//*[@id='activityVoucher']/div[2]/div[1]/div[1]/div[1]/span[2]/span[4]")).click();

        //driver.findElement(By.xpath("//*[@id=\"container\"]/div/div/div[2]/div[1]/div[6]/div[2]/div[3]/div[2]/div[1]/textarea")).sendKeys("东西不错");
        //driver.findElement(By.xpath("//*[@id=\"activityVoucher\"]/div[2]/div[1]/div[1]/div[2]/span[2]/span[5]")).click();
        //driver.close();
    }
}
