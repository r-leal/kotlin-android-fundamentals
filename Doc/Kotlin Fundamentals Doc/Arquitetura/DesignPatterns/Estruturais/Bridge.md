![[Padrão_de_Projeto_Bridge.png]]

O padrão **Bridge** (Ponte) é um padrão de projeto estrutural que serve para separar o **"o quê"** um objeto faz do **"como"** ele realmente faz isso.

A melhor forma de entender é pensar em um **controle remoto** e uma **televisão**:

1. O **Controle Remoto** é a "Abstração": ele tem botões genéricos como "Ligar" ou "Mudar Canal". Você sabe _o que_ quer fazer.
2. A **Televisão** é a "Implementação": TVs de marcas diferentes (Samsung, LG, Sony) podem funcionar internamente de formas totalmente distintas, mas todas respondem aos mesmos comandos do controle.

A "Ponte" é o que liga o seu controle a qualquer uma dessas televisões, permitindo que você troque de TV sem precisar aprender a usar um controle novo.

---

## Por que usar o Bridge? (O problema da herança)

Imagine que você está criando um jogo com vários tipos de soldados (**Troopers**). Se você usar herança comum, acabará com uma explosão de classes:

- `StormTrooperComRifle`
- `StormTrooperComLançaChamas`
- `ShockTrooperComRifle`
- `ShockTrooperComLançaChamas`

Se você tiver 10 tipos de soldados e 10 tipos de armas, precisará criar **100 classes**! O padrão Bridge resolve isso criando uma "ponte" entre o Soldado e a Arma, reduzindo a complexidade para apenas **20 classes** (10 soldados + 10 armas).

---

## Exemplo Prático em Kotlin

Neste exemplo, vamos separar a **Notificação** (o tipo de mensagem) do **Provedor** (como a mensagem é enviada).

```kotlin
// 1. A IMPLEMENTAÇÃO (Como enviar)
interface ProvedorDeEnvio {
    fun enviarMensagem(texto: String)
}

class ServicoEmail : ProvedorDeEnvio {
    override fun enviarMensagem(texto: String) = println("Enviando e-mail: $texto")
}

class ServicoSMS : ProvedorDeEnvio {
    override fun enviarMensagem(texto: String) = println("Enviando SMS: $texto")
}

// 2. A ABSTRAÇÃO (O que enviar - Esta é a PONTE)
abstract class Notificacao(protected val provedor: ProvedorDeEnvio) {
    abstract fun disparar(mensagem: String)
}

// 3. Versões refinadas da abstração
class NotificacaoUrgente(provedor: ProvedorDeEnvio) : Notificacao(provedor) {
    override fun disparar(mensagem: String) {
        provedor.enviarMensagem("ALERTA CRÍTICO: $mensagem")
    }
}

fun main() {
    // Criamos a ponte entre o tipo de mensagem e o serviço
    val avisoUrgente = NotificacaoUrgente(ServicoSMS())
    avisoUrgente.disparar("O servidor caiu!") // Saída: Enviando SMS: ALERTA CRÍTICO: O servidor caiu!
}
```

---

## Aplicações Reais e no Android

O padrão Bridge é fundamental em sistemas que precisam funcionar em múltiplos ambientes:

- **Ktor (Server Engines):** No framework Ktor, a função `embeddedServer()` usa o Bridge para permitir que você escreva sua lógica de rotas uma única vez e escolha entre diferentes "motores" de servidor (como **Netty** ou **CIO**) de forma intercambiável, sem mudar o seu código principal.
- **Drivers de Hardware no Android:** Quando o Android define como uma câmera deve funcionar (abstração), ele usa uma ponte para as implementações específicas criadas por fabricantes como Samsung ou Motorola. O sistema operacional não precisa saber os detalhes técnicos de cada lente, apenas como chamar a função `takePicture()`.
- **Injeção de Dependência:** Em projetos Android modernos, o uso de bibliotecas como **Hilt** ou **Koin** é, na prática, uma implementação do Bridge. Você injeta uma interface (abstração) no seu código, e o framework fornece a implementação real (real ou mock/teste) através de uma ponte.

## Resumo das Vantagens

