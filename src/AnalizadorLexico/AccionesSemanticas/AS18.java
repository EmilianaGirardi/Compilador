package AnalizadorLexico.AccionesSemanticas;

import AnalizadorLexico.Lexico;

import java.io.IOException;
import java.util.Optional;

public class AS18 implements AccionSemantica{

    @Override
    public Optional<Integer> ejecutar(String token, Character caracterActual, Lexico lexico) throws IOException {
        lexico.leerSiguiente();
        return null;
    }
}
