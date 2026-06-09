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

        double densityScore = averageDensityScore(densityResults);
        double methodScore = normalize(scoringEngine.getCompliancePercentage());
        double commentScore = scoringEngine.getNpcScore();
        double lineScore = normalize(scoringEngine.getLineLengthPercentage());
        double spacingScore = averageSpacingScore(spacingResults);

        System.out.println(BORDER_80);
        System.out.println(center("RELATORIO DE MANUTENABILIDADE DE CODIGO", 80));
        System.out.println(BORDER_80);
        System.out.println("Data da analise: " + LocalDateTime.now().format(DATE_FORMATTER));
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
        System.out.println(center("Fim do Relatorio - Reavalie o codigo apos as correcoes", 80));
        System.out.println(BORDER_80);
    }

    private static void printDensitySection(List<codeContext.StructuresDensityResult> densityResults, double densityScore) {
        System.out.println(BORDER_77);
        System.out.println(row("DENSIDADE DE ESTRUTURAS (Structural Density)"));
        System.out.println(BORDER_77);
        System.out.println(row("Formula: NDEC = 1 / (1 + (estruturas x profundidade media))"));
        System.out.println(BORDER_77);
        System.out.println(emptyRow());

        for (codeContext.StructuresDensityResult result : densityResults) {
            double ndc = scoringEngine.calculateNdec(result.structuresPerTenLines, result.averageDepth);
            System.out.println(row("Snippet: " + result.snippetName));
            System.out.println(row("  Linhas uteis: " + result.usefulLines));
            System.out.println(row("  Estruturas (if/for/while/try): " + result.structures));
            System.out.println(row("  Profundidade media: " + formatDouble(result.averageDepth, 2)));
            System.out.println(row("  Estruturas por 10 linhas: " + formatDouble(result.structuresPerTenLines, 2)));
            System.out.println(row("  NDEC: " + formatDouble(ndc, 4)));
            System.out.println(row("  Fonte: " + (result.parsedWithAst ? "AST" : "fallback textual")));
            System.out.println(row("  Avaliacao: " + classify(ndc)));
            System.out.println(emptyRow());
        }

        System.out.println(BORDER_77);
        System.out.println(row("TOTAL DE SNIPPETS: " + densityResults.size()));
        System.out.println(row("MEDIA NDEC GLOBAL: " + formatDouble(densityScore, 4)));
        System.out.println(row("CLASSIFICACAO GERAL: " + classify(densityScore)));
        System.out.println(BORDER_77);
    }

    private static void printMethodSection(double methodScore) {
        int methodsAboveLimit = Math.max(0, codeContext.totalMethods - codeContext.methodsBelowBenchmark);

        System.out.println(BORDER_77);
        System.out.println(row("TAMANHO DOS METODOS (Method Length)"));
        System.out.println(BORDER_77);
        System.out.println(row("Benchmark: " + codeContext.METHOD_LINE_BENCHMARK + " linhas uteis por metodo"));
        System.out.println(BORDER_77);
        System.out.println(emptyRow());
        System.out.println(row("Total de metodos analisados: " + codeContext.totalMethods));
        System.out.println(row("Metodos dentro do limite: " + codeContext.methodsBelowBenchmark));
        System.out.println(row("Metodos acima do limite: " + methodsAboveLimit));
        System.out.println(row("Porcentagem de conformidade: " + formatDouble(scoringEngine.getCompliancePercentage(), 2) + "%"));
        System.out.println(emptyRow());
        System.out.println(BORDER_77);
        System.out.println(row("CLASSIFICACAO: " + classify(methodScore)));
        System.out.println(BORDER_77);
    }

    private static void printCommentSection(double commentScore) {
        double benchmark = (codeContext.totalUsefulLines / 10.0) + (codeContext.totalMethods * 2.0);

        System.out.println(BORDER_77);
        System.out.println(row("PROPORCAO DE COMENTARIOS (Comment Proportion)"));
        System.out.println(BORDER_77);
        System.out.println(emptyRow());
        System.out.println(row("Linhas uteis (codigo): " + codeContext.usefulLines));
        System.out.println(row("Linhas de comentarios: " + codeContext.totalCommentLines));
        System.out.println(row("Proporcao comentario/codigo: " + formatDouble(commentRatio(), 2) + "%"));
        System.out.println(row("Benchmark calculado: (" + codeContext.totalUsefulLines + "/10) + (" + codeContext.totalMethods + "x2) = " + formatDouble(benchmark, 2)));
        System.out.println(row("Nota NPC (0 a 1): " + formatDouble(commentScore, 4)));
        System.out.println(emptyRow());
        System.out.println(BORDER_77);
        System.out.println(row("CLASSIFICACAO: " + classify(commentScore)));
        System.out.println(BORDER_77);
    }

    private static void printLineLengthSection(double lineScore) {
        System.out.println(BORDER_77);
        System.out.println(row("COMPRIMENTO DAS LINHAS (Line Length)"));
        System.out.println(BORDER_77);
        System.out.println(row("Limite estabelecido: " + codeContext.LINE_LENGTH_BENCHMARK + " caracteres por linha"));
        System.out.println(BORDER_77);
        System.out.println(emptyRow());
        System.out.println(row("Total de linhas uteis analisadas: " + codeContext.totalUsefulLines));
        System.out.println(row("Linhas adequadas (<= 80): " + codeContext.linesBelowBenchmark));
        System.out.println(row("Linhas acima do limite: " + lineLengh.getViolations().size()));
        System.out.println(row("Porcentagem de conformidade: " + formatDouble(scoringEngine.getLineLengthPercentage(), 2) + "%"));
        System.out.println(emptyRow());
        System.out.println(BORDER_77);
        System.out.println(row("CLASSIFICACAO: " + classify(lineScore)));
        System.out.println(BORDER_77);
        System.out.println(emptyRow());
        System.out.println(row("LINHAS QUE EXCEDEM O LIMITE:"));
        System.out.println(emptyRow());

        if (lineLengh.getViolations().isEmpty()) {
            System.out.println(row("  Nenhuma violacao encontrada."));
        } else {
            int shown = 0;
            for (lineLengh.Violation violation : lineLengh.getViolations()) {
                if (shown == 5) {
                    int remaining = lineLengh.getViolations().size() - shown;
                    System.out.println(row("  ... e mais " + remaining + " violacoes nao exibidas"));
                    break;
                }

                String header = String.format(Locale.US,
                        "Linha %d | %d caracteres | Excesso: +%d | Sugestao: %s",
                        violation.lineNumber, violation.lineLength, violation.excess, violation.suggestion);
                System.out.println(row(header));
                System.out.println(row("  Conteudo: " + violation.content));
                System.out.println(emptyRow());
                shown++;
            }
        }

        System.out.println(BORDER_77);
    }

    private static void printSpacingSection(List<codeContext.SpacingLinesResult> spacingResults, double spacingScore) {
        System.out.println(BORDER_77);
        System.out.println(row("ESPACAMENTO E FORMATACAO (Spacing & Formatting)"));
        System.out.println(BORDER_77);
        System.out.println(row("Metrica avaliada: Linhas em branco"));
        System.out.println(BORDER_77);
        System.out.println(emptyRow());

        if (spacingResults.isEmpty()) {
            System.out.println(row("Nenhum arquivo analisado para espacamento."));
        } else {
            for (codeContext.SpacingLinesResult result : spacingResults) {
                double resultScore = scoringEngine.calculateNelbScore(result) / 100.0;
                System.out.println(row("Total de linhas do arquivo: " + result.totalLines));
                System.out.println(row("Linhas de codigo + comentarios: " + (result.totalLines - result.blankLines)));
                System.out.println(row("Linhas em branco: " + result.blankLines));
                System.out.println(row("Proporcao de linhas em branco: " + formatDouble(result.totalLines == 0 ? 0.0 : (result.blankLines * 100.0 / result.totalLines), 2) + "%"));
                System.out.println(row("Linhas em branco consecutivas (2 ou mais): " + result.excessiveBlankLinesCount + " ocorrencias"));
                System.out.println(row("Nota NELB: " + formatDouble(resultScore, 4)));
                System.out.println(row("CLASSIFICACAO: " + classify(resultScore)));
                System.out.println(emptyRow());
                System.out.println(row("OBSERVACOES:"));
                System.out.println(row("  " + buildSpacingObservation(result)));
                System.out.println(emptyRow());
            }
        }

        System.out.println(BORDER_77);
    }

    private static void printSummarySection(double densityScore, double methodScore, double commentScore, double lineScore, double spacingScore) {
        double finalScore = densityScore + methodScore + commentScore + lineScore + spacingScore;

        System.out.println(BORDER_80);
        System.out.println(center("RESUMO GERAL E SCORE FINAL", 80));
        System.out.println(BORDER_80);
        System.out.println();
        System.out.println(BORDER_77);
        System.out.println(row("METRICA                    | PONTUACAO (0-1) | CLASSIFICACAO"));
        System.out.println(BORDER_77);
        System.out.println(row("Densidade de Estruturas    | " + formatDouble(densityScore, 4) + " | " + classify(densityScore)));
        System.out.println(row("Tamanho dos Metodos        | " + formatDouble(methodScore, 4) + " | " + classify(methodScore)));
        System.out.println(row("Proporcao de Comentarios   | " + formatDouble(commentScore, 4) + " | " + classify(commentScore)));
        System.out.println(row("Comprimento das Linhas     | " + formatDouble(lineScore, 4) + " | " + classify(lineScore)));
        System.out.println(row("Espacamento e Formatacao   | " + formatDouble(spacingScore, 4) + " | " + classify(spacingScore)));
        System.out.println(BORDER_77);
        System.out.println(row("SCORE FINAL DE MANUTENABILIDADE: " + formatDouble(finalScore, 2) + " / 5"));
        System.out.println(row("CLASSIFICACAO FINAL: " + classify(finalScore / 5.0)));
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
        return "Nenhuma observacao critica detectada.";
    }

    private static double averageDensityScore(List<codeContext.StructuresDensityResult> densityResults) {
        if (densityResults.isEmpty()) {
            return 0.0;
        }

        double total = 0.0;
        for (codeContext.StructuresDensityResult result : densityResults) {
            total += result.score;
        }
        return total / densityResults.size();
    }

    private static double averageSpacingScore(List<codeContext.SpacingLinesResult> spacingResults) {
        if (spacingResults.isEmpty()) {
            return 0.0;
        }

        double total = 0.0;
        for (codeContext.SpacingLinesResult result : spacingResults) {
            total += scoringEngine.calculateNelbScore(result) / 100.0;
        }
        return total / spacingResults.size();
    }

    private static double normalize(double scorePercentage) {
        return Math.max(0.0, Math.min(1.0, scorePercentage / 100.0));
    }

    private static double commentRatio() {
        if (codeContext.usefulLines == 0) {
            return 0.0;
        }
        return (double) codeContext.totalCommentLines * 100.0 / codeContext.usefulLines;
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
        return "CRITICO";
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
