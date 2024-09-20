package AnalizadorLexico.AccionesSemanticas;

import java.util.Optional;

import AnalizadorLexico.Lexico;
import AnalizadorLexico.TablaSimbolos;

public class AS2 extends AccionSemantica{
    @Override
    public Optional<Integer> ejecutar(Character caracterActual, Lexico lexico) {
        System.out.println("Error: cte mal escrita en linea "+ lexico.getContadorLinea());
        String token = lexico.getToken();
        
        TablaSimbolos TS = lexico.getTablaSimbolos();
        if(!TS.estaToken(token)) {
        	TS.agregarToken(token, null);
        }
        lexico.setYyval(token);
        return Optional.of(SINGLE_CONSTANTE);
    }
}
