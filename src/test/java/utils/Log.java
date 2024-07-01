package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {

	private static FileWriter fileWriter;
	private static PrintWriter writer;
	static DateTimeFormatter data = DateTimeFormatter.ofPattern("dd MM yyyy - HH.mm.ss");

	public static void criarArquivoLog(String nomeArquivo) {
		try {
			String caminhoCompleto = System.getProperty("user.dir") + "/src/test/java/log/";
			File diretorio = new File(caminhoCompleto);
			if (!diretorio.exists()) {
				if (diretorio.mkdirs()) {
					System.out.println("Diretório criado com sucesso: " + caminhoCompleto);
				} else {
					System.err.println("Falha ao criar diretório: " + caminhoCompleto);
					return;
				}
			}
			
			String nome = nomeArquivo + "-" + LocalDateTime.now().format(data) + ".txt";
			fileWriter = new FileWriter(caminhoCompleto + nome);
			writer = new PrintWriter(fileWriter);
			System.out.println("Arquivo de log criado: " + caminhoCompleto + nome);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void registrar(String mensagem) {
		if (writer != null) {
			writer.println(mensagem + " - " + LocalDateTime.now().format(data));
			writer.flush();
		} else {
			System.err.println("Pasta para adicionar log não criada");
		}
	}

	public static void encerrarLog() {
		if (writer != null) {
			writer.close();
			System.out.println("Arquivo de log fechado.");
		}
	}
}