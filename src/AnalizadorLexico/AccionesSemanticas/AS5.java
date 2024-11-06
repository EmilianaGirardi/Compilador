package AnalizadorLexico.AccionesSemanticas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import AnalizadorLexico.Lexico;
import AnalizadorLexico.TablaSimbolos;

public class AS5 extends AccionSemantica {

    public final int limite = 0177777;

    private String truncaEntero(int numero) throws NumberFormatException {
        numero = numero & 0x3FFFF; //Me quedo con los 18 bits de menor peso es decir con los ultimos 6 caracteres en octal
        return Integer.toOctalString(numero);
    }
 
    @Override
    public Optional<Integer> ejecutar(Character caracterActual) throws IOException {
    	Lexico lexico = Lexico.getInstance();
    	
    	String token = lexico.getToken();
    	
    	ArrayList<Integer> atributos = new ArrayList<Integer>();
    	atributos.add(OCTAL);
    	atributos.add(TIPO_OCTAL);
        int numero = Integer.parseInt(token, 8);
        if (numero < 0 || numero > limite){
            System.out.println("Octal fuera de rango. Linea:"+lexico.getContadorLinea());
            token = '0' + truncaEntero(numero);
        }
        TablaSimbolos TS = lexico.getTablaSimbolos();
        if(!TS.estaToken(token)){
            TS.agregarToken(token, atributos);
        }
        lexico.setYylval(token);
        return Optional.of(OCTAL);
    }
}
