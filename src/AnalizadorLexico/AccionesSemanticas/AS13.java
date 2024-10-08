package AnalizadorLexico.AccionesSemanticas;

import java.io.IOException;
import java.util.Optional;

import AnalizadorLexico.Lexico;

public class AS13 extends AccionSemantica{

    @Override
    public Optional<Integer> ejecutar(Character caracterActual, Lexico lexico) throws IOException {
        int ascii = lexico.getToken().charAt(0);
        return Optional.of(ascii);
    }

}
