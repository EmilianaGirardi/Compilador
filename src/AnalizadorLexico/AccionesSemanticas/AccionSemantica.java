package AnalizadorLexico.AccionesSemanticas;

import AnalizadorLexico.Lexico;
import AnalizadorLexico.TablaSimbolos;

import java.io.IOException;
import java.util.Optional;

public abstract class AccionSemantica {
    //CONSTANTES LITERALES TOKENS
    public final static int MENORIGUAL=257;
    public final static int ID=258;
    public final static int ASIGNACION=259;
    public final static int DISTINTO=260;
    public final static int MAYORIGUAL=261;
    public final static int SINGLE_CONSTANTE=262;
    public final static int ENTERO_UNSIGNED = 263;
    public final static int octal = 264;
    
    public abstract Optional<Integer> ejecutar(Character caracterActual, Lexico lexico) throws IOException;

}



