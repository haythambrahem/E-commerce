package org.example.pfa.ui;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddProductUITest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testAddProduct() throws InterruptedException {
        // 1️⃣ Open the admin products page
        driver.get("http://localhost:4200/admin/dashboard/products");

        // 2️⃣ Open the "Ajouter un produit" dialog
        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".add-btn")));
        addButton.click();

        // 3️⃣ Wait for dialog to appear
        WebElement dialog = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".dialog-container")));

        // 4️⃣ Fill product fields
        driver.findElement(By.id("product-name")).sendKeys("Selenium Test Product");
        driver.findElement(By.id("product-price")).sendKeys("99.99");
        driver.findElement(By.id("product-stock")).sendKeys("10");
        driver.findElement(By.id("product-description")).sendKeys("Description test Selenium");

        // 5️⃣ Select category (Angular Material)
        WebElement matSelect = driver.findElement(By.id("product-category"));
        matSelect.click(); // open dropdown
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//mat-option[@value='PRINTEMPS']")));
        option.click();

        // 6️⃣ Upload image
        WebElement upload = driver.findElement(By.id("product-image"));
        upload.sendKeys("C:\\Users\\merie\\Picture\\images.jpg"); // adapt path

        // 7️⃣ Submit the product
        WebElement submitButton = driver.findElement(By.id("btn-submit-product"));
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();

        // 8️⃣ Wait for the product to appear in the table
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//td[text()='Selenium Test Product']")));

        // 9️⃣ Assertion
        String pageSource = driver.getPageSource();
        Assertions.assertTrue(pageSource.contains("Selenium Test Product"),
                "Le produit ajouté n'apparaît pas dans la liste !");
    }
}
