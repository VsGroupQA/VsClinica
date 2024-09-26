package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
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
	
	/**
	 * Método para acessar campo da navbar
	 * @param id O ID para localizar item da navbar
	 */
	public void navbar(String id) {
		actions.clicarBotaoPegandoPeloId(id);
		Log.registrar("Acessar navbar: "+ id +"");
	}

	/**
	 * Método para abrir modal de agendamento
	 * @param css O CSS para localizar botão
	 */
	public void modalAgendamento(String css) {
		actions.clicarBotaoPegandoPeloCss(css);
		Log.registrar("Abrir modal");
	}

	/**
     * Seleciona um procedimento pelo nome.
     *
     * @param nome Nome do procedimento a ser selecionado.
     * @param xpathDrop XPath para abrir o dropdown.
     * @param xpathItem XPath dos itens no dropdown.
     */
	public void procedimento(String nome, String xpathDrop, String xpathItem) {
		actions.clicarBotaoPegandoPeloXpath(xpathDrop);
		List<WebElement> items = driver.findElements(By.xpath(xpathItem));

		for (WebElement item : items) {
			if (item.getText().equalsIgnoreCase(nome)) {
				item.click();
				Log.registrar("Adicionar procedimento");
				break;
			}
		}
	}

	/**
     * Seleciona um profissional pelo nome.
     *
     * @param nome Nome do profissional / Medico.
     * @param cssDrop Seletor CSS para abrir o dropdown.
     * @param cssItem Seletor CSS dos itens no dropdown.
     */
	public void profissional(String nome, String cssDrop, String cssItem) {
		actions.clicarBotaoPegandoPeloCss(cssDrop);

		List<WebElement> profissionais = driver.findElements(By.cssSelector(cssItem));
		for (WebElement profissional : profissionais) {
			if (profissional.getText().equalsIgnoreCase(nome)) {
				profissional.click();
				Log.registrar("Adicionar profissional");
				break;
			}
		}
	}

	/**
     * Seleciona um compromisso pelo nome.
     *
     * @param nome Nome do compromisso.
     * @param cssDrop Seletor CSS para abrir o dropdown.
     * @param cssItem Seletor CSS dos itens no dropdown.
     */
	public void compromisso(String nome, String cssDrop, String cssItem) {
		actions.clicarBotaoPegandoPeloCss(cssDrop);

		List<WebElement> compromissos = driver.findElements(By.cssSelector(cssItem));
		for (WebElement compromisso : compromissos) {
			if (compromisso.getText().equalsIgnoreCase(nome)) {
				compromisso.click();
				Log.registrar("Adicionar compromisso");
				break;
			}
		}
	}

	/**
     * Pesquisa e seleciona um paciente pelo nome.
     *
     * @param nome Nome do paciente a ser pesquisado.
     */
	public void paciente(String nome) {
		try {
			actions.escreverPegandoPeloXpath("//input[@name='paciente']", nome);
			actions.clicarBotaoPegandoPeloXpath("//li[contains(.,'" + nome + "')]");
		} catch (NoSuchElementException e) {
			Log.registrar("Paciente não encontrado: " + nome);
			throw e;
		}
		Log.registrar("Adicionar paciente");
	}

	/**
     * Adiciona a data de agendamento.
     *
     * @param data Data do agendamento no formato dd/MM/yyyy.
     * @param xpathData XPath do campo de data.
     */
	public void dataAgendamento(String data, String xpathData) {
		actions.escreverPegandoPeloXpath(xpathData, data);
		Log.registrar("Adicionar data de agendamento:" + data);
	}

	/**
     * Adiciona os horários de início e fim do agendamento.
     *
     * @param horaInicio Hora de início.
     * @param horaFim Hora de término.
     * @param elementoHoraInicio XPath do campo de hora de início.
     * @param elementoHoraFim XPath do campo de hora de término.
     */
	public void horaAgendamento(String horaInicio, String horaFim, String xpathHoraInicio, String xpathHoraFim) {
		actions.escreverPegandoPeloXpath(xpathHoraInicio, horaInicio);
		Log.registrar("Horario de agendamento adicionado - Inicio: " + horaInicio);
		actions.escreverPegandoPeloXpath(xpathHoraFim, horaFim);
		Log.registrar("Horario de agendamento adicionado - Fim: " + horaFim);
	}

    /**
     * Adiciona uma observação ao agendamento.
     *
     * @param horaObs Horário da observação.
     * @param data Data relacionada à observação.
     * @param xpath XPath do campo de observação.
     */
	public void observacao(String horaObs, String data, String xpath) {
		actions.escreverPegandoPeloXpath(xpath, "TESTE AGENDAMENTO AUTOMATIZADO" + data + " - " + horaObs);
	}

	/**
     * Clica no botão para criar o agendamento, verificando se deve ser criação normal ou não.
     *
     * @param isCriacaoNormal Booleano que indica se é uma criação normal ou uma duplicada.
     * @param xpath XPath do botão de criação.
     */
	public void botaoCriarAgendamento(boolean isCriacaoNormal, String xpath) {
		try {
			actions.clicarBotaoPegandoPeloXpath(xpath);
			Log.registrar("Concluir agendamento");

			if (!isCriacaoNormal) {
				Assert.fail("O botão de criação foi clicado, mas não deveria permitir agendamento duplicado.");
				Log.registrar("O botão de criação foi clicado, mas não deveria permitir agendamento duplicado.");
			}
		} catch (ElementClickInterceptedException e) {
			if (isCriacaoNormal) {
				Assert.fail("Falha ao clicar no botão de agendamento: o botão não está disponível no momento. "
						+ e.getMessage());
				Log.registrar("Falha ao clicar no botão de agendamento: o botão não está disponível no momento. "
						+ e.getMessage());
			} else {
				Assert.assertTrue("Agendamento duplicado detectado corretamente.", true);
				Log.registrar("Agendamento duplicado detectado corretamente.");
			}
		}
	}

	public void pesquisarPaciente(String nomePaciente) {
		actions.escreverPegandoPeloXpath(
				"/html/body/app-root/div/app-pacientes/div/app-filtro-pacientes/p-card/div/div/div/div/div[3]/input",
				nomePaciente);
	}

	public void selecionarPaciente() {
		actions.esperar(500);
		actions.clicarBotaoPegandoPeloXpath(
				"/html/body/app-root/div/app-pacientes/div/p-table/div/div/table/tbody/tr/td[3]");
	}

