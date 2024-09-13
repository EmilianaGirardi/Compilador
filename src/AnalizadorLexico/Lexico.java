package AnalizadorLexico;

import AnalizadorLexico.AccionesSemanticas.AS1;
import AnalizadorLexico.AccionesSemanticas.AS2;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedReader;



public class Lexico {
    private Par[][] matriz;
    private int contadorLinea;
    private TablaSimbolos tablaSimbolos;
    private char caracterActual;
    private FileReader fr;
    public static final char TAB = '\t';
    public static final char BLANK = ' ';
    public static final char SALTO_LINEA = '\n';
    public static final char RETORNO_CARRO = '\r';


    public Lexico(TablaSimbolos tablaSimbolos, String archivo){
        crearMatriz();
        this.tablaSimbolos = tablaSimbolos;
        this.contadorLinea=0;
        this.abrirArchivo(archivo);
    }

    private void crearMatriz(){
        this.matriz = new Par[17][28];

        String archivoAccionesCSV = "\"C:\\Users\\emigi\\OneDrive\\Escritorio\\Compiladores\\Matrix\\Matriz de AS.csv\"";
        String archivoEstadosCSV = "\"C:\\Users\\emigi\\OneDrive\\Escritorio\\Compiladores\\Matrix\\Matriz de estados.csv\"";
        String lineaAccSem = "";
        String lineaEstados = "";
        String separador = ",";

        try {
            BufferedReader accSem = new BufferedReader(new FileReader(archivoAccionesCSV));
            BufferedReader estados = new BufferedReader(new FileReader(archivoEstadosCSV));

            for (int lineaActual=0 ; lineaActual<17 ; lineaActual++){
                // Dividir la línea en columna
                lineaAccSem=accSem.readLine();
                lineaEstados=estados.readLine();

                String[] columnasAccSem = lineaAccSem.split(separador);
                String[] columnaEstados = lineaEstados.split(separador);

                // Imprimir cada columna de la fila
                for (int simboloActual=0 ; simboloActual<28 ; simboloActual++) {
                    matriz[lineaActual][simboloActual]=new Par();

                    switch (columnasAccSem[simboloActual]){
                        case "1":
                            matriz[lineaActual][simboloActual].setAs(new AS1());
                            break;
                        case "2":
                            matriz[lineaActual][simboloActual].setAs(new AS2());
                            break;
                        case "3":
                            matriz[lineaActual][simboloActual].setAs(new AS3());
                            break;
                        case "4":
                            matriz[lineaActual][simboloActual].setAs(new AS4());
                            break;
                        case "5":
                            matriz[lineaActual][simboloActual].setAs(new AS5());
                            break;
                        case "6":
                            matriz[lineaActual][simboloActual].setAs(new AS6());
                            break;
                        case "7":
                            matriz[lineaActual][simboloActual].setAs(new AS7());
                            break;
                        case "8":
                            matriz[lineaActual][simboloActual].setAs(new AS8());
                            break;
                        case "9":
                            matriz[lineaActual][simboloActual].setAs(new AS9());
                            break;
                        case "10":
                            matriz[lineaActual][simboloActual].setAs(new AS10());
                            break;
                        case "11":
                            matriz[lineaActual][simboloActual].setAs(new AS11());
                            break;
                        case "12":
                            matriz[lineaActual][simboloActual].setAs(new AS12());
                            break;
                        case "13":
                            matriz[lineaActual][simboloActual].setAs(new AS13());
                            break;
                        case "14":
                            matriz[lineaActual][simboloActual].setAs(new AS14());
                            break;
                        case "15":
                            matriz[lineaActual][simboloActual].setAs(new AS15());
                            break;
                        case "16":
                            matriz[lineaActual][simboloActual].setAs(new AS16());
                            break;
                        case "17":
                            matriz[lineaActual][simboloActual].setAs(new AS17());
                            break;
                        case "18":
                            matriz[lineaActual][simboloActual].setAs(new AS18());
                            break;
                    }

                    switch (columnaEstados[simboloActual]){
                        case "1":
                            matriz[lineaActual][simboloActual].setEstado(1);
                            break;
                        case "2":
                            matriz[lineaActual][simboloActual].setEstado(2);
                            break;
                        case "3":
                            matriz[lineaActual][simboloActual].setEstado(3);
                            break;
                        case "4":
                            matriz[lineaActual][simboloActual].setEstado(4);
                            break;
                        case "5":
                            matriz[lineaActual][simboloActual].setEstado(5);
                            break;
                        case "6":
                            matriz[lineaActual][simboloActual].setEstado(6);
                            break;
                        case "7":
                            matriz[lineaActual][simboloActual].setEstado(7);
                            break;
                        case "8":
                            matriz[lineaActual][simboloActual].setEstado(8);
                            break;
                        case "9":
                            matriz[lineaActual][simboloActual].setEstado(9);
                            break;
                        case "10":
                            matriz[lineaActual][simboloActual].setEstado(10);
                            break;
                        case "11":
                            matriz[lineaActual][simboloActual].setEstado(11);
                            break;
                        case "12":
                            matriz[lineaActual][simboloActual].setEstado(12);
                            break;
                        case "13":
                            matriz[lineaActual][simboloActual].setEstado(13);
                            break;
                        case "14":
                            matriz[lineaActual][simboloActual].setEstado(14);
                            break;
                        case "15":
                            matriz[lineaActual][simboloActual].setEstado(15);
                            break;
                        case "16":
                            matriz[lineaActual][simboloActual].setEstado(16);
                            break;
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        if (caracterActual == RETORNO_CARRO) {
            this.caracterActual = (char) fr.read();
            if (caracterActual == SALTO_LINEA)
                this.contadorLinea++;
        }
        if (caracterActual == TAB || caracterActual == BLANK)
            leerSiguiente();
    }


    //generar token deberia devolver un int que es el valor del token.
    //si se requiere devolver el lexema debe devolverse en la variable yylval (puntero a la tabla de simbolos)
    //TODO implementar la logica del metodo principal
    public int generarToken() throws IOException {
        this.leerSiguiente();
        String token = "";
    }
}
