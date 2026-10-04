package model;

import java.util.LinkedList;

public class Memoria {

    private final int tamanho;
    private final LinkedList<BlocoMemoria> blocos;

    // Guarda de onde o Next Fit deve continuar a busca
    private int indiceNextFit;

    public Memoria() {
        this.tamanho = 1000;
        this.blocos = new LinkedList<>();
        this.indiceNextFit = 0;

        // A memória começa como um único bloco livre de 1000
        blocos.add(new BlocoMemoria(0, tamanho));
    }

    public int getTamanho() {
        return tamanho;
    }

    public LinkedList<BlocoMemoria> getBlocos() {
        return blocos;
    }

    // Direciona a alocação para o algoritmo escolhido
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

    // First Fit: escolhe o primeiro bloco livre onde o processo cabe
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

    // Best Fit: escolhe o menor bloco livre onde o processo cabe
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

    // Worst Fit: escolhe o maior bloco livre onde o processo cabe
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

    // Next Fit: continua a busca a partir da última posição utilizada
    private boolean alocarNextFit(Processo processo) {

        if (blocos.isEmpty()) {
            return false;
        }

        // Mantém o índice válido mesmo se a lista mudar de tamanho
        indiceNextFit = indiceNextFit % blocos.size();

        int indiceInicial = indiceNextFit;

        do {

            BlocoMemoria bloco = blocos.get(indiceNextFit);

            if (bloco.isLivre()
                    && bloco.getTamanho() >= processo.getTamanho()) {

                int indiceAlocado = indiceNextFit;

                ocuparBloco(indiceAlocado, processo);

                // Próxima busca começa depois do bloco utilizado
                indiceNextFit = (indiceAlocado + 1) % blocos.size();

                return true;
            }

            // Faz a busca circular: ao chegar ao fim, volta ao início
            indiceNextFit = (indiceNextFit + 1) % blocos.size();

        } while (indiceNextFit != indiceInicial);

        return false;
    }

    // Ocupa o bloco escolhido e cria outro bloco com o espaço restante
    private void ocuparBloco(int indice, Processo processo) {

        BlocoMemoria bloco = blocos.get(indice);

        int tamanhoOriginal = bloco.getTamanho();
        int tamanhoProcesso = processo.getTamanho();

        bloco.setTamanho(tamanhoProcesso);
        bloco.setProcesso(processo);

        int tamanhoRestante = tamanhoOriginal - tamanhoProcesso;

        if (tamanhoRestante > 0) {

            int inicioBlocoLivre = bloco.getInicio() + tamanhoProcesso;

            BlocoMemoria blocoLivre = new BlocoMemoria(
                    inicioBlocoLivre,
                    tamanhoRestante);

            blocos.add(indice + 1, blocoLivre);
        }
    }

    // Remove o processo e transforma seu bloco novamente em espaço livre
    public boolean removerProcesso(int idProcesso) {

        for (BlocoMemoria bloco : blocos) {

            if (!bloco.isLivre()
                    && bloco.getProcesso().getId() == idProcesso) {

                bloco.setProcesso(null);

                // Junta espaços livres que ficaram lado a lado
                unirBlocosLivres();

                return true;
            }
        }

        return false;
    }

    // Une blocos livres consecutivos em um único bloco maior
    private void unirBlocosLivres() {

        for (int i = 0; i < blocos.size() - 1; i++) {

            BlocoMemoria atual = blocos.get(i);
            BlocoMemoria proximo = blocos.get(i + 1);

            if (atual.isLivre() && proximo.isLivre()) {

                atual.setTamanho(
                        atual.getTamanho() + proximo.getTamanho());

                blocos.remove(i + 1);

                // Reavalia a posição caso existam mais blocos livres seguidos
                i--;
            }
        }
    }

    // Soma apenas os blocos ocupados
    public int getTamanhoOcupado() {

        int ocupado = 0;

        for (BlocoMemoria bloco : blocos) {

            if (!bloco.isLivre()) {
                ocupado += bloco.getTamanho();
            }
        }

        return ocupado;
    }

    // Calcula o percentual de memória ocupada
    public double getPercentualOcupacao() {

        return ((double) getTamanhoOcupado() / tamanho) * 100;
    }

    // Exibe os blocos atuais da memória
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