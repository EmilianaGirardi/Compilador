package AnalizadorLexico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class TablaSimbolos {
    private Map<String, ArrayList<Integer>> map;
	String ambitos = ".global";

    public TablaSimbolos() {
		this.map =  new HashMap<>();
		this.agregarPalabras();
	}

	public String nameMangling (String id, String ambito){
		return id + this.ambitos + '.' + ambito;
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

	public String getAmbitos() {
		return ambitos;
	}

	public String buscarVar(String lexema){
		String ambito = this.ambitos;
		String[] partes = ambito.split("\\.");
		String variable;
		for (int i = partes.length - 1; i>=0; i--) {
			variable = lexema + '.' + i;
			if (estaToken(variable)){
				return variable;
			}
		}
		if (estaToken(lexema)) //variable esta en la TS pero no fue declarada.
			//TODO agregar un if que identifique que no es una cte
			//o devolver otra cosa cuando es cte (pero no null porque null es error de var fuera de alcance)
			return null;
		return "Terceto";
	}
	public String getUltimoAmbito(String ambito) {
		if (this.ambitos != null && !this.ambitos.isEmpty()) {
			String[] partes = ambito.split("\\.");  // Divide la cadena por el separador '.'
			return partes[partes.length - 1];  // Retorna el último elemento
		}
		return "";  // Retorna una cadena vacía si el ámbito es nulo o vacío
	}

	public void addAmbitos(String ambitos) {
		this.ambitos = this.ambitos + "."  + ambitos;
	}

	public void eliminarAmbito() {

		if (!this.ambitos.equals(".global")) {
			int posicionUltimoPunto = this.ambitos.lastIndexOf(".");

			this.ambitos = (posicionUltimoPunto != -1) ? this.ambitos.substring(0, posicionUltimoPunto) : this.ambitos;

		}
	}
	
	public void editarLexema(String oldKey, String newKey) {
		if(!oldKey.equals(newKey)){
			map.put(newKey, this.getValor(oldKey));
			map.remove(oldKey);
		}
	}

	public void editarTipo(String lexema, Integer tipo){
		map.get(lexema).set(1, tipo);
	}

	public void agregarUso(String lexema, Integer uso){
		map.get(lexema).add(2, uso);
	}
	public Integer getUso(String lexema){
		if (map.containsKey(lexema))
			return map.get(lexema).get(2);
		else return null;
	}

	public void agregarTipoParam(String lexema, Integer tipo){
		map.get(lexema).add(3, tipo);
	}

	public Integer getTipoParam(String lexema){
		if (map.containsKey(lexema))
			return map.get(lexema).get(3);
		else return null;
	}

	public Integer getTipo(String lexema){
		if (map.containsKey(lexema))
			return map.get(lexema).get(1);
		else return null;
	}

	public void imprimirTabla(){
		System.out.println("----------------------");
		System.out.println("Tabla de Simbolos");
		System.out.println("");
		for (String i: map.keySet() ){
			System.out.println("Lexema: " + i);
			System.out.println("Token: " + map.get(i).get(0));
			System.out.println("Tipo: " + map.get(i).get(1));
			if (map.get(i).size()>2) {
				System.out.println("Uso: " + map.get(i).get(2));
			}
			System.out.println("----------------------");
		}
	}
	
}

