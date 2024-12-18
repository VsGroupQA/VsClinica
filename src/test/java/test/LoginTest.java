package test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.*;
import utils.Log;
import utils.Access;
import utils.Actions;
import utils.Browser;
import pages.LoginPage;

@Epic("Módulo de Autenticação")
@Feature("Funcionalidade de Login")
public class LoginTest {

    private static WebDriver driver;
    private LoginPage loginPage;
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
        driver = Browser.iniciarNavegador(Access.navegador, Access.headless);
        driver.get(Access.url);
        loginPage = new LoginPage(driver);
        actions = new Actions(driver);
    }

    @AfterEach
    public void encerrarDriver() {
        Browser.fecharNavegador(Access.quit);
    }

    @Test
    @Story("Usuário realiza login com sucesso")
    @Description("Testa o login com credenciais válidas e verifica se a URL muda após o login")
    @Severity(SeverityLevel.CRITICAL)
    public void loginComSucesso() {
        String urlInicial = driver.getCurrentUrl();
        loginPage.signIn(Access.usuario, Access.senha);
        Log.registrar("esperar");

        String urlAposLogin = driver.getCurrentUrl();
        String mensagem = "Login realizado com sucesso!";

        // Verificação se as URLs são diferentes, indicando que o login foi realizado
        assertNotEquals(urlInicial, urlAposLogin, mensagem);
        Log.registrar(mensagem);
        Allure.addAttachment("URL após login", urlAposLogin);  // Anexando a URL no Allure
    }

    @Test
    @Story("Usuário tenta fazer login sem inserir o nome de usuário")
    @Description("Testa o login sem nome de usuário e verifica a mensagem de erro exibida")
    @Severity(SeverityLevel.NORMAL)
    public void loginSemUsuario() {
        loginPage.signIn("", Access.senha);
        loginPage.validarTextoIgual("body > app-root > p-toast > div > p-toastitem", "Usuário ou senha incorretos");
        capturarTela();  // Captura de tela quando falhar
    }

    @Test
    @Story("Usuário tenta fazer login sem inserir a senha")
    @Description("Testa o login sem senha e verifica a mensagem de erro exibida")
    @Severity(SeverityLevel.NORMAL)
    public void loginSemSenha() {
        loginPage.signIn(Access.usuario, "");
        loginPage.validarTextoIgual("body > app-root > p-toast > div > p-toastitem", "Usuário ou senha incorretos");
        capturarTela();  // Captura de tela quando falhar
    }

    @Test
    @Story("Usuário realiza login e logout")
    @Description("Testa o fluxo completo de login e logout, verificando se a URL muda após o logout")
    @Severity(SeverityLevel.CRITICAL)
    public void loginLogout() {
        loginPage.signIn(Access.usuario, Access.senha);
        String urlInicial = driver.getCurrentUrl();
        actions.esperar(1000);

        loginPage.logout();
        actions.esperar(200);
        String urlAposLogout = driver.getCurrentUrl();

        String mensagem = "Login e logout realizados com sucesso!";
        assertNotEquals(urlInicial, urlAposLogout, mensagem);
        Log.registrar(mensagem);
        Allure.addAttachment("URL após logout", urlAposLogout);  // Anexando a URL no Allure
    }

    // Método para capturar a tela em falhas de login
    @Attachment(value = "Captura de Tela", type = "image/png")
    public byte[] capturarTela() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
