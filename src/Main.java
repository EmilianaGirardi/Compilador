// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

import java.io.IOException;

import AnalizadorLexico.Lexico;
import AnalizadorLexico.TablaSimbolos;

public class Main {
	
	//TODO cambiar que agregue el numero de token cada vez que agrega a la TS
	// Agregar al mapeo caracteres invalidos
	// verificar que devuelve el reed al final del archivo
	// AS nico
	// modificar tamaño de la matriz y arreglar que detecte estados y acciones nuevas.
	
	
    public static void main(String[] args) {
    	
    	String archivoPrueba="/home/roman7978/Documentos/Workspace_Compilador/Compilador/src/AnalizadorLexico/ArchivoPrueba/Prueba0";
        
		try {
			Lexico l = new Lexico(archivoPrueba);
	
        	for (int i=0; i<4; i++) {
        		System.out.println("Token: "+l.yylex());
        	}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}