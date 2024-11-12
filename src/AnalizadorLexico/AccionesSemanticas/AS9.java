package AnalizadorLexico.AccionesSemanticas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import AnalizadorLexico.Lexico;
import AnalizadorLexico.TablaSimbolos;

public class AS9 extends AccionSemantica {

	@Override
	public Optional<Integer>  ejecutar(Character caracterActual) throws IOException {
		Lexico lexico = Lexico.getInstance();
		// TODO Auto-generated method stub
		TablaSimbolos TS = lexico.getTablaSimbolos();
		String token = lexico.getToken();
		ArrayList<Integer> atributos = new ArrayList<Integer>();
		atributos.add(ID);
		atributos.add(TIPO_SINGLE);
		atributos.add(NOMBRE_VAR);
		
		if(!TS.estaToken(token)) {
			TS.agregarToken(token, atributos);
		}
		
		lexico.setYylval(token);
		return Optional.of(ID);
	}

}
