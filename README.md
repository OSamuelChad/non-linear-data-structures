# 🌳 Non-Linear Data Structures

> Uma coleção completa e educativa de estruturas de dados não-lineares com implementações exemplares e documentação detalhada.

---

## 📋 Sobre o Projeto

Este repositório contém implementações didáticas de **estruturas de dados não-lineares** em **C++** e **Java**, incluindo árvores, grafos e estruturas relacionadas. O projeto foi desenvolvido para fins educacionais, oferecendo código limpo, bem comentado e exemplos práticos em ambas as linguagens.

## 🎯 Estruturas Implementadas

- 🌲 **Árvores**
  - Árvore Binária de Busca (BST)
  - Árvore AVL
  - Árvore Rubro-Negra
  - Árvore B

- 🔗 **Grafos**
  - Representação por Matriz de Adjacência
  - Representação por Lista de Adjacência
  - Busca em Profundidade (DFS)
  - Busca em Largura (BFS)
  - Algoritmo de Dijkstra
  - Algoritmo de Bellman-Ford

- 📚 **Outras Estruturas**
  - Heap (Fila de Prioridade)
  - Trie
  - Disjoint Set Union (Union-Find)

## 🚀 Começando

### Pré-requisitos

**Para C++:**
- GCC 9.0+ ou Clang 10+
- CMake 3.10+

**Para Java:**
- JDK 11 ou superior
- Maven 3.6+ (opcional, para build e testes)

### Instalação

```bash
# Clone o repositório
git clone https://github.com/OSamuelChad/non-linear-data-structures.git

# Navegue até o diretório
cd non-linear-data-structures
```

**Para compilar C++:**
```bash
mkdir build
cd build
cmake ..
make
```

**Para compilar Java:**
```bash
javac -d bin src/main/java/structures/*.java
```

## 💡 Uso

**C++:**
```cpp
#include "structures/BinarySearchTree.h"

int main() {
    BinarySearchTree<int> bst;
    
    // Inserir valores
    bst.insert(50);
    bst.insert(30);
    bst.insert(70);
    
    // Buscar um valor
    bool encontrado = bst.search(30);
    std::cout << "Valor encontrado: " << (encontrado ? "sim" : "não") << std::endl;
    
    return 0;
}
```

**Java:**
```java
import structures.BinarySearchTree;

public class Main {
    public static void main(String[] args) {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        
        // Inserir valores
        bst.insert(50);
        bst.insert(30);
        bst.insert(70);
        
        // Buscar um valor
        boolean encontrado = bst.search(30);
        System.out.println("Valor encontrado: " + encontrado);
    }
}
```

## 📖 Documentação

Cada estrutura de dados possui:
- ✅ Implementação completa
- ✅ Documentação inline
- ✅ Exemplos de uso
- ✅ Testes unitários
- ✅ Análise de complexidade

## 🧪 Testes

**Para C++:**
```bash
cd build
ctest
```

**Para Java:**
```bash
javac -cp bin:lib/* -d bin tests/**/*.java
java -cp bin org.junit.runner.JUnitCore tests.*
```

## 📊 Complexidade de Tempo

| Estrutura | Inserção | Busca | Remoção |
|-----------|----------|-------|---------|
| BST | O(log n) | O(log n) | O(log n) |
| AVL | O(log n) | O(log n) | O(log n) |
| Heap | O(log n) | O(1) | O(log n) |
| Hash Table | O(1) | O(1) | O(1) |

## 🤝 Contribuindo

Contribuições são bem-vindas! Para contribuir:

1. Faça um Fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📝 Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 👨‍💻 Autor

Desenvolvido com ❤️ por você

## ⭐ Dê uma Estrela!

Se este projeto foi útil para você, considere dar uma estrela! ⭐

---

**Última atualização:** 2026  
**Status:** Ativo em desenvolvimento 🚀