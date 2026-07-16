O padrão **Decorator** (Decorador) é um padrão de projeto estrutural que permite adicionar novas funcionalidades a um objeto de forma dinâmica, sem precisar alterar o código original da classe ou criar uma hierarquia complexa de subclasses.

A melhor forma de entender o Decorator é pensar em uma **capinha de celular**:

1. O **Celular** é o objeto principal que tem as funções básicas (ligar, navegar).
2. A **Capinha** é o "Decorador". Ela "embrulha" o celular e adiciona novas características (proteção contra quedas, cor diferente, suporte para cartão), mas para quem olha de fora, o objeto continua sendo um celular e funciona da mesma forma.
3. Você pode colocar várias capinhas ou acessórios uns sobre os outros (recursividade) para ganhar múltiplos benefícios ao mesmo tempo.

## Por que usar o Decorator? (O problema da Herança)

Imagine que você tem uma classe para enviar notificações. Se você usar a herança comum para adicionar funções, terá um problema chamado "explosão de classes".

- Você cria a classe `NotificacaoComLog`.
- Depois cria a `NotificacaoComValidacao`.
- Se quiser as duas funções juntas, precisará criar uma terceira classe: `NotificacaoComLogEValidacao`.

Com o tempo, o número de classes se torna impossível de gerenciar. O Decorator resolve isso permitindo que você combine essas habilidades apenas quando precisar delas.

## Exemplo Prático em Kotlin

No Kotlin, esse padrão é muito elegante porque a linguagem possui o recurso de **delegação** através da palavra-chave **by**, que elimina a necessidade de escrever códigos repetitivos para repassar as ordens ao objeto original.

Imagine um sistema de repositório de dados:

```kotlin
// 1. O CONTRATO (Interface comum para o objeto e seus decoradores)
interface Repositorio {
    fun buscarDados(): String
}

// 2. O OBJETO REAL (A implementação básica)
class RepositorioSimples : Repositorio {
    override fun buscarDados() = "Dados do Banco de Dados"
}

// 3. O DECORADOR (Usa a palavra 'by' para delegar o trabalho básico)
class DecoradorComLog(private val decorado: Repositorio) : Repositorio by decorado {
    override fun buscarDados(): String {
        println("Iniciando busca de dados...") // Nova funcionalidade (Log)
        return decorado.buscarDados()
    }
}

fun main() {
    val simples = RepositorioSimples()
    val comLog = DecoradorComLog(simples)
    
    // Para o usuário, comLog ainda se comporta como um Repositorio
    println(comLog.buscarDados())
}
```

## Aplicações Reais e no Android

Você verá o padrão Decorator em vários lugares no desenvolvimento Android e Java:

- **Gerenciamento de Arquivos (****java.io****):** Este é o exemplo mais clássico. Quando você quer ler um arquivo de forma eficiente, você usa um `BufferedReader` que "decora" um `FileReader`. O `BufferedReader` adiciona a habilidade de memória temporária (buffer) para tornar a leitura mais rápida, sem mudar a forma como você lê o arquivo.
- **Interface de Usuário (UI):** No sistema de visualização clássico do Android, você pode envolver um componente de texto (`TextView`) com decoradores que adicionam bordas, sombras ou barras de rolagem (`ScrollDecorator`), permitindo que essas características sejam ligadas ou desligadas em tempo de execução.
- **Configuração de Clientes HTTP:** Bibliotecas como o Ktor permitem que você instale "plugins" em um cliente. Esses plugins funcionam como decoradores que adicionam capacidades de log ou conversão de dados (JSON) a cada requisição feita.

## Vantagens para o Programador

1. **Flexibilidade:** Você pode adicionar ou remover responsabilidades de um objeto enquanto o app está rodando.
2. **Código Limpo:** Evita criar classes gigantescas ("carregadas de recursos") que tentam fazer tudo de uma vez.
3. **Organização:** Segue o Princípio da Responsabilidade Única, onde cada decorador foca em fazer apenas uma coisa nova.