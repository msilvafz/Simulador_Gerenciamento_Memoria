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

    private final Memoria memoria;
    private final GeradorDeProcessos gerador;
    private final TipoAlgoritmoAlocacao algoritmo;
    private final List<Processo> processosNaMemoria;
    private final Random random;

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

    public ResultadoSimulacao executar(int segundos, boolean exibirDetalhes) {

        if (exibirDetalhes) {
            System.out.println("========================================");
            System.out.println("       INICIANDO SIMULACAO");
            System.out.println("       Algoritmo: " + algoritmo);
            System.out.println("========================================");
        }

        for (int segundo = 1; segundo <= segundos; segundo++) {

            if (exibirDetalhes) {
                System.out.println(
                        "\n---------- SEGUNDO " + segundo + " ----------");
            }

            gerarEAlocarProcessos(exibirDetalhes);

            removerProcessosAleatorios(exibirDetalhes);

            somaPercentualOcupacao += memoria.getPercentualOcupacao();

            if (exibirDetalhes) {
                memoria.imprimirMemoria();
            }
        }

        double tamanhoMedioProcessos = (double) somaTamanhoProcessos
                / totalProcessosGerados;

        double ocupacaoMediaMemoria = somaPercentualOcupacao / segundos;

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

    private void gerarEAlocarProcessos(boolean exibirDetalhes) {

        for (int i = 0; i < 2; i++) {

            Processo processo = gerador.gerarProcesso();

            totalProcessosGerados++;
            somaTamanhoProcessos += processo.getTamanho();

            if (exibirDetalhes) {
                System.out.println("Gerado: " + processo);
            }

            boolean alocado = memoria.alocar(processo, algoritmo);

            if (alocado) {

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

    private void removerProcessosAleatorios(boolean exibirDetalhes) {

        if (processosNaMemoria.isEmpty()) {
            return;
        }

        int quantidadeRemover = random.nextInt(2) + 1;

        quantidadeRemover = Math.min(
                quantidadeRemover,
                processosNaMemoria.size());

        if (exibirDetalhes) {
            System.out.println(
                    "Processos que sairao da memoria: "
                            + quantidadeRemover);
        }

        for (int i = 0; i < quantidadeRemover; i++) {

            int indiceAleatorio = random.nextInt(processosNaMemoria.size());

            Processo processo = processosNaMemoria.remove(indiceAleatorio);

            memoria.removerProcesso(processo.getId());

            if (exibirDetalhes) {
                System.out.println(
                        "PID " + processo.getId()
                                + " removido da memoria.");
            }
        }
    }
}