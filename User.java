package ccim.iar.ui.screen;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import ccim.iar.ui.main.run;

public class User {

	private static final Logger log = LogManager.getLogger(User.class);

	public void login(WebDriver driver, String domain, String username, String password) {

        driver.get("https://" + domain + "/concerto");
        log.info("User login on page " + driver.getTitle());
        driver.navigate().to("javascript:document.getElementById('overridelink').click()");
        log.debug("Current url: " + driver.getCurrentUrl());
        
        driver.findElement(By.id("UserId")).sendKeys(username);
        driver.findElement(By.id("Password")).sendKeys(password);
        driver.findElement(By.id("Login")).submit();
		
        long startTime = System.currentTimeMillis();
        
        // Wait for the page to load, timeout after 20 seconds
        (new WebDriverWait(driver, 20)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
            	return d.findElement(By.id("login-disclaimer-form")).isDisplayed();
            }
        });
        
        log.info("login duration (sec): " + (System.currentTimeMillis() - startTime)/1000);

		/*        String winHandleBefore = driver.getWindowHandle();
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }
*/
        
        // disclaimer
        log.info("User login on page " + driver.getTitle());
        log.debug("Current url: " + driver.getCurrentUrl());
        log.debug("login-disclaimer-accept = " + driver.findElement(By.id("login-disclaimer-accept")).getText());
        driver.findElement(By.id("login-disclaimer-accept")).click();
        
        startTime = System.currentTimeMillis();
        
        // Wait for the page to load, timeout after 10 seconds
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
            	return d.findElement(By.id("ConcertoMenuBar")).isDisplayed();
            }
        });
        log.info("disclaimer duration (sec): " + (System.currentTimeMillis() - startTime)/1000);

        log.info("user logged in = " + driver.findElement(By.id("cpo-user-name")).getText());

	}

	public void logout(WebDriver driver) {

		driver.switchTo().defaultContent(); // you are now outside frames

		log.info("Logout from page " + driver.getTitle());
        
        driver.findElement(By.id("logoutbuttonDiv")).findElement(By.tagName("button")).click();

        // Wait for the page to load, timeout after 10 seconds
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
            	return d.findElement(By.id("Login")).isDisplayed();
            }
        });
        
        log.info("Logout done, now on page " + driver.getTitle());

	}
}

