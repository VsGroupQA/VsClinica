package pages;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.Actions;
import utils.Log;


public class PacientePage {
	private WebDriver driver;
	private Actions actions;
	private WebDriverWait wait;

	 
	
	public PacientePage(WebDriver driver) {
		this.driver = driver;
		this.actions = new Actions(this.driver);
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	public static String gerarCpf() {
        Random random = new Random();
        int[] numeros = new int[9];
        
        // Gera os 9 primeiros dígitos do CPF
        for (int i = 0; i < 9; i++) {
            numeros[i] = random.nextInt(10);
        }
        
        // Calcula o primeiro dígito verificador
        int primeiroDigitoVerificador = calcularDigitoVerificador(numeros, 10);
        
        // Adiciona o primeiro dígito verificador ao array
        int[] numerosComPrimeiroDV = new int[10];
        System.arraycopy(numeros, 0, numerosComPrimeiroDV, 0, 9);
        numerosComPrimeiroDV[9] = primeiroDigitoVerificador;
        
        // Calcula o segundo dígito verificador
        int segundoDigitoVerificador = calcularDigitoVerificador(numerosComPrimeiroDV, 11);
        
        // Monta o CPF completo
        StringBuilder cpfBuilder = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            cpfBuilder.append(numeros[i]);
        }
        cpfBuilder.append(primeiroDigitoVerificador);
        cpfBuilder.append(segundoDigitoVerificador);
        
        // Formata o CPF no formato XXX.XXX.XXX-XX
        return cpfBuilder.insert(3, ".").insert(7, ".").insert(11, "-").toString();
    }

    // Método para calcular o dígito verificador do CPF
    private static int calcularDigitoVerificador(int[] numeros, int pesoInicial) {
        int soma = 0;
        for (int i = 0; i < numeros.length; i++) {
            soma += numeros[i] * (pesoInicial - i);
        }
        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }
	
	public void acessarPacientes() {
		actions.esperarElementoVisivel(By.id("ROLE_PACIENTES"), 10);
		actions.clicarBotaoPegandoPeloId("ROLE_PACIENTES");
		Log.registrar("Tela de Usuários");
		}
	
	public void abrirModalNovoPaciente() {
		actions.clicarBotaoPegandoPeloXpath("//span[contains(.,'Novo paciente')]");
	}
	
	public void pesquisaAtracao(String nomeDrop) { 
		Log.registrar("Selecionar pesquisa de atracao");
		actions.clicarBotaoPegandoPeloXpath("//div[4]/div[3]/p-dropdown/div/span");
	    actions.esperarElementoVisivel(By.xpath("//p-dropdownitem/li"), 10);
	    
	    List<WebElement> tipoIntegracao = driver.findElements(By.xpath("//p-dropdownitem/li"));
	    for (WebElement integracao : tipoIntegracao) {
	        if (integracao.getText().equalsIgnoreCase(nomeDrop)) {
	            integracao.click();
	            Log.registrar("Nome no dropdown: '" + nomeDrop + "' encontrado e selecionado");
	            break;
	        }
	    }
	}
	
	public void btnCriarPaciente () {
		Log.registrar("Criando Paciente");
		actions.clicarBotaoPegandoPeloXpath("//span[contains(.,'Salvar')]");
	}
	
	public void cpfModalPaciente (String cpf) {
		actions.escreverPegandoPeloXpath("//input[@name='cpf']", cpf);
		Log.registrar("CPF Preenchido:" + cpf);
	}
	
