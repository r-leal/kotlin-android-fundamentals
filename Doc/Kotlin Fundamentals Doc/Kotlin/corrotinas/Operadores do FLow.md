
Os métodos da API de Flow do Kotlin são divididos principalmente entre **operadores terminais**, que iniciam a coleta dos dados, e **operadores intermediários**, que transformam o fluxo de dados em um novo Flow.

Abaixo estão descritos os principais métodos disponíveis, conforme documentado nas fontes:

## 1. Operadores Terminais (Iniciam a Coleta)

Estes métodos são funções de suspensão que executam o fluxo e entregam os resultados.

- **collect**: O operador terminal mais comum. Ele inscreve-se no Flow e processa cada valor emitido conforme ele chega. Por exemplo, em um aplicativo Android, pode ser utilizado para atualizar um adaptador de lista sempre que novos dados de filmes chegam do banco de dados.
- **collectLatest**: Semelhante ao `collect`, mas se um novo valor for emitido antes que o processamento do valor atual termine, ele cancela o bloco de código atual e reinicia com o novo valor. Ideal para cenários onde apenas o dado mais recente importa. Por exemplo, exibir o "filme do dia" na interface; se um novo filme for emitido antes do anterior ser processado, a ação antiga é cancelada e a nova começa.
- **toList** **/** **toSet**: Coleta todos os elementos do Flow e os converte em uma lista ou conjunto, respectivamente. Muito comuns em **testes de unidade**. Um fluxo de filmes favoritos pode ser convertido em uma lista para verificar se os dados retornados coincidem com o esperado.
- **first** **/** **firstOrNull**: Retorna o primeiro elemento emitido pelo Flow (ou nulo se estiver vazio) e cancela a coleta em seguida.
- **last** **/** **lastOrNull**: Retorna o último elemento emitido antes da conclusão do Flow.
- **single** **/** **singleOrNull**: Retorna o único elemento emitido; lança uma exceção se o Flow emitir mais de um valor ou nenhum.
- **count**: Retorna a quantidade total de elementos emitidos.
- **reduce** **/** **fold**: Acumulam os valores emitidos aplicando uma operação sequencial, retornando o resultado final.

## 2. Tratamento de Erros e Ciclo de Vida

Esses métodos permitem gerenciar exceções e estados do fluxo.

- **catch**: Captura exceções que ocorrem no fluxo "upstream" (antes do operador ser chamado). Ele permite logar o erro, emitir valores de fallback ou tratar a falha sem que o programa trave.
- **retry** **/** **retryWhen**: Tenta coletar o Flow novamente caso ocorra um erro, baseando-se no número de tentativas ou no tipo da exceção encontrada.
- **onStart**: Executa um bloco de código imediatamente antes da emissão dos valores começar. É útil para mostrar barras de progresso ou emitir valores iniciais.
- **onCompletion**: Chamado quando o Flow termina, seja com sucesso ou por causa de uma exceção. É comumente usado para esconder elementos de UI ou realizar limpezas de recursos.

## 3. Operadores Intermediários (Transformação)

Estes retornam um novo Flow sem iniciar a coleta.

- **map** **/** **mapNotNull**: Transforma cada valor emitido em um novo formato ou objeto.
- **filter** **/** **filterNot** **/** **filterNotNull**: Seleciona apenas os valores que atendem a uma condição específica.
- **transform**: Um operador versátil que permite aplicar transformações complexas e emitir valores arbitrários (inclusive múltiplos valores para uma única entrada).
- **onEach**: Realiza uma ação para cada elemento antes de ser emitido para o próximo operador, sem alterar o valor original.
- **distinctUntilChanged**: Suprime emissões consecutivas de valores repetidos.
- **take** **/** **takeWhile**: Limita o fluxo aos primeiros x elementos ou enquanto uma condição for verdadeira.
- **drop** **/** **dropWhile**: Ignora os primeiros elementos do fluxo.

## 4. Combinação e Concorrência

- **combine**: Combina os valores mais recentes de dois ou mais Flows sempre que qualquer um deles emitir algo novo.
- **zip**: Combina valores de dois Flows em pares (o primeiro do Flow A com o primeiro do Flow B).
- **merge**: Une múltiplos Flows do mesmo tipo em um único Flow, emitindo valores conforme eles chegam de qualquer origem.
- **flowOn**: Altera o `CoroutineContext` (como o Dispatcher) usado para as operações que precedem essa chamada, permitindo que a emissão ocorra em uma thread de IO, por exemplo.
- **buffer**: Permite que o Flow emita valores em paralelo com a coleta, evitando que um coletor lento atrase o emissor.
- **conflate**: Se o coletor estiver lento, ele descarta valores intermediários e entrega apenas o valor mais recente emitido.

## 5. Operadores de Achatamento (Flattening)

Usados quando cada valor emitido por um Flow resulta em outro Flow interno.

- **flatMapConcat**: Processa cada Flow interno sequencialmente.
- **flatMapMerge**: Processa Flows internos de forma concorrente.
- **flatMapLatest**: Cancela a coleta do Flow interno anterior assim que um novo valor chega para iniciar um novo Flow interno.