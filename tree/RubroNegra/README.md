# Árvore Rubro-Negra em Java

Implementação de uma árvore rubro-negra (Red-Black Tree) com as operações de **inserção**, **busca**, **remoção** e **exibição hierárquica**, todas em O(log n), exceto a exibição, que é O(n).

| Operação | Método | Complexidade |
|---|---|---|
| Inserção | `inserir(int)` | O(log n), até 2 rotações |
| Busca | `buscar(int)` | O(log n) |
| Remoção | `remover(int)` | O(log n), até 3 rotações |
| Mostrar | `mostrar()` | O(n) |

## Arquivos

- `src/ArvoreRubroNegra.java`: arquivo único com a estrutura (sentinela NIL, rotações e correções depois de inserir e de remover, segundo Cormen et al.), um verificador das 5 propriedades e o método `main` com o menu interativo para inserir, remover, buscar e mostrar a árvore.
- `Arvore-Rubro-Negra.docx`: o código formatado para entrega, com fonte monoespaçada e cores do IntelliJ IDEA.

## Como executar

Requer o JDK 17 ou superior.

```bash
cd src
javac -encoding UTF-8 ArvoreRubroNegra.java
java ArvoreRubroNegra

# ou, sem compilar antes:
java ArvoreRubroNegra.java
```

Exemplo de exibição (`[V]` = vermelho, `[P]` = preto, `(E)`/`(D)` = filho esquerdo/direito):

```
20 [P]  <- raiz
├── (D) 30 [V]
│   ├── (D) 40 [P]
│   │   ├── (D) 50 [V]
│   │   └── (E) 35 [V]
│   └── (E) 25 [P]
└── (E) 10 [V]
    ├── (D) 15 [P]
    └── (E) 5 [P]
        └── (E) 1 [V]
```
