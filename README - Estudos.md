# Guia de Estudo — Simulador de Gerenciamento de Memória

---

# 1. Objetivo do projeto

O projeto simula uma memória de tamanho fixo:

```text
1000 unidades
```

Processos são gerados com:

```text
ID único e incremental
Tamanho aleatório entre 10 e 50
```

Esses processos precisam ser alocados na memória usando um dos quatro algoritmos:

```text
First Fit
Next Fit
Best Fit
Worst Fit
```

Se nenhum espaço livre for grande o suficiente, o processo é descartado.

---

# 2. Estrutura geral

O projeto foi dividido em três partes principais:

```text
model
controller
service
```

A estrutura ficou:

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

---

# 3. Processo

A classe `Processo` representa cada processo criado pelo sistema.

Cada processo possui:

```java
private final int id;
private final int tamanho;
```

Exemplo:

```text
PID 1
Tamanho 35
```

Isso significa que o processo possui ID 1 e precisa ocupar 35 unidades da memória.

---

# 4. GeradorDeProcessos

A classe `GeradorDeProcessos` é responsável por criar novos processos.

Ela possui um contador de IDs:

```java
private final AtomicInteger contadorId = new AtomicInteger(1);
```

Assim os IDs são gerados de forma incremental:

```text
1
2
3
4
5
...
```

O tamanho do processo é aleatório:

```java
int tamanho = random.nextInt(41) + 10;
```

Isso gera valores entre:

```text
10 e 50
```

Portanto:

```text
PID 1 → 27
PID 2 → 41
PID 3 → 15
```

---

# 5. BlocoMemoria

A memória não é tratada como apenas um número.

Ela é dividida em blocos.

Cada `BlocoMemoria` possui:

```text
posição inicial
tamanho
processo
```

Exemplo:

```text
[Inicio: 0 | Tamanho: 30 | PID: 1]
```

Significa que o PID 1 ocupa:

```text
posição 0 até 29
```

Um bloco livre possui:

```text
processo = null
```

Exemplo:

```text
[Inicio: 30 | Tamanho: 970 | LIVRE]
```

---

# 6. Memoria

A classe `Memoria` representa a memória inteira.

Ela possui tamanho:

```java
1000
```

e é representada através de:

```java
LinkedList<BlocoMemoria>
```

No início existe apenas:

```text
[LIVRE | 1000]
```

Ou seja, toda a memória está disponível.

---

# 7. Como um processo é alocado

Imagine:

```text
Memória:
[LIVRE 1000]
```

Chega:

```text
PID 1
Tamanho 30
```

O bloco de 1000 é dividido:

```text
[PID 1 | 30]
[LIVRE | 970]
```

Isso é feito pelo método:

```java
ocuparBloco()
```

Esse método é compartilhado pelos quatro algoritmos.

Os algoritmos apenas decidem:

```text
QUAL bloco será utilizado
```

Depois disso, `ocuparBloco()` realiza a alocação.

---

# 8. First Fit

O First Fit procura desde o início da memória.

Ele escolhe:

```text
o primeiro bloco livre onde o processo cabe
```

Exemplo:

```text
LIVRE 20
LIVRE 50
LIVRE 100
```

Processo:

```text
30
```

Busca:

```text
20 → não cabe
50 → cabe
```

Escolha:

```text
50
```

O algoritmo não continua procurando depois que encontra um bloco válido.

---

# 9. Best Fit

O Best Fit procura todos os blocos livres.

Ele escolhe:

```text
o menor bloco onde o processo ainda cabe
```

Exemplo:

```text
LIVRE 50
LIVRE 30
LIVRE 860
```

Processo:

```text
25
```

Possibilidades:

```text
50
30
860
```

Escolha:

```text
30
```

Porque é o menor espaço capaz de receber o processo.

Depois da alocação:

```text
[PID | 25]
[LIVRE | 5]
```

---

# 10. Worst Fit

O Worst Fit faz o contrário do Best Fit.

Ele escolhe:

```text
o maior bloco livre disponível
```

Exemplo:

```text
LIVRE 50
LIVRE 30
LIVRE 860
```

Processo:

```text
25
```

Escolha:

```text
860
```

Depois:

```text
[PID | 25]
[LIVRE | 835]
```

Importante:

O Worst Fit compara os **espaços livres**, e não o tamanho dos processos já existentes.

---

# 11. Next Fit

O Next Fit é parecido com First Fit.

A diferença é:

```text
First Fit:
sempre começa do início

Next Fit:
continua a busca de onde parou
```

A memória é tratada como uma busca circular.

Exemplo:

```text
posição atual
      ↓
[PID][PID][LIVRE][PID][LIVRE]
```

O próximo processo começa a ser procurado a partir dessa região.

Se chegar ao final sem encontrar espaço:

```text
final → volta para o início
```

