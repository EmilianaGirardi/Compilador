%{
    	import java.io.*;
    	import AnalizadorLexico.Lexico;
%}

%token MENORIGUAL ID ASIGNACION DISTINTO MAYORIGUAL SINGLE_CONSTANTE ENTERO_UNSIGNED OCTAL MULTILINEA REPEAT IF THEN ELSE BEGIN END END_IF OUTF TYPEDEF FUN RET GOTO TRIPLE OCTAL UNSIGNED  SINGLE TIPO_OCTAL UNTIL

%left '+' '-'
%right menos

%%
programa : ID BEGIN conjunto_sentencias END ';'
  	;
conjunto_sentencias : declarativa ';'
			| ejecutable ';'
			| conjunto_sentencias declarativa ';'
			| conjunto_sentencias sentencias_ejecutables ';'
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
					| BEGIN sentencias_ejecutables END
					;
sentencias_ejecutables : ejecutable ';'
 | sentencias_ejecutables ejecutable ';'
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
	;
tipo : TIPO_OCTAL | UNSIGNED | SINGLE  ;

declaracionFun : tipo FUN ID '(' parametro ')' BEGIN cuerpoFun END
		;
parametro : tipo ID
	;
cuerpoFun : retorno
| conjunto_sentencias retorno
;
retorno : RET '(' exp_arit ')'
 ;
asig : ID ASIGNACION exp_arit
 ;
exp_arit : exp_arit '+' termino
	| exp_arit '-' termino
	| termino
	;
lista_exp_arit : exp_arit
		| lista_exp_arit ',' exp_arit
		;

termino : termino '*' factor
	| termino '/' factor
	| factor
	;
factor : ID
	| constante
	| invocacion_fun
	;
etiqueta : ID '@'
constante : SINGLE_CONSTANTE
|ENTERO_UNSIGNED
|OCTAL
|   '-' SINGLE_CONSTANTE   %prec menos
;
invocacion_fun : ID '(' exp_arit ')'
		| ID '(' tipo '(' exp_arit ')' ')'  //para evitar conflictos si que hay una operación (suma, resta, etc)
		;
sentencia_if : IF  condicion  THEN bloque_sentencias_ejecutables END_IF
		| IF condicion THEN bloque_sentencias_ejecutables ELSE bloque_sentencias_ejecutables END_IF
		;

condicion : '(' exp_arit comparador exp_arit ')'
	| '(' lista_exp_arit ')' comparador '(' lista_exp_arit ')'
	;
comparador : MAYORIGUAL
	| MENORIGUAL
	| DISTINTO
| '='
| '>'
| '<'
;

salida : OUTF '(' MULTILINEA ')' | OUTF '(' exp_arit ') ';
repeat_until : REPEAT bloque_sentencias_ejecutables UNTIL  condicion  ;
def_triple : TYPEDEF tipo_compuesto '<' tipo '>'  ID;
tipo_compuesto : TRIPLE
			;
goto : GOTO etiqueta
		;


