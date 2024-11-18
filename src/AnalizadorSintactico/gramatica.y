%{
        package AnalizadorSintactico;
    	import java.io.*;
    	import AnalizadorLexico.Lexico;
    	import GeneradorCodigo.Generador;
    	import java.util.ArrayList;
    	import AnalizadorLexico.TablaSimbolos;
    	import GeneradorCodigo.Terceto;

%}

%token MENORIGUAL ID ASIGNACION DISTINTO MAYORIGUAL SINGLE_CONSTANTE ENTERO_UNSIGNED OCTAL MULTILINEA REPEAT IF THEN ELSE BEGIN END END_IF OUTF TYPEDEF FUN RET GOTO TRIPLE OCTAL TIPO_UNSIGNED TIPO_SINGLE TIPO_OCTAL UNTIL


%%

/*---Programa---*/

programa : ID BEGIN conjunto_sentencias END ';' {
        lexico.getTablaSimbolos().agregarUso($1.sval, NOMBRE_PROGRAMA);
        System.out.println("Se detecto: Programa");}
      | BEGIN conjunto_sentencias END ';' {System.err.println("Error: Falta nombre de programa"); generador.setError();}
      | ID conjunto_sentencias END ';' {System.err.println("Error: Falta delimitador de programa ");generador.setError();}
      ;
      
conjunto_sentencias : declarativa ';'
			| declarativa {System.err.println("Error: Falta ; " + "antes de la linea: " + lexico.getContadorLinea()); generador.setError();}
			| ejecutable ';'
			| ejecutable {System.err.println("Error: Falta ; " + "antes de la linea: " + lexico.getContadorLinea());generador.setError();}
			| conjunto_sentencias declarativa ';'
			| conjunto_sentencias declarativa {System.err.println("Error: Falta ; " + "antes de la linea: " + lexico.getContadorLinea()); generador.setError();}
			| conjunto_sentencias ejecutable ';'
			| conjunto_sentencias ejecutable {System.out.println("Error: Falta ; " + "antes de la linea: " + lexico.getContadorLinea()); generador.setError();}
			;
/*------*/

/*---EJECUTABLE---*/

ejecutable : sentencia_if {System.out.println("Se detecto: Sentencia if ");}
    | invocacion_fun  {System.out.println("Se detecto: Invocacion a funcion " + " en linea: " + lexico.getContadorLinea());}
    | asig  {System.out.println("Se detecto: Asignacion " + " en linea: " + lexico.getContadorLinea());}
    | repeat_until {System.out.println("Se detecto: Ciclo repeat until ");}
    | goto {System.out.println("Se detecto: Sentencia GOTO " + " en linea: " + lexico.getContadorLinea());}
    | salida {System.out.println("Se detecto: Salida " + " en linea: " + lexico.getContadorLinea());}
    | etiqueta {System.out.println("Se detecto: Etiqueta " + " en linea: " + lexico.getContadorLinea());}
    ;

bloque_sentencias_ejecutables : BEGIN sentencias_ejecutables END
                                | BEGIN sentencias_ejecutables retorno END
                                | BEGIN retorno END
								;

sentencias_ejecutables : ejecutable ';'
 	| sentencias_ejecutables ejecutable ';'
 	| ejecutable {System.err.println("Error: Falta ;"); generador.setError();}
 	| sentencias_ejecutables ejecutable {System.out.println("Falta ;");}
	;


/*------*/

/*---DECLARATIVA---*/

declarativa : declaracionFun {System.out.println("Se detecto: Declaracion de funcion ");}
	| declarvar {System.out.println("Se detecto: Declaración de variable " + "en linea: " + lexico.getContadorLinea());}
	| def_triple {System.out.println("Se detecto: Declaración de tipo triple " + "en linea: " + lexico.getContadorLinea());}
	| declar_compuesto {System.out.println("Se detecto: Declaración de variable tipo triple " + "en linea: " + lexico.getContadorLinea());}
	;

declarvar : tipo lista_var {
        String[] lista = $2.sval.split(",");
        TablaSimbolos TS = lexico.getTablaSimbolos();
        Integer tipo = Integer.parseInt($1.sval);
        String ambito = TS.getAmbitos();
        for (String var : lista){
            TS.editarTipo(var, tipo);
            TS.agregarUso(var, NOMBRE_VAR);
            if (TS.estaToken(var + ambito)){
                System.err.println("Error: ya existe la variable " + var + " en el ambito " + ambito + ". Linea " + lexico.getContadorLinea());
                generador.setError();
            }
            TS.editarLexema(var, var + ambito);
        }
    }
    ;

declar_compuesto : ID lista_var{
        //hay que ver que el ID sea alcanzable por el ambito actual.
        TablaSimbolos TS = lexico.getTablaSimbolos();
        String id = TS.buscarVariable($1.sval);
        if (id == null){
            System.err.println("Error: variable no declarad. Linea: " + lexico.getContadorLinea());
            generador.setError();
        }
        else{
            if (tiposUsuario.contains($1.sval)){
                Integer t = TS.getTipo(id);
                    switch(t){
                        case(TIPO_TRIPLE_UNSIGNED):
                            t = T_UNSIGNED;
                            break;
                        case(TIPO_TRIPLE_SINGLE):
                            t = T_SINGLE;
                            break;
                        case(TIPO_TRIPLE_OCTAL):
                             t = T_OCTAL;
                             break;
                    }
                String[] lista = $2.sval.split(",");
                String ambito = TS.getAmbitos();
                for (String var : lista){
                    for(int i=1; i<=3; i++){
                        String token = var+'{'+i+'}';
                        ArrayList<Integer> atributos = new ArrayList<Integer>();
                        atributos.add(258);
                        atributos.add(t);
                        atributos.add(NOMBRE_VAR);
                        if (TS.estaToken(token + ambito)){
                           System.err.println("Error: ya existe la variable " + var + " en el ambito " + ambito + ". Linea " + lexico.getContadorLinea());
                           generador.setError();
                        }
                        TS.agregarToken(token + ambito, atributos);
                    }
                }
            }
        }
    }
	;

		
/*------*/

/*---VARIABLES---*/

lista_var : ID  {$$.sval = $1.sval;}
	| lista_var ',' ID {
	        $$.sval = $1.sval.concat(",").concat($3.sval);
	    }
	;

tipo : TIPO_OCTAL {$$.sval = String.valueOf(T_OCTAL);}
	| TIPO_UNSIGNED {$$.sval = String.valueOf(T_UNSIGNED);}
	| TIPO_SINGLE {$$.sval = String.valueOf(T_SINGLE);}
	;


/*------*/