Exemplo:

```text
[LIVRE 40] ... [LIVRE 20]
                      ↑
               posição atual
```

Processo:

```text
35
```

No final:

```text
35 cabe em 20?
não
```

Então volta:

```text
35 cabe em 40?
sim
```

E ocupa o espaço do começo.

---

# 12. Remoção de processos

Os processos também podem sair da memória.

Quando um processo é removido:

```java
bloco.setProcesso(null);
```

O bloco passa a ficar livre.

Exemplo:

Antes:

```text
[PID 1 | 20]
[PID 2 | 30]
[PID 3 | 40]
```

Removendo PID 2:

```text
[PID 1 | 20]
[LIVRE | 30]
[PID 3 | 40]
```

Esse espaço pode ser reutilizado futuramente.

---

# 13. União de blocos livres

Quando dois blocos livres ficam lado a lado, eles são unidos.

Exemplo:

```text
[LIVRE 30]
[LIVRE 40]
```

vira:

```text
[LIVRE 70]
```

Isso é necessário porque os dois blocos representam uma região contínua da memória.

Sem essa união, poderia ocorrer uma fragmentação falsa.

---

# 14. Fragmentação

A memória pode possuir espaço livre suficiente no total, mas dividido em vários blocos.

Exemplo:

```text
[LIVRE 20]
[PID]
[LIVRE 40]
```

Espaço livre total:

```text
60
```

Mas um processo de tamanho:

```text
50
```

não pode ser alocado.

Isso acontece porque nenhum bloco contínuo possui tamanho 50.

---

# 15. Descarte

Se nenhum bloco livre conseguir armazenar o processo:

```java
return false;
```

O processo é considerado descartado.

Exemplo:

```text
LIVRE 20
LIVRE 40
```

Processo:

```text
45
```

Mesmo existindo:

```text
60 unidades livres no total
```

o processo não pode ser alocado.

Taxa de descarte:

```text
processos descartados
---------------------- × 100
processos gerados
```

---

# 16. SimuladorMemoria

A classe `SimuladorMemoria` controla uma execução.

A cada segundo simulado:

```text
1. Gera 2 processos
2. Tenta alocar os processos
3. Sorteia 1 ou 2 processos para sair
4. Remove os processos sorteados
5. Registra a ocupação da memória
```

Exemplo:

```text
SEGUNDO 1

gera PID 1
gera PID 2

aloca PID 1
aloca PID 2

remove PID 1
```

Estado final:

```text
PID 2
+ espaço livre restante
```

---

# 17. Tempo simulado

O projeto não espera um segundo real.

Cada repetição do `for` representa:

```text
1 segundo simulado
```

Assim:

```java
for (int segundo = 1; segundo <= 100; segundo++)
```

representa:

```text
100 segundos
```

Isso evita esperar vários minutos ou horas durante os experimentos.

---

# 18. Quantidade de processos por execução

São gerados:

```text
2 processos por segundo
```

durante:

```text
100 segundos
```

Logo:

```text
2 × 100 = 200 processos
```

por execução.

---

# 19. Métrica 1 — tamanho médio dos processos

É calculado usando todos os processos gerados.

Exemplo:

```text
20
30
40
10
```

Média:

```text
20 + 30 + 40 + 10
-----------------
        4

= 25
```

No código são armazenados:

```text
somaTamanhoProcessos
totalProcessosGerados
```

Cálculo:

```text
soma dos tamanhos
-----------------
processos gerados
```

---

# 20. Métrica 2 — ocupação média da memória

A memória possui:

```text
1000 unidades
```

Se estiverem ocupadas:

```text
600
```

a ocupação é:

```text
600 / 1000 × 100
```

Resultado:

```text
60%
```

A ocupação é registrada ao final de cada segundo.

Depois:

```text
ocupação segundo 1
+ ocupação segundo 2
+ ...
+ ocupação segundo 100
-----------------------
          100
```

---

# 21. Métrica 3 — taxa de descarte

Exemplo:

```text
200 processos gerados
20 descartados
```

Cálculo:

```text
20 / 200 × 100
```

Resultado:

```text
10%
```

---

# 22. ResultadoSimulacao

A classe `ResultadoSimulacao` guarda os resultados de uma única execução.

Ela armazena:

```text
tamanho médio
ocupação média
taxa de descarte
```

Exemplo:

```text
Tamanho médio: 30,10
Ocupação média: 58,20%
Taxa de descarte: 10,50%
```

---

# 23. ExperimentoAlocacao

Uma única execução ainda possui muita aleatoriedade.

Por isso cada algoritmo é executado:

```text
100 vezes
```

Cada execução possui:

```text
100 segundos
```

Portanto:

```text
100 execuções
×
200 processos

= 20.000 processos por algoritmo
```

Para os quatro algoritmos:

