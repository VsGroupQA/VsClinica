package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.Access;
import utils.Actions;

import java.util.List;
import utils.Log;

public class IntegracoesPage {
	private WebDriver driver;
	private Actions actions;

	public IntegracoesPage(WebDriver driver) {
		this.driver = driver;
		this.actions = new Actions(this.driver);
	}
	
	public void acessarIntegracao() {
		actions.clicarBotaoPegandoPeloId("ROLE_CONFIGURACOES");
		actions.clicarBotaoPegandoPeloXpath(
				"/html/body/app-root/div/app-configuracoes/div/div[2]/p-card[4]/div/div/div/div[2]");
	
	}
	
	public void xpto() {
		
	}
}