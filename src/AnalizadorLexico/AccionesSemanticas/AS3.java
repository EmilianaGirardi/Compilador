package AnalizadorLexico.AccionesSemanticas;

import java.util.Optional;

import AnalizadorLexico.Lexico;
import AnalizadorLexico.TablaSimbolos;

public class AS3 extends AccionSemantica{
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
            	System.out.println("Warning: constante fuera de rango. Linea: "+ linea);
            	String nuevaCte = infPositivo.toString().replace('e', 's');
            	return nuevaCte;
            	
            }else if(supPositivo > result) {
            	System.out.println("Warning: constante fuera de rango. Linea: "+ linea);
            	String nuevaCte = supPositivo.toString().replace('e', 's');
            	return nuevaCte;
            }
        }
        if (result < 0.0f){
             if (infNegativo < result) {
            	System.out.println("Warning: cte fuera de rango. Linea "+ linea);
             	String nuevaCte = infNegativo.toString().replace('e', 's');
             	return nuevaCte;
             	
             }else if(supNegativo > result) {
            	System.out.println("Warning: cte fuera de rango. Linea "+ linea);
              	String nuevaCte = supNegativo.toString().replace('e', 's');
              	return nuevaCte; 
             }
        }
        
        return cte;
    }
 
    @Override
    public Optional<Integer> ejecutar(Character caracterActual, Lexico lexico) {
    	TablaSimbolos TS = lexico.getTablaSimbolos();
    	
    	String token = lexico.getToken();
    	
    	token = truncarFueraRango(token, lexico.getContadorLinea());
		
		if(!TS.estaToken(token)) {
			TS.agregarToken(token, SINGLE_CONSTANTE);
		}
		
		lexico.setYyval(token);
        return Optional.of(SINGLE_CONSTANTE);
    }
}
