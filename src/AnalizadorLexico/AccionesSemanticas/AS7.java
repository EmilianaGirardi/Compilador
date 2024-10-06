package AnalizadorLexico.AccionesSemanticas;

import java.util.ArrayList;
import java.util.Optional;

import AnalizadorLexico.Lexico;
import AnalizadorLexico.TablaSimbolos;

public class AS7 extends AccionSemantica{
    @Override
    public Optional<Integer> ejecutar(Character caracterActual, Lexico lexico) {
    	String token = lexico.getToken();
    	
    	ArrayList<Integer> atributos = new ArrayList<Integer>();
    	atributos.add(MULTILINEA);
        if (caracterActual.equals(']')) {
            TablaSimbolos TS = lexico.getTablaSimbolos();
            TS.agregarToken(token, atributos);
        }
        return Optional.of(MULTILINEA);
    }
}