%{
    	import java.io.*;
    	//import Lex.Lex;
%}

%token MENORIGUAL ID ASIGNACION DISTINTO MAYORIGUAL SINGLE_CONSTANTE ENTERO_UNSIGNED OCTAL MULTILINEA REPEAT IF THEN ELSE BEGIN END END_IF OUTF TYPEDEF FUN RET GOTO TRIPLE OCTAL UNSIGNED  SINGLE TIPO_OCTAL UNTIL

%left '+' '-'
%left '/' '*'
%right menos

%%
programa : ID BEGIN conjunto_sentencias END ';' {System.out.println("Se detecto: Programa");}
      | BEGIN conjunto_sentencias END ';' {System.out.println("Error, Falta nombre de programa " + "en linea: " + lexico.getContadorLinea());}
      | ID conjunto_sentencias END ';' {System.out.println("Error de delimitador de programa " + "en linea: " + lexico.getContadorLinea());}
      | ID BEGIN conjunto_sentencias {System.out.println("Error de delimitador de programa " + "en linea: " + lexico.getContadorLinea());}
      | ID conjunto_sentencias {System.out.println("Error de delimitador de programa " + "en linea: " + lexico.getContadorLinea());}
      ;
conjunto_sentencias : declarativa ';'
			| declarativa {System.out.println("Falta ; " + "en linea: " + lexico.getContadorLinea());}
			| ejecutable ';'
			| ejecutable {System.out.println("Falta ; " + "en linea: " + lexico.getContadorLinea());}
			| conjunto_sentencias declarativa ';'
			| conjunto_sentencias declarativa {System.out.println("Falta ; " + "en linea: " + lexico.getContadorLinea());}
			| conjunto_sentencias ejecutable ';'
			| conjunto_sentencias ejecutable {System.out.println("Falta ; " + "en linea: " + lexico.getContadorLinea());}
			;

ejecutable : sentencia_if {System.out.println("Se detecto: Sentencia if" + "en linea: " + lexico.getContadorLinea());}
    |invocacion_fun  {System.out.println("Se detecto: Invocacion a funcion " + "en linea: " + lexico.getContadorLinea());}
    |asig  {System.out.println("Se detecto: Asignacion " + "en linea: " + lexico.getContadorLinea());}
    | retorno {System.out.println("Se detecto: Retorno " + "en linea: " + lexico.getContadorLinea());}
    | repeat_until {System.out.println("Se detecto: Ciclo repeat until " + "en linea: " + lexico.getContadorLinea());}
    | goto {System.out.println("Se detecto: Asignacion " + "en linea: " + lexico.getContadorLinea());}
    | salida {System.out.println("Se detecto: Salida " + "en linea: " + lexico.getContadorLinea());}
    ;

bloque_sentencias_ejecutables : BEGIN sentencias_ejecutables END
					;

sentencias_ejecutables : ejecutable ';'
 | sentencias_ejecutables ejecutable ';'
 | ejecutable {System.out.println("Falta ;");}
 | sentencias_ejecutables ejecutable {System.out.println("Falta ;");}
;

declarativa : declaracionFun {System.out.println("Se detecto: Declaracion de funcion " + "en linea: " + lexico.getContadorLinea());}
| declarvar {System.out.println("Se detecto: Declaración de variable " + "en linea: " + lexico.getContadorLinea());}
| def_triple {System.out.println("Se detecto: Declaración de tipo triple " + "en linea: " + lexico.getContadorLinea());}
| declar_compuesto {System.out.println("Se detecto: Declaración de variable tipo triple " + "en linea: " + lexico.getContadorLinea());}
;
declarvar : tipo lista_var ; {System.out.println("Se detecto: Declaración de lista de variables " + "en linea: " + lexico.getContadorLinea());}

declar_compuesto : ID lista_var ; //tint t1,t2,t3 lo detecta el generador

lista_var : ID
	| lista_var ',' ID
	| error ID  {System.out.println("Error, falta ',' para diferenciar las variables");}
	;

tipo : TIPO_OCTAL | UNSIGNED | SINGLE  ;

declaracionFun : tipo FUN ID '(' parametro ')' BEGIN cuerpoFun END
		| tipo FUN  '(' parametro ')' BEGIN cuerpoFun END {System.out.println("Error, Falta nombre de funcion");}
		|tipo FUN ID '(' ')' BEGIN cuerpoFun END {System.out.println("Error, Falta parametro de funcion");}
		;


parametro : tipo ID
		| tipo {System.out.println("Error, falta nombre del parametro formal");}
		| ID {System.out.println("Error, falta tipo del parametro formal");}
	;

cuerpoFun : retorno
| conjunto_sentencias retorno
;

retorno : RET '(' exp_arit ')'
	| error '(' exp_arit ')' {System.out.println("Falta, la palabra ret que indica retorno");}
 ;


asig : ID ASIGNACION exp_arit
 ;

