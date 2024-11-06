package AnalizadorLexico.AccionesSemanticas;

import java.io.IOException;
import java.util.Optional;

import AnalizadorLexico.Lexico;

public class AS13 extends AccionSemantica{

    @Override
    public Optional<Integer> ejecutar(Character caracterActual) throws IOException {
    	Lexico lexico = Lexico.getInstance();
        int ascii = lexico.getToken().charAt(0);
        return Optional.of(ascii);
    }

}
