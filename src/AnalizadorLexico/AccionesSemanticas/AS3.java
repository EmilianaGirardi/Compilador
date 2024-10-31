package AnalizadorLexico.AccionesSemanticas;

import java.util.ArrayList;
import java.util.Optional;

import AnalizadorLexico.Lexico;
import AnalizadorLexico.TablaSimbolos;

public class AS3 extends AccionSemantica{
    @Override
    public Optional<Integer> ejecutar(Character caracterActual, Lexico lexico) {
    	TablaSimbolos TS = lexico.getTablaSimbolos();
    	
    	String token = lexico.getToken();
    	
    	ArrayList<Integer> atributos = new ArrayList<Integer>();
		atributos.add(SINGLE_CONSTANTE);
		atributos.add(TIPO_SINGLE);
		
		if(!TS.estaToken(token)) {
			TS.agregarToken(token, atributos);
		}
		
		lexico.setYylval(token);
        return Optional.of(SINGLE_CONSTANTE);
    }
}
