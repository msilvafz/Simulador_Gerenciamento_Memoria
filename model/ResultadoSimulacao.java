package model;

public class ResultadoSimulacao {

    private final double tamanhoMedioProcessos;
    private final double ocupacaoMediaMemoria;
    private final double taxaDescarte;

    public ResultadoSimulacao(
            double tamanhoMedioProcessos,
            double ocupacaoMediaMemoria,
            double taxaDescarte) {

        this.tamanhoMedioProcessos = tamanhoMedioProcessos;
        this.ocupacaoMediaMemoria = ocupacaoMediaMemoria;
        this.taxaDescarte = taxaDescarte;
    }

    public double getTamanhoMedioProcessos() {
        return tamanhoMedioProcessos;
    }

    public double getOcupacaoMediaMemoria() {
        return ocupacaoMediaMemoria;
    }

    public double getTaxaDescarte() {
        return taxaDescarte;
    }

    @Override
    public String toString() {

        return String.format(
                "Tamanho medio dos processos: %.2f%n"
                        + "Ocupacao media da memoria: %.2f%%%n"
                        + "Taxa de descarte: %.2f%%",
                tamanhoMedioProcessos,
                ocupacaoMediaMemoria,
                taxaDescarte);
    }
}