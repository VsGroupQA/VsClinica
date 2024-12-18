package test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

import utils.Log;
import utils.Access;
import utils.Actions;
import utils.Browser;
import pages.FichaPacientePage;
import pages.LoginPage;

public class FichaPacienteTest {

    private static WebDriver driver;
    private LoginPage loginPage;
    private Actions actions;
    private FichaPacientePage ficha;

    
    private static LocalDateTime agora = LocalDateTime.now();
    private static final DateTimeFormatter formato = DateTimeFormatter.ofPattern("HHmmSS");
    private String hora = agora.format(formato);
    
    @BeforeAll
    public static void iniciarLog() {
        Log.criarArquivoLog("Log.FichaPaciente");
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
        actions = new Actions(driver);
        ficha = new FichaPacientePage(driver);
    }

    @AfterEach
    public void encerrarDriver() {
        Browser.fecharNavegador(Access.quit);
    }
    
    @Test
    public void editarPaciente () {
    	Log.registrar("===== TESTE REALIZADO ===== - CRIAR NOVO PACIENTE");
    	actions.esperar(100);
    	ficha.acessarPacientes();
    }
    
    public void alterarImagemPaciente () {
    	
    }
    
    public void estudoCaso () {
    	
    }
    
    public void gerarResumo () {
    	
    }
    
    public void preencherEtapas () {
    	
    }
    
    public void subirImagensEtapas () {
    	
    }
    
    public void criarFormulario () {
    	
    }
    
    public void excluirFormulario () {
    	
    }
    
    public void imprimirFormulario () {
    	
    }
    
    public void criarContrato () {
    	
    }
    
    public void excluirContrato () {
    	
    }
    
    public void editarContrato () {
    	
    }
    
    public void imprimirContrato () {
    	
    }
    
    public void criarAtestado () {
    	
    }
    
    public void excluirAtestado () {
    	
    }
    
    public void editarAtestado () {
    	
    }
    
    public void imprimirAtestado () {
    	
    }
    
    public void criarPedidoExame () {
    	
    }
    
    public void anexarResultadoExame () {
    	
    }
    
    public void uploadExame () {
    	
    }
    
    public void excluirExame () {
    	
    }
    
    public void editarExame () {
    	
    }
    
    public void imprimirExame () {
    	
    }
    
    public void criarPrescricao () {
    	
    }
    
    public void excluirPrescricao () {
    	
    }
    
    public void editarPrescricao () {
    	
    }
    
    public void imprimirPrescricao () {
    	
    }
    
}

