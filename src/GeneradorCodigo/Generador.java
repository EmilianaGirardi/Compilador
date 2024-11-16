package GeneradorCodigo;

import AnalizadorLexico.Lexico;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Generador {
    private ArrayList<Terceto> tercetos;
    private TraductorAssembler traductor;
    private static volatile Generador instance;
    private boolean error;
    
    private Stack<String> pila;

    private ArrayList<String> etiquetas;
    private HashMap<Integer, String> mapGoto; //Key: numero terceto, Value: etiqueta

    private String[][] tablaComp;


    private Generador(){
        this.tercetos = new ArrayList<Terceto>();
        this.pila = new Stack<>();
        this.etiquetas = new ArrayList<>();
        this.mapGoto = new HashMap<Integer, String>();
        this.tablaComp = this.crearTabla();
        this.error = false;
    }

    private String[][] crearTabla(){
        String[][] result = new String[3][3];
        result[0][0] = "itoI";
        result[0][1] = "itoS";
        result[0][2] = null;
        result[1][0] = "stoI";
        result[1][1] = "stoS";
        result[1][2] = null;
        result[2][0] = null;
        result[2][1] = null;
        result[2][2] = "otoO";
        return  result;
    }

    public void setSalida(String salida) throws IOException {
        this.traductor = new TraductorAssembler(salida);
    }

    public String getConversion(Integer tipo1, Integer tipo2){
        return this.tablaComp[tipo1 - 1][tipo2 - 1];
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
    
    public void putEtiqueta(String etq) {
    	this.etiquetas.add(etq);
    	if(this.mapGoto.containsValue(etq)) {
    		mapGoto.entrySet().removeIf(terceto -> terceto.getValue().equals(etq));
    	}
    }
    
    public boolean isEtiqueta(String etq) {
    	return this.etiquetas.contains(etq);
    }
    
    /*public String posicionEtiqueta(String etq) {
    	return "[T"+this.etiquetas.get(etq)+"]";
    }*/
    
    public void addGoto(Integer pos, String etq) {
    	this.mapGoto.put(pos, etq);
    }
    
    /*Error*/
    public boolean getError() {
    	return this.error;
    }
    
    public void setError() {
    	this.error=true;
    }
    
    /*Traduccion*/
    public void generarCodigoMaquina() throws IOException {
    	if((!this.error) || (this.mapGoto.isEmpty())) {
            this.traductor.inicializarAssembler();
    		for (Terceto t : this.tercetos) {
        		this.traductor.traducir(t);
        	}
            traductor.cerrarTraduccion();
    	}
    	
    }
    
}
