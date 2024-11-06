package AnalizadorLexico.AccionesSemanticas;

import java.io.IOException;
import java.util.Optional;

import AnalizadorLexico.Lexico;

///REPENSARLO..............

public class AS6 extends AccionSemantica {

    @Override
    public Optional<Integer> ejecutar(Character caracterActual) throws IOException {
    	
    	Lexico lexico = Lexico.getInstance();
    	
        if (caracterActual.equals('\r')) {
        	lexico.leerSiguiente();
            caracterActual = lexico.getCaracterActual();
            if (caracterActual.equals('\n')){
            	lexico.leerSiguiente();
            	return Optional.empty();
            }
        }else if(caracterActual.equals('\n')) {
        	lexico.leerSiguiente();
        	return Optional.empty();
        }
        
        lexico.addCharToken(caracterActual);
        lexico.leerSiguiente();
        return Optional.empty();
    }
}
