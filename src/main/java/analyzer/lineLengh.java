package analyzer;

import context.codeContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class lineLengh {

    public static class Violation {
        public final int lineNumber;
        public final int lineLength;
        public final int excess;
        public final String content;
        public final String suggestion;

        public Violation(int lineNumber, int lineLength, int excess, String content, String suggestion) {
            this.lineNumber = lineNumber;
            this.lineLength = lineLength;
            this.excess = excess;
            this.content = content;
            this.suggestion = suggestion;
        }
    }

    private static final List<Violation> violations = new ArrayList<>();

    public static List<Violation> getViolations() {
        return Collections.unmodifiableList(violations);
    }

    public static void analyze(String sourceCode) {
        violations.clear();
        codeContext.totalUsefulLines = 0;
        codeContext.linesBelowBenchmark = 0;

        String[] lines = sourceCode.split("\\r?\\n", -1);
        boolean inBlockComment = false;

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            String trimmed = line.trim();

            if (trimmed.isEmpty()) continue;

            if (inBlockComment) {
                if (trimmed.contains("*/")) {
                    inBlockComment = false;
                }
                continue;
            }

            if (trimmed.startsWith("/*")) {
                if (!trimmed.contains("*/")) {
                    inBlockComment = true;
                }
                continue;
            }

            if (trimmed.startsWith("//")) {
                continue;
            }

            codeContext.totalUsefulLines++;

            String effectiveLine = stripInlineComment(line);
            int length = effectiveLine.length();

            if (length < codeContext.LINE_LENGTH_BENCHMARK) {
                codeContext.linesBelowBenchmark++;
            } else {
                int excess = length - codeContext.LINE_LENGTH_BENCHMARK;
                violations.add(new Violation(
                    i + 1,
                    length,
                    excess,
                    trimContent(effectiveLine),
                    suggestFix(effectiveLine)
                ));
            }
        }
    }

    static String stripInlineComment(String line) {
        boolean inString = false;
        boolean inChar = false;

        for (int i = 0; i < line.length() - 1; i++) {
            char c = line.charAt(i);

            if (c == '\\' && (inString || inChar)) {
                i++;
                continue;
            }
            if (c == '"' && !inChar)  inString = !inString;
            if (c == '\'' && !inString) inChar = !inChar;

            if (!inString && !inChar && c == '/' && line.charAt(i + 1) == '/') {
                return line.substring(0, i).stripTrailing();
            }
        }
        return line;
    }

    private static String trimContent(String line) {
        String t = line.trim();
        int limit = codeContext.LINE_LENGTH_BENCHMARK;
        return t.length() <= limit ? t : t.substring(0, limit) + "...";
    }

    private static String suggestFix(String line) {
        String t = line.trim();
        long commas = t.chars().filter(c -> c == ',').count();
        if (commas >= 3) {
            return "Separar parâmetros em linhas individuais";
        }
        if (t.contains("&&") || t.contains("||")
                || t.startsWith("if ") || t.startsWith("if(")
                || t.startsWith("while ") || t.startsWith("while(")) {
            return "Dividir condição longa em variáveis intermediárias";
        }
        long dots = t.chars().filter(c -> c == '.').count();
        if (dots >= 2) {
            return "Quebrar chamada de método em múltiplas linhas";
        }
        return "Extrair variável ou dividir a expressão";
    }
}
