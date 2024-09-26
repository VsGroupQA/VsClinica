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
import pages.LoginPage;

public class AgendamentoTest {
    private static WebDriver driver;
    private LoginPage loginPage;
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
        loginPage = new LoginPage(driver);
        loginPage.signIn(Access.usuario, Access.senha);
        agendamento = new AgendamentoPage(driver);
    }

    @AfterEach
    public void encerrarDriver() {
//        Browser.fecharNavegador();
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
    	Log.registrar("TESTE REALIZADO - Criar agendamento");
        String[] horarios = gerarHorariosAgendamento();
        agendamento.criarAgendamento(horarios[0], horarios[1], horarios[2], true);
        agendamento.validarNotificacao();
    }

    @Test
    public void agendamentoDuplicado() {
    	Log.registrar("TESTE REALIZADO - criar agendamento inicio duplicado");
        String[] horarios = gerarHorariosAgendamento();
        agendamento.criarAgendamento(horarios[0], horarios[1], horarios[2], true);
        agendamento.validarNotificacao();
        agendamento.criarAgendamento(horarios[0], horarios[1], horarios[2], false);
    }

    @Test
    public void agendamentoPelaLista() {
    	Log.registrar("TESTE REALIZADO - criar agendamento pela lista");
        String[] horarios = gerarHorariosAgendamento();
        agendamento.navbar("ROLE_AGENDAMENTOS");
        agendamento.criarAgendamentoLista(horarios[0], horarios[1], horarios[2], true);
        agendamento.validarNotificacao();
    }

    @Test
    public void agendamentoPelaListaDuplicado() {
    	Log.registrar("TESTE REALIZADO - criar agendamento duplicado pela lista");
        String[] horarios = gerarHorariosAgendamento();
        agendamento.navbar("ROLE_AGENDAMENTOS");
        agendamento.criarAgendamentoLista(horarios[0], horarios[1], horarios[2], true);
        agendamento.validarNotificacao();
        agendamento.criarAgendamentoLista(horarios[0], horarios[1], horarios[2], false);
    }

    @Test
    public void agendamentoPelaFicha() {
    	Log.registrar("TESTE REALIZADO - criar agendamento pela ficha do paciente");
        String[] horarios = gerarHorariosAgendamento();
        agendamento.navbar("ROLE_PACIENTES");
        agendamento.pesquisarPaciente(Access.paciente);
        agendamento.selecionarPaciente();
        agendamento.criarAgendamentoFichaPaciente(horarios[0], horarios[1], horarios[2], true);
    }

    @Test
    public void agendamentoPelaFichaDuplicado() {
    	Log.registrar("TESTE REALIZADO - criar agendamento duplicado pela ficha do paciente");
        String[] horarios = gerarHorariosAgendamento();
        agendamento.navbar("ROLE_PACIENTES");
        agendamento.pesquisarPaciente(Access.paciente);
        agendamento.selecionarPaciente();
        agendamento.criarAgendamentoFichaPaciente(horarios[0], horarios[1], horarios[2], true);
        agendamento.criarAgendamentoFichaPaciente(horarios[0], horarios[1], horarios[2], false);
    }
    
    @Test
    public void agendamentoSemCamposObrigatorios() {
    	Log.registrar("TESTE REALIZADO - criar agendamento sem campos obrigatórios preenchidos");
        String[] horarios = gerarHorariosAgendamento();
        agendamento.criarAgendamentoSemCamposObg(horarios[0], horarios[1], horarios[2], false);
    }
    
    @Test
    public void criarAgendamentoComExcecaoBloqueio() {
    	Log.registrar("TESTE REALIZADO - criar agendamento com exceção no agendamento");
    	String[] horarios = gerarHorariosAgendamento();
        
        agendamento.criarAgendamento(horarios[0], horarios[1], horarios[2], true);
        agendamento.validarNotificacao();
        agendamento.criarAgendamentoExcecao(horarios[0], horarios[1], horarios[2], true);
        agendamento.validarNotificacao();
    }
    
    public void confirmarAgendamento() {
    	agendamento.navbar("ROLE_AGENDAMENTOS");
    	
    }
    
    public void cancelarAgendamento() {
    	
    }
    
    public void pacienteCompareceu() {
    	
    }
    
    // Criar varios agendamentos
    public void agendamentoEmMassa() {
    	Log.registrar("TESTE - criar agendamento em massa");
        for (int i = 0; i < 10; i++) { // Define quantos agendamentos será realizado
            String[] horarios = gerarHorariosAgendamento();
            agendamento.criarAgendamento(horarios[0], horarios[1], horarios[2], true);
        }
    }
} // CRIAR: CALCELAMENTO, CONFIRMAÇÃO, CHEGADA
