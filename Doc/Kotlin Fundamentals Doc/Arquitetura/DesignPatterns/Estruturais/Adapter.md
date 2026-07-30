O padrão **Adapter** (Adaptador) funciona exatamente como um adaptador de tomadas da vida real. Imagine que você tem um carregador de celular com um plugue de três pinos (padrão brasileiro), mas a tomada na parede é do padrão americano (dois pinos chatos). Você não vai quebrar a parede para trocar a fiação, nem jogar seu carregador fora; você simplesmente usa um **adaptador** que serve de ponte entre os dois.

Na programação, o Adapter é um padrão **estrutural** usado quando você tem duas partes de um código que precisam conversar, mas as "interfaces" (os jeitos de chamar as funções) são incompatíveis. Ele "embrulha" um objeto antigo ou diferente para que ele pareça algo que seu sistema novo entenda.

## Exemplo Prático em Kotlin

Imagine que seu sistema Android espera receber energia de uma tomada americana (`USPlug`), onde a energia é representada pelo número `1`. No entanto, você só tem uma tomada europeia (`EUPlug`), que representa a energia com o texto `"YES"`.

### **1. O cenário de incompatibilidade:**

```kotlin
interface USPlug { val temEnergia: Int } // Espera 1 ou 0
interface EUPlug { val temEnergia: String } // Entrega "YES" ou "NO"

fun ligarAparelhoAmericano(tomada: USPlug) {
    if (tomada.temEnergia == 1) println("Ligado!")
}
```

Se tentarmos passar uma `EUPlug` para a função `ligarAparelhoAmericano`, o código não vai compilar porque o Android não sabe transformar o texto `"YES"` no número `1` automaticamente.

### **2. A solução com o Adapter (usando Extensões do Kotlin):** 

Uma forma elegante de criar adaptadores em Kotlin é através de **extension functions**. Criamos uma função que "transforma" o plugue americano em europeu:

```kotlin
// Este é o nosso ADAPTADOR
fun USPlug.paraTomadaEuropeia(): EUPlug {
    val status = if (this.temEnergia == 1) "YES" else "NO"
    return object : EUPlug {
        override val temEnergia = status
    }
}
```

### Aplicações Reais no Android

No desenvolvimento Android, você encontrará o padrão Adapter em vários lugares fundamentais:

- **RecyclerView Adapter:** É o exemplo mais famoso. O Android tem uma lista (`RecyclerView`), mas ele não sabe como desenhar seus dados (como uma lista de nomes). Você cria um `Adapter` que converte seus dados em "pedacinhos de tela" (ViewHolders) que o sistema consegue mostrar.
- **Android KTX:** A biblioteca oficial do Google usa adaptadores para tornar APIs antigas do Android mais fáceis de usar com Kotlin.
- **Corrotinas:** Se você tiver um serviço antigo que usa threads comuns do Java (Executors), você pode usar a função `asCoroutineDispatcher()` para **adaptar** esse serviço e usá-lo dentro do sistema moderno de Corrotinas do Kotlin.
- **Conversão de Dados:** Métodos que começam com "to", como `list.toTypedArray()`, são adaptadores simples que convertem uma lista em um array para que ela possa ser usada em funções que só aceitam arrays.

### Por que isso é importante?

O grande benefício é o **reuso de código**. Você consegue fazer sistemas criados em épocas diferentes trabalharem juntos sem precisar reescrever o código antigo, apenas criando essa "ponte" de adaptação entre eles

## **Back-end em Python**

No contexto de **back-end em Python**, o padrão Adapter é essencial para integrar sistemas legados ou bibliotecas de terceiros sem comprometer a integridade do seu código limpo. Abaixo, apresento como esse cenário se traduz para Python e suas aplicações práticas em servidores e microserviços.

---

### **Exemplo Prático em Python: Sistemas de Pagamento**

Imagine que seu back-end foi construído para aceitar pagamentos através de uma classe legada que usa o método `fazer_pagamento()`. No entanto, você precisa integrar um novo gateway de pagamento moderno que usa o método `executar_pagamento()`.

### **1. O cenário de incompatibilidade:**

Em Python, as interfaces podem ser definidas de forma explícita usando **Protocols** ou **Classes Abstratas (ABCs)** para garantir que os objetos sigam um contrato.

```python
from typing import Protocol

# O que o seu sistema atual espera (Interface Alvo)
class SistemaPagamento(Protocol):
    def fazer_pagamento(self, valor: float) -> None:
        ...

# O sistema antigo/legado que você já possui
class SistemaLegado:
    def fazer_pagamento(self, valor: float):
        print(f"Pagamento de R${valor} processado pelo sistema antigo.")

# O novo sistema que você quer usar, mas tem métodos diferentes
class GatewayModerno:
    def executar_pagamento(self, valor: float):
        print(f"Pagamento de R${valor} processado via API Moderna.")
```

### **2. A solução com o Adapter (usando Composição):**

Em vez de alterar o código do `GatewayModerno` (ao qual você pode não ter acesso), você cria uma classe "ponte".

