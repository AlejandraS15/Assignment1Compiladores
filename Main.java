import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        ArrayList<String> input = new ArrayList<>();
        while (true) {
            String line = kb.nextLine();
            if (line.isEmpty()) {
                break;
            }
            input.add(line);
        }
        String casos = input.get(0);
        int numCasos = Integer.parseInt(casos);
        int lineEstados = 1;
        int lineAlfabeto = 2;
        int lineFinales = 3;
        int inicio = 4;

        for (int i=0; i<numCasos; i++){
            String[] Alfabeto = input.get(lineAlfabeto).split(" ");
            int tamAlfabeto = Alfabeto.length;
            int numEstados = Integer.parseInt(input.get(lineEstados));
            String[][] matriz = new String[numEstados][];

            for (int j = 0; j < numEstados; j++) {
                matriz[j] = input.get(inicio + j).split(" ");
            }
            ArrayList<Integer> finales = new ArrayList<>();
            String[] estadosF = input.get(lineFinales).split(" ");
            for (String estado : estadosF) {
                int numEstadoF = Integer.parseInt(estado);
                finales.add(numEstadoF);
            }
            ArrayList<Integer> marcados = new ArrayList<>();
            ArrayList<Integer> noMarcados = new ArrayList<>();
            Map<String, ArrayList<Integer>> resultado = new HashMap<>();
            for(int n = 0; n< numEstados; n++){
                resultado = comparacion(n, numEstados, matriz, finales, marcados, noMarcados);
            }
            marcados = resultado.get("marcados");
            noMarcados = resultado.get("noMarcados");
            ultimaComparacion(marcados,noMarcados,matriz,tamAlfabeto);

            lineEstados = lineEstados+numEstados+3;
            lineAlfabeto = lineAlfabeto+numEstados+3;
            lineFinales = lineFinales+numEstados+3;
            inicio = inicio+numEstados+3;
        }
    }

    //Funcion que verifica si un estado es final o no.
    public static boolean esFinal(int estado, ArrayList<Integer> finales){
        boolean estadoFinal = false;
        for (int es : finales) {
            if (estado == es) {
                estadoFinal = true;
                break;
            }
        }
        return estadoFinal;
    }

    //Funcion que clasifica en 2 Arrays los estados marcados y los estados no marcados segun su estado (final o no).
    public static Map<String, ArrayList<Integer>> comparacion(int estadoA, int estadoB, String[][] matriz, ArrayList<Integer> finales, ArrayList<Integer> marcados, ArrayList<Integer> noMarcados){
        int estadoC = estadoA+1;
        if(esFinal(estadoA, finales)==true){
            for(int n = estadoA; n< estadoB-1; n++){
                if(esFinal(estadoC, finales)==false){
                    marcados.add(estadoA);
                    marcados.add(estadoC);
                }else{
                    noMarcados.add(estadoA);
                    noMarcados.add(estadoC);
                }
                estadoC = estadoC+1;
            }

        }else{
            for(int n = estadoA; n< estadoB-1; n++){
                if(esFinal(estadoC, finales)==true){
                    marcados.add(estadoA);
                    marcados.add(estadoC);
                }else{
                    noMarcados.add(estadoA);
                    noMarcados.add(estadoC);
                }
                estadoC = estadoC+1;
            }
        }
        Map<String, ArrayList<Integer>> resultado = new HashMap<>();
        resultado.put("marcados", marcados);
        resultado.put("noMarcados", noMarcados);
        return resultado;
    }

    //Funcion que verifica los estados que no estan marcados por medio de las transiciones del alfabeto y segun coincidencia de las transaciones con los estados marcados, los estados no marcados pueden ser o no marcados, y asi mostrando solo los estados equivalentes.
    public static void ultimaComparacion(ArrayList<Integer> marcados, ArrayList<Integer> noMarcados, String[][] matriz, int tamAlfabeto){
        ArrayList<Integer> encontrados = new ArrayList<>();
        for(int s=0; s<tamAlfabeto;s++) {
            for (int k = 0; k < (noMarcados.size() / 2); k++) {
                int ele1 = Integer.parseInt(matriz[noMarcados.get(k * 2)][s+1]);
                int ele2 = Integer.parseInt(matriz[noMarcados.get(k * 2 + 1)][s+1]);

                for (int j = 0; j < marcados.size(); j += 2) {
                    if ((marcados.get(j) == ele1 && marcados.get(j + 1) == ele2)||(marcados.get(j+1) == ele1 && marcados.get(j) == ele2)) {
                        if (!encontrados.contains(k*2) && !encontrados.contains(k*2+1)) {
                            encontrados.add(k*2);
                            encontrados.add(k*2+1);
                        }
                        break;
                    }
                }
            }
        }
        Collections.sort(encontrados, Collections.reverseOrder());
        for (int i = 0; i < encontrados.size(); i++) {
            noMarcados.remove((int) encontrados.get(i));
        }
        for(int p = 0; p<noMarcados.size()/2;p++){
            System.out.print("("+noMarcados.get(p*2)+","+noMarcados.get(p*2+1)+")");
        }
        System.out.println("");
    }
}