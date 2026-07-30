
Os **Design Patterns** (padrões de projeto) são um conjunto de **melhores práticas** e soluções replicáveis para desafios que desenvolvedores enfrentam com frequência. Eles não são códigos prontos, mas sim modelos testados que ajudam a escrever sistemas mais eficientes, flexíveis e fáceis de manter.

## O que são Padrões Criacionais?

Pode parecer que a única forma de criar um objeto é usando um construtor simples (como o `new` em Java ou chamando a classe em Kotlin). No entanto, os padrões criacionais **abstraem o processo de instanciação**.

Em vez de você "fabricar" o objeto manualmente em todos os lugares do código, esses padrões escondem a lógica de como os objetos são criados e como eles se encaixam. Isso dá muita flexibilidade ao sistema, permitindo decidir **quem** cria o objeto, **como** ele é criado e **quando** isso deve acontecer.

Abaixo, apresento os cinco padrões criacionais clássicos definidos pela literatura:

1. **[[Singleton]]:** É usado quando você precisa garantir que existirá **apenas uma única instância** de uma classe em todo o sistema. Em Kotlin, isso é tão comum que a linguagem possui a palavra-chave `object` para criá-lo automaticamente.
2. **[[Factory Method]]:** Este padrão define uma "receita" (interface) para criar um objeto, mas permite que as **subclasses decidam** qual classe específica será instanciada. Ele é útil quando sua classe principal não sabe exatamente qual tipo de objeto precisará criar no futuro.
3. **[[Abstract Factory]]:** Imagine uma fábrica de fábricas. Ele fornece uma interface para criar **famílias de objetos relacionados** (como botões e barras de rolagem de um mesmo estilo visual) sem que o código precise conhecer as classes concretas de cada um.
4. **[[Builder]]:** É ideal para criar **objetos complexos passo a passo**. Se um objeto precisa de muitos parâmetros para ser construído (alguns obrigatórios e outros opcionais), o Builder organiza esse processo de forma clara e flexível.
5. **[[Prototype]]:** Em vez de criar um objeto do zero, o Prototype cria novos objetos **clonando (copiando) uma instância já existente**, chamada de protótipo. Isso é vantajoso quando a criação de um novo objeto é muito cara em termos de processamento ou memória.

## Padrões de projeto estruturais

Os **padrões de projeto estruturais** tratam da composição de classes ou objetos para formar estruturas maiores e mais complexas, visando simplificar as interações e relacionamentos entre as partes do sistema. Eles ajudam a garantir que, quando uma parte do sistema muda, a estrutura inteira não precise ser alterada.

Abaixo, apresento um resumo introdutório dos principais padrões estruturais:

1. **[[Adapter]]**
- **O que é:** Funciona como um adaptador de tomada da vida real.
- **Aplicação:** Ele converte a interface de uma classe em outra interface que o cliente espera, permitindo que classes com interfaces incompatíveis trabalhem juntas.
- **Exemplo prático:** Adaptar um plugue de padrão americano para funcionar em uma tomada europeia no seu código.

2. **[[Bridge]]**
- **O que é:** Cria uma "ponte" entre uma abstração e sua implementação.
- **Aplicação:** O objetivo é separar a interface do que ela faz (abstração) de como ela realmente funciona (implementação), permitindo que ambas variem de forma independente.
- **Exemplo prático:** Ter uma hierarquia para tipos de janelas (lógica) e outra separada para como elas são desenhadas em diferentes sistemas como Windows ou Linux.

3. **[[Composite]]**
- **O que é:** Agrupa objetos em estruturas de árvore para representar hierarquias do tipo "parte-todo".
- **Aplicação:** Ele permite que os clientes tratem objetos individuais e grupos de objetos de maneira uniforme.
- **Exemplo prático:** Em um sistema gráfico, tratar um único "Círculo" e um "Grupo de Desenhos" (composto por vários círculos e quadrados) da mesma forma.

4. **[[Decorator]]** 
- **O que é:** Adiciona novas responsabilidades a um objeto de forma dinâmica.
- **Aplicação:** É uma alternativa flexível ao uso de herança para estender funcionalidades, permitindo "embrulhar" um objeto com camadas extras de comportamento.
- **Exemplo prático:** Adicionar bordas ou barras de rolagem a uma janela de texto apenas quando necessário.

5. **[[Facade]]** 

- **O que é:** Uma "cara" amigável para um sistema complexo.
- **Aplicação:** Fornece uma interface única e simplificada para um conjunto de interfaces em um subsistema, escondendo a complexidade interna do desenvolvedor.
- **Exemplo prático:** Uma única função `iniciarComputador()` que executa internamente tarefas complexas como checagem de CPU, memória e carregamento de drivers.

6. **[[Flyweight]]**

- **O que é:** Foca no compartilhamento para economizar recursos.
- **Aplicação:** Usa o compartilhamento de objetos para suportar eficientemente grandes quantidades de objetos pequenos (ajuda a reduzir o uso de memória).
- **Exemplo prático:** Em um editor de texto, em vez de criar um objeto para cada letra "A" em um livro, você cria apenas um objeto "A" e o compartilha em todas as posições onde ele aparece.

