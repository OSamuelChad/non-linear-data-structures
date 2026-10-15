import java.util.Scanner;

public class ArvoreRubroNegra {
    private static final boolean VERMELHO = true;
    private static final boolean PRETO = false;

    private static final class No {
        int chave;
        boolean cor;
        No esquerda;
        No direita;
        No pai;

        No(int chave, boolean cor, No nil) {
            this.chave = chave;
            this.cor = cor;
            this.esquerda = nil;
            this.direita = nil;
            this.pai = nil;
        }
    }

    private final No nil;
    private No raiz;
    private int tamanho;

    public ArvoreRubroNegra() {
        nil = new No(0, PRETO, null);
        nil.esquerda = nil;
        nil.direita = nil;
        nil.pai = nil;
        raiz = nil;
    }

    // ===================================================================
    // BUSCA — O(log n)
    // ===================================================================

    public boolean buscar(int chave) {
        return buscarNo(chave) != nil;
    }

    private No buscarNo(int chave) {
        No atual = raiz;
        while (atual != nil && chave != atual.chave) {
            atual = (chave < atual.chave) ? atual.esquerda : atual.direita;
        }
        return atual;
    }

    public String caminhoDaBusca(int chave) {
        StringBuilder caminho = new StringBuilder();
        No atual = raiz;
        while (atual != nil) {
            if (caminho.length() > 0) {
                caminho.append(" -> ");
            }
            caminho.append(atual.chave).append(atual.cor == VERMELHO ? "(V)" : "(P)");
            if (chave == atual.chave) {
                break;
            }
            atual = (chave < atual.chave) ? atual.esquerda : atual.direita;
        }
        return caminho.length() == 0 ? "(árvore vazia)" : caminho.toString();
    }

    // ===================================================================
    // INSERÇÃO — O(log n)
    // ===================================================================

    public boolean inserir(int chave) {
        No pai = nil;
        No atual = raiz;

        while (atual != nil) {
            pai = atual;
            if (chave == atual.chave) {
                return false;
            }
            atual = (chave < atual.chave) ? atual.esquerda : atual.direita;
        }

        No novo = new No(chave, VERMELHO, nil);
        novo.pai = pai;
        if (pai == nil) {
            raiz = novo;
        } else if (chave < pai.chave) {
            pai.esquerda = novo;
        } else {
            pai.direita = novo;
        }

        corrigirInsercao(novo);
        tamanho++;
        return true;
    }

    private void corrigirInsercao(No z) {
        while (z.pai.cor == VERMELHO) {
            No avo = z.pai.pai;

            if (z.pai == avo.esquerda) {
                No tio = avo.direita;

                if (tio.cor == VERMELHO) {
                    z.pai.cor = PRETO;
                    tio.cor = PRETO;
                    avo.cor = VERMELHO;
                    z = avo;
                } else {
                    if (z == z.pai.direita) {
                        z = z.pai;
                        rotacionarEsquerda(z);
                    }
                    z.pai.cor = PRETO;
                    z.pai.pai.cor = VERMELHO;
                    rotacionarDireita(z.pai.pai);
                }
            } else {
                No tio = avo.esquerda;

                if (tio.cor == VERMELHO) {
                    z.pai.cor = PRETO;
                    tio.cor = PRETO;
                    avo.cor = VERMELHO;
                    z = avo;
                } else {
                    if (z == z.pai.esquerda) {
                        z = z.pai;
                        rotacionarDireita(z);
                    }
                    z.pai.cor = PRETO;
                    z.pai.pai.cor = VERMELHO;
                    rotacionarEsquerda(z.pai.pai);
                }
            }
        }
        raiz.cor = PRETO;
    }

    // ===================================================================
    // REMOÇÃO — O(log n)
    // ===================================================================

    public boolean remover(int chave) {
        No z = buscarNo(chave);
        if (z == nil) {
            return false;
        }

        No y = z;
        boolean corOriginalY = y.cor;
        No x;

        if (z.esquerda == nil) {
            x = z.direita;
            transplantar(z, z.direita);
        } else if (z.direita == nil) {
            x = z.esquerda;
            transplantar(z, z.esquerda);
        } else {
            y = minimo(z.direita);
            corOriginalY = y.cor;
            x = y.direita;

            if (y.pai == z) {
                x.pai = y;
            } else {
                transplantar(y, y.direita);
                y.direita = z.direita;
                y.direita.pai = y;
            }
            transplantar(z, y);
            y.esquerda = z.esquerda;
            y.esquerda.pai = y;
            y.cor = z.cor;
        }

        if (corOriginalY == PRETO) {
            corrigirRemocao(x);
        }
        tamanho--;
        return true;
    }

