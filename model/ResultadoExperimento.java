package model;

public class ResultadoExperimento {

    private final TipoAlgoritmoAlocacao algoritmo;
    private final int quantidadeExecucoes;
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