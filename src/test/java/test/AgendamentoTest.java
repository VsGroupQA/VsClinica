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

    private static LocalDateTime agora = LocalDateTime.now();
    private static int incrementoMinutos = 0;

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
        Browser.fecharNavegador();
    }

    private String[] gerarHorariosAgendamento() {
        LocalDateTime horarioAtualizado = agora.plusMinutes(incrementoMinutos);
        incrementoMinutos += 2;

        String horarioAtual = horarioAtualizado.format(horaFormatter);
        String horarioMaisUmMinuto = horarioAtualizado.plusMinutes(1).format(horaFormatter);
        String dia = agora.format(dataFormatter);

        return new String[] { dia, horarioAtual, horarioMaisUmMinuto };
    }

    @Test
    public void criarNovoAgendamento() {
    	Log.registrar("TESTE - criar agendamento inicio");
        String[] horarios = gerarHorariosAgendamento();
        agendamento.novoAgendamento(horarios[0], horarios[1], horarios[2], true);
        agendamento.validarNotificacao();
    }

    @Test
    public void agendamentoDuplicado() {
    	Log.registrar("TESTE - criar agendamento inicio duplicado");
        String[] horarios = gerarHorariosAgendamento();
        agendamento.novoAgendamento(horarios[0], horarios[1], horarios[2], true);
        agendamento.validarNotificacao();
        agendamento.novoAgendamento(horarios[0], horarios[1], horarios[2], false);
    }

    @Test
    public void agendamentoPelaLista() {
    	Log.registrar("TESTE - criar agendamento pela lista");
        String[] horarios = gerarHorariosAgendamento();
        agendamento.navbar("ROLE_AGENDAMENTOS");
        agendamento.novoAgendamentoLista(horarios[0], horarios[1], horarios[2], true);
        agendamento.validarNotificacao();
    }

    @Test
    public void agendamentoPelaListaDuplicado() {
    	Log.registrar("TESTE - criar agendamento duplicado pela lista");
        String[] horarios = gerarHorariosAgendamento();
        agendamento.navbar("ROLE_AGENDAMENTOS");
        agendamento.novoAgendamentoLista(horarios[0], horarios[1], horarios[2], true);
        agendamento.validarNotificacao();
        agendamento.novoAgendamentoLista(horarios[0], horarios[1], horarios[2], false);
    }

    @Test
    public void agendamentoPelaFicha() {
    	Log.registrar("TESTE - criar agendamento pela ficha do paciente");
        String[] horarios = gerarHorariosAgendamento();
        agendamento.navbar("ROLE_PACIENTES");
        agendamento.pesquisarPaciente(Access.paciente);
        agendamento.selecionarPaciente();
        agendamento.novoAgendamentoFichaPaciente(horarios[0], horarios[1], horarios[2], true);
        agendamento.validarNotificacao();
    }

    @Test
    public void agendamentoPelaFichaDuplicado() {
    	Log.registrar("TESTE - criar agendamento duplicado pela ficha do paciente");
        String[] horarios = gerarHorariosAgendamento();
        agendamento.navbar("ROLE_PACIENTES");
        agendamento.pesquisarPaciente(Access.paciente);
        agendamento.selecionarPaciente();
        agendamento.novoAgendamentoFichaPaciente(horarios[0], horarios[1], horarios[2], true);
        agendamento.validarNotificacao();
        agendamento.novoAgendamentoFichaPaciente(horarios[0], horarios[1], horarios[2], false);
    }
    
    @Test
    public void agendamentoSemCamposObrigatorios() {
    	Log.registrar("TESTE - criar agendamento sem campos obrigatórios preenchidos");
        String[] horarios = gerarHorariosAgendamento();
        agendamento.novoAgendamentoSemCamposObg(horarios[0], horarios[1], horarios[2], false);
    }
    
    public void agendamentoEmMassa() {
    	Log.registrar("TESTE - criar agendamento em massa");
        for (int i = 0; i < 10; i++) { 
            String[] horarios = gerarHorariosAgendamento();
            agendamento.novoAgendamento(horarios[0], horarios[1], horarios[2], true);
        }
    }
}
