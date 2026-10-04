# Simulador de Gerenciamento de Memória

Projeto desenvolvido para a disciplina de Sistemas Operacionais, com o objetivo de simular diferentes algoritmos de alocação de memória e comparar seus comportamentos através de métricas obtidas experimentalmente.

## Objetivo

O simulador representa uma memória de tamanho fixo de **1000 unidades**, organizada através de uma lista encadeada de blocos livres e ocupados.

Durante a simulação, processos são gerados com tamanhos aleatórios entre **10 e 50 unidades** e precisam ser alocados utilizando um dos algoritmos disponíveis.

Caso nenhum bloco livre comporte o processo, ele é descartado.

## Algoritmos implementados

O projeto possui quatro algoritmos de alocação:

- **First Fit:** utiliza o primeiro bloco livre que comporta o processo.
- **Next Fit:** continua a busca a partir da posição onde a última alocação terminou.
- **Best Fit:** utiliza o menor bloco livre capaz de comportar o processo.
- **Worst Fit:** utiliza o maior bloco livre disponível.

## Funcionamento da memória

A memória possui tamanho total de:

```text
1000 unidades
```

Cada região é representada por um `BlocoMemoria`, que pode estar livre ou associado a um processo.

Exemplo:

```text
[PID 1 | 30]
[LIVRE | 50]
[PID 2 | 40]
[LIVRE | 880]
```

Quando um processo é removido, seu bloco volta a ficar livre.

Caso existam blocos livres consecutivos, eles são unidos para representar corretamente uma única região contínua de memória.

## Geração de processos

A classe `GeradorDeProcessos` é responsável pela criação dos processos.

Cada processo possui:

```text
ID único e incremental
Tamanho aleatório entre 10 e 50
```

Exemplo:

```text
[PID: 1 | Tamanho: 28]
[PID: 2 | Tamanho: 41]
[PID: 3 | Tamanho: 17]
```

## Simulação

Cada execução representa **100 segundos simulados**.

A cada segundo:

1. São gerados **2 novos processos**.
2. Os processos tentam ser alocados utilizando o algoritmo selecionado.
3. Caso não exista espaço adequado, o processo é descartado.
4. São escolhidos aleatoriamente **1 ou 2 processos** para sair da memória.
5. A ocupação da memória é registrada.

O tempo é simulado através dos ciclos do programa, portanto não é necessário esperar 100 segundos reais para cada execução.

## Métricas

Ao final de cada execução são calculadas três métricas:

### Tamanho médio dos processos

Representa a média dos tamanhos de todos os processos gerados durante a simulação.

### Ocupação média da memória

Representa o percentual médio de memória ocupada ao longo dos 100 segundos simulados.

### Taxa de descarte

Representa o percentual de processos que não conseguiram ser alocados por falta de um bloco livre adequado.

## Experimento

Para reduzir a influência da aleatoriedade, cada algoritmo é executado:

```text
100 vezes
```

Cada execução possui:

```text
100 segundos
2 processos gerados por segundo
200 processos gerados por execução
```

Portanto, para cada algoritmo são gerados aproximadamente:

```text
20.000 processos
```

Considerando os quatro algoritmos:

```text
80.000 processos
```

Ao final, são calculadas as médias globais das 100 execuções.

## Resultados obtidos

Em uma execução do experimento com 100 repetições por algoritmo, foram obtidos os seguintes resultados:

| Algoritmo | Tamanho médio | Ocupação média | Taxa de descarte |
|---|---:|---:|---:|
| First Fit | 30,10 | 58,70% | 10,22% |
| Next Fit | 29,99 | 57,76% | 10,56% |
| Best Fit | 29,89 | 59,50% | 9,15% |
| Worst Fit | 29,85 | 54,60% | 11,29% |

Os resultados podem apresentar pequenas variações entre execuções devido à geração e remoção aleatória dos processos.

Neste experimento, o **Best Fit** apresentou a maior ocupação média da memória e a menor taxa média de descarte.

O **Worst Fit** apresentou a menor ocupação média e a maior taxa de descarte entre os quatro algoritmos.

## Estrutura do projeto

```text
src
│
├── controller
│   ├── SimuladorMemoria.java
│   └── ExperimentoAlocacao.java
│
├── model
│   ├── Processo.java
│   ├── BlocoMemoria.java
│   ├── Memoria.java
│   ├── TipoAlgoritmoAlocacao.java
│   ├── ResultadoSimulacao.java
│   └── ResultadoExperimento.java
│
├── service
│   └── GeradorDeProcessos.java
│
└── Main.java
```

## Principais classes

### Processo

Representa um processo contendo:

```text
ID
Tamanho de alocação
```

### BlocoMemoria

Representa uma região da memória, podendo estar livre ou ocupada por um processo.

### Memoria

Responsável pelo gerenciamento dos blocos e implementação dos algoritmos:

```text
First Fit
Next Fit
Best Fit
Worst Fit
```

Também realiza a remoção de processos e a união de blocos livres adjacentes.

### SimuladorMemoria

Controla uma execução da simulação, incluindo:

```text
Geração de processos
Alocação
Remoção aleatória
Controle dos segundos
Coleta das métricas
```

### ExperimentoAlocacao

Executa cada algoritmo repetidamente e calcula as médias globais dos resultados.

## Como executar

O projeto foi desenvolvido em **Java**.

Para executar pelo VS Code:

1. Abra o projeto.
2. Certifique-se de que o JDK está configurado.
3. Execute o arquivo `Main.java`.

O `Main` executa automaticamente os quatro algoritmos:

```java
FIRST_FIT
NEXT_FIT
BEST_FIT
WORST_FIT
```

Cada algoritmo é executado 100 vezes, com 100 segundos simulados por execução.

## Tecnologias

- Java
- Programação Orientada a Objetos
- LinkedList
- Random
- Algoritmos de gerenciamento de memória