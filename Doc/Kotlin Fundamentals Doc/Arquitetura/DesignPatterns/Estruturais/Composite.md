O padrão **Composite** (Composto) é um padrão de projeto estrutural que permite agrupar vários objetos em uma estrutura de árvore para representar hierarquias do tipo "parte-todo".

A forma mais fácil de entender é pensar em uma **pasta no seu computador**:

- Uma pasta pode conter um **arquivo** (um item individual).
- Uma pasta também pode conter **outras pastas**, que por sua vez contêm mais arquivos.
- Quando você pede para o computador "calcular o tamanho" da pasta principal, ele não se importa se lá dentro há um arquivo ou dez pastas; ele trata o grupo e o item individual da mesma maneira.

---

## Por que usar o Composite?

O objetivo principal é permitir que o "cliente" (o seu código principal) ignore a diferença entre um objeto individual e uma coleção de objetos. Em vez de escrever códigos diferentes para lidar com uma pessoa ou com uma multidão, você define uma regra comum que ambos devem seguir. Isso simplifica o design, pois você pode criar estruturas arbitrariamente complexas sem precisar mudar a lógica de quem as utiliza.

## Estrutura do Padrão

O Composite divide os participantes em três papéis principais:

1. **Componente**: Uma interface ou classe abstrata que define o comportamento comum (o "contrato") para todos os itens.
2. **Folha (Leaf)**: O objeto individual que executa a tarefa real, mas não possui "filhos" (como o soldado em um exército).
3. **Composto (Composite)**: O container que armazena "folhas" ou outros "compostos", delegando o trabalho para eles.

---

## Exemplo Prático em Kotlin

Imagine um sistema para comandar um exército de soldados (**StormTroopers**) e esquadrões (**Squads**).

```kotlin
// 1. O COMPONENTE (O contrato comum)
interface Trooper {
    fun move(x: Long, y: Long)
    fun attackRebel(x: Long, y: Long)
}

// 2. A FOLHA (O soldado individual)
class StormTrooper : Trooper {
    override fun move(x: Long, y: Long) = println("Soldado movendo para $x, $y")
    override fun attackRebel(x: Long, y: Long) = println("Soldado atirando!")
}

// 3. O COMPOSTO (O esquadrão que agrupa soldados ou outros esquadrões)
class Squad(private val units: List<Trooper>) : Trooper {
    override fun move(x: Long, y: Long) {
        // O esquadrão move todos os seus membros de uma vez
        units.forEach { it.move(x, y) }
    }

    override fun attackRebel(x: Long, y: Long) {
        // O esquadrão faz todos atacarem juntos
        units.forEach { it.attackRebel(x, y) }
    }
}

fun main() {
    val soldado1 = StormTrooper()
    val soldado2 = StormTrooper()
    
    // Criamos um esquadrão (Composite) que contém soldados (Folhas)
    val esquadraoAlfa = Squad(listOf(soldado1, soldado2))
    
    // Podemos até ter um "esquadrão de esquadrões"
    val pelotao = Squad(listOf(esquadraoAlfa, StormTrooper()))
    
    // O comando é o mesmo, não importa a complexidade!
    pelotao.attackRebel(10, 20)
}
```

---

## Aplicações Reais no Android

O exemplo mais clássico do padrão Composite no mundo real é a estrutura de telas do **Android**:

- **View (Folha)**: Itens individuais como botões (`Button`), textos (`TextView`) ou imagens (`ImageView`). Eles desenham a si mesmos na tela, mas não podem conter outros itens.
- **ViewGroup (Composite)**: Containers como o `LinearLayout`, `ConstraintLayout` ou o widget `Group`. Eles herdam da interface `View`, mas sua função é agrupar e gerenciar várias outras `Views` (incluindo outros `ViewGroups`).
- **Uniformidade**: Quando o Android precisa redesenhar a tela, ele simplesmente chama o método `draw()` no container principal. Este container, seguindo o padrão Composite, repassa a ordem para todos os seus filhos, e assim por diante, até que toda a árvore da interface seja desenhada.

## Resumo das Vantagens

- **Hierarquias Claras**: Facilita a representação de estruturas complexas.
- **Flexibilidade**: Você pode adicionar novos tipos de componentes sem quebrar o código existente.
- **Simplicidade para o Cliente**: O código que usa os objetos não precisa saber se está lidando com uma peça única ou com uma caixa cheia de peças.