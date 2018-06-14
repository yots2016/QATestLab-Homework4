package myprojects.automation.assignment4;


import myprojects.automation.assignment4.model.ProductData;
import myprojects.automation.assignment4.utils.Properties;
import myprojects.automation.assignment4.utils.logging.CustomReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Contains main script actions that may be used in scripts.
 */
public class GeneralActions {
    private WebDriver driver;
    private WebDriverWait wait;

    public GeneralActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
    }

    /**
     * Logs in to Admin Panel.
     * @param login
     * @param password
     */
    public void login(String login, String password) {
        CustomReporter.logAction("user login");

        driver.get(Properties.getBaseAdminUrl());
        driver.findElement(By.id("email")).sendKeys(login);
        driver.findElement(By.id("passwd")).sendKeys(password);
        driver.findElement(By.name("submitLogin")).click();

        CustomReporter.logAction("enter to admin panel");
        waitForContentLoad(By.id("main"));
    }

    public ProductData createProduct() {
        CustomReporter.logAction("generate product data");
        return ProductData.generate();
    }

    /**
     * Waits until page loader disappears from the page
     * @param by
     */
    public WebElement waitForContentLoad(By by) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }
}