    private void corrigirRemocao(No x) {
        while (x != raiz && x.cor == PRETO) {
            if (x == x.pai.esquerda) {
                No irmao = x.pai.direita;

                if (irmao.cor == VERMELHO) {
                    irmao.cor = PRETO;
                    x.pai.cor = VERMELHO;
                    rotacionarEsquerda(x.pai);
                    irmao = x.pai.direita;
                }
                if (irmao.esquerda.cor == PRETO && irmao.direita.cor == PRETO) {
                    irmao.cor = VERMELHO;
                    x = x.pai;
                } else {
                    if (irmao.direita.cor == PRETO) {
                        irmao.esquerda.cor = PRETO;
                        irmao.cor = VERMELHO;
                        rotacionarDireita(irmao);
                        irmao = x.pai.direita;
                    }
                    irmao.cor = x.pai.cor;
                    x.pai.cor = PRETO;
                    irmao.direita.cor = PRETO;
                    rotacionarEsquerda(x.pai);
                    x = raiz;
                }
            } else {
                No irmao = x.pai.esquerda;

                if (irmao.cor == VERMELHO) {
                    irmao.cor = PRETO;
                    x.pai.cor = VERMELHO;
                    rotacionarDireita(x.pai);
                    irmao = x.pai.esquerda;
                }
                if (irmao.direita.cor == PRETO && irmao.esquerda.cor == PRETO) {
                    irmao.cor = VERMELHO;
                    x = x.pai;
                } else {
                    if (irmao.esquerda.cor == PRETO) {
                        irmao.direita.cor = PRETO;
                        irmao.cor = VERMELHO;
                        rotacionarEsquerda(irmao);
                        irmao = x.pai.esquerda;
                    }
                    irmao.cor = x.pai.cor;
                    x.pai.cor = PRETO;
                    irmao.esquerda.cor = PRETO;
                    rotacionarDireita(x.pai);
                    x = raiz;
                }
            }
        }
        x.cor = PRETO;
    }

    private void transplantar(No u, No v) {
        if (u.pai == nil) {
            raiz = v;
        } else if (u == u.pai.esquerda) {
            u.pai.esquerda = v;
        } else {
            u.pai.direita = v;
        }
        v.pai = u.pai;
    }

    private No minimo(No no) {
        while (no.esquerda != nil) {
            no = no.esquerda;
        }
        return no;
    }

    // ===================================================================
    // ROTAÇÕES — O(1)
    // ===================================================================

    private void rotacionarEsquerda(No x) {
        No y = x.direita;
        x.direita = y.esquerda;
        if (y.esquerda != nil) {
            y.esquerda.pai = x;
        }
        y.pai = x.pai;
        if (x.pai == nil) {
            raiz = y;
        } else if (x == x.pai.esquerda) {
            x.pai.esquerda = y;
        } else {
            x.pai.direita = y;
        }
        y.esquerda = x;
        x.pai = y;
    }

    private void rotacionarDireita(No y) {
        No x = y.esquerda;
        y.esquerda = x.direita;
        if (x.direita != nil) {
            x.direita.pai = y;
        }
        x.pai = y.pai;
        if (y.pai == nil) {
            raiz = x;
        } else if (y == y.pai.direita) {
            y.pai.direita = x;
        } else {
            y.pai.esquerda = x;
        }
        x.direita = y;
        y.pai = x;
    }

    // ===================================================================
    // MOSTRAR
    // ===================================================================

    public String mostrar() {
        if (raiz == nil) {
            return "(árvore vazia)";
        }
        StringBuilder saida = new StringBuilder();
        saida.append(descrever(raiz)).append("  <- raiz\n");
        mostrarFilhos(raiz, "", saida);
        return saida.toString();
    }