/*---FUNCION---*/

encabezadoFun : tipo FUN ID '(' tipo ID ')' {
                TablaSimbolos TS = lexico.getTablaSimbolos();

                String funcion = TS.nameMangling($3.sval);
                TS.editarLexema($3.sval, funcion);

                Integer tipo = Integer.parseInt($1.sval);
                TS.editarTipo(funcion, tipo);
                TS.agregarUso(funcion, NOMBRE_FUN);
                Integer tipoParam = Integer.parseInt($5.sval);
                TS.editarTipo($6.sval, tipoParam);
                TS.agregarUso($6.sval, NOMBRE_PARAMETRO);
                TS.agregarTipoParam(funcion, tipoParam);
                TS.addAmbitos($3.sval);
                TS.editarLexema($6.sval, $6.sval + TS.getAmbitos());

                $$.sval = generador.addTerceto(funcion,null,null);
                generador.getTerceto(Integer.parseInt($$.sval.replaceAll("\\D", ""))).setTipo(TIPO_ETIQUETA);
                generador.putEtiqueta(funcion);
              }
              | tipo FUN  '(' tipo ID ')' {System.err.println("Error: Falta nombre de funcion"); generador.setError();}
              | tipo FUN ID '(' ')' {System.err.println("Error: Falta parametro de funcion"); generador.setError();}
              | tipo FUN ID '(' tipo ')' {System.err.println("Error: falta nombre del parametro formal"); generador.setError();}
              | tipo FUN ID '(' ID ')' {System.err.println("Error: falta tipo del parametro formal"); generador.setError();}
              ;

declaracionFun : encabezadoFun BEGIN conjunto_sentencias retorno END{
                //verificar el tipo de retorno
                TablaSimbolos TS = lexico.getTablaSimbolos();
                String lexemaFun = TS.getUltimoAmbito(); //obtengo el lexema de la funcion
                Integer tipoFun = TS.getTipo(lexemaFun); //obtengo el tipo de la funcion
                Integer tipoRetorno = generador.getTerceto(Integer.parseInt($4.sval.replaceAll("\\D", ""))).getTipo();
                if (tipoFun != tipoRetorno){
                    System.err.println("Error: tipo de retorno invalido en funcion: " + lexemaFun);
                    generador.setError();
                }

                //desapilar el ambito de la funcion
                TS.eliminarAmbito();
     }

	| encabezadoFun BEGIN retorno END{
	            //verificar tipo retorno
	             TablaSimbolos TS = lexico.getTablaSimbolos();
                 String lexemaFun = TS.getUltimoAmbito(); //obtengo el lexema de la funcion
                 Integer tipoFun = TS.getTipo(lexemaFun); //obtengo el tipo de la funcion

                Integer tipoRetorno = generador.getTerceto(Integer.parseInt($3.sval.replaceAll("\\D", ""))).getTipo();
                if (tipoFun != tipoRetorno){
                     System.err.println("Error: tipo de retorno invalido en funcion: " + lexemaFun);
                     generador.setError();
                }
                //desapilar el ambito de la funcion
                TS.eliminarAmbito();

	}

	| encabezadoFun BEGIN conjunto_sentencias END {System.err.println("Error: falta retorno en funcion"); generador.setError();}

	;


retorno : RET '(' exp_arit ')' ';'{
            Integer tipo = null;
            TablaSimbolos TS = lexico.getTablaSimbolos();
            String expresion = TS.buscarVariable($3.sval);
            if(expresion == null){
            	System.err.println("Error: variable no declarada. Linea: " + lexico.getContadorLinea());
            	generador.setError();
                $$.sval = generador.addTerceto("RETORNO",expresion , null);
            }else{
				switch (expresion){
                	case "Terceto" :
                        tipo = generador.getTerceto(Integer.parseInt($3.sval.replaceAll("\\D", ""))).getTipo();
                        $$.sval = generador.addTerceto("RETORNO",$3.sval , null);
                        generador.getTerceto(Integer.parseInt($$.sval.replaceAll("\\D", ""))).setTipo(tipo);
                        break;
                	default: //la exp es una variable o una cte
                        tipo = TS.getTipo(expresion);
                        $$.sval = generador.addTerceto("RETORNO",expresion , null);
                        generador.getTerceto(Integer.parseInt($$.sval.replaceAll("\\D", ""))).setTipo(tipo);
                        break;
            	}
            }
            
        }
	    ;

