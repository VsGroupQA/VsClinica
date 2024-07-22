package pages;

import org.openqa.selenium.By;
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
	
	// COMPONENTES
	
	public void acessarIntegracao() {
		actions.clicarBotaoPegandoPeloId("ROLE_CONFIGURACOES");
		actions.esperar(1000);
		actions.clicarBotaoPegandoPeloXpath(
				"//p-card[@id='Integracoes']/div/div/div/div[2]/p[2]");
		Log.registrar("Acessar integrações");
	
	}
	
	public void adicionarNovaIntegracao() {
		actions.clicarBotaoPegandoPeloXpath("//button/span");
		Log.registrar("Nova integração");
	}
	
	public void adicionarNovoAgendador() {
		actions.clicarBotaoPegandoPeloXpath("//p-card[@id='agendadorTarefas']/div/div/div/div[2]/p");
		Log.registrar("Adicionar novo agendador");
	}
	
	public void selecionarTipoIntegracao(String nomeDoTipo) {
		actions.clicarBotaoPegandoPeloXpath("//p-dropdown/div/span");
		List<WebElement> tipoIntegracao = driver.findElements(By.xpath("//p-dropdownitem/li"));
		for (WebElement integracoes : tipoIntegracao) {
			if (integracoes.getText().equalsIgnoreCase(nomeDoTipo)) {
				integracoes.click();
				break;
			}
		}
	}
	
	public void urlIntegracao(String url) {
		actions.escreverPegandoPeloName("nome", url);
	}
	
	public void tokenFornecedor(String informarToken) {
		actions.escreverPegandoPeloName("token", informarToken);
	}
	
	public void template() {
		
	}
	
	public void criarLead() {
		
	}
	
	public void enviarLeadAquario() {
		
	}
	
	public void variaveis() {
		
	}
	
	public void nomeIntegracao() {
		
	}
	
	// GRUPO
	
	public void criarIntegracao(String tipo, String endpoint) {
		acessarIntegracao();
		adicionarNovaIntegracao();
		selecionarTipoIntegracao(tipo);
		urlIntegracao(endpoint);
		tokenFornecedor(Access.tokenOmnia);
	}
}