```python
# Este é o nosso ADAPTADOR
class AdaptadorPagamento:
    def __init__(self, gateway_novo: GatewayModerno):
        self.gateway_novo = gateway_novo

    def fazer_pagamento(self, valor: float):
        # Traduz a chamada "fazer_pagamento" para "executar_pagamento"
        self.gateway_novo.executar_pagamento(valor)

# Uso no Back-end
def processar_pedido(sistema: SistemaPagamento, total: float):
    sistema.fazer_pagamento(total)

gateway_novo = GatewayModerno()
adaptador = AdaptadorPagamento(gateway_novo)

# O sistema agora aceita o gateway novo através do adaptador
processar_pedido(adaptador, 150.00)
```

---

## **Aplicações Reais no Back-end**

No desenvolvimento de servidores e sistemas distribuídos, o Adapter resolve problemas críticos de infraestrutura:

- **Integração de Mensageria (Kafka vs. Pulsar):** Você pode definir uma interface genérica de `MessageConsumer` e criar adaptadores específicos para **Apache Kafka** ou **Apache Pulsar**. Isso permite trocar o provedor de mensageria apenas alterando o adaptador, sem tocar na lógica de negócio.
- **Camada de Anticorrupção (DDD):** No Domain-Driven Design, um adaptador é usado para traduzir a "linguagem ubíqua" de um microserviço externo para a linguagem do seu próprio domínio, evitando que conceitos de outros sistemas "poluam" seu núcleo.
- **Bibliotecas de Log de Terceiros:** Se você usa uma biblioteca como a `abc-logging` e decide mudar para a `xyz-logging`, você cria um adaptador para a nova biblioteca que mantém os mesmos nomes de métodos que seu sistema já usa, evitando alterações em centenas de arquivos.
- **Múltiplos Provedores de Banco de Dados:** Repositórios podem atuar como adaptadores que traduzem comandos de domínio para consultas específicas em **SQL** ou **NoSQL** (como MongoDB), permitindo que a lógica de use cases seja independente do banco utilizado.
- **Compatibilidade de Versões de APIs:** Um microserviço de nível superior pode usar adaptadores para se comunicar com diferentes versões de microserviços de nível inferior, mantendo a API externa estável para os clientes.
- **Padronização de Erros:** Você pode criar uma classe adaptadora para "envolver" exceções de bibliotecas externas e relançá-las como exceções que seu sistema entende, garantindo um tratamento de erros uniforme.

---

## **Por que isso é importante?**

No back-end, a principal vantagem é a adesão ao **Princípio do Aberto/Fechado (Open-Closed Principle)**: seu sistema está **aberto para expansão** (você pode adicionar novos provedores ou tecnologias) mas **fechado para modificação** (você não precisa alterar o código central que já funciona). Isso reduz drasticamente o risco de bugs ao introduzir novas dependências tecnológicas em sistemas complexos.


## Aplicação Prática: TypeScript + Angular

Vamos ver como isso funciona na prática de um desenvolvedor front-end. No Angular, usamos muito **Services** para buscar dados.

### O Cenário:

Seu sistema usa uma interface de envio de logs chamada `ActionSender`.

```typescript
// O que seu sistema espera (A "Tomada" Brasileira)
export interface ActionSender {
  sendAction(action: string): Promise<void>;
}
```

Agora, a empresa contratou uma ferramenta externa de Analytics super moderna, mas ela usa uma interface diferente chamada `EventSender`.

```
// A nova biblioteca (O "Plugue" Europeu)
export interface EventSender {
  sendEvent(eventName: string): void; // O método é diferente!
}
```

### A Solução: O Adapter

Em vez de sair mudando todos os seus componentes que usam `sendAction`, você cria um **Adapter** em TypeScript:

```typescript
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class EventAdapter implements ActionSender { // Ele se disfarça de ActionSender
  
  constructor(private analyticsService: EventSender) {}

  // Quando chamarem 'sendAction', ele traduz para 'sendEvent'
  public async sendAction(action: string): Promise<void> {
    console.log("Adaptando o chamado...");
    this.analyticsService.sendEvent(action); // Aqui acontece a mágica!
  }
}
```

Agora, no seu componente Angular, você injeta o `EventAdapter` no lugar da classe antiga, e tudo continua funcionando sem que o componente saiba da mudança.

### Por que usar isso? (Vantagens para o seu time)

1. **Princípio da Responsabilidade Única:** Você separa o código de conversão de dados da lógica principal do seu componente.
2. **Manutenibilidade:** Se a biblioteca de Analytics mudar de novo amanhã, você só mexe em **um lugar**: no seu Adapter.
3. **Flexibilidade:** Permite usar códigos legados (antigos) junto com tecnologias novas sem que eles briguem entre si.

### Dica de Ouro

O Adapter aumenta um pouco a complexidade porque você cria classes extras. Por isso, o segredo é o equilíbrio: só use quando as interfaces forem **realmente incompatíveis** e você não puder mudar a fonte original.
