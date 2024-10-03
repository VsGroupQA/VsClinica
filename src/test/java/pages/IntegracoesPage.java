package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
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

	// COMPONENTES
	
	 /**
     * Acessa a Configurações.
     */
	public void acessarConfig() {
		actions.esperar(3000);
		actions.clicarBotaoPegandoPeloId("ROLE_CONFIGURACOES");
		Log.registrar("Acessando Configuração");

	}

	 /**
     * Acessa a Integrações.
     */
	public void acessarIntegracao() {
		actions.esperar(1000);
		actions.clicarBotaoPegandoPeloXpath("//p-card[@id='Integracoes']/div/div/div/div[2]/p[2]");
		Log.registrar("Clicando na opção de integrações");
	}

    /**
     * Adiciona uma nova integração no sistema.
     */
	public void adicionarNovo(String xpath) {
		actions.clicarBotaoPegandoPeloXpath(xpath);
		Log.registrar("Selecionar botão de adicionar novo item. xpath: "+xpath+"");
	}

	
//    /**
//     * Adiciona uma nova integração no sistema.
//     */
//	public void adicionarNovo() {
//		actions.clicarBotaoPegandoPeloXpath("//button/span");
//		Log.registrar("Clicando no botão de adicionar nova integração");
//	}

	 /**
     * Seleciona o tipo de integração com base no nome fornecido.
     *
     * @param nomeDoTipo Nome do tipo de integração a ser selecionado.
     */
	public void selecionarTipoIntegracao(String nomeDoTipo) {
	    Log.registrar("Selecionando tipo de integração: " + nomeDoTipo);
	    
	    // Abrir o dropdown
	    WebElement dropdown = driver.findElement(By.xpath("//p-dropdown/div/span"));
	    dropdown.click();
	    
	    // Esperar até que os itens do dropdown estejam visíveis
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p-dropdownitem/li")));
	    
	    // Buscar e selecionar o item correto
	    List<WebElement> tipoIntegracao = driver.findElements(By.xpath("//p-dropdownitem/li"));
	    for (WebElement integracao : tipoIntegracao) {
	        if (integracao.getText().equalsIgnoreCase(nomeDoTipo)) {
	            integracao.click();
	            Log.registrar("Tipo de integração " + nomeDoTipo + " encontrado e selecionado");
	            break;
	        }
	    }
	}


	  /**
     * Insere a URL da integração.
     *
     * @param url URL do Endpoint do disparo do Omnia.
     */
	public void urlIntegracao(String url) {
		actions.escreverPegandoPeloName("nome", url);
		Log.registrar("Inserindo URL da integração: " + url);
	}

	/**
     * Insere o token do fornecedor na integração.
     *
     * @param informarToken Token do Omnia.
     */
	public void tokenFornecedor(String informarToken) {
		actions.escreverPegandoPeloName("token", informarToken);
		Log.registrar("Inserindo token do fornecedor (Omnia)");
	}

	/**
     * Adiciona um template à integração.
     */
	public void adicionarTemplate() {
		actions.clicarBotaoPegandoPeloCss("div.col-12:nth-child(5) > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > p-inputswitch:nth-child(1) > div:nth-child(1) > span:nth-child(2)");
		Log.registrar("Adicionando template");
	}

    /**
     * Seleciona uma criação de lead - 0: Aquario, 1: Equipe, 2:Usuario, 3:null.
     *
     * @param opcao      Parâmetro que define o tipo de lead a ser selecionado.
     * @param equipeOuUsuario Nome da equipe ou usuário a ser selecionado.
     */
	public void selecionarLead(int opcao, String equipeOuUsuario) {
		if (equipeOuUsuario == null) {
			Log.registrar("equipeOuUsuario é null, método será ignorado.");
			return;
		}
		switch (opcao) {
		
		case 0: // Aquario
			actions.clicarBotaoPegandoPeloCss("#active > div > span");
			Log.registrar("Enviar lead para aquario em integração");
			break;
		case 1: // Equipe
			selecionarEquipeOuUsuario(equipeOuUsuario, true);
			Log.registrar("Criar lead e enviar para equipe: " + equipeOuUsuario);
			break;
		case 2: // Usuário
			selecionarEquipeOuUsuario(equipeOuUsuario, false);
			Log.registrar("Criar lead e enviar para usuário: " + equipeOuUsuario);
			break;
		case 3:
			Log.registrar("Parâmetro nulo.");
			break;
		}
	}


    /**
     * Seleciona uma equipe ou usuário com base no nome e no tipo.
     *
     * @param nome     Nome da equipe ou usuário.
     * @param ehEquipe Verdadeiro se for equipe, falso se for usuário.
     */
	private void selecionarEquipeOuUsuario(String nome, boolean ehEquipe) {
		actions.clicarBotaoPegandoPeloXpath("//p-inputswitch/div/span");
		Log.registrar("Criar lead em especifico");
		actions.esperar(2000);
		actions.clicarBotaoPegandoPeloXpath(ehEquipe ? "//div[2]/p-radiobutton/div/div[2]" : "//p-radiobutton/div/div[2]");
		Log.registrar(ehEquipe ? "Selecionar Equipe" : "Selecionar Usuário");

		actions.clicarBotaoPegandoPeloXpath("//div[2]/div/p-dropdown/div/span");
		List<WebElement> dropdownItems = driver.findElements(By.xpath("//p-dropdownitem/li"));

		for (WebElement item : dropdownItems) {
			try {
				if (item.getText().equalsIgnoreCase(nome)) {
					item.click();
					Log.registrar("Item " + nome + " selecionado");
					break;
				}
			} catch (StaleElementReferenceException e) {
				dropdownItems = driver.findElements(By.xpath("//p-dropdownitem/li"));
				Log.registrar("StaleElementReferenceException: Recarregando dropdown items");
			}
		}
	}

	/**
     * Insere variáveis adicionais na integração.
     *
     * @param variavel Variável a ser inserida.
     */
	public void variaveis(String variavel) {
		if (variavel == null) {
			Log.registrar("Variável é null, método variaveis será ignorado.");
			return;
		}
		actions.escreverPegandoPeloName("infoAdicionais", variavel);
		Log.registrar("Inserindo variáveis adicionais: " + variavel);
	}

	/**
     * Insere o nome da integração.
     *
     * @param descricao Nome da integração.
     */
	public void nomeIntegracao(String nomeDescricao) {
		actions.escreverPegandoPeloName("nomeIntegracao", nomeDescricao);
		Log.registrar("Inserindo nome da integração: " + nomeDescricao);
	}

	 /**
     * Valida a notificação exibida após a ação.
     *
     * @param msg Mensagem esperada na notificação.
     */
	public void validarNotificacao(String msg) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement notificacao = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".p-toast-detail")));
			String textoNotificacao = notificacao.getText();
			
			Assert.assertEquals(msg, textoNotificacao);
			Log.registrar("Notificação de sucesso exibida: " + textoNotificacao);
			
		} catch (NoSuchElementException e) {
			Log.registrar("Falha ao encontrar a notificação de sucesso: " + e.getMessage());
			Assert.fail("Falha ao encontrar a notificação de sucesso: " + e.getMessage());
		}
	}
	
	 /**
     * Fechar notificação
     */
	public void fecharNotificacao () {
		actions.clicarBotaoPegandoPeloCss(".p-toast-icon-close-icon");
	}
	
	/**
     * Clica no botão de salvar agendador.
     */
	public void botaoSalvar(String xpath) {
		actions.clicarBotaoPegandoPeloXpath(xpath);
		Log.registrar("Clicando no botão de salvar");
	}

