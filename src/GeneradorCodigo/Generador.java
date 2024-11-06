package GeneradorCodigo;

import AnalizadorLexico.Lexico;

import java.util.ArrayList;
import java.util.Stack;

public class Generador {
    private ArrayList<Terceto> tercetos;
    private static volatile Generador instance;
    private Stack<String> pila;


    public String addTerceto(String operador, String operando1, String operando2){
        Terceto terceto = new Terceto(operador, operando1, operando2);
        this.tercetos.add(terceto);
        String pos = String.valueOf(this.tercetos.size()-1);
        return "T" + pos;
    }

    private Generador(){
        this.tercetos = new ArrayList<Terceto>();
    }

    public static Generador getInstance(){
        Generador result = instance;
        if (result == null) {
            result = instance;
            if (result == null) {
                instance = result = new Generador();
            }
        }
        return result;
    }

    public void imprimirTercetos(){
        System.out.println("");
        System.out.println("----------------------");
        System.out.println("Tercetos");
        System.out.println("");
        for (Terceto terceto: tercetos) {
            System.out.println(terceto.toString());
        }
    }

    public void agregarPila(String p){
        pila.push(p);
    }

    public void eliminarPila(){
        pila.pop();
    }

    public String obtenerElementoPila(){
        return pila.peek();
    }
}
