package AnalizadorLexico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class TablaSimbolos {
    private Map<String, ArrayList<Integer>> map; 
    
    public TablaSimbolos() {
		this.map =  new HashMap<>();
		this.agregarPalabras();
	}
    
    
    private void agregarPalabras() {

		// TODO Auto-generated method stub
    	ArrayList<Integer> atributos;
    	atributos = new ArrayList<>();
		atributos.add(266);
		atributos.add(0);
    	map.put("REPEAT", atributos );
    	
    	atributos = new ArrayList<>();
		atributos.add(267);
		atributos.add(0);
    	map.put("IF",atributos);
    	
    	atributos = new ArrayList<>();
		atributos.add(268);
		atributos.add(0);
    	map.put("THEN",atributos);
    	
    	atributos = new ArrayList<>();
		atributos.add(269);
		atributos.add(0);
    	map.put("ELSE",atributos);
    	
    	atributos = new ArrayList<>();
		atributos.add(270);
		atributos.add(0);
    	map.put("BEGIN",atributos);
    	
    	atributos = new ArrayList<>();
		atributos.add(271);
		atributos.add(0);
    	map.put("END",atributos);
    	
    	atributos = new ArrayList<>();
		atributos.add(272);
		atributos.add(0);
    	map.put("END_IF",atributos);
    	
    	atributos = new ArrayList<>();
		atributos.add(273);
		atributos.add(0);
    	map.put("OUTF",atributos);
    	
    	atributos = new ArrayList<>();
		atributos.add(274);
		atributos.add(0);
    	map.put("TYPEDEF",atributos);
    	
    	atributos = new ArrayList<>();
		atributos.add(275);
		atributos.add(0);
    	map.put("FUN",atributos);
    	
    	atributos = new ArrayList<>();
		atributos.add(276);
		atributos.add(0);
    	map.put("RET",atributos);
    	
    	atributos = new ArrayList<>();
		atributos.add(277);
		atributos.add(0);
    	map.put("GOTO",atributos);
    	
    	atributos = new ArrayList<>();
		atributos.add(278);
		atributos.add(0);
    	map.put("TRIPLE",atributos);

		atributos = new ArrayList<>();
		atributos.add(279);
		atributos.add(0);
		map.put("TIPO_UNSIGNED",atributos);

		atributos = new ArrayList<>();
		atributos.add(280);
		atributos.add(0);
		map.put("TIPO_SINGLE",atributos);

		atributos = new ArrayList<>();
		atributos.add(281);
		atributos.add(0);
		map.put("TIPO_OCTAL",atributos);

		atributos = new ArrayList<>();
		atributos.add(282);
		atributos.add(0);
		map.put("UNTIL",atributos);
    	
	}

	public void agregarToken(String lexema, ArrayList<Integer> atributos){
		
        map.put(lexema, atributos);
    }

	public boolean estaToken(String lexema){
        return map.containsKey(lexema);
    }
	
	public ArrayList<Integer> getValor(String lexema) {
		return map.get(lexema);
	}
 
	
	public void editarLexema(String oldKey, String newKey) {
		map.put(newKey, this.getValor(oldKey));
		map.remove(oldKey);
	}

	public void imprimirTabla(){
		System.out.println("----------------------");
		System.out.println("Tabla de Simbolos");
		System.out.println("");
		for (String i: map.keySet() ){
			System.out.println("Lexema: " + i);
			System.out.println("Token: " + map.get(i).get(0));
			System.out.println("Tipo: " + map.get(i).get(1));
			
			System.out.println("----------------------");
		}
	}
	
}

