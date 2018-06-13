package myprojects.automation.assignment4;


import myprojects.automation.assignment4.model.ProductData;
import myprojects.automation.assignment4.utils.Properties;
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
        // TODO implement logging in to Admin Panel
//        throw new UnsupportedOperationException();

        driver.get(Properties.getBaseAdminUrl());

        WebElement emailWebElement = driver.findElement(By.id("email"));
        emailWebElement.sendKeys(login);

        WebElement passwordWebElement = driver.findElement(By.id("passwd"));
        passwordWebElement.sendKeys(password);

        WebElement loginButtonWebElement = driver.findElement(By.name("submitLogin"));
        loginButtonWebElement.click();
    }

    public void createProduct(ProductData newProduct) {
        // TODO implement product creation scenario
        throw new UnsupportedOperationException();
    }

    /**
     * Waits until page loader disappears from the page
     * @param by
     */
    public WebElement waitForContentLoad(By by) {
        // TODO implement generic method to wait until page content is loaded

        // wait.until(...);
        // ...

        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }
}