1. **Flexibilidade:** Você pode trocar a implementação (ex: mudar de SMS para WhatsApp) em tempo de execução.
2. **Código Limpo:** Evita a criação excessiva de subclasses.
3. **Independência:** O desenvolvedor da interface (UI) e o desenvolvedor do sistema de mensagens podem trabalhar sem que um trave o outro.


## **Back-end em Python**

O padrão Bridge é fundamental para construir sistemas onde a lógica de negócio (abstração) e a infraestrutura tecnológica (implementação) precisam evoluir sem que uma quebre a outra. Ao contrário do _Adapter_, que é frequentemente usado para consertar sistemas já existentes, o Bridge é planejado **antecipadamente** para manter essas camadas desacopladas.

Abaixo, veja como traduzir o exemplo de notificações para Python e as aplicações reais no lado do servidor.

---

### **Exemplo Prático em Python: Sistema de Notificações**

Em Python, implementamos o Bridge utilizando **Protocolos** (da biblioteca `typing`) ou **Classes Abstratas (ABCs)** para definir a interface de implementação.

```python
from typing import Protocol
from abc import ABC, abstractmethod

# 1. O IMPLEMENTADOR (Interface para os motores de envio)
class ProvedorDeEnvio(Protocol):
    def enviar(self, texto: str) -> None:
        ...

# Implementações concretas
class ServicoEmail:
    def enviar(self, texto: str):
        print(f"Enviando E-mail: {texto}")

class ServicoSMS:
    def enviar(self, texto: str):
        print(f"Enviando SMS: {texto}")

# 2. A ABSTRAÇÃO (A lógica da mensagem - Esta é a PONTE)
class Notificacao(ABC):
    def __init__(self, provedor: ProvedorDeEnvio):
        # Mantém uma referência para o implementador (delegação)
        self._provedor = provedor 

    @abstractmethod
    def disparar(self, mensagem: str):
        pass

# 3. ABSTRAÇÃO REFINADA
class NotificacaoUrgente(Notificacao):
    def disparar(self, mensagem: str):
        # A abstração decide "o quê", a implementação faz "como"
        self._provedor.enviar(f"ALERTA CRÍTICO: {mensagem}")

# Uso no Back-end
if __name__ == "__main__":
    # Criamos a ponte ligando uma notificação urgente ao SMS
    aviso = NotificacaoUrgente(ServicoSMS())
    aviso.disparar("O servidor caiu!") 
    # Saída: Enviando SMS: ALERTA CRÍTICO: O servidor caiu!
```

Neste design, você pode adicionar um novo `ServicoWhatsApp` ou um novo tipo de `NotificacaoPromocional` sem nunca precisar alterar as classes que já existem.

---

### **Aplicações Reais no Back-end**

O padrão Bridge é amplamente utilizado em arquiteturas de servidores para lidar com a diversidade de tecnologias:

- **Drivers de Bancos de Dados:** Sistemas de back-end frequentemente definem uma abstração para operações de banco de dados (ex: um repositório), enquanto os drivers específicos (PostgreSQL, MySQL, Oracle) servem como os implementadores.
- **Gateways de Pagamento:** Você pode ter uma abstração de `ProcessadorDePagamento` que funciona independentemente de o implementador real ser o **Stripe**, **PayPal** ou **Adyen**. O processo de checkout permanece consistente, mesmo trocando o provedor.
- **Agregação de Microserviços:** Um microserviço de nível superior pode atuar como uma abstração que delega o trabalho pesado para microserviços de nível inferior (implementadores), permitindo que a lógica de controle evolua separadamente dos serviços de dados.
- **Renderização e Geração de Documentos:** Imagine uma classe `Relatorio` (abstração) que pode ser renderizada por diferentes implementadores, como um motor de **PDF**, **HTML** ou **Excel**. O Bridge evita que você precise criar classes como `RelatorioFinanceiroPDF` e `RelatorioFinanceiroHTML`.
- **Drivers de Dispositivos e IoT:** Em sistemas que controlam hardware, a abstração (ex: `ComandoDeLuz`) é separada da implementação do fabricante (ex: driver para lâmpadas **Philips Hue** vs **Xiaomi**), permitindo suporte a novos dispositivos sem mudar o código de automação central.

### **Por que isso é importante?**

O Bridge evita o acoplamento forte e a "explosão de classes". No back-end, isso significa que seu código se torna **testável** (você pode injetar um implementador _mock_ para testes) e **sustentável**, permitindo trocar bibliotecas de infraestrutura sem tocar na regra de negócio principal do seu sistema.

