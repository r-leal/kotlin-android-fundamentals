O padrão **Chain of Responsibility** (Corrente de Responsabilidade) é um modelo de projeto comportamental focado em dividir uma tarefa complexa em pequenos pedaços organizados como elos de uma corrente.

A forma mais fácil de entender é pensar em um **suporte técnico de telemarketing**:

1. Você liga para reclamar de um problema. O **atendente nível 1** (iniciante) atende.
2. Se for algo simples, ele resolve ali mesmo.
3. Se for complexo, ele diz: "Vou passar para o meu **supervisor**".
4. O supervisor (nível 2) tenta resolver. Se não conseguir, passa para o **gerente** (nível 3).
5. O pedido vai subindo a escada (a corrente) até que alguém finalmente o resolva ou a corrente acabe.

Na programação, isso evita que você crie um código gigante cheio de `if` e `else`. Em vez disso, você cria vários "ajudantes" independentes, e cada um decide se resolve o problema ou o passa adiante.

---

## Exemplo Prático em Kotlin

Imagine um sistema que valida se um usuário pode postar um comentário. Precisamos checar se o texto está vazio e se o usuário está logado.

```kotlin
// 1. O CONTRATO (Interface para todos os elos da corrente)
interface Validador {
    fun validar(comentario: String)
}

// 2. ELO 1: Checa se o texto está vazio
class ValidadorTexto(private val proximo: Validador?) : Validador {
    override fun validar(comentario: String) {
        if (comentario.isEmpty()) {
            println("Erro: Comentário vazio!")
        } else {
            // Se estiver OK, passa para o próximo elo
            proximo?.validar(comentario)
        }
    }
}

// 3. ELO 2: Checa se tem palavras proibidas
class ValidadorFiltro(private val proximo: Validador?) : Validador {
    override fun validar(comentario: String) {
        if (comentario.contains("spam")) {
            println("Erro: Conteúdo proibido!")
        } else {
            proximo?.validar(comentario)
        }
    }
}

// 4. ELO FINAL: O que fazer quando tudo der certo
class Publicador : Validador {
    override fun validar(comentario: String) {
        println("Sucesso: Comentário '$comentario' publicado!")
    }
}

fun main() {
    // Montando a corrente: Texto -> Filtro -> Publicar
    val corrente = ValidadorTexto(ValidadorFiltro(Publicador()))

    corrente.validar("Olá, mundo!") // Passa por todos e publica
    corrente.validar("")            // Para no primeiro elo
}
```

**Dica de Kotlin:** Em versões mais modernas e concisas, você pode usar **funções** em vez de classes para criar esses filtros (muitas vezes chamados de _middlewares_).

---

## Aplicações Reais no Android

Você encontrará o padrão Chain of Responsibility em lugares fundamentais do sistema Android:

- **Eventos de Toque (Touch Events):** Este é o exemplo clássico. Quando você toca em um botão dentro de uma lista, o Android pergunta primeiro ao **Botão** se ele quer tratar o toque. Se o botão não tratar, ele passa para a **Lista** (Parent), que pode passar para a **Tela** inteira (Activity).
- **Interceptadores de Rede (OkHttp/Retrofit):** Se você usa o Retrofit para buscar dados da internet, ele usa uma corrente para processar sua chamada. Um elo adiciona o "Token de Acesso", outro elo faz o "Log do erro" e o elo final realmente busca os dados na web.
- **Sistemas de Ajuda Contextual:** Em interfaces complexas, um pedido de ajuda pode ser passado de um componente pequeno para a janela principal até encontrar a informação correta para o usuário.

## Vantagens:

1. **Código Organizado:** Cada classe foca em apenas uma regra (Princípio da Responsabilidade Única).
2. **Flexibilidade:** Você pode mudar a ordem da corrente ou adicionar novos elos (como um "Validador de Emojis") sem quebrar o que já existe.
3. **Desacoplamento:** Quem envia o pedido não precisa saber quem vai resolvê-lo no final da linha.