O padrão de projeto **Prototype** (Protótipo) é focado na ideia de **copiar** algo que já existe em vez de criar algo novo do zero. Se o padrão Builder era como montar um sanduíche personalizado passo a passo, o Prototype é como usar uma **máquina de xerox** ou um **carimbo**: você já tem um modelo pronto e apenas faz cópias dele, ajustando pequenos detalhes se necessário.

## O Problema: Criar do zero é cansativo (ou caro)

Imagine que você está criando um jogo e precisa de 100 soldados. Cada soldado tem uma armadura complexa, um histórico de batalhas e uma lista de habilidades. Se você for criar cada um "do zero", o computador terá que gastar muita energia processando todas essas informações repetidamente. Além disso, se os soldados forem quase idênticos, seu código ficará cheio de repetições desnecessárias.

## A Solução: O Padrão Prototype

Em vez de construir cada objeto novo, você cria um **modelo inicial** (o protótipo). Quando precisar de um novo, você simplesmente pede para esse modelo: "Ei, tire uma cópia de você mesmo para mim!".

No modo tradicional, definimos uma regra que diz que o objeto sabe se duplicar.

```kotlin 
// Uma regra que diz: "quem for um Protótipo deve saber se clonar"
interface Protótipo {
    fun clonar(): Protótipo
}

class Soldado(val arma: String, val saude: Int) : Protótipo {
    override fun clonar(): Soldado {
        // Cria uma nova cópia de si mesmo com os mesmos dados
        return Soldado(arma, saude)
    }
}

// Como usar:
val soldadoMestre = Soldado("Espada Laser", 100)
val soldadoRecruta = soldadoMestre.clonar() // Xerox do mestre!
```

---


## O "Jeito Kotlin" (Data Classes e o método `copy`)

Assim como no Builder, o Kotlin é tão moderno que ele já traz o padrão Prototype "dentro da caixa" através das **Data Classes**. Você não precisa criar um método `clonar()` manualmente; o Kotlin cria o método **copy()** para você automaticamente.

**Por que o** **copy()** **é incrível?** Ele permite que você clone o objeto e, no mesmo instante, mude apenas o que for diferente.

```kotlin
// O Kotlin já entende isso como um protótipo pronto para ser copiado
data class Usuario(
    val nome: String,
    val nivel: String,
    val permissoes: List<String>
)

val adminBase = Usuario("Admin", "Nível Total", listOf("Ler", "Escrever", "Excluir"))

// Criando um novo admin a partir do protótipo, mudando apenas o nome
val novoAdmin = adminBase.copy(nome = "João") 

println(novoAdmin) 
// Resultado: Usuario(nome=João, nivel=Nível Total, permissoes=[Ler, Escrever, Excluir])
```

Neste exemplo, você não precisou redefinir o nível ou as permissões; o Kotlin copiou tudo do `adminBase` para o `novoAdmin`.

---

## Aplicações Reais

- **Sistemas de Permissões:** Como no exemplo acima, onde novos usuários de um mesmo cargo (como "Editor" ou "Visitante") recebem uma cópia de um perfil padrão de permissões.
- **Jogos (Inimigos):** Criar centenas de monstros que aparecem na tela de forma rápida, copiando um modelo básico em vez de carregar cada um do banco de dados.
- **Interface Gráfica (UI):** Se você tem um botão com um estilo visual complexo (cor, sombra, borda) e quer criar outro igual, você clona o primeiro e apenas muda o texto do botão.

**Resumo para o iniciante:** O Builder serve para quando você quer construir algo **novo e detalhado**. O Prototype serve para quando você já tem algo pronto e quer **multiplicar** isso rapidamente, fazendo apenas pequenos ajustes nas cópias.