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

    public void criarAgendamento() {
        actions.esperar(1000);
        agendamento.modalAgendamento();
        System.out.println("Acessar modal de criação");
        actions.esperar(100);
        agendamento.procedimento(Access.procedimento);
        System.out.println("procedimento");
        actions.esperar(100);
        agendamento.profissional(Access.medico);
        agendamento.compromisso(Access.compromisso);
        actions.esperar(100);
        agendamento.paciente(Access.paciente);
        agendamento.dataAgendamento(dia);
        actions.esperar(100);
        agendamento.horaAgendamento(horarioAtual,horarioMaisUmMinuto);
        agendamento.observacao(horarioAtual, dia);
        agendamento.criarAgendamento();
    }
    
    @Test
    public void agendamentoInicio() {
    	criarAgendamento();
    }

    @Test
    public void agendamentoSemCamposObrigatorios() {
        // Implementação para teste sem campos obrigatórios
    }

    @Test
    public void agendamentoEmMassa() {
        for (int i = 0; i < 100; i++) {
            criarAgendamento();
        }
    }

    @Test
    public void agendamentoDuplicado() {
    	criarAgendamento();

        actions.esperar(2000);
        agendamento.modalAgendamento();
        actions.esperar(800);
        agendamento.procedimento(Access.procedimento);
        actions.esperar(500);
        agendamento.profissional(Access.medico);
        agendamento.compromisso(Access.compromisso);
        actions.esperar(100);
        agendamento.paciente(Access.paciente);
        agendamento.dataAgendamento(dia);
        actions.esperar(100);
        agendamento.horaAgendamento(horarioAtual,horarioMaisUmMinuto);
        agendamento.observacao(horarioAtual, dia);
        agendamento.criarAgendamentoDuplicado();
    }

    @Test
    public void agendamentoPelaLista() {
        actions.esperar(500);
        agendamento.acessarListaAgendamentos();
        System.out.println("lista");
        criarAgendamento();
    }

    public void agendamentoPelaFicha() {
        // Implementação para agendamento pela ficha
    }
}
