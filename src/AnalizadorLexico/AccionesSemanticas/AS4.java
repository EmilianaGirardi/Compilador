package AnalizadorLexico.AccionesSemanticas;

import java.io.IOException;
import java.util.Optional;

import AnalizadorLexico.Lexico;
import AnalizadorLexico.TablaSimbolos;

public class AS4 extends AccionSemantica {

    public final int limite = (int) (Math.pow(2, 15) -1);

    private String truncaEntero(String token) throws NumberFormatException {
        return Integer.toString(limite);
    }

    @Override
    public Optional<Integer> ejecutar(Character caracterActual, Lexico lexico) throws IOException {
    	
    	String token = lexico.getToken();
    	
        if (Integer.valueOf(token) < 0 || Integer.valueOf(token) > limite){
            System.out.println("Warning: Entero se encuentra fuera de rango. Línea: "+lexico.getContadorLinea());
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
