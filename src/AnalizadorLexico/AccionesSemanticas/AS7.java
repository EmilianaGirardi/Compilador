package AnalizadorLexico.AccionesSemanticas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import AnalizadorLexico.Lexico;
import AnalizadorLexico.TablaSimbolos;

public class AS7 extends AccionSemantica{
    @Override
    public Optional<Integer> ejecutar(Character caracterActual) throws IOException {
    	Lexico lexico = Lexico.getInstance();
    	
    	String token = lexico.getToken();
    	
    	ArrayList<Integer> atributos = new ArrayList<Integer>();
    	atributos.add(MULTILINEA);
    	atributos.add(TIPO_MULTILINEA);
    	
        if (caracterActual.equals(']')) {
            TablaSimbolos TS = lexico.getTablaSimbolos();
            TS.agregarToken(token, atributos);
            lexico.leerSiguiente();
        }
        lexico.setYylval(token);
        return Optional.of(MULTILINEA);
    }
}