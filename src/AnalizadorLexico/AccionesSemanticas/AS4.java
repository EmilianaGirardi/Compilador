package AnalizadorLexico.AccionesSemanticas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import AnalizadorLexico.Lexico;
import AnalizadorLexico.TablaSimbolos;

public class AS4 extends AccionSemantica {

    public final int limite = (int) (Math.pow(2, 15) -1);

    private String truncaEntero(String token) throws NumberFormatException {
        return Integer.toString(limite);
    }

    @Override
    public Optional<Integer> ejecutar(Character caracterActual) throws IOException {
    	Lexico lexico = Lexico.getInstance();
    	String token = lexico.getToken();
    	
    	ArrayList<Integer> atributos = new ArrayList<Integer>();
        atributos.add(ENTERO_UNSIGNED);
        atributos.add(TIPO_UNSIGNED);
    	
        if (Integer.valueOf(token) < 0 || Integer.valueOf(token) > limite){
            System.out.println(YELLOW+"Warning: Entero se encuentra fuera de rango. Línea: "+lexico.getContadorLinea()+RESET);
            token = truncaEntero(token);
        }
        
        TablaSimbolos TS = lexico.getTablaSimbolos();
        if(!TS.estaToken(token)){
            TS.agregarToken(token, atributos);
        }
        lexico.setYylval(token);
        return Optional.of(ENTERO_UNSIGNED);
    }
}