invocacion_fun : ID '(' exp_arit ')'{
        //verificar que el uso de ID sea nombre de función.
        TablaSimbolos TS = lexico.getTablaSimbolos();

        String id = TS.buscarVariable($1.sval);
        if (id == null || TS.getUso(id) != NOMBRE_FUN){
            System.err.println("Error: funcion no declarada. Linea: " + lexico.getContadorLinea());
            generador.setError();
        }
        else{ //es una funcion alcanzable
            //verificar que el tipo del parametro formal sea igual al del parametro real.
            /* tenemos en la tabla de simbolos, para el lexema ID (nombre de la funcion) el tipo de su parametro,
            entonces lo comparamos con el tipo de exp_arit.
            */
             if(id.equals(TS.getUltimoAmbito())) {
                                    System.err.println("Error no se admiten funciones recursivas");
                                    generador.setError();
             }
            Integer tipoExp = null;
            String expresion = TS.buscarVariable($3.sval);
            if(expresion == null){
            	System.err.println("Error: variable no declarada. Linea: " + lexico.getContadorLinea());
            	generador.setError();
            }else{
            	switch (expresion){
	                case "Terceto" :
	                        tipoExp = generador.getTerceto(Integer.parseInt($3.sval.replaceAll("\\D", ""))).getTipo();
	                        break;
	                default: //la exp es una variable o una cte
	                        tipoExp = TS.getTipo(expresion);
	                        break;
            	}
            }
            

            if (tipoExp != TS.getTipoParam(id)){
                System.err.println("Error: Invocación a una función con un parametro incorrecto. Linea: " + lexico.getContadorLinea());
                generador.setError();
            }
            // TODO implementar logica de etiquetas para llamar a la funcion
            String operando1;
            if (expresion == "Terceto") operando1 = $1.sval;
            else operando1 = expresion;

            $$.sval = generador.addTerceto("CALL", id, operando1);
            Integer tipo = TS.getTipo(id);
            generador.getTerceto(Integer.parseInt($$.sval.replaceAll("\\D", ""))).setTipo(tipo);
        }

    }

    | ID '(' tipo exp_arit ')'{
        //verificar que el uso de ID sea nombre de función.
        TablaSimbolos TS = lexico.getTablaSimbolos();
        if($1.sval.equals(TS.getUltimoAmbito())) {
                            System.err.println("Error no se admiten funciones recursivas");
            generador.setError();
        }
        String id = TS.buscarVariable($1.sval);
        if (id == null || TS.getUso(id) != NOMBRE_FUN){
            System.err.println("Error: funcion no declarada. Linea: " + lexico.getContadorLinea());
            generador.setError();
        }
        else { //es una función alcanzable
            //verificar que el tipo del parametro formal sea igual al del parametro real.
            /* tenemos en la tabla de simbolos, para el lexema ID (nombre de la funcion) el tipo de su parametro,
            entonces lo comparamos con el tipo de exp_arit.
            */
            Integer tipoParam = TS.getTipoParam(id);
            Integer tipoCast = Integer.parseInt($3.sval);

             if(id.equals(TS.getUltimoAmbito())) {
                                    System.err.println("Error no se admiten funciones recursivas");
                                    generador.setError();
                                }

            if (tipoParam != tipoCast){
                System.err.println("Error: tipo de parametro incompatible. Linea: " + lexico.getContadorLinea());
                generador.setError();
            }
            else{
                Integer tipoExp = null;
                String expresion = TS.buscarVariable($4.sval);
                if(expresion == null){
                	System.err.println("Error: variable no declarada. Linea: " + lexico.getContadorLinea());
                	generador.setError();
                }else{
					switch (expresion){
                    	case "Terceto" :
                            tipoExp = generador.getTerceto(Integer.parseInt($4.sval.replaceAll("\\D", ""))).getTipo();
                            break;
                    	default: //la exp es una variable o una cte
                            tipoExp = TS.getTipo(expresion);
                            break;
                	}
                }
                
                //crear terceto de conversion
                String conversion = generador.getConversion(tipoExp, tipoCast);
                if (conversion == null) {
                	System.err.println("Error: conversion invalida. Linea: " + lexico.getContadorLinea());
                	generador.setError();
                }

                String operando1;
                if (expresion == "Terceto") operando1 = $1.sval;
                else operando1 = expresion;

                String terceto = generador.addTerceto(conversion, operando1, null);
                $$.sval = generador.addTerceto("CALL", id, terceto);
                Integer tipo = TS.getTipo(id);
                generador.getTerceto(Integer.parseInt($$.sval.replaceAll("\\D", ""))).setTipo(tipo);

            }
        }

    }
	| ID '(' tipo '(' exp_arit ')' ')' {
        //verificar que el uso de ID sea nombre de función.
        TablaSimbolos TS = lexico.getTablaSimbolos();

        String id = TS.buscarVariable($1.sval);
        if (id == null || TS.getUso(id) != NOMBRE_FUN){
            System.err.println("Error: funcion no declarada. Linea: " + lexico.getContadorLinea());
            generador.setError();
        }
        else { //es una función alcanzable
            //verificar que el tipo del parametro formal sea igual al del parametro real.
            /* tenemos en la tabla de simbolos, para el lexema ID (nombre de la funcion) el tipo de su parametro,
            entonces lo comparamos con el tipo de exp_arit.
            */
            if(id.equals(TS.getUltimoAmbito())) {
                        System.err.println("Error no se admiten funciones recursivas");
                        generador.setError();
                    }
            Integer tipoParam = TS.getTipoParam(id);
            Integer tipoCast = Integer.parseInt($3.sval);

            if (tipoParam != tipoCast){
                System.err.println("Error: tipo de parametro incompatible. Linea: " + lexico.getContadorLinea());
                generador.setError();
            }
            else{
                Integer tipoExp = null;
                String expresion = TS.buscarVariable($5.sval);
                if(expresion == null){
                	System.err.println("Error: variable no declarada. Linea: " + lexico.getContadorLinea());
                	generador.setError();
                }else{
                	switch (expresion){
                    	case "Terceto" :
                            tipoExp = generador.getTerceto(Integer.parseInt($5.sval.replaceAll("\\D", ""))).getTipo();
                            break;
                    	default: //la exp es una variable o una cte
                            tipoExp = TS.getTipo(expresion);
                            break;
                	}
                }
                
                //crear terceto de conversion
                String conversion = generador.getConversion(tipoExp, tipoCast);
                if (conversion == null) {System.out.println("Error de conversion en linea: " + lexico.getContadorLinea());}
                String operando1;
                if (expresion == "Terceto") operando1 = $1.sval;
                else operando1 = expresion;

                String terceto = generador.addTerceto(conversion, operando1, null);

                $$.sval = generador.addTerceto("CALL", id, terceto);
                Integer tipo = TS.getTipo(id);
                generador.getTerceto(Integer.parseInt($$.sval.replaceAll("\\D", ""))).setTipo(tipo);
            }
        }

    }
	| ID '(' ')' {System.err.println("Error: falta de parámetro en invocación a función. Linea: " + lexico.getContadorLinea()); generador.setError();}
	;

/*------*/

/*---EXPRESION ARITMETICA---*/

