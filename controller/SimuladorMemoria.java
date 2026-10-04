package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Memoria;
import model.Processo;
import model.ResultadoSimulacao;
import model.TipoAlgoritmoAlocacao;
import service.GeradorDeProcessos;

public class SimuladorMemoria {

    // Entidades usadas durante uma única simulação
    private final Memoria memoria;
    private final GeradorDeProcessos gerador;
    private final TipoAlgoritmoAlocacao algoritmo;

    // Mantém apenas os processos que realmente estão alocados
    private final List<Processo> processosNaMemoria;

    private final Random random;

    // Variáveis usadas no cálculo das métricas
    private int totalProcessosGerados;
    private int totalProcessosDescartados;
    private int somaTamanhoProcessos;
    private double somaPercentualOcupacao;

    public SimuladorMemoria(TipoAlgoritmoAlocacao algoritmo) {
        this.memoria = new Memoria();
        this.gerador = new GeradorDeProcessos();
        this.algoritmo = algoritmo;
        this.processosNaMemoria = new ArrayList<>();
        this.random = new Random();

        this.totalProcessosGerados = 0;
        this.totalProcessosDescartados = 0;
        this.somaTamanhoProcessos = 0;
        this.somaPercentualOcupacao = 0;
    }

    // Executa a simulação pelo número de segundos informado
    public ResultadoSimulacao executar(int segundos, boolean exibirDetalhes) {

        if (exibirDetalhes) {
            System.out.println("========================================");
            System.out.println("       INICIANDO SIMULACAO");
            System.out.println("       Algoritmo: " + algoritmo);
            System.out.println("========================================");
        }

        // Cada repetição representa 1 segundo simulado
        for (int segundo = 1; segundo <= segundos; segundo++) {

            if (exibirDetalhes) {
                System.out.println(
                        "\n---------- SEGUNDO " + segundo + " ----------");
            }

            // A cada segundo entram 2 processos
            gerarEAlocarProcessos(exibirDetalhes);

            // Depois saem aleatoriamente 1 ou 2 processos
            removerProcessosAleatorios(exibirDetalhes);

            // Registra a ocupação ao final daquele segundo
            somaPercentualOcupacao += memoria.getPercentualOcupacao();

            if (exibirDetalhes) {
                memoria.imprimirMemoria();
            }
        }

        // Média dos tamanhos de todos os processos gerados
        double tamanhoMedioProcessos = (double) somaTamanhoProcessos
                / totalProcessosGerados;

        // Média da ocupação registrada durante todos os segundos
        double ocupacaoMediaMemoria = somaPercentualOcupacao / segundos;

        // Percentual de processos que não conseguiram ser alocados
        double taxaDescarte = ((double) totalProcessosDescartados
                / totalProcessosGerados) * 100;

        if (exibirDetalhes) {
            System.out.println("\n========================================");
            System.out.println("        SIMULACAO FINALIZADA");
            System.out.println("========================================");
        }

        return new ResultadoSimulacao(
                tamanhoMedioProcessos,
                ocupacaoMediaMemoria,
                taxaDescarte);
    }

    // Gera exatamente 2 processos e tenta alocá-los
    private void gerarEAlocarProcessos(boolean exibirDetalhes) {

        for (int i = 0; i < 2; i++) {

            Processo processo = gerador.gerarProcesso();

            // Todo processo gerado entra nas métricas, mesmo se for descartado
            totalProcessosGerados++;
            somaTamanhoProcessos += processo.getTamanho();

            if (exibirDetalhes) {
                System.out.println("Gerado: " + processo);
            }

            boolean alocado = memoria.alocar(processo, algoritmo);

            if (alocado) {

                // Só processos alocados podem ser sorteados para sair depois
                processosNaMemoria.add(processo);

                if (exibirDetalhes) {
                    System.out.println(
                            "PID " + processo.getId()
                                    + " alocado.");
                }

            } else {

                totalProcessosDescartados++;

                if (exibirDetalhes) {
                    System.out.println(
                            "PID " + processo.getId()
                                    + " descartado por falta de espaco.");
                }
            }
        }
    }

    // Sorteia a saída de 1 ou 2 processos atualmente alocados
    private void removerProcessosAleatorios(boolean exibirDetalhes) {

        if (processosNaMemoria.isEmpty()) {
            return;
        }

        // Sorteia 1 ou 2
        int quantidadeRemover = random.nextInt(2) + 1;

        // Evita tentar remover mais processos do que existem
        quantidadeRemover = Math.min(
                quantidadeRemover,
                processosNaMemoria.size());

        if (exibirDetalhes) {
            System.out.println(
                    "Processos que sairao da memoria: "
                            + quantidadeRemover);
        }

        for (int i = 0; i < quantidadeRemover; i++) {

            // Escolhe aleatoriamente um processo da lista
            int indiceAleatorio = random.nextInt(processosNaMemoria.size());

            Processo processo = processosNaMemoria.remove(indiceAleatorio);

            // Libera também o bloco correspondente na memória
            memoria.removerProcesso(processo.getId());

            if (exibirDetalhes) {
                System.out.println(
                        "PID " + processo.getId()
                                + " removido da memoria.");
            }
        }
    }
}