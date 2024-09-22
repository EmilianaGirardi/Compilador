package AnalizadorLexico.AccionesSemanticas;

import java.util.Optional;

import AnalizadorLexico.Lexico;
import AnalizadorLexico.TablaSimbolos;

public class AS7 extends AccionSemantica{
    @Override
    public Optional<Integer> ejecutar(Character caracterActual, Lexico lexico) {
    	String token = lexico.getToken();
    	
        if (caracterActual.equals(']')) {
            TablaSimbolos TS = lexico.getTablaSimbolos();
            TS.agregarToken(token, MULTILINEA); ///NECESITO UN VALOR PARA AGREGAR y no se cual es :c. Le puse ese de forma provisional
        }
        return Optional.of(MULTILINEA);
    }
}