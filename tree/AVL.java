/**
 * Árvore AVL: HERDA de ABP e só adiciona o rebalanceamento.
 * Toda a lógica de percurso/comparação de chaves continua sendo a da ABP;
 * aqui só entra a parte extra que garante O(log n).
 */
public class AVL<T extends Comparable<T>> extends ABP<T> {

    @Override
    protected No<T> inserir(No<T> no, T chave) {
        no = super.inserir(no, chave); // insere normalmente e já atualiza altura
        return balancear(no);
    }

    @Override
    protected No<T> remover(No<T> no, T chave) {
        no = super.remover(no, chave); // remove normalmente e já atualiza altura
        return balancear(no);
    }

    /**
     * Verifica o FB do nó e aplica a rotação adequada.
     * Chamado no caminho de volta da recursão (pós-ordem), então sempre
     * balanceia de baixo para cima — é o que garante O(log n).
     */
    protected No<T> balancear(No<T> no) {
        if (no == null) {
            return null;
        }

        int fb = fatorBalanceamento(no);

        if (fb > 1) { // pesado à esquerda
            if (fatorBalanceamento(no.esquerda) < 0) {
                // caso esquerda-direita: primeiro "endireita" o filho esquerdo
                no.esquerda = rotacaoEsquerda(no.esquerda);
            }
            no = rotacaoDireita(no); // caso esquerda-esquerda (ou já corrigido acima)
        } else if (fb < -1) { // pesado à direita
            if (fatorBalanceamento(no.direita) > 0) {
                // caso direita-esquerda: primeiro "endireita" o filho direito
                no.direita = rotacaoDireita(no.direita);
            }
            no = rotacaoEsquerda(no); // caso direita-direita (ou já corrigido acima)
        }

        return no;
    }

    // Rotação simples à direita (usada quando a árvore está pesada à esquerda)
    protected No<T> rotacaoDireita(No<T> no) {
        No<T> novaRaiz = no.esquerda;
        no.esquerda = novaRaiz.direita;
        novaRaiz.direita = no;

        atualizarAltura(no);       // atualiza o antigo (agora filho) primeiro
        atualizarAltura(novaRaiz); // depois a nova raiz da subárvore
        return novaRaiz;
    }

    // Rotação simples à esquerda (usada quando a árvore está pesada à direita)
    protected No<T> rotacaoEsquerda(No<T> no) {
        No<T> novaRaiz = no.direita;
        no.direita = novaRaiz.esquerda;
        novaRaiz.esquerda = no;

        atualizarAltura(no);
        atualizarAltura(novaRaiz);
        return novaRaiz;
    }
}
