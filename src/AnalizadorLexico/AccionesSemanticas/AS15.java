package AnalizadorLexico.AccionesSemanticas;

import AnalizadorLexico.Lexico;

import java.io.IOException;
import java.util.Optional;

public class AS15 implements AccionSemantica{

    //as16 y as17 deberian ser igual a esta.
    @Override
    public Optional<Integer> ejecutar(String token, Character caracterActual, Lexico lexico) throws IOException {
        lexico.leerSiguiente();
        int ascii = caracterActual;
        return Optional.of(ascii);
    }

    /*
    Devolver el último carácter.
    Buscar en la tabla de símbolos Identificador tipo Menor.
    Si esta: devolver puntero a la tabla de símbolos.
    No está: Agregar a la tabla de símbolos, devolver puntero a la tabla de símbolos.
    */

    //EL < NO SE GUARDA EN LA TABLA DE SIMBOLOS PORQUE ES UN SOLO CARACTER
}