//	/**
//     * Clica no botão de salvar agendador.
//     */
//	public void botaoSalvarIntegracao() {
//		actions.clicarBotaoPegandoPeloXpath("/html/body/app-root/div/app-configuracoes/div/app-integracao/p-dialog/div/div/div[3]/form/div[8]/p-button[1]/button/span");
//		Log.registrar("Clicando no botão de salvar integração");
//	}
//
//	public void botaoSalvarAgendador() {
//		actions.clicarBotaoPegandoPeloXpath("//p-button/button/span");
//		Log.registrar("Clicando no botão de salvar agendador");
//	}

	/**
     * Insere o número de disparo na integração.
     *
     * @param numero Número de disparo a ser inserido.
     */
	public void numeroDisparo(String numero) {
		if (numero == null) {
			Log.registrar("Parâmetro 'numero' é null, método ignorado.");
			return; 
			}
		actions.escreverPegandoPeloName("numeroDisparo", numero);
		Log.registrar("Número de template adicionado: " + numero);
	}

	/**
	 * Insere o nome do template Omnia.
	 *
	 * @param nomeTemplate Nome do template a ser inserido.
	 */
	public void nomeTemplateOmnia(String nomeTemplate) {
		actions.escreverPegandoPeloName("infoTemplate", nomeTemplate);
		Log.registrar("Inserindo nome do template Omnia: " + nomeTemplate);
	}

	/**
	 * Seleciona um procedimento a partir de um dropdown.
	 *
	 * @param nomeProcedimento Nome do procedimento a ser selecionado.
	 */
	public void procedimento(String nomeProcedimento) {
		actions.clicarBotaoPegandoPeloXpath("//div[5]/div[2]/div[2]/div/p-dropdown/div/span");
		Log.registrar("Clicando no dropdown de procedimentos");
		
		List<WebElement> dropdownItems = driver.findElements(By.xpath("//p-dropdownitem/li"));
		for (WebElement item : dropdownItems) {
			if (item.getText().equalsIgnoreCase(nomeProcedimento)) {
				item.click();
				Log.registrar("Procedimento " + nomeProcedimento + " selecionado");
				break;
			}
		}
	}

