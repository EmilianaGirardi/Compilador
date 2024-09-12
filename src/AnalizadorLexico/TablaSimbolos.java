package AnalizadorLexico;

import java.util.HashMap;
import java.util.Map;

public class TablaSimbolos {
    private Map<String, Integer> map = new HashMap<>();
    //Map(Lexema, array de atributos )

    //el puntero a la tabla de simbolos es el string clave de acceso (lexema)
    //los signos +, -, etc. no se guardan
    //las palabras reservadas se guardan en la ts

    // Insertar elementos en el map (clave-valor)
        map.put("Uno", 1);
        map.put("Dos", 2);
        map.put("Tres", 3);
}
