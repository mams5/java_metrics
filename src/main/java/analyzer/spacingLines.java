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
                if (i > 0 && lines[i - 1].trim().isEmpty() && i > 1 && lines[i - 2].trim().isEmpty()) {
                    excessiveBlankLinesCount++;
                }
                continue;
            }

            if (methodPattern.matcher(lines[i]).find()) {
                methodCount++;
                if (i > 0 && lines[i - 1].trim().isEmpty()) {
                    wellSeparatedMethods++;
                }
            }

            if (!currentLine.isEmpty() && !commentPattern.matcher(currentLine).find()) {
                int contiguousCode = 0;
                int checkIndex = i;
                while (checkIndex < totalLines && !lines[checkIndex].trim().isEmpty() && !commentPattern.matcher(lines[checkIndex]).find()) {
                    contiguousCode++;
                    checkIndex++;
                }
                if (contiguousCode > 15) {
                    groupedLinesCount++;
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
