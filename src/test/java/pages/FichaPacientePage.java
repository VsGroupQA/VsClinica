package pages;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Actions;
import utils.Log;


public class FichaPacientePage {
	private WebDriver driver;
	private Actions actions;

	public FichaPacientePage(WebDriver driver) {
		this.driver = driver;
		this.actions = new Actions(this.driver);
	}
	
}