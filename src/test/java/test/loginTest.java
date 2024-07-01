package test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
import pages.loginPage;

public class loginTest {
    private static WebDriver driver;
    private loginPage loginPage;
    private Actions actions;

    @BeforeAll
    public static void iniciarLog() {
        Log.criarArquivoLog("Log.Login");
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
    }

    @AfterEach
    public void encerrarDriver() {
        Browser.fecharNavegador();
    }
    

    @Test
    public void loginComSucesso() {
    	
        String urlInicial = driver.getCurrentUrl();
        loginPage.signIn(Access.usuario, Access.senha);
        actions.esperar(4000);
        
        String urlAposLogin = driver.getCurrentUrl();
        actions.esperar(500);
        
        String mensagem = "Login realizado com sucesso!";
        
        assertNotEquals(urlInicial, urlAposLogin, mensagem);
        Log.registrar(mensagem);
    }
    
    @Test
    public void loginSemUsuario() {
    	
         loginPage.signIn("", Access.senha);
         loginPage.validarTextoIgual("body > app-root > p-toast > div > p-toastitem", "Usuário ou senha incorretos");

    }
    
    @Test
    public void loginemSenha() {
    	
    	loginPage.signIn(Access.usuario, "");
        loginPage.validarTextoIgual("body > app-root > p-toast > div > p-toastitem", "Usuário ou senha incorretos");
    }
    
    @Test
    public void loginLogout() {
    	
    	loginPage.signIn(Access.usuario, Access.senha);
    	String urlInicial = driver.getCurrentUrl();
    	actions.esperar(1000);
    	
    	loginPage.logout();
    	actions.esperar(500);
        String urlAposLogin = driver.getCurrentUrl();
        
        String mensagem = "Login realizado com sucesso!";
        
        assertNotEquals(urlInicial, urlAposLogin, mensagem);
        Log.registrar(mensagem);

    }
}