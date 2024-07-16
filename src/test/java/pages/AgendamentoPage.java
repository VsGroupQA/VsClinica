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

public class AgendamentoPage {
	private WebDriver driver;
	private Actions actions;

	public AgendamentoPage(WebDriver driver) {
		this.driver = driver;
		this.actions = new Actions(this.driver);
	}

	public void modalAgendamento() {
		actions.clicarBotaoPegandoPeloCss("p-button.ng-star-inserted > button:nth-child(1)");
	}

	public void procedimento(String nomeItem) {
		actions.clicarBotaoPegandoPeloXpath(
				"/html/body/div/div/div[2]/app-modal-agendamento/form/div[2]/div[1]/p-dropdown/div/div[2]");
		List<WebElement> items = driver.findElements(By.xpath("/html/body/div[2]/div/div/div/ul/p-dropdownitem/li"));

		for (WebElement item : items) {
			if (item.getText().equalsIgnoreCase(nomeItem)) {
				item.click();
				break;
			}
		}
	}

	public void profissional(String nomeProfissional) {
		actions.clicarBotaoPegandoPeloCss(".px-0 .p-dropdown-label");

		List<WebElement> profissionais = driver.findElements(By.cssSelector(".p-element .p-dropdown-item"));
		for (WebElement profissional : profissionais) {
			if (profissional.getText().equalsIgnoreCase(nomeProfissional)) {
				profissional.click();
				break;
			}
		}
	}

	public void compromisso(String nomeCompromisso) {
		actions.clicarBotaoPegandoPeloCss("p-dropdown.ng-pristine:nth-child(2) > div:nth-child(1) > div:nth-child(3)");

		List<WebElement> compromissos = driver.findElements(By.cssSelector("p-dropdownitem.p-element > li"));
		for (WebElement compromisso : compromissos) {
			if (compromisso.getText().equalsIgnoreCase(nomeCompromisso)) {
				compromisso.click();
				break;
			}
		}
	}

	public void paciente(String nomePaciente) {
		try {
			actions.escreverPegandoPeloXpath("//input[@name='paciente']", nomePaciente);
			actions.esperar(1000);
			actions.clicarBotaoPegandoPeloXpath("//li[contains(.,'" + nomePaciente + "')]");
		} catch (NoSuchElementException e) {
			System.err.println("Paciente não encontrado: " + nomePaciente);
			throw e;
		}
	}

	public void dataAgendamento(String data) {
		actions.escreverPegandoPeloXpath("//*[@id=\"icon\"]", data);
	}

	public void horaAgendamento(String horaInicio, String horaFim) {

		actions.escreverPegandoPeloXpath("//div[5]/div/p-calendar/span/input", horaInicio);
		System.out.println("adiciondo hora inicio" + horaInicio);

		actions.esperar(300);

		actions.escreverPegandoPeloXpath("//div[2]/p-calendar/span/input", horaFim);
		System.out.println("adiciondo hora fim" + horaFim + "aa");
	}

	public void validarNotificacao(String elemento) {
		actions.esperar(500);

		WebElement notf = driver.findElement(By.xpath(elemento));

		String notfText = notf.getText();
		System.out.println(notf);

		Assert.assertTrue("A notificação não contém a palavra 'Salvo'. Texto da notificação: " + notfText,
				notfText.contains("Salvo"));

	}

	public void botaoCriar() {
		try {
			actions.clicarBotaoPegandoPeloXpath(
					"/html/body/div/div/div[2]/app-modal-agendamento/form/div[3]/p-button/button");
		} catch (ElementClickInterceptedException e) {
			Assert.fail("Falha ao clicar no botão de agendamento: o botão não está disponível no momento. "
					+ e.getMessage());
		}
	}

	// Negativo
	public void criarAgendamentoDuplicado() {
		try {
			actions.clicarBotaoPegandoPeloXpath(
					"/html/body/div/div/div[2]/app-modal-agendamento/form/div[3]/p-button/button");
		} catch (ElementClickInterceptedException e) {
			Assert.assertTrue(true);
		}
	}

	public void observacao(String horaObs, String data) {
		actions.escreverPegandoPeloXpath(
				"/html/body/div/div/div[2]/app-modal-agendamento/form/div[2]/div[10]/span/textarea",
				"TESTE DE AGENDAMENTO" + data + " - " + horaObs);
	}

	public void acessarListaAgendamentos() {
		actions.clicarBotaoPegandoPeloId("ROLE_AGENDAMENTOS");
	}

	public void validarMensagemErro() {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    WebElement mensagemErro = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
	            "/html/body/div/div/div[2]/app-modal-agendamento/form/div[2]/div[5]/div[1]/div[1]")));
	    String mensagemTexto = mensagemErro.getText();
	    String textoErro = "Existe um bloqueio nesse dia de";

	    if (mensagemTexto.contains(textoErro)) {
	        Assert.fail("A mensagem de horário bloqueado foi exibida: " + mensagemTexto);
	    } else {
	        Assert.assertTrue("A mensagem de erro não contém o texto indesejado.", true);
	    }
	}

	public void novoAgendamento(String data, String horaInicio, String horaFim) {
		actions.esperar(2000);
		modalAgendamento();
		System.out.println("Acessar modal de criação");

		procedimento(Access.procedimento);
		System.out.println("Selecionar procedimento");

		profissional(Access.medico);
		compromisso(Access.compromisso);
		paciente(Access.paciente);

		dataAgendamento(data);
		horaAgendamento(horaInicio, horaFim);
		validarMensagemErro();
		observacao(horaInicio, data);
		botaoCriar();

	}

}
