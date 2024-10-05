%{
    	import java.io.*;
    	//import Lex.Lex;
%}

%token MENORIGUAL ID ASIGNACION DISTINTO MAYORIGUAL SINGLE_CONSTANTE ENTERO_UNSIGNED OCTAL MULTILINEA REPEAT IF THEN ELSE BEGIN END END_IF OUTF TYPEDEF FUN RET GOTO TRIPLE OCTAL UNSIGNED  SINGLE TIPO_OCTAL UNTIL

%left '+' '-'
%left '/' '*'
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
			| conjunto_sentencias ejecutable ';'
			| conjunto_sentencias ejecutable {System.out.println(“Falta ;”);}
			;

ejecutable : sentencia_if
    |invocacion_fun
    |asig
    | retorno
    | repeat_until
    | goto
    | salida
    ;

bloque_sentencias_ejecutables : BEGIN sentencias_ejecutables END
					;

sentencias_ejecutables : ejecutable ';'
 | sentencias_ejecutables ejecutable ';'
 | ejecutable {System.out.println(“Falta ;”);}
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
	| error ID  {System.out.println(“Error, falta ',' para diferenciar las variables);}
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
	| error '(' exp_arit ')' {System.out.println(“Falta, la palabra ret que indica retorno”);}
 ;


asig : ID ASIGNACION exp_arit
 ;

exp_arit : exp_arit '+' termino
	| exp_arit '-' termino
	| error ';' {System.out.println("Error en expresion aritmetica");}
	| termino
	;




lista_exp_arit : exp_arit
		| lista_exp_arit ',' exp_arit
		;

termino : termino '*' factor
	| termino '/' factor
	| factor
	| error factor {System.out.println("Error en termino");}
	;

factor : ID
	| constante
	| invocacion_fun
	;
etiqueta : ID '@'
;

constante : SINGLE_CONSTANTE
    |ENTERO_UNSIGNED
    |OCTAL
    /* |   '-' SINGLE_CONSTANTE   %prec menos */
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
	| '(' '(' lista_exp_arit ')' comparador '(' lista_exp_arit ')' ')'
    | '(' '(' lista_exp_arit  comparador '(' lista_exp_arit ')' ')' {System.out.println(“Error, falta de parentesis en la condicion”);}
    | '(' '(' lista_exp_arit ')' comparador lista_exp_arit ')' ')' {System.out.println(“Error, falta de parentesis en la condicion”);}
    | '(' lista_exp_arit ')' comparador '(' lista_exp_arit ')' ')' {System.out.println(“Error, falta de parentesis en la condicion”);}
    | '(' '(' lista_exp_arit ')' comparador '(' lista_exp_arit ')' {System.out.println(“Error, falta de parentesis en la condicion”);}
	| exp_arit comparador exp_arit ')' {System.out.println(“Error, falta de parentesis en la condicion”);}
	| '(' exp_arit comparador exp_arit  {System.out.println(“Error, falta de parentesis en la condicion”);}
	|'(' exp_arit exp_arit ')' {System.out.println(“Error, falta de comparador);}
	| '(' '(' lista_exp_arit ')' '(' lista_exp_arit ')' ')' {System.out.println(“Error, falta de comparador);}
	;


comparador : MAYORIGUAL
	| MENORIGUAL
	| DISTINTO
| '='
| '>'
| '<'
;

salida : OUTF '(' MULTILINEA ')' | OUTF '(' exp_arit ') '
    |OUTF '(' ')'  {System.out.println(“Error, falta tipo del parametro formal”);}
    |OUTF '(' error ')' {System.out.println(“Error, parametro invalido”);}
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



