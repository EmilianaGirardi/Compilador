package AnalizadorLexico.AccionesSemanticas;

import java.io.IOException;
import java.util.Optional;

import AnalizadorLexico.Lexico;

///REPENSARLO..............

public class AS6 extends AccionSemantica {

    @Override
    public Optional<Integer> ejecutar(Character caracterActual, Lexico lexico) throws IOException {
    	
        if (caracterActual.equals("/r")) { //Se supone que al barra r lo tomaba como un solo caracter
            caracterActual = lexico.leerSiguiente();
            if (!caracterActual.equals("/n"))
            {
                lexico.addCharToken("/r");
            }
        }

        return Optional.empty(); //Deberia ser void este retorno nose si esta bien
    }
}
