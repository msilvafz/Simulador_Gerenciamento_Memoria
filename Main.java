import controller.SimuladorMemoria;
import model.TipoAlgoritmoAlocacao;

public class Main {

    public static void main(String[] args) {

        SimuladorMemoria simulador = new SimuladorMemoria(
                TipoAlgoritmoAlocacao.FIRST_FIT
        );

        simulador.executar(10);
    }
}