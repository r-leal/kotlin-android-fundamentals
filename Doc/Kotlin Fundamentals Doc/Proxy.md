O padrão **Proxy** (que significa "procurador" ou "representante") é um padrão de projeto estrutural que funciona como um intermediário ou um "guarda-costas" para outro objeto.

A melhor forma de entender é pensar em um **Secretário(a)**:

1. Se você quer falar com um **Diretor** muito ocupado (o objeto real), você fala primeiro com o **Secretário** (o Proxy).
2. O secretário pode resolver coisas simples sem incomodar o diretor, pode verificar se você tem permissão para entrar, ou pode pedir para você esperar até que o diretor esteja realmente disponível.

Na programação, você interage com o Proxy como se fosse o objeto real, mas o Proxy controla o acesso a esse objeto por trás das cenas.

---

## Por que usar um Proxy?

Existem três motivos principais (tipos de Proxy) que são ideais:

1. **Proxy Virtual (Lazy Loading):** Usado quando o objeto real é "pesado" (consome muita memória ou tempo para carregar). O Proxy adia a criação do objeto até o exato momento em que ele for necessário.
2. **Proxy de Proteção:** Atua como um segurança, verificando se quem está chamando a função tem permissão para acessar aquele objeto sensível.
3. **Proxy Remoto:** Representa um objeto que não está no seu celular, mas sim em outro lugar, como um servidor na nuvem.

---

## Exemplo Prático em Kotlin (O jeito manual)

Imagine um sistema que carrega uma imagem pesada da internet.

```kotlin
// 1. O CONTRATO (Interface comum)
interface Imagem {
    fun exibir()
}

// 2. O OBJETO REAL (Pesado e lento)
class ImagemReal(val url: String) : Imagem {
    init {
        println("Baixando imagem pesada de $url... (isso leva tempo)")
    }

    override fun exibir() {
        println("Exibindo a imagem de $url")
    }
}

// 3. O PROXY (O intermediário leve)
class ImagemProxy(val url: String) : Imagem {
    private var imagemReal: ImagemReal? = null

    override fun exibir() {
        // Só cria o objeto real na primeira vez que for exibir
        if (imagemReal == null) {
            imagemReal = ImagemReal(url)
        }
        imagemReal?.exibir()
    }
}

fun main() {
    val imagem = ImagemProxy("http://foto.com/gato_pesado.png")
    
    // A imagem ainda NÃO foi baixada aqui.
    println("O Proxy foi criado, mas a rede ainda está livre.")
    
    // Agora sim, o Proxy chama o objeto real
    imagem.exibir() 
}
```

---

## O "Jeito Kotlin" (by lazy)

O Kotlin é tão moderno que ele tem o padrão Proxy Virtual embutido através da função **by lazy**. Ela garante que o código dentro do bloco só seja executado quando você acessar a variável pela primeira vez.

```kotlin
val imagemPesada: ByteArray by lazy {
    println("Buscando dados da rede...")
    URL("http://exemplo.com/foto.png").readBytes()
}
```

---

## Aplicações Reais no Android

No desenvolvimento Android, o Proxy é usado constantemente:

- **Carregamento de Imagens:** Aplicativos como o Instagram usam Proxies para mostrar um "espaço reservado" (placeholder) cinza enquanto a imagem real está sendo baixada em segundo plano.
- **Retrofit:** Esta biblioteca famosa para lidar com APIs cria Proxies automaticamente para suas interfaces. Você define as funções, e o Retrofit cria um Proxy que sabe como ir buscar os dados na internet para você.
- **Segurança (Permissions):** O sistema Android age como um Proxy de proteção quando um app pede para usar a câmera. Ele interrompe a chamada e pergunta ao usuário: "Você permite que este app acesse a câmera?".

## Resumo:

- **Proxy** = Um representante que controla o acesso.
- **Diferença do Decorador:** Enquanto o Decorador adiciona novas funções ("enfeita" o objeto), o Proxy foca em **controlar como e quando** o objeto é usado.