/**
 * Árvore Binária de Busca "comum".
 * IMPORTANTE: os métodos públicos (inserir, remover) delegam para versões
 * recursivas PROTEGIDAS. É exatamente essas versões protegidas que a AVL
 * vai sobrescrever (@Override) para plugar o rebalanceamento, sem precisar
 * reescrever a lógica de percurso da árvore.
 */
public class ABP<T extends Comparable<T>> {

    protected No<T> raiz;

    // ---------- INSERÇÃO ----------

    public void inserir(T chave) {
        raiz = inserir(raiz, chave);
    }

    protected No<T> inserir(No<T> no, T chave) {
        if (no == null) {
            return criarNo(chave);
        }
        int cmp = chave.compareTo(no.chave);
        if (cmp < 0) {
            no.esquerda = inserir(no.esquerda, chave);
        } else if (cmp > 0) {
            no.direita = inserir(no.direita, chave);
        } else {
            return no; // chave duplicada: não insere de novo
        }
        atualizarAltura(no);
        return no;
    }

    // Método de fábrica: permite a AVL (ou outra subclasse) trocar o tipo
    // de nó criado sem duplicar o método inserir inteiro.
    protected No<T> criarNo(T chave) {
        return new No<>(chave);
    }

    // ---------- BUSCA ----------

    public boolean buscar(T chave) {
        return buscar(raiz, chave) != null;
    }

    protected No<T> buscar(No<T> no, T chave) {
        if (no == null) {
            return null;
        }
        int cmp = chave.compareTo(no.chave);
        if (cmp < 0) {
            return buscar(no.esquerda, chave);
        } else if (cmp > 0) {
            return buscar(no.direita, chave);
        } else {
            return no;
        }
    }

    // ---------- REMOÇÃO ----------

    public void remover(T chave) {
        raiz = remover(raiz, chave);
    }

    protected No<T> remover(No<T> no, T chave) {
        if (no == null) {
            return null; // chave não existe, nada a fazer
        }
        int cmp = chave.compareTo(no.chave);
        if (cmp < 0) {
            no.esquerda = remover(no.esquerda, chave);
        } else if (cmp > 0) {
            no.direita = remover(no.direita, chave);
        } else {
            // achou o nó a remover
            if (no.esquerda == null) {
                return no.direita;
            }
            if (no.direita == null) {
                return no.esquerda;
            }
            // dois filhos: substitui pelo sucessor (menor da subárvore direita)
            No<T> sucessor = menorNo(no.direita);
            no.chave = sucessor.chave;
            no.direita = remover(no.direita, sucessor.chave);
        }
        atualizarAltura(no);
        return no;
    }

    protected No<T> menorNo(No<T> no) {
        while (no.esquerda != null) {
            no = no.esquerda;
        }
        return no;
    }

    // ---------- ALTURA / FATOR DE BALANCEAMENTO ----------

    protected int altura(No<T> no) {
        return (no == null) ? -1 : no.altura;
    }

    protected void atualizarAltura(No<T> no) {
        no.altura = 1 + Math.max(altura(no.esquerda), altura(no.direita));
    }

    protected int fatorBalanceamento(No<T> no) {
        return (no == null) ? 0 : altura(no.esquerda) - altura(no.direita);
    }

    // ---------- EXIBIÇÃO ----------

    public void mostrar() {
        mostrar(raiz, 0);
    }

    // Impressão "deitada": gira a árvore 90°, direita fica em cima.
    // Cada chave aparece com seu FB entre colchetes, ex: 15[-1]
    protected void mostrar(No<T> no, int nivel) {
        if (no == null) {
            return;
        }
        mostrar(no.direita, nivel + 1);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nivel; i++) {
            sb.append("        ");
        }
        sb.append(no.chave).append(" [").append(fatorBalanceamento(no)).append("]");
        System.out.println(sb);
        mostrar(no.esquerda, nivel + 1);
    }
}
