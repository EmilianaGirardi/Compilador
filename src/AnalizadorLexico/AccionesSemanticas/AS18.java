package AnalizadorLexico.AccionesSemanticas;

import AnalizadorLexico.Lexico;

import java.io.IOException;

public class AS18 implements AccionSemantica{

    @Override
    public void ejecutar(String token, Character caracterActual, Lexico lexico) throws IOException {
        lexico.leerSiguiente();
    }
}
