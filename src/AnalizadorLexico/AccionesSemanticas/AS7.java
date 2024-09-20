package AnalizadorLexico.AccionesSemanticas;

import java.util.Optional;

import AnalizadorLexico.Lexico;
import AnalizadorLexico.TablaSimbolos;

public class AS7 extends AccionSemantica{
    @Override
    public Optional<Integer> ejecutar(Character caracterActual, Lexico lexico) {
    	String token = lexico.getToken();
    	
        if (caracterActual.equals("/r")) { /////EL simbolo es ']'
            TablaSimbolos TS = lexico.getTablaSimbolos();
            TS.agregarToken(token, null);
        }
        return Optional.empty();
    }
}