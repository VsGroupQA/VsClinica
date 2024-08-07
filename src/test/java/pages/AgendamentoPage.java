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
	
	public void acessarInicio () {
		actions.clicarBotaoPegandoPeloId("ROLE_DASHBOARD");
		actions.esperar(2000);
	}

	public void modalAgendamento(String css) {
		actions.clicarBotaoPegandoPeloCss(css);
		Log.registrar("Abrir modal");
	}

	public void procedimento(String nomeItem, String xpathDrop, String xpathItem) {
		actions.clicarBotaoPegandoPeloXpath(xpathDrop);
		List<WebElement> items = driver.findElements(By.xpath(xpathItem));

		for (WebElement item : items) {
			if (item.getText().equalsIgnoreCase(nomeItem)) {
				item.click();
				break;
			}
		}
		Log.registrar("Adicionar procedimento");
	}

	public void profissional(String nomeProfissional, String cssDrop, String cssItem) {
		actions.clicarBotaoPegandoPeloCss(cssDrop);

		List<WebElement> profissionais = driver.findElements(By.cssSelector(cssItem));
		for (WebElement profissional : profissionais) {
			if (profissional.getText().equalsIgnoreCase(nomeProfissional)) {
				profissional.click();
				break;
			}
		}

		Log.registrar("Adicionar profissional");
	}

	public void compromisso(String nomeCompromisso, String cssDrop, String cssItem) {
		actions.clicarBotaoPegandoPeloCss(cssDrop);

		List<WebElement> compromissos = driver.findElements(By.cssSelector(cssItem));
		for (WebElement compromisso : compromissos) {
			if (compromisso.getText().equalsIgnoreCase(nomeCompromisso)) {
				compromisso.click();
				break;
			}
		}

		Log.registrar("Adicionar compromisso");
	}

	public void paciente(String nomePaciente) {
		try {
			actions.escreverPegandoPeloXpath("//input[@name='paciente']", nomePaciente);
			actions.esperar(1000);
			actions.clicarBotaoPegandoPeloXpath("//li[contains(.,'" + nomePaciente + "')]");
		} catch (NoSuchElementException e) {
			Log.registrar("Paciente não encontrado: " + nomePaciente);
			throw e;
		}
		Log.registrar("Adicionar paciente");
	}

	public void dataAgendamento(String data, String xpathData) {
		actions.escreverPegandoPeloXpath(xpathData, data);
		Log.registrar("Adicionar data de agendamento:" + data + "");
	}

	public void horaAgendamento(String horaInicio, String horaFim, String elementoHoraInicio, String elementoHoraFim) {
		actions.escreverPegandoPeloXpath(elementoHoraInicio, horaInicio);
		Log.registrar("Adicionar horario de agendamento - Inicio: " + horaInicio + "");
		actions.esperar(300);
		actions.escreverPegandoPeloXpath(elementoHoraFim, horaFim);
		Log.registrar("Adicionar horario de agendamento - Fim: " + horaFim + "");
	}

	public void observacao(String horaObs, String data, String xpath) {
		actions.escreverPegandoPeloXpath(xpath, "TESTE DE AGENDAMENTO" + data + " - " + horaObs);
	}

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

	public void navbar(String nomeId) {
		actions.clicarBotaoPegandoPeloId(nomeId);
	}

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
		actions.clicarBotaoPegandoPeloXpath("//*[@id=\"pr_id_426-table\"]/tbody/tr[1]/td/div/p-card[1]/div/div/div/div/div[7]/div[3]/button/span");
	}
	
	public void acessarListaPaciente() {
		actions.clicarBotaoPegandoPeloId("ROLE_AGENDAMENTOS");
		actions.esperar(500);
	}
	
	// GRUPO

	public void grupoNovoAgendamento(String data, String horaInicio, String horaFim, Boolean statusBotao) {
		actions.esperar(2000);
		modalAgendamento("p-button.ng-star-inserted > button:nth-child(1)");
		procedimento(Access.procedimento,
				"/html/body/div/div/div[2]/app-modal-agendamento/form/div[2]/div[1]/p-dropdown/div/div[2]",
				"/html/body/div[2]/div/div/div/ul/p-dropdownitem/li");
		profissional(Access.medico, ".px-0 .p-dropdown-label", ".p-element .p-dropdown-item");
		// homologacao - modal
		//actions.clicarBotaoPegandoPeloXpath("/html/body/div[2]/div/div[4]/p-footer/button");
		
		compromisso(Access.compromisso, "p-dropdown.ng-pristine:nth-child(2) > div:nth-child(1) > div:nth-child(3)",
				"p-dropdownitem.p-element > li");
		paciente(Access.paciente);
		dataAgendamento(data, "//*[@id=\"icon\"]");
		horaAgendamento(horaInicio, horaFim, "//div[5]/div/p-calendar/span/input", "//div[2]/p-calendar/span/input");
		observacao(horaInicio, data,
				"/html/body/div/div/div[2]/app-modal-agendamento/form/div[2]/div[10]/span/textarea");
		botaoCriarAgendamento(statusBotao,
				"/html/body/div/div/div[2]/app-modal-agendamento/form/div[3]/p-button/button");
	}

	public void grupoNovoAgendamentoLista(String data, String horaInicio, String horaFim, Boolean statusBotao) {
		actions.esperar(2000);
		modalAgendamento("p-button.ng-star-inserted > button:nth-child(1)");
		procedimento(Access.procedimento,
				"/html/body/app-root/div/app-agendamentos/p-dialog[1]/div/div/div[2]/app-modal-agendamento/form/div[2]/div[1]/p-dropdown/div/span",
				"/html/body/div[1]/div/div/div/ul/p-dropdownitem/li");

		profissional(Access.medico, ".px-0 .p-dropdown-label", ".p-element .p-dropdown-item");
		compromisso(Access.compromisso, "p-dropdown.ng-pristine:nth-child(2) > div:nth-child(1) > div:nth-child(3)",
				"p-dropdownitem.p-element > li");
		paciente(Access.paciente);
		dataAgendamento(data, "//div[4]/p-calendar/span/input");
		horaAgendamento(horaInicio, horaFim, "//div[5]/div/p-calendar/span/input", "//div[2]/p-calendar/span/input");
		observacao(horaInicio, data, "//textarea");
		botaoCriarAgendamento(statusBotao,
				"/html/body/app-root/div/app-agendamentos/p-dialog[1]/div/div/div[2]/app-modal-agendamento/form/div[3]/p-button/button");

	}

	public void grupoNovoAgendamentoFichaPaciente(String data, String horaInicio, String horaFim, Boolean statusBotao) {
		actions.esperar(1000);
		modalAgendamento("button.p-ripple");
		actions.esperar(1000);
		procedimento(Access.procedimento,
				"/html/body/app-root/div/app-detalhes-do-paciente/p-dialog[1]/div/div/div[2]/app-modal-agendamento/form/div[2]/div[1]/p-dropdown/div/span",
				"/html/body/div[1]/div/div/div/ul/p-dropdownitem/li");

		profissional(Access.medico, ".px-0 .p-dropdown-label", ".p-element .p-dropdown-item");
		compromisso(Access.compromisso, "p-dropdown.ng-pristine:nth-child(2) > div:nth-child(1) > div:nth-child(3)",
				"p-dropdownitem.p-element > li");
		dataAgendamento(data, "//div[4]/p-calendar/span/input");
		horaAgendamento(horaInicio, horaFim, "//div[5]/div/p-calendar/span/input", "//div[5]/div[2]/p-calendar/span/input");
		observacao(horaInicio, data, "//textarea");
		botaoCriarAgendamento(statusBotao,
				"/html/body/app-root/div/app-detalhes-do-paciente/p-dialog[1]/div/div/div[2]/app-modal-agendamento/form/div[3]/p-button/button");

	}

	public void grupoNovoAgendamentoSemCamposObg(String data, String horaInicio, String horaFim, Boolean statusBotao) {
		actions.esperar(2000);
		modalAgendamento("p-button.ng-star-inserted > button:nth-child(1)");
		profissional(Access.medico, ".px-0 .p-dropdown-label", ".p-element .p-dropdown-item");
		paciente(Access.paciente);
		dataAgendamento(data, "//*[@id=\"icon\"]");
		horaAgendamento(horaInicio, horaFim, "//div[5]/div/p-calendar/span/input", "//div[2]/p-calendar/span/input");
		observacao(horaInicio, data,
				"/html/body/div/div/div[2]/app-modal-agendamento/form/div[2]/div[10]/span/textarea");
		botaoCriarAgendamento(statusBotao,
				"/html/body/div/div/div[2]/app-modal-agendamento/form/div[3]/p-button/button");
	}
}