exp_arit : exp_arit '+' termino {
                    //verificación de tipos

                    Integer tipoExp = null;
                    Integer tipoTermino = null;
                    TablaSimbolos TS = lexico.getTablaSimbolos();

                    String expresion = TS.buscarVariable($1.sval);
                    if(expresion == null){
						System.err.println("Error: variable no declarada. Linea: " + lexico.getContadorLinea());
						generador.setError();
                    }else{
                    	switch (expresion){
                        	case "Terceto":
                                tipoExp = generador.getTerceto(Integer.parseInt($1.sval.replaceAll("\\D", ""))).getTipo();
                                break;
                        	default: //es una var valida
                                tipoExp = TS.getTipo(expresion);
                                break;
                    	}
                    }
                    

                    String termino = TS.buscarVariable($3.sval);
                    if(termino == null){
                    	System.err.println("Error: variable no declarada. Linea: " + lexico.getContadorLinea());
                    	generador.setError();
                    }else{
                    	switch (termino){
                        	case "Terceto":
                                tipoTermino = generador.getTerceto(Integer.parseInt($3.sval.replaceAll("\\D", ""))).getTipo();
                                break;
                        	default: //es una var valida
                                tipoTermino = TS.getTipo(termino);
                                break;
                    	}
                    }

                    

                    if(tipoExp != tipoTermino){
                        System.err.println("Error: Incompatibilidad de tipos en suma. Linea " + lexico.getContadorLinea());
                        generador.setError();
                    }
                    else{
                        String operando1, operando2;
                        if (expresion == "Terceto") operando1 = $1.sval;
                        else operando1 = expresion;
                        if (termino == "Terceto") operando2 = $3.sval;
                        else operando2 = termino;

                        $$.sval = generador.addTerceto("+", operando1, operando2);
                        generador.getTerceto(Integer.parseInt($$.sval.replaceAll("\\D", ""))).setTipo(tipoExp);
                        System.out.println("Se detecto: Suma " + "en linea: " + lexico.getContadorLinea());
                    }
           }

	       | exp_arit '-' termino {
                    //verificación de tipos

                    Integer tipoExp = null;
                    Integer tipoTermino = null;
                    TablaSimbolos TS = lexico.getTablaSimbolos();

                    String expresion = TS.buscarVariable($1.sval);
                    if(expresion == null){
						System.err.println("Error: variable no declarada. Linea: " + lexico.getContadorLinea());
						generador.setError();
                    }else{
                    	switch (expresion){
                        	case "Terceto":
                                tipoExp = generador.getTerceto(Integer.parseInt($1.sval.replaceAll("\\D", ""))).getTipo();
                                break;
                        	default: //es una var valida
                                tipoExp = TS.getTipo(expresion);
                                break;
                    	}	
                    }
                    

                    String termino = TS.buscarVariable($3.sval);

                    if(termino == null){
                    	System.err.println("Error: variable no declarada. Linea: " + lexico.getContadorLinea());
                    	generador.setError();
                    }else{
						switch (termino){
	                        case "Terceto":
	                                tipoTermino = generador.getTerceto(Integer.parseInt($3.sval.replaceAll("\\D", ""))).getTipo();
	                                break;
	                        default: //es una var valida
	                                tipoTermino = TS.getTipo(termino);
	                                break;
	                    }
                    }
                    

                    if(tipoExp != tipoTermino){
                        System.err.println("Error: Incompatibilidad de tipos en resta. Linea " + lexico.getContadorLinea());
                        generador.setError();
                    }
                    else{
                        String operando1, operando2;
                        if (expresion == "Terceto") operando1 = $1.sval;
                        else operando1 = expresion;
                        if (termino == "Terceto") operando2 = $3.sval;
                        else operando2 = termino;

                        $$.sval = generador.addTerceto("-", operando1, operando2);
                        generador.getTerceto(Integer.parseInt($$.sval.replaceAll("\\D", ""))).setTipo(tipoExp);
                        System.out.println("Se detecto: Resta " + "en linea: " + lexico.getContadorLinea());
                    }
           }

           | exp_arit '+' error ';' {
                    System.err.println("Error: Falta el término después de '+' en expresion aritmetica. Línea: " + lexico.getContadorLinea());
                    generador.setError();
           }

           | exp_arit '-' error ';'  {
                    System.err.println("Error: Falta el término después de '-' en expresión aritmetica. Línea: " + lexico.getContadorLinea());
                    generador.setError();
           }
           
           | termino {$$.sval = $1.sval;}
           ;



lista_exp_arit : exp_arit {
        			$$.sval = $1.sval;
    			}
	| lista_exp_arit ',' exp_arit {
	    $$.sval = $1.sval.concat(",").concat($3.sval);
	}
	;

termino : termino '*' factor{
                    //verificación de tipos

                    Integer tipoFactor = null;
                    Integer tipoTermino = null;
                    TablaSimbolos TS = lexico.getTablaSimbolos();

                    String factor = TS.buscarVariable($3.sval);
                    if(factor == null){
                    	System.err.println("Error: variable no declarada. Linea: " + lexico.getContadorLinea());
                    	generador.setError();
                    }else{
	                    switch (factor){
	                        case "Terceto":
	                                tipoFactor = generador.getTerceto(Integer.parseInt($3.sval.replaceAll("\\D", ""))).getTipo();
	                                break;
	                        default: //es una var valida
	                            tipoFactor = TS.getTipo(factor);
	                            break;
	                    }	
                    }
                    

                    String termino = TS.buscarVariable($1.sval);
                    if(termino == null){
                    	System.err.println("Error: variable no declarada. Linea: " + lexico.getContadorLinea());
                    	generador.setError();
                    }else {
	                    switch (termino){
	                        case "Terceto":
	                                tipoTermino = generador.getTerceto(Integer.parseInt($1.sval.replaceAll("\\D", ""))).getTipo();
	                                break;
	                        default: //es una var valida
	                                tipoTermino = TS.getTipo(termino);
	                                break;
	                    }	
                    }

                    if(tipoFactor != tipoTermino){
                        System.err.println("Error: Incompatibilidad de tipos en multiplicacion. Linea " + lexico.getContadorLinea());
                        generador.setError();
                    }
                    else{
                        String operando1, operando2;
                        if (termino == "Terceto") operando1 = $1.sval;
                        else operando1 = termino;
                        if (factor == "Terceto") operando2 = $3.sval;
                        else operando2 = factor;

                        $$.sval = generador.addTerceto("*", operando1, operando2);
                        generador.getTerceto(Integer.parseInt($$.sval.replaceAll("\\D", ""))).setTipo(tipoTermino);
                        System.out.println("Se detecto: Multiplicacion " + "en linea: " + lexico.getContadorLinea());
                    }
           }

	| termino '/' factor {
                    //verificación de tipos

                    Integer tipoFactor = null;
                    Integer tipoTermino = null;
                    TablaSimbolos TS = lexico.getTablaSimbolos();

                    String factor = TS.buscarVariable($3.sval);
                    if(factor == null){
                    	System.err.println("Error: variable no declarada. Linea: " + lexico.getContadorLinea());
                    	generador.setError();
                    }else{
	                    switch (factor){
	                        case "Terceto":
	                                tipoFactor = generador.getTerceto(Integer.parseInt($3.sval.replaceAll("\\D", ""))).getTipo();
	                                break;
	                        default: //es una var valida
	                            tipoFactor = TS.getTipo(factor);
	                            break;
	                    }	
                    }
                    

                    String termino = TS.buscarVariable($1.sval);
                    if(termino == null){
                    	System.err.println("Error: variable no declarada. Linea: " + lexico.getContadorLinea());
                    	generador.setError();
                    }else {
	                    switch (termino){
	                        case "Terceto":
	                                tipoTermino = generador.getTerceto(Integer.parseInt($1.sval.replaceAll("\\D", ""))).getTipo();
	                                break;
	                        default: //es una var valida
	                                tipoTermino = TS.getTipo(termino);
	                                break;
	                    }	
                    }

                    if(tipoFactor != tipoTermino){
                        System.err.println("Error: Incompatibilidad de tipos en division. Linea " + lexico.getContadorLinea());
                        generador.setError();
                    }
                    else{
                        String operando1, operando2;
                        if (termino == "Terceto") operando1 = $1.sval;
                        else operando1 = termino;
                        if (factor == "Terceto") operando2 = $3.sval;
                        else operando2 = factor;

                        $$.sval = generador.addTerceto("/", operando1, operando2);
                        generador.getTerceto(Integer.parseInt($$.sval.replaceAll("\\D", ""))).setTipo(tipoTermino);
                        System.out.println("Se detecto: Division " + "en linea: " + lexico.getContadorLinea());
                    }
           }
	| factor {
	    	$$.sval = $1.sval;
		}

	| termino '*' error ';' {System.err.println("Error: Falta el factor después de '*' en expresion aritmetica. Línea: " + lexico.getContadorLinea()); generador.setError();}
    | termino '/' error ';'  {System.err.println("Error: Falta el factor después de '/' en expresión aritmetica. Línea: " + lexico.getContadorLinea()); generador.setError();}
	;

