package analyzer;

import context.codeContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class lineLenghTest {

    @BeforeEach
    void resetContext() {
        codeContext.totalUsefulLines = 0;
        codeContext.linesBelowBenchmark = 0;
    }

    @Test
    void percentageIs100WhenAllLinesAreBelowBenchmark() {
        StringBuilder src = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            src.append("int x = ").append(i).append(";\n");
        }
        lineLengh.analyze(src.toString());
        assertEquals(100.0, scoring.scoringEngine.getLineLengthPercentage(), 0.001);
        assertTrue(lineLengh.getViolations().isEmpty());
    }

    @Test
    void percentageIsZeroWhenAllLinesExceedBenchmark() {
        String longLine = "int " + "x".repeat(96) + " = 1;\n";
        StringBuilder src = new StringBuilder();
        for (int i = 0; i < 5; i++) src.append(longLine);

        lineLengh.analyze(src.toString());

        assertEquals(0, codeContext.linesBelowBenchmark);
        assertEquals(5, codeContext.totalUsefulLines);
        assertEquals(0.0, scoring.scoringEngine.getLineLengthPercentage(), 0.001);
    }

    @Test
    void percentageIsCorrectForMixedLines() {
        String shortLine = "int x = 1;\n";
        String longLine  = "int " + "y".repeat(96) + " = 1;\n";
        StringBuilder src = new StringBuilder();
        for (int i = 0; i < 8; i++) src.append(shortLine);
        for (int i = 0; i < 2; i++) src.append(longLine);

        lineLengh.analyze(src.toString());

        assertEquals(10, codeContext.totalUsefulLines);
        assertEquals(8,  codeContext.linesBelowBenchmark);
        assertEquals(80.0, scoring.scoringEngine.getLineLengthPercentage(), 0.001);
    }

    @Test
    void percentageIs100WhenNoUsefulLines() {
        lineLengh.analyze("   \n\n  \n");
        assertEquals(0, codeContext.totalUsefulLines);
        assertEquals(100.0, scoring.scoringEngine.getLineLengthPercentage(), 0.001);
    }

    @Test
    void blankLinesAreNotCountedAsUseful() {
        lineLengh.analyze("\n   \n\t\nint x = 1;\n");
        assertEquals(1, codeContext.totalUsefulLines);
    }

    @Test
    void lineCommentLinesAreNotCountedAsUseful() {
        lineLengh.analyze("// this is a comment\nint x = 1;\n// another comment\n");
        assertEquals(1, codeContext.totalUsefulLines);
    }

    @Test
    void blockCommentLinesAreNotCountedAsUseful() {
        lineLengh.analyze("/* start\n * middle\n */\nint x = 1;\n");
        assertEquals(1, codeContext.totalUsefulLines);
    }

    @Test
    void javadocBlockIsNotCountedAsUseful() {
        lineLengh.analyze("/** javadoc\n * @param x foo\n */\npublic void foo() {\n}\n");
        assertEquals(2, codeContext.totalUsefulLines);
    }

    @Test
    void singleLineBlockCommentIsNotCountedAsUseful() {
        lineLengh.analyze("/* single line block comment */\nint x = 1;\n");
        assertEquals(1, codeContext.totalUsefulLines);
    }

    @Test
    void inlineCommentIsStrippedBeforeMeasuringLength() {
        String line = "int x = someValue; // " + "x".repeat(100) + "\n";
        lineLengh.analyze(line);
        assertEquals(1, codeContext.totalUsefulLines);
        assertTrue(lineLengh.getViolations().isEmpty());
    }

    @Test
    void doubleSlashInsideStringIsNotTreatedAsComment() {
        lineLengh.analyze("String url = \"http://example.com\";\n");
        assertEquals(1, codeContext.totalUsefulLines);
        assertTrue(lineLengh.getViolations().isEmpty());
    }

    @Test
    void violationHasCorrectLineNumberAndExcess() {
        String src = "int x = 1;\n" + "int " + "y".repeat(96) + " = 1;\n";
        lineLengh.analyze(src);

        List<lineLengh.Violation> violations = lineLengh.getViolations();
        assertEquals(1, violations.size());

        lineLengh.Violation v = violations.get(0);
        assertEquals(2, v.lineNumber);
        assertEquals(105, v.lineLength);
        assertEquals(25, v.excess);
    }

    @Test
    void violationSuggestionForManyParameters() {
        String line = "someMethod(parameterOne, parameterTwo, parameterThree, parameterFour);\n";
        assertTrue(line.trim().length() > 80);
        lineLengh.analyze(line);
        assertFalse(lineLengh.getViolations().isEmpty());
        assertEquals("Separar parâmetros em linhas individuais",
                lineLengh.getViolations().get(0).suggestion);
    }

    @Test
    void violationSuggestionForLongCondition() {
        String line = "if (conditionAlpha && conditionBeta && conditionGamma && conditionDelta) {\n";
        assertTrue(line.trim().length() > 80);
        lineLengh.analyze(line);
        assertFalse(lineLengh.getViolations().isEmpty());
        assertEquals("Dividir condição longa em variáveis intermediárias",
                lineLengh.getViolations().get(0).suggestion);
    }

    @Test
    void violationSuggestionForMethodChain() {
        String line = "result = objectInstance.getService().getRepository().findAllActiveUsers();\n";
        assertTrue(line.trim().length() > 80);
        lineLengh.analyze(line);
        assertFalse(lineLengh.getViolations().isEmpty());
        assertEquals("Quebrar chamada de método em múltiplas linhas",
                lineLengh.getViolations().get(0).suggestion);
    }

    @Test
    void stripInlineComment_removesTrailingComment() {
        assertEquals("int x = 1;", lineLengh.stripInlineComment("int x = 1; // set x"));
    }

    @Test
    void stripInlineComment_preservesDoubleSlashInString() {
        String line = "String s = \"http://x.com\";";
        assertEquals(line, lineLengh.stripInlineComment(line));
    }

    @Test
    void stripInlineComment_noCommentReturnsOriginal() {
        String line = "int x = 42;";
        assertEquals(line, lineLengh.stripInlineComment(line));
    }
}
