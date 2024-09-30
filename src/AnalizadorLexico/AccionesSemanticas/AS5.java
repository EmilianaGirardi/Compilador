package AnalizadorLexico.AccionesSemanticas;

import java.io.IOException;
import java.util.Optional;

import AnalizadorLexico.Lexico;
import AnalizadorLexico.TablaSimbolos;

public class AS5 extends AccionSemantica {

    public final int limite = 0177777;

    private String truncaEntero(int numero) throws NumberFormatException {
        numero = numero & 0xFFFF;
        return Integer.toOctalString(numero);
    }

    @Override
    public Optional<Integer> ejecutar(Character caracterActual, Lexico lexico) throws IOException {
    	String token = lexico.getToken();
        int numero = Integer.parseInt(token, 8)
        if (numero < 0 || numero > limite){
            System.out.println("Octal fuera de rango. Linea:"+lexico.getContadorLinea());
            token = truncaEntero(numero);
        }
        TablaSimbolos TS = lexico.getTablaSimbolos();
        if(!TS.estaToken(token)){
            TS.agregarToken(token, OCTAL);
        }
        lexico.setYyval(token);
        return Optional.of(OCTAL);
    }
}
