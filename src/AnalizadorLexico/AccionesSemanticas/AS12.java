package AnalizadorLexico.AccionesSemanticas;

import java.io.IOException;
import java.util.Optional;

import AnalizadorLexico.Lexico;

public class AS12 extends AccionSemantica {

	@Override
	public Optional<Integer>  ejecutar(Character caracterActual, Lexico lexico) throws IOException {
        lexico.addCharToken(caracterActual);
        String token = lexico.getToken();
        
        if (!lexico.getTablaSimbolos().estaToken(token)){
            lexico.getTablaSimbolos().agregarToken(token, MAYORIGUAL);
        }
        lexico.setYyval(token); //puntero a la tabla de simbolos
        lexico.leerSiguiente();

        return Optional.of(MAYORIGUAL); //devuelve el token
	}

}
