package model;

import java.util.LinkedList;

public class Memoria {

    private final int tamanho;
    private final LinkedList<BlocoMemoria> blocos;
    private int indiceNextFit;

    public Memoria() {
        this.tamanho = 1000;
        this.blocos = new LinkedList<>();
        this.indiceNextFit = 0;

        // Inicialmente toda a memória está livre
        blocos.add(new BlocoMemoria(0, tamanho));
    }

    public int getTamanho() {
        return tamanho;
    }

    public LinkedList<BlocoMemoria> getBlocos() {
        return blocos;
    }

    public boolean alocar(Processo processo, TipoAlgoritmoAlocacao algoritmo) {

        switch (algoritmo) {

            case FIRST_FIT:
                return alocarFirstFit(processo);
            case NEXT_FIT:
                return alocarNextFit(processo);
            case BEST_FIT:
                return alocarBestFit(processo);
            case WORST_FIT:
                return alocarWorstFit(processo);
            default:
                return false;
        }
    }

    private boolean alocarFirstFit(Processo processo) {

        for (int i = 0; i < blocos.size(); i++) {

            BlocoMemoria bloco = blocos.get(i);

            if (bloco.isLivre()
                    && bloco.getTamanho() >= processo.getTamanho()) {

                ocuparBloco(i, processo);

                return true;
            }
        }

        return false;
    }

    private boolean alocarBestFit(Processo processo) {

        int melhorIndice = -1;
        int menorTamanho = Integer.MAX_VALUE;

        for (int i = 0; i < blocos.size(); i++) {

            BlocoMemoria bloco = blocos.get(i);

            if (bloco.isLivre()
                    && bloco.getTamanho() >= processo.getTamanho()
                    && bloco.getTamanho() < menorTamanho) {

                melhorIndice = i;
                menorTamanho = bloco.getTamanho();
            }
        }

        if (melhorIndice != -1) {
            ocuparBloco(melhorIndice, processo);
            return true;
        }

        return false;
    }

    private boolean alocarWorstFit(Processo processo) {

        int piorIndice = -1;
        int maiorTamanho = -1;

        for (int i = 0; i < blocos.size(); i++) {

            BlocoMemoria bloco = blocos.get(i);

            if (bloco.isLivre()
                    && bloco.getTamanho() >= processo.getTamanho()
                    && bloco.getTamanho() > maiorTamanho) {

                piorIndice = i;
                maiorTamanho = bloco.getTamanho();
            }
        }

        if (piorIndice != -1) {
            ocuparBloco(piorIndice, processo);
            return true;
        }

        return false;
    }

    private boolean alocarNextFit(Processo processo) {

        if (blocos.isEmpty()) {
            return false;
        }

        // Garante que o índice continue válido caso a lista tenha sido alterada
        indiceNextFit = indiceNextFit % blocos.size();

        int indiceInicial = indiceNextFit;

        do {

            BlocoMemoria bloco = blocos.get(indiceNextFit);

            if (bloco.isLivre()
                    && bloco.getTamanho() >= processo.getTamanho()) {

                int indiceAlocado = indiceNextFit;

                ocuparBloco(indiceAlocado, processo);

                // A próxima busca continuará depois do bloco utilizado
                indiceNextFit = (indiceAlocado + 1) % blocos.size();

                return true;
            }

            indiceNextFit = (indiceNextFit + 1) % blocos.size();

        } while (indiceNextFit != indiceInicial);

        return false;
    }

    private void ocuparBloco(int indice, Processo processo) {

        BlocoMemoria bloco = blocos.get(indice);

        int tamanhoOriginal = bloco.getTamanho();
        int tamanhoProcesso = processo.getTamanho();

        // O bloco passa a representar o processo
        bloco.setTamanho(tamanhoProcesso);
        bloco.setProcesso(processo);

        int tamanhoRestante = tamanhoOriginal - tamanhoProcesso;

        // Se sobrou espaço, cria um novo bloco livre logo após o processo
        if (tamanhoRestante > 0) {

            int inicioBlocoLivre = bloco.getInicio() + tamanhoProcesso;

            BlocoMemoria blocoLivre = new BlocoMemoria(
                    inicioBlocoLivre,
                    tamanhoRestante);

            blocos.add(indice + 1, blocoLivre);
        }
    }

    public boolean removerProcesso(int idProcesso) {

        for (BlocoMemoria bloco : blocos) {

            if (!bloco.isLivre()
                    && bloco.getProcesso().getId() == idProcesso) {

                // Ao remover o processo, o bloco volta a ficar livre
                bloco.setProcesso(null);

                // Junta possíveis blocos livres vizinhos
                unirBlocosLivres();

                return true;
            }
        }

        return false;
    }

    private void unirBlocosLivres() {

        for (int i = 0; i < blocos.size() - 1; i++) {

            BlocoMemoria atual = blocos.get(i);
            BlocoMemoria proximo = blocos.get(i + 1);

            if (atual.isLivre() && proximo.isLivre()) {

                atual.setTamanho(
                        atual.getTamanho() + proximo.getTamanho());

                blocos.remove(i + 1);

                // Volta uma posição para verificar se ainda existem
                // outros blocos livres consecutivos
                i--;
            }
        }
    }

    public int getTamanhoOcupado() {

        int ocupado = 0;

        for (BlocoMemoria bloco : blocos) {

            if (!bloco.isLivre()) {
                ocupado += bloco.getTamanho();
            }
        }

        return ocupado;
    }

    public double getPercentualOcupacao() {

        return ((double) getTamanhoOcupado() / tamanho) * 100;
    }

    public void imprimirMemoria() {

        System.out.println("\n========================================");
        System.out.println("           ESTADO DA MEMORIA");
        System.out.println("========================================");

        for (BlocoMemoria bloco : blocos) {
            System.out.println(bloco);
        }

        System.out.println("========================================");
    }
}