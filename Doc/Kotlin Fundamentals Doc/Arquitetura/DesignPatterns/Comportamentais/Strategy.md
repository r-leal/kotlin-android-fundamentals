O padrão **Strategy** (Estratégia) é um modelo de projeto que permite que um objeto mude o seu comportamento durante a execução do programa. 

A forma mais fácil de entender é pensar em um **herói de videogame** que pode trocar de armas: o herói é o mesmo, mas a maneira como ele ataca muda completamente dependendo da "estratégia" (arma) que ele estiver usando no momento.

Em vez de criar um código cheio de condições complicadas (como muitos `if` e `else`) dentro de uma única classe, você separa cada comportamento em sua própria estrutura, tornando-os **intercambiáveis**.

## Exemplo Prático em Kotlin

Imagine que estamos desenvolvendo o herói mencionado. Primeiro, definimos um "contrato" (interface) que todas as armas devem seguir.

1. **O Contrato (Interface):**

```kotlin
interface Arma {
    fun disparar(x: Int, y: Int): String
}
```

2. **As Estratégias (Implementações):** Criamos diferentes classes para cada comportamento específico.

```kotlin
class Pistola : Arma {
    override fun disparar(x: Int, y: Int) = "Tiro reto em ($x, $y)"
}

class Boomerang : Arma {
    override fun disparar(x: Int, y: Int) = "Tiro que vai e volta para ($x, $y)"
}
```

3. **O Contexto (O Herói):** O herói guarda uma referência para a arma atual e delega a ação de atirar para ela.

```kotlin
class Heroi {
    // Começa com a pistola por padrão
    var armaAtual: Arma = Pistola()

    fun atacar(x: Int, y: Int) {
        println(armaAtual.disparar(x, y))
    }
}

fun main() {
    val heroi = Heroi()
    heroi.atacar(10, 20) // Saída: Tiro reto...
    
    // Mudando a estratégia em tempo de execução
    heroi.armaAtual = Boomerang()
    heroi.atacar(10, 20) // Saída: Tiro que vai e volta...
}
```

## O "Jeito Kotlin" de simplificar o Strategy

O Kotlin trata **funções como cidadãs de primeira classe**, o que significa que você pode guardar uma função dentro de uma variável ou passá-la como argumento. Isso permite implementar o padrão Strategy de forma muito mais simples, sem precisar criar várias classes, usando apenas funções ou referências de membros (usando `::`).

## Aplicações no Android e Vida Real

Você encontrará o padrão Strategy em diversos lugares no desenvolvimento Android moderno:

- **Kotlin Flows:** A biblioteca de fluxos de dados do Android utiliza estratégias para decidir quando um fluxo deve começar a compartilhar dados, como o `SharingStarted.Lazily` (começa apenas quando alguém observa) ou `SharingStarted.WhileSubscribed`.
- **Sistemas de Pagamento:** Um aplicativo de compras pode usar o Strategy para permitir que o usuário escolha entre pagar com "Cartão de Crédito", "Pix" ou "Boleto" no momento do checkout, onde cada método tem uma lógica de processamento diferente.
- **Ordenação de Listas:** Ao usar funções como `sortBy`, você está passando uma "estratégia" de comparação para que o sistema saiba como organizar os itens da sua lista.

## **Vantagens:**

1. **Código Limpo:** Você elimina blocos gigantes de `if/else` ou `when` que seriam difíceis de manter.
2. **Flexibilidade:** É muito fácil adicionar uma nova arma ou método de pagamento sem mexer no código que já funciona.
3. **Testabilidade:** Como cada comportamento está em sua própria função ou classe, fica muito mais simples testar cada um isoladamente