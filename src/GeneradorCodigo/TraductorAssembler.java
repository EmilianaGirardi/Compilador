package GeneradorCodigo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class TraductorAssembler {
	
	private String path;
	private FileWriter salida;
	private Integer numAux;

	private Integer numCadena;
	private HashMap<String, String> mapaCadenas; //key: lexema; valor: etiqueta del .data
	
	private Generador generador;

	private static String saltoLinea = "\r\n";

	public TraductorAssembler(String archivoSalida) throws IOException {
		this.path = archivoSalida;
		this.salida = new FileWriter(path, false);
		this.numAux=0;
		this.numCadena = 0;
		this.generador = Generador.getInstance();
		this.mapaCadenas = new HashMap<String, String>();
	}

	public void addCadena(String lexema) throws IOException {
		if(!mapaCadenas.containsKey(lexema)) {
			String etq = "_cadena"+this.numCadena+"_";
			this.numCadena++;
			
			mapaCadenas.put(lexema, etq);
			
			salida.append(etq+" DB "+ "\""+lexema+"\", 10, 0");
		}
	}
	
	private String crearAux() {
		numAux++;
		return "@aux"+numAux;
	}

	public void cerrarTraduccion() throws IOException {
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
	//TODO verificar si el formato de la constante single es el correcto para assembler
	private void sumaPuntoFlotante(Terceto terceto) throws IOException {
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

		salida.append("MOV ST, " + op1 + saltoLinea);
		salida.append("ADD ST, " + op2 + saltoLinea);
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
		op2 = terceto.getOperando2();
		if (op2.matches("\\[T\\d+\\]")){
			pos = Integer.parseInt(op2.replaceAll("\\D", ""));
			op2 = generador.getTerceto(pos).getAux();
		}

		result = crearAux();

		salida.append("MOV ST, " + op1 + saltoLinea);
		salida.append("SUB ST, " + op2 + saltoLinea);
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
		op2 = terceto.getOperando2();
		if (op2.matches("\\[T\\d+\\]")){
			pos = Integer.parseInt(op2.replaceAll("\\D", ""));
			op2 = generador.getTerceto(pos).getAux();
		}

		result = crearAux();

		salida.append("MOV ST, " + op1 + saltoLinea);
		salida.append("MUL ST, " + op2 + saltoLinea);
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
		op2 = terceto.getOperando2();
		if (op2.matches("\\[T\\d+\\]")){
			pos = Integer.parseInt(op2.replaceAll("\\D", ""));
			op2 = generador.getTerceto(pos).getAux();
		}

		result = crearAux();

		salida.append("MOV ST, " + op1 + saltoLinea);
		salida.append("DIV ST, " + op2 + saltoLinea);
		salida.append("MOV " + result + ", ST" + saltoLinea);

		terceto.setAux(result);
	}

	//NAGU
	private void etiqueta(Terceto terceto) throws IOException {
		String result = this.crearAux();
		this.salida.append("MOV "+result + saltoLinea);
		terceto.addAux(result);
	}

	//NAGU
	private void call(Terceto terceto) throws IOException {
		String parametro = terceto.getOperando2();

	    if (parametro.matches("\\[T\\d+\\]")) {
	        int pos = Integer.parseInt(parametro.replaceAll("\\D", ""));
	        parametro = generador.getTerceto(pos).getAux();
	    }
	    
	    salida.append("PUSH " + parametro + saltoLinea);
	    salida.append("CALL " + terceto.getOperando1() + saltoLinea);  
	    
	}

	//NAGU
	private void ret(Terceto terceto) throws IOException {
		String retorno = terceto.getOperando1();
		
		if (retorno.matches("\\[T\\d+\\]")) {
	        int pos = Integer.parseInt(retorno.replaceAll("\\D", ""));
	        retorno = generador.getTerceto(pos).getAux();
	    }	
		
		String result = this.crearAux();
		salida.append("MOV AX,"+retorno+ saltoLinea);
		salida.append("MOV "+result+", AX"+ saltoLinea);
		
		terceto.addAux(result);
	}


	//EMI
	private void mayorIgual(Terceto terceto) throws IOException{

	}

	//EMI
	private void mayor(Terceto terceto) throws IOException{

	}

	//EMI
	private void menorIgual(Terceto terceto) throws IOException{

	}

	//EMI
	private void menor(Terceto terceto) throws IOException{

	}

	//NAGU
	private void igual(Terceto terceto) throws IOException{

	}

	//NAGU
	private void and(Terceto terceto) throws IOException{

	}

	//EMI
	private void branchIncondicional(Terceto terceto) throws IOException {
		//el tercer operando tiene la etiqueta
	}

	//EMI
	private void branchFalse(Terceto terceto) throws IOException {
		//el segundo operando tienen la condicion
		//el tercer operando tiene la etiqueta
	}

	//EMI
	private void branchTrue(Terceto terceto) throws IOException {
		//el segundo operando tienen la condicion
		//el tercer operando tiene la etiqueta
	}

	//NAGU
	private void asignacion(Terceto terceto) throws IOException{
		String operando2 = terceto.getOperando2();
		
		if (operando2.matches("\\[T\\d+\\]")) {
	        int pos = Integer.parseInt(operando2.replaceAll("\\D", ""));
	        operando2 = generador.getTerceto(pos).getAux();
	    }
		
		String result = this.crearAux();
		
		salida.append("MOV AX, "+operando2 + saltoLinea);
		salida.append("MOV "+terceto.getOperando1()+", AX" + saltoLinea);
	}

	//NAGU
	private void asignacionPuntoFlotante(Terceto terceto) throws IOException{
		String operando2 = terceto.getOperando2();
		
		if (operando2.matches("\\[T\\d+\\]")) {
	        int pos = Integer.parseInt(operando2.replaceAll("\\D", ""));
	        operando2 = generador.getTerceto(pos).getAux();
	    }
		
		String result = this.crearAux();
		
		salida.append("MOV ST, "+operando2 + saltoLinea);
		salida.append("MOV "+terceto.getOperando1()+", ST" + saltoLinea);
	}

	//AMBOS DOS
	private void impresion(Terceto terceto) throws IOException{
		String lexema = terceto.getOperando1();
		salida.append("invoke StdOut, addr "+mapaCadenas.get(lexema));
	}

	
	public void traducir(Terceto t) throws IOException {
		// TODO El metodo debe tomar el terceto y mapear hacia que metodo de traduccion debe dirigirse
		String operador = t.getOperador();
		Integer tipo = t.getTipo();
		if (tipo == 2) {
			switch (operador) {
				case "+":
					this.sumaPuntoFlotante(t);
					break;
				case "-":
					this.restaPuntoFlotante(t);
					break;
			}
		}
		else{
			switch (operador) {
				case "+":
					this.suma(t);
					break;
				case "-":
					this.resta(t);
					break;
			}
		}
	}

}