factor : ID {
            $$.sval = $1.sval;
            System.out.println("Se detecto: Identificador " + $1.sval + " en linea: " + lexico.getContadorLinea());
            }
	| invocacion_fun {System.out.println("Se detecto: Invocación a función " + "en linea: " + lexico.getContadorLinea());}
	| triple {$$.sval = $1.sval;}
	| SINGLE_CONSTANTE {
		 	$$.sval = truncarFueraRango($1.sval, lexico.getContadorLinea());
		 	TablaSimbolos TS = lexico.getTablaSimbolos();
            TS.editarLexema($1.sval, $$.sval);
        }
    | ENTERO_UNSIGNED {
            $$.sval = $1.sval;
        }
    | OCTAL {
            $$.sval = $1.sval;
        }
    | '-' SINGLE_CONSTANTE {
        	$$.sval = truncarFueraRango("-"+$2.sval, lexico.getContadorLinea());
        	TablaSimbolos TS = lexico.getTablaSimbolos();
        	TS.editarLexema($2.sval, $$.sval);
        }
    ;


triple : ID '{' ENTERO_UNSIGNED '}' {
    String token = $1.sval+'{'+$3.sval+'}';
    TablaSimbolos TS = lexico.getTablaSimbolos();
    String var = TS.buscarVariable(token);
    if (var != null){
        $$.sval = token;
    }
    else {
        System.err.println("Error: Variable inexistenet o Intento de acceso a una posición de triple inexistente. Linea " + lexico.getContadorLinea());
        generador.setError();
        $$.sval = token;
        /*en este punto, los tercetos se generan igual,
        aunque se intente acceder a un valor invalido del tercerto.
        El generador de assembler deberia volver a verificar si la constante es 1, 2 o 3
        y solo generar codigo en ese caso, de lo contrario lanzar error.
        */
    }
}
;


/*------*/

/*---ASIGNACION ; ETIQUETA ; CONSTANTE ; SALIDA---*/

asig : ID ASIGNACION exp_arit {
            Integer tipoExp = null;
            Integer tipoID = null;
            TablaSimbolos TS = lexico.getTablaSimbolos();
            String expresion = TS.buscarVariable($3.sval);
            if(expresion==null){
				System.err.println("Error: variable no declarada. Linea: " + lexico.getContadorLinea());
				generador.setError();
            }else{
				switch (expresion){
                    case "Terceto":
                            tipoExp = generador.getTerceto(Integer.parseInt($3.sval.replaceAll("\\D", ""))).getTipo();
                            break;
                    default: //es una var valida
                            tipoExp = TS.getTipo(expresion);
                            break;
                }
            }
                    

            String id = TS.buscarVariable($1.sval);
            if(id == null){
            	System.err.println("Error: variable no declarada. Linea: " + lexico.getContadorLinea());
            	generador.setError();
            }else{
            	//es una var valida
                tipoID = TS.getTipo(id);

                if (tipoID != tipoExp){
                     System.err.println("Error: tipos invalidos en asignación. Linea: " +lexico.getContadorLinea());
                     generador.setError();
                }else{
                   String operando2;
                   if (expresion == "Terceto") operando2 = $3.sval;
                   else operando2 = expresion;
                   $$.sval = generador.addTerceto(":=", id, operando2);
                   generador.getTerceto(Integer.parseInt($$.sval.replaceAll("\\D", ""))).setTipo(tipoID);
               }
            }
                

        }

    | triple ASIGNACION exp_arit {
            Integer tipoExp = null;
            Integer tipoID = null;
            TablaSimbolos TS = lexico.getTablaSimbolos();
            String expresion = TS.buscarVariable($3.sval);
            if(expresion==null){
            	System.err.println("Error: variable no declarada. Linea: " + lexico.getContadorLinea());
            	generador.setError();
            }else{
            	switch (expresion){
                    case "Terceto":
                            tipoExp = generador.getTerceto(Integer.parseInt($3.sval.replaceAll("\\D", ""))).getTipo();
                                break;
                    default: //es una var valida
                            tipoExp = TS.getTipo(expresion);
                            break;
                }
            }
                    

            String id = TS.buscarVariable($1.sval);
            if(id == null){
            	System.err.println("Error: variable no declarada. Linea: " + lexico.getContadorLinea());
            	generador.setError();
            }else{
                tipoID = TS.getTipo(id);

                if (tipoID != tipoExp){
                      System.err.println("Error: tipos invalidos en asignación. Linea: " +lexico.getContadorLinea());
                 }
                else{
                    String operando2;
                    if (expresion == "Terceto") operando2 = $3.sval;
                    else operando2 = expresion;
                    $$.sval = generador.addTerceto(":=", id, operando2);
                    generador.getTerceto(Integer.parseInt($$.sval.replaceAll("\\D", ""))).setTipo(tipoID);
                }
                
            }
                

        }
 ;	


etiqueta : ID '@' {
                lexico.getTablaSimbolos().agregarUso($2.sval, NOMBRE_ETIQUETA);
                TablaSimbolos TS = lexico.getTablaSimbolos();
    			String etq = $1.sval+"@"+TS.getAmbitos();
    			if(!generador.isEtiqueta(etq)){
    				$$.sval = generador.addTerceto(etq,null,null);

    				generador.putEtiqueta(etq);

    				generador.getTerceto(Integer.parseInt($$.sval.replaceAll("\\D", ""))).setTipo(TIPO_ETIQUETA);

    			}else{
    				System.err.println("Error: la etiqueta "+etq+" ya existente. Linea: "+lexico.getContadorLinea());
    			}
    		}
		;

