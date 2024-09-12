package AnalizadorLexico;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;



public class Lexico {
    private Par[][] matriz;
    private int contadorLinea;

    private TablaSimbolos tablaSimbolos;

    private int punteroArchivo;

    private char caracterActual;

    private FileReader fr;

    public Lexico(Par[][] matriz, TablaSimbolos tablaSimbolos, String archivo){
        this.matriz = matriz;
        this.tablaSimbolos = tablaSimbolos;
        this.contadorLinea=0;
        this.abrirArchivo(archivo);
    }

    public int getContadorLinea() {
        return contadorLinea;
    }

    public void abrirArchivo(String archivo) {
        try {
            this.fr = new FileReader(archivo);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void leerSiguiente() throws IOException {
        this.caracterActual = (char) fr.read();
        if (caracterActual == '/r/n')
            this.contadorLinea++;
    }

    //generar token deberia devolver un int que es el valor del token.
    //si se requiere devolver el lexema debe devolverse en la variable yylval (puntero a la tabla de simbolos)
    public int generarToken() throws IOException {
        this.leerSiguiente();
        String token = "";
    }
}
