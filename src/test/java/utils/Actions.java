package utils;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Actions {

	private WebDriver driver;

	public Actions(WebDriver driver) {
		this.driver = driver;
	}

	// CSS
	public WebElement pegarElementoPeloCss(String css) {
		return driver.findElement(By.cssSelector(css));
	}

	public void clicarBotaoPegandoPeloCss(String css) {
		pegarElementoPeloCss(css).click();
	}

	public void escreverPegandoPeloCss(String css, String texto) {
		pegarElementoPeloCss(css).sendKeys(texto);
	}

	public String lerTextoPegandoPeloCss(String css) {
		return pegarElementoPeloCss(css).getText();
	}

	// ID
	public WebElement pegarElementoPeloId(String id) {
		return driver.findElement(By.id(id));
	}

	public void clicarBotaoPegandoPeloId(String id) {
		pegarElementoPeloId(id).click();
	}

	public void escreverPegandoPeloId(String id, String texto) {
		pegarElementoPeloId(id).sendKeys(texto);
	}

	// XPATH
	public WebElement pegarElementoPeloXpath(String xpath) {
		return driver.findElement(By.xpath(xpath));
	}

	public void clicarBotaoPegandoPeloXpath(String xpath) {
		pegarElementoPeloXpath(xpath).click();
	}

	public void escreverPegandoPeloXpath(String xpath, String texto) {
		pegarElementoPeloXpath(xpath).sendKeys(texto);
	}

	// NAME
	public WebElement pegarElementoPeloName(String name) {
		return driver.findElement(By.name(name));
	}

	public void clicarBotaoPegandoPeloName(String name) {
		pegarElementoPeloName(name).click();
	}

	public void escreverPegandoPeloName(String name, String texto) {
		pegarElementoPeloName(name).sendKeys(texto);
	}


	// ESPERA
	public void esperar(int tempoEspera) { 
		try {
			Thread.sleep(tempoEspera);
		} catch (InterruptedException e) {
			e.printStackTrace();
			Log.registrar("Problema com a espera. Erro: " + e);
		}
	}

	// RECARREGAR PÁGINA
	public void recarregarPagina() {
		driver.navigate().refresh();
	}
	
	// Método de espera explícita
	public void esperarElementoVisivel(By by, int tempoEspera) {
	    new WebDriverWait(driver, Duration.ofSeconds(tempoEspera))
	        .until(ExpectedConditions.visibilityOfElementLocated(by));
	}

}
