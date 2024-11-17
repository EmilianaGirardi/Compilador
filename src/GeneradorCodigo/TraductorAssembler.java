package GeneradorCodigo;

import AnalizadorLexico.Lexico;
import AnalizadorLexico.TablaSimbolos;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class TraductorAssembler {
	
	private String path;
	private FileWriter salida;
	private Integer numAux;

	private Integer numCadena;
	private Integer numFloat;
	private HashMap<String, String> mapaCadenas; //key: lexema; valor: etiqueta del .data
	private HashMap<String, String> mapaSingles; //key: cte; valor: etiqueta del .data
	private Generador generador;
	private Lexico lexico;

	private static String saltoLinea = "\r\n";

	public TraductorAssembler(String archivoSalida) throws IOException {
		this.path = archivoSalida;
		this.salida = new FileWriter(path, false);
		this.numAux = 0;
		this.numCadena = 0;
		this.numFloat = 0;
		this.generador = Generador.getInstance();
		this.lexico = Lexico.getInstance();
		this.mapaCadenas = new HashMap<String, String>();
		this.mapaSingles = new HashMap<String, String>();
	}

	public void inicializarAssembler() throws IOException {
		salida.append(".MODEL flat, stdcall\n" +
				"option casemap :none\n" +
				"include \\masm32\\include\\masm32rt.inc\n" +
				"includelib \\masm32\\lib\\kernel32.lib\n" +
				"includelib \\masm32\\lib\\masm32.lib" + saltoLinea);
		salida.append(".STACK 200h" + saltoLinea);
		salida.append(saltoLinea);
		salida.append(".DATA" + saltoLinea);
		//mapeo de variables y cadenas
		TablaSimbolos TS = lexico.getTablaSimbolos();
		for (String lexema : TS.getMap().keySet()){
			if (TS.getToken(lexema) == 258 && TS.getTipo(lexema) !=50){ //IDENTIFICADORES
				
				if (TS.getTipo(lexema) == 2){ //tipo single (32 bits)
					salida.append(lexema + " DD ? " +saltoLinea);
				}
				else { //unsigned y octal de 16 bits
					salida.append(lexema + " DW ? " + saltoLinea);
				}
			}
			if (TS.getToken(lexema) == 265){ //MULTILINEA
				addCadena(lexema);
			}
			if (TS.getToken(lexema)== 262) { //ctes single
				addFloat(lexema);
			}
		}
		salida.append(saltoLinea);
		salida.append(".CODE" + saltoLinea);
		salida.append("START:" + saltoLinea);
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
	
	private String crearAux() {
		numAux++;
		return "@aux"+numAux;
	}

	public void cerrarTraduccion() throws IOException {
		salida.append("END START");
		this.salida.close();
	}

	private void suma(Terceto terceto) throws IOException {
		//MOV R1, _a
		//ADD R1, @aux1
		//MOV @aux2, R1

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

		result = crearAux();

		salida.append("MOV AX, " + op1 + saltoLinea);
		salida.append("ADD AX, " + op2 + saltoLinea);
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

		result = crearAux();

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

		result = crearAux();

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

		result = crearAux();

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

		result = crearAux();

		salida.append("MOV ST, " + op1 + saltoLinea);
		salida.append("FADD ST, " + op2 + saltoLinea);
		salida.append("MOV " + result + ", ST" + saltoLinea);

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

		result = crearAux();

		salida.append("MOV ST, " + op1 + saltoLinea);
		salida.append("FSUB ST, " + op2 + saltoLinea);
		salida.append("MOV " + result + ", ST" + saltoLinea);

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

		result = crearAux();

		salida.append("MOV ST, " + op1 + saltoLinea);
		salida.append("FMUL ST, " + op2 + saltoLinea);
		salida.append("MOV " + result + ", ST" + saltoLinea);

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

		result = crearAux();

		salida.append("MOV ST, " + op1 + saltoLinea);
		salida.append("FDIV ST, " + op2 + saltoLinea);
		salida.append("MOV " + result + ", ST" + saltoLinea);

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

		if (retorno.matches("\\[T\\d+\\]")) {
			int pos = Integer.parseInt(retorno.replaceAll("\\D", ""));
			retorno = generador.getTerceto(pos).getAux();
		}

		String result = this.crearAux();
		salida.append("MOV AX,"+retorno+ saltoLinea);
		salida.append("MOV "+result+", AX"+ saltoLinea);
		salida.append("RET"+ saltoLinea);

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

		salida.append("MOV CX, 0 " + saltoLinea); //inicializamos en false
		salida.append("MOV " + op1 + ", AX" + saltoLinea);
		salida.append("CMP AX, " + op2 + saltoLinea);
		salida.append("SETGE CL" + saltoLinea);
		//En este punto, CL contendrá:
		//1 si op1 <= op2 (verdadero)
		//0 si op1 > op2 (falso)

		String result = crearAux();
		salida.append("MOV " + result + ", CL" + saltoLinea);
		terceto.setAux(result);
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

		salida.append("MOV CX, 0 " + saltoLinea); //inicializamos en false
		salida.append("MOV " + op1 + ", AX" + saltoLinea);
		salida.append("CMP AX, " + op2 + saltoLinea);
		salida.append("SETG CL" + saltoLinea);
		//En este punto, CL contendrá:
		//1 si op1 <= op2 (verdadero)
		//0 si op1 > op2 (falso)

		String result = crearAux();
		salida.append("MOV " + result + ", CL" + saltoLinea);
		terceto.setAux(result);
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

		salida.append("MOV CX, 0 " + saltoLinea); //inicializamos en false
		salida.append("MOV " + op1 + ", AX" + saltoLinea);
		salida.append("CMP AX, " + op2 + saltoLinea);
		salida.append("SETLE CL" + saltoLinea);
		//En este punto, CL contendrá:
		//1 si op1 <= op2 (verdadero)
		//0 si op1 > op2 (falso)

		String result = crearAux();
		salida.append("MOV " + result + ", CL" + saltoLinea);
		terceto.setAux(result);
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

		salida.append("MOV CX, 0 " + saltoLinea); //inicializamos en false
		salida.append("MOV " + op1 + ", AX" + saltoLinea);
		salida.append("CMP AX, " + op2 + saltoLinea);
		salida.append("SETL CL" + saltoLinea);
		//En este punto, CL contendrá:
		//1 si op1 < op2 (verdadero)
		//0 si op1 >= op2 (falso)

		String result = crearAux();
		salida.append("MOV " + result + ", CL" + saltoLinea);
		terceto.setAux(result);
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

		salida.append("MOV CX, 0 " + saltoLinea); //inicializamos en false
		salida.append("MOV " + op1 + ", AX" + saltoLinea);
		salida.append("CMP AX, " + op2 + saltoLinea);
		salida.append("SETE CL" + saltoLinea);
		//En este punto, CL contendrá:
		//1 si op1 < op2 (verdadero)
		//0 si op1 >= op2 (falso)

		String result = crearAux();
		salida.append("MOV " + result + ", CL" + saltoLinea);
		terceto.setAux(result);

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

		salida.append("MOV CX, 0 " + saltoLinea); //inicializamos en false
		salida.append("MOV " + op1 + ", AX" +saltoLinea);
		salida.append("CMP AX, " + op2 + saltoLinea);
		salida.append("SETNE CL" + saltoLinea);
		//En este punto, CL contendrá:
		//1 si op1 < op2 (verdadero)
		//0 si op1 >= op2 (falso)

		String result = crearAux();
		salida.append("MOV " + result + ", CL" + saltoLinea);
		terceto.setAux(result);

	}
	private void and(Terceto terceto) throws IOException{
		String op1 = terceto.getOperando1();
		String op2 = terceto.getOperando2();
		int pos = Integer.parseInt(op1.replaceAll("\\D", ""));
		op1 = generador.getTerceto(pos).getAux(); //resultado condicion 1
		pos = Integer.parseInt(op2.replaceAll("\\D", ""));
		op2 = generador.getTerceto(pos).getAux(); //resultado condicion 1

		salida.append("MOV " + op1 + ", AX" + saltoLinea);
		salida.append("AND AX, "+ op2 + saltoLinea); //AND
		salida.append("CMP AX, 0" + saltoLinea); //compara el resultado con cero (false)

		String result = crearAux();
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

		}


	}
}
