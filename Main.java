import controller.ExperimentoAlocacao;
import model.ResultadoExperimento;
import model.TipoAlgoritmoAlocacao;

public class Main {

    public static void main(String[] args) {

        // Responsável por executar as simulações de cada algoritmo
        ExperimentoAlocacao experimento = new ExperimentoAlocacao();

        // Cada algoritmo será testado 100 vezes, com 100 segundos por execução
        int quantidadeExecucoes = 100;
        int segundosPorExecucao = 100;

        System.out.println("============================================================");
        System.out.println("           EXPERIMENTO DE ALOCACAO DE MEMORIA");
        System.out.println("============================================================");
        System.out.println("Execucoes por algoritmo: " + quantidadeExecucoes);
        System.out.println("Segundos por execucao: " + segundosPorExecucao);
        System.out.println("============================================================");

        // Percorre automaticamente os quatro algoritmos do enum
        for (TipoAlgoritmoAlocacao algoritmo : TipoAlgoritmoAlocacao.values()) {

            ResultadoExperimento resultado = experimento.executar(
                    algoritmo,
                    quantidadeExecucoes,
                    segundosPorExecucao);

            // Exibe as médias finais obtidas para cada algoritmo
            System.out.println();
            System.out.println("--------------------------------------------");
            System.out.println(resultado);
            System.out.println("--------------------------------------------");
        }
    }
}