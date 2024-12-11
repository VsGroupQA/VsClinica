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
        driver = Browser.iniciarNavegador(Access.navegador, Access.headless);
        driver.get(Access.url);
        loginPage = new LoginPage(driver);
        loginPage.signIn(Access.usuario, Access.senha);
        agendamento = new AgendamentoPage(driver);
        actions = new Actions(driver);
    }

    @AfterEach
    public void encerrarDriver() {
        Browser.fecharNavegador(Access.quit);
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
    	Log.registrar("===== TESTE REALIZADO ===== - CRIAR NOVO AGENDAMENTO");
        String[] horarios = gerarHorariosAgendamento();
        // Criar
        agendamento.criarAgendamento(horarios[0], horarios[1], horarios[2], true);
        // Validar
        actions.validarNotificacao("Agendamento cadastrado com sucesso.");
    }

    @Test
    public void agendamentoDuplicado() {
    	Log.registrar("===== TESTE REALIZADO ===== - CRIAR AGENDAMENTO DUPLICADO");
        String[] horarios = gerarHorariosAgendamento();
        // Criar
        agendamento.criarAgendamento(horarios[0], horarios[1], horarios[2], true);
        // Validar
        actions.validarNotificacao("Agendamento cadastrado com sucesso.");
        // Criar Duplicado
        agendamento.criarAgendamento(horarios[0], horarios[1], horarios[2], false);
    }

    @Test
    public void agendamentoPelaLista() {
    	Log.registrar("===== TESTE REALIZADO ===== - CRIAR NOVO AGENDAMENTO LISTA");
        String[] horarios = gerarHorariosAgendamento();
        // Criar
        agendamento.criarAgendamentoLista(horarios[0], horarios[1], horarios[2], true);
        // Validar
        actions.validarNotificacao("Agendamento cadastrado com sucesso.");
    }

    @Test
    public void agendamentoPelaListaDuplicado() {
    	Log.registrar("===== TESTE REALIZADO ===== - CRIAR AGENDAMENTO DUPLICADO PELA LISTA");
        String[] horarios = gerarHorariosAgendamento();
        // Criar
        agendamento.criarAgendamentoLista(horarios[0], horarios[1], horarios[2], true);
        // Validar
        actions.validarNotificacao("Agendamento cadastrado com sucesso.");
        // Criar Duplicado
        agendamento.criarAgendamentoLista(horarios[0], horarios[1], horarios[2], false);
    }

//    @Test
    public void agendamentoPelaFicha() {
    	// Funcionando, porém recarregando página
    	Log.registrar("===== TESTE REALIZADO ===== - CRIAR AGENDAMENTO PELA FICHA");
        String[] horarios = gerarHorariosAgendamento();
        
        agendamento.navbar("ROLE_PACIENTES");
        agendamento.pesquisarPaciente(Access.paciente);
        agendamento.selecionarPaciente();
       
        agendamento.criarAgendamentoFichaPaciente(horarios[0], horarios[1], horarios[2], true);
       
        actions.validarNotificacao("Agendamento cadastrado com sucesso.");
    }

    @Test
    public void agendamentoPelaFichaDuplicado() {
    	Log.registrar("===== TESTE REALIZADO ===== - CRIAR AGENDAMENTO PELA FICHA DUPLICADO");
        String[] horarios = gerarHorariosAgendamento();
        // Acessar
        agendamento.navbar("ROLE_PACIENTES");
        agendamento.pesquisarPaciente(Access.paciente);
        agendamento.selecionarPaciente();
        // Criar
        agendamento.criarAgendamentoFichaPaciente(horarios[0], horarios[1], horarios[2], true);
        actions.esperar(2000);
        agendamento.criarAgendamentoFichaPaciente(horarios[0], horarios[1], horarios[2], false);
    }
    
    @Test
    public void agendamentoSemCamposObrigatorios() {
    	Log.registrar("===== TESTE REALIZADO ===== - CRIAR AGENDAMENTO SEM CAMPOS OBGS PREENCHIDOS");
        String[] horarios = gerarHorariosAgendamento();
        // Criar
        agendamento.criarAgendamentoSemCamposObg(horarios[0], horarios[1], horarios[2], false);
    }
    
    //////////////////////////
    
    public void criarAgendamentoComExcecaoBloqueio() {
    	Log.registrar("===== TESTE REALIZADO ===== - CRIAR AGENDAMENTO COM EXCEÇÃO DE BLOQUEIO");
    	Log.registrar("**Necessário que o login utilizado tenha permissão de 'Exceção'**");
    	String[] horarios = gerarHorariosAgendamento();
        // Cria
        agendamento.criarAgendamento(horarios[0], horarios[1], horarios[2], true);
        // Valida
        actions.validarNotificacao("Agendamento cadastrado com sucesso.");
        // Cria com exceção
        agendamento.criarAgendamentoExcecao(horarios[0], horarios[1], horarios[2], true);
        // Valida
        actions.validarNotificacao("Agendamento cadastrado com sucesso.");
    }
    
   
    public void confirmarAgendamento() {
    	Log.registrar("===== TESTE REALIZADO ===== - CONFIRMAR AGENDAMENTO");
    	String[] horarios = gerarHorariosAgendamento();
    	// Cria
    	agendamento.criarAgendamentoExcecao(horarios[0], horarios[1], horarios[2], true);
        // Valida
    	actions.validarNotificacao("Agendamento cadastrado com sucesso.");
        // Altera status
    	actions.esperar(1500);
    	agendamento.alterarStatus(Access.paciente, 1);
    	// Valida
    	actions.validarNotificacao("Agendamento confirmado com sucesso.");
    }
    
   
    public void cancelarAgendamento() {
    	Log.registrar("===== TESTE REALIZADO ===== - CANCELAR AGENDAMENTO");
    	String[] horarios = gerarHorariosAgendamento();
    	// Criar
    	agendamento.criarAgendamentoExcecao(horarios[0], horarios[1], horarios[2], true);
        // Valida
    	actions.validarNotificacao("Agendamento cadastrado com sucesso.");
        // Altera satus
    	actions.esperar(1500);
    	agendamento.alterarStatus(Access.paciente, 2);
    	// Valida
    	actions.validarNotificacao("Agendamento cancelado com sucesso.");
    }
    
    
    // Criar varios agendamentos - BDD GHERKIN
    public void agendamentoEmMassa() {
    	Log.registrar("TESTE - !! CRIAR AGENDAMENTO EM MASSA !!");
        for (int i = 0; i < 10; i++) { // Define quantos agendamentos será realizado
            String[] horarios = gerarHorariosAgendamento();
            agendamento.criarAgendamento(horarios[0], horarios[1], horarios[2], true);
        }
    }
}
