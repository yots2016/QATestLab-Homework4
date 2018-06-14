package myprojects.automation.assignment4.tests;

import myprojects.automation.assignment4.BaseTest;
import myprojects.automation.assignment4.model.ProductData;
import myprojects.automation.assignment4.utils.Properties;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class CreateProductTest extends BaseTest {

    private String productName;
    private String quantityProduct;
    private String priceProduct;

    @DataProvider
    public Object[][] getLoginData() {
        return new String[][] {
                {"webinar.test@gmail.com", "Xcg7299bnSmMuRLp9ITw"}
        };
    }

    @Test (dataProvider = "getLoginData")
    public void createNewProduct(String login, String password) {
        actions.login(login, password);

        waitForContentLoad(By.id("subtab-AdminCatalog")).click();
        driver.findElement(By.id("page-header-desc-configuration-add")).click();
        productName = actions.createProduct().getName();
        waitForContentLoad(By.id("form_step1_name_1")).sendKeys(productName);

        quantityProduct = actions.createProduct().getQty().toString();
        driver.findElement(By.id("form_step1_qty_0_shortcut")).sendKeys(quantityProduct);
        WebElement priceForm = driver.findElement(By.id("form_step1_price_shortcut"));
        priceForm.sendKeys("\b\b\b\b\b\b\b\b");

        priceProduct = actions.createProduct().getPrice();
        priceForm.sendKeys(priceProduct);

        new Actions(driver).keyDown(Keys.CONTROL).sendKeys("o").perform();
        waitForContentLoad(By.className("growl-close")).click();

        driver.findElement(By.xpath("//*[@class=\"btn btn-primary js-btn-save\"]")).click();
        waitForContentLoad(By.className("growl-close")).click();
    }

    @Test (dependsOnMethods = {"createNewProduct"})
    public void checkingProductDisplay() {
        driver.get(Properties.getBaseUrl());
        waitForContentLoad(By.xpath("//*[@id=\"content\"]/section/a")).click();
        driver.switchTo().window(new ArrayList<>(driver.getWindowHandles()).get(1));

        WebElement serchField = waitForContentLoad(By.xpath("//*[@id=\"search_widget\"]//*[@name=\"s\"]"));
        serchField.sendKeys(Keys.BACK_SPACE);
        serchField.sendKeys(productName);
        serchField.submit();

        WebElement productNameElementAfterSearch = driver.findElement(By
                .xpath("//*[@id=\"js-product-list\"]//*[@class=\"h3 product-title\"]/a"));
        String productNameAfterSearch = productNameElementAfterSearch.getText();
        Assert.assertEquals(productNameAfterSearch, productName);

        productNameElementAfterSearch.click();

        WebElement productNameElementAfterOpen = driver.findElement(By.xpath("//*[@id=\"main\"]/div[1]/div[2]/h1"));
        String productNameAfterOpen = productNameElementAfterOpen.getText();
        Assert.assertEquals(productNameAfterOpen, productName.toUpperCase());

        WebElement productQuantityElementAfterOpen = driver.findElement(By
                .xpath("//*[@class=\"product-quantities\"]/span"));
        String productQuantityAfterOpen = productQuantityElementAfterOpen.getText();
        Assert.assertEquals(productQuantityAfterOpen.split(" ")[0], quantityProduct);

        WebElement productPriceElementAfterOpen = driver.findElement(By
                .xpath("//*[@id=\"main\"]/div[1]/div[2]/div[1]/div[1]/div/span"));
        String productPriceAfterOpen = productPriceElementAfterOpen.getAttribute("content");
        productPriceAfterOpen = productPriceAfterOpen.replace('.', ',');
        Assert.assertEquals(productPriceAfterOpen, priceProduct);
    }

    public WebElement waitForContentLoad(By by) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }
}
