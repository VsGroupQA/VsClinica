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
import pages.LoginPage;
import pages.PacientePage;

public class PacienteTest {

    private static WebDriver driver;
    private LoginPage loginPage;
    private PacientePage paciente;
    private Actions actions;

    
    private static LocalDateTime agora = LocalDateTime.now();
    private static final DateTimeFormatter formato = DateTimeFormatter.ofPattern("HHmmSS");
    private String hora = agora.format(formato);
    
    @BeforeAll
    public static void iniciarLog() {
        Log.criarArquivoLog("Log.Pacientes");
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
        paciente = new PacientePage(driver);
        actions = new Actions(driver);
    }

    @AfterEach
    public void encerrarDriver() {
        Browser.fecharNavegador(Access.quit);
    }
    
    @Test
    public void criarPaciente () {
    	Log.registrar("===== TESTE REALIZADO ===== - CRIAR NOVO PACIENTE");
    	String nome = "Botjaum " + hora;
    	
    	paciente.acessarPacientes();
    	paciente.criarPaciente(nome, Access.numero);
    	paciente.btnCriarPaciente();
    	actions.validarNotificacao("Paciente cadastrado com sucesso.");
    }
    
    @Test
    public void cpfMenosCaracteres () {
    	Log.registrar("===== TESTE REALIZADO ===== - VALIDAR TAMANHO CPF");
    	
    	paciente.acessarPacientes();
    	paciente.abrirModalNovoPaciente();
    	paciente.cpfModalPaciente("000.000.000");
    	paciente.validarMensagemErro("CPF PRECISA TER 11 CARACTERES.");
    	
    }
    
    @Test
    public void pacienteCpfDuplicado () {
    	Log.registrar("===== TESTE REALIZADO ===== - PACIENTE COM CPF JÁ EXISTENTE");
    	
    	paciente.acessarPacientes();
    	paciente.abrirModalNovoPaciente();
    	paciente.cpfModalPaciente("000.000.000-00");
    	paciente.validarMensagemErro("CPF JÁ EXISTE.");
    	
    }
    
    @Test
    public void editarPaciente () {
    	Log.registrar("===== TESTE REALIZADO ===== - EDITAR PACIENTE");
    	String nome = "Botjaum " + hora;
    	
    	paciente.acessarPacientes();
    	paciente.criarPaciente(nome, Access.numero);
    	paciente.btnCriarPaciente();
    	actions.validarNotificacao("Paciente cadastrado com sucesso.");
    	actions.fecharNotificacao();
    	
    	paciente.pesquisarPaciente(nome);
    	paciente.editarPaciente(nome);
    	paciente.obsModalPaciente(" - EDITADO POR TESTE AUTOMATIZADO");
    	paciente.btnCriarPaciente();
    	actions.validarNotificacao("Paciente cadastrado com sucesso.");
    }
    
    @Test
    public void desativarPaciente () {
    	Log.registrar("===== TESTE REALIZADO ===== - DESATIVAR PACIENTE");
    	String nome = "Botjaum " + hora;
    	
    	paciente.acessarPacientes();
    	paciente.criarPaciente(nome, Access.numero);
    	paciente.btnCriarPaciente();
    	actions.validarNotificacao("Paciente cadastrado com sucesso.");
    	actions.fecharNotificacao();
    	
    	paciente.pesquisarPaciente(nome);
    	paciente.desativarPaciente(nome);
    	actions.validarNotificacao("Paciente desativado com sucesso.");
    }
    
    @Test
    public void abrirEstudoCaso () {
    	Log.registrar("===== TESTE REALIZADO ===== - ABRIR ESTUDO DE CASO DO PACIENTE");
    	String nome = "Botjaum " + hora;
    	
    	paciente.acessarPacientes();
    	paciente.criarPaciente(nome, Access.numero);
    	paciente.btnCriarPaciente();
    	actions.validarNotificacao("Paciente cadastrado com sucesso.");
    	actions.fecharNotificacao();
    	
    	paciente.pesquisarPaciente(nome);
    	paciente.estudoCasoPaciente(nome);
    	paciente.validarEstudoCaso("Estudo de caso");
    }
    
    
    
    
    
 // Criar cenário para criar paciente e agendamento - BDD
    public void abrirDebitosPacinte () {
    	Log.registrar("===== TESTE REALIZADO ===== - ABRIR DÉBITOS PACIENTE");
    	String nome = "Botjaum " + hora;
    	
    	paciente.acessarPacientes();
    	paciente.criarPaciente(nome, Access.numero);
    	paciente.btnCriarPaciente();
    	actions.validarNotificacao("Paciente cadastrado com sucesso.");
    	actions.fecharNotificacao();
    	
    	paciente.pesquisarPaciente(nome);
    	paciente.debitosPaciente(nome);
    	actions.validarAcessoTelaDebitos();
    }
  
}