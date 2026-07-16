O padrão **Flyweight** (que em português pode ser traduzido como "peso-mosca") é um padrão de projeto estrutural focado em **economizar memória**. Imagine que você está organizando uma grande festa e precisa de 500 cadeiras. Em vez de comprar cada cadeira individualmente (o que custaria caro e ocuparia muito espaço no estoque), você as aluga de uma empresa que compartilha o mesmo estoque de cadeiras com várias outras festas.

Na programação, o Flyweight faz algo parecido: ele permite que você trabalhe com **uma grande quantidade de objetos** sem sobrecarregar a memória do computador, compartilhando as partes que são iguais entre eles.

## O Conceito: O que é fixo vs. O que muda

Para entender esse padrão, dividimos as informações de um objeto em duas partes:

1. **Estado Intrínseco (O que é fixo):** São as informações que não mudam e podem ser compartilhadas por todos os objetos do mesmo tipo (ex: a cor, o modelo ou a imagem de um personagem em um jogo).
2. **Estado Extrínseco (O que muda):** São as informações únicas de cada objeto, que dependem do contexto (ex: a posição X e Y de um personagem na tela ou o nome de um usuário).

## Exemplo Prático em Kotlin: Um exército de caracóis

Imagine que você está criando um jogo para Android com milhares de caracóis na tela. Cada caracol tem várias imagens pesadas para suas animações. Se cada caracol carregar suas próprias imagens, o celular vai travar por falta de memória.

**1. O Objeto Compartilhado (Flyweight)** Aqui, criamos um objeto que guarda apenas o que é pesado e repetido (as imagens). No Kotlin, usamos um `object` (Singleton) ou uma Factory para garantir que essas imagens existam em apenas um lugar.

```kotlin
// Este objeto guarda as imagens que serão compartilhadas por TODOS os caracóis
object SnailSprites {
    val imagens = listOf("caracol_parado.png", "caracol_movendo.png") 
}
```

**2. O Objeto Leve (O Caracol)** Cada caracol individual guarda apenas a sua posição e uma **referência** para as imagens compartilhadas.

```kotlin
class Caracol(val id: Int) {
    // Estado único (Extrínseco)
    var posX: Int = 0
    var posY: Int = 0

    // Estado compartilhado (Intrínseco)
    // Ele não carrega a imagem, apenas aponta para onde ela está guardada
    val imagensCompartilhadas = SnailSprites.imagens

    fun desenhar() {
        println("Caracol $id na posição ($posX, $posY) usando as imagens de SnailSprites")
    }
}
```

**3. O Gerenciador (Flyweight Factory)** Muitas vezes usamos uma "Fábrica" para garantir que, se pedirmos um tipo de objeto que já existe, ela nos devolva a versão compartilhada em vez de criar uma nova.

## Aplicações Reais no Android

Você encontra o padrão Flyweight em lugares fundamentais do Android:

- **Renderização de Textos:** Quando o Android desenha um parágrafo longo, ele não cria um objeto novo para cada letra "A". Ele tem um único "molde" (Flyweight) para a letra "A" e apenas o desenha em diferentes posições na tela.
- **Ícones em Listas (RecyclerView):** Se você tem uma lista com 1.000 contatos e todos que não têm foto exibem o mesmo ícone padrão, o Android não carrega esse ícone 1.000 vezes na memória. Ele carrega uma vez e compartilha essa imagem entre todos os itens da lista.
- **Pool de Conexões:** Bibliotecas de banco de dados usam algo similar, mantendo um grupo de conexões prontas que são "emprestadas" e compartilhadas, evitando o custo de criar uma nova toda vez.

## Vantagens e Cuidados

- **Vantagem:** Redução drástica no uso de RAM, permitindo que aplicativos rodem de forma fluida mesmo em celulares mais simples.
- **Cuidado:** É essencial que os dados compartilhados sejam **imutáveis** (usando `val` em Kotlin). Se você mudar a cor da "cadeira alugada" para a sua festa, ela aparecerá pintada na festa de outra pessoa também!.