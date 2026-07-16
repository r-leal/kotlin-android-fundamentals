
O padrão **Abstract Factory** (Fábrica Abstrata) é uma evolução do conceito de Factory Method. Se o Factory Method é uma "fábrica" que cria um tipo de objeto, o Abstract Factory é frequentemente descrito como uma **"fábrica de fábricas"**.

Enquanto o Factory Method foca em criar **um único produto** (ex: uma peça de xadrez), o Abstract Factory foca em criar **famílias de produtos relacionados** que devem ser usados juntos (ex: um conjunto completo de interface para Android vs. um conjunto para iOS).

## Como funciona a Abstract Factory?

A ideia principal é fornecer uma interface para criar objetos sem especificar suas classes concretas. Isso garante que o sistema seja independente de como seus produtos são criados e representados.

Os Participantes:

1. **AbstractFactory**: Uma interface que declara métodos para criar cada tipo de produto abstrato.
2. **ConcreteFactory**: Uma classe que implementa esses métodos para criar produtos específicos de uma "família".
3. **AbstractProduct**: Define a interface para um tipo de objeto (ex: "Botão").
4. **ConcreteProduct**: A implementação real do objeto para uma família específica (ex: "Botão Estilo Windows").
5. **Client**: O código que usa apenas as interfaces da fábrica e dos produtos.

---

## Exemplo Prático em Kotlin: Configurações de Servidor

Imagine que seu aplicativo precisa ler configurações de diferentes tipos de arquivos (JSON ou YAML). Cada tipo de arquivo exige um "conjunto" de ferramentas: um parser para propriedades e um parser para a configuração geral do servidor.

1. **Produtos Abstratos (Interfaces)**:

```kotlin
interface Property { val name: String; val value: Any }
interface ServerConfiguration { val properties: List<Property> }
```

1. **A Fábrica Abstrata**:

```kotlin
interface Parser {
    fun createProperty(prop: String): Property
    fun createServerConfig(props: List<String>): ServerConfiguration
}
```

1. **Fábricas Concretas (Famílias JSON e YAML)**:

```kotlin
class JsonParser : Parser {
    override fun createProperty(prop: String) = // Lógica para JSON
    override fun createServerConfig(props: List<String>) = // Lógica para JSON
}

class YamlParser : Parser {
    override fun createProperty(prop: String) = // Lógica para YAML
    override fun createServerConfig(props: List<String>) = // Lógica para YAML
}
```

---

## Factory Method vs. Abstract Factory

|Característica|Factory Method|Abstract Factory|
|---|---|---|
|**Foco**|Cria **um** produto.|Cria uma **família** de produtos relacionados.|
|**Mecanismo**|Baseia-se em **herança** (subclasses decidem o que criar).|Baseia-se em **composição de objetos** (uma fábrica é passada como parâmetro).|
|**Complexidade**|Mais simples, exige apenas um método novo.|Mais complexo, envolve múltiplas interfaces e classes.|
|**Retorno**|O produto é retornado imediatamente.|A fábrica pode construir o produto complexo passo a passo (embora isso seja mais comum no padrão _Builder_).|

---

## Aplicações Reais e Vantagens

- **Isolamento de Classes Concretas**: O cliente não conhece os nomes das classes reais (como `WindowsButton` ou `MotifButton`), apenas as interfaces.
- **Troca de Famílias Facilitada**: Para mudar a aparência de todo o seu app, basta trocar a instância da fábrica concreta (ex: de `MaterialFactory` para `CupertinoFactory`) e todo o restante do código continuará funcionando.
- **Consistência**: Garante que você não use acidentalmente um botão de Mac dentro de uma janela de Windows, pois a fábrica força o uso de produtos da mesma família.

**Onde vemos isso?** No Java, a classe `java.util.Collections` usa esse conceito com métodos como `emptyList()` e `emptyMap()`, que retornam diferentes tipos de coleções (produtos) sob uma interface comum. No Android, o **Dagger Hilt** e o **Koin** facilitam o uso desse padrão ao injetar a fábrica correta dependendo do contexto (ex: injetar uma fábrica de "Mock/Teste" ou uma fábrica de "Produção").