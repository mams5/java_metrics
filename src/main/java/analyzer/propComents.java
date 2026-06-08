package analyzer;

import context.codeContext;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.Comment;
import java.util.HashSet;
import java.util.Set;

public class propComents {

    public static void analyze(String sourceCode) {
        CompilationUnit CompUnit = StaticJavaParser.parse(sourceCode);

        // Se o totalMethods estiver zerado garantimos a contagem aqui
        if (codeContext.totalMethods == 0) {
            codeContext.totalMethods = CompUnit.findAll(MethodDeclaration.class).size();
        }

        // mapea todas as linhas que possuem comentários
        Set<Integer> commentLines = new HashSet<>();
        for (Comment comment : CompUnit.getAllComments()) {
            if (comment.getRange().isPresent()) {
                int startLine = comment.getRange().get().begin.line;
                int endLine = comment.getRange().get().end.line;
                
                for (int i = startLine; i <= endLine; i++) {
                    commentLines.add(i);
                }
            }
        }
        codeContext.totalCommentLines = commentLines.size();

        int usefulLinesQt = 0;
        String[] lines = sourceCode.split("\\r?\\n");
        
        for (int i = 0; i < lines.length; i++) {
            int currentLineNumber = i + 1; // JavaParser começa a contar linhas no 1
            String trimmedLine = lines[i].trim();

            // Uma linha é útil se NÃO estiver em branco e NÃO for um comentário
            if (!trimmedLine.isEmpty() && !commentLines.contains(currentLineNumber)) {
                usefulLinesQt++;
            }
        }
        codeContext.usefulLines = usefulLinesQt;
    }
}