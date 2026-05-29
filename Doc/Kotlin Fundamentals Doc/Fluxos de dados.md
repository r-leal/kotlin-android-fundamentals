
## 1. LiveData

O **LiveData** é uma classe de detentor de dados observável e **ciente do ciclo de vida** (lifecycle-aware). Ele garante que o observador (geralmente uma Activity ou Fragment) só receba atualizações quando estiver em um estado ativo (como `STARTED` ou `RESUMED`).

- **Caso de Uso:** Expor dados do ViewModel para a UI de forma simples, garantindo que não haja vazamentos de memória ou falhas por atualizações em telas inativas.
- **Limitação:** É estritamente vinculado ao ecossistema Android e funciona apenas na thread principal por padrão.

## 2. Kotlin Flow (Cold Flow)

O **Flow** é uma biblioteca de fluxo assíncrono construída sobre corrotinas. Ele é um **fluxo frio (cold stream)**, o que significa que o código dentro do construtor não começa a ser executado até que o fluxo seja coletado por um terminal (como o `collect`).

- **Diferença:** Cada novo assinante inicia uma execução independente do zero.
- **Caso de Uso:** Streams de dados sob demanda, como leituras de sensores ou requisições de rede pontuais.

```kotlin
// Exemplo de criação de um Flow simples
fun getNumbers(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(1000)
        emit(i) // Envia o valor para o coletor
    }
}
```

## 3. StateFlow

O **StateFlow** é um **fluxo quente (hot stream)** especializado que representa um estado e sempre mantém o **último valor emitido**. Ele requer um valor inicial e é a recomendação moderna para substituir o LiveData no Android.

- **Diferença:** Ao contrário do Flow comum, ele emite valores mesmo sem ouvintes ativos. Novos assinantes recebem imediatamente o estado atual.
- **Caso de Uso:** Gerenciar o estado da interface do usuário (ex: lista de itens, status de carregamento).

```kotlin
class MovieViewModel : ViewModel() {
    // Fluxo mutável interno
    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    // Fluxo imutável exposto para a UI
    val movies: StateFlow<List<Movie>> = _movies

    fun loadMovies() {
        viewModelScope.launch {
            _movies.value = repository.fetchMovies()
        }
    }
}
```

## 4. SharedFlow

O **SharedFlow** também é um **fluxo quente**, mas projetado para emitir eventos para múltiplos ouvintes simultaneamente. Ele não exige um valor inicial e pode ser configurado com um "replay" para enviar valores passados a novos assinantes.

- **Diferença:** Enquanto o StateFlow é para "estado" (o que a UI _é_), o SharedFlow é para "eventos" (o que a UI _deve fazer_).
- **Caso de Uso:** Eventos que não devem ser repetidos em mudanças de configuração, como exibir um SnackBar, navegar para outra tela ou disparar um alerta.

```kotlin
private val _eventMessage = MutableSharedFlow<String>()
val eventMessage = _eventMessage.asSharedFlow()

suspend fun triggerError() {
    _eventMessage.emit("Erro de conexão!") // Todos os ouvintes ativos recebem
}
```

Comparativo e Coleta Segura na UI

| Característica              | LiveData       | Flow (Cold)  | StateFlow         | SharedFlow   |
| --------------------------- | -------------- | ------------ | ----------------- | ------------ |
| **Tipo de Stream**          | Quente         | Frio         | Quente            | Quente       |
| **Estado Inicial**          | Não (opcional) | Não          | Sim (obrigatório) | Não          |
| **Ciente de Ciclo de Vida** | Sim            | Não (manual) | Não (manual)      | Não (manual) |
| **Multi-plataforma**        | Não            | Sim          | Sim               | Sim          |

**Dica de Coleta no Android:** Para garantir que StateFlow ou SharedFlow não desperdicem recursos quando a tela está em segundo plano, utilize `repeatOnLifecycle` ou `flowWithLifecycle` dentro da Activity ou Fragment. Isso faz com que a coleta pare automaticamente no `onStop` e reinicie no `onStart`.

## 5. Kotlin Channels (Canais)

Enquanto o **Flow** é geralmente utilizado para representar fluxos de dados (streams), os **Channels** são projetados para a **comunicação entre corrotinas**. Eles funcionam de forma semelhante a uma `BlockingQueue` do Java, mas com uma diferença crucial: em vez de bloquear a thread quando o canal está cheio ou vazio, eles **suspendem** a corrotina.

- **Fluxo Quente (Hot Stream):** Ao contrário do Flow comum (frio), o Channel é um fluxo quente. Isso significa que ele está em operação contínua e pode produzir dados independentemente de haver alguém ouvindo no momento.
- **Unicidade na Entrega:** Um valor enviado para um Channel é entregue a **exatamente um receptor**. Se houver múltiplas corrotinas "escutando" o mesmo canal, elas distribuirão o trabalho entre si (modelo _Fan-out_), mas cada mensagem individual só será processada por um único trabalhador.

**Exemplo de uso básico:**

```kotlin
val channel = Channel<Int>()
launch {
    // Enviando dados
    for (x in 1..5) channel.send(x)
    channel.close() // Importante fechar para encerrar os receptores
}

launch {
    // Recebendo dados
    for (y in channel) {
        println("Recebido: $y")
    }
}
```

--------------------------------------------------------------------------------

### Quando usar Channel vs. SharedFlow?

A escolha entre `Channel` e `SharedFlow` para gerenciar eventos depende de **quem** deve receber o evento e de **como** ele deve ser processado.

#### 1. Channel (Comunicação Ponto a Ponto)

Use o **Channel** quando você deseja garantir que cada evento seja tratado por **apenas um consumidor**.

- **Garantia de Entrega:** Se um evento é enviado e não há ninguém coletando no exato momento, ele fica na fila (buffer) até que um consumidor apareça. Isso o torna ideal para tarefas de processamento onde você não quer perder o evento.
- **Caso de Uso:** Distribuição de carga de trabalho entre múltiplos trabalhadores ou comandos que devem disparar uma única ação específica (ex: processar um pagamento, salvar um arquivo em segundo plano).

#### 2. SharedFlow (Broadcasting / Transmissão)

Use o **SharedFlow** quando você deseja que um evento seja transmitido para **múltiplos ouvintes** simultaneamente.

- **Comportamento de Transmissão:** Um evento emitido no `SharedFlow` será recebido por todos os coletores que estiverem ativos no momento da emissão. Se ninguém estiver ouvindo, o evento é descartado (a menos que seja configurado um _replay_).
- **Caso de Uso:** Eventos de interface de usuário (UI) que várias partes do aplicativo podem precisar observar, como disparar um SnackBar, navegação global ou notificações de erro que afetam múltiplos componentes da tela ao mesmo tempo.

#### Resumo Comparativo

|Característica|Channel|SharedFlow|
|---|---|---|
|**Público**|Exatamente um receptor (Unicast)|Múltiplos receptores (Broadcast)|
|**Persistência**|Mantém o valor até ser consumido|Descarta o valor se não houver coletores ativos (sem replay)|
|**Backpressure**|Suspende o remetente se o buffer estiver cheio|Gerencia de forma flexível (ex: descartar o mais antigo)|
|**Propósito**|Distribuição de tarefas / Pipeline|Notificação de eventos / Observação|