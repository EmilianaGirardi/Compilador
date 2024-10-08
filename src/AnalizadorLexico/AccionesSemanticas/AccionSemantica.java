package AnalizadorLexico.AccionesSemanticas;

import AnalizadorLexico.Lexico;
import AnalizadorLexico.TablaSimbolos;

import java.io.IOException;
import java.util.Optional;

import java.util.ArrayList;

public abstract class AccionSemantica {
    //CONSTANTES LITERALES TOKENS

    public final static int MENORIGUAL=257;
    public final static int ID=258;
    public final static int ASIGNACION=259;
    public final static int DISTINTO=260;
    public final static int MAYORIGUAL=261;
    public final static int SINGLE_CONSTANTE=262;
    public final static int ENTERO_UNSIGNED=263;
    public final static int OCTAL=264;
    public final static int MULTILINEA=265;
    
    //---
    
    public final static int TIPO_UNSIGNED = 1;
    public final static int  TIPO_SINGLE = 2;
    
    public abstract Optional<Integer> ejecutar(Character caracterActual, Lexico lexico) throws IOException;

}



