package model;

public class ResultadoExperimento {

    // Identifica qual algoritmo gerou este resultado
    private final TipoAlgoritmoAlocacao algoritmo;

    // Quantidade de simulações usadas para calcular as médias finais
    private final int quantidadeExecucoes;

    // Médias globais obtidas após todas as execuções
    private final double tamanhoMedioProcessos;
    private final double ocupacaoMediaMemoria;
    private final double taxaMediaDescarte;

    public ResultadoExperimento(
            TipoAlgoritmoAlocacao algoritmo,
            int quantidadeExecucoes,
            double tamanhoMedioProcessos,
            double ocupacaoMediaMemoria,
            double taxaMediaDescarte) {

        this.algoritmo = algoritmo;
        this.quantidadeExecucoes = quantidadeExecucoes;
        this.tamanhoMedioProcessos = tamanhoMedioProcessos;
        this.ocupacaoMediaMemoria = ocupacaoMediaMemoria;
        this.taxaMediaDescarte = taxaMediaDescarte;
    }

    public TipoAlgoritmoAlocacao getAlgoritmo() {
        return algoritmo;
    }

    public int getQuantidadeExecucoes() {
        return quantidadeExecucoes;
    }

    public double getTamanhoMedioProcessos() {
        return tamanhoMedioProcessos;
    }

    public double getOcupacaoMediaMemoria() {
        return ocupacaoMediaMemoria;
    }

    public double getTaxaMediaDescarte() {
        return taxaMediaDescarte;
    }

    // Formata as médias finais do algoritmo para exibição no terminal
    @Override
    public String toString() {

        return String.format(
                "Algoritmo: %s%n"
                        + "Execucoes: %d%n"
                        + "Tamanho medio dos processos: %.2f%n"
                        + "Ocupacao media da memoria: %.2f%%%n"
                        + "Taxa media de descarte: %.2f%%",
                algoritmo,
                quantidadeExecucoes,
                tamanhoMedioProcessos,
                ocupacaoMediaMemoria,
                taxaMediaDescarte);
    }
}