//	/**
//	 * Adiciona um novo agendador.
//	 */
//	public void adicionarNovoAgendador() {
//		actions.clicarBotaoPegandoPeloXpath("//p-card[@id='agendadorTarefas']/div/div/div/div[2]/p");
//		Log.registrar("Clicando no botão de adicionar novo agendador");
//	}

	/**
	 * Insere a descrição do agendador.
	 *
	 * @param nomeDescricao Descrição a ser inserida.
	 */
	public void descricaoAgendador(String nomeDescricao) {
		actions.escreverPegandoPeloName("descricao", nomeDescricao);
		Log.registrar("Inserindo descrição do agendador: " + nomeDescricao);
	}

	/**
	 * Seleciona o tipo de alerta a partir de um dropdown.
	 *
	 * @param nomeDoTipo Nome do tipo de alerta a ser selecionado.
	 */
	public void selecionarTipoAlerta(String nomeDoTipo) {
		actions.clicarBotaoPegandoPeloXpath("/html/body/app-root/div/app-configuracoes/div/app-integracao/app-agendador-tarefas/p-dialog/div/div/div[3]/div/form/div[3]/div/p-dropdown/div/span");
		Log.registrar("Clicando no dropdown de tipo de alerta");
		
		List<WebElement> tipoAlerta = driver.findElements(By.xpath("//p-dropdownitem/li"));
		for (WebElement alerta : tipoAlerta) {
			if (alerta.getText().equalsIgnoreCase(nomeDoTipo)) {
				alerta.click();
				Log.registrar("Tipo de alerta " + nomeDoTipo + " selecionado");
				break;
			}
		}
	}

	/**
	 * Insere o horário de execução.
	 *
	 * @param hora Horário a ser inserido.
	 */
	public void horarioExecucao(String hora) {
		// Saindo null
		actions.escreverPegandoPeloName("horarioExec", hora);
		Log.registrar("Inserindo horário de execução: " + hora);
	}

	/**
	 * Exclui uma integração com base no nome fornecido.
	 *
	 * @param nomeEsperado Nome da integração a ser excluída.
	 */
	public void excluirIntegracao(String nomeEsperado) {
		Log.registrar("Tentando excluir integração com o nome: " + nomeEsperado);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
		Log.registrar("Tabela de integrações carregada");
		
		List<WebElement> linhas = driver.findElements(By.xpath("//table/tbody/tr"));

		for (int i = 1; i <= linhas.size(); i++) {
			try {
				WebElement nomeColuna = driver.findElement(By.xpath("//tr[" + i + "]/td[2]"));
				String item = nomeColuna.getText();
				Log.registrar("Nome da integração na linha " + i + ": " + item);
				
				if (item.equals(nomeEsperado)) {
					WebElement botaoExcluir = driver.findElement(By.xpath("//tr[" + i + "]/td[6]/div/button[2]/span"));
					actions.esperar(2000);
					botaoExcluir.click();
					Log.registrar("Integração excluída com sucesso");
					break;
				}

			} catch (NoSuchElementException e) {
				Log.registrar("Elemento não encontrado na linha " + i + ": " + e.getMessage());
			}

		}
	}

	/**
	 * Desativa uma integração com base no nome fornecido.
	 *
	 * @param nomeEsperado Nome da integração a ser desativada.
	 */
	public void desativarIntegracao(String nomeEsperado) {
		Log.registrar("Tentando desativar integração com o nome: " + nomeEsperado);
		actions.esperar(1000);
		Log.registrar("Esperando");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
		Log.registrar("Tabela de integrações carregada");

		List<WebElement> linhas = driver.findElements(By.xpath("//table/tbody/tr"));

		for (int i = 1; i <= linhas.size(); i++) {
			try {
				WebElement nomeColuna = driver.findElement(By.xpath("//tr[" + i + "]/td[2]"));
				String txt = nomeColuna.getText();
				Log.registrar("Nome da integração na linha " + i + ": " + txt);

				if (txt.equals(nomeEsperado)) {
					Log.registrar("Integração encontrada: " + txt);
					WebElement botaoDesativar = driver
							.findElement(By.xpath("//tr[" + i + "]/td[6]/div/p-inputswitch/div/span"));
					actions.esperar(2000);
					botaoDesativar.click();
					Log.registrar("Clicando no botão de desativar integração");

					break;
				}
			} catch (NoSuchElementException e) {
				Log.registrar("Elemento não encontrado na linha " + i + ": " + e.getMessage());
			}
		}
	}

	/**
	 * Desativa um agendamento de disparo com base no nome fornecido.
	 *
	 * @param nomeEsperado Nome do agendamento a ser desativado.
	 */
	public void desativarAgendamento(String nomeEsperado) {
		Log.registrar("Tentando desativar agendamento de disparo com o nome: " + nomeEsperado);

	    // Espera até que a tabela esteja visível
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='pr_id_22-table']")));
	    Log.registrar("Tabela de integrações carregada");

	    List<WebElement> linhas = driver.findElements(By.xpath("//table[@id='pr_id_22-table']/tbody/tr"));

	    boolean encontrado = false;

	    for (int i = 1; i <= linhas.size(); i++) {
	        try {
	            WebElement linha = linhas.get(i - 1);
	            WebElement nomeColuna = linha.findElement(By.xpath("./td[1]"));
	            String txt = nomeColuna.getText();
	            Log.registrar("Nome da integração na linha " + i + ": " + txt);

	            if (txt.equals(nomeEsperado)) {
	                Log.registrar("Integração encontrada: " + txt);
	                WebElement botaoEditar = linha.findElement(By.xpath("//table[@id='pr_id_22-table']/tbody/tr/td[6]/div/p-inputswitch/div/span"));
	                botaoEditar.click();
	                Log.registrar("Clicando no botão de desativar integração");
	                encontrado = true;
	                break;
	            }
	        } catch (NoSuchElementException e) {
	            Log.registrar("Elemento não encontrado na linha " + i + ": " + e.getMessage());
	        }
	    }

	    if (!encontrado) {
	        Log.registrar("Nenhuma integração foi encontrada com o nome: " + nomeEsperado);
	    }
	}

	/**
	 * Exclui um agendamento de disparo com base no nome fornecido.
	 *
	 * @param nomeEsperado Nome do agendamento a ser excluído.
	 */
	public void excluirAgendamento(String nomeEsperado) {
	    Log.registrar("Tentando excluir agendamento de disparo com o nome: " + nomeEsperado);
	    actions.esperar(2000);  // Espera inicial
	    Log.registrar("Esperando");

	    // Espera até que a tabela esteja visível
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='pr_id_22-table']")));
	    Log.registrar("Tabela de agendamento carregada");

	    List<WebElement> linhas = driver.findElements(By.xpath("//table[@id='pr_id_22-table']/tbody/tr"));

	    boolean encontrado = false;

	    for (int i = 1; i <= linhas.size(); i++) {
	        try {
	            WebElement linha = linhas.get(i - 1);
	            WebElement nomeColuna = linha.findElement(By.xpath("./td[1]"));
	            String txt = nomeColuna.getText();
	            Log.registrar("Nome do agendador na linha " + i + ": " + txt);

	            if (txt.equals(nomeEsperado)) {
	                Log.registrar("Agendamento programado encontrado: " + txt);
	                WebElement botaoExcluir = linha.findElement(By.xpath("./td[6]/div/button[2]"));
	                botaoExcluir.click();
	                Log.registrar("Clicando no botão de desativar integração");
	                encontrado = true;
	                break;
	            }
	        } catch (NoSuchElementException e) {
	            Log.registrar("Elemento não encontrado na linha " + i + ": " + e.getMessage());
	        }
	    }

	    if (!encontrado) {
	        Log.registrar("Nenhum agendamento de disparo foi encontrado com o nome: " + nomeEsperado);
	    }
	}

	/**
	 * Fecha modal
	 */
	public void fecharModal(String xpath) {
		actions.clicarBotaoPegandoPeloXpath(xpath);
	}