```text
20.000 × 4
=
80.000 processos
```

---

# 24. ResultadoExperimento

Depois das 100 execuções, é calculada a média global.

Exemplo:

```text
Execução 1 → descarte 9%
Execução 2 → descarte 11%
Execução 3 → descarte 10%
...
```

No final:

```text
soma das taxas
--------------
     100
```

Isso reduz o impacto da aleatoriedade.

---

# 25. Resultado obtido

Em uma execução do experimento foram obtidos:

| Algoritmo | Tamanho médio | Ocupação média | Descarte |
|---|---:|---:|---:|
| First Fit | 30,10 | 58,70% | 10,22% |
| Next Fit | 29,99 | 57,76% | 10,56% |
| Best Fit | 29,89 | 59,50% | 9,15% |
| Worst Fit | 29,85 | 54,60% | 11,29% |

---

# 26. Como interpretar os resultados

## First Fit

Apresentou bom equilíbrio entre:

```text
ocupação
descarte
simplicidade
```

Ele utiliza o primeiro espaço disponível.

---

## Next Fit

Teve resultado próximo ao First Fit.

Sua principal diferença é não voltar sempre para o início da memória.

Isso pode reduzir parte do custo de busca, mas também pode deixar espaços anteriores sem uso durante algum tempo.

---

## Best Fit

Nesse experimento apresentou:

```text
maior ocupação média
menor taxa de descarte
```

Isso ocorreu porque tenta utilizar o menor espaço possível para cada processo.

Assim, tende a preservar regiões maiores para processos futuros.

---

## Worst Fit

Nesse experimento apresentou:

```text
menor ocupação média
maior taxa de descarte
```

Ele sempre utiliza o maior espaço disponível.

Com o tempo, isso pode dividir grandes áreas livres em regiões menores.

---

# 27. Fluxo completo do projeto

A execução pode ser entendida assim:

```text
Main
 ↓
ExperimentoAlocacao
 ↓
SimuladorMemoria
 ↓
GeradorDeProcessos
 ↓
Processo
 ↓
Memoria
 ↓
Algoritmo escolhido
 ↓
BlocoMemoria
 ↓
Alocação ou descarte
 ↓
Remoção de processos
 ↓
Métricas
 ↓
ResultadoSimulacao
 ↓
ResultadoExperimento
```

---

# 28. Resumo dos quatro algoritmos

```text
FIRST FIT
→ primeiro espaço que cabe

NEXT FIT
→ primeiro espaço que cabe a partir da última posição

BEST FIT
→ menor espaço que cabe

WORST FIT
→ maior espaço que cabe
```

Uma forma rápida de memorizar:

```text
First = primeiro

Next = próximo

Best = menor desperdício imediato

Worst = maior espaço disponível
```

---

# 29. Perguntas que podem ser feitas sobre o projeto

## Por que utilizar LinkedList?

Porque o enunciado solicita uma representação da memória através de lista encadeada.

Além disso, os blocos podem ser adicionados e removidos durante a simulação.

---

## Por que existe BlocoMemoria?

Porque precisamos representar individualmente cada região livre ou ocupada da memória.

Sem isso, não seria possível diferenciar os espaços disponíveis para os algoritmos.

---

## Por que unir blocos livres?

Porque dois blocos livres consecutivos representam, fisicamente, uma única região contínua de memória.

---

## Por que um processo pode ser descartado mesmo existindo memória livre?

Porque o espaço livre pode estar fragmentado.

Pode existir memória suficiente no total, mas nenhum bloco contínuo grande o suficiente.

---

## Qual a diferença entre First Fit e Next Fit?

First Fit começa a busca sempre no início.

Next Fit continua de onde a busca anterior terminou.

---

## Qual a diferença entre Best Fit e Worst Fit?

Best Fit escolhe o menor bloco adequado.

Worst Fit escolhe o maior bloco adequado.

---

## Por que repetir 100 vezes?

Porque a geração e a remoção dos processos são aleatórias.

Repetir o experimento reduz o impacto de uma execução específica.

---

## Por que não utilizar Thread.sleep(1000)?

Porque os segundos são simulados.

Usar tempo real tornaria o experimento extremamente demorado.

---

# 30. Resumo final

O projeto simula:

```text
memória de 1000 unidades
```

com:

```text
processos entre 10 e 50 unidades
```

e compara:

```text
First Fit
Next Fit
Best Fit
Worst Fit
```

Durante 100 segundos:

```text
2 processos entram por segundo
1 ou 2 processos saem por segundo
```

São calculadas:

```text
tamanho médio
ocupação média
taxa de descarte
```

Cada algoritmo é testado 100 vezes para reduzir o efeito da aleatoriedade.

O objetivo final é observar como diferentes estratégias de alocação afetam o uso da memória e a quantidade de processos que conseguem ser alocados.