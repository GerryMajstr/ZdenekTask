package StepDefinition;

import java.time.Duration;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.netty.handler.timeout.TimeoutException;

public class AddProductToBasket {

	WebDriver driver;
	WebDriverWait wait;
	Wait<WebDriver> fluentWait;
	Actions action;

	public void openWebUrl() {
		
		//Set property for chromedriver
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Zdene\\Desktop\\Selenium\\chromedriver.exe");
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		fluentWait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(10))
				.pollingEvery(Duration.ofSeconds(2)).ignoring(NoSuchElementException.class);
		action = new Actions(driver);

		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		
		//Navigate to aliexpress.com
		driver.get("https://aliexpress.com");
	}

	public void navigateToTShirtCategory() {
		
		//Close popups if they are present on page
		fluentWait.until(ExpectedConditions.elementToBeClickable(By.className("btn-accept"))).click();
		fluentWait.until(ExpectedConditions.elementToBeClickable(By.className("btn-close"))).click();
		
		//Hover over men category and click on t-shirts
		WebElement menCategory = driver.findElement(By.xpath(
				"//dl[contains(@class, 'item-men')]//a[contains(@href, 'men-clothing') and (not(contains(text(), 'Sale')))]"));
		action.moveToElement(menCategory).perform();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
				"//dl[contains(@class, 'item-men')]//a[contains(@href, 't-shirt') and contains(text(), 'Shirt')]")))
				.click();
		System.out.print("\nNavigation to men hoodies succeeded");
	}

	public void filterProductsByPrice() throws InterruptedException {
		
		//Click on sort by price twice, to sort it from high -> low
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(@ae_object_value, 'price')]")))
				.click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(@ae_object_value, 'price')]")))
				.click();
		Thread.sleep(1000);
	}

	public void addProducts() throws InterruptedException {
		for (int i = 1; i < 3; i++) {
			
			//Open most expensive product
			wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("(//div[@class= 'product-container']//a//img)[" + i + "]"))).click();
			Thread.sleep(1000);
			
			//New tab in browser is opened, we have to switch to new one and click add to basket
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs.get(1));
			Thread.sleep(1000);
			wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//div[@class='product-action']//button[contains(@class, 'addcart')]"))).click();
			
			//Close opened tab and switch back to original one
			driver.close();
			driver.switchTo().window(tabs.get(0));
			Thread.sleep(1000);
		}
		System.out.print("\nProducts were added to basket");
	}

	public void checkBasket() throws InterruptedException {
		
		//Open basket
		wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//div[contains(@class, 'shopcart')]//a[contains(@href, 'shopcart')]"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class= 'shopping-cart-list']")));
		Thread.sleep(1000);
		
		//Get number of products in basket and check it's correct
		int numberOfProductsInBasket = driver.findElements(By.xpath("//div[@class= 'shopping-cart-list']/div")).size();
		assertEquals(2, numberOfProductsInBasket);
		System.out.print("\n" + numberOfProductsInBasket + " products are visible in basket");
	}

	@Test
	public void aliexpressTest() throws InterruptedException {
		try {
			openWebUrl();
			navigateToTShirtCategory();
			filterProductsByPrice();
			addProducts();
			checkBasket();
			System.out.print("\nTest finished successfully");
		} catch (InterruptedException e) {
			//
		} finally {
			driver.close();
			driver.quit();
		}
	}
}
