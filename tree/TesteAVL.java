public class TesteAVL {

    public static void main(String[] args) {
        AVL<Integer> arvore = new AVL<>();

        System.out.println("== Inserindo 10, 5, 15, 2, 8, 22 ==");
        int[] valores = {10, 5, 15, 2, 8, 22};
        for (int v : valores) {
            arvore.inserir(v);
        }
        arvore.mostrar();

        System.out.println("\n== Inserindo 25 (deve causar rotação em 15) ==");
        arvore.inserir(25);
        arvore.mostrar();

        System.out.println("\n== Removendo 5 (substituído pelo sucessor 8) ==");
        arvore.remover(5);
        arvore.mostrar();

        System.out.println("\n== Buscas ==");
        System.out.println("Contém 22? " + arvore.buscar(22));
        System.out.println("Contém 100? " + arvore.buscar(100));
    }
}
