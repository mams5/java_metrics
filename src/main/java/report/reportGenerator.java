package report;

import context.codeContext;
import scoring.scoringEngine;

public class reportGenerator {

    // Gera e exibe o relatório final com as métricas extraídas.
    public static void generateReport() {
        System.out.println("==================================================");
        System.out.println("         RELATÓRIO DE MÉTRICAS DE CÓDIGO          ");
        System.out.println("==================================================");
        System.out.println("Métrica avaliada: Tamanho dos Métodos");
        System.out.println("Benchmark definido: " + codeContext.METHOD_LINE_BENCHMARK + " linhas úteis por método.");
        System.out.println("--------------------------------------------------");
        System.out.println("Total de unidades funcionais (métodos): " + codeContext.totalMethods);
        System.out.println("Métodos em conformidade (abaixo do limite): " + codeContext.methodsBelowBenchmark);
        System.out.println("--------------------------------------------------");
        
        // Exibe a porcentagem final calculada pelo contexto
        System.out.printf("Porcentagem de métodos adequados: %.2f%%\n", scoringEngine.getCompliancePercentage());
        System.out.println("==================================================");
    }
}