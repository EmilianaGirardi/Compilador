package AnalizadorLexico.AccionesSemanticas;

import java.io.IOException;
import java.util.Optional;

import AnalizadorLexico.Lexico;

public class AS15 extends AccionSemantica{

    @Override
    public Optional<Integer> ejecutar(Character caracterActual, Lexico lexico) throws IOException {
        int ascii = caracterActual;
        lexico.leerSiguiente();
        return Optional.of(ascii);
    }

    //EL < NO SE GUARDA EN LA TABLA DE SIMBOLOS PORQUE ES UN SOLO CARACTER
}