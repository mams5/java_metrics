package main.analyzer;

import main.context.codeContext;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class methodSizes {

    public static void analyze(String sourceCode) {
        // 1. O JavaParser transforma a string de 1000+ linhas em uma Árvore (AST)
        CompilationUnit cu = StaticJavaParser.parse(sourceCode);

        // 2. Usamos um "Visitor" para encontrar automaticamente todos os métodos do arquivo
        cu.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(MethodDeclaration md, Void arg) {
                super.visit(md, arg);
                
                // Ignora métodos abstratos ou assinaturas de interface (que não possuem corpo)
                if (md.getBody().isEmpty()) {
                    return;
                }

                codeContext.totalMethods++;

                // O toString() do JavaParser gera o código do método "limpo", 
                // removendo automaticamente todos os comentários e espaços extras originais.
                String cleanMethodBody = md.getBody().get().toString();
                
                int lineCount = 0;
                String[] lines = cleanMethodBody.split("\\r?\\n");
                
                for (String line : lines) {
                    String trimmed = line.trim();
                    // Ignoramos linhas vazias e as chaves de abertura/fechamento do bloco
                    // para contar estritamente as instruções de código.
                    if (!trimmed.isEmpty() && !trimmed.equals("{") && !trimmed.equals("}")) {
                        lineCount++;
                    }
                }

                // Verifica contra o limite salvo nas variáveis globais
                if (lineCount <= codeContext.METHOD_LINE_BENCHMARK) {
                    codeContext.methodsBelowBenchmark++;
                }
            }
        }, null);
    }
}