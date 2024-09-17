package AnalizadorLexico.AccionesSemanticas;

import AnalizadorLexico.Lexico;

import java.io.IOException;
import java.util.Optional;

public abstract class AccionSemantica {
    //CONSTANTES LITERALES TOKENS
    public final static int MENORIGUAL=257;
    p
    public abstract Optional<Integer> ejecutar(String token, Character caracterActual, Lexico lexico) throws IOException;
}

class AS1 extends AccionSemantica{

    @Override
    public Optional<Integer> ejecutar(String token, Character caracterActual, Lexico lexico) {
        token = token + caracterActual;
        return null;
    }
}
class AS2 extends AccionSemantica{
    @Override
    public Optional<Integer> ejecutar(String token, Character caracterActual, Lexico lexico) {
        System.out.println("Error: cte mal escrita en linea ", lexico.getContadorLinea());
        return null;
    }
}
class AS3 extends AccionSemantica{
    private final double infPositivo = Math.pow(1.1754943, -38 );
    private final double supPositivo = Math.pow(3.40282347, 38);
    private final double infNegativo = Math.pow(-3.40282347, 38);
    private final double supNegativo = Math.pow(-1.17549435, -38);


    private boolean fueraRango(String cte){
        String mantisa= new String();
        String exp = new String();
        int i = 0;
        while(cte.indexOf(i) != 's' && cte.indexOf(i) != 'S' && i < cte.length()) {
            mantisa = mantisa + cte.indexOf(i);
            i++;
        }
        while (i < cte.length()){
            exp = exp + cte.indexOf(i);
            i++;
        }
        if (exp.equals(" ")) {exp ="1";}
        double mnt = Double.parseDouble(mantisa);
        double exponente = Double.parseDouble(exp);
        double result = Math.pow(mnt,exponente);

        if (result > 0){
            if (infPositivo < result && supPositivo > result)
                return false;

        }
         if (result < 0){
             if (infNegativo < result && supNegativo > result)
                 return  false;
         }
         return true;
    }


    @Override
    public Optional<Integer> ejecutar(String token, Character caracterActual, Lexico lexico) {
       if (fueraRango(token)) {
           System.out.println("Warning: cte fuera de rango en linea ", lexico.getContadorLinea());
       }

       /* Buscar en la tabla de símbolos
        Si esta: devolver constante + puntero a la tabla de símbolos
        No está: Agregar a la tabla de símbolos, devolver constante + puntero a la tabla de símbolos
        */
        return null;
    }
}


