package AnalizadorLexico.AccionesSemanticas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import AnalizadorLexico.Lexico;
import AnalizadorLexico.TablaSimbolos;

public class AS11 extends AccionSemantica {

	@Override
	public Optional<Integer> ejecutar(Character caracterActual) throws IOException {
		Lexico lexico = Lexico.getInstance();
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
			System.out.println(YELLOW+"Warning: sentencia de inequidad incompleta. Línea: "+lexico.getContadorLinea()+RESET);
		}
		
		ArrayList<Integer> atributos = new ArrayList<Integer>();
		atributos.add(DISTINTO);
		atributos.add(TIPO_SIMBOLO);
		if(!TS.estaToken(token)) {
			TS.agregarToken(token, atributos);
		}
		lexico.setYylval(token);
		
		return Optional.of(DISTINTO);
	}

}