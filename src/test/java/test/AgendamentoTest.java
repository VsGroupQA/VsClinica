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
		Browser.fecharNavegador();
	}

	@Test
	public void criarNovoAgendamento() {
		agendamento.novoAgendamento(dia, horarioAtual, horarioMaisUmMinuto);

	}

	@Test
	public void agendamentoSemCamposObrigatorios() {
		actions.esperar(2000);
		agendamento.modalAgendamento();

		agendamento.criarAgendamentoDuplicado();
		
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
		agendamento.modalAgendamento();
		agendamento.procedimento(Access.procedimento);
		agendamento.profissional(Access.medico);
		agendamento.compromisso(Access.compromisso);

		agendamento.paciente(Access.paciente);
		agendamento.dataAgendamento(dia);

		agendamento.horaAgendamento(horarioAtual, horarioMaisUmMinuto);
		agendamento.observacao(horarioAtual, dia);
		agendamento.criarAgendamentoDuplicado();
	}


	public void agendamentoPelaLista() {
		
	}


	public void agendamentoPelaFicha() {
		
	}


	public void agendaBloqueada() {

	}
}