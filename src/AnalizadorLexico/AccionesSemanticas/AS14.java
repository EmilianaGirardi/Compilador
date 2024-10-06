package AnalizadorLexico.AccionesSemanticas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import AnalizadorLexico.Lexico;

public class AS14 extends AccionSemantica{
    @Override
    public Optional<Integer> ejecutar(Character caracterActual, Lexico lexico) throws IOException {
        lexico.addCharToken(caracterActual);
        String token = lexico.getToken();
        ArrayList<Integer> atributos = new ArrayList<Integer>();
        atributos.add(MENORIGUAL);
        if (!lexico.getTablaSimbolos().estaToken(token)){
            lexico.getTablaSimbolos().agregarToken(token, atributos);
        }
        lexico.setYylval(token); //puntero a la tabla de simbolos
        lexico.leerSiguiente();

        return Optional.of(MENORIGUAL); //devuelve el token

    }
}
