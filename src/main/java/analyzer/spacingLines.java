package analyzer;

import context.codeContext;
import java.nio.file.Path;
import java.util.regex.Pattern;

public class spacingLines {

    public static void analyze(Path filePath, String sourceCode) {
        String fileName = filePath.getFileName().toString();
        String[] lines = sourceCode.split("\\r?\\n");
        int totalLines = lines.length;

        int blankLines = 0;
        int methodCount = 0;
        int wellSeparatedMethods = 0;
        int excessiveBlankLinesCount = 0;
        int groupedLinesCount = 0;
        int wellSpacedCommentsCount = 0;
        int totalCommentsCount = 0;

        Pattern methodPattern = Pattern.compile("(public|protected|private|static|\\s) +[\\w\\<\\>\\[\\]]+\\s+(\\w+) *\\([^\\)]*\\) *(\\{|\\s*\\{)");
        Pattern commentPattern = Pattern.compile("^\\s*(\\/\\/|\\/\\*|\\*)");

        for (int i = 0; i < totalLines; i++) {
            String currentLine = lines[i].trim();

            if (currentLine.isEmpty()) {
                blankLines++;
                // Penaliza severamente se houver mais de 2 linhas vazias seguidas (espaçamento artificial)
                if (i > 0 && lines[i - 1].trim().isEmpty()) {
                    excessiveBlankLinesCount++;
                }
                continue;
            }

            if (lines[i].length() > 120) {
                groupedLinesCount += 3; // Penalização direta no peso de agrupamento/organização
            }

            if (methodPattern.matcher(lines[i]).find()) {
                methodCount++;
                if (i > 0 && lines[i - 1].trim().isEmpty()) {
                    wellSeparatedMethods++;
                }
            }

            if (currentLine.endsWith("{") || currentLine.contains("for") || currentLine.contains("if")) {
                int lookAhead = 0;
                int checkIndex = i + 1;
                boolean foundSpacing = false;
                
                while (checkIndex < totalLines && lookAhead < 10 && !methodPattern.matcher(lines[checkIndex]).find()) {
                    if (lines[checkIndex].trim().isEmpty()) {
                        foundSpacing = true;
                        break;
                    }
                    lookAhead++;
                    checkIndex++;
                }
                
                if (!foundSpacing && lookAhead > 5) {
                    groupedLinesCount += 2; 
                }
            }

            if (commentPattern.matcher(lines[i]).find()) {
                totalCommentsCount++;
                if (i > 0 && lines[i - 1].trim().isEmpty()) {
                    wellSpacedCommentsCount++;
                }
            }
        }

        codeContext.SpacingLinesResult result = new codeContext.SpacingLinesResult(
                fileName, totalLines, blankLines, methodCount, wellSeparatedMethods,
                excessiveBlankLinesCount, groupedLinesCount, wellSpacedCommentsCount, totalCommentsCount
        );

        codeContext.spacingLinesResults.add(result);
    }
}
