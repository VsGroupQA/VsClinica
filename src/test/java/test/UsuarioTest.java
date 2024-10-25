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
import pages.UsuarioPage;
import pages.LoginPage;

public class UsuarioTest {

    private static WebDriver driver;
    private LoginPage loginPage;
    private UsuarioPage usuario;
    
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
    }

    @AfterEach
    public void encerrarDriver() {
        Browser.fecharNavegador(Access.quit);
    }
    
    @Test
    public void criarUsuarioMedico() {
    	String user = "teste" + hora;
    	String nome = "Testevaldo " + hora;
    	
    	usuario.acessarUsuarios();
    	usuario.criarUsuario(
    			nome, 
    			"teste@teste.com", 
    			user, 
    			"123", 
    			"Default"
    			);
    	usuario.botaoSalvar(true);
    	usuario.validarNotificacao("Cadastro criado com sucesso");
    }
    
    @Test
    public void criarUsuarioADM() {
    	String user = "teste" + hora;
    	String nome = "Testevaldo " + hora;
    	
    	usuario.acessarUsuarios();
    	usuario.usuarioAdm();
    	usuario.criarUsuario(
    			nome, 
    			"teste@teste.com", 
    			user, 
    			"123", 
    			"Default"
    			);
    	usuario.botaoSalvar(true);
    	// Ajustar notificação - (editado com sucesso)
    	usuario.validarNotificacao("Cadastro criado com sucesso"); 
    }
    
    @Test
    public void criarUsuarioSemCamposObg() {
    	usuario.acessarUsuarios();
    	usuario.usuarioAdm();
    	usuario.criarUsuario(
    			"", 
    			"", 
    			"", 
    			"", 
    			"Default"
    			);
    	usuario.botaoSalvar(false);
    }
    
    
}