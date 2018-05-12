package toy;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;



public class ToySelenium {
    @BeforeAll
    public static void setGeckoDriverPath(){
        System.setProperty("webdriver.gecko.driver", "lib/gecko_lib");
    }
    @Test
    public void test1() {
        FirefoxDriver driver = new FirefoxDriver();
        driver.get("https://www.google.com");
        try {
            driver.findElement(By.xpath("//*[@id=\"lst-ib\"]")).sendKeys("Charanjit");
            driver.findElement(By.xpath("//*[@id=\"tsf\"]/div[2]/div[3]/center/input[1]")).click();
            Assertions.assertEquals(driver.getTitle(), "Charanjit - Google Search");
        }
        finally {
            driver.quit();
        }
    }

    @Test
    public void testHeadless(){
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("-headless");

        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("moz:firefoxOptions", options);

        FirefoxDriver driver = new FirefoxDriver(options);
        try {
            driver.setLogLevel(Level.FINEST);
            driver.get("https://www.google.com");
            driver.findElement(By.xpath("//*[@id=\"lst-ib\"]")).sendKeys("Charanjit");
            File src= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            try {
                // now copy the  screenshot to desired location using copyFile
                File target = new File("s1.png");
                Files.copy(src.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            catch(Exception ex) {
                System.out.println("can't copy");

            }

            driver.findElement(By.xpath("//*[@id=\"hplogo\"]")).click();
            WebElement submitButton = driver.findElement(By.xpath("//*[@id=\"tsf\"]/div[2]/div[3]/center/input[1]"));
            if(submitButton != null)
                submitButton.click();
            Assertions.assertEquals(driver.getTitle(), "Charanjit - Google Search");
        }
        catch(Exception ex) {
            System.out.println(ex.getCause());
            System.out.println(ex.getMessage());
        }
        finally {
            driver.quit();
        }
    }

}