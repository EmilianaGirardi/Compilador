package AnalizadorLexico.AccionesSemanticas;

import AnalizadorLexico.Lexico;

import java.io.IOException;

public class AS15 implements AccionSemantica{

    //as16 y as17 deberian ser igual a esta.
    @Override
    public void ejecutar(String token, Character caracterActual, Lexico lexico) throws IOException {
        String simbolo = String.valueOf(caracterActual);
        lexico.setYyval(simbolo);
        lexico.leerSiguiente();
    }

    /*
    Devolver el último carácter.
    Buscar en la tabla de símbolos Identificador tipo Menor.
    Si esta: devolver puntero a la tabla de símbolos.
    No está: Agregar a la tabla de símbolos, devolver puntero a la tabla de símbolos.
    */

    //EL < NO SE GUARDA EN LA TABLA DE SIMBOLOS PORQUE ES UN SOLO CARACTER
}
