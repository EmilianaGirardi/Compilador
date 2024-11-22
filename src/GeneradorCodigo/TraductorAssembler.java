package GeneradorCodigo;

import AnalizadorLexico.Lexico;
import AnalizadorLexico.TablaSimbolos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class TraductorAssembler {
	
	private String path;
	
	private FileWriter encabezado;
	private FileWriter cuerpo;
	private FileWriter funciones;
	
	private FileWriter assembler;
	
	private boolean funcion;
	private int anidamiento;
	
	private Integer numAux;
	private Integer numCadena;
	private Integer numFloat;
	
	private HashMap<String, String> mapaCadenas; //key: lexema; valor: etiqueta del .data
	private HashMap<String, String> mapaSingles; //key: cte; valor: etiqueta del .data
	private Generador generador;
	private Lexico lexico;

	private final static int  T_SINGLE = 2;
    
    private final static int  TIPO_ETIQUETA = 8;
 
    private final static int  TIPO_FUNCION = 10;
    private final static int TIPO_RETORNO = 11; 
    private final static int  TIPO_DESCONOCIDO = 50;
	private final static int TIPO_AUX_ENTERO = 12;
	private final static int TIPO_AUX_FLOAT = 13;
	
	private static String saltoLinea = "\r\n";
	private String identacion;

	public TraductorAssembler(String archivoSalida) throws IOException {
		this.path = archivoSalida;
		
		this.assembler = new FileWriter(path, false);
		this.cuerpo  = new FileWriter("src/GeneradorCodigo/cuerpo.txt",false);
		
		this.encabezado= new FileWriter("src/GeneradorCodigo/encabezado.txt",false);
		this.funciones= new FileWriter("src/GeneradorCodigo/funciones.txt",false);
		
		this.numAux = 0;
		this.numCadena = 0;
		this.numFloat = 0;
		
		this.funcion = false;
		this.anidamiento = 0;
		
		this.generador = Generador.getInstance();
		this.lexico = Lexico.getInstance();
		this.mapaCadenas = new HashMap<String, String>();
		this.mapaSingles = new HashMap<String, String>();
		
		this.identacion = "  ";
	}

	public void inicializarAssembler() throws IOException {
		encabezado.append("option casemap :none\n" +
				"include \\masm32\\include\\masm32rt.inc\n" +
				"includelib \\masm32\\lib\\kernel32.lib\n" +
				"includelib \\masm32\\lib\\masm32.lib" + saltoLinea);
		encabezado.append(".STACK 200h" + saltoLinea);
		encabezado.append(saltoLinea);
		encabezado.append(".DATA" + saltoLinea);
		encabezado.append("errorMsgOverflow db \"Overflow: en suma!.\", 10, 0 ;"+saltoLinea);
		encabezado.append("errorMsgConversionNegativa db \"Conversion invalida: no puede convertir un Single negativo a Entero!.\", 10, 0 ;"+saltoLinea);
		encabezado.append("errorMsgRestaNegativa db \"Resta invalida: resultado negativo!.\", 10, 0 ;"+saltoLinea);
		
		//mapeo de variables y cadenas
		TablaSimbolos TS = lexico.getTablaSimbolos();
		for (String lexema : TS.getMap().keySet()){
			if (TS.getToken(lexema) == 258 && TS.getTipo(lexema) !=TIPO_DESCONOCIDO){ //IDENTIFICADORES
				
				if(TS.getUso(lexema)==null || (TS.getUso(lexema)!=null && TS.getUso(lexema)!=102) ){
					
					if (TS.getTipo(lexema) == T_SINGLE){ //tipo single (32 bits)
						encabezado.append(lexema + " DD 0.0 " +saltoLinea);
					}
					else { //unsigned y octal de 16 bits
						encabezado.append(lexema + " DW 0 " + saltoLinea);
					}
				}
			}
			if (TS.getToken(lexema) == 265){ //MULTILINEA
				addCadena(lexema);
			}
			if (TS.getToken(lexema)== 262) { //ctes single
				addFloat(lexema);
			}
		}
		
	}
	
	public void cerrarDeclaracion() throws IOException {
		TablaSimbolos TS = lexico.getTablaSimbolos();
		for (String lexema : TS.getMap().keySet()){
			if(TS.getTipo(lexema)!=null && TS.getTipo(lexema)==TIPO_AUX_ENTERO) {
				encabezado.append(lexema + " DW 0 "+ saltoLinea);
			}else if(TS.getTipo(lexema)!=null && TS.getTipo(lexema)==TIPO_AUX_FLOAT) {
				encabezado.append(lexema + " DD 0.0 "+ saltoLinea);
			}
		}
		encabezado.append(saltoLinea);
		encabezado.append(".CODE" + saltoLinea);
	}

	public void addCadena(String lexema) throws IOException {
		if(!mapaCadenas.containsKey(lexema)) {
			String etq = "cadena"+this.numCadena;
			this.numCadena++;
			
			mapaCadenas.put(lexema, "_"+etq+"_");
			
			encabezado.append("_"+etq+"_ DB "+ "\""+lexema+"\", 10, 0" + saltoLinea);
		}
	}
	
	public void addFloat(String cte) throws IOException {
		if(!mapaSingles.containsKey(cte)) {
			String etq = "_float"+this.numFloat+"_";
			this.numFloat++;
			
			mapaSingles.put(cte, etq);
			
			encabezado.append(etq+" DD "+ cte + saltoLinea);
		}
	}
	
	private String crearAux(Integer tipo) {
		numAux++;
		
		ArrayList<Integer> atributo = new ArrayList<Integer>();
		atributo.add(0);
		if(tipo==TIPO_AUX_ENTERO)
			atributo.add(TIPO_AUX_ENTERO);
		else
			atributo.add(TIPO_AUX_FLOAT);
		
		String var = "@aux"+numAux;
		
		lexico.getTablaSimbolos().agregarToken(var, atributo);
		
		return var;
	}

	public void cerrarTraduccion() throws IOException {
		this.cuerpo.append(this.identacion+"JMP END_START"+saltoLinea);
		this.cuerpo.append(saltoLinea);
		this.cuerpo.append(this.identacion+"??errorOverflow:"+saltoLinea);
		this.cuerpo.append(this.identacion+"  invoke StdOut, addr errorMsgOverflow"+saltoLinea);
		this.cuerpo.append(this.identacion+"  JMP END_START"+saltoLinea);
		this.cuerpo.append(saltoLinea);
		this.cuerpo.append(this.identacion+"??errorConversionNegativo:"+saltoLinea);
		this.cuerpo.append(this.identacion+"  invoke StdOut, addr errorMsgConversionNegativa"+saltoLinea);
		this.cuerpo.append(this.identacion+"  JMP END_START"+saltoLinea);
		this.cuerpo.append(saltoLinea);
		this.cuerpo.append(this.identacion+"??errorRestaNegativa:"+saltoLinea);
		this.cuerpo.append(this.identacion+"  invoke StdOut, addr errorMsgRestaNegativa"+saltoLinea);
		this.cuerpo.append(saltoLinea);
		this.cuerpo.append("END_START: "+saltoLinea);
		this.cuerpo.append("invoke ExitProcess, 0"+saltoLinea);
		this.cuerpo.append("END START"); 
		
		this.funciones.append("START:" + saltoLinea);
		
		this.cerrarDeclaracion();
		
		this.encabezado.close();
		this.cuerpo.close();
		this.funciones.close();
		
		cargarArchivo("/GeneradorCodigo/encabezado.txt", this.assembler);
		
		cargarArchivo("/GeneradorCodigo/funciones.txt", this.assembler);
		
		cargarArchivo("/GeneradorCodigo/cuerpo.txt", this.assembler);
	
		this.assembler.close();
		
	}

    private void cargarArchivo(String pathArchivoOrigen, FileWriter archivoDestino) throws IOException {
        InputStream archivo = getClass().getResourceAsStream(pathArchivoOrigen);
        
        if (archivo == null) {
            throw new IOException("No se pudo encontrar uno de los archivos de Assembler en el classpath");
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(archivo));
        String linea= reader.readLine();
        
        while (linea != null) {
            archivoDestino.append(linea).append("\n");
            linea= reader.readLine();
        }

        reader.close();
    }
	
	private void suma(Terceto terceto, FileWriter salida) throws IOException {
		String op1, op2, result;
		Integer pos;
		op1 = terceto.getOperando1();
		if (op1.matches("\\[T\\d+\\]")){
			pos = Integer.parseInt(op1.replaceAll("\\D", ""));
			op1 = generador.getTerceto(pos).getAux();
		}
		op2 = terceto.getOperando2();
		if (op2.matches("\\[T\\d+\\]")){
			pos = Integer.parseInt(op2.replaceAll("\\D", ""));
			op2 = generador.getTerceto(pos).getAux();
		}

		result = crearAux(TIPO_AUX_ENTERO);
		

		salida.append(this.identacion+"MOV AX, " + op1 + saltoLinea);
		salida.append(this.identacion+"ADD AX, " + op2 + saltoLinea);
		salida.append(this.identacion+"JC ??errorOverflow"+ saltoLinea);
		salida.append(this.identacion+"MOV " + result + ", AX" + saltoLinea);
		

		terceto.setAux(result);
	}

	private void resta(Terceto terceto, FileWriter salida) throws IOException {
		String op1, op2, result;
		Integer pos;
		op1 = terceto.getOperando1();
		if (op1.matches("\\[T\\d+\\]")){
			pos = Integer.parseInt(op1.replaceAll("\\D", ""));
			op1 = generador.getTerceto(pos).getAux();
		}
		op2 = terceto.getOperando2();
		if (op2.matches("\\[T\\d+\\]")){
			pos = Integer.parseInt(op2.replaceAll("\\D", ""));
			op2 = generador.getTerceto(pos).getAux();
		}

		result = crearAux(TIPO_AUX_ENTERO);

		salida.append(this.identacion+"MOV AX, " + op1 + saltoLinea);
		salida.append(this.identacion+"SUB AX, " + op2 + saltoLinea);
		salida.append(this.identacion+"JS ??errorRestaNegativa"+ saltoLinea);
		salida.append(this.identacion+"MOV " + result + ", AX" + saltoLinea);

		terceto.setAux(result);
	}
	
	private void multiplicacion(Terceto terceto, FileWriter salida) throws IOException {
		String op1, op2, result;
		Integer pos;
		op1 = terceto.getOperando1();
		if (op1.matches("\\[T\\d+\\]")){
			pos = Integer.parseInt(op1.replaceAll("\\D", ""));
			op1 = generador.getTerceto(pos).getAux();
		}
		op2 = terceto.getOperando2();
		if (op2.matches("\\[T\\d+\\]")){
			pos = Integer.parseInt(op2.replaceAll("\\D", ""));
			op2 = generador.getTerceto(pos).getAux();
		}

		result = crearAux(TIPO_AUX_ENTERO);

		salida.append(this.identacion+"MOV AX, " + op1 + saltoLinea);
		salida.append(this.identacion+"MOV BX, " + op2 + saltoLinea);
		salida.append(this.identacion+"MUL BX " + saltoLinea);
		salida.append(this.identacion+"MOV " + result + ", AX" + saltoLinea);

		terceto.setAux(result);
	}
	
	private void division(Terceto terceto, FileWriter salida) throws IOException {
		String op1, op2, result;
		Integer pos;
		op1 = terceto.getOperando1();
		if (op1.matches("\\[T\\d+\\]")){
			pos = Integer.parseInt(op1.replaceAll("\\D", ""));
			op1 = generador.getTerceto(pos).getAux();
		}
		op2 = terceto.getOperando2();
		if (op2.matches("\\[T\\d+\\]")){
			pos = Integer.parseInt(op2.replaceAll("\\D", ""));
			op2 = generador.getTerceto(pos).getAux();
		}

		result = crearAux(TIPO_AUX_ENTERO);

		salida.append(this.identacion+"MOV AX, " + op1 + saltoLinea);
		salida.append(this.identacion+"MOV BX, " + op2 + saltoLinea);
		salida.append(this.identacion+"DIV BX "+ saltoLinea);
		salida.append(this.identacion+"MOV " + result + ", AX" + saltoLinea);

		
		
		terceto.setAux(result);
		
	}

	//Punto Flotante
	private void sumaPuntoFlotante(Terceto terceto, FileWriter salida) throws IOException {
		String op1, op2, result;
		Integer pos;
		op1 = terceto.getOperando1();
		if (op1.matches("\\[T\\d+\\]")){
			pos = Integer.parseInt(op1.replaceAll("\\D", ""));
			op1 = generador.getTerceto(pos).getAux();
		}
		if (mapaSingles.containsKey(op1)) {
			op1 = mapaSingles.get(op1);
		}
		op2 = terceto.getOperando2();
		if (op2.matches("\\[T\\d+\\]")){
			pos = Integer.parseInt(op2.replaceAll("\\D", ""));
			op2 = generador.getTerceto(pos).getAux();
		}
		if (mapaSingles.containsKey(op2)) {
			op2 = mapaSingles.get(op2);
		}

		result = crearAux(TIPO_AUX_FLOAT);

		salida.append(this.identacion+"FLD "+ op1 + saltoLinea);
		salida.append(this.identacion+"FADD "+ op2 + saltoLinea);
		salida.append(this.identacion+"FST " + result + saltoLinea);

		terceto.setAux(result);
	}

	private void restaPuntoFlotante(Terceto terceto, FileWriter salida) throws IOException {
		String op1, op2, result;
		Integer pos;
		op1 = terceto.getOperando1();
		if (op1.matches("\\[T\\d+\\]")){
			pos = Integer.parseInt(op1.replaceAll("\\D", ""));
			op1 = generador.getTerceto(pos).getAux();
		}
		if (mapaSingles.containsKey(op1)) {
			op1 = mapaSingles.get(op1);
		}
		
		op2 = terceto.getOperando2();
		if (op2.matches("\\[T\\d+\\]")){
			pos = Integer.parseInt(op2.replaceAll("\\D", ""));
			op2 = generador.getTerceto(pos).getAux();
		}
		if (mapaSingles.containsKey(op2)) {
			op2 = mapaSingles.get(op2);
		}

		result = crearAux(TIPO_AUX_FLOAT);

		salida.append(this.identacion+"FLD "+ op1 + saltoLinea);
		salida.append(this.identacion+"FSUB " + op2 + saltoLinea);
		salida.append(this.identacion+"FST " + result + saltoLinea);

		terceto.setAux(result);
	}

	private void multiplicacionPuntoFlotante(Terceto terceto, FileWriter salida) throws IOException {
		String op1, op2, result;
		Integer pos;
		op1 = terceto.getOperando1();
		if (op1.matches("\\[T\\d+\\]")){
			pos = Integer.parseInt(op1.replaceAll("\\D", ""));
			op1 = generador.getTerceto(pos).getAux();
		}
		if (mapaSingles.containsKey(op1)) {
			op1 = mapaSingles.get(op1);
		}
		
		op2 = terceto.getOperando2();
		if (op2.matches("\\[T\\d+\\]")){
			pos = Integer.parseInt(op2.replaceAll("\\D", ""));
			op2 = generador.getTerceto(pos).getAux();
		}
		if (mapaSingles.containsKey(op2)) {
			op2 = mapaSingles.get(op2);
		}

		result = crearAux(TIPO_AUX_FLOAT);

		salida.append(this.identacion+"FLD "+ op1 + saltoLinea);
		salida.append(this.identacion+"FMUL " + op2 + saltoLinea);
		salida.append(this.identacion+"FST " + result + saltoLinea);

		terceto.setAux(result);
	}

	private void divisionPuntoFlotante(Terceto terceto, FileWriter salida) throws IOException {
		String op1, op2, result;
		Integer pos;
		op1 = terceto.getOperando1();
		if (op1.matches("\\[T\\d+\\]")){
			pos = Integer.parseInt(op1.replaceAll("\\D", ""));
			op1 = generador.getTerceto(pos).getAux();
		}
		if (mapaSingles.containsKey(op1)) {
			op1 = mapaSingles.get(op1);
		}
		
		op2 = terceto.getOperando2();
		if (op2.matches("\\[T\\d+\\]")){
			pos = Integer.parseInt(op2.replaceAll("\\D", ""));
			op2 = generador.getTerceto(pos).getAux();
		}
		if (mapaSingles.containsKey(op2)) {
			op2 = mapaSingles.get(op2);
		}

		result = crearAux(TIPO_AUX_FLOAT);

		
		salida.append(this.identacion+"FLD " + op1 + saltoLinea);
		salida.append(this.identacion+"FDIV " + op2 + saltoLinea);
		salida.append(this.identacion+"FST " + result + saltoLinea);

		terceto.setAux(result);
	}

	private void etiqueta(Terceto terceto, FileWriter salida) throws IOException {
		salida.append(this.identacion+terceto.getOperador()+":" + saltoLinea);
	}

	private void call(Terceto terceto, FileWriter salida) throws IOException {
		TablaSimbolos TS = lexico.getTablaSimbolos();
		String parametro = terceto.getOperando2();
		Integer tipoParam;
	    if (parametro.matches("\\[T\\d+\\]")) {
	        int pos = Integer.parseInt(parametro.replaceAll("\\D", ""));
	        parametro = generador.getTerceto(pos).getAux();
	    }
	    tipoParam = TS.getTipo(parametro);
	    //salida.append(this.identacion+"PUSH " + parametro + saltoLinea);
	    
	    if(tipoParam!=null && tipoParam==TIPO_AUX_FLOAT) {
	    	salida.append(this.identacion+"FLD "+parametro+saltoLinea);
	    }else {
	    	String auxParam = terceto.getOperando1()+"@param";
		    if(!TS.estaToken(auxParam)) {
		    	ArrayList<Integer> atributo = new ArrayList<Integer>();
				atributo.add(0);
				atributo.add(TIPO_AUX_ENTERO);
				TS.agregarToken(auxParam, atributo);
		    }
		    salida.append(this.identacion+"MOV AX, "+parametro+saltoLinea);
		    salida.append(this.identacion+"MOV "+auxParam+", AX"+saltoLinea);
	    }
	    salida.append(this.identacion+"CALL " + terceto.getOperando1() + saltoLinea);  
	    
	    String result;
	    if(terceto.getTipo()==T_SINGLE) {
	    	result = this.crearAux(TIPO_AUX_FLOAT);
	    	salida.append(this.identacion+"FST "+result+ saltoLinea);
	    }else {
	    	result = this.crearAux(TIPO_AUX_ENTERO);
	    	salida.append(this.identacion+"MOV "+result+", AX"+ saltoLinea);
	    }
	    
	    terceto.addAux(result);
	}


	private void funcion(Terceto terceto) throws IOException {
		// TODO Auto-generated method stub
		this.funcion=true;
		this.anidamiento++;
		
		this.funciones.append(this.identacion+terceto.getOperador()+":" + saltoLinea);
		this.identacion+="  ";
		//this.funciones.append(this.identacion+"PUSH EBP"+ saltoLinea);
		//this.funciones.append(this.identacion+"MOV EBP, ESP"+ saltoLinea); //Mantengo el puntero a la base 
		
		
		String parametro = terceto.getOperando1(); 
		Integer tipo = lexico.getTablaSimbolos().getTipo(parametro);
		
		if(tipo == 2) {
			//this.funciones.append(this.identacion+"FLD DWORD PTR [EBP+8]"+saltoLinea);
			this.funciones.append(this.identacion+"FST "+parametro+saltoLinea);
		}else {
			this.funciones.append(this.identacion+"MOV AX, "+terceto.getOperador()+"@param"+ saltoLinea);
			this.funciones.append(this.identacion+"MOV "+parametro+", AX" + saltoLinea);
		}
	}
	
	private void ret(Terceto terceto) throws IOException {
		String retorno = terceto.getOperando1();
		Integer tipo= terceto.getTipo();;

		if (retorno.matches("\\[T\\d+\\]")) {
			int pos = Integer.parseInt(retorno.replaceAll("\\D", ""));
			retorno = generador.getTerceto(pos).getAux();
		}
		
		if(tipo == T_SINGLE) {
			this.funciones.append(this.identacion+"FLD "+retorno+ saltoLinea);
		}else {
			this.funciones.append(this.identacion+"MOV AX,"+retorno+ saltoLinea); 
		}
		
		
		//this.funciones.append(this.identacion+"MOV ESP, EBP"+ saltoLinea);
		//this.funciones.append(this.identacion+"POP EBP"+ saltoLinea);
		
		if(terceto.getTipo()==TIPO_RETORNO) {
			if(this.anidamiento==1) {
				this.anidamiento=0;
				this.funcion = false;	
			}else {
				this.anidamiento--;
			}	
			
			this.identacion= this.identacion.substring(0, this.identacion.length() - 2);
		}
		
		this.funciones.append(this.identacion+"RET"+ saltoLinea);
	}

	private void mayorIgual(Terceto terceto, FileWriter salida) throws IOException{
		String op1, op2;
		Integer pos;

		op1 = terceto.getOperando1();
		if (op1.matches("\\[T\\d+\\]")){
			pos = Integer.parseInt(op1.replaceAll("\\D", ""));
			op1 = generador.getTerceto(pos).getAux();
		}
		
		
		op2 = terceto.getOperando2();
		if (op2.matches("\\[T\\d+\\]")){
			pos = Integer.parseInt(op2.replaceAll("\\D", ""));
			op2 = generador.getTerceto(pos).getAux();
		}

		if (terceto.getTipo()!=null && terceto.getTipo() == T_SINGLE){
			if (mapaSingles.containsKey(op1)) {
				op1 = mapaSingles.get(op1);
			}
			if (mapaSingles.containsKey(op2)) {
				op2 = mapaSingles.get(op2);
			}
			salida.append(this.identacion+"MOV CX, 0 " + saltoLinea); //inicializamos en false
			salida.append(this.identacion+"FLD " + op1 + saltoLinea);  // Cargar op1 en ST(0)
			salida.append(this.identacion+"FLD " + op2 + saltoLinea);  // Cargar op2 en ST(1)
    		salida.append(this.identacion+"FCOM ST(1)" + saltoLinea);  // Comparar ST(0) con op2
    		salida.append(this.identacion+"FSTSW AX"+saltoLinea);
    		salida.append(this.identacion+"SAHF"+saltoLinea);
    		
			salida.append(this.identacion+"SETGE  CL" + saltoLinea);
			//En este punto, CL contendrá:
			//1 si op1 <= op2 (verdadero)
			//0 si op1 > op2 (falso)
			salida.append(this.identacion+"MOV CH, 0 " + saltoLinea);
			String result = crearAux(TIPO_AUX_ENTERO);
			salida.append(this.identacion+"MOV " + result + ", CX" + saltoLinea);
			terceto.setAux(result);
		} else{
			
					salida.append(this.identacion+"MOV CX, 0 " + saltoLinea); //inicializamos en false
					salida.append(this.identacion+"MOV AX, " + op1  + saltoLinea);
					salida.append(this.identacion+"CMP AX, " + op2 + saltoLinea);
					salida.append(this.identacion+"SETAE CL" + saltoLinea);
					//En este punto, CL contendrá:
					//1 si op1 <= op2 (verdadero)
					//0 si op1 > op2 (falso)
					salida.append(this.identacion+"MOV CH, 0 " + saltoLinea); //inicializamos en false
					String result = crearAux(TIPO_AUX_ENTERO);
					salida.append(this.identacion+"MOV " + result + ", CX" + saltoLinea);
					terceto.setAux(result);
		
		}
		
	}

	private void mayor(Terceto terceto, FileWriter salida) throws IOException{
		String op1, op2;
		Integer pos;

		op1 = terceto.getOperando1();
		if (op1.matches("\\[T\\d+\\]")){
			pos = Integer.parseInt(op1.replaceAll("\\D", ""));
			op1 = generador.getTerceto(pos).getAux();
		}

		op2 = terceto.getOperando2();
		if (op2.matches("\\[T\\d+\\]")){
			pos = Integer.parseInt(op2.replaceAll("\\D", ""));
			op2 = generador.getTerceto(pos).getAux();
		}

		if (terceto.getTipo() != null && terceto.getTipo() == T_SINGLE){
			if (mapaSingles.containsKey(op1)) {
				op1 = mapaSingles.get(op1);
			}
			if (mapaSingles.containsKey(op2)) {
				op2 = mapaSingles.get(op2);
			}
			salida.append(this.identacion+"MOV CX, 0 " + saltoLinea); //inicializamos en false
			salida.append(this.identacion+"FLD " + op1 + saltoLinea);  // Cargar op1 en ST(0)
			salida.append(this.identacion+"FLD " + op2 + saltoLinea);  // Cargar op2 en ST(1)
    		salida.append(this.identacion+"FCOM ST(1)" + saltoLinea);  // Comparar ST(0) con op2
    		salida.append(this.identacion+"FSTSW AX"+saltoLinea);
    		salida.append(this.identacion+"SAHF"+saltoLinea);
			salida.append(this.identacion+"SETG  CL" + saltoLinea);
			
			salida.append(this.identacion+"MOV CH, 0 " + saltoLinea);
			String result = crearAux(TIPO_AUX_ENTERO);
			salida.append(this.identacion+"MOV " + result + ", CX" + saltoLinea);
			terceto.setAux(result);
		}else{

			salida.append(this.identacion+"MOV CX, 0 " + saltoLinea); //inicializamos en false
			salida.append(this.identacion+"MOV  AX, " + op1 + saltoLinea);
			salida.append(this.identacion+"CMP AX, " + op2 + saltoLinea);
			salida.append(this.identacion+"SETA CL" + saltoLinea);
			salida.append(this.identacion+"MOV CH, 0 " + saltoLinea); //inicializamos en false
			
			String result = crearAux(TIPO_AUX_ENTERO);
			salida.append(this.identacion+"MOV " + result + ", CX" + saltoLinea);
			terceto.setAux(result);
		}

	}

	private void menorIgual(Terceto terceto, FileWriter salida) throws IOException{
		String op1, op2;
		Integer pos;

		op1 = terceto.getOperando1();
		if (op1.matches("\\[T\\d+\\]")){
			pos = Integer.parseInt(op1.replaceAll("\\D", ""));
			op1 = generador.getTerceto(pos).getAux();
		}

		op2 = terceto.getOperando2();
		if (op2.matches("\\[T\\d+\\]")){
			pos = Integer.parseInt(op2.replaceAll("\\D", ""));
			op2 = generador.getTerceto(pos).getAux();
		}
		
		if (terceto.getTipo()!=null && terceto.getTipo() == T_SINGLE){
			if (mapaSingles.containsKey(op1)) {
				op1 = mapaSingles.get(op1);
			}
			if (mapaSingles.containsKey(op2)) {
				op2 = mapaSingles.get(op2);
			}
			salida.append(this.identacion+"MOV CX, 0 " + saltoLinea); //inicializamos en false
			salida.append(this.identacion+"FLD " + op1 + saltoLinea);  // Cargar op1 en ST(0)
			salida.append(this.identacion+"FLD " + op2 + saltoLinea);  // Cargar op2 en ST(1)
    		salida.append(this.identacion+"FCOM ST(1)" + saltoLinea);  // Comparar ST(0) con op2
    		salida.append(this.identacion+"FSTSW AX"+saltoLinea);
    		salida.append(this.identacion+"SAHF"+saltoLinea);
			salida.append(this.identacion+"SETLE  CL" + saltoLinea);

			salida.append(this.identacion+"MOV CH, 0 " + saltoLinea);
			String result = crearAux(TIPO_AUX_ENTERO);
			salida.append(this.identacion+"MOV " + result + ", CX" + saltoLinea);
			terceto.setAux(result);
		}else{
			salida.append(this.identacion+"MOV CX, 0 " + saltoLinea); //inicializamos en false
			salida.append(this.identacion+"MOV AX, " + op1  + saltoLinea);
			salida.append(this.identacion+"CMP AX, " + op2 + saltoLinea);
			salida.append(this.identacion+"SETBE CL" + saltoLinea);
			salida.append(this.identacion+"MOV CH, 0 " + saltoLinea); //inicializamos en false
			String result = crearAux(TIPO_AUX_ENTERO);
			salida.append(this.identacion+"MOV " + result + ", CX" + saltoLinea);
			terceto.setAux(result);

		}

	}

	private void menor(Terceto terceto, FileWriter salida) throws IOException{
		String op1, op2;
		Integer pos;

		op1 = terceto.getOperando1();
		if (op1.matches("\\[T\\d+\\]")){
			pos = Integer.parseInt(op1.replaceAll("\\D", ""));
			op1 = generador.getTerceto(pos).getAux();
		}

		op2 = terceto.getOperando2();
		if (op2.matches("\\[T\\d+\\]")){
			pos = Integer.parseInt(op2.replaceAll("\\D", ""));
			op2 = generador.getTerceto(pos).getAux();
		}

		if (terceto.getTipo()!=null && terceto.getTipo()== T_SINGLE){
			if (mapaSingles.containsKey(op1)) {
				op1 = mapaSingles.get(op1);
			}
			if (mapaSingles.containsKey(op2)) {
				op2 = mapaSingles.get(op2);
			}
			salida.append(this.identacion+"MOV CX, 0 " + saltoLinea); //inicializamos en false
			salida.append(this.identacion+"FLD " + op1 + saltoLinea);  // Cargar op1 en ST(0)
			salida.append(this.identacion+"FLD " + op2 + saltoLinea);  // Cargar op2 en ST(1)
    		salida.append(this.identacion+"FCOM ST(1)" + saltoLinea);  // Comparar ST(0) con op2
    		salida.append(this.identacion+"FSTSW AX"+saltoLinea);
    		salida.append(this.identacion+"SAHF"+saltoLinea);
			salida.append(this.identacion+"SETL  CL" + saltoLinea);

			salida.append(this.identacion+"MOV CH, 0 " + saltoLinea);
			String result = crearAux(TIPO_AUX_ENTERO);
			salida.append(this.identacion+"MOV " + result + ", CX" + saltoLinea);
			terceto.setAux(result);
		}else{

			salida.append(this.identacion+"MOV CX, 0 " + saltoLinea); //inicializamos en false
			salida.append(this.identacion+"MOV AX, " + op1  + saltoLinea);
			salida.append(this.identacion+"CMP AX, " + op2 + saltoLinea);
			salida.append(this.identacion+"SETB CL" + saltoLinea); // ¿QUE HACE ESTA INSTRUCCION?
			salida.append(this.identacion+"MOV CH, 0 " + saltoLinea); //inicializamos en false
			
			String result = crearAux(TIPO_AUX_ENTERO);
			salida.append(this.identacion+"MOV " + result + ", CX" + saltoLinea);
			terceto.setAux(result);
		}

	}

	private void igual(Terceto terceto, FileWriter salida) throws IOException{
		String op1, op2;
		Integer pos;

		op1 = terceto.getOperando1();
		if (op1.matches("\\[T\\d+\\]")){
			pos = Integer.parseInt(op1.replaceAll("\\D", ""));
			op1 = generador.getTerceto(pos).getAux();
		}

		op2 = terceto.getOperando2();
		if (op2.matches("\\[T\\d+\\]")){
			pos = Integer.parseInt(op2.replaceAll("\\D", ""));
			op2 = generador.getTerceto(pos).getAux();
		}

		if (terceto.getTipo()!=null && terceto.getTipo() == T_SINGLE){
			if (mapaSingles.containsKey(op1)) {
				op1 = mapaSingles.get(op1);
			}
			if (mapaSingles.containsKey(op2)) {
				op2 = mapaSingles.get(op2);
			}
			salida.append(this.identacion+"MOV CX, 0 " + saltoLinea); //inicializamos en false
			salida.append(this.identacion+"FLD " + op1 + saltoLinea);  // Cargar op1 en ST(0)
			salida.append(this.identacion+"FLD " + op2 + saltoLinea);  // Cargar op2 en ST(1)
    		salida.append(this.identacion+"FCOM ST(1)" + saltoLinea);  // Comparar ST(0) con op2
    		salida.append(this.identacion+"FSTSW AX"+saltoLinea);
    		salida.append(this.identacion+"SAHF"+saltoLinea);
			salida.append(this.identacion+"SETZ  CL" + saltoLinea);
			
					
			salida.append(this.identacion+"MOV CH, 0 " + saltoLinea);
			String result = crearAux(TIPO_AUX_ENTERO);
			salida.append(this.identacion+"MOV " + result + ", CX" + saltoLinea);
			terceto.setAux(result);
		}else{
			salida.append(this.identacion+"MOV CX, 0 " + saltoLinea); //inicializamos en false
			salida.append(this.identacion+"MOV AX, " + op1  + saltoLinea);
			salida.append(this.identacion+"CMP AX, " + op2 + saltoLinea);
			salida.append(this.identacion+"SETE CL" + saltoLinea);
			salida.append(this.identacion+"MOV CH, 0 " + saltoLinea); //inicializamos en false

			String result = crearAux(TIPO_AUX_ENTERO);
			salida.append(this.identacion+"MOV " + result + ", CX" + saltoLinea);
			terceto.setAux(result);

		}
	}

	private void distinto(Terceto terceto, FileWriter salida) throws IOException{
		String op1, op2;
		Integer pos;

		op1 = terceto.getOperando1();
		if (op1.matches("\\[T\\d+\\]")){
			pos = Integer.parseInt(op1.replaceAll("\\D", ""));
			op1 = generador.getTerceto(pos).getAux();
		}

		op2 = terceto.getOperando2();
		if (op2.matches("\\[T\\d+\\]")){
			pos = Integer.parseInt(op2.replaceAll("\\D", ""));
			op2 = generador.getTerceto(pos).getAux();
		}

		if (terceto.getTipo()!=null && terceto.getTipo() == T_SINGLE){
			if (mapaSingles.containsKey(op1)) {
				op1 = mapaSingles.get(op1);
			}
			if (mapaSingles.containsKey(op2)) {
				op2 = mapaSingles.get(op2);
			}
			salida.append(this.identacion+"MOV CX, 0 " + saltoLinea); //inicializamos en false
			salida.append(this.identacion+"FLD " + op1 + saltoLinea);  // Cargar op1 en ST(0)
			salida.append(this.identacion+"FLD " + op2 + saltoLinea);  // Cargar op2 en ST(1)
    		salida.append(this.identacion+"FCOM ST(1)" + saltoLinea);  // Comparar ST(0) con op2
    		salida.append(this.identacion+"FSTSW AX"+saltoLinea);
    		salida.append(this.identacion+"SAHF"+saltoLinea);
			salida.append(this.identacion+"SETNZ  CL" + saltoLinea);

			salida.append(this.identacion+"MOV CH, 0 " + saltoLinea);
			String result = crearAux(TIPO_AUX_ENTERO);
			salida.append(this.identacion+"MOV " + result + ", CX" + saltoLinea);
			terceto.setAux(result);
		}else{
			salida.append(this.identacion+"MOV CX, 0 " + saltoLinea); //inicializamos en false
			salida.append(this.identacion+"MOV AX, " + op1  + saltoLinea);
			salida.append(this.identacion+"CMP AX, " + op2 + saltoLinea);
			salida.append(this.identacion+"SETNE CL" + saltoLinea);
			salida.append(this.identacion+"MOV CH, 0 " + saltoLinea); //inicializamos en false
	
			String result = crearAux(TIPO_AUX_ENTERO);
			salida.append(this.identacion+"MOV " + result + ", CX" + saltoLinea);
			terceto.setAux(result);
		}

	}
	
	private void and(Terceto terceto, FileWriter salida) throws IOException{
		String op1 = terceto.getOperando1();
		String op2 = terceto.getOperando2();
		int pos = Integer.parseInt(op1.replaceAll("\\D", ""));
		op1 = generador.getTerceto(pos).getAux(); //resultado condicion 1
		pos = Integer.parseInt(op2.replaceAll("\\D", ""));
		op2 = generador.getTerceto(pos).getAux(); //resultado condicion 1
		
		salida.append(this.identacion+"MOV CX, 0 " + saltoLinea); //inicializamos en false

		salida.append(this.identacion+"MOV AX, "+op1+ saltoLinea);
		salida.append(this.identacion+"AND AX, "+ op2 + saltoLinea); //AND
		salida.append(this.identacion+"CMP AX, 0" + saltoLinea); //compara el resultado con cero (false)
		
		salida.append(this.identacion+"SETNZ CL" + saltoLinea);
		salida.append(this.identacion+"MOV CH, 0 " + saltoLinea); //Me aseguro de que la parte alta sea cero.
		
		String result = crearAux(TIPO_AUX_ENTERO);
		salida.append(this.identacion+"MOV " + result+", CX"+ saltoLinea);
		terceto.setAux(result);
	}
	
	private void branchIncondicional(Terceto terceto, FileWriter salida) throws IOException {
		String etiqueta = terceto.getOperando2();
		salida.append(this.identacion+"JMP " + etiqueta + saltoLinea);

	}
	
	private void branchFalse(Terceto terceto, FileWriter salida) throws IOException {
		//el primer operando tiene la condicion
		//el segundo operando tiene la etiqueta
		String etiqueta = terceto.getOperando2();
		int pos = Integer.parseInt(terceto.getOperando1().replaceAll("\\D", ""));
		Terceto condicion = generador.getTerceto(pos);
		String resultCondicion = condicion.getAux();
		salida.append(this.identacion+"CMP " + resultCondicion + ", 0" + saltoLinea);
		salida.append(this.identacion+"JE " + etiqueta +saltoLinea); //salta si es igual a cero
	}
	
	private void asignacion(Terceto terceto, FileWriter salida) throws IOException{
		String operando2 = terceto.getOperando2();
		
		if (operando2.matches("\\[T\\d+\\]")) {
	        int pos = Integer.parseInt(operando2.replaceAll("\\D", ""));
	        operando2 = generador.getTerceto(pos).getAux();
	    }
		
		salida.append(this.identacion+"MOV AX, "+operando2 + saltoLinea);
		salida.append(this.identacion+"MOV "+terceto.getOperando1()+", AX" + saltoLinea);


	}
	
	private void asignacionPuntoFlotante(Terceto terceto, FileWriter salida) throws IOException{
		String operando2 = terceto.getOperando2();
		
		if (operando2.matches("\\[T\\d+\\]")) {
	        int pos = Integer.parseInt(operando2.replaceAll("\\D", ""));
	        operando2 = generador.getTerceto(pos).getAux();
	    }
		if (mapaSingles.containsKey(operando2)) {
			operando2 = mapaSingles.get(operando2);
		}
		
		
		salida.append(this.identacion+"FLD "+operando2 + saltoLinea);
		salida.append(this.identacion+"FST "+terceto.getOperando1() + saltoLinea);
	}
	
	private void impresion(Terceto terceto, FileWriter salida) throws IOException{
		String lexema = terceto.getOperando1();
		salida.append(this.identacion+"invoke StdOut, addr "+mapaCadenas.get(lexema)+saltoLinea);
	}

	private void conversionItoS(Terceto terceto, FileWriter salida) throws IOException {
		String operando = terceto.getOperando1();
		Integer pos;
		
		if (operando.matches("\\[T\\d+\\]")){
			pos = Integer.parseInt(operando.replaceAll("\\D", ""));
			operando = generador.getTerceto(pos).getAux();
		}
		
		String result = this.crearAux(TIPO_AUX_FLOAT);
		salida.append(this.identacion+"FILD "+operando+saltoLinea);
		
		salida.append(this.identacion+"FST "+result+saltoLinea);
		terceto.addAux(result);
	}
	
	private void conversionStoI(Terceto terceto, FileWriter salida) throws IOException {
			String operando = terceto.getOperando1();
			Integer pos;
			
			if (operando.matches("\\[T\\d+\\]")){
				pos = Integer.parseInt(operando.replaceAll("\\D", ""));
				operando = generador.getTerceto(pos).getAux();
			}else if(this.mapaSingles.containsKey(operando)) {
					operando = this.mapaSingles.get(operando);
			}	
			
			salida.append(this.identacion+"MOV EAX, "+operando+saltoLinea);
			salida.append(this.identacion+"CMP EAX, 0"+saltoLinea);
			salida.append(this.identacion+"JL ??errorConversionNegativo"+saltoLinea);
			
			salida.append(this.identacion+"FLD ["+operando+"]"+saltoLinea);
			
			String result = this.crearAux(TIPO_AUX_ENTERO);
			salida.append(this.identacion+"FISTP "+result+saltoLinea);
			
			terceto.setAux(result);
	}
	
	//En el mapero deberia preguntar si el booleano es true o false y pasar por paramentro el FileWriter
	public void traducir(Terceto t) throws IOException {
		// TODO El metodo debe tomar el terceto y mapear hacia que metodo de traduccion debe dirigirse
		String operador = t.getOperador();
		Integer tipo = t.getTipo();
		
		FileWriter salida;
		if(this.funcion) {
			salida=this.funciones;
		}else {
			salida=this.cuerpo;
		}
		
		if(tipo!=null && tipo == TIPO_ETIQUETA) {
			this.etiqueta(t,salida);

		}else if(tipo!=null && tipo == TIPO_FUNCION) {
			this.funcion(t);
		}else if (tipo!=null && tipo == T_SINGLE) { //FLOAT
			switch (operador) {
				case "+":
					this.sumaPuntoFlotante(t,salida);
					break;

				case "-":
					this.restaPuntoFlotante(t,salida);
					break;

				case "*":
					this.multiplicacionPuntoFlotante(t,salida);
					break;

				case "/":
					this.divisionPuntoFlotante(t,salida);
					break;

				case ":=":
					this.asignacionPuntoFlotante(t,salida);
					break;
			}
		}else{
			switch (operador) {
				case "+":
					this.suma(t,salida);
					break;
				case "-":
					this.resta(t,salida);
					break;
				case "*":
					this.multiplicacion(t,salida);
					break;
				case "/":
					this.division(t,salida);
					break;
				case ":=":
					this.asignacion(t,salida);
					break;
			}
		}

		//Verifico en caso de no ser una exp_arit.
		switch (operador) {
			case "SALIDA":
				this.impresion(t,salida);
				break;

			case "RETORNO":
				this.ret(t);
				break;

			case "CALL":
				this.call(t,salida);
				break;

			case "BI":
				this.branchIncondicional(t,salida);
				break;

			case "BF":
				this.branchFalse(t,salida);
				break;

			case "AND":
				this.and(t,salida);
				break;

			case "!=":
				this.distinto(t,salida);
				break;

			case ">":
				this.mayor(t,salida);
				break;

			case ">=":
				this.mayorIgual(t,salida);
				break;

			case "<":
				this.menor(t,salida);
				break;

			case "<=":
				this.menorIgual(t,salida);
				break;

			case "=":
				this.igual(t,salida);
				break;
				
			case "itoS":
				this.conversionItoS(t,salida);
				break;
			
			case "stoI":
				this.conversionStoI(t,salida);
				break;

		}
		
		salida.append(saltoLinea);
	}
}
