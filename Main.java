import controller.ExperimentoAlocacao;
import controller.SimuladorMemoria;
import model.ResultadoExperimento;
import model.ResultadoSimulacao;
import model.TipoAlgoritmoAlocacao;

public class Main {

    public static void main(String[] args) {

        // true = demonstra um algoritmo passo a passo
        // false = compara os quatro algoritmos
        boolean modoDemonstracao = false;

        if (modoDemonstracao) {

            // MODO DEMONSTRACAO
            SimuladorMemoria simulador = new SimuladorMemoria(
                    TipoAlgoritmoAlocacao.FIRST_FIT);

            ResultadoSimulacao resultado = simulador.executar(10, true);

            System.out.println("\n========================================");
            System.out.println("       RESULTADO DA DEMONSTRACAO");
            System.out.println("========================================");
            System.out.println(resultado);

        } else {

            // MODO EXPERIMENTO
            ExperimentoAlocacao experimento = new ExperimentoAlocacao();

            int quantidadeExecucoes = 100;
            int segundosPorExecucao = 100;

            System.out.println("\n============================================================");
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
}