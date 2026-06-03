package main;

import main.analyzer.methodSizes;
import main.report.reportGenerator;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            // Substitua o caminho abaixo pelo caminho real do arquivo gigante que você quer testar
            // Exemplo Windows: "C:\\Users\\SeuUsuario\\Desktop\\ArquivoGigante.java"
            Path filePath = Paths.get("Caminho/Para/O/Seu/ArquivoGigante.java");
            
            // Lê todas as milhares de linhas do arquivo e transforma em uma única String
            String sourceCode = Files.readString(filePath);

            System.out.println("Iniciando análise com JavaParser...\n");

            // Roda a sua feature
            methodSizes.analyze(sourceCode);

            // Exibe o relatório
            reportGenerator.generate();

        } catch (Exception e) {
            System.err.println("Erro ao ler o arquivo ou analisar o código: " + e.getMessage());
            e.printStackTrace();
        }
    }
}