package report;

import analyzer.lineLengh;
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

        System.out.println("Métrica avaliada: Comprimento das Linhas");
        System.out.println("--------------------------------------------------");
        System.out.println("Total de linhas úteis analisadas : " + codeContext.totalUsefulLines);
        System.out.println("Linhas adequadas (abaixo de 80)  : " + codeContext.linesBelowBenchmark);
        System.out.println("Linhas acima do limite           : " + lineLengh.getViolations().size());
        System.out.println("--------------------------------------------------");
        System.out.printf("Porcentagem de linhas adequadas: %.2f%%\n", scoringEngine.getLineLengthPercentage());
        System.out.println("==================================================");

        if (!lineLengh.getViolations().isEmpty()) {
            System.out.println("\nLinhas que podem ser melhoradas:");
            System.out.println("--------------------------------------------------");
            for (lineLengh.Violation v : lineLengh.getViolations()) {
                System.out.printf("Linha %d | %d chars (+%d acima do limite)%n",
                        v.lineNumber, v.lineLength, v.excess);
                System.out.println("  Conteúdo  : " + v.content);
                System.out.println("  Sugestão  : " + v.suggestion);
                System.out.println();
            }
        }

        System.out.println("Métrica avaliada: Proporção de Comentários:");
        System.out.println("Linhas Úteis: " + codeContext.totalUsefulLines);
        System.out.println("Linhas de Comentários: " + codeContext.totalCommentLines);
        System.out.println("--------------------------------------------------");
        
        // Exibe o cálculo detalhado para conferência
        double benchmarkAtual = (codeContext.totalUsefulLines / 10.0) + (codeContext.totalMethods * 2.0);
        System.out.printf("Benchmark calculado: %.2f\n", benchmarkAtual);
        
        // Multiplica por 100 para exibir em formato de nota percentual ou deixa de 0 a 1
        double npc = scoringEngine.getNpcScore();
        System.out.printf("Nota NPC (0 a 1): %.2f\n", npc);
        System.out.println("==================================================");
        
    }
}