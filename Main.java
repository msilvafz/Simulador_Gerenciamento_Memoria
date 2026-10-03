import model.Memoria;
import model.Processo;
import model.TipoAlgoritmoAlocacao;

public class Main {

    public static void main(String[] args) {

        Memoria memoria = new Memoria();

        Processo p1 = new Processo(1, 20);
        Processo p2 = new Processo(2, 50);
        Processo p3 = new Processo(3, 20);
        Processo p4 = new Processo(4, 30);
        Processo p5 = new Processo(5, 20);

        memoria.alocar(p1, TipoAlgoritmoAlocacao.FIRST_FIT);
        memoria.alocar(p2, TipoAlgoritmoAlocacao.FIRST_FIT);
        memoria.alocar(p3, TipoAlgoritmoAlocacao.FIRST_FIT);
        memoria.alocar(p4, TipoAlgoritmoAlocacao.FIRST_FIT);
        memoria.alocar(p5, TipoAlgoritmoAlocacao.FIRST_FIT);

        System.out.println("\nMEMORIA INICIAL:");
        memoria.imprimirMemoria();

        // Cria dois espaços livres diferentes
        memoria.removerProcesso(2); // espaço de 50
        memoria.removerProcesso(4); // espaço de 30

        System.out.println("\nMEMORIA APOS REMOCOES:");
        memoria.imprimirMemoria();
        
        Processo p6 = new Processo(6, 25);

        System.out.println("\nALOCANDO PID 6 COM WORST FIT:");

        memoria.alocar(
                p6,
                TipoAlgoritmoAlocacao.WORST_FIT);

        memoria.imprimirMemoria();
    }
}