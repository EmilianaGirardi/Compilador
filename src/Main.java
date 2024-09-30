// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

import java.io.IOException;

import AnalizadorLexico.Lexico;
import AnalizadorLexico.TablaSimbolos;

public class Main {
	
    public static void main(String[] args) {
    	
    	String archivoPrueba="/home/roman7978/Documentos/Workspace_Compilador/Compilador/src/AnalizadorLexico/ArchivoPrueba/Prueba0";

		try {
			Lexico l = new Lexico(archivoPrueba);
			int v = 1;
        	while(v!=0) {
        		v = l.yylex();
        		//System.out.println("Token: "+v);
        	}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}