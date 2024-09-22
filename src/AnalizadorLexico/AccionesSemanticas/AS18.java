package AnalizadorLexico.AccionesSemanticas;

import AnalizadorLexico.Lexico;

import java.io.IOException;
import java.util.Optional;

public class AS18 extends AccionSemantica{

    @Override
    public Optional<Integer> ejecutar(Character caracterActual, Lexico lexico) throws IOException {
        System.out.println("Warning: caracter invalido eliminado. Línea: "+lexico.getContadorLinea());
        lexico.leerSiguiente();
        return Optional.empty();
    }
}
