package AnalizadorLexico.AccionesSemanticas;

import AnalizadorLexico.Lexico;

import java.io.IOException;
import java.util.Optional;

public class AS18 extends AccionSemantica{

    @Override
    public Optional<Integer> ejecutar(Character caracterActual) throws IOException {
    	Lexico lexico = Lexico.getInstance();
        if(caracterActual!=' ' && caracterActual!='\t') {
            System.out.println("Warning: caracter invalido eliminado. Línea: " + lexico.getContadorLinea());
        }
        lexico.leerSiguiente();
        return Optional.empty();
    }
}
