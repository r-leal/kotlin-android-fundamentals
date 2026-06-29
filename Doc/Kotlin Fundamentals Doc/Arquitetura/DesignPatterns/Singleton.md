
O padrão **Singleton** é um padrão criacional cujo objetivo principal é garantir que uma classe tenha **apenas uma única instância** em todo o sistema, fornecendo um ponto de acesso global a esse objeto. É ideal para gerenciar recursos compartilhados que não devem ter múltiplas cópias em memória, como um gerenciador de janelas ou um sistema de arquivos.

### Implementação em Kotlin

Diferente de linguagens como Java ou C++, onde o desenvolvedor precisa ocultar o construtor e gerenciar a instância manualmente, o Kotlin possui suporte nativo através da palavra-chave **object**.

- **Declaração de Objeto:** Ao usar `object`, o Kotlin declara a classe e cria sua única instância simultaneamente.
- **Inicialização:** Como objetos Singleton não podem ter construtores, qualquer lógica de inicialização deve ser colocada em um bloco **init**.
- **Lazy Initialization:** A instância é criada apenas quando é acessada pela primeira vez, o que economiza recursos do sistema.

### Exemplos com Aplicações para Android

No desenvolvimento Android, o Singleton é amplamente utilizado para centralizar configurações e acessos a dados:

1. **Configuração de Banco de Dados:** É comum criar um objeto singleton `Db` para armazenar parâmetros de conexão (host, porta, credenciais) lidos de variáveis de ambiente, garantindo que a configuração seja carregada uma única vez.
2. **Representação de Tabelas (Exposed):** Bibliotecas de banco de dados como o Exposed utilizam objetos singleton para definir a estrutura das tabelas (ex: `object CatsTable : IntIdTable()`), permitindo acesso direto às colunas em qualquer parte do código.
3. **Comparadores de Dados:** Para ordenar listas de objetos na UI (como uma lista de contatos), pode-se declarar um `object NameComparator` dentro da classe de dados, evitando a criação de novos objetos de comparação a cada ordenação.
4. **Logging:** Um objeto `Logger` centralizado pode ser usado para registrar eventos em todo o aplicativo, garantindo que as mensagens sigam o mesmo fluxo de saída.

### Vantagens e Desvantagens

#### **Vantagens:**

- **Controle de Acesso:** A classe encapsula sua instância, permitindo controle estrito sobre como e quando os clientes a acessam.
- **Espaço de Nomes Reduzido:** Melhora a organização do código ao evitar a poluição do namespace global com variáveis soltas.
- **Eficiência para Classes Sem Estado:** Minimiza a criação repetida de objetos em classes que apenas fornecem funções utilitárias agrupadas.
- **Inicialização Preguiçosa:** O recurso permanece "dormente" até ser necessário, poupando memória no início da execução do app.

#### **Desvantagens:**

- **Acoplamento Excessivo:** O uso indiscriminado pode criar dependências ocultas entre diferentes partes do código, dificultando a manutenção.
- **Dificuldade em Testes Unitários:** Como o Singleton tem uma instância global fixa, é difícil substituí-lo por objetos simulados (_mocks_) durante os testes, a menos que se use injeção de dependência.
- **Falta de Flexibilidade na Instanciação:** Diferente de classes comuns, você não tem controle sobre os parâmetros da instância, pois não pode passar argumentos para um construtor de um `object`.
- **Riscos de Estado Global:** Se o Singleton contiver dados mutáveis (`var`), ele pode introduzir problemas de segurança de thread e comportamentos imprevisíveis se múltiplas partes do app alterarem seu estado simultaneamente.

## Criação
Criar um Singleton em Kotlin pode ser feito de forma nativa pela linguagem ou através de frameworks de Injeção de Dependência (DI) como **Koin** e **Hilt**. A escolha depende da complexidade do projeto e da necessidade de testabilidade.

### 1. Criando Manualmente (O Jeito Kotlin)

O Kotlin possui suporte nativo para o padrão Singleton através da palavra-chave **object**. Esta é a forma mais simples e eficiente para objetos que não possuem dependências complexas.

- **Como funciona:** A declaração `object` define uma classe e cria uma única instância dela simultaneamente.
- **Inicialização Preguiçosa (Lazy):** A instância é criada apenas quando é acessada pela primeira vez, poupando recursos.
- **Sem Construtores:** Objetos singleton não podem ter construtores. Caso precise de lógica de inicialização, utilize o bloco **init**.

**Exemplo:**

```kotlin
object DatabaseManager {
    init {
        println("Inicializando banco de dados...")
    }
    
    fun saveData(data: String) {
        println("Dado salvo: $data")
    }
}
```

### 2. Criando com Koin

O **Koin** é um framework de DI que utiliza uma DSL (Domain-Specific Language) baseada em Kotlin para configurar dependências. No Koin, o escopo Singleton é o padrão para seus componentes (frequentemente chamados de "beans").

- **DSL do Koin:** Você define suas dependências dentro de um `module`.
- **Função** **single****:** É usada para declarar que uma instância deve ser tratada como um Singleton em todo o ciclo de vida da aplicação.

**Exemplo de configuração:**

```kotlin
val appModule = module {
    // Declara um Singleton
    single { MyService() } 
}
```

### 3. Criando com Hilt (Dagger Hilt)

O **Hilt** é uma biblioteca de DI construída sobre o Dagger que se integra perfeitamente ao ecossistema Android e ao Kotlin. Diferente do Singleton manual (`object`), o Hilt facilita a substituição de instâncias por objetos simulados (_mocks_) durante testes unitários.

- **Anotação** **@Singleton****:** Para definir um Singleton no Hilt, você anota a classe ou o método provedor dentro de um módulo.
- **Escopo de Componente:** O Singleton no Hilt é vinculado ao `SingletonComponent`, o que garante que a instância viva enquanto o aplicativo estiver rodando.

**Exemplo com anotação em classe:**

```kotlin
@Singleton
class AnalyticsService @Inject constructor() {
    // Lógica do serviço
}
```

**Exemplo em um Módulo (para classes externas ou interfaces):**

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://api.example.com").build()
    }
}
```

### Comparação e Vantagens

|Método|Quando usar?|Vantagem Principal|
|---|---|---|
|**Manual (****object****)**|Projetos pequenos ou utilitários sem dependências.|Simplicidade e suporte nativo da linguagem.|
|**Koin**|Aplicativos Kotlin que preferem uma abordagem pragmática e sem anotações.|Configuração via DSL Kotlin legível e fácil de aprender.|
|**Hilt**|Aplicativos Android robustos que exigem alta testabilidade e arquitetura padrão.|Facilita o desacoplamento e a criação de mocks para testes.|

**Importante:** Embora os singletons manuais (`object`) sejam práticos, em sistemas de software de larga escala, eles podem dificultar os testes, pois você não tem controle sobre a instanciação e não pode passar parâmetros via construtor. Nesses casos, o uso de DI (Koin ou Hilt) é altamente recomendado.