//	/**
//	 * Fecha modal
//	 */
//	public void fecharModal() {
//		actions.clicarBotaoPegandoPeloXpath("//p-button[2]/button/span");
//	}

	/**
	 * Edita integração
	 */
	public void editarIntegracao(String nomeEsperado) {
	    Log.registrar("Tentando editar integração com o nome: " + nomeEsperado);
	    actions.esperar(1000);
	    Log.registrar("Esperando");

	    // Espera até que a tabela esteja visível
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
	    Log.registrar("Tabela de integrações carregada");

	    List<WebElement> linhas = driver.findElements(By.xpath("//table/tbody/tr"));

	    for (int i = 1; i <= linhas.size(); i++) {
	        try {
	            WebElement nomeColuna = linhas.get(i - 1).findElement(By.xpath("./td[2]"));
	            String txt = nomeColuna.getText();
	            Log.registrar("Nome da integração na linha " + i + ": " + txt);

	            if (txt.equals(nomeEsperado)) {
	                Log.registrar("Integração encontrada: " + txt);
	                WebElement botaoEditar = linhas.get(i - 1).findElement(By.xpath("./td[6]/div/button"));
	                botaoEditar.click();
	                Log.registrar("Clicando no botão de editar integração");
	                break;
	            }
	        } catch (NoSuchElementException e) {
	            Log.registrar("Elemento não encontrado na linha " + i + ": " + e.getMessage());
	        }
	    }
	}