## Front-end

### 1. O Problema: A Explosão de Classes

Imagine que você está criando um sistema de botões para um portal em **Angular**. Você tem dois tipos de botões: um `BotaoSimples` e um `BotaoComIcone`. Até aí, tudo bem.

Agora, o designer pede que esses botões tenham dois temas: **Dark** e **Light**. Se usarmos herança comum, teríamos que criar:

- `BotaoSimplesDark`
- `BotaoSimplesLight`
- `BotaoComIconeDark`
- `BotaoComIconeLight`

E se amanhã surgir um tema "Blue"? E um "BotaoDropdown"? O número de combinações cresce de forma **exponencial**. Isso é o que chamamos de "explosão de classes". Você acaba com um emaranhado de código difícil de manter.

### 2. A Solução: O Padrão Bridge

O **Bridge** sugere que, em vez de tentar enfiar tudo em uma única hierarquia de herança, você divida sua classe em duas partes independentes:

1. **Abstração:** É a camada de controle de alto nível (o que o usuário vê ou interage).
2. **Implementação:** É a camada de baixo nível que faz o trabalho pesado ou lida com detalhes específicos (como cores ou APIs de plataformas).

Em vez de ser um "Botão Dark", o botão agora **possui um** tema. Essa referência entre o botão e o tema é a nossa **ponte**.

### 3. Exemplo Prático: TypeScript + Angular

Vamos ver como aplicar isso em um cenário front-end real: diferentes tipos de **controles remotos** para diferentes **dispositivos** (TVs e Rádios).

#### A Implementação (O "Como funciona")

Primeiro, definimos uma interface comum para todos os dispositivos. Eles são a nossa "plataforma".

```typescript
// Interface de Implementação
export interface Dispositivo {
  estaLigado(): boolean;
  ligar(): void;
  desligar(): void;
}

// Implementações concretas
export class TV implements Dispositivo {
  private ligado = false;
  estaLigado() { return this.ligado; }
  ligar() { this.ligado = true; console.log("TV ligada!"); }
  desligar() { this.ligado = false; console.log("TV desligada!"); }
}

export class Radio implements Dispositivo {
  private ligado = false;
  estaLigado() { return this.ligado; }
  ligar() { this.ligado = true; console.log("Rádio tocando!"); }
  desligar() { this.ligado = false; console.log("Rádio em silêncio..."); }
}
```

#### A Abstração (O "O que o usuário vê")

O controle remoto é a nossa abstração. Ele usa a interface `Dispositivo` para fazer o trabalho, sem saber se está falando com uma TV ou um Rádio.

```typescript
// Abstração Base
export class ControleRemoto {
  // A "Ponte" para a implementação
  constructor(protected dispositivo: Dispositivo) {}

  togglePower(): void {
    if (this.dispositivo.estaLigado()) {
      this.dispositivo.desligar();
    } else {
      this.dispositivo.ligar();
    }
  }
}

// Abstração Refinada (versão com mais botões)
export class ControleRemotoAvancado extends ControleRemoto {
  mutar(): void {
    console.log("Dispositivo mutado!");
  }
}
```

### 4. Por que usar isso no seu dia a dia?

- **Independência Total:** Você pode criar novos temas (implementações) ou novos tipos de componentes (abstrações) sem que um quebre o outro.
- **Código Limpo:** Você foca na lógica de alto nível na abstração e nos detalhes técnicos na implementação (Princípio da Responsabilidade Única).
- **Extensível:** Segue o Princípio Aberto/Fechado: você introduz novas classes sem alterar as que já funcionam.
- **Troca em Tempo de Execução:** Você pode mudar o objeto de implementação dentro da abstração a qualquer momento (ex: trocar o tema do site com um clique).

### Resumo para levar para casa:

- O **Bridge** separa "o que a classe faz" de "como ela faz".
- Ele usa **composição** (um objeto contém o outro) em vez de herança pesada.
- No front-end, use-o para separar componentes de UI (abstração) de renderizadores ou provedores de dados específicos (implementação).

Agora, da próxima vez que pedirem para você criar 10 variações de um mesmo componente, não use herança infinita. Construa uma ponte!
