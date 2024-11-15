package GeneradorCodigo;

import java.util.ArrayList;

public class TraductorAssembler {
	
	private static String path = "RUTA de archivo...";
	private ArrayList<Boolean> registros32bits; //4 registros
	private ArrayList<Boolean> registros16bits; //4 registros
	private ArrayList<Boolean> registrosPuntoFlotante; //8 registros
	
	public TraductorAssembler() {
		// TODO Auto-generated constructor stub
		this.cargarRegistros();
	}
	
	
	private void cargarRegistros() {
		// TODO Auto-generated method stub
		for(int i=0; i<=4 ; i++) {
			registros32bits.add(false);
			registros16bits.add(false);
			registrosPuntoFlotante.add(false);
		}
		for(int i=0; i<=4 ; i++) {
			registrosPuntoFlotante.add(false);
		}
	}


	private void suma() {
		
	}
	
	private void resta() {
		
	}
	
	private void multiplicacion() {
		
	}
	
	private void division() {
		
	}
	
	private void etiqueta() {
		
	}
	
	private void salto() {
		
	}
	
	private void invocacionFuncion() {
		
	}
	
	// Etc.
	
	public void traducir(Terceto t) {
		// TODO El metodo debe tomar el terceto y mapear hacia que metodo de traduccion debe dirigirse
		
	}

}
