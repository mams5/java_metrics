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
        System.out.println("--------------------------------------------------");
        System.out.println("Métrica adicional: Densidade de Estruturas");
        System.out.println("Fórmula: NDEC = 1 / (1 + (ESTRUTURAS * PROFUNDIDADE))");
        System.out.println("Linhas úteis: exclui comentários e linhas em branco");

        for (codeContext.StructuresDensityResult result : codeContext.structuresDensityResults) {
            System.out.println("Arquivo: " + result.snippetName);
            System.out.println("Linhas úteis: " + result.usefulLines);
            System.out.println("Estruturas: " + result.structures);
            System.out.printf("Profundidade média: %.2f%n", result.averageDepth);
            System.out.printf("Estruturas por 10 linhas: %.2f%n", result.structuresPerTenLines);
            System.out.printf("NDEC: %.4f%n", scoringEngine.calculateNdec(result.structuresPerTenLines, result.averageDepth));

            if (!result.note.isEmpty()) {
                System.out.println("Observação: " + result.note);
            }

            System.out.println("--------------------------------------------------");
        }

        System.out.println("Total de arquivos analisados pela structuresDensity: " + codeContext.structuresDensityResults.size());
        System.out.println("==================================================");
    }
}