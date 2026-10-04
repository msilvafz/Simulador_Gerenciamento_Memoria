package model;

public class BlocoMemoria {

    // Posição inicial do bloco dentro da memória
    private int inicio;

    // Tamanho ocupado por este bloco
    private int tamanho;

    // Processo que ocupa o bloco; null significa bloco livre
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

    // Bloco está livre quando não possui processo associado
    public boolean isLivre() {
        return processo == null;
    }

    // Exibe o bloco como LIVRE ou mostra o PID que o ocupa
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