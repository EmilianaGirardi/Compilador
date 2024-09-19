package AnalizadorLexico.AccionesSemanticas;

import AnalizadorLexico.Lexico;
import AnalizadorLexico.TablaSimbolos;

import java.io.IOException;
import java.util.Optional;

public abstract class AccionSemantica {
    //CONSTANTES LITERALES TOKENS
    public final static int MENORIGUAL=257;
    public final static int ID=258;
    public final static int ASIGNACION=259;
    public final static int DISTINTO=260;
    public final static int MAYORIGUAL=261;
    public final static int SINGLE_CONSTANTE=262;
    public final static int ENTERO_UNSIGNED = 263;
    public final static int octal = 264;
    public abstract Optional<Integer> ejecutar(String token, Character caracterActual, Lexico lexico) throws IOException;
}

class AS1 extends AccionSemantica{

    @Override
    public Optional<Integer> ejecutar(String token, Character caracterActual, Lexico lexico) throws IOException {
        token = token + caracterActual;
        lexico.leerSiguiente();
        return null;
    }
}
class AS2 extends AccionSemantica{
    @Override
    public Optional<Integer> ejecutar(String token, Character caracterActual, Lexico lexico) {
        System.out.println("Error: cte mal escrita en linea "+ lexico.getContadorLinea());
        
        TablaSimbolos TS = lexico.getTablaSimbolos();
        if(!TS.estaToken(token)) {
        	TS.agregarToken(token, null);
        }
        lexico.setYyval(token);
        return Optional.of(SINGLE_CONSTANTE);
    }
}
class AS3 extends AccionSemantica{
    private final Float infPositivo = (float) Math.pow(1.1754943, -38 );
    private final Float supPositivo = (float) Math.pow(3.40282347, 38);
    private final Float infNegativo = (float) Math.pow(-3.40282347, 38);
    private final Float supNegativo = (float) Math.pow(-1.17549435, -38);


    private String truncarFueraRango(String cte, int linea) throws NumberFormatException{
    	// Reemplazar 's' por 'e' para convertir a notación científica y parsear el float
        cte = cte.replace('s', 'e');
        Float result = Float.parseFloat(cte);
        
        if (result > 0.0f){
            if (infPositivo < result) {
            	System.out.println("Warning: cte fuera de rango en linea "+ linea);
            	String nuevaCte = infPositivo.toString().replace('e', 's');
            	return nuevaCte;
            	
            }else if(supPositivo > result) {
            	System.out.println("Warning: cte fuera de rango en linea "+ linea);
            	String nuevaCte = supPositivo.toString().replace('e', 's');
            	return nuevaCte;
            }
        }
        if (result < 0.0f){
             if (infNegativo < result) {
            	System.out.println("Warning: cte fuera de rango en linea "+ linea);
             	String nuevaCte = infNegativo.toString().replace('e', 's');
             	return nuevaCte;
             	
             }else if(supNegativo > result) {
            	System.out.println("Warning: cte fuera de rango en linea "+ linea);
              	String nuevaCte = supNegativo.toString().replace('e', 's');
              	return nuevaCte; 
             }
        }
        
        return cte;
    }


    @Override
    public Optional<Integer> ejecutar(String token, Character caracterActual, Lexico lexico) {
    	TablaSimbolos TS = lexico.getTablaSimbolos();
    	
    	token = truncarFueraRango(token, lexico.getContadorLinea());
		
		if(!TS.estaToken(token)) {
			TS.agregarToken(token, null);
		}
		
		lexico.setYyval(token);
        return Optional.of(SINGLE_CONSTANTE);
    }
}


public class AS4 extends AccionSemantica {

    public final int limite = Math.pow(2, 15) -1;

    private String truncaEntero(String token) throws NumberFormatException {
        int numero = Integer.parseInt(token);
        numero = numero & 0xFFFF;
        return Integer.toString(numero);
    }

    @Override
    public Optional<Integer> ejecutar(String token, Character caracterActual, Lexico lexico) throws IOException {
        if ((int) token < 0 || (int) token > limite){
            System.out.println('EL entero se encuentra fuera de rango');
            token = truncaEntero(token);
        }
        TablaSimbolos TS = lexico.getTablaSimbolos();
        if(!TS.estaToken(token)){
            TS.agregarToken(token, null);
        }
        lexico.setYyval(token);
        return Optional.of(ENTERO_UNSIGNED);
    }
}

public class AS5 extends AccionSemantica {

    public final int limite = 0177777;

    private String truncaEntero(String token) throws NumberFormatException {
        int numero = Integer.parseInt(token);
        numero = numero & 0xFFFF;
        return Integer.toString(numero);
    }

    @Override
    public Optional<Integer> ejecutar(String token, Character caracterActual, Lexico lexico) throws IOException {
        if ((int) token < 0 || (int) token > limite){
            System.out.println('EL entero se encuentra fuera de rango');
            token = truncaEntero(token);
        }
        TablaSimbolos TS = lexico.getTablaSimbolos();
        if(!TS.estaToken(token)){
            TS.agregarToken(token, null);
        }
        lexico.setYyval(token);
        return Optional.of(ENTERO_UNSIGNED);
    }
}

