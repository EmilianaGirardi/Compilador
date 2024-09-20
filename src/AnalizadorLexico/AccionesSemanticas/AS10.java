package AnalizadorLexico.AccionesSemanticas;

import java.io.IOException;
import java.util.Optional;

import AnalizadorLexico.Lexico;
import AnalizadorLexico.TablaSimbolos;

public class AS10 extends AccionSemantica {

	@Override
	public Optional<Integer> ejecutar(Character caracterActual,  Lexico lexico) {
		// TODO Auto-generated method stub
		TablaSimbolos TS = lexico.getTablaSimbolos();
		lexico.addCharToken('=');
		
		String token = lexico.getToken();
		
		if(caracterActual=='=') {
			try {
				lexico.leerSiguiente();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			System.out.println("Warning: asignación incompleta. Línea: "+lexico.getContadorLinea());
		}
		
		if(!TS.estaToken(token)) {
			TS.agregarToken(token, ASIGNACION);
		}
		lexico.setYyval(token);
		
		return Optional.of(ASIGNACION);
	}

}