//	// modal?
//	public void fecharAgendador() {
//		actions.clicarBotaoPegandoPeloCss(".p-dialog-header-close-icon > path");
//	}	
	
	/**
	 * Edita Agendador
	 */
	public void editarAgendador(String nomeEsperado) {
	    Log.registrar("Tentando editar agendamento de disparo com o nome: " + nomeEsperado);
	    actions.esperar(1000);  // Espera inicial
	    Log.registrar("Esperando");

	    // Espera até que a tabela esteja visível
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='pr_id_22-table']")));
	    Log.registrar("Tabela de integrações carregada");

	    List<WebElement> linhas = driver.findElements(By.xpath("//table[@id='pr_id_22-table']/tbody/tr"));

	    boolean encontrado = false;

	    for (int i = 1; i <= linhas.size(); i++) {
	        try {
	            WebElement linha = linhas.get(i - 1);
	            WebElement nomeColuna = linha.findElement(By.xpath("./td[1]"));
	            String txt = nomeColuna.getText();
	            Log.registrar("Nome da integração na linha " + i + ": " + txt);

	            if (txt.equals(nomeEsperado)) {
	                Log.registrar("Integração encontrada: " + txt);
	                WebElement botaoEditar = linha.findElement(By.xpath("./td[6]/div/button"));
	                botaoEditar.click();
	                Log.registrar("Clicando no botão de editar integração");
	                encontrado = true;
	                break;
	            }
	        } catch (NoSuchElementException e) {
	            Log.registrar("Elemento não encontrado na linha " + i + ": " + e.getMessage());
	        }
	    }

	    if (!encontrado) {
	        Log.registrar("Nenhuma integração foi encontrada com o nome: " + nomeEsperado);
	    }
	}
	
	

	
	// GRUPO

	/**
	 * Cria uma nova integração em grupo.
	 *
	 * @param tipo Tipo da integração.
	 * @param endpoint Endpoint da integração.
	 * @param token Token da integração.
	 * @param nomeIntegracao Nome da integração.
	 * @param variavel Variável adicional.
	 * @param caseLead Case lead a ser selecionado.
	 * @param equipeUsuario Equipe de usuário.
	 */
	public void criarIntegracao(String tipo, String endpoint, String token, String nomeIntegracao, String variavel, int caseLead, String equipeUsuario) {
		Log.registrar("Iniciando criação de nova integração");
		try {
			acessarConfig();
			acessarIntegracao();
			adicionarNovo("//button/span");
			selecionarTipoIntegracao(tipo);
			urlIntegracao(endpoint);
			tokenFornecedor(token);
			nomeIntegracao(nomeIntegracao);
			variaveis(variavel);
			selecionarLead(caseLead, equipeUsuario);
			
			Log.registrar("Criação de integração concluída");

		} catch (ElementNotInteractableException e) {
			Log.registrar("Erro ao interagir com o elemento: " + e.getMessage());
			Assert.fail("Teste falhou devido à ElementNotInteractableException: " + e.getMessage());

		} catch (WebDriverException e) {
			Log.registrar("Erro inesperado no WebDriver: " + e.getMessage());
			Assert.fail("Teste falhou devido a um erro no WebDriver: " + e.getMessage());
		}
	}

	/**
	 * Adiciona um template em grupo.
	 *
	 * @param procedimento Nome do procedimento.
	 * @param numero Número do disparo.
	 * @param template Nome do template.
	 */
	public void grupoAdicionarTemplate(String procedimento, String numero, String template) {
		Log.registrar("Iniciando adição de template em grupo");
		actions.esperar(1000);
		Log.registrar("Esperando 1 segundo");
		adicionarTemplate();
		Log.registrar("Esperando 1 segundo");
		procedimento(procedimento);
		numeroDisparo(numero);
		nomeTemplateOmnia(template);
		Log.registrar("Adição de template em grupo concluída");
	}

	/**
	 * Adiciona um agendador em grupo.
	 *
	 * @param descricao Descrição do agendador.
	 */
	public void adicionarAgendador(String descricao) {
		Log.registrar("Iniciando adição de agendador em grupo");

		adicionarNovo("//p-card[@id='agendadorTarefas']/div/div/div/div[2]/p");
		descricaoAgendador(descricao);
		selecionarTipoAlerta("ALERTAR_AGENDAMENTOS");
		Log.registrar("Adição de agendador em grupo concluída");
	}

	

}
