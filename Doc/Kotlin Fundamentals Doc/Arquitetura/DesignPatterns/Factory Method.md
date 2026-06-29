
O padrão **Factory Method** (ou simplesmente padrão Factory) é um dos padrões de projeto "criacionais", o que significa que ele lida com a melhor forma de criar objetos em seu código.

A forma mais fácil de entender é pensar em uma **fábrica da vida real**: se você quer um carro, você não precisa saber como montar o motor ou soldar as portas; você apenas pede um "carro" à fábrica, e ela te entrega o produto pronto. Na programação, o padrão Factory faz a mesma coisa: ele esconde a lógica complexa de criação e decide qual objeto exato te entregar.

Por que usar o Factory em vez de um construtor comum?

Normalmente, criamos objetos chamando o construtor (ex: `val carro = Carro()`). No entanto, construtores têm limitações: eles sempre retornam o mesmo tipo de classe e não podem ter nomes personalizados que expliquem **como** o objeto está sendo criado. O Factory resolve isso ao delegar a criação para um método especial.

---

## Exemplo Prático em Kotlin: Um Jogo de Xadrez

Imagine que você está criando um jogo de xadrez e precisa criar peças (Peão, Rainha, Rei) a partir de um arquivo de texto. Você não sabe de antemão qual peça será lida.

1. **A Interface (O contrato comum):** Todas as peças compartilham comportamentos básicos.
2. **As Classes Concretas:** Diferentes tipos de peças.
3. **A "Fábrica" (Factory Method):** Uma função que decide qual peça criar com base em uma entrada.

Dessa forma, o seu programa principal apenas chama `criarPeça('p', "A2")` e recebe um Peão, sem precisar conhecer os detalhes internos da classe `Peão`.

---

O "Jeito Kotlin": Static Factory com Companion Object

No Kotlin, é muito comum usar o **Companion Object** para criar o que chamamos de "Static Factory Method". Isso permite que você crie objetos com nomes que fazem sentido, em vez de apenas usar o nome da classe.

**Exemplo de um Servidor:**

```kotlin
class Servidor private constructor(val porta: Int) {
    companion object {
        // Este é o método Factory
        fun naPortaPadrao() = Servidor(8080)
        fun naPorta(porta: Int) = Servidor(porta)
    }
}

// Uso:
val servidor = Servidor.naPortaPadrao() // Muito mais legível que Servidor(8080)
```

Neste exemplo, o construtor é `private`, então a única maneira de criar um servidor é através da fábrica, garantindo controle total sobre a criação.

---

## Aplicações Práticas

- **Bibliotecas de Configuração:** Quando um sistema lê um arquivo (JSON ou XML) e precisa converter esses dados em objetos de código, ele usa uma Factory para decidir qual objeto criar com base no conteúdo do arquivo.
- **Interface de Usuário (UI):** Se você quer que seu aplicativo tenha uma aparência diferente no Windows e no Mac, você pode ter uma `FabricaDeInterface`. No Windows, ela te entrega botões no estilo Windows; no Mac, botões no estilo Mac, sem que o restante do código precise mudar.
- **Conexões de Banco de Dados:** Criar instâncias de conexão que variam dependendo se você está em um ambiente de "teste" ou "produção".

## Vantagens para o desenvolvedor:

- **Nomes Significativos:** Você pode dar nomes como `carregarDoBancoDeDados()` em vez de apenas usar o construtor.
- **Desacoplamento:** O código que usa o objeto não precisa saber a classe exata dele, apenas a interface que ele segue.
- **Flexibilidade:** Se você decidir mudar a classe que está sendo criada no futuro, você só precisa alterar um lugar (o método Factory), e não o aplicativo inteiro.

## Exemplos Reais em Bibliotecas

Muitas ferramentas modernas que você usa no Android e backend são, na verdade, grandes implementações do padrão Factory:

- **Ktor:** Os mecanismos de servidor do Ktor, como **CIO** e **Netty**, utilizam o padrão Factory para instanciar o servidor de forma intercambiável. A função `embeddedServer` atua como uma fábrica abstrata que decide qual motor de execução (engine) criar sem que você precise mexer na lógica da aplicação.
- **Retrofit:** Quando você chama `retrofit.create(MyApiService::class.java)`, você está usando uma fábrica que gera a implementação concreta da sua interface através de proxies dinâmicos em tempo de execução.
- **Room:** Ao definir um **DAO** como uma interface, o Room atua como uma fábrica que fornece a implementação concreta necessária para realizar as consultas SQL, garantindo que o seu código principal dependa apenas do contrato (interface).
- **Kotlin Collections:** Funções como `listOf()` ou `emptyList()` são métodos de fábrica que retornam instâncias de listas (muitas vezes singletons internos) de forma otimizada para o desenvolvedor.

## Por que são consideradas Factory?

Essas bibliotecas são fábricas porque elas **isolam as classes concretas**. Você define a interface (o contrato), e a fábrica (Retrofit, Ktor, Room) fornece o objeto pronto, gerenciando a complexidade de criação e permitindo que você troque implementações (como trocar de banco de dados ou de motor HTTP) sem quebrar o código cliente.

---

## Fábricas e Injeção de Dependência (DI)

Em sistemas de grande escala, usar fábricas manuais ou singletons globais (`object`) pode dificultar testes unitários, pois você perde o controle sobre a instanciação. É aqui que frameworks de DI entram para gerenciar as fábricas.

- **Koin:** Utiliza a função `factory { ... }` para declarar que uma nova instância de um objeto deve ser gerada **toda vez** que for solicitada, funcionando como um provedor de fábrica centralizado.
- **Hilt/Dagger:** Utiliza métodos anotados com `@Provides` (sem anotação de escopo como `@Singleton`). Cada vez que uma classe pede aquela dependência, o Hilt chama o método "fábrica" para criar uma nova instância.

### Vantagem da DI com Factory:
Ao injetar uma fábrica em vez de um objeto fixo, você ganha a capacidade de substituir a implementação real por um objeto simulado (**mock**) durante os testes, algo que fábricas estáticas simples tornam difícil de fazer.