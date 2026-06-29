
Os **Design Patterns** (padrões de projeto) são um conjunto de **melhores práticas** e soluções replicáveis para desafios que desenvolvedores enfrentam com frequência. Eles não são códigos prontos, mas sim modelos testados que ajudam a escrever sistemas mais eficientes, flexíveis e fáceis de manter.

## O que são Padrões Criacionais?

Pode parecer que a única forma de criar um objeto é usando um construtor simples (como o `new` em Java ou chamando a classe em Kotlin). No entanto, os padrões criacionais **abstraem o processo de instanciação**.

Em vez de você "fabricar" o objeto manualmente em todos os lugares do código, esses padrões escondem a lógica de como os objetos são criados e como eles se encaixam. Isso dá muita flexibilidade ao sistema, permitindo decidir **quem** cria o objeto, **como** ele é criado e **quando** isso deve acontecer.

Abaixo, apresento os cinco padrões criacionais clássicos definidos pela literatura:

1. **[[Singleton]]:** É usado quando você precisa garantir que existirá **apenas uma única instância** de uma classe em todo o sistema. Em Kotlin, isso é tão comum que a linguagem possui a palavra-chave `object` para criá-lo automaticamente.
2. **[[Factory Method]]:** Este padrão define uma "receita" (interface) para criar um objeto, mas permite que as **subclasses decidam** qual classe específica será instanciada. Ele é útil quando sua classe principal não sabe exatamente qual tipo de objeto precisará criar no futuro.
3. **[[Abstract Factory]]:** Imagine uma fábrica de fábricas. Ele fornece uma interface para criar **famílias de objetos relacionados** (como botões e barras de rolagem de um mesmo estilo visual) sem que o código precise conhecer as classes concretas de cada um.
4. **Builder:** É ideal para criar **objetos complexos passo a passo**. Se um objeto precisa de muitos parâmetros para ser construído (alguns obrigatórios e outros opcionais), o Builder organiza esse processo de forma clara e flexível.
5. **Prototype:** Em vez de criar um objeto do zero, o Prototype cria novos objetos **clonando (copiando) uma instância já existente**, chamada de protótipo. Isso é vantajoso quando a criação de um novo objeto é muito cara em termos de processamento ou memória.