goto : GOTO ID '@' {
            TablaSimbolos TS = lexico.getTablaSimbolos();
            TS.agregarUso($2.sval, NOMBRE_ETIQUETA);
			String etq = $2.sval+"@"+TS.getAmbitos();
			$$.sval = generador.addTerceto("BI", null , etq);
			generador.getTerceto(Integer.parseInt($$.sval.replaceAll("\\D", ""))).setTipo(TIPO_SALTO);

			if(!generador.isEtiqueta(etq)){
		    	generador.addGoto(Integer.parseInt($$.sval.replaceAll("\\D", "")), etq);
		    }

		    
       }
	| GOTO error ';' {System.err.println("Error: falta de etiqueta en la sentencia GOTO" + ". Linea: " + lexico.getContadorLinea()); generador.setError();}
	;

salida : OUTF '(' MULTILINEA ')' {
        	$$.sval = generador.addTerceto("SALIDA", $3.sval, null);
        }

        | OUTF '(' exp_arit ')' {
        	$$.sval = generador.addTerceto("SALIDA", $3.sval, null);
        }

    |OUTF '(' ')'  {System.err.println("Error: falta parametro " + ". Linea: " + lexico.getContadorLinea()); generador.setError();}
    |OUTF '(' error ')' {System.err.println("Error: parametro invalido " + ". Linea: " + lexico.getContadorLinea()); generador.setError();}
;

/*------*/

/*---CONDICIONALES---*/

sentencia_if : condicion_if bloque_sentencias_ejecutables END_IF {
							int pos =Integer.parseInt(generador.obtenerElementoPila().replaceAll("\\D", ""));
							generador.eliminarPila();
							Terceto t = generador.getTerceto(pos);
							String label = "ET"+generador.getSizeTercetos();

							t.setTercerParametro(label);
							$$.sval=generador.addTerceto(label, null, null);
							generador.getTerceto(Integer.parseInt($$.sval.replaceAll("\\D", ""))).setTipo(TIPO_ETIQUETA);
			}

	| condicion_if bloque_sentencias_ejecutables {System.err.println("Error: Falta END_IF de cierre " + ". Linea: " + lexico.getContadorLinea()); generador.setError();}
	| condicion_if bloque_sentencias_ejecutables condicion_else bloque_sentencias_ejecutables END_IF {
		System.out.println("Se detecto: Sentencia if " + "en linea: " + lexico.getContadorLinea());
		int pos =Integer.parseInt(generador.obtenerElementoPila().replaceAll("\\D", ""));
		generador.eliminarPila();
		Terceto t = generador.getTerceto(pos);
		String label = "ET"+generador.getSizeTercetos();
		t.setTercerParametro(label);
		
		$$.sval=generador.addTerceto(label, null, null);
		generador.getTerceto(Integer.parseInt($$.sval.replaceAll("\\D", ""))).setTipo(TIPO_ETIQUETA);
	 }
	| condicion_if bloque_sentencias_ejecutables condicion_else bloque_sentencias_ejecutables {System.out.println("Error, Falta END_IF de cierre " + "en linea: " + lexico.getContadorLinea());}
	| condicion_if END_IF {System.err.println("Error: Falta de contenido en el bloque then " + ". Linea: " + lexico.getContadorLinea()); generador.setError();}
	| condicion_if condicion_else END_IF {System.err.println("Error: Falta de contenido en el bloque else " + ". Linea: " + lexico.getContadorLinea()); generador.setError();}
	;

condicion_if : IF condicion THEN{
							$$.sval = generador.addTerceto("BF", $2.sval, null);
							generador.getTerceto(Integer.parseInt($$.sval.replaceAll("\\D", ""))).setTipo(TIPO_SALTO);
							generador.agregarPila($$.sval);
				} 
			 ;

