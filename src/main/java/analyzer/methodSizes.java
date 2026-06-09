package analyzer;

import context.codeContext;
import scoring.scoringEngine;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class methodSizes {

    public static void analyze(String sourceCode) {
        // Transforma a string de muitas linhas em uma Árvore (AST)
        CompilationUnit compUnit = StaticJavaParser.parse(sourceCode);

        // Visitor para encontrar automaticamente os métodos do arquivo
        compUnit.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(MethodDeclaration md, Void arg) {
                super.visit(md, arg);
                
                if (md.getBody().isEmpty()) {
                    return;
                }

                codeContext.totalMethods++;

                // removendo automaticamente todos os comentários e espaços extras originais.
                String cleanMethodBody = md.getBody().get().toString();
                
                int lineCount = 0;
                String[] lines = cleanMethodBody.split("\\r?\\n");
                
                for (String line : lines) {
                    String trimmed = line.trim();
                    // Ignoramos linhas vazias e as chaves de abertura/fechamento do bloco
                    if (!trimmed.isEmpty() && !trimmed.equals("{") && !trimmed.equals("}")) {
                        lineCount++;
                    }
                }

                if (lineCount <= codeContext.METHOD_LINE_BENCHMARK) {
                    codeContext.methodsBelowBenchmark++;
                }
            }
        }, null);
    }
}