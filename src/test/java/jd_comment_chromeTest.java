import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;

/**
 * Created by luwe on 9/19/2017.
 */
public class jd_comment_chromeTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void main() throws Exception {
        //t1234
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        int i, j;
        String projectpath = System.getProperty("user.dir") ;
        WebElement link;
        String comment = "东西不错";
        System.setProperty("webdriver.chrome.driver", projectpath + "\\res\\ChromeDriver.exe");
        WebDriver driver = new ChromeDriver();
        try {
            //put the browser to the 2nd screen
            driver.manage().window().setPosition(new Point(-1000,1));
            driver.manage().window().maximize();
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            driver.get("https://club.jd.com/mycomments.aspx");
            //login
            driver.findElement(By.linkText("账户登录")).click();
            //Read account credential from file
            String[] account = readFileByLines(projectpath+ "\\res\\jd.txt");
            driver.findElement(By.id("loginname")).sendKeys(account[1]);
            driver.findElement(By.id("nloginpwd")).sendKeys(account[2]);
            driver.findElement(By.id("loginsubmit")).click();
            //new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfElementLocated(By.linkText("评价")));

            //test code to avoid the actual run
            if (driver.findElements(By.linkText("评价")).size() > 0) {
                File screenshotFile= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(screenshotFile, new File(projectpath + "/screenshot/e" + sdf.format(date) + ".png"));
                return;
            }

            //Iterate for each item to be commented
            while (driver.findElements(By.linkText("评价")).size() > 0) {
                driver.findElement(By.linkText("评价")).click();
                //switch tab to the new open tab
                ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
                driver.close();
                driver.switchTo().window(tabs.get(1));
                //Rate for delivery
                i = 1;
                j = 1;
                while (driver.findElements(By.xpath("//*[@id=\"activityVoucher\"]/div[2]/div[1]/div[1]/div[" + Integer.toString(i) + "]/span[2]/span[5]")).size() > 0) {
                    link = driver.findElement(By.xpath("//*[@id=\"activityVoucher\"]/div[2]/div[1]/div[1]/div[" + Integer.toString(i) + "]/span[2]/span[5]"));
                    executor.executeScript("arguments[0].click()", link);
                    i++;
                }
                //Rate for the goods
                while (driver.findElements(By.xpath("//*[@id=\"container\"]/div/div/div[2]/div[1]/div[" + Integer.toString(j + 5) + "]/div[2]/div[1]/div[2]/span/span[5]")).size() > 0) {
                    link = driver.findElement(By.xpath("//*[@id=\"container\"]/div/div/div[2]/div[1]/div[" + Integer.toString(j + 5) + "]/div[2]/div[1]/div[2]/span/span[5]"));//rating
                    executor.executeScript("arguments[0].click()", link);
                    if (driver.findElements(By.xpath("//*[@id=\"container\"]/div/div/div[2]/div[1]/div[" + Integer.toString(j + 5) + "]/div[2]/div[2]/div[2]/div/a[1]")).size() > 0) {//some of the items might not need comment category
                        link = driver.findElement(By.xpath("//*[@id=\"container\"]/div/div/div[2]/div[1]/div[" + Integer.toString(j + 5) + "]/div[2]/div[2]/div[2]/div/a[1]"));//comment category
                        executor.executeScript("arguments[0].click()", link);
                        driver.findElement(By.xpath("//*[@id=\"container\"]/div/div/div[2]/div[1]/div[" + Integer.toString(j + 5) + "]/div[2]/div[3]/div[2]/div[1]/textarea")).sendKeys(comment);//comment words
                    } else {
                        driver.findElement(By.xpath("//*[@id=\"container\"]/div/div/div[2]/div[1]/div[" + Integer.toString(j + 5) + "]/div[2]/div[2]/div[2]/div[1]/textarea")).sendKeys(comment);
                    }
                    j++;
                }

                executor.executeScript("window.scrollTo(0, document.body.scrollHeight)");//scroll to the bottom so the script can 'see' the submit button
                driver.findElement(By.linkText("提交")).click();
                driver.findElement(By.linkText("返回待评价列表 >")).click();
            }
        } catch (Exception e) {
            System.out.print (e);
            //capature error screenshot
            File screenshotFile= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshotFile, new File(projectpath + "/screenshot/e" + sdf.format(date) + ".png"));
            return;
        } finally {
            driver.close();
        }
    }

    private String[] readFileByLines(String fileName) {
        String[] content = new String[16];
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                content[line] = tempString;
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return content;
    }
}