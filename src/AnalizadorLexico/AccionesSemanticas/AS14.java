package AnalizadorLexico.AccionesSemanticas;

import AnalizadorLexico.Lexico;
import AnalizadorLexico.TablaSimbolos;

import java.io.IOException;
import java.util.Optional;

public class AS14 implements AccionSemantica{
    /* Agregar caracter al String.
        Buscar en la tabla de símbolos Identificador tipo MenorIgual.
        Si esta: devolver puntero a la tabla de símbolos.
        No está: Agregar a la tabla de símbolos, devolver puntero a la tabla de símbolos.
    */
    @Override
    public Optional<Integer> ejecutar(String token, Character caracterActual, Lexico lexico) throws IOException {
        token = token + caracterActual;
        if (!lexico.getTablaSimbolos().estaToken("MenorIgual")){
            lexico.getTablaSimbolos().agregarToken("MenorIgual", 1);
        }
        lexico.setYyval("MenorIgual"); //puntero a la tabla de simbolos
        lexico.leerSiguiente();

        return Optional.of(MENORIGUAL); //devuelve el token

    }
}


