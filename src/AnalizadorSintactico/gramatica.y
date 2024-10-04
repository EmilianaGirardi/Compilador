%{
    	import java.io.*;
    	import AnalizadorLexico.Lexico;

private final Float infPositivo = (float) Math.pow(1.1754943, -38 );
private final Float supPositivo = (float) Math.pow(3.40282347, 38);
private final Float infNegativo = (float) Math.pow(-3.40282347, 38);
private final Float supNegativo = (float) Math.pow(-1.17549435, -38);


//construccion del Lexico
String archivo = args[0];
private Lexico lexico = Lexico.getInstance(archivo);


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

%}

%token MENORIGUAL ID ASIGNACION DISTINTO MAYORIGUAL SINGLE_CONSTANTE ENTERO_UNSIGNED OCTAL MULTILINEA REPEAT IF THEN ELSE BEGIN END END_IF OUTF TYPEDEF FUN RET GOTO TRIPLE OCTAL UNSIGNED  SINGLE TIPO_OCTAL UNTIL
%left '+' '-'
%right menos
%%
programa : ID BEGIN conjunto_sentencias END ';'
	      | BEGIN conjunto_sentencias END ';' {System.out.println(“Error, Falta nombre de programa”);}
	   | ID conjunto_sentencias END ';' {System.out.println(“Error de delimitador de programa”);}
	   | ID BEGIN conjunto_sentencias {System.out.println(“Error de delimitador de programa”);}
	   | ID conjunto_sentencias {System.out.println(“Error de delimitador de programa”);}
  	;
conjunto_sentencias : declarativa ';'
			| declarativa {System.out.println(“Falta ;”);}
			| ejecutable ';'
			| ejecutable {System.out.println(“Falta ;”);}
			| conjunto_sentencias declarativa ';'
			| conjunto_sentencias declarativa {System.out.println(“Falta ;”);}
			| conjunto_sentencias sentencias_ejecutables ';'
			|conjunto_sentencias sentencias_ejecutables {System.out.println(“Falta ;”);}
			;
ejecutable : sentencia_if
    |invocacion_fun
    |asig
    | retorno
    | repeat_until
    | goto
    | salida
    ;
bloque_sentencias_ejecutables : ejecutable ';'
					| ejecutable {System.out.println(“Falta ;”);}
					| BEGIN sentencias_ejecutables END
					;
sentencias_ejecutables : ejecutable ';'
				| ejecutable {System.out.println(“Falta ;”);}
 | sentencias_ejecutables ejecutable ';'
| sentencias_ejecutables ejecutable {System.out.println(“Falta ;”);}
;

declarativa : declaracionFun
| declarvar
| def_triple
| declar_compuesto
;
declarvar : tipo lista_var ;
declar_compuesto : ID lista_var ; //tint t1,t2,t3 lo detecta el generador
lista_var : ID
	| lista_var ',' ID
	| lista_var ID  {System.out.println(“Error, falta ',' para diferenciar las variables);}
	;
tipo : TIPO_OCTAL | UNSIGNED | SINGLE  ;

declaracionFun : tipo FUN ID '(' parametro ')' BEGIN cuerpoFun END
		| tipo FUN  '(' parametro ')' BEGIN cuerpoFun END {System.out.println(“Error, Falta nombre de funcion”);}
		|tipo FUN ID '(' ')' BEGIN cuerpoFun END {System.out.println(“Error, Falta parametro de funcion”);}
		;
parametro : tipo ID
		| tipo {System.out.println(“Error, falta nombre del parametro formal”);}
		| ID {System.out.println(“Error, falta tipo del parametro formal”);}
	;
cuerpoFun : retorno
| conjunto_sentencias retorno
;
retorno : RET '(' exp_arit ')'
	| '(' exp_arit ')' {System.out.println(“Falta, la palabra ret que indica retorno”);}
 ;
asig : ID ASIGNACION exp_arit
 ;
exp_arit : exp_arit '+' termino
	| exp_arit '-' termino
	| exp_arit  termino {System.out.printl(“Error, falta de operador”);}
	| exp_arit '+' {System.out.printl(“Error, falta de operando”);}
	| exp_arit '-' {System.out.printl(“Error, falta de operando”);}
	| termino
	;
