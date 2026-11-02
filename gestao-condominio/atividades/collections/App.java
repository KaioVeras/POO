import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

// Residencial Golden Java - aplicação de Collections (Grupo 02)
// ArrayList: moradores | HashSet: visitantes do dia | HashMap: taxa por unidade
public class App {

    public static void main(String[] args) {
        // ArrayList: moradores na ordem em que foram cadastrados
        ArrayList<String> moradores = new ArrayList<>();
        moradores.add("Carlos - Unidade 101");
        moradores.add("Ana - Unidade 201");

        System.out.println("===== MORADORES (ArrayList) =====");
        for (String morador : moradores) {
            System.out.println(morador);
        }
        System.out.println("Total de moradores: " + moradores.size());

        // HashSet: o mesmo visitante entrando duas vezes conta uma só
        HashSet<String> visitantes = new HashSet<>();
        visitantes.add("Marina");
        visitantes.add("João (entregador)");
        visitantes.add("Marina");

        System.out.println("\n===== VISITANTES DO DIA (HashSet) =====");
        for (String visitante : visitantes) {
            System.out.println(visitante);
        }
        System.out.println("Visitantes distintos: " + visitantes.size());
        boolean marinaVeio = visitantes.contains("Marina");
        System.out.println("Marina esteve no condomínio? " + marinaVeio);

        // HashMap: cada unidade associada à sua taxa condominial
        HashMap<Integer, Double> taxas = new HashMap<>();
        taxas.put(101, 500.00);
        taxas.put(102, 500.00);
        taxas.put(201, 625.00); // cobertura: taxa 25% maior

        System.out.println("\n===== TAXAS POR UNIDADE (HashMap) =====");
        double arrecadacao = 0;
        for (Integer unidade : taxas.keySet()) {
            double taxa = taxas.get(unidade);
            System.out.printf("Unidade %d: R$ %.2f%n", unidade, taxa);
            arrecadacao += taxa;
        }
        System.out.printf("Taxa da unidade 201: R$ %.2f%n", taxas.get(201));
        System.out.printf("Arrecadação prevista: R$ %.2f%n", arrecadacao);
    }
}
