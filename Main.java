import model.Processo;
import service.GeradorDeProcessos;

public class Main {

    public static void main(String[] args) {

        GeradorDeProcessos gerador = new GeradorDeProcessos();

        for (int i = 0; i < 5; i++) {
            Processo processo = gerador.gerarProcesso();
            System.out.println(processo);
        }
    }
}