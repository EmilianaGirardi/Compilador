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

programa : ID BEGIN conjunto_sentencias END ';' {System.out.println("Se detecto: Programa");}
      | BEGIN conjunto_sentencias END ';' {System.out.println("Error, Falta nombre de programa");}
      | ID conjunto_sentencias END ';' {System.out.println("Error de delimitador de programa ");}
      ;
      
conjunto_sentencias : declarativa ';'
			| declarativa {System.out.println("Falta ; " + "antes de la linea: " + lexico.getContadorLinea());}
			| ejecutable ';'
			| ejecutable {System.out.println("Falta ; " + "antes de la linea: " + lexico.getContadorLinea());}
			| conjunto_sentencias declarativa ';'
			| conjunto_sentencias declarativa {System.out.println("Falta ; " + "antes de la linea: " + lexico.getContadorLinea());}
			| conjunto_sentencias ejecutable ';'
			| conjunto_sentencias ejecutable {System.out.println("Falta ; " + "antes de la linea: " + lexico.getContadorLinea());}
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
 	| ejecutable {System.out.println("Falta ;");}
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
        Integer tipo;
        for (String var : lista){
            tipo = Integer.parseInt($1.sval);
            TS.editarTipo(var, tipo);
        }
    }
    ;

declar_compuesto : ID lista_var{
        if (tiposUsuario.contains($1.sval)){
            TablaSimbolos TS = lexico.getTablaSimbolos();
            Integer t = TS.getTipo($1.sval);
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
            for (String var : lista){
                for(int i=1; i<=3; i++){
                    String token = var+'{'+i+'}';
                    ArrayList<Integer> atributos = new ArrayList<Integer>();
                    atributos.add(258);
                    atributos.add(t);
                    TS.agregarToken(token, atributos);
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

declaracionFun : tipo FUN ID '(' parametro ')' BEGIN conjunto_sentencias retorno END{
                TablaSimbolos TS = lexico.getTablaSimbolos();
                Integer tipo = Integer.parseInt($1.sval);
                TS.editarTipo($3.sval, tipo);
                Integer tipoRetorno = generador.getTerceto(Integer.parseInt($9.sval)).getTipo();
                if (tipo != tipoRetorno){
                    System.out.println("Error: tipo de retorno invalido en funcion: " + $3.sval);
                }
     }
	| tipo FUN ID '(' parametro ')' BEGIN retorno END{
	            TablaSimbolos TS = lexico.getTablaSimbolos();
                Integer tipo = Integer.parseInt($1.sval);
                TS.editarTipo($3.sval, tipo);
                Integer tipoRetorno = generador.getTerceto(Integer.parseInt($8.sval)).getTipo();
                if (tipo != tipoRetorno){
                     System.out.println("Error: tipo de retorno invalido en funcion: " + $3.sval);
                }
	}

	| tipo FUN ID '(' parametro ')' BEGIN conjunto_sentencias END {System.out.println("Error, falta retorno en funcion");}
	| tipo FUN  '(' parametro ')' BEGIN conjunto_sentencias retorno END {System.out.println("Error, Falta nombre de funcion");}
	| tipo FUN  '(' parametro ')' BEGIN retorno END {System.out.println("Error, Falta nombre de funcion");}
	| tipo FUN ID '(' ')' BEGIN conjunto_sentencias retorno END {System.out.println("Error, Falta parametro de funcion");}
	| tipo FUN ID '(' ')' BEGIN retorno END {System.out.println("Error, Falta parametro de funcion");}
	;


parametro : tipo ID
	| tipo {System.out.println("Error, falta nombre del parametro formal");}
	| ID {System.out.println("Error, falta tipo del parametro formal");}
	;


retorno : RET '(' exp_arit ')' ';'{
            $$.sval = generador.addTerceto("RETORNO", $3.sval, null);
            Integer tipo = generador.getTerceto(Integer.parseInt($3.sval)).getTipo();
            generador.getTerceto(Integer.parseInt($$.sval)).setTipo(tipo);
        }
	    ;

invocacion_fun : ID '(' exp_arit ')'{
    $$.sval = generador.addTerceto("INVOCACION", $1.sval, $3.sval);
    }
	| ID '(' tipo '(' exp_arit ')' ')'  //para evitar conflictos si que hay una operación (suma, resta, etc)
	| ID '(' ')' {System.out.println("Error de falta de parámetro en invocación a función en linea: " + lexico.getContadorLinea());}
	;

/*------*/

/*---EXPRESION ARITMETICA---*/

exp_arit : exp_arit '+' termino {
                    $$.sval = generador.addTerceto("+", $1.sval, $3.sval);
                    System.out.println("Se detecto: Suma " + "en linea: " + lexico.getContadorLinea());
           }

	       | exp_arit '-' termino {
	                $$.sval = generador.addTerceto("-", $1.sval, $3.sval);
	                System.out.println("Se detecto: Resta " + "en linea: " + lexico.getContadorLinea());
	       }

           | exp_arit '+' error ';' {
                    System.out.println("Error: Falta el término después de '+' en expresion aritmetica en línea: " + lexico.getContadorLinea());
           }

           | exp_arit '-' error ';'  {
                    System.out.println("Error: Falta el término después de '-' en expresión aritmetica en línea: " + lexico.getContadorLinea());
           }
           
           | termino
           ;



lista_exp_arit : exp_arit {
        			$$.sval = $1.sval;
    			}
	| lista_exp_arit ',' exp_arit {
	    $$.sval = $1.sval.concat(",").concat($3.sval);
	}
	;

termino : termino '*' factor {
       			 $$.sval = generador.addTerceto("*", $1.sval, $3.sval);
       			 System.out.println("Se detecto: Multiplicación " + "en linea: " + lexico.getContadorLinea());
   		 }
	| termino '/' factor {
        	$$.sval = generador.addTerceto("/", $1.sval, $3.sval);
        	System.out.println("Se detecto: División " + "en linea: " + lexico.getContadorLinea());
		}
	| factor {
	    	$$.sval = $1.sval;
		}

	| termino '*' error ';' {System.out.println("Error: Falta el factor después de '*' en expresion aritmetica en línea: " + lexico.getContadorLinea());}
    | termino '/' error ';'  {System.out.println("Error: Falta el factor después de '/' en expresión aritmetica en línea: " + lexico.getContadorLinea());}
	
	;

factor : ID {
            $$.sval = $1.sval;
            System.out.println("Se detecto: Identificador " + $1.sval + " en linea: " + lexico.getContadorLinea());
        }
	| invocacion_fun {System.out.println("Se detecto: Invocación a función " + "en linea: " + lexico.getContadorLinea());}
	| triple {$$.sval = $1.sval;}
	| SINGLE_CONSTANTE {
		 	$$.sval = truncarFueraRango($1.sval, lexico.getContadorLinea());
            lexico.getTablaSimbolos().editarLexema($1.sval, $$.sval);
        }
    | ENTERO_UNSIGNED {
            $$.sval = $1.sval;
        }
    | OCTAL {
            $$.sval = $1.sval;
        }
    | '-' SINGLE_CONSTANTE {
        	$$.sval = truncarFueraRango("-"+$2.sval, lexico.getContadorLinea());
        	lexico.getTablaSimbolos().editarLexema($2.sval, $$.sval);
        }
    ;


triple : ID '{' ENTERO_UNSIGNED '}' {
    String token = $1.sval+'{'+$3.sval+'}';
    TablaSimbolos TS = lexico.getTablaSimbolos();
    if (TS.estaToken(token)){
        $$.sval = token;
    }
    else {
        System.out.println("Variable inexistenet o Intento de acceso a una posición de triple inexistente en linea " + lexico.getContadorLinea());
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
            $$.sval = generador.addTerceto(":=", $1.sval, $3.sval);
            
            int t_exp_arit;

            if ($3.sval.matches("\\[T\\d+\\]")) {
            	int pos = Integer.parseInt($3.sval.replaceAll("\\D", ""));
    			t_exp_arit = generador.getTerceto(pos).getTipo();
			} else {
    			t_exp_arit = lexico.getTablaSimbolos().getTipo($3.sval);;
			}

            
            int t_id = lexico.getTablaSimbolos().getTipo($1.sval);

            if(t_id==null){
            	System.out.println("Error: variable "+$1.sval+" no declarada. Linea: "+lexico.getContadorLinea());
            	//generador.getTerceto(Integer.parseInt($$.sval)).setTipo(t_id); ponerle tipo al terceto sería raro, porque si tiene un error no se sabe
            }else if(t_id!=t_exp_arit){
            	System.out.println("Error: se intenta asignar un "+t_exp_arit+" a una variable "+t_id+" en la linea "+lexico.getContadorLinea());
            	//generador.getTerceto(Integer.parseInt($$.sval)).setTipo(t_id); ponerle tipo al terceto sería raro, porque si tiene un error no se sabe
            }else{
				generador.getTerceto(Integer.parseInt($$.sval.replaceAll("\\D", "")).setTipo(t_id);
            }
        }

    | triple ASIGNACION exp_arit {
        $$.sval = generador.addTerceto(":=", $1.sval, $3.sval);

        int t_exp_arit;    
        if ($3.sval.matches("\\[T\\d+\\]")) {
        	int pos = Integer.parseInt($3.sval.replaceAll("\\D", ""));
    		t_exp_arit = generador.getTerceto(pos).getTipo();
		} else {
    		t_exp_arit = lexico.getTablaSimbolos().getTipo($3.sval);;
		}
        
        int t_triple = lexico.getTablaSimbolos().getTipo($1.sval);

        if(t_triple==null){
            	System.out.println("Error: variable "+$1.sval+" no existente. Linea: "+lexico.getContadorLinea());
            	//generador.getTerceto(Integer.parseInt($$.sval)).setTipo(t_id); ponerle tipo al terceto sería raro, porque si tiene un error no se sabe
        }else if(t_triple!=t_exp_arit){
            System.out.println("Error: se intenta asignar un "+t_exp_arit+" a una variable "+t_triple+" en la linea "+lexico.getContadorLinea());
            //generador.getTerceto(Integer.parseInt($$.sval)).setTipo(t_id); ponerle tipo al terceto sería raro, porque si tiene un error no se sabe
        }else{
			generador.getTerceto(Integer.parseInt($$.sval.replaceAll("\\D", "")).setTipo(t_triple);
        }
    }
 ;	


etiqueta : ID '@' {
    			String etq = $1.sval+"@";
    			if(!generador.isEtiqueta(etq)){
    				generador.putEtiqueta(etq, $$.sval);

    				$$.sval = generador.addTerceto("ET"+$1.sval+"@",null,null);
    				generador.getTerceto(Integer.parseInt($$.sval.replaceAll("\\D", "")).setTipo(TIPO_ETIQUETA);
    			}else{
    				System.out.println("Error: la etiqueta "+etq+" ya existe. Linea: "+lexico.getContadorLinea());
    			}
    		}
		;

goto : GOTO ID '@' {
			String etq = $2.sval+"@";
			if(generador.isEtiqueta(etq)){
				$$.sval = generador.addTerceto("BI", etq, generador.posicionEtiqueta(etq));	
			}else{
				$$.sval = generador.addTerceto("BI", etq, null);
				//generador.addGoto(Integer.parseInt($$.sval.replaceAll("\\D", ""), etq).setTipo(TIPO_SALTO); ¿Le agregamos un tipo a los saltos?
			}
        	
       }
	| GOTO error ';' {System.out.println("Error, falta de etiqueta en la sentencia GOTO" + "en linea: " + lexico.getContadorLinea());}
	;

salida : OUTF '(' MULTILINEA ')' {
        	$$.sval = generador.addTerceto("SALIDA", $3.sval, null);
        }

        | OUTF '(' exp_arit ')' {
        	$$.sval = generador.addTerceto("SALIDA", $3.sval, null);
        }

    |OUTF '(' ')'  {System.out.println("Error, falta parametro " + "en linea: " + lexico.getContadorLinea());}
    |OUTF '(' error ')' {System.out.println("Error, parametro invalido " + "en linea: " + lexico.getContadorLinea());}
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
			}

	| condicion_if bloque_sentencias_ejecutables {System.out.println("Error, Falta END_IF de cierre " + "en linea: " + lexico.getContadorLinea());}
	| condicion_if bloque_sentencias_ejecutables condicion_else bloque_sentencias_ejecutables END_IF {
		System.out.println("Se detecto: Sentencia if " + "en linea: " + lexico.getContadorLinea());
		int pos =Integer.parseInt(generador.obtenerElementoPila().replaceAll("\\D", ""));
		generador.eliminarPila();
		Terceto t = generador.getTerceto(pos);
		String label = "ET"+generador.getSizeTercetos();
		t.setTercerParametro(label);
		
		$$.sval=generador.addTerceto(label, null, null);
	 }
	| condicion_if bloque_sentencias_ejecutables condicion_else bloque_sentencias_ejecutables {System.out.println("Error, Falta END_IF de cierre " + "en linea: " + lexico.getContadorLinea());}
	| condicion_if END_IF {System.out.println("Error, Falta de contenido en el bloque then " + "en linea: " + lexico.getContadorLinea());}
	| condicion_if condicion_else END_IF {System.out.println("Error, Falta de contenido en el bloque else " + "en linea: " + lexico.getContadorLinea());}
	;

condicion_if : IF condicion THEN{
							$$.sval = generador.addTerceto("BF", $2.sval, null);
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
							generador.agregarPila($$.sval);

							generador.addTerceto(label, null, null);
				  }
				;
	

condicion : '(' exp_arit comparador exp_arit ')' {

        		$$.sval = generador.addTerceto($3.sval, $2.sval, $4.sval);
        		System.out.println("Se detecto: comparación");

        		int pos;
        		int t_primer_exp_arit;

        		if ($2.sval.matches("\\[T\\d+\\]")) {
        			pos = Integer.parseInt($2.sval.replaceAll("\\D", ""));
    				t_primer_exp_arit = generador.getTerceto(pos).getTipo();
				} else {
		    		t_primer_exp_arit = lexico.getTablaSimbolos().getTipo($2.sval);;
				}

        		int t_segunda_exp_arit;
        		if ($4.sval.matches("\\[T\\d+\\]")) {
        			pos = Integer.parseInt($4.sval.replaceAll("\\D", ""));
    				t_segunda_exp_arit = generador.getTerceto(pos).getTipo();
				} else {
		    		t_exp_t_segunda_exp_aritarit = lexico.getTablaSimbolos().getTipo($4.sval);;
				}

        		if(t_primer_exp_arit != t_segunda_exp_arit){
        			System.out.println("Error: comparación entre dos expreiones de tipos diferentes. Linea: "+lexico.getContadorLinea());
        		}
        	}

		| '(' '(' lista_exp_arit ')' comparador '(' lista_exp_arit ')' ')' {

	        String[] lista1 = $3.sval.split(",");
	        String[] lista2 = $7.sval.split(",");
	        if (lista1.length != lista2.length){
	            System.out.println("Los tamaños de las listas en la condicion no coinciden en linea: " + lexico.getContadorLinea());
	        }else{
	        	$$.sval = generador.addTerceto($5.sval, lista1[0], lista2[0]);

	        	boolean error_comparacion = false;
	        	int pos;
	            int t_primer_exp_arit;

	        	if (lista1[0].matches("\\[T\\d+\\]")) {
	        		pos = Integer.parseInt(lista1[0].replaceAll("\\D", ""));
	    			t_primer_exp_arit = generador.getTerceto(pos).getTipo();
				} else {
			    	t_primer_exp_arit = lexico.getTablaSimbolos().getTipo(lista1[0]);;
				}

	        	int t_segunda_exp_arit;
	        	if (lista2[0].matches("\\[T\\d+\\]")) {
	        		pos = Integer.parseInt(lista2[0].replaceAll("\\D", ""));
	    			t_segunda_exp_arit = generador.getTerceto(pos).getTipo();
				} else {
			    	t_exp_t_segunda_exp_aritarit = lexico.getTablaSimbolos().getTipo(lista2[0]);;
				}

				if(t_primer_exp_arit!=t_segunda_exp_arit) error_comparacion=true;


	            if(lista1.length!=1){
	                String auxTerceto;

	                for (int i = 1; i<lista1.length; i++){
	                    auxTerceto= generador.addTerceto($5.sval, lista1[i], lista2[i]);
	                    $$.sval =generador.addTerceto("AND", $$.sval, auxTerceto);
	                    
	                    if (lista1[i].matches("\\[T\\d+\\]")) {
	        				pos = Integer.parseInt(lista1[i].replaceAll("\\D", ""));
	    					t_primer_exp_arit = generador.getTerceto(pos).getTipo();
						} else {
			    			t_primer_exp_arit = lexico.getTablaSimbolos().getTipo(lista1[i]);;
						}

			        	if (lista2[i].matches("\\[T\\d+\\]")) {
			        		pos = Integer.parseInt(lista2[i].replaceAll("\\D", ""));
			    			t_segunda_exp_arit = generador.getTerceto(pos).getTipo();
						} else {
					    	t_exp_t_segunda_exp_aritarit = lexico.getTablaSimbolos().getTipo(lista2[i]);;
						}

						if(t_primer_exp_arit!=t_segunda_exp_arit) error_comparacion=true;
	                }

	                if(error_comparacion){
	                	System.out.println("Error: comparación entre dos expreiones de tipos diferentes. Linea: "+lexico.getContadorLinea());
	                }

	                /*$$.sval = generador.addTerceto("BF", $$.sval, null);*/
	                /*generador.addPila($$.sval);*/
	            }

	        }
		    System.out.println("Se detecto: comparación múltiple");
		  }

	    | '(' '(' lista_exp_arit  comparador '(' lista_exp_arit ')' ')' {System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
	    | '(' '(' lista_exp_arit ')' comparador lista_exp_arit ')' ')' {System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
	    | '(' lista_exp_arit ')' comparador '(' lista_exp_arit ')' ')' {System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
	    | '(' '(' lista_exp_arit ')' comparador '(' lista_exp_arit ')' {System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
		| exp_arit comparador exp_arit ')' {System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
		| '(' exp_arit comparador exp_arit  {System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
		/*|'(' exp_arit exp_arit ')' {System.out.println("Error, falta de comparador " + "en linea: " + lexico.getContadorLinea() );} */
		| '(' '(' lista_exp_arit ')' '(' lista_exp_arit ')' ')' {System.out.println("Error, falta de comparador " + "en linea: " + lexico.getContadorLinea());}
		| '(' '(' lista_exp_arit ')'  ')' {System.out.println("Error, falta de lista de expresión aritmetica en comparación " + "en linea: " + lexico.getContadorLinea());}
		;


comparador : MAYORIGUAL {$$.sval = ">=";}
		| MENORIGUAL {$$.sval = "<=";}
		| DISTINTO {$$.sval = "!=";}
		| '=' {$$.sval = "=";}
		| '>' {$$.sval = ">";}
		| '<' {$$.sval = "<";}
		;

repeat_until : sentencia_repeat bloque_sentencias_ejecutables UNTIL  condicion {
					$$.sval = generador.addTerceto("BT", $4.sval, generador.obtenerElementoPila());
    				generador.eliminarPila();
				}

		| sentencia_repeat UNTIL condicion {System.out.println("Error, falta cuerpo en la iteracion " + "en linea: " + lexico.getContadorLinea());}
		| sentencia_repeat bloque_sentencias_ejecutables condicion {System.out.println("Error, falta de until en la iteracion repeat" + "en linea: " + lexico.getContadorLinea());}
 		;


sentencia_repeat: REPEAT {
				    $$.sval = generador.addTerceto("ET" + generador.getSizeTercetos(), null, null);
				    generador.agregarPila('E' + $$.sval);
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
        this.tiposUsuario.add($6.sval);
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
    public final static int  TIPO_ETIQUETA = 8;
    public final static int  TIPO_DESCONOCIDO = 50;
    public final static int TIPO_TRIPLE_UNSIGNED = 5;
    public final static int TIPO_TRIPLE_SINGLE = 6;
    public final static int TIPO_TRIPLE_OCTAL = 7;

    public ArrayList<String> tiposUsuario;

public int yylex() throws IOException {
    int token = lexico.yylex();
    this.yylval = lexico.getYylval();
    return token;
}

public void yyerror(String mensaje) {
    System.out.println("Error: " + mensaje);
}

public Parser(String archivo) throws IOException {
    lexico = Lexico.getInstance(archivo);
    generador = Generador.getInstance();
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
    if(args.length > 0) {
          String archivo = args[0];
          try {
            Parser parser = new Parser(archivo);
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


