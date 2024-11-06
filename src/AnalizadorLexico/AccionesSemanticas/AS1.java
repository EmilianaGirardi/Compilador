package AnalizadorLexico.AccionesSemanticas;

import java.io.IOException;
import java.util.Optional;

import AnalizadorLexico.Lexico;

public class AS1 extends AccionSemantica{

    @Override
    public Optional<Integer> ejecutar(Character caracterActual) throws IOException {
    	Lexico lexico = Lexico.getInstance();
        lexico.addCharToken(caracterActual);
        lexico.leerSiguiente();
        return null;
    }
}