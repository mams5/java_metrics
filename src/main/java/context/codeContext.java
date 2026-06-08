package context;

import java.util.ArrayList;
import java.util.List;

public class codeContext {
    // Benchmarks
    public static final int METHOD_LINE_BENCHMARK = 5;
    public static final int LINE_LENGTH_BENCHMARK = 80;

    // Variáveis globais — methodSizes
    public static int totalMethods = 0;
    public static int methodsBelowBenchmark = 0;

    // ---- FEATURE 2: Proporção de Comentários ----
    public static int usefulLines = 0;   
    public static int totalCommentLines = 0; 

    // Variáveis globais — lineLength
    public static int totalUsefulLines = 0;
    public static int linesBelowBenchmark = 0;

    public static final List<StructuresDensityResult> structuresDensityResults = new ArrayList<>();

    public static final class StructuresDensityResult {
        public final String snippetName;
        public final int usefulLines;
        public final int structures;
        public final double averageDepth;
        public final double structuresPerTenLines;
        public final double score;
        public final boolean parsedWithAst;
        public final String note;

        public StructuresDensityResult(String snippetName, int usefulLines, int structures, double averageDepth,
                                       double structuresPerTenLines, double score, boolean parsedWithAst, String note) {
            this.snippetName = snippetName;
            this.usefulLines = usefulLines;
            this.structures = structures;
            this.averageDepth = averageDepth;
            this.structuresPerTenLines = structuresPerTenLines;
            this.score = score;
            this.parsedWithAst = parsedWithAst;
            this.note = note;
        }
    }
}