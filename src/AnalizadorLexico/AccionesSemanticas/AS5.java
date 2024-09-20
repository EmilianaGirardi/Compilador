package AnalizadorLexico.AccionesSemanticas;

import java.io.IOException;
import java.util.Optional;

import AnalizadorLexico.Lexico;
import AnalizadorLexico.TablaSimbolos;

public class AS5 extends AccionSemantica {

    public final int limite = 0177777;

    private String truncaEntero(String token) throws NumberFormatException {
        int numero = Integer.parseInt(token);
        numero = numero & 0xFFFF;
        return Integer.toString(numero);
    }

    @Override
    public Optional<Integer> ejecutar(Character caracterActual, Lexico lexico) throws IOException {
    	String token = lexico.getToken();
    	
        if (Integer.valueOf(token) < 0 || Integer.valueOf(token) > limite){
            System.out.println("EL entero se encuentra fuera de rango");
            token = truncaEntero(token);
        }
        TablaSimbolos TS = lexico.getTablaSimbolos();
        if(!TS.estaToken(token)){
            TS.agregarToken(token, ENTERO_UNSIGNED);
        }
        lexico.setYyval(token);
        return Optional.of(ENTERO_UNSIGNED);
    }
}
