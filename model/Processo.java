package model;

public class Processo {

    private final int id;
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

    @Override
    public String toString() {
        return String.format("[PID: %d | Tamanho: %d]", id, tamanho);
    }
}