    private void mostrarFilhos(No no, String prefixo, StringBuilder saida) {
        boolean temEsquerda = no.esquerda != nil;
        boolean temDireita = no.direita != nil;

        if (temDireita) {
            String conector = temEsquerda ? "├── " : "└── ";
            saida.append(prefixo).append(conector)
                 .append("(D) ").append(descrever(no.direita)).append('\n');
            mostrarFilhos(no.direita, prefixo + (temEsquerda ? "│   " : "    "), saida);
        }
        if (temEsquerda) {
            saida.append(prefixo).append("└── ")
                 .append("(E) ").append(descrever(no.esquerda)).append('\n');
            mostrarFilhos(no.esquerda, prefixo + "    ", saida);
        }
    }

    private String descrever(No no) {
        return no.chave + (no.cor == VERMELHO ? " [V]" : " [P]");
    }

    public String emOrdem() {
        StringBuilder saida = new StringBuilder();
        emOrdem(raiz, saida);
        return saida.length() == 0 ? "(árvore vazia)" : saida.toString().trim();
    }

    private void emOrdem(No no, StringBuilder saida) {
        if (no == nil) {
            return;
        }
        emOrdem(no.esquerda, saida);
        saida.append(no.chave).append(' ');
        emOrdem(no.direita, saida);
    }

    // ===================================================================
    // INFORMAÇÕES E VERIFICAÇÃO DAS PROPRIEDADES
    // ===================================================================

    public int tamanho() {
        return tamanho;
    }

    public boolean estaVazia() {
        return tamanho == 0;
    }

    public int altura() {
        return altura(raiz);
    }

    private int altura(No no) {
        if (no == nil) {
            return 0;
        }
        return 1 + Math.max(altura(no.esquerda), altura(no.direita));
    }

    public int alturaNegra() {
        int contador = 0;
        No atual = raiz;
        while (atual != nil) {
            atual = atual.esquerda;
            if (atual.cor == PRETO) {
                contador++;
            }
        }
        return contador;
    }