	public void validarMensagemErro(String msg) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement notificacao = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".error > .ng-star-inserted")));
            String textoNotificacao = notificacao.getText();
            Assert.assertEquals(msg, textoNotificacao);
            Log.registrar("Mensagem exibida: " + textoNotificacao);
        
    }
	
	public void validarEstudoCaso(String msg) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement notificacao = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".p-dialog-header")));
        String textoNotificacao = notificacao.getText();
        Assert.assertEquals(msg, textoNotificacao);
        Log.registrar("Mensagem exibida: " + textoNotificacao);
    
	}

	public void pesquisarPaciente (String paciente) {
		actions.esperar(600);
		actions.escreverPegandoPeloName("nomePaciente", paciente);
		Log.registrar("Pequisando: " + paciente);
	}
	
	public void editarPaciente(String nomeEsperado) {
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
	    Log.registrar("Tabela localizada");

	    List<WebElement> linhas = driver.findElements(By.xpath("//table/tbody/tr"));

	    for (int i = 1; i <= linhas.size(); i++) {
	        try {
	            WebElement nomeColuna = linhas.get(i - 1).findElement(By.xpath("./td[3]"));
	            String txt = nomeColuna.getText();
	            Log.registrar("Nome na linha: " + i + ": " + txt);

	            if (txt.equals(nomeEsperado)) {
	                Log.registrar("Paciente encontrado: " + txt);
	                WebElement botaoEditar = linhas.get(i - 1).findElement(By.cssSelector(".ng-star-inserted:nth-child("+i+") > .col .p-element:nth-child(3) > .p-button-icon"));
	                botaoEditar.click();
	                Log.registrar("Botão de edição clicado");
	                break;
	            }
	        } catch (NoSuchElementException e) {
	            Log.registrar("Elemento não encontrado na linha " + i + ": " + e.getMessage());
	        }
	    }
	}
	
	public void obsModalPaciente(String obs) {
		actions.escreverPegandoPeloName("observacoes", obs);
		Log.registrar("Observação: " + obs);
	}
	
	public void desativarPaciente(String nomeEsperado) {
		actions.esperar(500);
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
	    Log.registrar("Tabela localizada");

	    List<WebElement> linhas = driver.findElements(By.xpath("//table/tbody/tr"));

	    for (int i = 1; i <= linhas.size(); i++) {
	        try {
	            WebElement nomeColuna = linhas.get(i - 1).findElement(By.xpath("./td[3]"));
	            String txt = nomeColuna.getText();
	            Log.registrar("Nome na linha: " + i + ": " + txt);

	            if (txt.equals(nomeEsperado)) {
	                Log.registrar("Paciente encontrado: " + txt);
	                WebElement botaoEditar = linhas.get(i - 1).findElement(By.cssSelector(".ng-star-inserted:nth-child("+i+") .p-inputswitch-slider"));
	                botaoEditar.click();
	                Log.registrar("Botão de edição clicado");
	                break;
	            }
	        } catch (NoSuchElementException e) {
	            Log.registrar("Elemento não encontrado na linha " + i + ": " + e.getMessage());
	        }
	    }
	}
	
	public void estudoCasoPaciente(String nomeEsperado) {
		actions.esperar(500);
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
	    Log.registrar("Tabela localizada");

	    List<WebElement> linhas = driver.findElements(By.xpath("//table/tbody/tr"));

	    for (int i = 1; i <= linhas.size(); i++) {
	        try {
	            WebElement nomeColuna = linhas.get(i - 1).findElement(By.xpath("./td[3]"));
	            String txt = nomeColuna.getText();
	            Log.registrar("Nome na linha: " + i + ": " + txt);

	            if (txt.equals(nomeEsperado)) {
	                Log.registrar("Paciente encontrado: " + txt);
	                WebElement botaoEditar = linhas.get(i - 1).findElement(By.cssSelector(".ng-star-inserted:nth-child("+i+") .p-button-danger"));
	                botaoEditar.click();
	                Log.registrar("Botão de edição clicado");
	                break;
	            }
	        } catch (NoSuchElementException e) {
	            Log.registrar("Elemento não encontrado na linha " + i + ": " + e.getMessage());
	        }
	    }
	}
	
	public void debitosPaciente(String nomeEsperado) {
		actions.esperar(500);
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
	    Log.registrar("Tabela localizada");

	    List<WebElement> linhas = driver.findElements(By.xpath("//table/tbody/tr"));

	    for (int i = 1; i <= linhas.size(); i++) {
	        try {
	            WebElement nomeColuna = linhas.get(i - 1).findElement(By.xpath("./td[3]"));
	            String txt = nomeColuna.getText();
	            Log.registrar("Nome na linha: " + i + ": " + txt);

	            if (txt.equals(nomeEsperado)) {
	                Log.registrar("Paciente encontrado: " + txt);
	                WebElement botaoEditar = linhas.get(i - 1).findElement(By.cssSelector(".ng-star-inserted:nth-child("+i+") .p-element:nth-child(2)"));
	                botaoEditar.click();
	                Log.registrar("Botão de edição clicado");
	                break;
	            }
	        } catch (NoSuchElementException e) {
	            Log.registrar("Elemento não encontrado na linha " + i + ": " + e.getMessage());
	        }
	    }
	}
	
	// GRUPO
	
		/**
		 * Adicionar um novo usuário
		 *
		 * @param nomePaciente       Nome do paciente.
		 * @param celular       	 numero do paciente.
		 */
	 public void criarPaciente(String nomePaciente, String celular) {
	        Log.registrar("Criando um novo paciente");
	        String cpfGerado = gerarCpf();
	        
	        abrirModalNovoPaciente();
	        actions.escreverPegandoPeloName("nome", nomePaciente);
	        actions.escreverPegandoPeloName("rg", "TS123");
	        actions.escreverPegandoPeloName("nomeMae", "Testevalda");
	        actions.escreverPegandoPeloName("email", "paciente@teste.com");
	        actions.escreverPegandoPeloName("cep", "30270-300");
	        actions.escreverPegandoPeloName("numero", "123");
	        actions.clicarBotaoPegandoPeloXpath("//p-radiobutton/div/div[2]"); // Sexo
	        actions.escreverPegandoPeloId("icon", "14/10/1914"); // data nasc.
	        cpfModalPaciente(cpfGerado);
	        actions.escreverPegandoPeloName("celular", celular);
	        obsModalPaciente ("PACIENTE CRIADO POR TESTE AUTOMATIZADO");
	        pesquisaAtracao("Instagram");
	    }
	
	
	 
}