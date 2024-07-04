package pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;

import utils.Actions;

public class AgendamentoPage {
    private WebDriver driver;
    private Actions actions;

    public AgendamentoPage(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(this.driver); 
    }

    public void agendarPaciente() {
        actions.clicarBotaoPegandoPeloXpath("/html/body/app-root/div/app-calendario/div/app-filtro-pacientes/p-card/div/div/div/div/div[6]/p-button");
        System.out.println("Acessar agendamento");
        // Abrir procedimento e validar
        selecionarProcedimentoEValidar("Cardiologia", 3); // Colocar para iterar item por item 'for'
        System.out.println("Procedimento selecionado e validado com sucesso");
        actions.escreverPegandoPeloXpath("/html/body/div/div/div[2]/app-modal-agendamento/form/div[2]/div[6]/p-autocomplete/span/input", "biro biro");
    }

    public void procedimento() {
        // Abrir o dropdown
        System.out.println("Abrir dropdown");
        actions.clicarBotaoPegandoPeloXpath("//div[2]/div/p-dropdown/div/span");
    }

    public void selecionarProcedimentoEValidar(String textoEsperado, int indice) {
        // Abrir o dropdown
        procedimento();

        // Selecionar o item do dropdown com base no índice
        String itemXpath = "//p-dropdownitem[" + indice + "]/li/div";
        actions.clicarBotaoPegandoPeloXpath(itemXpath);

        // Validar se o texto selecionado no dropdown é igual ao texto esperado
        WebElement dropdownElement = driver.findElement(By.xpath("//div[2]/div/p-dropdown/div/span"));
        String textoSelecionado = dropdownElement.getText();

        if (textoEsperado.equals(textoSelecionado)) {
            System.out.println("Texto do dropdown validado com sucesso: " + textoSelecionado);
        } else {
            System.err.println("Erro na validação do texto do dropdown. Texto esperado: " + textoEsperado + ", Texto encontrado: " + textoSelecionado);
        }
    }
}
