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

public class UsuarioTest {

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
    
    @Test
    public void criarUsuarioMedico() {
    	String user = "user" + hora;
    	String nome = "Testevaldo " + hora;
    	Log.registrar("===== TESTE REALIZADO ===== - CRIAR USUÁRIO MEDICO");
    	
    	usuario.acessarUsuarios();
    	usuario.criarUsuario(
    			nome, 
    			"teste@teste.com", 
    			user, 
    			"123", 
    			"Default"
    			);
    	usuario.botaoSalvar(true);
    	actions.validarNotificacao("Cadastro criado com sucesso");
    }
    
    @Test
    public void criarUsuarioDuplicado() {
    	String user = "user" + hora;
    	String nome = "Testevaldo " + hora;
    	Log.registrar("===== TESTE REALIZADO ===== - CRIAR USUÁRIO MEDICO DUPLICADO");
    	
    	usuario.acessarUsuarios();
    	usuario.criarUsuario(
    			nome, 
    			"teste@teste.com", 
    			user, 
    			"123", 
    			"Default"
    			);
    	usuario.botaoSalvar(true);
    	actions.validarNotificacao("Cadastro criado com sucesso");
    	actions.fecharNotificacao();
    	// Duplicado
    	usuario.criarUsuario(
    			nome, 
    			"teste@teste.com", 
    			user, 
    			"123", 
    			"Default"
    			);
    	usuario.botaoSalvar(false);
    	actions.validarNotificacao("login "+ user +" já existente.");
    }
    
//    @Test
    public void criarUsuarioMedicoSemCamposObgs() {
    	Log.registrar("===== TESTE REALIZADO ===== - CRIAR USUÁRIO MEDICO SEM CAMPOS OBRIGATOIOS");
    	
    	usuario.acessarUsuarios();
    	usuario.criarUsuario(
    			"", 
    			"", 
    			"", 
    			"", 
    			"Default"
    			);
    	usuario.botaoSalvar(true);
    	actions.validarNotificacao("Cadastro criado com sucesso");
    }
    
    public void editarUsuarioMedico () {
    	
    }
    
    public void excluirUsuarioMedico () {
    	
    }
    
    public void criarEscalaMedica() {
    	
    }
    
    public void adicionarBloqueioMedico () {
    	
    }
    
    public void criarUsuarioAdm () {
    	
    }
    
    public void criarUsuarioAdmDuplicado () {
    	
    }
    
    public void criarUsuarioAdmSemCamposObgs () {
    	
    }
    
    public void editarUsuarioAdm () {
    	
    }
    
    public void excluirUsuarioAdm () {
    	
    }
}