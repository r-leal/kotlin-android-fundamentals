
Arquitetura de software é a estrutura fundamental de um sistema. Ela define não apenas como o código é organizado, mas como os diferentes componentes se comunicam, como os dados fluem e quais tecnologias servem de base para que o software seja escalável, seguro e fácil de manter.

Em resumo, a arquitetura de software permite **tomar decisões difíceis de mudar no futuro** de forma consciente, garantindo que o software sobreviva ao tempo e ao crescimento do negócio.

## Os Pilares da Arquitetura

Uma boa arquitetura é guiada pelos chamados **Atributos de Qualidade** (ou _Non-functional requirements_). Os principais incluem:

- **Escalabilidade:** A capacidade do sistema de lidar com um aumento de carga (mais usuários ou dados).
    
- **Resiliência:** Como o sistema se recupera de falhas.
    
- **Manutenibilidade:** A facilidade de modificar o sistema ou adicionar novas funcionalidades sem quebrar o que já existe.
    
- **Segurança:** A proteção dos dados e a integridade das operações.

## Padrões de arquitetura

### MVVM
O **[[MVVM]] (Model-View-ViewModel)** é o padrão de arquitetura recomendado pelo Google para o desenvolvimento Android. Ele foi projetado para separar as responsabilidades da interface do usuário (UI) da lógica de negócios, facilitando o teste, a manutenção e a escalabilidade do código. Ele é baseado no [[Paradigma Reativo]]

### MVI
O **[[MVI]] (Model-View-Intent)** é um padrão de arquitetura para interfaces de usuário que se baseia nos princípios da **programação reativa** e do **fluxo de dados unidirecional (Unidirectional Data Flow - UDF)**.

Originalmente popularizado no desenvolvimento Android e web (fortemente inspirado pelo ciclo do Redux e da arquitetura Elm), o MVI busca resolver problemas de estado complexo e inconsistências na UI.

O MVI é especialmente poderoso quando combinado com ferramentas modernas de UI declarativa, como o **Jetpack Compose**, onde a interface reage naturalmente a mudanças de estado. O [[Paradigma Declarativo]] é o mais aconselhável para o MVI

### Comparação Rápida: MVVM vs MVI

|**Característica**|**MVVM**|**MVI**|
|---|---|---|
|**Estado**|Múltiplas propriedades (LiveData/StateFlow)|Único objeto de estado imutável|
|**Fluxo**|Bidirecional ou Unidirecional|Estritamente Unidirecional|
|**Interação**|Chamadas diretas de funções no ViewModel|Envio de "Intents" (Eventos)|
|**Complexidade**|Baixa/Média|Média/Alta|
