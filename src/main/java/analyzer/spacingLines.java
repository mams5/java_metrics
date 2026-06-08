package analyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Feature: Espaçamento e Linhas em Branco (NELB)
 *
 * Disciplina: IF006 — Legibilidade e Manutenibilidade de Código
 * Universidade Federal de Pernambuco — CIn/UFPE
 *
 * Calcula a nota NELB de 0 a 100, representando a qualidade do
 * espaçamento vertical de um arquivo Java.
 *
 * Fórmula:
 *   NELB = PROP×35 + SEP_MÉTODOS×30 + SEM_EXCESSO×20 + AGRUP×10 + ESP_COMENT×5
 *
 * Cada componente gera um valor entre 0 e 1, medido diretamente do código.
 *
 * Uso no Main do grupo:
 *   String sourceCode = Files.readString(filePath);
 *   SpacingLines SpacingLines = new SpacingLines();
 *   SpacingLines SpacingResult result = SpacingLines.analyze(sourceCode);
 *   reportGenerator.generateSpacing(result);
 *
 * Fundamentação:
 *   Buse & Weimer (TSE 2010): '# blank lines' tem maior poder preditivo
 *   de legibilidade do que comentários — validado com 120 avaliadores humanos.
 *
 *   Sergeyuk et al. (ICPC 2024): 'Visual Organization' é um dos 12 aspectos
 *   que 390 desenvolvedores reais usam para julgar legibilidade de código.
 *
 *   Google Java Style Guide: separação entre métodos é regra obrigatória;
 *   múltiplas linhas em branco consecutivas nunca são encorajadas.
 *
 *   Cornell Java Style Guide: comentários de bloco devem ser precedidos
 *   por linha em branco para criar vínculo visual claro.
 *
 * Limitações assumidas:
 *   - Não avalia indentação (espaçamento horizontal).
 *   - Arquivos com menos de 10 linhas retornam nota neutra (50.0).
 *   - Não distingue linhas em branco intencionais de acidentais.
 *   - Não cobre código gerado automaticamente (boilerplate).
 *   - Pesos são hipótese inicial — serão ajustados na Fase 2 com dataset CODE-UP.
 */
public class spacingLines {

    // ─── Constantes ────────────────────────────────────────────────────────

    private static final Pattern METHOD_START = Pattern.compile(
        "^\\s*(public|private|protected|static|final|synchronized|abstract)"
        + "[^;]*\\{"
    );

    /** Limiar de linhas úteis para considerar um método "longo". */
    private static final int METODO_LONGO_THRESHOLD = 15;

    /** Número de linhas em branco consecutivas para ser considerado "excessivo". */
    private static final int EXCESSO_THRESHOLD = 3;

    /**
     * Intervalo ideal de proporção de linhas em branco.
     * Hipótese inicial do grupo — será ajustada na Fase 2 com dataset CODE-UP.
     */
    private static final double PROP_MIN = 0.10;
    private static final double PROP_MAX = 0.25;

    // ─── Resultado ─────────────────────────────────────────────────────────

    /**
     * Encapsula os resultados dos 5 componentes e a nota final NELB.
     */
    public static class SpacingResult {

        /** Proporção de linhas em branco normalizada (0–1). Peso: 35. */
        public final double prop;

        /** % de métodos com ao menos 1 linha em branco antes. Peso: 30. */
        public final double sepMetodos;

        /** Ausência de blocos de 3+ linhas em branco consecutivas. Peso: 20. */
        public final double semExcesso;

        /** % de métodos longos com linhas em branco internas. Peso: 10. */
        public final double agrup;

        /** % de comentários de bloco precedidos por linha em branco. Peso: 5. */
        public final double espComent;

        /** Nota final NELB de 0 a 100. */
        public final double nelb;

        public SpacingResult(double prop, double sepMetodos, double semExcesso,
                             double agrup, double espComent) {
            this.prop       = clamp(prop);
            this.sepMetodos = clamp(sepMetodos);
            this.semExcesso = clamp(semExcesso);
            this.agrup      = clamp(agrup);
            this.espComent  = clamp(espComent);
            this.nelb = this.prop * 35
                      + this.sepMetodos * 30
                      + this.semExcesso * 20
                      + this.agrup * 10
                      + this.espComent * 5;
        }

        private static double clamp(double v) {
            return Math.max(0.0, Math.min(1.0, v));
        }

        /**
         * Converte NELB (0–100) para escala 0–1.
         * Use ao somar com as demais features no ScoringEngine.
         */
        public double nelb01() {
            return nelb / 100.0;
        }

        @Override
        public String toString() {
            return String.format(
                "NELB=%.1f | prop=%.2f | sep=%.2f | exc=%.2f | agrup=%.2f | coment=%.2f",
                nelb, prop, sepMetodos, semExcesso, agrup, espComent
            );
        }
    }

    // ─── Método principal ──────────────────────────────────────────────────

    /**
     * Analisa o espaçamento do código Java recebido como String.
     * Compatível com o padrão do Main do grupo:
     *   String sourceCode = Files.readString(filePath);
     *
     * @param sourceCode conteúdo do arquivo Java como String
     * @return SpacingResult com os 5 componentes e a nota final (0–100)
     */
    public SpacingResult analyze(String sourceCode) {
        List<String> lines = Arrays.asList(sourceCode.split("\n"));

        if (lines.size() < 10) {
            return new SpacingResult(0.5, 0.5, 1.0, 1.0, 1.0);
        }

        return new SpacingResult(
            calcularProporcao(lines),
            calcularSeparacaoMetodos(lines),
            calcularSemExcesso(lines),
            calcularAgrupamentoInterno(lines),
            calcularEspacoComentarios(lines)
        );
    }