public class AS6 extends AccionSemantica {

    @Override
    public Optional<Integer> ejecutar(String token, Character caracterActual, Lexico lexico) throws IOException {
        if (caracterActual.equals("/r")) { //Se supone que al barra r lo tomaba como un solo caracter
            caracterActual = lexico.leerSiguiente();
            if (!caracterActual.equals("/n"))
            {
                token.append("/r");
            }
        }

        return Optional.empty(); //Deberia ser void este retorno nose si esta bien
    }
}


public class AS7 extends AccionSemantica{
    @Override
    public Optional<Integer> ejecutar(String token, Character caracterActual, Lexico lexico) {
        if (caracterActual.equals("/r")) {
            TablaSimbolos TS = lexico.getTablaSimbolos();
            TS.agregarToken(token, null);
        }
        return Optional.empty();
    }
}
public class AS8 extends AccionSemantica{

    @Override
    public Optional<Integer>  ejecutar(String token, Character caracterActual, Lexico lexico) {
        // TODO Auto-generated method stub
        TablaSimbolos TS = lexico.getTablaSimbolos();

        if(!TS.estaToken(token)) {
            TS.agregarToken(token, null);
        }

        lexico.setYyval(token);
        return Optional.of(ID);
    }
} //LA 8 y la 9 son iguales

/*
 * ACC_SEM 4 - 8
 * */

class AS9 extends AccionSemantica {

	@Override
	public Optional<Integer>  ejecutar(String token, Character caracterActual, Lexico lexico) {
		// TODO Auto-generated method stub
		TablaSimbolos TS = lexico.getTablaSimbolos();
		
		if(!TS.estaToken(token)) {
			TS.agregarToken(token, null);
		}
		
		lexico.setYyval(token);
		return Optional.of(ID);
	}

}

class AS10 extends AccionSemantica {

	@Override
	public Optional<Integer> ejecutar(String token, Character caracterActual,  Lexico lexico) {
		// TODO Auto-generated method stub
		TablaSimbolos TS = lexico.getTablaSimbolos();
		token+='=';
		
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
			TS.agregarToken(token, null);
		}
		lexico.setYyval(token);
		
		return Optional.of(ASIGNACION);
	}

}


class AS11 extends AccionSemantica {

	@Override
	public Optional<Integer> ejecutar(String token, Character caracterActual, Lexico lexico) {
		// TODO Auto-generated method stub
		TablaSimbolos TS = lexico.getTablaSimbolos();
		token+='=';
		
		if(caracterActual=='=') {
			try {
				lexico.leerSiguiente();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			System.out.println("Warning: sentencia de inequidad incompleta. Línea: "+lexico.getContadorLinea());
		}
		
		if(!TS.estaToken(token)) {
			TS.agregarToken(token, null);
		}
		lexico.setYyval(token);
		
		return Optional.of(DISTINTO);
	}

}

class AS12 extends AccionSemantica {

	@Override
	public Optional<Integer>  ejecutar(String token, Character caracterActual, Lexico lexico) throws IOException {
        token = token + caracterActual;
        if (!lexico.getTablaSimbolos().estaToken(token)){
            lexico.getTablaSimbolos().agregarToken(token, null);
        }
        lexico.setYyval(token); //puntero a la tabla de simbolos
        lexico.leerSiguiente();

        return Optional.of(MAYORIGUAL); //devuelve el token
	}

}

class AS13 extends AccionSemantica{

    //as16 y as17 deberian ser igual a esta.
    @Override
    public Optional<Integer> ejecutar(String token, Character caracterActual, Lexico lexico) throws IOException {
        int ascii = token.charAt(0);
        return Optional.of(ascii);
    }

    //EL < NO SE GUARDA EN LA TABLA DE SIMBOLOS PORQUE ES UN SOLO CARACTER
}

class AS14 extends AccionSemantica{
    @Override
    public Optional<Integer> ejecutar(String token, Character caracterActual, Lexico lexico) throws IOException {
        token = token + caracterActual;
        if (!lexico.getTablaSimbolos().estaToken(token)){
            lexico.getTablaSimbolos().agregarToken(token, null);
        }
        lexico.setYyval(token); //puntero a la tabla de simbolos
        lexico.leerSiguiente();

        return Optional.of(MENORIGUAL); //devuelve el token

    }
}

class AS15 extends AccionSemantica{

    @Override
    public Optional<Integer> ejecutar(String token, Character caracterActual, Lexico lexico) throws IOException {
        int ascii = token.charAt(0);
        lexico.leerSiguiente();
        return Optional.of(ascii);
    }

    //EL < NO SE GUARDA EN LA TABLA DE SIMBOLOS PORQUE ES UN SOLO CARACTER
}

class AS16 extends AccionSemantica{

    @Override
    public Optional<Integer> ejecutar(String token, Character caracterActual, Lexico lexico) throws IOException {
        lexico.leerSiguiente();
        return null;
    }
}