condicion_else	: ELSE {
							int pos =Integer.parseInt(generador.obtenerElementoPila().replaceAll("\\D", ""));
							generador.eliminarPila();
							Terceto t = generador.getTerceto(pos);
							int tam = generador.getSizeTercetos()+1;
							String label = "ET"+tam;
							
							t.setTercerParametro(label);
							$$.sval = generador.addTerceto("BI", null, null);
							generador.getTerceto(Integer.parseInt($$.sval.replaceAll("\\D", ""))).setTipo(TIPO_SALTO);
							generador.agregarPila($$.sval);

							generador.addTerceto(label, null, null);
							generador.getTerceto(Integer.parseInt($$.sval.replaceAll("\\D", ""))).setTipo(TIPO_ETIQUETA);
				  }
				;
	

 condicion : '(' exp_arit comparador exp_arit ')' {

                    		TablaSimbolos TS = lexico.getTablaSimbolos();

                    		String primer_exp_arit = TS.buscarVariable($2.sval);
                    		Integer t_primer_exp_arit = null;
                    		Integer pos;

                    		if(primer_exp_arit == null){
                    			System.err.println("Error: variable no declarada. Linea "+lexico.getContadorLinea());
                    			generador.setError();
                    		}else{
                    			switch(primer_exp_arit){
                    				case "Terceto":
                    					pos = Integer.parseInt($2.sval.replaceAll("\\D", ""));
                						t_primer_exp_arit = generador.getTerceto(pos).getTipo();
                    					break;
                    				default:
                    					t_primer_exp_arit = TS.getTipo(primer_exp_arit);
                    					break;
                    			}
                    		}

            				Strign segunda_exp_arit = TS.buscarVariable($4.sval);
                    		Integer t_segunda_exp_arit = null;

                    		if(segunda_exp_arit == null){
                    			System.err.println("Error: variable no declarada. Linea "+lexico.getContadorLinea());
                    			generador.setError();
                    		}else{
                    			switch(segunda_exp_arit){
                    				case "Terceto":
                    					pos = Integer.parseInt($4.sval.replaceAll("\\D", ""));
                						t_segunda_exp_arit = generador.getTerceto(pos).getTipo();
                    					break;
                    				default:
                    					t_segunda_exp_arit = TS.getTipo(segunda_exp_arit);
                    					break;
                    			}
                    		}

                    		if(t_primer_exp_arit != t_segunda_exp_arit){
                    			System.err.println("Error: comparación entre dos expresiones de tipos diferentes. Linea: "+lexico.getContadorLinea());
                    			generador.setError();
                    		}

                    		String operando1, operando2;

                    		if (primer_exp_arit == "Terceto") operando1 = $2.sval;
                            else operando1 = primer_exp_arit;
                            if (factor == "Terceto") operando2 = $4.sval;
                            else operando2 = segunda_exp_arit;

                    		$$.sval = generador.addTerceto($3.sval, operando1, operando2);
                            generador.getTerceto(Integer.parseInt($$.sval.replaceAll("\\D", ""))).setTipo(t_primer_exp_arit);
                    		System.out.println("Se detecto: comparación");

                    	}

		| '(' '(' lista_exp_arit ')' comparador '(' lista_exp_arit ')' ')' {

          	        String[] lista1 = $3.sval.split(",");
          	        String[] lista2 = $7.sval.split(",");
          	        if (lista1.length != lista2.length){
          	            System.err.println("Error: Los tamaños de las listas en la condicion no coinciden. Linea: " + lexico.getContadorLinea());
          	            generador.setError();
          	        }else{

          	        	boolean error_comparacion = false;

          	        	TablaSimbolos TS = lexico.getTablaSimbolos();

                  		Strign primer_exp_arit = TS.buscarVariable(lista1[0]);
          	        	Strign segunda_exp_arit = TS.buscarVariable(lista2[0]);

          	            Integer t_primer_exp_arit = null;
                  		Integer t_segunda_exp_arit = null;
          	            int pos;

          				String operando1, operando2;

                  		if(primer_exp_arit == null){
                  			System.err.println("Error: variable no declarada. Linea "+lexico.getContadorLinea());
                  			generador.setError();
                  		}else{
                  			switch(primer_exp_arit){
                  				case "Terceto":
                  					pos = Integer.parseInt($2.sval.replaceAll("\\D", ""));
              						t_primer_exp_arit = generador.getTerceto(pos).getTipo();
                  					break;
                  				default:
                  					t_primer_exp_arit = TS.getTipo(primer_exp_arit);
                  					break;
                  			}
                  		}



                  		if(segunda_exp_arit == null){
                  			System.err.println("Error: variable no declarada. Linea "+lexico.getContadorLinea());
                  			generador.setError();
                  		}else{
                  			switch(segunda_exp_arit){
                  				case "Terceto":
                  					pos = Integer.parseInt($4.sval.replaceAll("\\D", ""));
              						t_segunda_exp_arit = generador.getTerceto(pos).getTipo();
                  					break;
                  				default:
                  					t_segunda_exp_arit = TS.getTipo(segunda_exp_arit);
                  					break;
                  			}
                  		}

          				if(t_primer_exp_arit!=t_segunda_exp_arit) error_comparacion=true;


                  		if (primer_exp_arit == "Terceto") operando1 = lista1[0];
                          else operando1 = primer_exp_arit;
                          if (segunda_exp_arit == "Terceto") operando2 = lista2[0];
                          else operando2 = segunda_exp_arit;

                  		$$.sval = generador.addTerceto($5.sval, operando1, operando2);
                        generador.getTerceto(Integer.parseInt($$.sval.replaceAll("\\D", ""))).setTipo(t_primer_exp_arit);


          	            if(lista1.length!=1){
          	                String auxTerceto;

          	                for (int i = 1; i<lista1.length; i++){

          	                    primer_exp_arit = TS.buscarVariable(lista1[i]);
          	                    segunda_exp_arit = TS.buscarVariable(lista2[i]);


          	                    if(primer_exp_arit == null){
                  					System.err.println("Error: variable no declarada. Linea "+lexico.getContadorLinea());
                  					generador.setError();
          		        		}else{
          		        			switch(primer_exp_arit){
          		        				case "Terceto":
          		        					pos = Integer.parseInt(lista1[i].replaceAll("\\D", ""));
          		    						t_primer_exp_arit = generador.getTerceto(pos).getTipo();
          		        					break;
          		        				default:
          		        					t_primer_exp_arit = TS.getTipo(primer_exp_arit);
          		        					break;
          		        			}
                  				}


          		        		if(segunda_exp_arit == null){
          		        			System.err.println("Error: variable no declarada. Linea "+lexico.getContadorLinea());
          		        			generador.setError();
          		        		}else{
          		        			switch(segunda_exp_arit){
          		        				case "Terceto":
          		        					pos = Integer.parseInt(lista2[i].replaceAll("\\D", ""));
          		    						t_segunda_exp_arit = generador.getTerceto(pos).getTipo();
          		        					break;
          		        				default:
          		        					t_segunda_exp_arit = TS.getTipo(segunda_exp_arit);
          		        					break;
          		        			}
          		        		}

          						if(t_primer_exp_arit!=t_segunda_exp_arit) error_comparacion=true;

          						if (primer_exp_arit == "Terceto") operando1 = lista1[i];
                          		else operando1 = primer_exp_arit;
                          		if (segunda_exp_arit == "Terceto") operando2 = lista2[i];
                          		else operando2 = segunda_exp_arit;

          						auxTerceto= generador.addTerceto($5.sval, operando1, operando2);
          	                    $$.sval =generador.addTerceto("AND", $$.sval, auxTerceto);
                                generador.getTerceto(Integer.parseInt($$.sval.replaceAll("\\D", ""))).setTipo(t_primer_exp_arit);
          	                }

          	                if(error_comparacion){
          	                	System.err.println("Error: comparación entre dos expresiones de tipos diferentes. Linea: "+lexico.getContadorLinea());
          	                	generador.setError();
          	                }
          	            }

          	        }
          		    System.out.println("Se detecto: comparación múltiple");
          		  }

	    | '(' '(' lista_exp_arit  comparador '(' lista_exp_arit ')' ')' {System.err.println("Error: falta de parentesis en la condicion. Linea: " + lexico.getContadorLinea()); generador.setError();}
	    | '(' '(' lista_exp_arit ')' comparador lista_exp_arit ')' ')' {System.err.println("Error: falta de parentesis en la condicion. Linea: " + lexico.getContadorLinea()); generador.setError();}
	    | '(' lista_exp_arit ')' comparador '(' lista_exp_arit ')' ')' {System.err.println("Error: falta de parentesis en la condicion. Linea: " + lexico.getContadorLinea()); generador.setError();}
	    | '(' '(' lista_exp_arit ')' comparador '(' lista_exp_arit ')' {System.err.println("Error: falta de parentesis en la condicion. Linea: " + lexico.getContadorLinea()); generador.setError();}
		| exp_arit comparador exp_arit ')' {System.err.println("Error: falta de parentesis en la condicion. Linea: " + lexico.getContadorLinea()); generador.setError();}
		| '(' exp_arit comparador exp_arit  {System.err.println("Error: falta de parentesis en la condicion. Linea: " + lexico.getContadorLinea()); generador.setError();}
		/*|'(' exp_arit exp_arit ')' {System.out.println("Error, falta de comparador " + "en linea: " + lexico.getContadorLinea() );} */
		| '(' '(' lista_exp_arit ')' '(' lista_exp_arit ')' ')' {System.err.println("Error: falta de comparador. Linea: " + lexico.getContadorLinea()); generador.setError();}
		| '(' '(' lista_exp_arit ')'  ')' {System.err.println("Error, falta de lista de expresión aritmetica en comparación. Linea: " + lexico.getContadorLinea()); generador.setError();}
		;