    // ─── Componente 1: Proporção de linhas em branco (peso 35) ────────────

    /**
     * Mede: linhas_branco / total_linhas
     * Intervalo ideal: 10%–25% (hipótese do grupo, validada na Fase 2).
     *
     * Buse & Weimer (TSE 2010): '# blank lines' é a feature com maior
     * poder preditivo de legibilidade — supera comentários.
     */
    private double calcularProporcao(List<String> lines) {
        int total = lines.size();
        long brancos = lines.stream().filter(l -> l.trim().isEmpty()).count();
        double proporcao = (double) brancos / total;

        if (proporcao >= PROP_MIN && proporcao <= PROP_MAX) {
            double centro = (PROP_MIN + PROP_MAX) / 2.0;
            double raio   = (PROP_MAX - PROP_MIN) / 2.0;
            double dist   = Math.abs(proporcao - centro) / raio;
            return Math.max(0.7, 1.0 - dist * 0.3);
        } else if (proporcao < PROP_MIN) {
            return proporcao / PROP_MIN;
        } else {
            return Math.max(0.0, 1.0 - (proporcao - PROP_MAX) * 4.0);
        }
    }

    // ─── Componente 2: Separação entre métodos (peso 30) ──────────────────

    /**
     * Mede: % de métodos com ao menos 1 linha em branco antes.
     *
     * Princípio de Buse & Weimer aplicado;
     * Google Java Style Guide torna isso regra obrigatória.
     */
    private double calcularSeparacaoMetodos(List<String> lines) {
        int total = 0;
        int comSeparacao = 0;

        for (int i = 1; i < lines.size(); i++) {
            if (METHOD_START.matcher(lines.get(i)).find()) {
                total++;
                if (lines.get(i - 1).trim().isEmpty()) {
                    comSeparacao++;
                }
            }
        }

        return total == 0 ? 1.0 : (double) comSeparacao / total;
    }

    // ─── Componente 3: Ausência de blocos excessivos (peso 20) ────────────

    /**
     * Mede: penaliza ocorrências de 3+ linhas em branco consecutivas.
     *
     * Excesso prejudica tanto quanto ausência.
     * Google Style Guide: múltiplas linhas em branco nunca são encorajadas.
     */
    private double calcularSemExcesso(List<String> lines) {
        int blocos = 0;
        int consecutivas = 0;

        for (String line : lines) {
            if (line.trim().isEmpty()) {
                consecutivas++;
                if (consecutivas == EXCESSO_THRESHOLD) {
                    blocos++;
                }
            } else {
                consecutivas = 0;
            }
        }

        return blocos == 0 ? 1.0 : Math.max(0.0, 1.0 - blocos * 0.25);
    }

    // ─── Componente 4: Agrupamento lógico interno (peso 10) ───────────────

    /**
     * Mede: % de métodos longos (>=15 linhas úteis) com linhas em branco
     * separando blocos internos.
     *
     * Sergeyuk et al. (ICPC 2024): 'Visual Organization' é um dos 12
     * aspectos que desenvolvedores reais usam para julgar legibilidade.
     */
    private double calcularAgrupamentoInterno(List<String> lines) {
        List<List<String>> metodosLongos = extrairMetodosLongos(lines);

        if (metodosLongos.isEmpty()) {
            return 1.0;
        }

        long comAgrupamento = metodosLongos.stream()
            .filter(corpo -> corpo.stream().anyMatch(l -> l.trim().isEmpty()))
            .count();

        return (double) comAgrupamento / metodosLongos.size();
    }

    // ─── Componente 5: Espaço antes de comentários (peso 5) ───────────────

    /**
     * Mede: % de comentários de bloco precedidos por linha em branco.
     *
     * Recomendação do Cornell Java Style Guide — detalhe de acabamento
     * que completa a organização visual dos demais componentes.
     */
    private double calcularEspacoComentarios(List<String> lines) {
        int total = 0;
        int comEspaco = 0;

        for (int i = 1; i < lines.size(); i++) {
            String t = lines.get(i).trim();
            if (t.startsWith("//") || t.startsWith("/*") || t.startsWith("/**")) {
                total++;
                if (lines.get(i - 1).trim().isEmpty()) {
                    comEspaco++;
                }
            }
        }

        return total == 0 ? 1.0 : (double) comEspaco / total;
    }

    // ─── Auxiliar: extração de métodos longos ──────────────────────────────

    private List<List<String>> extrairMetodosLongos(List<String> lines) {
        List<List<String>> resultado = new ArrayList<>();
        int i = 0;

        while (i < lines.size()) {
            if (METHOD_START.matcher(lines.get(i)).find()) {
                List<String> corpo = new ArrayList<>();
                int braces  = 0;
                boolean started = false;
                int linhasUteis = 0;

                for (int j = i; j < lines.size(); j++) {
                    String line = lines.get(j);
                    corpo.add(line);

                    for (char c : line.toCharArray()) {
                        if (c == '{') { braces++; started = true; }
                        if (c == '}') braces--;
                    }

                    String t = line.trim();
                    if (!t.isEmpty() && !t.startsWith("//") && !t.startsWith("*")) {
                        linhasUteis++;
                    }

                    if (started && braces == 0) {
                        if (linhasUteis >= METODO_LONGO_THRESHOLD) {
                            resultado.add(new ArrayList<>(corpo));
                        }
                        i = j + 1;
                        break;
                    }
                }

                if (!started) i++;
            } else {
                i++;
            }
        }

        return resultado;
    }
}
