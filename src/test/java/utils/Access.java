package utils;



public class Access {

    // URL
    public static String url = setUrl(2);
    
    // NAVEGADOR
    public static String navegador = "chrome";
    
   // HEADLESS
    public static boolean headless = false;
    
    // QUIT NAVEGADOR
    public static boolean quit = true; // 
    
    // DEFINIR LEAD CRIADO
    public static String leadUsuario = null;
    public static int opcao = 3;
    
    /* 
     * ANOTAÇOES 
     */
    
    // ACESSO ADMIN
    public static String usuario = "admin.vs";
    public static String senha = "Luandabh001";
    
    // ACESSO CLINICO
    public static String usuarioMedico = "vena";
    public static String senhaMedico = "123";
    
    // AGENDAMENTO
    public static String procedimento = "PROCEDIMENTO X";
    public static String compromisso = "FINALIZADO";
    public static String paciente = "Jhonata Venancio - VS GROUP";
    public static String medico = "VS GROUP";
    
    // PACIENTE
    public static String numero = "31996463050";
    
    // INTEGRAÇÃO
    public static String urlBuscarEquipe = "https://omnia-dev.vsomnia.com.br:8443/equipe-usuario/equipes";
    public static String urlBuscarUsuario = "https://omnia-dev.vsomnia.com.br:8443/equipe-usuario/usuarios";
    public static String urlIntegracao = "https://omnia-dev.vsomnia.com.br:8443/disparoEmMassa/enviar";
    public static String tokenOmnia = "16082001";
    public static String numeroDisparo = "553140404963";
    public static String nomeTemplate = "hotel_gup";
    public static String variavel = "A=NOME_CLIENTE;B=PROCEDIMENTO;C=DIA;D=DIA_SEMANA;E=HORA;F=NASCIMENTO;G=COMPROMISSO;";
    
    // OMNIA - CRIAR LEAD
    public static String equipeOmnia = "Equipe Testes Gerais";
    public static String usuarioOmnia = "Jhonata Vena";

    
    public static String setUrl(int number) {
        switch (number) {
            case 1:
            	return "https://homologacao-rejuvenesce.vsgestaoclinica.com.br/";
            case 2:
                return "https://staging-rejuvensce.vsgestaoclinica.com.br/";
            case 3:
            	return "https://rejuvenesce.vsgestaoclinica.com.br/";
            default:
            	return "https://staging-rejuvensce.vsgestaoclinica.com.br/";
        }
    }
}
