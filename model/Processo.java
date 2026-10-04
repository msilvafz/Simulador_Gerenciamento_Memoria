package model;

public class Processo {

    // Identificador único do processo
    private final int id;

    // Quantidade de memória que o processo precisa ocupar
    private final int tamanho;

    public Processo(int id, int tamanho) {
        this.id = id;
        this.tamanho = tamanho;
    }

    public int getId() {
        return id;
    }

    public int getTamanho() {
        return tamanho;
    }

    // Facilita a visualização do processo no terminal
    @Override
    public String toString() {
        return String.format("[PID: %d | Tamanho: %d]", id, tamanho);
    }
}