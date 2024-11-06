package AnalizadorLexico.AccionesSemanticas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import AnalizadorLexico.Lexico;

public class AS12 extends AccionSemantica {

	@Override
	public Optional<Integer>  ejecutar(Character caracterActual) throws IOException {
		Lexico lexico = Lexico.getInstance();
        lexico.addCharToken(caracterActual);
        String token = lexico.getToken();
        
        ArrayList<Integer> atributos = new ArrayList<Integer>();
        atributos.add(MAYORIGUAL);
        if (!lexico.getTablaSimbolos().estaToken(token)){
            lexico.getTablaSimbolos().agregarToken(token, atributos);
        }
        lexico.setYylval(token); //puntero a la tabla de simbolos
        lexico.leerSiguiente();

        return Optional.of(MAYORIGUAL); //devuelve el token
	}

}
