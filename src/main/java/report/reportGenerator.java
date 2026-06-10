package report;

import analyzer.lineLengh;
import context.codeContext;
import scoring.scoringEngine;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class reportGenerator {

    private static final String BORDER_80 = "================================================================================";
    private static final String BORDER_77 = "+-----------------------------------------------------------------------------+";
    private static final int TABLE_INNER_WIDTH = 75;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static void generateReport() {
        List<codeContext.StructuresDensityResult> densityResults = codeContext.structuresDensityResults;
        List<codeContext.SpacingLinesResult> spacingResults = codeContext.spacingLinesResults;

        double densityScore = scoringEngine.getDensityScore();
        double methodScore = scoringEngine.getMethodScore();
        double commentScore = scoringEngine.getCommentScore();
        double lineScore = scoringEngine.getLineScore();
        double spacingScore = scoringEngine.getSpacingScore();

        System.out.println(BORDER_80);
        System.out.println(center("RELATÓRIO DE MANUTENABILIDADE DE CÓDIGO", 80));
        System.out.println(BORDER_80);
        System.out.println("Data da análise: " + LocalDateTime.now().format(DATE_FORMATTER));
        System.out.println("Total de snippets analisados: " + densityResults.size());
        System.out.println(BORDER_80);
        System.out.println();

        printDensitySection(densityResults, densityScore);
        System.out.println();
        printMethodSection(methodScore);
        System.out.println();
        printCommentSection(commentScore);
        System.out.println();
        printLineLengthSection(lineScore);
        System.out.println();
        printSpacingSection(spacingResults, spacingScore);
        System.out.println();
        printSummarySection(densityScore, methodScore, commentScore, lineScore, spacingScore);
        System.out.println();

        System.out.println(BORDER_80);
        System.out.println(center("Fim do Relatório - Reavalie o código após as correções", 80));
        System.out.println(BORDER_80);
    }

    private static void printDensitySection(List<codeContext.StructuresDensityResult> densityResults, double densityScore) {
        System.out.println(BORDER_77);
        System.out.println(row("DENSIDADE DE ESTRUTURAS (Structural Density)"));
        System.out.println(BORDER_77);
        System.out.println(row("Fórmula: NDEC = 1 / (1 + (estruturas x profundidade média))"));
        System.out.println(BORDER_77);
        System.out.println(emptyRow());

        for (codeContext.StructuresDensityResult result : densityResults) {
            double ndc = scoringEngine.calculateNdec(result.structuresPerTenLines, result.averageDepth);
            System.out.println(row("Snippet: " + result.snippetName));
            System.out.println(row("  Linhas úteis: " + result.usefulLines));
            System.out.println(row("  Estruturas (if/for/while/try): " + result.structures));
            System.out.println(row("  Profundidade média: " + formatDouble(result.averageDepth, 2)));
            System.out.println(row("  Estruturas por 10 linhas: " + formatDouble(result.structuresPerTenLines, 2)));
            System.out.println(row("  NDEC: " + formatDouble(ndc, 4)));
            System.out.println(row("  Fonte: " + (result.parsedWithAst ? "AST" : "fallback textual")));
            System.out.println(row("  Avaliação: " + classify(ndc)));
            System.out.println(emptyRow());
        }

        System.out.println(BORDER_77);
        System.out.println(row("TOTAL DE SNIPPETS: " + densityResults.size()));
        System.out.println(row("MÉDIA NDEC GLOBAL: " + formatDouble(densityScore, 4)));
        System.out.println(row("CLASSIFICAÇÃO GERAL: " + classify(densityScore)));
        System.out.println(BORDER_77);
    }

    private static void printMethodSection(double methodScore) {
        int methodsAboveLimit = Math.max(0, codeContext.totalMethods - codeContext.methodsBelowBenchmark);

        System.out.println(BORDER_77);
        System.out.println(row("TAMANHO DOS MÉTODOS (Method Length)"));
        System.out.println(BORDER_77);
        System.out.println(row("Benchmark: " + codeContext.METHOD_LINE_BENCHMARK + " linhas úteis por método"));
        System.out.println(BORDER_77);
        System.out.println(emptyRow());
        System.out.println(row("Total de métodos analisados: " + codeContext.totalMethods));
        System.out.println(row("Métodos dentro do limite: " + codeContext.methodsBelowBenchmark));
        System.out.println(row("Métodos acima do limite: " + methodsAboveLimit));
        System.out.println(row("Porcentagem de conformidade: " + formatDouble(scoringEngine.getCompliancePercentage(), 2) + "%"));
        System.out.println(emptyRow());
        System.out.println(BORDER_77);
        System.out.println(row("CLASSIFICAÇÃO: " + classify(methodScore)));
        System.out.println(BORDER_77);
    }

    private static void printCommentSection(double commentScore) {
        System.out.println(BORDER_77);
        System.out.println(row("PROPORÇÃO DE COMENTÁRIOS (Comment Proportion)"));
        System.out.println(BORDER_77);
        System.out.println(emptyRow());
        System.out.println(row("Linhas úteis (código): " + codeContext.usefulLines));
        System.out.println(row("Linhas de comentários: " + codeContext.totalCommentLines));
        System.out.println(row("Proporção comentário/código: " + formatDouble(scoringEngine.getCommentRatio(), 2) + "%"));
        System.out.println(row("Benchmark calculado: (" + codeContext.totalUsefulLines + "/10) + (" + codeContext.totalMethods + "x2) = " + formatDouble(scoringEngine.getCommentBenchmark(), 2)));
        System.out.println(row("Nota NPC (0 a 1): " + formatDouble(commentScore, 4)));
        System.out.println(emptyRow());
        System.out.println(BORDER_77);
        System.out.println(row("CLASSIFICAÇÃO: " + classify(commentScore)));
        System.out.println(BORDER_77);
    }

    private static void printLineLengthSection(double lineScore) {
        System.out.println(BORDER_77);
        System.out.println(row("COMPRIMENTO DAS LINHAS (Line Length)"));
        System.out.println(BORDER_77);
        System.out.println(row("Limite estabelecido: " + codeContext.LINE_LENGTH_BENCHMARK + " caracteres por linha"));
        System.out.println(BORDER_77);
        System.out.println(emptyRow());
        System.out.println(row("Total de linhas úteis analisadas: " + codeContext.totalUsefulLines));
        System.out.println(row("Linhas adequadas (<= 80): " + codeContext.linesBelowBenchmark));
        System.out.println(row("Linhas acima do limite: " + lineLengh.getViolations().size()));
        System.out.println(row("Porcentagem de conformidade: " + formatDouble(scoringEngine.getLineLengthPercentage(), 2) + "%"));
        System.out.println(emptyRow());
        System.out.println(BORDER_77);
        System.out.println(row("CLASSIFICAÇÃO: " + classify(lineScore)));
        System.out.println(BORDER_77);
        System.out.println(emptyRow());
        System.out.println(row("LINHAS QUE EXCEDEM O LIMITE:"));
        System.out.println(emptyRow());

        if (lineLengh.getViolations().isEmpty()) {
            System.out.println(row("  Nenhuma violação encontrada."));
        } else {
            int shown = 0;
            for (lineLengh.Violation violation : lineLengh.getViolations()) {
                if (shown == 5) {
                    int remaining = lineLengh.getViolations().size() - shown;
                    System.out.println(row("  ... e mais " + remaining + " violações não exibidas"));
                    break;
                }

                String header = String.format(Locale.US,
                        "Linha %d | %d caracteres | Excesso: +%d | Sugestão: %s",
                        violation.lineNumber, violation.lineLength, violation.excess, violation.suggestion);
                System.out.println(row(header));
                System.out.println(row("  Conteúdo: " + violation.content));
                System.out.println(emptyRow());
                shown++;
            }
        }

        System.out.println(BORDER_77);
    }

    private static void printSpacingSection(List<codeContext.SpacingLinesResult> spacingResults, double spacingScore) {
        System.out.println(BORDER_77);
        System.out.println(row("ESPAÇAMENTO E FORMATAÇÃO (Spacing & Formatting)"));
        System.out.println(BORDER_77);
        System.out.println(row("Métrica avaliada: Linhas em branco"));
        System.out.println(BORDER_77);
        System.out.println(emptyRow());

        if (spacingResults.isEmpty()) {
            System.out.println(row("Nenhum arquivo analisado para espaçamento."));
        } else {
            for (codeContext.SpacingLinesResult result : spacingResults) {
                double resultScore = scoringEngine.calculateNelbNormalizedScore(result);
                System.out.println(row("Total de linhas do arquivo: " + result.totalLines));
                System.out.println(row("Linhas de código + comentários: " + (result.totalLines - result.blankLines)));
                System.out.println(row("Linhas em branco: " + result.blankLines));
                System.out.println(row("Proporção de linhas em branco: " + formatDouble(result.totalLines == 0 ? 0.0 : (result.blankLines * 100.0 / result.totalLines), 2) + "%"));
                System.out.println(row("Linhas em branco consecutivas (2 ou mais): " + result.excessiveBlankLinesCount + " ocorrências"));
                System.out.println(row("Nota NELB: " + formatDouble(resultScore, 4)));
                System.out.println(row("CLASSIFICAÇÃO: " + classify(resultScore)));
                System.out.println(emptyRow());
                System.out.println(row("OBSERVAÇÕES:"));
                System.out.println(row("  " + buildSpacingObservation(result)));
                System.out.println(emptyRow());
            }
        }

        System.out.println(BORDER_77);
    }

    private static void printSummarySection(double densityScore, double methodScore, double commentScore, double lineScore, double spacingScore) {
        double finalScore = scoringEngine.getFinalScore();

        System.out.println(BORDER_80);
        System.out.println(center("RESUMO GERAL E SCORE FINAL", 80));
        System.out.println(BORDER_80);
        System.out.println();
        System.out.println(BORDER_77);
        System.out.println(row("METRICA                    | PONTUAÇÃO (0-1) | CLASSIFICAÇÃO"));
        System.out.println(BORDER_77);
        System.out.println(row("Densidade de Estruturas    | " + formatDouble(densityScore, 4) + " | " + classify(densityScore)));
        System.out.println(row("Tamanho dos Métodos        | " + formatDouble(methodScore, 4) + " | " + classify(methodScore)));
        System.out.println(row("Proporção de Comentários   | " + formatDouble(commentScore, 4) + " | " + classify(commentScore)));
        System.out.println(row("Comprimento das Linhas     | " + formatDouble(lineScore, 4) + " | " + classify(lineScore)));
        System.out.println(row("Espaçamento e Formatação   | " + formatDouble(spacingScore, 4) + " | " + classify(spacingScore)));
        System.out.println(BORDER_77);
        System.out.println(row("SCORE FINAL DE MANUTENABILIDADE: " + formatDouble(finalScore, 2) + " / 5"));
        System.out.println(row("CLASSIFICAÇÃO FINAL: " + classify(finalScore / 5.0)));
        System.out.println(BORDER_77);
    }

    private static String buildSpacingObservation(codeContext.SpacingLinesResult result) {
        if (result.excessiveBlankLinesCount > 0) {
            return "Existem blocos com linhas em branco consecutivas; revisar formatacao manualmente.";
        }
        if (result.groupedLinesCount > 0) {
            return "Ha trechos longos sem respiro visual; considere quebrar blocos extensos.";
        }
        if (result.totalCommentsCount > 0 && result.wellSpacedCommentsCount == 0) {
            return "Comentarios existem, mas podem estar pouco separados do codigo.";
        }
        return "Nenhuma observação crítica detectada.";
    }

    private static String classify(double score) {
        if (score >= 0.85) {
            return "EXCELENTE";
        }
        if (score >= 0.70) {
            return "BOM";
        }
        if (score >= 0.50) {
            return "REGULAR";
        }
        return "CRÍTICO";
    }

    private static String center(String text, int width) {
        if (text.length() >= width) {
            return text;
        }

        int totalPadding = width - text.length();
        int leftPadding = totalPadding / 2;
        int rightPadding = totalPadding - leftPadding;
        return " ".repeat(leftPadding) + text + " ".repeat(rightPadding);
    }

    private static String padRight(String text, int width) {
        if (text.length() >= width) {
            return text.substring(0, width);
        }
        return text + " ".repeat(width - text.length());
    }

    private static String row(String text) {
        return "| " + padRight(text, TABLE_INNER_WIDTH) + " |";
    }

    private static String emptyRow() {
        return row("");
    }

    private static String formatDouble(double value, int decimals) {
        return String.format(Locale.US, "%1$." + decimals + "f", value);
    }
}
