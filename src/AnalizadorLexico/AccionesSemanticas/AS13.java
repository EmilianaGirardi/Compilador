package AnalizadorLexico.AccionesSemanticas;

import java.io.IOException;
import java.util.Optional;

import AnalizadorLexico.Lexico;

public class AS13 extends AccionSemantica{

    //as16 y as17 deberian ser igual a esta.
    @Override
    public Optional<Integer> ejecutar(Character caracterActual, Lexico lexico) throws IOException {
        int ascii = lexico.getToken().charAt(0);
        return Optional.of(ascii);
    }

    //EL < NO SE GUARDA EN LA TABLA DE SIMBOLOS PORQUE ES UN SOLO CARACTER
}
