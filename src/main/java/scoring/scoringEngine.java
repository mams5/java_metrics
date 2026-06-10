package scoring;

import context.codeContext;

import java.util.List;

public class scoringEngine {

    public static double getDensityScore() {
        List<codeContext.StructuresDensityResult> results = codeContext.structuresDensityResults;
        if (results.isEmpty()) {
            return 0.0;
        }

        double total = 0.0;
        for (codeContext.StructuresDensityResult result : results) {
            total += result.score;
        }
        return total / results.size();
    }

    public static double getMethodScore() {
        return normalizePercentage(getCompliancePercentage());
    }

    public static double getCommentScore() {
        return getNpcScore();
    }

    public static double getLineScore() {
        return normalizePercentage(getLineLengthPercentage());
    }

    public static double getSpacingScore() {
        List<codeContext.SpacingLinesResult> results = codeContext.spacingLinesResults;
        if (results.isEmpty()) {
            return 0.0;
        }

        double total = 0.0;
        for (codeContext.SpacingLinesResult result : results) {
            total += calculateNelbNormalizedScore(result);
        }
        return total / results.size();
    }

    public static double getFinalScore() {
        return getDensityScore() + getMethodScore() + getCommentScore() + getLineScore() + getSpacingScore();
    }

    public static double calculateNelbNormalizedScore(codeContext.SpacingLinesResult result) {
        return calculateNelbScore(result) / 100.0;
    }

    public static double getCommentRatio() {
        if (codeContext.usefulLines == 0) {
            return 0.0;
        }
        return (double) codeContext.totalCommentLines * 100.0 / codeContext.usefulLines;
    }

    public static double getCommentBenchmark() {
        return (codeContext.totalUsefulLines / 10.0) + (codeContext.totalMethods * 2.0);
    }

    private static double normalizePercentage(double scorePercentage) {
        return Math.max(0.0, Math.min(1.0, scorePercentage / 100.0));
    }

    // Calcula porcentagem de methodSizes
    public static double getCompliancePercentage() {
        if (codeContext.totalMethods == 0) {
            return 0.0;
        }
        return ((double) codeContext.methodsBelowBenchmark / codeContext.totalMethods) * 100.0;
    }

    public static double calculateNdec(double structuresPerTenLines, double averageDepth) {
        return 1.0 / (1.0 + (structuresPerTenLines * averageDepth));
    }

    // Calcula porcentagem de lineLength
    public static double getLineLengthPercentage() {
        if (codeContext.totalUsefulLines == 0) {
            return 100.0;
        }
        return ((double) codeContext.linesBelowBenchmark / codeContext.totalUsefulLines) * 100.0;
    }
    
    // Calcula a proporção de comentários do time
    public static double getNpcScore() {
        if (codeContext.totalUsefulLines == 0 && codeContext.totalMethods == 0) return 0.0;

        double benchmark = getCommentBenchmark();

        if (benchmark == 0) return 0.0; // Previne divisão por zero

        double npc = 1.0 - ((codeContext.totalCommentLines - benchmark) / benchmark);

        return Math.max(0.0, Math.min(1.0, npc));
    }

    // NOVO: Cálculos da Métrica NELB baseados no seu script
    public static double calculateNelbScore(codeContext.SpacingLinesResult res) {
        if (res.totalLines == 0) return 0.0;

        // 1. PROP: Proporção ideal de linhas em branco (alvo de ~20% do código)
        double blankRatio = (double) res.blankLines / res.totalLines;
        double propComponent = Math.max(0.0, 1.0 - Math.abs(blankRatio - 0.20) * 3);

        // 2. SEP_MÉTODOS: Métodos que possuem linha em branco antecedente
        double sepMetodosComponent = res.methodCount == 0 ? 1.0 : (double) res.wellSeparatedMethods / res.methodCount;

        // 3. SEM_EXCESSO: Penaliza blocos triplos de linhas em branco vazias
        double semExcessoComponent = Math.max(0.0, 1.0 - ((double) res.excessiveBlankLinesCount / res.totalLines) * 5);

        // 4. AGRUP: Avalia se o código possui blocos sem respiro (>15 linhas coladas)
        double agrupComponent = Math.max(0.0, 1.0 - ((double) res.groupedLinesCount / res.totalLines) * 2);

        // 5. ESP_COMENT: Comentários que possuem linhas em branco antes de começarem
        double espComentComponent = res.totalCommentsCount == 0 ? 1.0 : (double) res.wellSpacedCommentsCount / res.totalCommentsCount;

        // Fórmula ponderada baseada no seu cabeçalho
        double nelb = (propComponent * 35) + 
                      (sepMetodosComponent * 30) + 
                      (semExcessoComponent * 20) + 
                      (agrupComponent * 10) + 
                      (espComentComponent * 5);

        return Math.max(0.0, Math.min(100.0, nelb));
    }
}
