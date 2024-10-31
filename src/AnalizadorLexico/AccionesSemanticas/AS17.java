package AnalizadorLexico.AccionesSemanticas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import AnalizadorLexico.Lexico;
import AnalizadorLexico.TablaSimbolos;

public class AS17 extends AccionSemantica{

	@Override
	public Optional<Integer> ejecutar(Character caracterActual, Lexico lexico) throws IOException {
		// TODO Auto-generated method stub
		TablaSimbolos TS = lexico.getTablaSimbolos();
		String token = lexico.getToken();

		lexico.setYylval(token);
		
		if(!TS.estaToken(token)) {
			ArrayList<Integer> atributos = new ArrayList<Integer>();
			atributos.add(ID);
			atributos.add(TIPO_DESCONOCIDO);
			
			TS.agregarToken(token, atributos);
			return Optional.of(ID);
		}else{
			return Optional.of(TS.getValor(token).get(0));
		}
		

	}
	
}
