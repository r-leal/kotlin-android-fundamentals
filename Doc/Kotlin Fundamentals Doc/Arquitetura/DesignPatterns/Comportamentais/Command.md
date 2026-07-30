O padrão **Command** (Comando) é um padrão de projeto comportamental que transforma uma ação em um **objeto físico** (como um "ticket" ou um "pedido").

A melhor forma de entender é pensar em um **restaurante**:

1. Você (o Cliente) não vai até a cozinha explicar ao cozinheiro como fritar um ovo.
2. Você faz um **pedido** para o garçom. O garçom escreve esse pedido em um pedaço de papel (o **Comando**).
3. Esse papel agora contém toda a informação necessária e pode ser colocado em uma fila, grampeado, guardado para depois ou até cancelado.
4. O cozinheiro (o **Executor**) pega o papel e realiza a ação quando estiver pronto.

Na programação, isso permite que você separe quem pede a ação de quem realmente sabe como fazê-la, permitindo **agendar tarefas**, **enfileirar ações** ou criar o botão **"Desfazer"**.

---

## Exemplo Prático em Kotlin

Imagine que você está criando um jogo e quer dar uma série de ordens para um soldado (**Trooper**) se mover, mas quer que ele as execute apenas quando você der o sinal.

1. O jeito clássico (usando Interface)

Primeiro, definimos uma regra comum para todos os comandos: eles devem ter uma função para "executar".

```kotlin
// A regra: todo comando deve saber se "executar"
interface Command {
    fun execute()
}

// Um comando específico para mover o soldado
class MoveCommand(val trooper: Trooper, val x: Int, val y: Int) : Command {
    override fun execute() {
        trooper.move(x, y) // O comando sabe quem deve agir e como
    }
}
```

2. O "Jeito Kotlin" (mais simples)

Como no Kotlin as funções são tratadas como "cidadãs de primeira classe", você pode simplificar tudo usando apenas nomes para as funções (chamados de `typealias`), sem precisar criar várias classes.

```kotlin
// Dizemos que 'Command' é apenas qualquer função que não recebe nada e não retorna nada
typealias Command = () -> Unit

class Trooper {
    private val orders = mutableListOf<Command>() // Uma lista de "papéis de pedido"

    fun addOrder(order: Command) {
        orders.add(order) // Guardamos o comando na fila
    }

    fun executeOrders() {
        while (orders.isNotEmpty()) {
            val order = orders.removeFirst()
            order() // Executamos a função guardada no "papel" [11]
        }
    }
    
    fun move(x: Int, y: Int) = println("Movendo para $x, $y")
}

fun main() {
    val soldado = Trooper()
    
    // Adicionamos ordens para serem feitas DEPOIS
    soldado.addOrder { soldado.move(10, 0) }
    soldado.addOrder { soldado.move(10, 10) }
    
    println("Soldado parado... aguardando sinal.")
    soldado.executeOrders() // Agora sim ele executa tudo em sequência! [12]
}
```

---

## Aplicações Reais no Android

O padrão Command é a base de muitas interações que você vê no seu celular:

- **Botões e Menus:** No Android, quando você configura um `setOnClickListener` em um botão, você está essencialmente passando um **Comando** para o botão. O botão não sabe o que o seu código faz; ele apenas guarda aquele "bloco de código" e o "executa" quando o usuário clica.
- **Histórico de Ações (Undo/Redo):** Aplicativos de edição de fotos ou texto guardam uma lista de objetos Command que foram executados. Para "Desfazer", eles simplesmente pegam o último comando da lista e chamam uma função inversa (como um `unexecute`).
- **Notificações Agendadas:** Quando você programa um alarme ou um lembrete, o sistema guarda um "Comando" que será disparado apenas em um horário específico no futuro.

## Vantagens:

1. **Flexibilidade:** Você pode trocar o que um botão faz sem precisar mudar o código interno do botão.
2. **Organização:** Permite que ações complexas sejam "empacotadas" e passadas adiante como se fossem variáveis comuns.
3. **Controle:** Facilita o controle de tempo (executar agora vs. executar depois).