//	public void navbar(String nomeId) {
//		actions.clicarBotaoPegandoPeloId(nomeId);
//	}

    /**
     * Método para validar a notificação de sucesso após criar um agendamento.
     */
	public void validarNotificacao() {
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 
	        WebElement notificacao = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".p-toast-detail")));
	        String textoNotificacao = notificacao.getText();
	        Assert.assertEquals("Agendamento cadastrado com sucesso.", textoNotificacao);
	        Log.registrar("Notificação de sucesso exibida: "+ textoNotificacao +"");
	        
	    } catch (NoSuchElementException e) {
	        Log.registrar("Falha ao encontrar a notificação de sucesso: " + e.getMessage());
	        Assert.fail("Falha ao encontrar a notificação de sucesso: " + e.getMessage());
	    }
	}
	
	public void pesquisarPaceinteAgendamento(String nome) {
		actions.escreverPegandoPeloName("nomePaciente", nome);
	}
	
	public void cancelarPrimeiroAgendamento() {
	    int tentativas = 0;

	    while (tentativas < 3) {
	        try {
	            // Tente localizar e clicar no botão
	            actions.clicarBotaoPegandoPeloCss("#pr_id_14-table > tbody > tr:nth-child(1) > td > div > p-card > div > div > div > div > div.grid.col-4.justify-content-end.align-items-center > div.btn-secondary > button");
	            break; // Sai do loop se a operação foi bem-sucedida
	        } catch (StaleElementReferenceException e) {
	            // Caso ocorra o erro, incrementa a contagem de tentativas e tenta novamente
	            tentativas++;
	            if (tentativas == 3) {
	                throw e; // Se atingir o número máximo de tentativas, lança a exceção novamente
	            }
	        }
	    }
	}


	
	public void acessarListaPaciente() {
		actions.clicarBotaoPegandoPeloId("ROLE_AGENDAMENTOS");
		actions.esperar(500);
	}
	
	public void excecaoAgenda() {
		actions.clicarBotaoPegandoPeloCss(".p-inputswitch-slider");
	}
	
	public void filtrarStatusPendente() {
		actions.clicarBotaoPegandoPeloCss(".col-1_5:nth-child(3) .p-dropdown-label");	
		actions.clicarBotaoPegandoPeloCss(".p-element:nth-child(1) > .p-dropdown-item > .ng-star-inserted");
	}
	
	public void statusPaciente () {
        
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
		observacao(horaInicio, data,"/html/body/div/div/div[2]/app-modal-agendamento/form/div[2]/div[10]/span/textarea");
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
		modalAgendamento("p-button.ng-star-inserted > button:nth-child(1)");
		actions.esperar(500);
		procedimento(Access.procedimento,"/html/body/app-root/div/app-agendamentos/p-dialog[1]/div/div/div[2]/app-modal-agendamento/form/div[2]/div[1]/p-dropdown/div/span","/html/body/div[1]/div/div/div/ul/p-dropdownitem/li");
		profissional(Access.medico, ".px-0 .p-dropdown-label", ".p-element .p-dropdown-item");
		compromisso(Access.compromisso, "p-dropdown.ng-pristine:nth-child(2) > div:nth-child(1) > div:nth-child(3)","p-dropdownitem.p-element > li");
		paciente(Access.paciente);
		dataAgendamento(data, "//div[4]/p-calendar/span/input");
		horaAgendamento(horaInicio, horaFim, "//div[5]/div/p-calendar/span/input", "//div[2]/p-calendar/span/input");
		observacao(horaInicio, data, "//textarea");
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
		modalAgendamento("button.p-ripple");
		actions.esperar(500);
		procedimento(Access.procedimento,"/html/body/app-root/div/app-detalhes-do-paciente/p-dialog[1]/div/div/div[2]/app-modal-agendamento/form/div[2]/div[1]/p-dropdown/div/span","/html/body/div[1]/div/div/div/ul/p-dropdownitem/li");
		profissional(Access.medico, ".px-0 .p-dropdown-label", ".p-element .p-dropdown-item");
		compromisso(Access.compromisso, "p-dropdown.ng-pristine:nth-child(2) > div:nth-child(1) > div:nth-child(3)","p-dropdownitem.p-element > li");
		dataAgendamento(data, "//div[4]/p-calendar/span/input");
		horaAgendamento(horaInicio, horaFim, "//div[5]/div/p-calendar/span/input", "//div[5]/div[2]/p-calendar/span/input");
		observacao(horaInicio, data, "//textarea");
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
		observacao(horaInicio, data,"/html/body/div/div/div[2]/app-modal-agendamento/form/div[2]/div[10]/span/textarea");
		botaoCriarAgendamento(statusBotao,"/html/body/div/div/div[2]/app-modal-agendamento/form/div[3]/p-button/button");
	}
	
	/**
	 * Adiciona um novo agendamento com exceção.
	 *
	 * @param data       Data do agendamento no formato "dd/MM/yyyy".
	 * @param horaInicio Hora de início do agendamento no formato "HH:mm".
	 * @param horaFim    Hora de fim do agendamento no formato "HH:mm".
	 * @par
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
		observacao(horaInicio, data,"/html/body/div/div/div[2]/app-modal-agendamento/form/div[2]/div[10]/span/textarea");
		botaoCriarAgendamento(statusBotao,"/html/body/div/div/div[2]/app-modal-agendamento/form/div[3]/p-button/button");
	}
	
	public void grupoAlterarStatus(String paciente) {
		acessarListaPaciente();
		pesquisarPaciente(paciente);
		filtrarStatusPendente();
		
	}
}
