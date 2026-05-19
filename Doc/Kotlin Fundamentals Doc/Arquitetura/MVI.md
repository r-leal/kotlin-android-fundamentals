## 1. Model (O Estado Único)

Diferente do MVVM, onde o Model muitas vezes se refere apenas à camada de dados, no MVI o **Model** representa o **Estado da UI (UI State)** em um determinado momento. Ele é uma representação imutável de tudo o que a tela deve exibir.

- **Single Source of Truth:** Existe apenas um objeto de estado para toda a tela.
    
- **Imutabilidade:** O estado não é alterado; um novo estado é criado e emitido sempre que algo muda.
    
- **Exemplo:** Uma `data class` que contém `isLoading: Boolean`, `items: List<Data>` e `errorMessage: String?`.
    

## 2. View (O Observador do Estado)

A View no MVI é responsável por renderizar o estado emitido e traduzir as interações do usuário em "Intenções".

- **Responsabilidade:** Observar o fluxo único de estados e reagir a ele.
    
- **Render:** Possui uma função principal (geralmente chamada de `render`) que recebe o novo objeto de estado e atualiza todos os componentes da interface de uma vez.
    
- **Emissor de Intents:** Cada clique ou ação do usuário não chama uma função direta, mas dispara uma intenção para o fluxo.
    

## 3. Intent (A Intenção de Mudança)

A **Intent** (não confundir com o `android.content.Intent`) representa o desejo do usuário de realizar uma ação ou uma mudança no sistema.

- **Comunicação Unidirecional:** É a única forma de enviar comandos da View para a lógica de negócio.
    
- **Representação:** Geralmente implementada via `sealed class` ou `sealed interface` (ex: `LoadUserIntent`, `RefreshIntent`, `FilterIntent`).
    

## Como a comunicação acontece (Unidirectional Data Flow - UDF)

O fluxo no MVI é rigorosamente unidirecional e cíclico:

1. **View para Intent:** O usuário interage (ex: puxa para atualizar). A View dispara uma `Intent.Refresh`.
    
2. **Intent para ViewModel:** O ViewModel recebe essa intenção e decide qual ação tomar.
    
3. **ViewModel para Model:** O ViewModel processa a lógica (via Repositories) e gera um **novo Estado (Model)**. Se estava em "Sucesso" e agora está carregando, ele emite um estado com `isLoading = true`.
    
4. **Model para View:** A View observa esse novo estado e o renderiza. O ciclo se fecha.
    

## Vantagens de usar MVI no Android

- **Previsibilidade Total:** Como o estado é imutável e o fluxo é unidirecional, é muito fácil rastrear por que a tela está exibindo determinada informação.
    
- **Depuração (Time-Travel Debugging):** É possível registrar todos os estados emitidos e "reproduzir" a experiência do usuário para encontrar bugs.
    
- **Consistência da UI:** Evita o problema de "estados conflitantes" (ex: mostrar um carregando e uma mensagem de erro ao mesmo tempo), já que há apenas um objeto de estado.
    

## Ferramentas Essenciais

- **Kotlin Flow / StateFlow:** Essenciais para gerenciar o fluxo contínuo de intenções e estados de forma reativa.
    
- **Coroutines:** Para processar as intenções em threads de background de forma assíncrona.
    
- **Jetpack Compose:** O MVI se integra perfeitamente ao Compose, que é naturalmente voltado para o consumo de estados únicos.
    

## Desvantagens

- **Complexidade Elevada:** Introduz conceitos como Redutores (Reducers) e processamento de efeitos colaterais, o que aumenta a curva de aprendizado.
    
- **Verbosidade (Boilerplate):** Mesmo para ações simples, é necessário definir Intenções, Estados e gerenciar a redução do estado.
    
- **Gerenciamento de Memória:** A criação constante de novos objetos de estado pode aumentar a pressão no Garbage Collector se não for bem implementada.
    

## Principais Problemas Enfrentados

- **Gerenciamento de Side-Effects:** Lidar com ações que não devem ser persistidas no estado (como mostrar um Toast ou navegar para outra tela) exige uma implementação separada (geralmente chamada de `Effect` ou `Event`).
    
- **Processamento de Estado:** Se o estado for muito grande, atualizar apenas uma pequena parte pode se tornar complexo dentro do redutor.
    
- **Explosão de Intents:** Em telas muito complexas, a `sealed class` de intenções pode crescer demais, exigindo uma melhor modularização da lógica.