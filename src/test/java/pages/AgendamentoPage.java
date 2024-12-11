package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.Access;
import utils.Actions;

import java.time.Duration;
import java.util.List;
import utils.Log;

public class AgendamentoPage {
	private WebDriver driver;
	private Actions actions;

	public AgendamentoPage(WebDriver driver) {
		this.driver = driver;
		this.actions = new Actions(this.driver);
	}

	
	// COMPONENTES
	
	public void navbar(String id) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement elemento = wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
		elemento.click();
		Log.registrar("Acessar navbar: "+ id +"");
	}

	public void modalAgendamento(String css) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement elemento = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(css)));
		actions.esperar(500);
		elemento.click();
		Log.registrar("Abrindo modal");
	}

	public void procedimento(String nome, String xpathDrop, String xpathItem) {
		Log.registrar("Abrindo dropdown de procedimento");
		
		actions.clicarBotaoPegandoPeloXpath(xpathDrop);
		List<WebElement> items = driver.findElements(By.xpath(xpathItem));

		for (WebElement item : items) {
			if (item.getText().equalsIgnoreCase(nome)) {
				item.click();
				Log.registrar("Adicionar procedimento: "+ nome +"");
				break;
			}
		}
	}


	public void profissional(String nome, String cssDrop, String cssItem) {
		Log.registrar("Abrindo dropdown de profissional");
		
		actions.clicarBotaoPegandoPeloCss(cssDrop);
		List<WebElement> profissionais = driver.findElements(By.cssSelector(cssItem));
		for (WebElement profissional : profissionais) {
			if (profissional.getText().equalsIgnoreCase(nome)) {
				profissional.click();
				Log.registrar("Adicionar profissional: "+ nome +"");
				break;
			}
		}
	}

	public void compromisso(String nome, String cssDrop, String cssItem) {
		Log.registrar("Abrindo dropdown de compromisso");
		
		actions.clicarBotaoPegandoPeloCss(cssDrop);
		List<WebElement> compromissos = driver.findElements(By.cssSelector(cssItem));
		for (WebElement compromisso : compromissos) {
			if (compromisso.getText().equalsIgnoreCase(nome)) {
				compromisso.click();
				Log.registrar("Adicionar compromisso: "+ nome +"");
				break;
			}
		}
	}

	public void paciente(String nome) {
		try {
			actions.escreverPegandoPeloXpath("//input[@name='paciente']", nome);
			actions.clicarBotaoPegandoPeloXpath("//li[contains(.,'" + nome + "')]");
			Log.registrar("Pacinete encontrado: "+ nome +"");
			
		} catch (NoSuchElementException e) {
			Log.registrar("Paciente não encontrado: " + nome);
			throw e;
		}
	}

	
	public void dataAgendamento(String data, String xpathData) {
		actions.escreverPegandoPeloXpath(xpathData, data);
		Log.registrar("Adicionar data de agendamento:" + data);
	}


	public void horaAgendamento(String horaInicio, String horaFim, String xpathHoraInicio, String xpathHoraFim) {
		actions.escreverPegandoPeloXpath(xpathHoraInicio, horaInicio);
		Log.registrar("Horario de agendamento adicionado - Inicio: " + horaInicio);
		actions.escreverPegandoPeloXpath(xpathHoraFim, horaFim);
		Log.registrar("Horario de agendamento adicionado - Fim: " + horaFim);
	}


	public void observacao(String horaObs, String data) {
		actions.escreverPegandoPeloXpath("/html/body/div/div/div[2]/app-modal-agendamento/form/div[2]/div[10]/span/textarea", "TESTE AGENDAMENTO AUTOMATIZADO" + data + " - " + horaObs);
	}
	
	public void observacaoFichaPaciete (String horaObs, String data) {
		actions.escreverPegandoPeloXpath("/html/body/app-root/div/app-detalhes-do-paciente/p-dialog[1]/div/div/div[2]/app-modal-agendamento/form/div[2]/div[10]/span/textarea", "TESTE AGENDAMENTO AUTOMATIZADO" + data + " - " + horaObs);
	}
	
	public void observacaoLista(String horaObs, String data) {
		actions.escreverPegandoPeloXpath("//textarea", "TESTE AGENDAMENTO AUTOMATIZADO" + data + " - " + horaObs);
	}
	
	public void botaoCriarAgendamento(boolean ehCriacaoNormal, String xpath) {
	    try {
	        // Tenta clicar no botão de criação
	        actions.clicarBtnXpathSemTratamentoErro(xpath);
	        Log.registrar("Clicar botão de criação");

	        // Verifica se o fluxo é de criação duplicada e falha o teste se o botão foi clicado
	        if (!ehCriacaoNormal) {

	            Assert.fail("O botão de criação foi clicado, mas não deveria permitir agendamento duplicado.");
	            Log.registrar("O botão de criação foi clicado, mas não deveria permitir agendamento duplicado.");
	        }
	    } catch (ElementClickInterceptedException e) {
	        // Se o clique falhou, verifica se é um caso de criação duplicada
	        if (!ehCriacaoNormal) {

	            Assert.assertTrue("Agendamento duplicado detectado corretamente.", true);
	            Log.registrar("Agendamento duplicado detectado corretamente.");
	        } else {
	            // Se for criação normal, o teste falha, pois o clique deveria funcionar
	            Assert.fail("Falha ao clicar no botão de agendamento: o botão não está disponível no momento. " + e.getMessage());
	            Log.registrar("Falha ao clicar no botão de agendamento: o botão não está disponível no momento. " + e.getMessage());
	        }
	    }
	}


	public void pesquisarPaciente(String nomePaciente) {
		actions.esperar(300);
		actions.escreverPegandoPeloName("nomePaciente", nomePaciente);
	}


	public void selecionarPaciente() {
		actions.esperar(500);
		actions.clicarBotaoPegandoPeloXpath("/html/body/app-root/div/app-pacientes/div/p-table/div/div/table/tbody/tr/td[3]");
	}

	
	public void pesquisarPaceinteAgendamento(String nome) {
		actions.escreverPegandoPeloName("nomePaciente", nome);
		Log.registrar("Pesquisando paciente: "+ nome +"");
	}
	

	public void excecaoAgenda() {
		actions.clicarBotaoPegandoPeloCss(".p-inputswitch-slider");
		Log.registrar("Ativar botão de EXCEÇÃO");
	}
	

	public void filtrarStatusAgendamento(String status) {
		Log.registrar("Abrir dropdown de agendamento");
		actions.esperar(500);
		actions.clicarBotaoPegandoPeloXpath("//div[3]/p-dropdown/div/div[2]");

	    // Listar todas as opções do dropdown
	    List<WebElement> items = driver.findElements(By.xpath("//ul[@role='listbox']//li[@role='option']"));

	    // Loop para encontrar e clicar na opção correta
	    for (WebElement item : items) {
	        if (item.getText().equalsIgnoreCase(status)) { // Comparar com o status passado como parâmetro
	            item.click(); // Clicar na opção correspondente
	            Log.registrar("Selecionado o status: " + status); // Registrar a ação no log
	            break;
	        }
	    }
	    Log.registrar("Dropdown de status finalizado");
	}


	public void acaoListaAgendamento (int acao) {
		actions.esperar(500);
		switch (acao) {
		
		case 0: // PACIENTE CHEGOU
			actions.clicarBotaoPegandoPeloXpath("//p-togglebutton/div/span");
			Log.registrar("Ação para 'Paciente chegou'");
			break;
			
		case 1: // CONFIRMADO
			actions.clicarBotaoPegandoPeloXpath("//div[2]/button/span");
			Log.registrar("Ação para confirmar agendamento");
			break;
			
		case 2: // CANCELADO
			actions.clicarBotaoPegandoPeloXpath("//div[3]/button/span");
			actions.esperar(500);
			// escrever motivo de cancelamento
			actions.escreverPegandoPeloXpath("(//input[@type='text'])[5]",
					"TESTE DE CANCELAMENTO");
			actions.clicarBotaoPegandoPeloCss(".col-6 .p-button-label");
			Log.registrar("Ação para cancelar agendamento");
			break;
		}
	}
	
	
	// GRUPO

	/**
	 * Adiciona um agendamento no sistema.
	 *
	 * @param data       Data do agendamento no formato "dd/MM/yyyy".
	 * @param horaInicio Hora de início do agendamento no formato "HH:mm".
	 * @param horaFim    Hora de fim do agendamento no formato "HH:mm".
	 * @param statusBotao Estado do botão de confirmação (true para clicar, false para não clicar).
	 */
	public void criarAgendamento(String data, String horaInicio, String horaFim, Boolean statusBotao) {
		modalAgendamento("p-button.ng-star-inserted > button:nth-child(1)");
		actions.esperar(500);
		procedimento(Access.procedimento,"/html/body/div/div/div[2]/app-modal-agendamento/form/div[2]/div[1]/p-dropdown/div/div[2]","/html/body/div[2]/div/div/div/ul/p-dropdownitem/li");
		profissional(Access.medico, ".px-0 .p-dropdown-label", ".p-element .p-dropdown-item");
		compromisso(Access.compromisso, "p-dropdown.ng-pristine:nth-child(2) > div:nth-child(1) > div:nth-child(3)","p-dropdownitem.p-element > li");
		paciente(Access.paciente);
		dataAgendamento(data, "//*[@id=\"icon\"]");
		horaAgendamento(horaInicio, horaFim, "//div[5]/div/p-calendar/span/input", "//div[2]/p-calendar/span/input");
		observacao(horaInicio, data);
		botaoCriarAgendamento(statusBotao,"/html/body/div/div/div[2]/app-modal-agendamento/form/div[3]/p-button/button");
	}

	/**
	 * Adiciona um agendamento na lista de agendamentos.
	 *
	 * @param data       Data do agendamento no formato "dd/MM/yyyy".
	 * @param horaInicio Hora de início do agendamento no formato "HH:mm".
	 * @param horaFim    Hora de fim do agendamento no formato "HH:mm".
	 * @param statusBotao Estado do botão de confirmação (true para clicar, false para não clicar).
	 */
	public void criarAgendamentoLista(String data, String horaInicio, String horaFim, Boolean statusBotao) {
		navbar("ROLE_AGENDAMENTOS");
		actions.esperar(1000);
		modalAgendamento("p-button.ng-star-inserted > button:nth-child(1)");
		actions.esperar(1000);
		procedimento(Access.procedimento,"/html/body/app-root/div/app-agendamentos/p-dialog[1]/div/div/div[2]/app-modal-agendamento/form/div[2]/div[1]/p-dropdown/div/span","/html/body/div[1]/div/div/div/ul/p-dropdownitem/li");
		profissional(Access.medico, ".px-0 .p-dropdown-label", ".p-element .p-dropdown-item");
		compromisso(Access.compromisso, "p-dropdown.ng-pristine:nth-child(2) > div:nth-child(1) > div:nth-child(3)","p-dropdownitem.p-element > li");
		paciente(Access.paciente);
		dataAgendamento(data, "//div[4]/p-calendar/span/input");
		horaAgendamento(horaInicio, horaFim, "//div[5]/div/p-calendar/span/input", "//div[2]/p-calendar/span/input");
		observacaoLista(horaInicio, data);
		botaoCriarAgendamento(statusBotao,"/html/body/app-root/div/app-agendamentos/p-dialog[1]/div/div/div[2]/app-modal-agendamento/form/div[3]/p-button/button");
	}

	/**
	 * Adiciona um agendamento na ficha do paciente.
	 *
	 * @param data       Data do agendamento no formato "dd/MM/yyyy".
	 * @param horaInicio Hora de início do agendamento no formato "HH:mm".
	 * @param horaFim    Hora de fim do agendamento no formato "HH:mm".
	 * @param statusBotao Estado do botão de confirmação (true para clicar, false para não clicar).
	 */
	public void criarAgendamentoFichaPaciente(String data, String horaInicio, String horaFim, Boolean statusBotao) {
		modalAgendamento("#btn-estudoCaso > button");
		actions.esperar(600);
		procedimento(Access.procedimento,"/html/body/app-root/div/app-detalhes-do-paciente/p-dialog[1]/div/div/div[2]/app-modal-agendamento/form/div[2]/div[1]/p-dropdown/div/span","/html/body/div[1]/div/div/div/ul/p-dropdownitem/li");
		profissional(Access.medico, ".px-0 .p-dropdown-label", ".p-element .p-dropdown-item");
		compromisso(Access.compromisso, "p-dropdown.ng-pristine:nth-child(2) > div:nth-child(1) > div:nth-child(3)","p-dropdownitem.p-element > li");
		dataAgendamento(data, "//div[4]/p-calendar/span/input");
		horaAgendamento(horaInicio, horaFim, "//div[5]/div/p-calendar/span/input", "//div[5]/div[2]/p-calendar/span/input");
		observacaoFichaPaciete(horaInicio, data);
		actions.esperar(500);
		botaoCriarAgendamento(statusBotao,"/html/body/app-root/div/app-detalhes-do-paciente/p-dialog[1]/div/div/div[2]/app-modal-agendamento/form/div[3]/p-button/button");
	}

	/**
	 * Adiciona um agendamento com campos obrigatórios mínimos.
	 *
	 * @param data       Data do agendamento no formato "dd/MM/yyyy".
	 * @param horaInicio Hora de início do agendamento no formato "HH:mm".
	 * @param horaFim    Hora de fim do agendamento no formato "HH:mm".
	 * @param statusBotao Estado do botão de confirmação (true para clicar, false para não clicar).
	 */
	public void criarAgendamentoSemCamposObg(String data, String horaInicio, String horaFim, Boolean statusBotao) {
		modalAgendamento("p-button.ng-star-inserted > button:nth-child(1)");
		actions.esperar(500);
		profissional(Access.medico, ".px-0 .p-dropdown-label", ".p-element .p-dropdown-item");
		paciente(Access.paciente);
		dataAgendamento(data, "//*[@id=\"icon\"]");
		horaAgendamento(horaInicio, horaFim, "//div[5]/div/p-calendar/span/input", "//div[2]/p-calendar/span/input");
		observacao(horaInicio, data);
		botaoCriarAgendamento(statusBotao,"/html/body/div/div/div[2]/app-modal-agendamento/form/div[3]/p-button/button");
	}
	
	/**
	 * Adiciona um novo agendamento com exceção.
	 *
	 * @param data       Data do agendamento no formato "dd/MM/yyyy".
	 * @param horaInicio Hora de início do agendamento no formato "HH:mm".
	 * @param horaFim    Hora de fim do agendamento no formato "HH:mm".
	 */
	public void criarAgendamentoExcecao(String data, String horaInicio, String horaFim, Boolean statusBotao) {
		modalAgendamento("p-button.ng-star-inserted > button:nth-child(1)");
		actions.esperar(500);
		procedimento(Access.procedimento,"/html/body/div/div/div[2]/app-modal-agendamento/form/div[2]/div[1]/p-dropdown/div/div[2]","/html/body/div[2]/div/div/div/ul/p-dropdownitem/li");
		profissional(Access.medico, ".px-0 .p-dropdown-label", ".p-element .p-dropdown-item");
		// Botão Execeção
		excecaoAgenda();
		compromisso(Access.compromisso, "p-dropdown.ng-pristine:nth-child(2) > div:nth-child(1) > div:nth-child(3)","p-dropdownitem.p-element > li");
		paciente(Access.paciente);
		dataAgendamento(data, "//*[@id=\"icon\"]");
		horaAgendamento(horaInicio, horaFim, "//div[5]/div/p-calendar/span/input", "//div[2]/p-calendar/span/input");
		observacao(horaInicio, data);
		Log.registrar("Botao");
		botaoCriarAgendamento(statusBotao,"/html/body/div/div/div[2]/app-modal-agendamento/form/div[3]/p-button/button");
	}
	
	/**
	 * Alterar stats do agendamento
	 *
	 * @param paciente       Nome do paciente
	 * @param acao Seleciona a ação no agendamento confirmar, cancelar, chegou?
	 */
	public void alterarStatus(String paciente, int acao) {
    	navbar("ROLE_AGENDAMENTOS");
    	filtrarStatusAgendamento("Pendente");
        pesquisarPaciente(paciente);
        acaoListaAgendamento(acao);
	}
}