lista_exp_arit : exp_arit
		| lista_exp_arit ',' exp_arit
		;

termino : termino '*' factor
	| termino '/' factor
	| termino factor {System.out.printl(“Error, falta de operador”);}
	| '*' factor {System.out.printl(“Error, falta de operando”);}
	| '/' factor {System.out.printl(“Error, falta de operando”);}
	| termino '*' {System.out.printl(“Error, falta de operando”);}
	|termino '/' {System.out.printl(“Error, falta de operando”);}
	| factor
	;
factor : ID
	| constante
	| invocacion_fun
	;
etiqueta : ID '@'
constante : SINGLE_CONSTANTE
{String nuevoToken = truncarFueraRango($1.sval, lexico.getContadorLinea());
lexico.getTablaSimbolos().editarLexema($1.sval, nuevoToken);}
|ENTERO_UNSIGNED
|OCTAL
|  '-' SINGLE_CONSTANTE   %prec menos
;
invocacion_fun : ID '(' exp_arit ')'
		| ID '(' tipo '(' exp_arit ')' ')'  //para evitar conflictos si que hay una operación (suma, resta, etc)
		;
sentencia_if : IF  condicion  THEN bloque_sentencias_ejecutables END_IF
	| IF  condicion  THEN bloque_sentencias_ejecutables {System.out.println(“Error, Falta END_IF de cierre”);}
		| IF condicion THEN bloque_sentencias_ejecutables ELSE bloque_sentencias_ejecutables END_IF
		| IF condicion THEN bloque_sentencias_ejecutables ELSE bloque_sentencias_ejecutables {System.out.println(“Error, Falta END_IF de cierre”);}
		| IF  condicion  THEN END_IF {System.out.println(“Error, Falta de contenido en el bloque then”);}
		|IF condicion THEN bloque_sentencias_ejecutables ELSE END_IF {System.out.println(“Error, Falta de contenido en el bloque else”);}
		;

condicion : '(' exp_arit comparador exp_arit ')'
	| '(' lista_exp_arit ')' comparador '(' lista_exp_arit ')'
	| exp_arit comparador exp_arit ')' {System.out.println(“Error, falta de parentesis en la condicion”);}
	| '(' exp_arit comparador exp_arit  {System.out.println(“Error, falta de parentesis en la condicion”);}
	| lista_exp_arit ')' comparador '(' lista_exp_arit ')' {System.out.println(“Error, falta de parentesis en la condicion”);}
	| '(' lista_exp_arit  comparador '(' lista_exp_arit ')' {System.out.println(“Error, falta de parentesis en la condicion”);}
	| '(' lista_exp_arit ')' comparador  lista_exp_arit ')' {System.out.println(“Error, falta de parentesis en la condicion”);}
	| '(' lista_exp_arit ')' comparador '(' lista_exp_arit {System.out.println(“Error, falta de parentesis en la condicion”);}
	|'(' exp_arit exp_arit ')' {System.out.println(“Error, falta de comparador2);}
	| '(' lista_exp_arit ')' '(' lista_exp_arit ')' {System.out.println(“Error, falta de comparador2);}

	;
comparador : MAYORIGUAL
	| MENORIGUAL
	| DISTINTO
| '='
| '>'
| '<'
;

salida : OUTF '(' MULTILINEA ')' | OUTF '(' exp_arit ') '
| OUTF '('')'  {System.out.println(“Error, falta tipo del parametro formal”);}
|OUTF'('condicion')' {System.out.println(“Error, parametro invalido”);}
|OUTF'(asignacion)' {System.out.println(“Error, parametro invalido”);}
 ;
repeat_until : REPEAT bloque_sentencias_ejecutables UNTIL  condicion
|REPEAT UNTIL  condicion {System.out.println(“Error, falta cuerpo en la iteracion”);}
| REPEAT bloque_sentencias_ejecutables condicion {System.out.println(“Error, falta de until en la iteracion repeat”);}
 ;
def_triple : TYPEDEF tipo_compuesto '<' tipo '>'  ID;
tipo_compuesto : TRIPLE
			;
goto : GOTO etiqueta
	| GOTO error ';' {System.out.println(“Error, falta de etiqueta en la sentencia GOTO”);}
		;



