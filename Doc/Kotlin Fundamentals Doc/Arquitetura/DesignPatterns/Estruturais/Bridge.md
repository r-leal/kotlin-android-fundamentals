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