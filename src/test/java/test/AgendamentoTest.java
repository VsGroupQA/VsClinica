package test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import utils.Log;
import utils.Access;
import utils.Actions;
import utils.Browser;
import pages.AgendamentoPage;
import pages.loginPage;

public class AgendamentoTest {

	private static WebDriver driver;
	private loginPage loginPage;
	private Actions actions;
	private AgendamentoPage agendamento;

	static DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	static DateTimeFormatter horaFormatter = DateTimeFormatter.ofPattern("HH:mm");

	LocalDateTime agora = LocalDateTime.now();
	LocalDateTime horaMaisUmMinuto = agora.plusMinutes(1);

	String horarioAtual = agora.format(horaFormatter);
	String horarioMaisUmMinuto = horaMaisUmMinuto.format(horaFormatter);
	String dia = agora.format(dataFormatter);

	@BeforeAll
	public static void iniciarLog() {
		Log.criarArquivoLog("Log.Agendamento");
	}

	@AfterAll
	public static void encerrarLog() {
		Log.encerrarLog();
	}

	@BeforeEach
	public void iniciaDriver() {
		driver = Browser.iniciarNavegador(Access.navegador);
		driver.get(Access.url);
		loginPage = new loginPage(driver);
		loginPage.signIn(Access.usuario, Access.senha);
		actions = new Actions(driver);
		agendamento = new AgendamentoPage(driver);
	}

	@AfterEach
	public void encerrarDriver() {
//		Browser.fecharNavegador();
	}

	@Test
	public void criarNovoAgendamento() {
		agendamento.novoAgendamento(dia, horarioAtual, horarioMaisUmMinuto);

	}

	@Test
	public void agendamentoSemCamposObrigatorios() {

	}

	public void agendamentoEmMassa() {
		LocalDateTime agora = LocalDateTime.now();

		for (int i = 0; i < 4; i++) {
			LocalDateTime horarioAtualizado = agora.plusMinutes(i); // Adiciona i minutos a cada iteração

			String horarioAtual = horarioAtualizado.format(horaFormatter);
			String horarioMaisUmMinuto = horarioAtualizado.plusMinutes(1).format(horaFormatter);
			String dia = agora.format(dataFormatter);

			agendamento.novoAgendamento(dia, horarioAtual, horarioMaisUmMinuto);
		}
	}

	@Test
	public void agendamentoDuplicado() {
		agendamento.novoAgendamento(dia, horarioAtual, horarioMaisUmMinuto);

		actions.esperar(2000);
		agendamento.modalAgendamento("p-button.ng-star-inserted > button:nth-child(1)");
		agendamento.procedimento(Access.procedimento,
				"/html/body/div/div/div[2]/app-modal-agendamento/form/div[2]/div[1]/p-dropdown/div/div[2]",
				"/html/body/div[2]/div/div/div/ul/p-dropdownitem/li");
		agendamento.profissional(Access.medico, ".px-0 .p-dropdown-label",
				".p-element .p-dropdown-item");
		agendamento.compromisso(Access.compromisso, 
				"p-dropdown.ng-pristine:nth-child(2) > div:nth-child(1) > div:nth-child(3)",
				"p-dropdownitem.p-element > li"
				);
		agendamento.paciente(Access.paciente);
		agendamento.dataAgendamento(dia, "//*[@id=\"icon\"]");

		agendamento.horaAgendamento(horarioAtual, horarioMaisUmMinuto);
		agendamento.observacao(horarioAtual, dia, 
				"/html/body/div/div/div[2]/app-modal-agendamento/form/div[2]/div[10]/span/textarea");
		agendamento.botaoCriarAgendamento(false, 
				"/html/body/div/div/div[2]/app-modal-agendamento/form/div[3]/p-button/button");
	}
	
	@Test
	public void agendamentoPelaLista() {
		agendamento.navbar();
		agendamento.novoAgendamentoLista(dia, horarioAtual, horarioMaisUmMinuto);
	}

	public void agendamentoPelaFicha() {

	}

	public void agendaBloqueada() {

	}
}