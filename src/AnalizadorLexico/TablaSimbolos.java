package AnalizadorLexico;

import java.util.HashMap;
import java.util.Map;

public class TablaSimbolos {
    private Map<String, Integer> map = new HashMap<>();
    //Map(Lexema, array de atributos )

    //el puntero a la tabla de simbolos es el string clave de acceso (lexema)
    //los signos +, -, etc. no se guardan
    //las palabras reservadas se guardan en la ts

    public void agregarToken(String lexema, Integer atributo){
        map.put(lexema, atributo);
    }

    public boolean estaToken(String lexema){
        return map.containsKey(lexema);
    }

}

