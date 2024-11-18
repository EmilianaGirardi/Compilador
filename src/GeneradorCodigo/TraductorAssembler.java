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
	private FileWriter salida;
	private FileWriter assembler;
	
	
	private Integer numAux;

	private Integer numCadena;
	private Integer numFloat;
	private HashMap<String, String> mapaCadenas; //key: lexema; valor: etiqueta del .data
	private HashMap<String, String> mapaSingles; //key: cte; valor: etiqueta del .data
	private Generador generador;
	private Lexico lexico;

	private static Integer TIPO_AUX_ENTERO = 10;
	private static Integer TIPO_AUX_FLOAT = 11;
	
	private static String saltoLinea = "\r\n";
	private String identacion;

	public TraductorAssembler(String archivoSalida) throws IOException {
		this.path = archivoSalida;
		
		this.assembler = new FileWriter(path, false);
		
		this.salida  = new FileWriter("src/GeneradorCodigo/salida.txt",false);
		this.encabezado= new FileWriter("src/GeneradorCodigo/encabezado.txt",false);
		
		this.numAux = 0;
		this.numCadena = 0;
		this.numFloat = 0;
		this.generador = Generador.getInstance();
		this.lexico = Lexico.getInstance();
		this.mapaCadenas = new HashMap<String, String>();
		this.mapaSingles = new HashMap<String, String>();
		
		this.identacion = "";
	}

	public void inicializarAssembler() throws IOException {
		encabezado.append(".MODEL flat, stdcall\n" +
				"option casemap :none\n" +
				"include \\masm32\\include\\masm32rt.inc\n" +
				"includelib \\masm32\\lib\\kernel32.lib\n" +
				"includelib \\masm32\\lib\\masm32.lib" + saltoLinea);
		encabezado.append(".STACK 200h" + saltoLinea);
		encabezado.append(saltoLinea);
		encabezado.append(".DATA" + saltoLinea);
		encabezado.append("errorMsgOverflow db \"Overflow: en suma!.\", 10, 0 ;"+saltoLinea);
		encabezado.append("errorMsgConversionNegativa db \"Conversion invalida: no puede convertir un Single negativo a Entero!.\", 10, 0 ;"+saltoLinea);
		
		//mapeo de variables y cadenas
		TablaSimbolos TS = lexico.getTablaSimbolos();
		for (String lexema : TS.getMap().keySet()){
			if (TS.getToken(lexema) == 258 && TS.getTipo(lexema) !=50){ //IDENTIFICADORES
				
				if (TS.getTipo(lexema) == 2){ //tipo single (32 bits)
					encabezado.append(lexema + " DD ? " +saltoLinea);
				}
				else { //unsigned y octal de 16 bits
					encabezado.append(lexema + " DW ? " + saltoLinea);
				}
			}
			if (TS.getToken(lexema) == 265){ //MULTILINEA
				addCadena(lexema);
			}
			if (TS.getToken(lexema)== 262) { //ctes single
				addFloat(lexema);
			}
			
			if(TS.getTipo(lexema)==TIPO_AUX_ENTERO) {
				encabezado.append(lexema + " DW ? "+ saltoLinea);
			}else if(TS.getTipo(lexema)==TIPO_AUX_FLOAT) {
				encabezado.append(lexema + " DD ? "+ saltoLinea);
			}
		}
		encabezado.append(saltoLinea);
		encabezado.append(".CODE" + saltoLinea);
		encabezado.append("START:" + saltoLinea);
		
	}

	public void addCadena(String lexema) throws IOException {
		if(!mapaCadenas.containsKey(lexema)) {
			String etq = "_cadena"+this.numCadena+"_";
			this.numCadena++;
			
			mapaCadenas.put(lexema, etq);
			
			salida.append(etq+" DB "+ "\""+lexema+"\", 10, 0" + saltoLinea);
		}
	}
	
	public void addFloat(String cte) throws IOException {
		if(!mapaSingles.containsKey(cte)) {
			String etq = "_float"+this.numFloat+"_";
			this.numFloat++;
			
			mapaSingles.put(cte, etq);
			
			salida.append(etq+" DD "+ cte + saltoLinea);
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
		salida.append("??OVERFLOW_ERROR:"+saltoLinea);
		salida.append("invoke StdOut, addr errorMsgOverflow"+saltoLinea);
		salida.append("??errorConversionNegativo");
		salida.append("invoke StdOut, addr errorMsgConversionNegativa"+saltoLinea);
		
		salida.append("END START");
		
		
		this.inicializarAssembler();
		
		cargarArchivo("/GeneradorCodigo/encabezado.txt", this.assembler);
		cargarArchivo("/GeneradorCodigo/salida.txt", this.assembler);
		
		
		this.encabezado.close();
		this.salida.close(); 
		this.assembler.close();
		
	}

    private String cargarArchivo(String pathArchivoOrigen, FileWriter archivoDestino) throws IOException {
        StringBuilder contenido = new StringBuilder();
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
        return contenido.toString();
    }
	
	private void suma(Terceto terceto) throws IOException {
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
		

		salida.append("MOV AX, " + op1 + saltoLinea);
		salida.append("ADD AX, " + op2 + saltoLinea);
		salida.append("JC ??OVERFLOW_ERROR");
		salida.append("MOV " + result + ", AX" + saltoLinea);
		

		terceto.setAux(result);
	}

	private void resta(Terceto terceto) throws IOException {
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

		salida.append("MOV AX, " + op1 + saltoLinea);
		salida.append("SUB AX, " + op2 + saltoLinea);
		salida.append("MOV " + result + ", AX" + saltoLinea);

		terceto.setAux(result);
	}
	
	private void multiplicacion(Terceto terceto) throws IOException {
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

		salida.append("MOV AX, " + op1 + saltoLinea);
		salida.append("MUL AX, " + op2 + saltoLinea);
		salida.append("MOV " + result + ", AX" + saltoLinea);

		terceto.setAux(result);
	}
	
	private void division(Terceto terceto) throws IOException {
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

		salida.append("MOV AX, " + op1 + saltoLinea);
		salida.append("DIV AX, " + op2 + saltoLinea);
		salida.append("MOV " + result + ", AX" + saltoLinea);

		terceto.setAux(result);
		
	}

	//Punto Flotante
	private void sumaPuntoFlotante(Terceto terceto) throws IOException {
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

		salida.append("FLD "+ op1 + saltoLinea);
		salida.append("FADD ST, " + op2 + saltoLinea);
		salida.append("FST " + result + saltoLinea);

		terceto.setAux(result);
	}

	private void restaPuntoFlotante(Terceto terceto) throws IOException {
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

		salida.append("FLD "+ op1 + saltoLinea);
		salida.append("FSUB ST, " + op2 + saltoLinea);
		salida.append("FST " + result + saltoLinea);

		terceto.setAux(result);
	}

	private void multiplicacionPuntoFlotante(Terceto terceto) throws IOException {
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

		salida.append("FLD "+ op1 + saltoLinea);
		salida.append("FMUL ST, " + op2 + saltoLinea);
		salida.append("FST " + result + saltoLinea);

		terceto.setAux(result);
	}

	private void divisionPuntoFlotante(Terceto terceto) throws IOException {
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

		
		salida.append("FLD " + op1 + saltoLinea);
		salida.append("FDIV ST, " + op2 + saltoLinea);
		salida.append("FST " + result + saltoLinea);

		terceto.setAux(result);
	}

	private void etiqueta(Terceto terceto) throws IOException {
		this.salida.append(terceto.getOperador()+":" + saltoLinea);
	}

	private void call(Terceto terceto) throws IOException {
		String parametro = terceto.getOperando2();

	    if (parametro.matches("\\[T\\d+\\]")) {
	        int pos = Integer.parseInt(parametro.replaceAll("\\D", ""));
	        parametro = generador.getTerceto(pos).getAux();
	    }
	    
	    salida.append("PUSH " + parametro + saltoLinea);
	    salida.append("CALL " + terceto.getOperando1() + saltoLinea);  
	    
	}

	private void ret(Terceto terceto) throws IOException {
		String retorno = terceto.getOperando1();
		Integer tipo= terceto.getTipo();;

		if (retorno.matches("\\[T\\d+\\]")) {
			int pos = Integer.parseInt(retorno.replaceAll("\\D", ""));
			retorno = generador.getTerceto(pos).getAux();
		}

		String result;
		
		if(tipo == 2) {
			result = this.crearAux(TIPO_AUX_FLOAT);
			salida.append("FLD "+retorno+ saltoLinea);
			salida.append("FST "+result+ saltoLinea);
			salida.append("RET"+ saltoLinea);
		}else {
			result = this.crearAux(TIPO_AUX_ENTERO);
			salida.append("MOV AX,"+retorno+ saltoLinea);
			salida.append("MOV "+result+", AX"+ saltoLinea);
			salida.append("RET"+ saltoLinea);
		}
		
		terceto.addAux(result);
	}

	private void mayorIgual(Terceto terceto) throws IOException{
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

		if (terceto.getTipo() == 2){
			salida.append("MOV CX, 0 " + saltoLinea); //inicializamos en false
			salida.append("FLD " + op1 + saltoLinea);  // Cargar op1 en ST(0)
    		salida.append("FCOMI  " + op2 + saltoLinea);  // Comparar ST(0) con op2
			salida.append("SETGE  CL" + saltoLinea);
			//En este punto, CL contendrá:
			//1 si op1 <= op2 (verdadero)
			//0 si op1 > op2 (falso)

			String result = crearAux(TIPO_AUX_ENTERO);
			salida.append("MOV " + result + ", CL" + saltoLinea);
			terceto.setAux(result);
		} else{
			
					salida.append("MOV CX, 0 " + saltoLinea); //inicializamos en false
					salida.append("MOV " + op1 + ", AX" + saltoLinea);
					salida.append("CMP AX, " + op2 + saltoLinea);
					salida.append("SETAE CL" + saltoLinea);
					//En este punto, CL contendrá:
					//1 si op1 <= op2 (verdadero)
					//0 si op1 > op2 (falso)
			
					String result = crearAux(TIPO_AUX_ENTERO);
					salida.append("MOV " + result + ", CL" + saltoLinea);
					terceto.setAux(result);
		
		}
		
	}

	private void mayor(Terceto terceto) throws IOException{
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

		if (terceto.getTipo() == 2){
			salida.append("MOV CX, 0 " + saltoLinea); //inicializamos en false
			salida.append("FLD " + op1 + saltoLinea);  // Cargar op1 en ST(0)
    		salida.append("FCOMI  " + op2 + saltoLinea);  // Comparar ST(0) con op2
			salida.append("SETG  CL" + saltoLinea);

			String result = crearAux(TIPO_AUX_ENTERO);
			salida.append("MOV " + result + ", CL" + saltoLinea);
			terceto.setAux(result);
		}else{

			salida.append("MOV CX, 0 " + saltoLinea); //inicializamos en false
			salida.append("MOV " + op1 + ", AX" + saltoLinea);
			salida.append("CMP AX, " + op2 + saltoLinea);
			salida.append("SETA CL" + saltoLinea);

			String result = crearAux(TIPO_AUX_ENTERO);
			salida.append("MOV " + result + ", CL" + saltoLinea);
			terceto.setAux(result);
		}

	}

	private void menorIgual(Terceto terceto) throws IOException{
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

		if (terceto.getTipo() == 2){
			salida.append("MOV CX, 0 " + saltoLinea); //inicializamos en false
			salida.append("FLD " + op1 + saltoLinea);  // Cargar op1 en ST(0)
    		salida.append("FCOMI  " + op2 + saltoLinea);  // Comparar ST(0) con op2
			salida.append("SETLE  CL" + saltoLinea);

			String result = crearAux(TIPO_AUX_ENTERO);
			salida.append("MOV " + result + ", CL" + saltoLinea);
			terceto.setAux(result);
		}else{
			salida.append("MOV CX, 0 " + saltoLinea); //inicializamos en false
			salida.append("MOV " + op1 + ", AX" + saltoLinea);
			salida.append("CMP AX, " + op2 + saltoLinea);
			salida.append("SETBE CL" + saltoLinea);
	
			String result = crearAux(TIPO_AUX_ENTERO);
			salida.append("MOV " + result + ", CL" + saltoLinea);
			terceto.setAux(result);

		}

	}

	private void menor(Terceto terceto) throws IOException{
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

		if (terceto.getTipo() == 2){
			salida.append("MOV CX, 0 " + saltoLinea); //inicializamos en false
			salida.append("FLD " + op1 + saltoLinea);  // Cargar op1 en ST(0)
    		salida.append("FCOMI  " + op2 + saltoLinea);  // Comparar ST(0) con op2
			salida.append("SETL  CL" + saltoLinea);

			String result = crearAux(TIPO_AUX_ENTERO);
			salida.append("MOV " + result + ", CL" + saltoLinea);
			terceto.setAux(result);
		}else{

			salida.append("MOV CX, 0 " + saltoLinea); //inicializamos en false
			salida.append("MOV " + op1 + ", AX" + saltoLinea);
			salida.append("CMP AX, " + op2 + saltoLinea);
			salida.append("SETB CL" + saltoLinea);
	
			String result = crearAux(TIPO_AUX_ENTERO);
			salida.append("MOV " + result + ", CL" + saltoLinea);
			terceto.setAux(result);
		}

	}

	private void igual(Terceto terceto) throws IOException{
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

		if (terceto.getTipo() == 2){
			salida.append("MOV CX, 0 " + saltoLinea); //inicializamos en false
			salida.append("FLD " + op1 + saltoLinea);  // Cargar op1 en ST(0)
    		salida.append("FCOMI  " + op2 + saltoLinea);  // Comparar ST(0) con op2
			salida.append("SETZ  CL" + saltoLinea);

			String result = crearAux(TIPO_AUX_ENTERO);
			salida.append("MOV " + result + ", CL" + saltoLinea);
			terceto.setAux(result);
		}else{
			salida.append("MOV CX, 0 " + saltoLinea); //inicializamos en false
			salida.append("MOV " + op1 + ", AX" + saltoLinea);
			salida.append("CMP AX, " + op2 + saltoLinea);
			salida.append("SETE CL" + saltoLinea);

			String result = crearAux(TIPO_AUX_ENTERO);
			salida.append("MOV " + result + ", CL" + saltoLinea);
			terceto.setAux(result);

		}
	}

	private void distinto(Terceto terceto) throws IOException{
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

		if (terceto.getTipo() == 2){
			salida.append("MOV CX, 0 " + saltoLinea); //inicializamos en false
			salida.append("FLD " + op1 + saltoLinea);  // Cargar op1 en ST(0)
    		salida.append("FCOMI  " + op2 + saltoLinea);  // Comparar ST(0) con op2
			salida.append("SETNZ  CL" + saltoLinea);

			String result = crearAux(TIPO_AUX_ENTERO);
			salida.append("MOV " + result + ", CL" + saltoLinea);
			terceto.setAux(result);
		}else{
			salida.append("MOV CX, 0 " + saltoLinea); //inicializamos en false
			salida.append("MOV " + op1 + ", AX" +saltoLinea);
			salida.append("CMP AX, " + op2 + saltoLinea);
			salida.append("SETNE CL" + saltoLinea);
	
			String result = crearAux(TIPO_AUX_ENTERO);
			salida.append("MOV " + result + ", CL" + saltoLinea);
			terceto.setAux(result);
		}

	}
	private void and(Terceto terceto) throws IOException{
		String op1 = terceto.getOperando1();
		String op2 = terceto.getOperando2();
		int pos = Integer.parseInt(op1.replaceAll("\\D", ""));
		op1 = generador.getTerceto(pos).getAux(); //resultado condicion 1
		pos = Integer.parseInt(op2.replaceAll("\\D", ""));
		op2 = generador.getTerceto(pos).getAux(); //resultado condicion 1

		salida.append(S"MOV " + op1 + ", AX" + saltoLinea);
		salida.append("AND AX, "+ op2 + saltoLinea); //AND
		salida.append("CMP AX, 0" + saltoLinea); //compara el resultado con cero (false)

		String result = crearAux(TIPO_AUX_ENTERO);
		salida.append("SETNZ " + result + saltoLinea);
		terceto.setAux(result);
	}
	private void branchIncondicional(Terceto terceto) throws IOException {
		String etiqueta = terceto.getOperando2();
		salida.append("JMP " + etiqueta + saltoLinea);

	}
	private void branchFalse(Terceto terceto) throws IOException {
		//el primer operando tiene la condicion
		//el segundo operando tiene la etiqueta
		String etiqueta = terceto.getOperando2();
		int pos = Integer.parseInt(terceto.getOperando1().replaceAll("\\D", ""));
		Terceto condicion = generador.getTerceto(pos);
		String resultCondicion = condicion.getAux();
		salida.append("CMP " + resultCondicion + ", 0" + saltoLinea);
		salida.append("JE " + etiqueta +saltoLinea); //salta si es igual a cero
	}
	private void branchTrue(Terceto terceto) throws IOException {
		//el primer operando tiene la condicion
		//el segundo operando tiene la etiqueta
		String etiqueta = terceto.getOperando2();
		int pos = Integer.parseInt(terceto.getOperando1().replaceAll("\\D", ""));
		Terceto condicion = generador.getTerceto(pos);
		String resultCondicion = condicion.getAux();
		salida.append("CMP " + resultCondicion + ", 0" + saltoLinea);
		salida.append("JNZ " + etiqueta +saltoLinea); //salta si no es cero
	}
	private void asignacion(Terceto terceto) throws IOException{
		String operando2 = terceto.getOperando2();
		
		if (operando2.matches("\\[T\\d+\\]")) {
	        int pos = Integer.parseInt(operando2.replaceAll("\\D", ""));
	        operando2 = generador.getTerceto(pos).getAux();
	    }
		
		salida.append("MOV AX, "+operando2 + saltoLinea);
		salida.append("MOV "+terceto.getOperando1()+", AX" + saltoLinea);


	}
	private void asignacionPuntoFlotante(Terceto terceto) throws IOException{
		String operando2 = terceto.getOperando2();
		
		if (operando2.matches("\\[T\\d+\\]")) {
	        int pos = Integer.parseInt(operando2.replaceAll("\\D", ""));
	        operando2 = generador.getTerceto(pos).getAux();
	    }
		if (mapaSingles.containsKey(operando2)) {
			operando2 = mapaSingles.get(operando2);
		}
		
		
		salida.append("MOV ST, "+operando2 + saltoLinea);
		salida.append("MOV "+terceto.getOperando1()+", ST" + saltoLinea);
	}
	private void impresion(Terceto terceto) throws IOException{
		String lexema = terceto.getOperando1();
		salida.append("invoke StdOut, addr "+mapaCadenas.get(lexema));
	}

	private void conversionItoS(Terceto terceto) throws IOException {
		String operando = terceto.getOperando1();
		Integer pos;
		
		if (operando.matches("\\[T\\d+\\]")){
			pos = Integer.parseInt(operando.replaceAll("\\D", ""));
			operando = generador.getTerceto(pos).getAux();
		}
		
		String result = this.crearAux(TIPO_AUX_FLOAT);
		salida.append("FILD "+operando+saltoLinea);
		
		salida.append("MOV "+result+", ST"+saltoLinea);
		terceto.addAux(result);
		
		/*salida.append("MOV BX, " + operando);
		salida.append("MOV ECX, 0")	;
		salida.append("MOV CX, BX");
		salida.append("MOV EBX, ECX");*/
	}
	
	private void conversionStoI(Terceto terceto) throws IOException {
			String operando = terceto.getOperando1();
			Integer pos;
			
			if (operando.matches("\\[T\\d+\\]")){
				pos = Integer.parseInt(operando.replaceAll("\\D", ""));
				operando = generador.getTerceto(pos).getAux();
			}else if(this.mapaSingles.containsKey(operando)) {
					operando = this.mapaSingles.get(operando);
			}	
			
			this.salida.append("MOV EAX, "+operando+saltoLinea);
			this.salida.append("CMP EAX, 0");
			this.salida.append("JL ??errorConversionNegativo"+saltoLinea);
			
			this.salida.append("FLD dword["+operando+"]"+saltoLinea);
			
			String result = this.crearAux(TIPO_AUX_ENTERO);
			this.salida.append("FISTP "+result+saltoLinea);
			
			terceto.setAux(result);
	}
	
	public void traducir(Terceto t) throws IOException {
		// TODO El metodo debe tomar el terceto y mapear hacia que metodo de traduccion debe dirigirse
		String operador = t.getOperador();
		Integer tipo = t.getTipo();

		if(tipo!=null && tipo == 8) {
			this.etiqueta(t);

		}else if (tipo!=null && tipo == 2) { //FLOAT
			switch (operador) {
				case "+":
					this.sumaPuntoFlotante(t);
					break;

				case "-":
					this.restaPuntoFlotante(t);
					break;

				case "*":
					this.multiplicacionPuntoFlotante(t);
					break;

				case "/":
					this.divisionPuntoFlotante(t);
					break;

				case ":=":
					this.asignacionPuntoFlotante(t);
					break;
			}
		}else{
			switch (operador) {
				case "+":
					this.suma(t);
					break;
				case "-":
					this.resta(t);
					break;
				case "*":
					this.multiplicacion(t);
					break;
				case "/":
					this.division(t);
					break;
				case ":=":
					this.asignacion(t);
					break;
			}
		}

		//Verifico en caso de no ser una exp_arit.
		switch (operador) {
			case "SALIDA":
				this.impresion(t);
				break;

			case "RETORNO":
				this.ret(t);
				break;

			case "CALL":
				this.call(t);
				break;

			case "BI":
				this.branchIncondicional(t);
				break;

			case "BF":
				this.branchFalse(t);
				break;

			case "BT":
				this.branchTrue(t);
				break;

			case "AND":
				this.and(t);
				break;

			case "!=":
				this.distinto(t);
				break;

			case ">":
				this.mayor(t);
				break;

			case ">=":
				this.mayorIgual(t);
				break;

			case "<":
				this.menor(t);
				break;

			case "<=":
				this.menorIgual(t);
				break;

			case "=":
				this.igual(t);
				break;
				
			case "itoS":
				this.conversionItoS(t);
				break;
			
			case "stoI":
				this.conversionStoI(t);
				break;

		}


	}
}
