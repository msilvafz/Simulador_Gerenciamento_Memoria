import controller.SimuladorMemoria;
import model.ResultadoSimulacao;
import model.TipoAlgoritmoAlocacao;

public class Main {

    public static void main(String[] args) {

        SimuladorMemoria simulador = new SimuladorMemoria(
                TipoAlgoritmoAlocacao.FIRST_FIT);

        ResultadoSimulacao resultado = simulador.executar(10);

        System.out.println("\n========================================");
        System.out.println("       RESULTADO DA SIMULACAO");
        System.out.println("========================================");

        System.out.println(resultado);

        System.out.println("========================================");
    }
}