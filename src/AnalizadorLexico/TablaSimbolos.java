package AnalizadorLexico;

import java.util.HashMap;
import java.util.Map;

public class TablaSimbolos {
    private Map<String, Integer> map; 
    
    public TablaSimbolos() {
		this.map =  new HashMap<>();
		this.agregarPalabras();
	}
    
    
    private void agregarPalabras() {
		// TODO Auto-generated method stub
    	
    	map.put("REPEAT",265);
    	map.put("IF",266);
    	map.put("THEN",267);
    	map.put("ELSE",268);
    	map.put("BEGIN",269);
    	map.put("END",270);
    	map.put("END_IF",271);
    	map.put("OUTF",272);
    	map.put("TYPEDEF",273);
    	map.put("FUN",274);
    	map.put("RET",275);
    	map.put("GOTO",276);
    	map.put("TRIPLE",277);
    	
	}

	public void agregarToken(String lexema, Integer atributo){
        map.put(lexema, atributo);
    }

	public boolean estaToken(String lexema){
        return map.containsKey(lexema);
    }
	
	public Integer getValor(String lexema) {
		return map.get(lexema);
	}

}

