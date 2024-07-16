package pages;

import org.openqa.selenium.WebDriver;

import utils.Actions;


public class ProcedimentoPage {
	private WebDriver driver;
	private Actions actions;

	public ProcedimentoPage(WebDriver driver) {
		this.driver = driver;
		this.actions = new Actions(this.driver);
	}
	
	public void acessarProcediementos() {
		actions.esperar(3000);	
		actions.clicarBotaoPegandoPeloId("ROLE_CONFIGURACOES");
		actions.esperar(500);	
		actions.clicarBotaoPegandoPeloXpath("//*[@id=\"configuracoes\"]/div/div/div/div[2]");
		actions.esperar(1000);	
		actions.clicarBotaoPegandoPeloXpath("//tr[2]/td[6]/div/button/span");
		
	}
	
	public void procedimento() {
		
		// pesquisa pelo nome do procedimento
		// Adicionar novas etapas
	}
}