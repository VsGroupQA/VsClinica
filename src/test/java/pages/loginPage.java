package pages;


import java.time.Duration;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.Actions;
import utils.Log;

public class loginPage {

    private WebDriver driver;
    private Actions actions;

    public loginPage(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(this.driver); 
    }

    public void signIn(String login, String senha) {
        actions.escreverPegandoPeloName("login", login);
        actions.escreverPegandoPeloName("senha", senha);
        Log.registrar("informações");
        actions.clicarBotaoPegandoPeloXpath("/html/body/app-root/div/app-login/div/form/div/div[4]/button");
        Log.registrar("Logar");
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
    }
    
    public void logout() {
    	actions.clicarBotaoPegandoPeloId("img-usuario");
    	actions.esperar(500);
        actions.clicarBotaoPegandoPeloXpath("//div/div/div[2]/div[2]");
       
    }

    public void validarTextoIgual(String css, String mensagem) {
    	WebElement mensagemAtual = driver.findElement(By.cssSelector(css));
    	
    	String atual = mensagemAtual.getText();
    	String esperado = mensagem;
    	
    	System.out.println(atual);
    	
    	if (atual.contains(esperado)) {
    		Log.registrar("Texto igual = "+ atual +" e "+ esperado +" .");
    	} else {
    		Log.registrar("Texto diferente = "+ atual +" e "+ esperado +" .");
    		Assert.fail();
    	}
    	
    }


}