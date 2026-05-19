As **data classes** no Kotlin são projetadas especificamente para classes cujo propósito principal é **armazenar dados**. Ao marcar uma classe com a palavra-chave `data`, o compilador gera automaticamente funções utilitárias que evitam o código repetitivo (_boilerplate_).

## O que o compilador gera automaticamente?

A partir das propriedades declaradas no **construtor primário**, o Kotlin deriva:

- **equals()** **/** **hashCode()**: Para comparação de igualdade baseada nos dados e não na referência de memória.
- **toString()**: Gera uma saída legível, como `"User(name=John, age=42)"`.
- **copy()**: Permite criar uma nova instância alterando apenas algumas propriedades.
- **componentN()**: Funções que permitem a **declaração de desestruturação** (ex: `val (name, age) = user`).

## Vantagens

1. **Redução de Boilerplate**: Você não precisa escrever manualmente métodos de comparação ou representação em string.
2. **Imutabilidade Facilitada**: O método `copy()` incentiva o uso de propriedades `val`, permitindo "modificar" objetos criando novas versões deles, o que é essencial em arquiteturas como MVI ou ao usar Jetpack Compose.
3. **Interoperabilidade**: No JVM, se todos os parâmetros tiverem valores padrão, o compilador gera um construtor sem argumentos, facilitando o uso com bibliotecas como Jackson ou JPA.
4. **Clareza**: Comparado a `Pair` ou `Triple`, data classes com nomes significativos tornam o código muito mais legível.

## Desvantagens e Limitações

1. **Herança Restrita**: Uma data class **não pode ser** **abstract****,** **open****,** **sealed** **ou** **inner**. No entanto, elas podem estender outras classes ou implementar interfaces.
2. **Cópia Rasa (****Shallow Copy****)**: O método `copy()` não é recursivo. Se a classe contiver uma lista mutável, tanto o original quanto a cópia compartilharão a mesma referência de lista. Alterar a lista em um afetará o outro.
3. **Exclusão de Propriedades**: Apenas propriedades no construtor primário entram nos métodos gerados. Se você declarar uma propriedade no corpo da classe, ela será ignorada pelo `equals`, `hashCode` e `toString`.

## Boas Práticas

- **Prefira Imutabilidade**: Use sempre `val` no construtor primário para garantir consistência, especialmente em ambientes multithreading ou estados de UI.
- **Cuidado com Tipos Mutáveis**: Evite usar `MutableList` ou objetos mutáveis como propriedades de uma data class devido ao comportamento do _shallow copy_.
- **Substitua Pair/Triple**: Em vez de usar `Pair<String, Int>`, crie uma data class nomeada. Isso melhora a semântica do código.
- **Validação**: Use blocos `init` dentro da data class para validar os dados recebidos no construtor.

## Métodos

### 1. `equals()` e `hashCode()`

Esses métodos trabalham juntos para permitir a **comparação baseada em valores** (igualdade estrutural) em vez de comparação por referência de memória.

- **Exemplo prático:** Se você tiver duas instâncias com os mesmos dados no construtor primário, elas serão consideradas iguais pelo operador `==`.
- **Ponto de atenção:** Se uma propriedade for declarada apenas no corpo da classe (fora do construtor), ela será **ignorada** pelo `equals()`.

```
data class User(val name: String, val age: Int)

val user1 = User("Alice", 25)
val user2 = User("Alice", 25)

println(user1 == user2) // true (equals retorna true)
println(user1.hashCode() == user2.hashCode()) // true
```

### 2. `toString()`

Gera uma representação em texto legível da instância, facilitando o **debug** e a leitura de logs. O formato padrão é `NomeDaClasse(propriedade1=valor1, propriedade2=valor2, ...)`.

- **Exemplo prático:**

```
val user = User("Bob", 30)
println(user) // Saída: User(name=Bob, age=30)
```

### 3. `copy()`

Permite criar uma nova instância do objeto, permitindo **alterar algumas propriedades** enquanto mantém o restante inalterado. Isso é fundamental para manter a **imutabilidade** dos dados.

- **Exemplo prático:**

```
val olderUser = user.copy(age = 31) // name continua "Bob", mas age muda para 31
```

- **Nota (Cópia Rasa):** O método `copy()` realiza uma **cópia rasa (shallow copy)**. Isso significa que, se a classe contiver uma lista mutável, a cópia e o original compartilharão a mesma referência da lista; alterar um afetará o outro.

### 4. `componentN()` (Destructuring)

O compilador gera funções chamadas `component1()`, `component2()`, etc., na ordem em que as propriedades são declaradas no construtor. Elas permitem o uso de **declarações de desestruturação**.

- **Exemplo prático:**

```
val (userName, userAge) = user // userName recebe "Bob", userAge recebe 30 [6]
// Por baixo dos panos, o Kotlin chama:
// val userName = user.component1()
// val userAge = user.component2()
```

![[DataClass.png]]