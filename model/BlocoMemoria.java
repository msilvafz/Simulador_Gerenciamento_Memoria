package model;

public class BlocoMemoria {

    private int inicio;
    private int tamanho;
    private Processo processo;

    public BlocoMemoria(int inicio, int tamanho) {
        this.inicio = inicio;
        this.tamanho = tamanho;
        this.processo = null;
    }

    public int getInicio() {
        return inicio;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public Processo getProcesso() {
        return processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }

    public boolean isLivre() {
        return processo == null;
    }

    @Override
    public String toString() {
        if (isLivre()) {
            return String.format(
                    "[Inicio: %d | Tamanho: %d | LIVRE]",
                    inicio,
                    tamanho);
        }

        return String.format(
                "[Inicio: %d | Tamanho: %d | PID: %d]",
                inicio,
                tamanho,
                processo.getId());
    }
}