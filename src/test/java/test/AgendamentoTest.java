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
import pages.LoginPage;

public class AgendamentoTest {
    private static WebDriver driver;
    private LoginPage loginPage;
    private AgendamentoPage agendamento;
    private Actions actions;

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
        actions = new Actions(driver);
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
    	Log.registrar("TESTE REALIZADO - Criar agendamento");
        String[] horarios = gerarHorariosAgendamento();
        agendamento.criarAgendamento(horarios[0], horarios[1], horarios[2], true);
        agendamento.validarNotificacao("Agendamento cadastrado com sucesso.");
    }

    @Test
    public void agendamentoDuplicado() {
    	Log.registrar("TESTE REALIZADO - Criar agendamento duplicado");
        String[] horarios = gerarHorariosAgendamento();
        agendamento.criarAgendamento(horarios[0], horarios[1], horarios[2], true);
        agendamento.validarNotificacao("Agendamento cadastrado com sucesso.");
        agendamento.criarAgendamento(horarios[0], horarios[1], horarios[2], false);
    }

    @Test
    public void agendamentoPelaLista() {
    	Log.registrar("TESTE REALIZADO - Criar agendamento - Lista");
        String[] horarios = gerarHorariosAgendamento();
        agendamento.navbar("ROLE_AGENDAMENTOS");
        agendamento.criarAgendamentoLista(horarios[0], horarios[1], horarios[2], true);
        agendamento.validarNotificacao("Agendamento cadastrado com sucesso.");
    }

    @Test
    public void agendamentoPelaListaDuplicado() {
    	Log.registrar("TESTE REALIZADO - Criar agendamento duplicado - Lista");
        String[] horarios = gerarHorariosAgendamento();
        agendamento.navbar("ROLE_AGENDAMENTOS");
        agendamento.criarAgendamentoLista(horarios[0], horarios[1], horarios[2], true);
        agendamento.validarNotificacao("Agendamento cadastrado com sucesso.");
        agendamento.criarAgendamentoLista(horarios[0], horarios[1], horarios[2], false);
    }

    @Test
    public void agendamentoPelaFicha() {
    	Log.registrar("TESTE REALIZADO - Criar agendamento - Ficha do paciente");
        String[] horarios = gerarHorariosAgendamento();
        agendamento.navbar("ROLE_PACIENTES");
        agendamento.pesquisarPaciente(Access.paciente);
        agendamento.selecionarPaciente();
        agendamento.criarAgendamentoFichaPaciente(horarios[0], horarios[1], horarios[2], true);
    }

    @Test
    public void agendamentoPelaFichaDuplicado() {
    	Log.registrar("TESTE REALIZADO - Criar agendamento duplicado - Ficha do paciente");
        String[] horarios = gerarHorariosAgendamento();
        agendamento.navbar("ROLE_PACIENTES");
        agendamento.pesquisarPaciente(Access.paciente);
        agendamento.selecionarPaciente();
        agendamento.criarAgendamentoFichaPaciente(horarios[0], horarios[1], horarios[2], true);
        actions.esperar(3000);
        agendamento.criarAgendamentoFichaPaciente(horarios[0], horarios[1], horarios[2], false);
    }
    
    @Test
    public void agendamentoSemCamposObrigatorios() {
    	Log.registrar("TESTE REALIZADO - Criar agendamento sem campos obrigatórios");
        String[] horarios = gerarHorariosAgendamento();
        agendamento.criarAgendamentoSemCamposObg(horarios[0], horarios[1], horarios[2], false);
    }
    
    @Test
    public void criarAgendamentoComExcecaoBloqueio() {
    	Log.registrar("TESTE REALIZADO - Criar agendamento - Com botão EXCEÇÃO");
    	String[] horarios = gerarHorariosAgendamento();
        
        agendamento.criarAgendamento(horarios[0], horarios[1], horarios[2], true);
        agendamento.validarNotificacao("Agendamento cadastrado com sucesso.");
        agendamento.criarAgendamentoExcecao(horarios[0], horarios[1], horarios[2], true);
        agendamento.validarNotificacao("Agendamento cadastrado com sucesso.");
    }
    
    @Test
    public void confirmarAgendamento() {
    	Log.registrar("TESTE REALIZADO - Confirmar agendamento");
    	String[] horarios = gerarHorariosAgendamento();
    	
    	agendamento.criarAgendamentoExcecao(horarios[0], horarios[1], horarios[2], true);
        agendamento.validarNotificacao("Agendamento cadastrado com sucesso.");
        actions.esperar(1500);
    	agendamento.alterarStatus(Access.paciente, 1);
    	agendamento.validarNotificacao("Agendamento confirmado com sucesso.");
    }
    
    @Test
    public void cancelarAgendamento() {
    	Log.registrar("TESTE REALIZADO - Cancelar agendamento");
    	String[] horarios = gerarHorariosAgendamento();
    	
    	agendamento.criarAgendamentoExcecao(horarios[0], horarios[1], horarios[2], true);
        agendamento.validarNotificacao("Agendamento cadastrado com sucesso.");
        actions.esperar(1500);
    	agendamento.alterarStatus(Access.paciente, 2);
    	agendamento.validarNotificacao("Agendamento cancelado com sucesso.");
    }
    
    @Test
    public void pacienteChegou() {
    	Log.registrar("TESTE REALIZADO - Paciente chegou?");
    	String[] horarios = gerarHorariosAgendamento();
    	
    	agendamento.criarAgendamentoExcecao(horarios[0], horarios[1], horarios[2], true);
        agendamento.validarNotificacao("Agendamento cadastrado com sucesso.");
        actions.esperar(1500);
    	agendamento.alterarStatus(Access.paciente, 0);
    	agendamento.validarNotificacao("Paciente chegou.");
    }
    
    // Criar varios agendamentos
    public void agendamentoEmMassa() {
    	Log.registrar("TESTE - !! CRIAR AGENDAMENTO EM MASSA !!");
        for (int i = 0; i < 10; i++) { // Define quantos agendamentos será realizado
            String[] horarios = gerarHorariosAgendamento();
            agendamento.criarAgendamento(horarios[0], horarios[1], horarios[2], true);
        }
    }
}
