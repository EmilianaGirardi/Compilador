package AnalizadorLexico.AccionesSemanticas;

import AnalizadorLexico.Lexico;

public interface AccionSemantica {

    public abstract void ejecutar(String token, Character caracterActual );
}

public class AS1 implements AccionSemantica{

    @Override
    public void ejecutar(String token, Character caracterActual) {

        token = token + caracterActual;

    }
}
public class AS2 implements AccionSemantica{
    @Override
    public void ejecutar(String token, Character caracterActual) {
        System.out.println("Error: cte mal escrita en linea ", Lexico.getContadorLinea());
    }
}
public class AS3 implements AccionSemantica{
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
    public void ejecutar(String token, Character caracterActual) {
       if (fueraRango(token)) {
           System.out.println("Warning: cte fuera de rango en linea ", Lexico.getContadorLinea());
       }

       /* Buscar en la tabla de símbolos
        Si esta: devolver constante + puntero a la tabla de símbolos
        No está: Agregar a la tabla de símbolos, devolver constante + puntero a la tabla de símbolos
        */
    }
}


