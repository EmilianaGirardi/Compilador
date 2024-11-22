package AnalizadorLexico.AccionesSemanticas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import AnalizadorLexico.Lexico;
import AnalizadorLexico.TablaSimbolos;

public class AS2 extends AccionSemantica{
    @Override
    public Optional<Integer> ejecutar(Character caracterActual) throws IOException {
    	Lexico lexico = Lexico.getInstance();
        System.out.println(YELLOW+"Warning: constante mal escrita. Linea: "+ lexico.getContadorLinea()+RESET);
        lexico.addCharToken('0');
        String token = lexico.getToken();
        
        ArrayList<Integer> atributos = new ArrayList<Integer>();
        atributos.add(SINGLE_CONSTANTE);
        atributos.add(TIPO_SINGLE);
        
        TablaSimbolos TS = lexico.getTablaSimbolos();
        if(!TS.estaToken(token)) {
        	TS.agregarToken(token, atributos);
        }
        lexico.setYylval(token);
        return Optional.of(SINGLE_CONSTANTE);
    }
}
