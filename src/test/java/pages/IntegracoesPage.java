package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.Actions;
import utils.Log;

import java.time.Duration;
import java.util.List;


public class IntegracoesPage {
	private WebDriver driver;
	private Actions actions;
	private WebDriverWait wait;

	public IntegracoesPage(WebDriver driver) {
		this.driver = driver;
		this.actions = new Actions(this.driver);
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	/*TESTE COMPONENTES*/

	public void acessarIntegracao() {
		actions.esperar(1000);
		actions.clicarBotaoPegandoPeloId("ROLE_CONFIGURACOES");
		actions.esperar(1000);
		actions.clicarBotaoPegandoPeloXpath("//p-card[@id='Integracoes']/div/div/div/div[2]/p[2]");
		Log.registrar("Acessar integrações");
	}

	public void adicionarNovaIntegracao() {
		actions.clicarBotaoPegandoPeloXpath("//button/span");
		Log.registrar("Nova integração");
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

	public void adicionarTemplate() {
		actions.clicarBotaoPegandoPeloCss(
				"div.col-12:nth-child(5) > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > p-inputswitch:nth-child(1) > div:nth-child(1) > span:nth-child(2)");
	}

	public void selecionarLead(int parametro, String equipeOuUsuario) {
		if (equipeOuUsuario == null) {
			Log.registrar("equipeOuUsuario é null, método será ignorado.");
			return;
		}
		switch (parametro) {
		case 0: // Aquario
			actions.clicarBotaoPegandoPeloCss("#active > div > span");
			Log.registrar("Enviar lead para aquario em integração");
			break;
		case 1: // Equipe
			selecionarEquipeOuUsuario(equipeOuUsuario, true);
			Log.registrar("Criar lead e enviar para equipe: " + equipeOuUsuario + "");
			break;
		case 2: // Usuário
			selecionarEquipeOuUsuario(equipeOuUsuario, false);
			Log.registrar("Criar lead e enviar para usuário: " + equipeOuUsuario + "");
			break;
		case 3:
			Log.registrar("Parâmetro nulo.");
			break;
		default:
			Log.registrar("Parâmetro inválido: " + parametro);
			throw new IllegalArgumentException("Parâmetro inválido: " + parametro);
		}
	}

	private void selecionarEquipeOuUsuario(String nome, boolean ehEquipe) {
		actions.clicarBotaoPegandoPeloXpath("//p-inputswitch/div/span");
		Log.registrar("Criar lead em especifico");
		actions.esperar(2000);
		actions.clicarBotaoPegandoPeloXpath(
				ehEquipe ? "//div[2]/p-radiobutton/div/div[2]" : "//p-radiobutton/div/div[2]");
		Log.registrar(ehEquipe ? "Selecionar Equipe" : "Selecionar Usuário");

		actions.clicarBotaoPegandoPeloXpath("//div[2]/div/p-dropdown/div/span");
		List<WebElement> dropdownItems = driver.findElements(By.xpath("//p-dropdownitem/li"));

		for (WebElement item : dropdownItems) {
			try {
				if (item.getText().equalsIgnoreCase(nome)) {
					item.click();
					break;
				}
			} catch (StaleElementReferenceException e) {
				dropdownItems = driver.findElements(By.xpath("//p-dropdownitem/li"));
			}
		}
	}

	public void variaveis(String variavel) {
		if (variavel == null) {
			Log.registrar("Variável é null, método variaveis será ignorado.");
			return;
		}
		actions.escreverPegandoPeloName("infoAdicionais", variavel);
	}

	public void nomeIntegracao(String descricao) {
		actions.escreverPegandoPeloName("nomeIntegracao", descricao);
	}

	public void validarNotificacao(String msg) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement notificacao = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".p-toast-detail")));
			String textoNotificacao = notificacao.getText();
			Assert.assertEquals(msg, textoNotificacao);
			Log.registrar("Notificação de sucesso exibida: " + textoNotificacao + "");

		} catch (NoSuchElementException e) {
			Log.registrar("Falha ao encontrar a notificação de sucesso: " + e.getMessage());
			Assert.fail("Falha ao encontrar a notificação de sucesso: " + e.getMessage());
		}
	}

	public void botaoSalvarIntegração() {
		actions.clicarBotaoPegandoPeloXpath(
				"/html/body/app-root/div/app-configuracoes/div/app-integracao/p-dialog/div/div/div[3]/form/div[8]/p-button[1]/button/span");
	}

	public void numeroDisparo(String numero) {
		if (numero == null) {
			Log.registrar("Parâmentro 'numero' é null, método ignorado.");
			return;
		}

		actions.escreverPegandoPeloName("numeroDisparo", numero);
		Log.registrar("Numero de template adicionado: " + numero);
	}

	public void nomeTemplateOmnia(String nomeTemplate) {
		actions.escreverPegandoPeloName("infoTemplate", nomeTemplate);
	}

	public void procedimento(String nomeProcedimento) {
		actions.clicarBotaoPegandoPeloXpath("//div[5]/div[2]/div[2]/div/p-dropdown/div/span");
		List<WebElement> dropdownItems = driver.findElements(By.xpath("//p-dropdownitem/li"));
		for (WebElement item : dropdownItems) {
			if (item.getText().equalsIgnoreCase(nomeProcedimento)) {
				item.click();
				break;
			}
		}
		Log.registrar("Procurar pelo procediemento: "+ nomeProcedimento);
	}

	public void adicionarNovoAgendador() {
		actions.clicarBotaoPegandoPeloXpath("//p-card[@id='agendadorTarefas']/div/div/div/div[2]/p");
		Log.registrar("Adicionar novo agendador");
	}
	
	public void descricaoAgendador(String nomeDescricao) {
		actions.escreverPegandoPeloName("descricao", nomeDescricao);
	}

	public void selecionarTipoAlerta(String nomeDoTipo) {
		actions.clicarBotaoPegandoPeloXpath("/html/body/app-root/div/app-configuracoes/div/app-integracao/app-agendador-tarefas/p-dialog/div/div/div[3]/div/form/div[3]/div/p-dropdown/div/span");
		List<WebElement> tipoAlerta = driver.findElements(By.xpath("//p-dropdownitem/li"));
		for (WebElement alerta : tipoAlerta) {
			if (alerta.getText().equalsIgnoreCase(nomeDoTipo)) {
				alerta.click();
				break;
			}
		}
	}

	public void horarioExecucao(String hora) {
		actions.escreverPegandoPeloName("horarioExec", hora);
	}
	
	public void deletarIntegracao(String nomeEsperado) {
	        // Aguarda a tabela ser carregada
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));

	        List<WebElement> linhas = driver.findElements(By.xpath("//table/tbody/tr"));

	        for (int i = 1; i <= linhas.size(); i++) {
	            // Encontra o elemento da coluna de nome da linha atual
	            WebElement nomeColuna = driver.findElement(By.xpath("//tr[" + i + "]/td[2]"));

	            String x = nomeColuna.getText();
	            Log.registrar(x);
	            // Verifica se o texto da coluna de nome é igual ao nome esperado
	            if (nomeColuna.getText().equals(nomeEsperado)) {
	            	
	            	
	                // Encontra e clica no botão de excluir da linha correspondente
	                WebElement botaoExcluir = driver.findElement(By.xpath("//tr[" + i + "]/td[6]/div/button[2]/span"));
	                botaoExcluir.click();

	                // Adiciona uma espera para a confirmação de exclusão, se necessário
	                wait.until(ExpectedConditions.invisibilityOf(linhas.get(i - 1)));

	                // Se a integração foi excluída, pode sair do loop
	                break;
	            }
	        }
	  }
	
	// GRUPO
	
	public void grupoCriarIntegracao(String tipo, String endpoint, String token, String nomeIntegracao, 
			String variavel, int caseLead, String equipeUsuario) {
		acessarIntegracao();
		adicionarNovaIntegracao();
		selecionarTipoIntegracao(tipo);
		urlIntegracao(endpoint);
		tokenFornecedor(token);
		nomeIntegracao(nomeIntegracao);
		variaveis(variavel);
		selecionarLead(caseLead, equipeUsuario);
	}

	public void grupoAdicionarTemplate(String procedimento, String numero, String template) {
		actions.esperar(1000);
		adicionarTemplate();
		actions.esperar(1000);
		procedimento(procedimento);
		numeroDisparo(numero);
		nomeTemplateOmnia(template);
	}
	
	public void grupoAdicionarAgendador(String descricao) {
		actions.esperar(4000);
		acessarIntegracao();
		adicionarNovoAgendador();
		descricaoAgendador(descricao);
		selecionarTipoAlerta("ALERTAR_AGENDAMENTOS");
		
	}

}
