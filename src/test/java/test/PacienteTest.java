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
import pages.UsuarioPage;
import pages.LoginPage;

public class PacienteTest {

    private static WebDriver driver;
    private LoginPage loginPage;
    private UsuarioPage usuario;
    private Actions actions;

    
    private static LocalDateTime agora = LocalDateTime.now();
    private static final DateTimeFormatter formato = DateTimeFormatter.ofPattern("HHmmSS");
    private String hora = agora.format(formato);
    
    @BeforeAll
    public static void iniciarLog() {
        Log.criarArquivoLog("Log.Usuario");
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
        usuario = new UsuarioPage(driver);
        actions = new Actions(driver);
    }

    @AfterEach
    public void encerrarDriver() {
        Browser.fecharNavegador(Access.quit);
    }
    
    public void criarPaciente () {
    	
    }
    
    public void criarPacienteDuplicado () {
    	
    }
    
    public void criarPacienteSemCamposObgs () {
    	
    }
    
    public void desativarPaciente () {
    	
    }
    
    public void editarPaciente () {
    	
    }
    
    public void abrirFinanceiro () {
    	
    }
    
    public void abrirEstudoCaso () {
    	
    }
    
    public void pesquisarPaciente () {
    	
    }
    
}