7. **[[Proxy]]**

- **O que é:** Um substituto ou representante de outro objeto.
- **Aplicação:** Fornece um intermediário para controlar o acesso a um objeto original, podendo ser usado para carregamento preguiçoso (_lazy loading_), segurança ou controle de acesso remoto.
- **Exemplo prático:** Um objeto Proxy que carrega uma imagem pesada da internet apenas no momento em que ela realmente precisa aparecer na tela.

## Padrões de projeto comportamentais

Os **padrões comportamentais** são modelos que explicam como os objetos de um programa devem **interagir e se comunicar** entre si. Eles não focam em como os objetos são criados, mas sim em como eles trabalham juntos para realizar tarefas complexas, permitindo que o sistema mude de comportamento de forma flexível.

Abaixo, apresento um resumo de cada um, explicado de forma simples:

1. **[[Strategy]]** (Estratégia)

- **O que é:** Permite que você escolha entre diferentes formas de realizar uma tarefa enquanto o programa está rodando.
- **Exemplo:** Imagine um herói de jogo que pode atacar com uma espada ou com um arco. O herói não muda, mas a "estratégia" de ataque pode ser trocada a qualquer momento.

2. **[[Iterator]]** (Iterador)

- **O que é:** Uma maneira de percorrer todos os itens de uma lista ou coleção sem precisar entender como essa lista está organizada internamente.
- **Exemplo:** É como folhear as páginas de um livro; você só precisa saber como ir para a "próxima página", sem se preocupar com a forma como as folhas foram coladas na encadernação.

3. **[[State]]** (Estado)

- **O que é:** Faz com que um objeto mude o que ele faz dependendo do seu "humor" ou "estado" atual.
- **Exemplo:** Pense em um caracol em um jogo: se ele está com a vida cheia, ele ataca; se está ferido, ele se esconde na concha. O caracol é o mesmo, mas suas ações mudam conforme seu estado.

4. **[[Command]]** (Comando)

- **O que é:** Transforma uma ação em um "token" ou "ticket" que pode ser guardado e executado mais tarde.
- **Exemplo:** É como um pedido em um restaurante: o garçom escreve o comando em um papel, e o cozinheiro pode prepará-lo imediatamente, colocá-lo em uma fila ou até cancelá-lo.

5. **[[Chain of Responsibility]]** (Corrente de Responsabilidade)

- **O que é:** Passa uma tarefa por uma "corrente" de pessoas até que alguém finalmente resolva o problema.
- **Exemplo:** Se você clica em um botão de ajuda, ele pergunta ao componente acima dele; se este não souber a resposta, pergunta à janela inteira, e assim por diante, até chegar ao sistema principal.

6. **[[Interpreter]]** (Interpretador)

- **O que é:** Cria uma forma do programa entender uma linguagem simples ou regras específicas criadas por você.
- **Exemplo:** É como criar um "dialeto" especial para que o seu programa saiba ler instruções escritas de um jeito fácil para humanos, como se fosse uma receita de bolo personalizada.

7. **[[Mediator]]** (Mediador)

- **O que é:** Um "intermediário" que organiza a conversa entre vários objetos para que eles não precisem falar diretamente uns com os outros.
- **Exemplo:** Imagine uma torre de controle de aeroporto; os pilotos não falam entre si para pousar, eles falam apenas com a torre, que organiza todo o tráfego.

8. **[[Memento]]** (Lembrança)

- **O que é:** Permite tirar uma "foto" do estado de um objeto para que você possa restaurá-lo depois.
- **Exemplo:** É o famoso botão "Desfazer" (Undo) ou o sistema de "Save Game" em jogos, que permite voltar exatamente para onde as coisas estavam antes de um erro.

9. **[[Observer]]** (Observador)

- **O que é:** Cria um sistema de "assinatura" onde um objeto avisa automaticamente a todos os seus "assinantes" quando algo muda.
- **Exemplo:** Funciona como um canal no YouTube ou um jornal: sempre que sai uma notícia nova, todos que se inscreveram recebem o aviso.

10. **[[Template Method]]** (Método Modelo)

- **O que é:** Define o "esqueleto" ou o passo a passo de uma tarefa, mas permite que partes desses passos sejam personalizadas.
- **Exemplo:** Pense em uma rotina diária: você sempre acorda, toma café e vai trabalhar; o "modelo" é fixo, mas o que você come ou onde trabalha pode mudar a cada dia.

11. **[[Visitor]]** (Visitante)

- **O que é:** Permite que um "estranho" entre em uma estrutura de objetos e realize ações neles sem que você precise mudar o código original desses objetos.
- **Exemplo:** É como um técnico de manutenção visitando casas em um condomínio: ele entra e faz o reparo necessário sem que a estrutura da casa precise ser alterada.