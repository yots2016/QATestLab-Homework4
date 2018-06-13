package myprojects.automation.assignment4.tests;

import myprojects.automation.assignment4.BaseTest;
import myprojects.automation.assignment4.model.ProductData;
import myprojects.automation.assignment4.utils.Properties;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class CreateProductTest extends BaseTest {

    @DataProvider
    public Object[][] getLoginData() {
        return new String[][] {
                {"webinar.test@gmail.com", "Xcg7299bnSmMuRLp9ITw"}
        };
    }

    @Test (dataProvider = "getLoginData")
    public void createNewProduct(String login, String password) {
        // TODO implement test for product creation

        actions.login(login, password);
        waitForContentLoad(By.id("subtab-AdminCatalog")).click();
        driver.findElement(By.id("page-header-desc-configuration-add")).click();
        String productName = ProductData.generate().getName();
        waitForContentLoad(By.id("form_step1_name_1")).sendKeys(productName);
        driver.findElement(By.id("form_step1_qty_0_shortcut")).sendKeys(ProductData.generate().getQty().toString());
        WebElement priceForm = driver.findElement(By.id("form_step1_price_shortcut"));
        priceForm.sendKeys("\b\b\b\b\b\b\b\b");
        priceForm.sendKeys(ProductData.generate().getPrice());
        new Actions(driver).keyDown(Keys.CONTROL).sendKeys("o").perform();
        waitForContentLoad(By.className("growl-close")).click();
        driver.findElement(By.xpath("//*[@class=\"btn btn-primary js-btn-save\"]")).click();
        waitForContentLoad(By.className("growl-close")).click();

        driver.get(Properties.getBaseUrl());
        waitForContentLoad(By.xpath("//*[@id=\"content\"]/section/a")).click();
        ArrayList<String> tabs = new ArrayList(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
//        driver.close();


        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//
        WebElement serchField = waitForContentLoad(By.xpath("//*[@id=\"search_widget\"]//*[@name=\"s\"]"));
        serchField.sendKeys(Keys.BACK_SPACE);
        serchField.sendKeys(productName);
        serchField.submit();


        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    // TODO implement logic to check product visibility on website

//    @Test(dependsOnMethods = {"createNewProduct"})
//    public void checkProduct() {
//
//    }

    public WebElement waitForContentLoad(By by) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public static void clickOnInvisibleElement(WebElement element, WebDriver driver) {

        String script = "var object = arguments[0];"
                + "var theEvent = document.createEvent(\"MouseEvent\");"
                + "theEvent.initMouseEvent(\"click\", true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);"
                + "object.dispatchEvent(theEvent);"
                ;

        ((JavascriptExecutor)driver).executeScript(script, element);
    }
}
