package analyzer;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import context.codeContext;
import scoring.scoringEngine;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

public class structuresDensity {
    private structuresDensity() {
    }

    public static void analyze(Path inputPath) throws IOException {
        codeContext.structuresDensityResults.clear();

        if (Files.isDirectory(inputPath)) {
            try (Stream<Path> files = Files.list(inputPath)) {
                files.filter(Files::isRegularFile)
                        .filter(path -> path.toString().endsWith(".java"))
                        .sorted(Comparator.comparing(path -> path.getFileName().toString()))
                        .forEach(structuresDensity::analyzeFile);
            }
            return;
        }

        analyzeFile(inputPath);
    }

    private static void analyzeFile(Path filePath) {
        try {
            String sourceCode = Files.readString(filePath, StandardCharsets.UTF_8);
            int usefulLines = countUsefulLines(sourceCode);
            Totals totals = new Totals();

            visit(StaticJavaParser.parse(sourceCode), 0, totals);

            double averageDepth = totals.structures == 0 ? 0.0 : (double) totals.depthSum / totals.structures;
            double structuresPerTenLines = usefulLines == 0 ? 0.0 : (totals.structures * 10.0) / usefulLines;
            double score = scoringEngine.calculateNdec(structuresPerTenLines, averageDepth);

                codeContext.structuresDensityResults.add(new codeContext.StructuresDensityResult(
                    filePath.getFileName().toString(),
                    usefulLines,
                    totals.structures,
                    averageDepth,
                    structuresPerTenLines,
                    score,
                    true,
                    ""
            ));
        } catch (Exception exception) {
                codeContext.structuresDensityResults.add(new codeContext.StructuresDensityResult(
                    filePath.getFileName().toString(),
                    0,
                    0,
                    0.0,
                    0.0,
                    0.0,
                    false,
                    exception.getMessage() == null ? exception.getClass().getSimpleName() : exception.getMessage()
            ));
        }
    }

    private static void visit(Node node, int depth, Totals totals) {
        int nextDepth = depth;

        if (node instanceof IfStmt || node instanceof ForStmt || node instanceof ForEachStmt || node instanceof WhileStmt || node instanceof SwitchStmt) {
            nextDepth = depth + 1;
            totals.structures++;
            totals.depthSum += nextDepth;
        }

        for (Node child : node.getChildNodes()) {
            visit(child, nextDepth, totals);
        }
    }

    private static int countUsefulLines(String sourceCode) {
        int usefulLines = 0;
        boolean insideBlockComment = false;

        for (String line : sourceCode.split("\\R")) {
            String trimmed = line.trim();

            if (trimmed.isEmpty()) {
                continue;
            }

            if (insideBlockComment) {
                if (trimmed.contains("*/")) {
                    insideBlockComment = false;
                }
                continue;
            }

            if (trimmed.startsWith("//")) {
                continue;
            }

            if (trimmed.startsWith("/*")) {
                insideBlockComment = !trimmed.contains("*/");
                continue;
            }

            usefulLines++;
        }

        return usefulLines;
    }

    private static final class Totals {
        private int structures;
        private int depthSum;
    }
}
