import controller.ExperimentoAlocacao;
import model.ResultadoExperimento;
import model.TipoAlgoritmoAlocacao;

public class Main {

    public static void main(String[] args) {

        ExperimentoAlocacao experimento = new ExperimentoAlocacao();

        int quantidadeExecucoes = 100;
        int segundosPorExecucao = 100;

        System.out.println("============================================================");
        System.out.println("           EXPERIMENTO DE ALOCACAO DE MEMORIA");
        System.out.println("============================================================");
        System.out.println("Execucoes por algoritmo: " + quantidadeExecucoes);
        System.out.println("Segundos por execucao: " + segundosPorExecucao);
        System.out.println("============================================================");

        for (TipoAlgoritmoAlocacao algoritmo : TipoAlgoritmoAlocacao.values()) {

            ResultadoExperimento resultado = experimento.executar(
                    algoritmo,
                    quantidadeExecucoes,
                    segundosPorExecucao);

            System.out.println();
            System.out.println("--------------------------------------------");
            System.out.println(resultado);
            System.out.println("--------------------------------------------");
        }
    }
}