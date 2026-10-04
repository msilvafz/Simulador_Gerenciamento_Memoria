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

    public ResultadoSimulacao executar(int segundos) {

        System.out.println("========================================");
        System.out.println("       INICIANDO SIMULACAO");
        System.out.println("       Algoritmo: " + algoritmo);
        System.out.println("========================================");

        for (int segundo = 1; segundo <= segundos; segundo++) {

            System.out.println("\n---------- SEGUNDO " + segundo + " ----------");

            gerarEAlocarProcessos();

            removerProcessosAleatorios();

            somaPercentualOcupacao += memoria.getPercentualOcupacao();

            memoria.imprimirMemoria();
        }

        System.out.println("\n========================================");
        System.out.println("        SIMULACAO FINALIZADA");
        System.out.println("========================================");

        double tamanhoMedioProcessos = (double) somaTamanhoProcessos / totalProcessosGerados;

        double ocupacaoMediaMemoria = somaPercentualOcupacao / segundos;

        double taxaDescarte = ((double) totalProcessosDescartados
                / totalProcessosGerados) * 100;

        return new ResultadoSimulacao(
                tamanhoMedioProcessos,
                ocupacaoMediaMemoria,
                taxaDescarte);
    }

    private void gerarEAlocarProcessos() {

        for (int i = 0; i < 2; i++) {

            Processo processo = gerador.gerarProcesso();

            // Registra informações para as métricas
            totalProcessosGerados++;
            somaTamanhoProcessos += processo.getTamanho();

            System.out.println("Gerado: " + processo);

            boolean alocado = memoria.alocar(processo, algoritmo);

            if (alocado) {

                processosNaMemoria.add(processo);

                System.out.println(
                        "PID " + processo.getId() + " alocado.");

            } else {

                totalProcessosDescartados++;

                System.out.println(
                        "PID " + processo.getId()
                                + " descartado por falta de espaco.");
            }
        }
    }

    private void removerProcessosAleatorios() {

        if (processosNaMemoria.isEmpty()) {
            return;
        }

        // Sorteia se serão removidos 1 ou 2 processos
        int quantidadeRemover = random.nextInt(2) + 1;

        // Evita tentar remover mais processos do que existem
        quantidadeRemover = Math.min(
                quantidadeRemover,
                processosNaMemoria.size());

        System.out.println(
                "Processos que sairao da memoria: "
                        + quantidadeRemover);

        for (int i = 0; i < quantidadeRemover; i++) {

            int indiceAleatorio = random.nextInt(processosNaMemoria.size());

            Processo processo = processosNaMemoria.remove(indiceAleatorio);

            memoria.removerProcesso(processo.getId());

            System.out.println(
                    "PID " + processo.getId()
                            + " removido da memoria.");
        }
    }
}