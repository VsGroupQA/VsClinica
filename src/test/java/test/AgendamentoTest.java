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
import utils.Browser;
import pages.AgendamentoPage;
import pages.loginPage;

public class AgendamentoTest {

	private static WebDriver driver;
	private loginPage loginPage;
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
		agendamento = new AgendamentoPage(driver);
	}

	@AfterEach
	public void encerrarDriver() {
//		Browser.fecharNavegador();
	}

	@Test
	public void criarNovoAgendamento() {
		agendamento.novoAgendamento(dia, horarioAtual, horarioMaisUmMinuto, true);

	}

	@Test
	public void agendamentoSemCamposObrigatorios() {

	}
	
	// EM MASSA
	public void agendamentoEmMassa() {
		LocalDateTime agora = LocalDateTime.now();

		for (int i = 0; i < 4; i++) {
			LocalDateTime horarioAtualizado = agora.plusMinutes(i); // Adiciona i minutos a cada iteração

			String horarioAtual = horarioAtualizado.format(horaFormatter);
			String horarioMaisUmMinuto = horarioAtualizado.plusMinutes(1).format(horaFormatter);
			String dia = agora.format(dataFormatter);

			agendamento.novoAgendamento(dia, horarioAtual, horarioMaisUmMinuto, true);
		}
	}

	@Test
	public void agendamentoDuplicado() {
		agendamento.novoAgendamento(dia, horarioAtual, horarioMaisUmMinuto, true);
		agendamento.novoAgendamento(dia, horarioAtual, horarioMaisUmMinuto, false);
	}
	
	@Test
	public void agendamentoPelaLista() {
		agendamento.navbar("ROLE_AGENDAMENTOS");
		agendamento.novoAgendamentoLista(dia, horarioAtual, horarioMaisUmMinuto, true);
	}
	
	@Test
	public void agendamentoPelaListaDuplicado() {
		agendamento.navbar("ROLE_AGENDAMENTOS");
		agendamento.novoAgendamentoLista(dia, horarioAtual, horarioMaisUmMinuto, true);
		agendamento.novoAgendamentoLista(dia, horarioAtual, horarioMaisUmMinuto, false);
	}
	
	@Test
	public void agendamentoPelaFicha() {
		agendamento.navbar("ROLE_PACIENTES");
		agendamento.pesquisarPaciente(Access.paciente);
		agendamento.selecionarPaciente();
		agendamento.novoAgendamentoFichaPaciente(dia, horarioAtual, horarioMaisUmMinuto, true);
	}

	public void agendaBloqueada() {

	}
}