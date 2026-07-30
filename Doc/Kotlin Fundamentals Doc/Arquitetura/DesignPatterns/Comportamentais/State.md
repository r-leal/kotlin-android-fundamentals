O padrão **State** (Estado) é um modelo de projeto que permite que um objeto mude completamente o seu comportamento quando o seu "estado interno" muda.

A melhor forma de entender é pensar em um **humano**: se você está "Feliz", você reage a uma piada rindo; se você está "Bravo", você pode reagir à mesma piada ignorando-a. Você continua sendo a mesma pessoa, mas o que você **faz** muda dependendo do seu **estado**.

Na programação, esse padrão é usado para evitar que seu código vire uma "bagunça" de comandos `if` e `else` gigantescos toda vez que algo acontece.

## Diferença de outros padrões

Você pode confundir o State com o padrão **Strategy** (que vimos anteriormente). A diferença principal é:

- **Strategy:** O "chefe" (programador) decide de fora qual ferramenta usar.
- **State:** O próprio objeto decide mudar seu comportamento sozinho, baseado no que acontece com ele (ex: um caracol que se esconde na concha sozinho ao ser tocado).

---

## Exemplo Prático em Kotlin: O Caracol do Jogo

Imagine um inimigo em um jogo de plataforma (um caracol carnívoro). Ele tem diferentes "humores" (estados):

1. **Parado (Still):** Economizando energia.
2. **Agressivo (Aggressive):** Correndo atrás do herói.
3. **Fugindo (Retreating):** Se escondendo para lamber as feridas.

No Kotlin, usamos **Sealed Classes** (classes seladas) para representar esses estados, pois elas garantem que o computador conheça todas as opções possíveis.

```kotlin 
// 1. Definimos o que pode acontecer na vida do caracol
interface Eventos {
    fun verHeroi()
    fun levarDano()
}

// 2. Criamos os Estados (Humores) usando Sealed Interface
sealed interface Humor : Eventos

object Parado : Humor {
    override fun verHeroi() = println("Mudar para: AGRESSIVO!")
    override fun levarDano() = println("Mudar para: MORTO :(")
}

object Agressivo : Humor {
    override fun verHeroi() = println("Já estou atacando!")
    override fun levarDano() = println("Mudar para: FUGINDO!")
}

// 3. O Caracol (Contexto) que guarda o estado atual
class Caracol {
    var estadoAtual: Humor = Parado

    fun reagirAoHeroi() {
        estadoAtual.verHeroi()
        // A lógica de transição ocorreria aqui
    }
}
```

---

## Aplicações Reais no Android

Você encontrará o padrão State em quase todos os aplicativos Android modernos:

- **Ciclo de Vida de Tarefas (Coroutines):** Quando você inicia uma tarefa no Android, ela passa por estados como **Ativo**, **Cancelando**, **Cancelado** ou **Completado**. O comportamento de uma tarefa "Ativa" é diferente de uma tarefa "Cancelada".
- **Telas de Carregamento (UI States):** É muito comum uma tela ter três estados: **Carregando** (mostra um círculo girando), **Sucesso** (mostra a lista de dados) e **Erro** (mostra uma mensagem de erro e um botão de tentar novamente). O aplicativo muda o que mostra na tela mudando o objeto de estado.
- **Conexão de Rede:** Um aplicativo pode mudar seu comportamento se a conexão estiver **Conectada**, **Reconectando** ou **Desconectada**.
- **Ferramentas de Edição:** Em apps de desenho, o comportamento do toque na tela muda se o estado da ferramenta for "Lápis" (desenha linha) ou "Borracha" (apaga linha).

## Resumo:

- **O que faz:** Permite que um objeto pareça "mudar de classe" quando seu estado muda.
- **Vantagem:** Organiza o código e elimina dezenas de `if/else` complicados.
- **Dica de Ouro:** No Kotlin, sempre use `sealed class` ou `sealed interface` para implementar seus estados, pois o compilador te avisará se você esquecer de tratar algum deles.