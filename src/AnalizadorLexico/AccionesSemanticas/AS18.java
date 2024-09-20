package AnalizadorLexico.AccionesSemanticas;

import AnalizadorLexico.Lexico;

import java.io.IOException;
import java.util.Optional;

public class AS18 extends AccionSemantica{

    @Override
    public Optional<Integer> ejecutar(Character caracterActual, Lexico lexico) throws IOException {
        System.out.print("Warning: caracter invalido eliminado.");
        lexico.leerSiguiente();
        return Optional.empty();
    }
}
