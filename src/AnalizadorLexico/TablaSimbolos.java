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
    	atributos = new ArrayList<>(265);
    	map.put("REPEAT", atributos );
    	
    	atributos = new ArrayList<>(266);
    	map.put("IF",atributos);
    	
    	atributos = new ArrayList<>(267);
    	map.put("THEN",atributos);
    	
    	atributos = new ArrayList<>(268);
    	map.put("ELSE",atributos);
    	
    	atributos = new ArrayList<>(269);
    	map.put("BEGIN",atributos);
    	
    	atributos = new ArrayList<>(270);
    	map.put("END",atributos);
    	
    	atributos = new ArrayList<>(271);
    	map.put("END_IF",atributos);
    	
    	atributos = new ArrayList<>(272);
    	map.put("OUTF",atributos);
    	
    	atributos = new ArrayList<>(273);
    	map.put("TYPEDEF",atributos);
    	
    	atributos = new ArrayList<>(274);
    	map.put("FUN",atributos);
    	
    	atributos = new ArrayList<>(275);
    	map.put("RET",atributos);
    	
    	atributos = new ArrayList<>(276);
    	map.put("GOTO",atributos);
    	
    	atributos = new ArrayList<>(277);
    	map.put("TRIPLE",atributos);
    	
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

