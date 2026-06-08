package context;

import java.util.ArrayList;
import java.util.List;

public class codeContext {
    // Benchmarks
    public static final int METHOD_LINE_BENCHMARK = 5;

    // Variáveis globais
    public static int totalMethods = 0;
    public static int methodsBelowBenchmark = 0;

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