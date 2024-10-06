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
    	map.put("REPEAT", atributos );
    	
    	atributos = new ArrayList<>();
		atributos.add(267);
    	map.put("IF",atributos);
    	
    	atributos = new ArrayList<>();
		atributos.add(268);
    	map.put("THEN",atributos);
    	
    	atributos = new ArrayList<>();
		atributos.add(269);
    	map.put("ELSE",atributos);
    	
    	atributos = new ArrayList<>();
		atributos.add(270);
    	map.put("BEGIN",atributos);
    	
    	atributos = new ArrayList<>();
		atributos.add(271);
    	map.put("END",atributos);
    	
    	atributos = new ArrayList<>();
		atributos.add(272);
    	map.put("END_IF",atributos);
    	
    	atributos = new ArrayList<>();
		atributos.add(273);
    	map.put("OUTF",atributos);
    	
    	atributos = new ArrayList<>();
		atributos.add(274);
    	map.put("TYPEDEF",atributos);
    	
    	atributos = new ArrayList<>();
		atributos.add(275);
    	map.put("FUN",atributos);
    	
    	atributos = new ArrayList<>();
		atributos.add(276);
    	map.put("RET",atributos);
    	
    	atributos = new ArrayList<>();
		atributos.add(277);
    	map.put("GOTO",atributos);
    	
    	atributos = new ArrayList<>();
		atributos.add(278);
    	map.put("TRIPLE",atributos);

		atributos = new ArrayList<>();
		atributos.add(279);
		map.put("TIPO_UNSIGNED",atributos);

		atributos = new ArrayList<>();
		atributos.add(280);
		map.put("TIPO_SINGLE",atributos);

		atributos = new ArrayList<>();
		atributos.add(281);
		map.put("TIPO_OCTAL",atributos);

		atributos = new ArrayList<>();
		atributos.add(282);
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
	
	
}