comparador : MAYORIGUAL {$$.sval = ">=";}
		| MENORIGUAL {$$.sval = "<=";}
		| DISTINTO {$$.sval = "!=";}
		| '=' {$$.sval = "=";}
		| '>' {$$.sval = ">";}
		| '<' {$$.sval = "<";}
		;

repeat_until : sentencia_repeat bloque_sentencias_ejecutables UNTIL  condicion {
					int pos = Integer.parseInt(generador.obtenerElementoPila().replaceAll("\\D", ""));

					$$.sval = generador.addTerceto("BT", $4.sval, generador.getTerceto(pos).getOperador());
					generador.getTerceto(Integer.parseInt($$.sval.replaceAll("\\D", ""))).setTipo(TIPO_SALTO);
    				generador.eliminarPila();
				}

		| sentencia_repeat UNTIL condicion {System.err.println("Error: falta cuerpo en la iteracion. Linea: " + lexico.getContadorLinea()); generador.setError();}
		| sentencia_repeat bloque_sentencias_ejecutables condicion {System.err.println("Error: falta de until en la iteracion repeat. Linea: " + lexico.getContadorLinea()); generador.setError();}
 		;


sentencia_repeat: REPEAT {
				    $$.sval = generador.addTerceto("ET" + generador.getSizeTercetos(), null, null);
				    generador.getTerceto(Integer.parseInt($$.sval.replaceAll("\\D", ""))).setTipo(TIPO_ETIQUETA);
				    generador.agregarPila($$.sval);
				} //Hay que cambiar esto...
				;
/*-----*/

/*---TIPO COMPUESTO---*/

def_triple : TYPEDEF TRIPLE '<' tipo '>'  ID{
    Integer t = Integer.parseInt($4.sval);
    switch(t){
        case(T_UNSIGNED):
            lexico.getTablaSimbolos().editarTipo($6.sval, TIPO_TRIPLE_UNSIGNED);
            break;
        case(T_SINGLE):
            lexico.getTablaSimbolos().editarTipo($6.sval, TIPO_TRIPLE_SINGLE);
            break;

        case(T_OCTAL):
             lexico.getTablaSimbolos().editarTipo($6.sval, TIPO_TRIPLE_OCTAL);
             break;

        /*case(default):
            System.out.printl("Se intentó definir un tipo Triple de un tipo invalido en linea: " + lexico.getContadorLinea());
            break;
         */
    }
        TablaSimbolos TS = lexico.getTablaSimbolos();
        String amb = TS.getAmbitos();
        this.tiposUsuario.add($6.sval + amb);
        TS.agregarUso($6.sval, NOMBRE_TIPO);
        TS.editarLexema($6.sval, $6.sval + amb);
    }
	;

/*-----*/		

/*--CODIGO---*/
%%

private Lexico lexico;
private Generador generador;
private final Float infPositivo = 1.17549435e-38f;//(float) Math.pow(1.1754943, -38 )
private final Float supPositivo = 3.40282347e38f;//(float) Math.pow(3.40282347, 38)
private final Float infNegativo = -3.40282347e38f;//(float) Math.pow(-3.40282347, 38)
private final Float supNegativo = -1.17549435e-38f;//(float) Math.pow(-1.17549435, -38)

    public final static int T_UNSIGNED = 1;
    public final static int  T_SINGLE = 2;
    public final static int  T_OCTAL = 3;
    public final static int  TIPO_MULTILINEA = 4;
    //¿sEtiqueta seria uso?
    public final static int  TIPO_ETIQUETA = 8;
    public final static int  TIPO_SALTO = 9;
    public final static int  TIPO_DESCONOCIDO = 50;
    public final static int TIPO_TRIPLE_UNSIGNED = 5;
    public final static int TIPO_TRIPLE_SINGLE = 6;
    public final static int TIPO_TRIPLE_OCTAL = 7;

    public final static int NOMBRE_VAR = 101;
    public final static int NOMBRE_FUN = 102;
    public final static int NOMBRE_TIPO = 103;
    public final static int NOMBRE_PARAMETRO = 104;
    public final static int NOMBRE_ETIQUETA = 105;
    public final static int NOMBRE_PROGRAMA = 106;

    public ArrayList<String> tiposUsuario;

public int yylex() throws IOException {
    int token = lexico.yylex();
    this.yylval = lexico.getYylval();
    return token;
}

public void yyerror(String mensaje) {
    System.out.println("Error: " + mensaje);
}

public Parser(String archivo, String salida) throws IOException {
    lexico = Lexico.getInstance(archivo);
    generador = Generador.getInstance();
    generador.setSalida(salida);
    this.tiposUsuario = new ArrayList<>();

}

private String truncarFueraRango(String cte, int linea) throws NumberFormatException{
   	// Reemplazar 's' por 'e' para convertir a notación científica y parsear el float
       cte = cte.replace('s', 'e');
       Float result = Float.parseFloat(cte);

       if(result>0.0f) {
	        if (infPositivo > result) {
	        	System.out.println("Warning: constante fuera de rango. Linea: "+ linea);
	            String nuevaCte = infPositivo.toString().replace('E', 's');
	            return nuevaCte;

	        }else if(supPositivo < result) {
	        	System.out.println("Warning: constante fuera de rango. Linea: "+ linea);
	            String nuevaCte = supPositivo.toString().replace('E', 's');
	            return nuevaCte;
	        }
       }else {
       	if(infNegativo > result) {
       		System.out.println("Warning: constante fuera de rango. Linea: "+ linea);
	            String nuevaCte = infNegativo.toString().replace('E', 's');
	            return nuevaCte;
	        }else if(supNegativo < result) {
	        	System.out.println("Warning: constante fuera de rango. Linea: "+ linea);
	            String nuevaCte = supNegativo.toString().replace('E', 's');
	            return nuevaCte;
	        }
       }

       cte = cte.replace('e', 's');
       return cte;
   }

public static void main(String[] args) throws IOException {
    if(args.length == 2) {
          String archivo = args[0];
          String salida = args[1];
          try {
            Parser parser = new Parser(archivo, salida);
            parser.run();
          }
          catch (IOException excepcion){
            excepcion.printStackTrace();
          }
        }
        else {
          System.out.println("Se debe ingresar un archivo a compilar");
        }
}


