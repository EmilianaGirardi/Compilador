package AnalizadorLexico;

import AnalizadorLexico.AccionesSemanticas.*;
//import AnalizadorLexico.AccionesSemanticas.AccionSemantica;

import java.io.FileReader;
import java.io.IOException;
//import java.util.ArrayList;
import java.io.BufferedReader;
import java.util.Iterator;
import java.util.Optional;


public class Lexico {
    private Par[][] matriz;
    private int contadorLinea;
    private TablaSimbolos tablaSimbolos;
    private char caracterActual;
    private FileReader fr;
    private String yyval;
    private String token;

	private static final int FILAS=17;
    private static final int COLUMNAS=29;
    public static final int ESTADO_FINAL=50;

    public static final char TAB = '\t';
    public static final char BLANK = ' ';
    public static final char SALTO_LINEA = '\n';
    public static final char RETORNO_CARRO = '\r';
    //public static final char FINAL_ARCHIVO = '$'; (no hace falta ya que el reed devuelve -1 en eof)


    public Lexico(String archivo) throws IOException{
        crearMatriz();
        this.tablaSimbolos = new TablaSimbolos();
        this.contadorLinea=0;
        defaultToken();
        this.abrirArchivo(archivo);
    }

    private void crearMatriz(){
        this.matriz = new Par[FILAS][COLUMNAS];

        String archivoAccionesCSV = "/home/roman7978/Documentos/Workspace_Compilador/Compilador/src/AnalizadorLexico/Matriz/AccionesSemanticas-MatrizEstados.csv";
        String archivoEstadosCSV = "/home/roman7978/Documentos/Workspace_Compilador/Compilador/src/AnalizadorLexico/Matriz/TransicionEstados-MatrizEstados.csv";
        String lineaAccSem = "";
        String lineaEstados = "";
        String separador = ",";

        try {
            BufferedReader accSem = new BufferedReader(new FileReader(archivoAccionesCSV));
            BufferedReader estados = new BufferedReader(new FileReader(archivoEstadosCSV));

            for (int lineaActual=0 ; lineaActual<FILAS ; lineaActual++){
                // Dividir la línea en columna
                lineaAccSem=accSem.readLine();
                lineaEstados=estados.readLine();

                String[] columnasAccSem = lineaAccSem.split(separador);
                String[] columnaEstados = lineaEstados.split(separador);

                // Imprimir cada columna de la fila
                for (int simboloActual=0 ; simboloActual<COLUMNAS ; simboloActual++) {
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
                        case "17":
                            matriz[lineaActual][simboloActual].setEstado(17);
                            break;
                        case "50":
                        	matriz[lineaActual][simboloActual].setEstado(50);
                            break;
                    }

                }
            }
            /*
            for (int i = 0; i<FILAS; i++) {
				for (int j = 0; j<COLUMNAS; j++) {
					System.out.println("");
					System.out.println("Estado: "+i+" - Simbolo: "+j);
					System.out.println("Matriz estado: "+matriz[i][j].getEstado());
					System.out.println("Matriz accionSemantica: "+matriz[i][j].getAs());
					System.out.println("------------------------------------");
				}
				
			}
			*/
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int getContadorLinea() {
        return contadorLinea;
    }

    public void abrirArchivo(String archivo) throws IOException {
        this.fr = new FileReader(archivo);
        this.leerSiguiente();
    }

    public void leerSiguiente() throws IOException {
        this.caracterActual = (char) fr.read();
        
        if (caracterActual == RETORNO_CARRO) {
        	
            this.caracterActual = (char) fr.read();
            if (caracterActual == SALTO_LINEA)
                this.contadorLinea++;
            
        }else if(caracterActual == SALTO_LINEA){
            this.contadorLinea++;
        }
        
        if (caracterActual == TAB || caracterActual == BLANK)
            leerSiguiente();
        
    }

    public void setYyval(String yyval) {
        this.yyval = yyval;
    }

    public TablaSimbolos getTablaSimbolos() {
        return tablaSimbolos;
    }

    //generar token deberia devolver un int que es el valor del token.
    //si se requiere devolver el lexema debe devolverse en la variable yylval (puntero a la tabla de simbolos)
    public int yylex() throws IOException {
        //this.leerSiguiente();
        //String token = "";
        int estadoActual = 0;
        Optional<Integer> t = null;
        
        
        while (estadoActual != ESTADO_FINAL){
        	
        	int columna = mapeoCaracter(caracterActual);
        	
            t = matriz[estadoActual][columna].getAs().ejecutar(caracterActual, this);
            estadoActual=matriz[estadoActual][columna].getEstado();
            
            
        }
        //cuando sale del while retornar el int del token (los da yacc)
        //entrega 0 si es end of file
        
       defaultToken();
        
        if (t != null){
            if (t.isPresent()) {
                return t.get();
            }
        }

        if(caracterActual == -1) { //PROBLEMA: CARACTER ACTUAL ES CHAR, POR LO QUE NO TOMA NUNCA EL VALOR -1
            /* Cuando fr.read() devuelve -1, indica que se ha alcanzado el final del archivo.
            Sin embargo, al convertir este valor a char con (char) fr.read(), el valor -1
            se convierte en un carácter Unicode inválido, ya que char en Java no puede representar valores negativos.
            En Java, el tipo char representa un carácter Unicode de 16 bits, con un rango de 0 a 65535.
            Por lo tanto, cuando intentas asignar -1 a un char, el valor se convierte en 65535
            debido a la conversión de tipo.
            Para manejar correctamente el final del archivo,
            deberías almacenar el valor devuelto por fr.read() en una variable de tipo int
            antes de realizar cualquier conversión. */
            //no me gusta que sea int, prefiero manejarlo de otra forma.
        	return 0;
        }
        
        return 1;
    }

	public int mapeoCaracter(char caracter) {
		// TODO Auto-generated method stub
		switch(caracter) {
		case '[':
			return 3;
			
		case 'u':
			return 4;
			
		case 'v':
			return 5;

		case 'w':
			return 6;
			
		case 's':
			return 7;
			
		case '#':
			return 8;
			
		case '>':
			return 9;
			
		case '<':
			return 10;
			
		case '!':
			return 11;
			
		case ':':
			return 12;
			
		case '.':
			return 13;
		
		case '+':
			return 14;
			
		case '-':
			return 15;
		
		case '*':
			return 16;
			
		case '/':
			return 17;
			
		case '=':
			return 18;
			
		case '(':
			return 19;
			
		case ')':
			return 20;
			
		case ',':
			return 21;
			
		case ';':
			return 22;
			
		case ']':
			return 23;
			
		case '_':
			return 25;
			
		case RETORNO_CARRO :
			return 26;
			
		case SALTO_LINEA :
			return 27;
		
		default:
			if(Character.isDigit(caracter)) {
				int d = (int)caracter;
				if(d>7) {
					return 0;
				}else if(d>0 && d<8) {
					return 1;
				}else if(d==0) {
					return 3;
				}else {
					return 28;
				}
			}else
            { if (Character.isAlphabetic(caracter)){
                return 24;
            }else
				return 28;
			}
		}
	}
	
    public String getToken() {
		return token;
	}

	public void addCharToken(char t) {
		this.token+=t;
	}
	
	public void defaultToken() {
		this.token="";
	}
}
