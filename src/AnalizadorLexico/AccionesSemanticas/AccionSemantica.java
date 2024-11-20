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
    public final static int  TIPO_OCTAL = 3;
    public final static int  TIPO_MULTILINEA = 4;
    public final static int TIPO_TRIPLE = 5;
    public final static int  TIPO_DESCONOCIDO = 50;
    public final static int TIPO_SIMBOLO = 60;

    public final static int NOMBRE_VAR = 101;

    public static final String YELLOW = "\u001B[33m"; //color amarillo para warnings.
    public static final String RESET = "\u001B[0m"; //color blanco para texto plano.
    
    public abstract Optional<Integer> ejecutar(Character caracterActual) throws IOException;

}