    public String verificarPropriedades() {
        if (raiz.cor != PRETO) {
            return "a raiz não é preta";
        }
        try {
            verificar(raiz, Long.MIN_VALUE, Long.MAX_VALUE);
            return null;
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }

    private int verificar(No no, long minimo, long maximo) {
        if (no == nil) {
            return 1;
        }
        if (no.chave <= minimo || no.chave >= maximo) {
            throw new IllegalStateException("ordem de busca violada no nó " + no.chave);
        }
        boolean filhoVermelho = no.esquerda.cor == VERMELHO || no.direita.cor == VERMELHO;
        if (no.cor == VERMELHO && filhoVermelho) {
            throw new IllegalStateException(
                    "nó vermelho " + no.chave + " com filho vermelho");
        }
        int esquerda = verificar(no.esquerda, minimo, no.chave);
        int direita = verificar(no.direita, no.chave, maximo);
        if (esquerda != direita) {
            throw new IllegalStateException(
                    "altura negra diferente abaixo do nó " + no.chave);
        }
        return esquerda + (no.cor == PRETO ? 1 : 0);
    }

    // ===================================================================
    // PROGRAMA PRINCIPAL (MENU DE TESTE)
    // ===================================================================

    private static final Scanner ENTRADA = new Scanner(System.in);

    public static void main(String[] args) {
        ArvoreRubroNegra arvore = new ArvoreRubroNegra();
        boolean executando = true;

        System.out.println("=========================================");
        System.out.println("        ÁRVORE RUBRO-NEGRA (JAVA)        ");
        System.out.println("=========================================");

        while (executando) {
            exibirMenu();
            String opcao = lerLinha("Escolha uma opção: ");

            switch (opcao) {
                case "1" -> menuInserir(arvore);
                case "2" -> menuRemover(arvore);
                case "3" -> menuBuscar(arvore);
                case "4" -> menuMostrar(arvore);
                case "5" -> menuInserirVarios(arvore);
                case "6" -> menuCarregarExemplo(arvore);
                case "7" -> menuInformacoes(arvore);
                case "0" -> executando = false;
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
        System.out.println("Programa encerrado.");
    }

    private static void exibirMenu() {
        System.out.println();
        System.out.println("---------------- MENU ----------------");
        System.out.println(" 1 - Inserir nó");
        System.out.println(" 2 - Remover nó");
        System.out.println(" 3 - Buscar nó");
        System.out.println(" 4 - Mostrar árvore (hierárquica)");
        System.out.println(" 5 - Inserir vários nós");
        System.out.println(" 6 - Carregar exemplo (10 20 30 15 25 5 1 35 40 50)");
        System.out.println(" 7 - Informações (tamanho, altura, validação)");
        System.out.println(" 0 - Sair");
        System.out.println("--------------------------------------");
    }

    private static void menuInserir(ArvoreRubroNegra arvore) {
        Integer chave = lerInteiro("Valor a inserir: ");
        if (chave == null) {
            return;
        }
        if (arvore.inserir(chave)) {
            System.out.println("Nó " + chave + " inserido.");
            menuMostrar(arvore);
        } else {
            System.out.println("O valor " + chave + " já existe na árvore.");
        }
    }

    private static void menuRemover(ArvoreRubroNegra arvore) {
        if (arvore.estaVazia()) {
            System.out.println("A árvore está vazia.");
            return;
        }
        Integer chave = lerInteiro("Valor a remover: ");
        if (chave == null) {
            return;
        }
        if (arvore.remover(chave)) {
            System.out.println("Nó " + chave + " removido.");
            menuMostrar(arvore);
        } else {
            System.out.println("O valor " + chave + " não foi encontrado.");
        }
    }

    private static void menuBuscar(ArvoreRubroNegra arvore) {
        Integer chave = lerInteiro("Valor a buscar: ");
        if (chave == null) {
            return;
        }
        boolean encontrado = arvore.buscar(chave);
        System.out.println("Caminho percorrido: " + arvore.caminhoDaBusca(chave));
        System.out.println(encontrado
                ? "Valor " + chave + " ENCONTRADO."
                : "Valor " + chave + " NÃO encontrado.");
    }

    private static void menuMostrar(ArvoreRubroNegra arvore) {
        System.out.println();
        System.out.println("Legenda: [V] vermelho  [P] preto  (E) esquerda  (D) direita");
        System.out.println(arvore.mostrar());
        System.out.println("Em ordem: " + arvore.emOrdem());
    }

    private static void menuInserirVarios(ArvoreRubroNegra arvore) {
        String linha = lerLinha("Digite os valores separados por espaço: ");
        if (linha.isBlank()) {
            System.out.println("Nenhum valor informado.");
            return;
        }
        for (String parte : linha.trim().split("\\s+")) {
            try {
                int chave = Integer.parseInt(parte);
                System.out.println(arvore.inserir(chave)
                        ? "  inserido: " + chave
                        : "  ignorado (duplicado): " + chave);
            } catch (NumberFormatException e) {
                System.out.println("  ignorado (não é inteiro): " + parte);
            }
        }
        menuMostrar(arvore);
    }

    private static void menuCarregarExemplo(ArvoreRubroNegra arvore) {
        int[] valores = {10, 20, 30, 15, 25, 5, 1, 35, 40, 50};
        for (int valor : valores) {
            arvore.inserir(valor);
        }
        System.out.println("Exemplo carregado.");
        menuMostrar(arvore);
    }

    private static void menuInformacoes(ArvoreRubroNegra arvore) {
        String problema = arvore.verificarPropriedades();
        System.out.println("Quantidade de nós : " + arvore.tamanho());
        System.out.println("Altura            : " + arvore.altura());
        System.out.println("Altura negra      : " + arvore.alturaNegra());
        double limite = 2 * Math.log(arvore.tamanho() + 1) / Math.log(2);
        System.out.printf("Limite teórico    : 2·log2(n+1) = %.2f%n", limite);
        String situacao = (problema == null) ? "OK" : "VIOLADA - " + problema;
        System.out.println("Propriedades RB   : " + situacao);
    }

    private static String lerLinha(String mensagem) {
        System.out.print(mensagem);
        return ENTRADA.hasNextLine() ? ENTRADA.nextLine().trim() : "0";
    }

    private static Integer lerInteiro(String mensagem) {
        String linha = lerLinha(mensagem);
        try {
            return Integer.parseInt(linha);
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida: digite um número inteiro.");
            return null;
        }
    }
}
