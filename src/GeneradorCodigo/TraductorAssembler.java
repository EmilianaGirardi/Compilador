package GeneradorCodigo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TraductorAssembler {
	
	private String path;
	private FileWriter salida;
	private Integer numAux;

	private Generador generador;

	private static String saltoLinea = "\r\n";

	public TraductorAssembler(String archivoSalida) throws IOException {
		this.path = archivoSalida;
		this.salida = new FileWriter(path, false);
		this.numAux=0;
		this.generador = Generador.getInstance();
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

		salida.append("MOV AX, " + op1);
		salida.append("SUB AX, " + op2);
		salida.append("MOV " + result + ", AX" );
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

		salida.append("MOV AX, " + op1);
		salida.append("MUL AX, " + op2);
		salida.append("MOV " + result + ", AX" );
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

		salida.append("MOV AX, " + op1);
		salida.append("DIV AX, " + op2);
		salida.append("MOV " + result + ", AX" );
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

		salida.append("MOV ST, " + op1);
		salida.append("ADD ST, " + op2);
		salida.append("MOV " + result + ", ST" );

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

		salida.append("MOV ST, " + op1);
		salida.append("MUL ST, " + op2);
		salida.append("MOV " + result + ", ST" );
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

		salida.append("MOV ST[0], " + op1);
		salida.append("DIV ST[0], " + op2);
		salida.append("MOV " + result + ", ST" );
	}

	///
	private void etiqueta() {
		
	}
	
	private void salto() {
		
	}
	
	private void invocacionFuncion() {
		
	}
	
	// Etc.
	
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
