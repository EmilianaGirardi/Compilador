package GeneradorCodigo;

import AnalizadorLexico.Lexico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Generador {
    private ArrayList<Terceto> tercetos;
    private static volatile Generador instance;
    private Stack<String> pila;
    
    private HashMap<String, Integer> etiquetas;
    private HashMap<Integer, String> mapGoto; //Key: numero terceto, Value: etiqueta

    private Generador(){
        this.tercetos = new ArrayList<Terceto>();
        this.pila = new Stack<>();
        this.etiquetas = new HashMap<String, Integer>();
        this.mapGoto = new HashMap<Integer, String>();
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

    /*Tercetos*/
    public String addTerceto(String operador, String operando1, String operando2){
        Terceto terceto = new Terceto(operador, operando1, operando2);
        this.tercetos.add(terceto);
        String pos = String.valueOf(this.tercetos.size()-1);
        return "[T" + pos + "]";
    }

    public int getSizeTercetos(){
        return tercetos.size();
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

    public Terceto getTerceto(int pos) {
        return tercetos.get(pos);
    }

    public int obtenerTamanioTercetos(){
        return this.tercetos.size();
    }
    
    
    /*Pila*/

    public void agregarPila(String p){
        pila.push(p);
    }

    public void eliminarPila(){
        pila.pop();
    }

    public String obtenerElementoPila(){
        return pila.peek();
    }
    
    /*Etiquetas y Goto*/
    
    public void putEtiqueta(String etq, Integer pos) {
    	this.etiquetas.put(etq, pos);
    	if(this.mapGoto.containsValue(etq)) {
    		for (Map.Entry<Integer, String> map :  this.mapGoto.entrySet()){
    	        if (etq.equals(map.getValue())) {
    	            this.getTerceto(map.getKey()).setTercerParametro("[T"+pos+"]");;
    	        }
    	    }
    		
    		mapGoto.entrySet().removeIf(terceto -> terceto.getValue().equals(etq));
    	}
    }
    
    public boolean isEtiqueta(String etq) {
    	return this.etiquetas.containsKey(etq);
    }
    
    public String posicionEtiqueta(String etq) {
    	return "[T"+this.etiquetas.get(etq)+"]";
    }
    
    public void addGoto(Integer pos, String etq) {
    	this.mapGoto.put(pos, etq);
    }
}
