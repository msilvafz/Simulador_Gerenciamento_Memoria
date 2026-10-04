package controller;

import model.ResultadoExperimento;
import model.ResultadoSimulacao;
import model.TipoAlgoritmoAlocacao;

public class ExperimentoAlocacao {

    public ResultadoExperimento executar(
            TipoAlgoritmoAlocacao algoritmo,
            int quantidadeExecucoes,
            int segundosPorExecucao) {

        double somaTamanhoMedio = 0;
        double somaOcupacaoMedia = 0;
        double somaTaxaDescarte = 0;

        for (int i = 0; i < quantidadeExecucoes; i++) {

            // Cada execução precisa começar com uma memória nova
            SimuladorMemoria simulador = new SimuladorMemoria(algoritmo);

            ResultadoSimulacao resultado = simulador.executar(
                    segundosPorExecucao,
                    false);

            somaTamanhoMedio += resultado.getTamanhoMedioProcessos();

            somaOcupacaoMedia += resultado.getOcupacaoMediaMemoria();

            somaTaxaDescarte += resultado.getTaxaDescarte();
        }

        double tamanhoMedioGlobal = somaTamanhoMedio / quantidadeExecucoes;

        double ocupacaoMediaGlobal = somaOcupacaoMedia / quantidadeExecucoes;

        double taxaDescarteGlobal = somaTaxaDescarte / quantidadeExecucoes;

        return new ResultadoExperimento(
                algoritmo,
                quantidadeExecucoes,
                tamanhoMedioGlobal,
                ocupacaoMediaGlobal,
                taxaDescarteGlobal);
    }
}