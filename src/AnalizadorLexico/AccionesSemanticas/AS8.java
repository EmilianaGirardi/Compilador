package AnalizadorLexico.AccionesSemanticas;

import java.util.Optional;

import AnalizadorLexico.Lexico;
import AnalizadorLexico.TablaSimbolos;

public class AS8 extends AccionSemantica{

    @Override
    public Optional<Integer>  ejecutar(Character caracterActual, Lexico lexico) {
        // TODO Auto-generated method stub
        TablaSimbolos TS = lexico.getTablaSimbolos();
        String token = lexico.getToken();
        
        if(!TS.estaToken(token)) {
            TS.agregarToken(token, ID);
        }

        lexico.setYyval(token);
        return Optional.of(ID);
    }
} //LA 8 y la 9 son iguales