exp_arit : exp_arit '+' termino {System.out.println("Se detecto: Suma " + "en linea: " + lexico.getContadorLinea());}
	| exp_arit '-' termino {System.out.println("Se detecto: Resta " + "en linea: " + lexico.getContadorLinea());}
	| error ';' {System.out.println("Error en expresion aritmetica");}
	| termino
	;




lista_exp_arit : exp_arit
		| lista_exp_arit ',' exp_arit
		;

termino : termino '*' factor {System.out.println("Se detecto: Multiplicación " + "en linea: " + lexico.getContadorLinea());}
	| termino '/' factor {System.out.println("Se detecto: División " + "en linea: " + lexico.getContadorLinea());}
	| factor
	| error factor {System.out.println("Error en termino" + "en linea: " + lexico.getContadorLinea());}
	;

factor : ID {System.out.println("Se detecto: Identificador " + $1.sval + "en linea: " + lexico.getContadorLinea());}
	| constante
	| invocacion_fun {System.out.println("Se detecto: División " + "en linea: " + lexico.getContadorLinea());}
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

sentencia_if : IF  condicion  THEN bloque_sentencias_ejecutables END_IF {System.out.println("Se detecto: Sentencia if " + "en linea: " + lexico.getContadorLinea());}
	| IF  condicion  THEN bloque_sentencias_ejecutables {System.out.println("Error, Falta END_IF de cierre " + "en linea: " + lexico.getContadorLinea());}
		| IF condicion THEN bloque_sentencias_ejecutables ELSE bloque_sentencias_ejecutables END_IF {System.out.println("Se detecto: Sentencia if " + "en linea: " + lexico.getContadorLinea());}
		| IF condicion THEN bloque_sentencias_ejecutables ELSE bloque_sentencias_ejecutables {System.out.println("Error, Falta END_IF de cierre " + "en linea: " + lexico.getContadorLinea());}
		| IF  condicion  THEN END_IF {System.out.println("Error, Falta de contenido en el bloque then " + "en linea: " + lexico.getContadorLinea());}
		|IF condicion THEN bloque_sentencias_ejecutables ELSE END_IF {System.out.println("Error, Falta de contenido en el bloque else " + "en linea: " + lexico.getContadorLinea());}
		;


condicion : '(' exp_arit comparador exp_arit ')'
	| '(' '(' lista_exp_arit ')' comparador '(' lista_exp_arit ')' ')'
    | '(' '(' lista_exp_arit  comparador '(' lista_exp_arit ')' ')' {System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
    | '(' '(' lista_exp_arit ')' comparador lista_exp_arit ')' ')' {System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
    | '(' lista_exp_arit ')' comparador '(' lista_exp_arit ')' ')' {System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
    | '(' '(' lista_exp_arit ')' comparador '(' lista_exp_arit ')' {System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
	| exp_arit comparador exp_arit ')' {System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
	| '(' exp_arit comparador exp_arit  {System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
	|'(' exp_arit exp_arit ')' {System.out.println("Error, falta de comparador " + "en linea: " + lexico.getContadorLinea() );}
	| '(' '(' lista_exp_arit ')' '(' lista_exp_arit ')' ')' {System.out.println("Error, falta de comparador " + "en linea: " + lexico.getContadorLinea());}
	;


comparador : MAYORIGUAL
	| MENORIGUAL
	| DISTINTO
| '='
| '>'
| '<'
;

salida : OUTF '(' MULTILINEA ')' | OUTF '(' exp_arit ') '
    |OUTF '(' ')'  {System.out.println("Error, falta tipo del parametro formal " + "en linea: " + lexico.getContadorLinea());}
    |OUTF '(' error ')' {System.out.println("Error, parametro invalido " + "en linea: " + lexico.getContadorLinea());}
;

repeat_until : REPEAT bloque_sentencias_ejecutables UNTIL  condicion
|REPEAT UNTIL  condicion {System.out.println("Error, falta cuerpo en la iteracion " + "en linea: " + lexico.getContadorLinea());}
| REPEAT bloque_sentencias_ejecutables condicion {System.out.println("Error, falta de until en la iteracion repeat" + "en linea: " + lexico.getContadorLinea());}
 ;

def_triple : TYPEDEF tipo_compuesto '<' tipo '>'  ID;
tipo_compuesto : TRIPLE
			;

goto : GOTO etiqueta
	| GOTO error ';' {System.out.println("Error, falta de etiqueta en la sentencia GOTO" + "en linea: " + lexico.getContadorLinea());}
	;
%%

private Lexico lexico;
private final Float infPositivo = (float) Math.pow(1.1754943, -38 );
private final Float supPositivo = (float) Math.pow(3.40282347, 38);
private final Float infNegativo = (float) Math.pow(-3.40282347, 38);
private final Float supNegativo = (float) Math.pow(-1.17549435, -38);

public int yylex() {
    return lexico.yylex();
}

public void yyerror(String mensaje) {
    System.out.println("Error: " + mensaje");
}

public Parser(String archivo) {
    lexico = Lexico.getInstance(archivo);
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

public static void main(String[] args){
    if(args.length > 1) {
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


