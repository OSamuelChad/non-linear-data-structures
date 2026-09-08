/**
 * Nó genérico usado pela ABP (e reaproveitado pela AVL).
 * Guarda a altura do nó para permitir calcular o Fator de Balanceamento
 * em tempo O(1) em vez de recalcular recursivamente toda vez.
 */
public class No<T extends Comparable<T>> {

    protected T chave;
    protected No<T> esquerda;
    protected No<T> direita;
    protected int altura; // altura do nó nesta subárvore (folha = 0)

    public No(T chave) {
        this.chave = chave;
        this.esquerda = null;
        this.direita = null;
        this.altura = 0;
    }

    public T getChave() {
        return chave;
    }
}
