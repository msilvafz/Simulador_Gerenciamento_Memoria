package controller;

import model.ResultadoExperimento;
import model.ResultadoSimulacao;
import model.TipoAlgoritmoAlocacao;

public class ExperimentoAlocacao {

    // Executa várias simulações do mesmo algoritmo e calcula as médias globais
    public ResultadoExperimento executar(
            TipoAlgoritmoAlocacao algoritmo,
            int quantidadeExecucoes,
            int segundosPorExecucao) {

        double somaTamanhoMedio = 0;
        double somaOcupacaoMedia = 0;
        double somaTaxaDescarte = 0;

        for (int i = 0; i < quantidadeExecucoes; i++) {

            // Cada execução começa com uma nova memória e novas métricas
            SimuladorMemoria simulador = new SimuladorMemoria(algoritmo);

            // false evita imprimir os detalhes de cada segundo
            ResultadoSimulacao resultado = simulador.executar(
                    segundosPorExecucao,
                    false);

            // Acumula os resultados de cada simulação
            somaTamanhoMedio += resultado.getTamanhoMedioProcessos();
            somaOcupacaoMedia += resultado.getOcupacaoMediaMemoria();
            somaTaxaDescarte += resultado.getTaxaDescarte();
        }

        // Calcula a média das métricas após todas as execuções
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